package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.constans.DataCenterEnum;
import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.constans.PowerOperationEnum;
import com.sobey.api.service.data.ConstansData;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.core.utils.Encodes;
import com.sobey.core.utils.Identities;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.ConfigFirewallAddressDTO;
import com.sobey.generate.cmdbuild.ConfigFirewallPolicyDTO;
import com.sobey.generate.cmdbuild.ConfigFirewallServiceCategoryDTO;
import com.sobey.generate.cmdbuild.ConfigRouterStaticDTO;
import com.sobey.generate.cmdbuild.ConfigSystemInterfaceDTO;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.IdResult;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.NicDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.firewall.AuthenticateFirewallParameter;
import com.sobey.generate.firewall.ConfigFirewallAddressParameter;
import com.sobey.generate.firewall.ConfigFirewallAddressParameters;
import com.sobey.generate.firewall.ConfigFirewallPolicyParameter;
import com.sobey.generate.firewall.ConfigFirewallPolicyParameters;
import com.sobey.generate.firewall.ConfigFirewallServiceCategoryParameter;
import com.sobey.generate.firewall.ConfigFirewallServiceCategoryParameters;
import com.sobey.generate.firewall.ConfigSystemInterfaceParameter;
import com.sobey.generate.firewall.ConfigSystemInterfaceParameters;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.instance.BindingNetworkDevicePortGroupParameter;
import com.sobey.generate.instance.BindingPortGroupParameter;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.CreatePortGroupParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.VMDiskParameter;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.switches.SwitchPolicyParameter;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.zabbix.ZabbixSoapService;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	@Autowired
	private FirewallSoapService firewallSoapService;

	@Autowired
	private SwitchesSoapService switchesSoapService;

	@Autowired
	private InstanceSoapService instanceSoapService;

	@Autowired
	private StorageSoapService storageSoapService;

	@Autowired
	private LoadbalancerSoapService loadbalancerSoapService;

	@Autowired
	private DnsSoapService dnsSoapService;

	@Autowired
	private ZabbixSoapService zabbixSoapService;

	/**
	 * 默认的数据中心.
	 */
	private static final String datacenter = DataCenterEnum.成都核心数据中心.toString();

	@Override
	public WSResult createTenants(TenantsDTO tenantsDTO) {

		WSResult result = new WSResult();

		/**
		 * Step.1 在CMDB中创建Tenants
		 * 
		 * Step.2 在CMDB中为Tenants分配一个默认的Subnet
		 * 
		 */

		// Step.1 在CMDB中创建Tenants

		// 密钥生成:基于Base62编码的SecureRandom随机生成bytes.
		tenantsDTO.setAccessKey(Encodes.encodeBase64(Identities.randomBase62(16).getBytes()));
		cmdbuildSoapService.createTenants(tenantsDTO);

		// 获得创建的Tenants对象
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", tenantsDTO.getDescription());

		TenantsDTO queryTenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenantsByParams(
				CMDBuildUtil.wrapperSearchParams(map)).getDto();

		// Step.2 在CMDB中为Tenants分配一个默认的Subnet

		result = createSubnet(ConstansData.defaultSubnetDTO(queryTenantsDTO.getId()));

		if (!WSResult.SUCESS.equals(result.getCode())) {
			return result;
		}

		return result;
	}

	@Override
	public WSResult createSubnet(SubnetDTO subnetDTO) {

		WSResult result = new WSResult();

		/**
		 * 
		 * Step.1 将Subnet保存至CMDB中
		 * 
		 * Step.2 由Subnet生成Ipaddress,保存至CMDB中
		 * 
		 * Step.3 由Subnet生成ConfigFirewallAddress,保存至CMDB中
		 * 
		 * Step.4 将Subnet和firewall中的接口组绑定,关系保存至CMDB中(暂时未将配置信息写入firewall)
		 */

		// Step.1 将Subnet保存至CMDB中

		// 获得为每个子网分配一个从1开始递增的portId.portId主要和 防火墙-> 网络-> 接口中的名称对应,公网IP 8-9,子网为1-7
		int portId = cmdbuildSoapService.getMaxPolicyId(subnetDTO.getTenants());
		subnetDTO.setPortId(portId);
		subnetDTO.setPortIndex(cmdbuildSoapService.getMaxPortIndex(subnetDTO.getTenants()));

		cmdbuildSoapService.createSubnet(subnetDTO);

		// Step.2 由Subnet生成Ipaddress,保存至CMDB中

		// 获得创建的Subnet对象
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", subnetDTO.getDescription());
		map.put("EQ_tenants", subnetDTO.getTenants());
		SubnetDTO querySubnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnetByParams(
				CMDBuildUtil.wrapperSearchParams(map)).getDto();

		// 根据Subnet生成ipaddress List,并保存至CMDB
		List<IpaddressDTO> ipaddressDTOs = generatedIpaddressDTOs(querySubnetDTO);
		for (IpaddressDTO ipaddressDTO : ipaddressDTOs) {
			cmdbuildSoapService.createIpaddress(ipaddressDTO);
		}

		// Step.3 由Subnet生成ConfigFirewallAddress,保存至CMDB中

		ConfigFirewallAddressDTO configFirewallAddressDTO = new ConfigFirewallAddressDTO();

		configFirewallAddressDTO.setDescription(subnetDTO.getSegment());
		configFirewallAddressDTO.setPolicyId(cmdbuildSoapService.getMaxPolicyId(subnetDTO.getTenants()));
		configFirewallAddressDTO.setSubnet(querySubnetDTO.getId());
		configFirewallAddressDTO.setTenants(subnetDTO.getTenants());

		cmdbuildSoapService.createConfigFirewallAddress(configFirewallAddressDTO);

		// Step.4 将Subnet和firewall中的接口组绑定,关系保存至CMDB中(暂时未将配置信息写入firewall)

		ConfigSystemInterfaceDTO configSystemInterfaceDTO = new ConfigSystemInterfaceDTO();

		configSystemInterfaceDTO.setDescription(generateInterfaceName(portId));
		configSystemInterfaceDTO.setTenants(subnetDTO.getTenants());
		configSystemInterfaceDTO.setPortId(portId);
		configSystemInterfaceDTO.setSubnet(querySubnetDTO.getId());
		cmdbuildSoapService.createConfigSystemInterface(configSystemInterfaceDTO);

		return result;
	}

	/**
	 * 生成接口名( 防火墙-> 网络 ) , 1.电信 2.联通 3-9.子网 10.管理网段 <br>
	 * eg: port1
	 * 
	 * @param portId
	 * @return
	 */
	private String generateInterfaceName(int portId) {
		return "port" + portId;
	}

	/**
	 * 根据subnet生成IP
	 * 
	 * @param subnetDTO
	 * @return
	 */
	private List<IpaddressDTO> generatedIpaddressDTOs(SubnetDTO subnetDTO) {

		List<IpaddressDTO> ipaddressDTOs = new ArrayList<IpaddressDTO>();

		String prefixIP = StringUtils.substringBeforeLast(subnetDTO.getSegment(), ".");

		// IP从2-255 ,共254个IP
		for (int i = 2; i < 255; i++) {
			IpaddressDTO ipaddressDTO = new IpaddressDTO();
			ipaddressDTO.setGateway(subnetDTO.getGateway());
			ipaddressDTO.setIdc(subnetDTO.getIdc());

			ipaddressDTO.setIpAddressStatus(LookUpConstants.IPAddressStatus.未使用.getValue());
			ipaddressDTO.setIpAddressPool(LookUpConstants.IPAddressPool.PrivatePool.getValue());

			ipaddressDTO.setNetMask(subnetDTO.getNetMask());
			ipaddressDTO.setSegment(subnetDTO.getSegment());
			ipaddressDTO.setSubnet(subnetDTO.getId());
			ipaddressDTO.setDescription(prefixIP + "." + i);
			ipaddressDTOs.add(ipaddressDTO);
		}

		return ipaddressDTOs;
	}

	/**
	 * 生成vCenter中VM的名称.<br>
	 *
	 * VMWare中虚拟机名称不能重复,所以将VM名称设置为 "租户标识符-ip地址"
	 * 
	 * eg: Tenants-vMTpvWGq-10.10.100.1
	 * 
	 * @param tenantsDTO
	 * @param ipaddressDTO
	 * @return
	 */
	private String generateVMName(TenantsDTO tenantsDTO, IpaddressDTO ipaddressDTO) {
		return tenantsDTO.getCode() + "-" + ipaddressDTO.getDescription();
	}

	/**
	 * 生成vCenter中端口组的名称.<br>
	 *
	 * 端口组名称设置为 "租户标识符-VlanId",同一个Server中端口组名称和vlanId是唯一的.
	 * 
	 * eg: Tenants-vMTpvWGq-100
	 * 
	 * @param tenantsDTO
	 * @param ipaddressDTO
	 * @return
	 */
	private String generatVlanName(TenantsDTO tenantsDTO, Integer vlanId) {
		return tenantsDTO.getCode() + "-" + vlanId;
	}

	/**
	 * 获得Subnet未使用的IPAddress.
	 * 
	 * @param subnetDTO
	 * @return
	 */
	private IpaddressDTO findAvailableIPAddressDTO(SubnetDTO subnetDTO) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_subnet", subnetDTO.getId());
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue());

		List<Object> list = cmdbuildSoapService.getIpaddressList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		return (IpaddressDTO) list.get(0);
	}

	/**
	 * 获得专为防火墙分配的管理IP地址
	 * 
	 * @param subnetDTO
	 * @return
	 */
	private IpaddressDTO findAvailableManagerIPAddressDTO() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idc", ConstansData.idcId);
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue());
		map.put("EQ_ipaddressPool", LookUpConstants.IPAddressPool.ManagerPool.getValue());

		List<Object> list = cmdbuildSoapService.getIpaddressList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		return (IpaddressDTO) list.get(0);
	}

	/**
	 * 根据vCenter中Host的负载能力获得合适的Host(Server)
	 * 
	 * @param idcDTO
	 * @return
	 */
	private ServerDTO findSuitableServerDTO(IdcDTO idcDTO) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idc", idcDTO.getId());
		List<Object> list = cmdbuildSoapService.getServerList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		ServerDTO minimumLoadServerDTO = null;

		int size = Integer.MAX_VALUE;

		for (Object object : list) {

			ServerDTO serverDTO = (ServerDTO) object;

			HashMap<String, Object> ecsMap = new HashMap<String, Object>();
			ecsMap.put("EQ_server", serverDTO.getId());

			List<Object> ecsList = cmdbuildSoapService.getEcsList(CMDBuildUtil.wrapperSearchParams(ecsMap))
					.getDtoList().getDto();

			// 获得Server关联ECS数量最少的.
			if (size > ecsList.size()) {
				minimumLoadServerDTO = serverDTO;
				size = ecsList.size();
			}
		}

		return minimumLoadServerDTO;
	}

	/**
	 * 获得Vlan(端口组)
	 * 
	 * 每台Host的物理网卡上,一个Subnet应该只能对应一个Vlan.<br>
	 * 
	 * 如果Subnet在该Host物理网卡上没有Vlan,创建一个新的Vlan,并且Vlan的名称和Id应该唯一.
	 * 
	 * @param serverDTO
	 * @return
	 */
	private VlanDTO findSuitableVlanDTO(ServerDTO serverDTO, SubnetDTO subnetDTO) {

		HashMap<String, Object> niciMap = new HashMap<String, Object>();
		niciMap.put("EQ_server", serverDTO.getId());
		List<Object> nicList = cmdbuildSoapService.getNicList(CMDBuildUtil.wrapperSearchParams(niciMap)).getDtoList()
				.getDto();

		// 判断网卡是否为空
		if (!nicList.isEmpty()) {

			NicDTO nicDTO = (NicDTO) nicList.get(0);// 随机获得一个网卡,并默认Server上肯定有网卡

			HashMap<String, Object> vlanMap = new HashMap<String, Object>();
			vlanMap.put("EQ_nic", nicDTO.getId());
			vlanMap.put("EQ_subnet", subnetDTO.getId());
			VlanDTO vlanDTO = (VlanDTO) cmdbuildSoapService.findVlanByParams(CMDBuildUtil.wrapperSearchParams(vlanMap))
					.getDto();

			if (vlanDTO == null) {
				// 如果为null,表示subnet在该网卡上没有关联的端口组.创建一个新的Vlan.

				Integer vlanId = cmdbuildSoapService.getMaxVlanId(nicDTO.getId(), subnetDTO.getId());
				System.out.println(vlanId);

				TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(subnetDTO.getTenants()).getDto();

				String vlanName = generatVlanName(tenantsDTO, vlanId);

				VlanDTO insertVlanDTO = new VlanDTO();
				insertVlanDTO.setDescription(vlanName);
				insertVlanDTO.setIdc(subnetDTO.getIdc());
				insertVlanDTO.setNetMask(subnetDTO.getNetMask());
				insertVlanDTO.setNic(nicDTO.getId());
				insertVlanDTO.setTenants(subnetDTO.getTenants());
				insertVlanDTO.setSubnet(subnetDTO.getId());
				insertVlanDTO.setSegment(subnetDTO.getSegment());
				insertVlanDTO.setGateway(subnetDTO.getGateway());
				insertVlanDTO.setVlanId(vlanId);
				cmdbuildSoapService.createVlan(insertVlanDTO);

				// 在Host的网卡上创建端口组.
				CreatePortGroupParameter createPortGroupParameter = new CreatePortGroupParameter();
				createPortGroupParameter.setDatacenter(datacenter);
				createPortGroupParameter.setHostName(serverDTO.getDescription());
				createPortGroupParameter.setPortGroupName(vlanName);
				createPortGroupParameter.setVirtualSwitchName(nicDTO.getVirtualSwitchName());
				createPortGroupParameter.setVlanId(vlanId);
				instanceSoapService.createPortGroupInstance(createPortGroupParameter);

				HashMap<String, Object> queryVlanMap = new HashMap<String, Object>();
				queryVlanMap.put("EQ_nic", nicDTO.getId());
				queryVlanMap.put("EQ_subnet", subnetDTO.getId());
				queryVlanMap.put("EQ_description", vlanName);
				VlanDTO queryVlanDTO = (VlanDTO) cmdbuildSoapService.findVlanByParams(
						CMDBuildUtil.wrapperSearchParams(queryVlanMap)).getDto();

				return queryVlanDTO;
			}

			// subnet在该Nic上有关联的端口组(Vlan),直接返回VlanDTO对象.
			return vlanDTO;

		} else {
			// 如果网卡为null,抛出异常:静态数据异常.
			return null;
		}

	}

	@Override
	public WSResult createECS(EcsDTO ecsDTO) {

		WSResult result = new WSResult();

		/**
		 * 
		 * Step.1 获得Server
		 * 
		 * Step.2 创建ECS
		 * 
		 * Step.3 获得端口组Vlan
		 * 
		 * Step.4 绑定端口组
		 * 
		 * Step.5 在盛科交换机上创建策略
		 * 
		 * Step.6 保存至CMDB
		 * 
		 * Step.7 修改分配给VM的IP状态.
		 * 
		 */

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(ecsDTO.getSubnet()).getDto();
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(subnetDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(subnetDTO.getIdc()).getDto();

		// Step.1 获得Server

		ServerDTO serverDTO = findSuitableServerDTO(idcDTO);// 将ECS创建在负载最轻的Server上

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", serverDTO.getDescription());

		IpaddressDTO serverIP = (IpaddressDTO) cmdbuildSoapService.findIpaddressByParams(
				CMDBuildUtil.wrapperSearchParams(map)).getDto();

		IpaddressDTO ipaddressDTO = findAvailableIPAddressDTO(subnetDTO); // 从子网中选择一个IP.

		// Step.2 创建ECS
		String vmName = generateVMName(tenantsDTO, ipaddressDTO);

		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setDatacenter(datacenter);
		cloneVMParameter.setDescription(ecsDTO.getRemark());
		cloneVMParameter.setGateway(subnetDTO.getGateway());
		cloneVMParameter.setIpaddress(ipaddressDTO.getDescription());
		cloneVMParameter.setResourcePool(serverDTO.getResgroup());
		cloneVMParameter.setHostId(serverDTO.getHostgroup());
		cloneVMParameter.setSubNetMask(subnetDTO.getNetMask());
		cloneVMParameter.setVmName(vmName);
		cloneVMParameter.setVmTemplateName(ecsSpecDTO.getImageName());
		cloneVMParameter.setVmTemplateOS(ecsSpecDTO.getOsTypeText());

		instanceSoapService.cloneVMByInstance(cloneVMParameter);

		// Step.3 获得端口组Vlan
		VlanDTO vlanDTO = findSuitableVlanDTO(serverDTO, subnetDTO);

		// Step.4 绑定端口组

		BindingPortGroupParameter bindingPortGroupParameter = new BindingPortGroupParameter();
		bindingPortGroupParameter.setDatacenter(datacenter);
		bindingPortGroupParameter.setPortGroupName(vlanDTO.getDescription());
		bindingPortGroupParameter.setVmName(vmName);

		instanceSoapService.bindingPortGroupInstance(bindingPortGroupParameter);

		// Step.5 在盛科交换机上创建策略,为不同子网的通讯做配置,重要

		System.out.println(vlanDTO.getVlanId());
		SwitchPolicyParameter switchPolicyParameter = new SwitchPolicyParameter();
		switchPolicyParameter.setHostIp(serverIP.getDescription());
		switchPolicyParameter.setVlanId(vlanDTO.getVlanId());

		switchesSoapService.createPolicyInSwitch(switchPolicyParameter);

		// Step.6 保存至CMDB

		ecsDTO.setServer(serverDTO.getId());
		ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		ecsDTO.setEcsType(LookUpConstants.ECSType.instance.getValue());

		IdResult idResult = cmdbuildSoapService.createEcs(ecsDTO);

		// Step.7 修改分配给VM的IP状态.
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		result.setMessage(idResult.getMessage());
		return result;
	}

	@Override
	public WSResult destroyECS(Integer ecsId) {

		WSResult result = new WSResult();

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 关闭VM电源
		 * 
		 * Step.3 销毁虚拟机
		 * 
		 * Step.4 初始化虚拟机的IP状态
		 * 
		 * Step.5 删除zabbix中的监控
		 * 
		 * Step.6 CMDBuild中删除ECS信息
		 * 
		 */

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(ecsDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 关闭VM电源
		powerOpsECS(ecsId, PowerOperationEnum.poweroff.toString());

		// Step.3 销毁虚拟机
		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setDatacenter(idcDTO.getDescription());
		destroyVMParameter.setVmName(generateVMName(tenantsDTO, ipaddressDTO));
		instanceSoapService.destroyVMByInstance(destroyVMParameter);

		// Step.4 初始化虚拟机的IP状态
		cmdbuildSoapService.initIpaddress(ecsDTO.getIpaddress());

		// Step.5 删除zabbix中的监控
		deleteHost(ecsDTO);

		// Step.6 CMDBuild中删除ECS信息
		cmdbuildSoapService.deleteEcs(ecsId);

		return result;
	}

	/**
	 * 删除监控
	 * 
	 * @param ecsDTO
	 */
	private void deleteHost(EcsDTO ecsDTO) {
		zabbixSoapService.deleleHost(ecsDTO.getDescription());
	}

	@Override
	public WSResult powerOpsECS(Integer ecsId, String powerOperation) {

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 操作VM电源
		 * 
		 * Step.3 修改ECS的运行状态
		 * 
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(ecsDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 操作VM电源
		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVmName(generateVMName(tenantsDTO, ipaddressDTO));
		powerVMParameter.setPowerOperation(powerOperation);
		powerVMParameter.setDatacenter(idcDTO.getDescription());

		com.sobey.generate.instance.WSResult wsResult = instanceSoapService.powerVMByInstance(powerVMParameter);

		if (!WSResult.SUCESS.equals(wsResult.getCode())) {
			result.setError(wsResult.getCode(), wsResult.getMessage());
			return result;
		}

		// Step.3 修改ECS的运行状态
		if (PowerOperationEnum.poweroff.toString().equals(powerOperation)) {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.停止.getValue());
		} else {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		}

		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		result.setMessage("ECS电源操作成功");
		return result;
	}

	@Override
	public WSResult reconfigECS(Integer ecsId, Integer ecsSpecId) {

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 获得ECS Spec信息
		 * 
		 * Step.3 更新VM内存大小和CPU数量
		 * 
		 * Step.4 更新CMDBuild ECS规格
		 * 
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(ecsDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 获得ECS Spec信息
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsSpecId).getDto();

		// Step.3 更新VM内存大小和CPU数量

		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setCpuNumber(ecsSpecDTO.getCpuNumber());
		reconfigVMParameter.setDatacenter(idcDTO.getRemark());
		reconfigVMParameter.setMemoryMB(ecsSpecDTO.getMemory().longValue());
		reconfigVMParameter.setVmName(generateVMName(tenantsDTO, ipaddressDTO));

		com.sobey.generate.instance.WSResult wsResult = instanceSoapService.reconfigVMByInstance(reconfigVMParameter);

		if (!WSResult.SUCESS.equals(wsResult.getCode())) {
			result.setError(wsResult.getCode(), wsResult.getMessage());
			return result;
		}

		// Step.4 更新CMDBuild ECS规格
		ecsDTO.setEcsSpec(ecsSpecId);
		// ecsDTO.setEcsType(value);
		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		result.setMessage("ECS配置修改成功");
		return result;
	}

	@Override
	public WSResult createES3(Es3DTO es3DTO, Integer ecsId) {

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 为ECS分配存储空间
		 * 
		 * Step.3 写入CMDBuild
		 * 
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(es3DTO.getIdc()).getDto();

		// Step.2 为ECS分配存储空间
		VMDiskParameter vmDiskParameter = new VMDiskParameter();
		vmDiskParameter.setDatacenter(idcDTO.getDescription());
		vmDiskParameter.setDiskGB(es3DTO.getTotalSize());
		vmDiskParameter.setDiskName(es3DTO.getDescription());
		vmDiskParameter.setVmName(ecsDTO.getDescription());

		instanceSoapService.createVMDiskByInstance(vmDiskParameter);

		// Step.3 写入CMDBuild
		cmdbuildSoapService.createEs3(es3DTO);

		// ECS和ES3的关联关系
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", es3DTO.getDescription());

		Es3DTO queryEs3DTO = (Es3DTO) cmdbuildSoapService.findEs3ByParams(CMDBuildUtil.wrapperSearchParams(map))
				.getDto();
		cmdbuildSoapService.createMapEcsEs3(ecsId, queryEs3DTO.getId());

		return result;
	}

	@Override
	public WSResult deleteES3(Integer es3Id) {

		WSResult result = new WSResult();

		Es3DTO es3DTO = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(es3DTO.getIdc()).getDto();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", es3Id);
		List<Object> objects = cmdbuildSoapService.getMapEcsEs3List(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		for (Object object : objects) {
			MapEcsEs3DTO mapEcsEs3DTO = (MapEcsEs3DTO) object;

			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEs3DTO.getIdObj1())).getDto();

			// Step.2 为ECS分配存储空间
			VMDiskParameter vmDiskParameter = new VMDiskParameter();
			vmDiskParameter.setDatacenter(idcDTO.getDescription());
			vmDiskParameter.setDiskGB(es3DTO.getTotalSize());
			vmDiskParameter.setDiskName(es3DTO.getDescription());
			vmDiskParameter.setVmName(ecsDTO.getDescription());

			instanceSoapService.deleteVMDiskByInstance(vmDiskParameter);
		}

		return result;
	}

	@Override
	public WSResult createRouter(EcsDTO ecsDTO) {

		/**
		 * 
		 * Step.1 获得Server
		 * 
		 * Step.2 创建vRouter
		 * 
		 * Step.3 注册更新vRouter防火墙
		 * 
		 * Step.4 修改vRouter端口
		 * 
		 * Step.5 保存Ecs至CMDB
		 * 
		 * Step.6 保存Router至CMDB
		 * 
		 * Step.7 修改分配给Router的IP状态.
		 */

		WSResult result = new WSResult();

		// 从管理网段IP中获得的未使用的IP.
		IpaddressDTO managerIpaddressDTO = findAvailableManagerIPAddressDTO();
		System.out.println(managerIpaddressDTO.getDescription());

		// EcsSpec应该有router的规格(大中小),同理netscarler也应该有(最大连接数 5K,20k,40K,100K)
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(ecsDTO.getSubnet()).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(subnetDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(subnetDTO.getIdc()).getDto();

		// Step.1 获得Server
		ServerDTO serverDTO = findSuitableServerDTO(idcDTO);// 将ECS创建在负载最轻的Server上
		IpaddressDTO ipaddressDTO = findAvailableIPAddressDTO(subnetDTO); // 从子网中选择一个IP.

		// Step.2 创建vRouter
		String vmName = generateVMName(tenantsDTO, ipaddressDTO);

		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setDatacenter(datacenter);
		cloneVMParameter.setDescription(ecsDTO.getRemark());
		cloneVMParameter.setGateway(subnetDTO.getGateway());
		cloneVMParameter.setIpaddress(ipaddressDTO.getDescription());
		cloneVMParameter.setResourcePool(serverDTO.getResgroup());
		cloneVMParameter.setSubNetMask(subnetDTO.getNetMask());
		cloneVMParameter.setVmName(vmName);
		cloneVMParameter.setVmTemplateName(ecsSpecDTO.getImageName());
		cloneVMParameter.setVmTemplateOS(ecsSpecDTO.getOsTypeText());
		cloneVMParameter.setHostId(serverDTO.getHostgroup());

		instanceSoapService.cloneNetworkDeviceByInstance(cloneVMParameter);

		// Step.3 注册更新vRouter防火墙
		AuthenticateFirewallParameter authenticateFirewallParameter = new AuthenticateFirewallParameter();
		authenticateFirewallParameter.setUrl(ConstansData.vRouter_default_ipaddress);
		authenticateFirewallParameter.setUserName(ConstansData.firewall_username);
		authenticateFirewallParameter.setPassword(ConstansData.firewall_password);
		firewallSoapService.registeredByFirewall(authenticateFirewallParameter);

		// Step.4 修改vRouter端口
		modifyFirewallConfigSystemInterface(managerIpaddressDTO);// 修改防火墙中 系统管理 -> 网络 -> 接口 中的配置信息.

		// Step.5 保存Ecs至CMDB
		ecsDTO.setServer(serverDTO.getId());
		ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		ecsDTO.setEcsType(LookUpConstants.ECSType.firewall.getValue());

		cmdbuildSoapService.createEcs(ecsDTO);

		// Step.6 保存Router至CMDB
		HashMap<String, Object> ecsMap = new HashMap<String, Object>();
		ecsMap.put("EQ_ipaddress", ipaddressDTO.getId());
		EcsDTO queryEcsDTO = (EcsDTO) cmdbuildSoapService.findEcsByParams(CMDBuildUtil.wrapperSearchParams(ecsMap))
				.getDto();

		RouterDTO routerDTO = new RouterDTO();
		routerDTO.setDescription(ecsDTO.getDescription());
		routerDTO.setEcs(queryEcsDTO.getId());
		routerDTO.setIdc(idcDTO.getId());
		// routerDTO.setSubnet(subnetDTO.getId());
		routerDTO.setTenants(tenantsDTO.getId());
		routerDTO.setIpaddress(managerIpaddressDTO.getId());

		cmdbuildSoapService.createRouter(routerDTO);

		// Step.7 修改分配给Router的IP状态.
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());
		cmdbuildSoapService.allocateIpaddress(managerIpaddressDTO.getId());

		result.setMessage("Router创建成功");
		return result;
	}

	/**
	 * 修改防火墙中 网络 -> 接口 中的配置信息.<br>
	 * 
	 * 创建防火墙后,需要将防火墙port10的接口替换成未使用的管理网段,通过这样的修改能实现远程执行防火墙.
	 * 
	 * @param manangerSegment
	 * @return
	 */
	private WSResult modifyFirewallConfigSystemInterface(IpaddressDTO ipaddressDTO) {

		WSResult result = new WSResult();

		ConfigSystemInterfaceParameter configSystemInterfaceParameter = new ConfigSystemInterfaceParameter();
		configSystemInterfaceParameter.setGateway(ipaddressDTO.getDescription());
		configSystemInterfaceParameter.setUrl(ConstansData.vRouter_default_ipaddress);
		configSystemInterfaceParameter.setUserName(ConstansData.firewall_username);
		configSystemInterfaceParameter.setPassword(ConstansData.firewall_password);
		configSystemInterfaceParameter.setSubnetMask("255.255.254.0");

		// TODO "port10" 为防火墙->网络->接口名,写死.后续视情况决定是否抽象成常量.
		configSystemInterfaceParameter.setInterfaceName("port10");

		firewallSoapService.modifyConfigSystemInterfaceByFirewall(configSystemInterfaceParameter);

		return result;
	}

	@Override
	public WSResult bindingRouter(List<SubnetDTO> subnetDTOs, RouterDTO routerDTO) {

		/**
		 * 
		 * Step.1 将Subnet所属的vlan绑定到Router上.
		 * 
		 * Step.2 在vRouter上执行脚本(配置接口 config System Interface)
		 * 
		 * Step.3 在vRouter上执行脚本(配置地址段 config firewall address)
		 * 
		 * Step.4 在vRouter上执行脚本(配置子网策略 config firewall policy)
		 * 
		 * Step.5 Router和Subnet的关联关系保存至CMDB
		 * 
		 */

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(routerDTO.getTenants()).getDto();

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(routerDTO.getEcs()).getDto();

		// vRouter Ip
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// vRouter 管理IP
		IpaddressDTO managerDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(routerDTO.getIpaddress()).getDto();

		// Router所在的宿主机
		ServerDTO serverDTO = (ServerDTO) cmdbuildSoapService.findServer(ecsDTO.getServer()).getDto();

		String url = managerDTO.getDescription();

		// Step.1 将Subnet所属的vlan绑定到VM中的vRouter上.
		for (SubnetDTO subnetDTO : subnetDTOs) {

			// 获得网络适配器Id
			VlanDTO vlanDTO = findSuitableVlanDTO(serverDTO, subnetDTO);

			BindingNetworkDevicePortGroupParameter bindingNetworkDevicePortGroupParameter = new BindingNetworkDevicePortGroupParameter();

			bindingNetworkDevicePortGroupParameter.setDatacenter(datacenter);
			bindingNetworkDevicePortGroupParameter.setPortGroupName(vlanDTO.getDescription());
			bindingNetworkDevicePortGroupParameter.setVmName(generateVMName(tenantsDTO, ipaddressDTO));
			bindingNetworkDevicePortGroupParameter.setPortIndex(subnetDTO.getPortIndex());

			instanceSoapService.bindingNetworkDevicePortGroupInstance(bindingNetworkDevicePortGroupParameter);
		}

		// Config System Interface
		ConfigSystemInterfaceParameters interfaceParameters = new ConfigSystemInterfaceParameters();
		System.out.println(url);
		interfaceParameters.setUrl(url);
		interfaceParameters.setUserName(ConstansData.firewall_username);
		interfaceParameters.setPassword(ConstansData.firewall_password);

		ArrayList<ConfigSystemInterfaceParameter> interfaceArrayList = new ArrayList<ConfigSystemInterfaceParameter>();

		// Config Firewall Address
		ConfigFirewallAddressParameters addressParameters = new ConfigFirewallAddressParameters();
		addressParameters.setUrl(url);
		addressParameters.setUserName(ConstansData.firewall_username);
		addressParameters.setPassword(ConstansData.firewall_password);

		ArrayList<ConfigFirewallAddressParameter> addressArrayList = new ArrayList<ConfigFirewallAddressParameter>();

		// Config Firewall Policy
		ConfigFirewallPolicyParameters policyParameters = new ConfigFirewallPolicyParameters();
		policyParameters.setUrl(url);
		policyParameters.setUserName(ConstansData.firewall_username);
		policyParameters.setPassword(ConstansData.firewall_password);

		ArrayList<ConfigFirewallPolicyParameter> policyArrayList = new ArrayList<ConfigFirewallPolicyParameter>();

		for (SubnetDTO subnetDTO : subnetDTOs) {

			// Step.2 在vRouter上执行脚本(配置地址段 config firewall address)

			ConfigFirewallAddressParameter configFirewallAddressParameter = new ConfigFirewallAddressParameter();
			configFirewallAddressParameter.setSegment(subnetDTO.getSegment());
			configFirewallAddressParameter.setSubnetMask(subnetDTO.getNetMask());

			// 插入CMDB
			ConfigFirewallAddressDTO addressDTO = new ConfigFirewallAddressDTO();
			addressDTO.setSubnet(subnetDTO.getId());
			addressDTO.setTenants(tenantsDTO.getId());
			addressDTO.setPolicyId(cmdbuildSoapService.getMaxPolicyId(tenantsDTO.getId()));
			addressDTO.setDescription(subnetDTO.getSegment());
			cmdbuildSoapService.createConfigFirewallAddress(addressDTO);

			addressArrayList.add(configFirewallAddressParameter);

			// Step.3 在vRouter上执行脚本(配置接口 config System Interface,每个Subnet都有一个.)
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("EQ_subnet", subnetDTO.getId());

			ConfigSystemInterfaceDTO configSystemInterfaceDTO = (ConfigSystemInterfaceDTO) cmdbuildSoapService
					.findConfigSystemInterfaceByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();

			ConfigSystemInterfaceParameter configSystemInterfaceParameter = new ConfigSystemInterfaceParameter();
			configSystemInterfaceParameter.setGateway(subnetDTO.getGateway());
			configSystemInterfaceParameter.setInterfaceName(configSystemInterfaceDTO.getDescription());
			configSystemInterfaceParameter.setSubnetMask(subnetDTO.getNetMask());

			interfaceArrayList.add(configSystemInterfaceParameter);

			// Step.3 在vRouter上执行脚本(配置子网策略 config firewall policy)

			// new 一个新的list出来,将传递进来的list填充进去,并将循环中的自身对象remove出去,这样就达到源对应多个目标的目的.
			ArrayList<SubnetDTO> arrayList = new ArrayList<SubnetDTO>();
			arrayList.addAll(subnetDTOs);
			arrayList.remove(subnetDTO);

			for (SubnetDTO dto : arrayList) {

				// 将子网之间通讯的防火墙策略保存至CMDB
				createPolicyDTO(tenantsDTO, subnetDTO, dto);

				ConfigFirewallPolicyParameter configFirewallPolicyParameter = new ConfigFirewallPolicyParameter();
				configFirewallPolicyParameter.setPolicyId(cmdbuildSoapService.getMaxPolicyId(tenantsDTO.getId()));
				configFirewallPolicyParameter.setPolicyType("Subnet");

				configFirewallPolicyParameter.setSrcintf("port" + subnetDTO.getPortId());
				configFirewallPolicyParameter.setDstintf("port" + dto.getPortId());
				configFirewallPolicyParameter.setSrcaddr(subnetDTO.getSegment());
				configFirewallPolicyParameter.setDstaddr(dto.getSegment());

				policyArrayList.add(configFirewallPolicyParameter);
			}

			// Step.4 Router和Subnet的关联关系保存至CMDB
			// TODO 两者关联关系要报错.mark.
			// subnetDTO.setRouter(routerDTO.getId());
			// cmdbuildSoapService.updateSubnet(subnetDTO.getId(), subnetDTO);

		}

		addressParameters.getConfigFirewallAddressParameters().addAll(addressArrayList);
		firewallSoapService.configFirewallAddressParameterListByFirewall(addressParameters);

		interfaceParameters.getConfigSystemInterfaceParameters().addAll(interfaceArrayList);
		firewallSoapService.configSystemInterfaceListByFirewall(interfaceParameters);

		policyParameters.getConfigFirewallPolicyParameters().addAll(policyArrayList);
		firewallSoapService.configFirewallPolicyParameterListByFirewall(policyParameters);

		return result;
	}

	/**
	 * 将子网之间通讯的防火墙策略保存至CMDB
	 * 
	 * @param tenantsDTO
	 * @param srcSubnetDTO
	 *            源
	 * @param dstSubnetDTO
	 *            目标
	 */
	private void createPolicyDTO(TenantsDTO tenantsDTO, SubnetDTO srcSubnetDTO, SubnetDTO dstSubnetDTO) {

		String srcintf = "port" + srcSubnetDTO.getPortId();
		String dstintf = "port" + dstSubnetDTO.getPortId();

		ConfigFirewallPolicyDTO configFirewallPolicyDTO = new ConfigFirewallPolicyDTO();
		configFirewallPolicyDTO.setTenants(tenantsDTO.getId());
		configFirewallPolicyDTO.setSrcintf(srcintf);
		configFirewallPolicyDTO.setDstintf(dstintf);
		configFirewallPolicyDTO.setSrcaddr(srcSubnetDTO.getSegment());
		configFirewallPolicyDTO.setDstaddr(dstSubnetDTO.getSegment());
		configFirewallPolicyDTO.setPolicyId(cmdbuildSoapService.getMaxPolicyId(tenantsDTO.getId()));
		configFirewallPolicyDTO.setDescription(srcintf + "-" + dstintf);
		cmdbuildSoapService.createConfigFirewallPolicy(configFirewallPolicyDTO);

	}

	/**
	 * 配置静态路由<br>
	 * 
	 * TODO 静态路由中很多参数是由运维人员指定,数量不一定.前期暂时写死,后期静态路由需有个单独的表,方便对静态路由表进行维护.
	 * 
	 * @param firewallServiceDTO
	 */
	private void createConfigRouterStatic(FirewallServiceDTO firewallServiceDTO) {

		ConfigRouterStaticDTO configRouterStaticDTO = new ConfigRouterStaticDTO();

		configRouterStaticDTO.setFirewallService(firewallServiceDTO.getId());
		configRouterStaticDTO.setTenants(firewallServiceDTO.getTenants());
		configRouterStaticDTO.setRouterId(cmdbuildSoapService.getMaxRouterId(firewallServiceDTO.getTenants()));
		configRouterStaticDTO.setIsp(LookUpConstants.ISP.中国电信.getValue());
		configRouterStaticDTO.setSegment("221.237.156.1");
		cmdbuildSoapService.createConfigRouterStatic(configRouterStaticDTO);
	}

	@Override
	public WSResult createFirewallService(FirewallServiceDTO firewallServiceDTO,
			List<ConfigFirewallServiceCategoryDTO> categoryDTOs) {

		/**
		 * Step.1 创建FirewallService
		 * 
		 * Step.2 将防火墙具体策略ConfigFirewallServiceCategory和FirewallService关联
		 * 
		 */

		// 下行规则(从外部访问云资源)
		// 协议,行为,起始端口,结束端口,目标IP

		// 上行规则(从云资源访问外部)
		// 协议,行为,起始端口,结束端口,目标IP

		WSResult result = new WSResult();

		firewallServiceDTO = new FirewallServiceDTO();
		firewallServiceDTO.setIdc(ConstansData.idcId);
		firewallServiceDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		cmdbuildSoapService.createFirewallService(firewallServiceDTO);

		HashMap<String, Object> firewallServiceMap = new HashMap<String, Object>();
		firewallServiceMap.put("EQ_tenants", firewallServiceDTO.getTenants());
		firewallServiceMap.put("EQ_description", firewallServiceDTO.getDescription());

		FirewallServiceDTO queryFirewallServiceDTO = (FirewallServiceDTO) cmdbuildSoapService
				.findFirewallServiceByParams(CMDBuildUtil.wrapperSearchParams(firewallServiceMap)).getDto();

		for (ConfigFirewallServiceCategoryDTO configFirewallServiceCategoryDTO : categoryDTOs) {
			configFirewallServiceCategoryDTO.setFirewallService(queryFirewallServiceDTO.getId());
			cmdbuildSoapService.createconfigFirewallServiceCategory(configFirewallServiceCategoryDTO);
		}

		return result;
	}

	@Override
	public WSResult bindingFirewallService(RouterDTO routerDTO, List<FirewallServiceDTO> firewallServiceDTOs) {

		WSResult wsResult = new WSResult();

		/**
		 * 
		 * Step.1 将防火墙策略写入vRouter中
		 * 
		 * Step.2 将防火墙策略和vRouter关联关系更新至CMDB中
		 * 
		 */

		// Step.1 将防火墙策略写入vRouter中
		// 查询出防火墙策略(FirewallService 对应的 ConfigFirewallServiceCategory)
		// 将ConfigFirewallServiceCategory进行封装,远程执行.

		for (FirewallServiceDTO firewallServiceDTO : firewallServiceDTOs) {

			// 防火墙下的具体策略.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("EQ_firewallService", firewallServiceDTO.getId());
			List<Object> list = cmdbuildSoapService
					.getconfigFirewallServiceCategoryList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto();

			List<ConfigFirewallServiceCategoryDTO> categoryDTOs = new ArrayList<ConfigFirewallServiceCategoryDTO>();

			for (Object object : list) {
				ConfigFirewallServiceCategoryDTO categoryDTO = (ConfigFirewallServiceCategoryDTO) object;
				categoryDTOs.add(categoryDTO);
			}

			ConfigFirewallServiceCategoryParameters categoryParameters = wrapperConfigFirewallServiceCategoryParameter(
					categoryDTOs, routerDTO);
			firewallSoapService.configFirewallServiceCategoryParameterListByFirewall(categoryParameters);

		}

		return wsResult;
	}

	/**
	 * 将ConfigFirewallServiceCategoryDTO 封装成 ConfigFirewallServiceCategoryParameters
	 * 
	 * @return
	 */
	private ConfigFirewallServiceCategoryParameters wrapperConfigFirewallServiceCategoryParameter(
			List<ConfigFirewallServiceCategoryDTO> categoryDTOs, RouterDTO routerDTO) {

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(routerDTO.getEcs()).getDto();
		// Router IP地址
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		String categoryName = "";

		// Router下所有的Subnet
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_router", routerDTO.getId());
		List<Object> subnetObjects = cmdbuildSoapService.getSubnetList(CMDBuildUtil.wrapperSearchParams(map))
				.getDtoList().getDto();

		ArrayList<ConfigFirewallServiceCategoryParameter> arrayList = new ArrayList<ConfigFirewallServiceCategoryParameter>();

		for (ConfigFirewallServiceCategoryDTO dto : categoryDTOs) {

			for (Object object : subnetObjects) {

				SubnetDTO subnetDTO = (SubnetDTO) object;

				ConfigFirewallServiceCategoryParameter CTCParameter = new ConfigFirewallServiceCategoryParameter();
				ConfigFirewallServiceCategoryParameter CNCParameter = new ConfigFirewallServiceCategoryParameter();

				if (StringUtils.equalsIgnoreCase("上行", dto.getDirection())) {

					// 上行规则(从云资源访问外部)

					CTCParameter.setUrl(ipaddressDTO.getDescription());
					CTCParameter.setUserName(ConstansData.firewall_username);
					CTCParameter.setPassword(ConstansData.firewall_password);
					CTCParameter.setAction(dto.getAction());// allow & deny
					CTCParameter.setCategoryName(categoryName);
					CTCParameter.setPortrange(dto.getStartPort() + "-" + dto.getEndPort());
					CTCParameter.setPolicyId(cmdbuildSoapService.getMaxPolicyId(routerDTO.getTenants()));
					CTCParameter.setSrcintf(getInterfaceNameBySubnet(subnetDTO));
					CTCParameter.setDstintf("port1");
					CTCParameter.setSrcaddr(subnetDTO.getSegment());
					CTCParameter.setDstaddr("221.237.156.161");// 电信

					CNCParameter.setUrl(ipaddressDTO.getDescription());
					CNCParameter.setUserName(ConstansData.firewall_username);
					CNCParameter.setPassword(ConstansData.firewall_password);
					CNCParameter.setAction(dto.getAction());// allow & deny
					CNCParameter.setCategoryName(categoryName);
					CNCParameter.setPortrange(dto.getStartPort() + "-" + dto.getEndPort());
					CNCParameter.setPolicyId(cmdbuildSoapService.getMaxPolicyId(routerDTO.getTenants()));
					CNCParameter.setSrcintf(getInterfaceNameBySubnet(subnetDTO));
					CNCParameter.setDstintf("port2");
					CNCParameter.setSrcaddr(subnetDTO.getSegment());
					CNCParameter.setDstaddr("119.6.200.207");// 联通

				} else {

					// 下行规则(从外部访问云资源)

					CTCParameter.setUrl(ipaddressDTO.getDescription());
					CTCParameter.setUserName(ConstansData.firewall_username);
					CTCParameter.setPassword(ConstansData.firewall_password);
					CTCParameter.setAction(dto.getAction());// allow & deny
					CTCParameter.setCategoryName(categoryName);
					CTCParameter.setPortrange(dto.getStartPort() + "-" + dto.getEndPort());
					CTCParameter.setPolicyId(cmdbuildSoapService.getMaxPolicyId(routerDTO.getTenants()));

					CTCParameter.setSrcintf("port1");
					CTCParameter.setDstintf(getInterfaceNameBySubnet(subnetDTO));
					CTCParameter.setSrcaddr("221.237.156.161");
					CTCParameter.setDstaddr(subnetDTO.getSegment());// 电信

					CNCParameter.setUrl(ipaddressDTO.getDescription());
					CNCParameter.setUserName(ConstansData.firewall_username);
					CNCParameter.setPassword(ConstansData.firewall_password);
					CNCParameter.setAction(dto.getAction());// allow & deny
					CNCParameter.setCategoryName(categoryName);
					CNCParameter.setPortrange(dto.getStartPort() + "-" + dto.getEndPort());
					CNCParameter.setPolicyId(cmdbuildSoapService.getMaxPolicyId(routerDTO.getTenants()));
					CNCParameter.setSrcintf("port2");
					CNCParameter.setDstintf(getInterfaceNameBySubnet(subnetDTO));
					CNCParameter.setSrcaddr("119.6.200.207");
					CNCParameter.setDstaddr(subnetDTO.getSegment());// 联通
				}

				arrayList.add(CNCParameter);
				arrayList.add(CTCParameter);
			}
		}

		ConfigFirewallServiceCategoryParameters categoryParameters = new ConfigFirewallServiceCategoryParameters();

		categoryParameters.getConfigFirewallServiceCategoryParameters().addAll(arrayList);

		return categoryParameters;

	}

	/**
	 * 获得IP或网段对应的接口名称(防火墙 -> 网络 -> 接口)
	 * 
	 * @param subnetDTO
	 *            子网
	 * @return
	 */
	private String getInterfaceNameBySubnet(SubnetDTO subnetDTO) {

		HashMap<String, Object> interfaceMap = new HashMap<String, Object>();
		interfaceMap.put("EQ_subnet", subnetDTO.getId());
		List<Object> interfaceObjects = cmdbuildSoapService
				.getConfigSystemInterfaceList(CMDBuildUtil.wrapperSearchParams(interfaceMap)).getDtoList().getDto();

		// Subnet对应的interface对象. eg port1,port2
		ConfigSystemInterfaceDTO interfaceDTO = (ConfigSystemInterfaceDTO) interfaceObjects.get(0);

		return interfaceDTO.getDescription();
	}

	@Override
	public WSResult applyEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult recoverEIP(Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingEIP(Integer eipId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingEIP(Integer eipId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, String[] eipIdsArray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteDNS(Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

}
