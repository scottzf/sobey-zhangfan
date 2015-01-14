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
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.NicDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DNSPolicyParameter;
import com.sobey.generate.dns.DNSPublicIPParameter;
import com.sobey.generate.firewall.AuthenticateFirewallParameter;
import com.sobey.generate.firewall.ConfigFirewallAddressParameter;
import com.sobey.generate.firewall.ConfigFirewallAddressParameters;
import com.sobey.generate.firewall.ConfigFirewallPolicyParameter;
import com.sobey.generate.firewall.ConfigFirewallPolicyParameters;
import com.sobey.generate.firewall.ConfigSystemInterfaceParameter;
import com.sobey.generate.firewall.ConfigSystemInterfaceParameters;
import com.sobey.generate.firewall.EIPParameter;
import com.sobey.generate.firewall.EIPPolicyParameter;
import com.sobey.generate.instance.BindingNetworkDevicePortGroupParameter;
import com.sobey.generate.instance.BindingPortGroupParameter;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.CreatePortGroupParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.VMDiskParameter;
import com.sobey.generate.switches.SwitchPolicyParameter;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	// @Autowired
	// private FirewallSoapService firewallSoapService;
	//
	// @Autowired
	// private SwitchesSoapService switchesSoapService;
	//
	// @Autowired
	// private InstanceSoapService instanceSoapService;
	//
	// @Autowired
	// private StorageSoapService storageSoapService;
	//
	// @Autowired
	// private LoadbalancerSoapService loadbalancerSoapService;
	//
	// @Autowired
	// private DnsSoapService dnsSoapService;
	//
	// @Autowired
	// private ZabbixSoapService zabbixSoapService;

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
		IdResult idResult = cmdbuildSoapService.createTenants(tenantsDTO);

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

		result.setMessage(idResult.getMessage());

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

		IdResult idResult = cmdbuildSoapService.createSubnet(subnetDTO);

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

		// TODO 应该可以不用,待后续流程review时再做决定.
		// Step.3 由Subnet生成ConfigFirewallAddress,保存至CMDB中

		// ConfigFirewallAddressDTO configFirewallAddressDTO = new ConfigFirewallAddressDTO();
		// configFirewallAddressDTO.setDescription(subnetDTO.getSegment());
		// configFirewallAddressDTO.setPolicyId(cmdbuildSoapService.getMaxPolicyId(subnetDTO.getTenants()));
		// configFirewallAddressDTO.setSubnet(querySubnetDTO.getId());
		// configFirewallAddressDTO.setTenants(subnetDTO.getTenants());
		// cmdbuildSoapService.createConfigFirewallAddress(configFirewallAddressDTO);

		// Step.4 将Subnet和firewall中的接口组绑定,关系保存至CMDB中(暂时未将配置信息写入firewall)

		ConfigSystemInterfaceDTO configSystemInterfaceDTO = new ConfigSystemInterfaceDTO();

		configSystemInterfaceDTO.setDescription(generateInterfaceName(portId));
		configSystemInterfaceDTO.setTenants(subnetDTO.getTenants());
		configSystemInterfaceDTO.setPortId(portId);
		configSystemInterfaceDTO.setSubnet(querySubnetDTO.getId());
		cmdbuildSoapService.createConfigSystemInterface(configSystemInterfaceDTO);

		result.setMessage(idResult.getMessage());

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
				// instanceSoapService.createPortGroupInstance(createPortGroupParameter);

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
		 * Step.5 保存至CMDB
		 * 
		 * Step.6 修改分配给VM的IP状态.
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

		// instanceSoapService.cloneVMByInstance(cloneVMParameter);

		// Step.3 获得端口组Vlan
		VlanDTO vlanDTO = findSuitableVlanDTO(serverDTO, subnetDTO);

		// Step.4 绑定端口组

		BindingPortGroupParameter bindingPortGroupParameter = new BindingPortGroupParameter();
		bindingPortGroupParameter.setDatacenter(datacenter);
		bindingPortGroupParameter.setPortGroupName(vlanDTO.getDescription());
		bindingPortGroupParameter.setVmName(vmName);

		// instanceSoapService.bindingPortGroupInstance(bindingPortGroupParameter);

		// Step.5 保存至CMDB

		ecsDTO.setServer(serverDTO.getId());
		ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		ecsDTO.setEcsType(LookUpConstants.ECSType.instance.getValue());

		// Step.6 修改分配给VM的IP状态.
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		IdResult idResult = cmdbuildSoapService.createEcs(ecsDTO);

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
		// instanceSoapService.destroyVMByInstance(destroyVMParameter);

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
		// zabbixSoapService.deleleHost(ecsDTO.getDescription());
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

		// com.sobey.generate.instance.WSResult wsResult = instanceSoapService.powerVMByInstance(powerVMParameter);

		// if (!WSResult.SUCESS.equals(wsResult.getCode())) {
		// result.setError(wsResult.getCode(), wsResult.getMessage());
		// return result;
		// }

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

		// com.sobey.generate.instance.WSResult wsResult =
		// instanceSoapService.reconfigVMByInstance(reconfigVMParameter);

		// if (!WSResult.SUCESS.equals(wsResult.getCode())) {
		// result.setError(wsResult.getCode(), wsResult.getMessage());
		// return result;
		// }

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

		// instanceSoapService.createVMDiskByInstance(vmDiskParameter);

		// Step.3 写入CMDBuild
		IdResult idResult = cmdbuildSoapService.createEs3(es3DTO);

		// ECS和ES3的关联关系
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", es3DTO.getDescription());

		Es3DTO queryEs3DTO = (Es3DTO) cmdbuildSoapService.findEs3ByParams(CMDBuildUtil.wrapperSearchParams(map))
				.getDto();
		cmdbuildSoapService.createMapEcsEs3(ecsId, queryEs3DTO.getId());

		cmdbuildSoapService.createEcs(ecsDTO);

		result.setMessage(idResult.getMessage());

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

			// instanceSoapService.deleteVMDiskByInstance(vmDiskParameter);
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

		// instanceSoapService.cloneNetworkDeviceByInstance(cloneVMParameter);

		// Step.3 注册更新vRouter防火墙
		AuthenticateFirewallParameter authenticateFirewallParameter = new AuthenticateFirewallParameter();
		authenticateFirewallParameter.setUrl(ConstansData.vRouter_default_ipaddress);
		authenticateFirewallParameter.setUserName(ConstansData.firewall_username);
		authenticateFirewallParameter.setPassword(ConstansData.firewall_password);
		// firewallSoapService.registeredByFirewall(authenticateFirewallParameter);

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

		IdResult idResult = cmdbuildSoapService.createRouter(routerDTO);

		// Step.7 修改分配给Router的IP状态.
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());
		cmdbuildSoapService.allocateIpaddress(managerIpaddressDTO.getId());

		result.setMessage(idResult.getMessage());
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

		// firewallSoapService.modifyConfigSystemInterfaceByFirewall(configSystemInterfaceParameter);

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
		 * Step.5 在盛科交换机上创建策略,为不同子网的通讯做配置,重要
		 * 
		 */

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(routerDTO.getTenants()).getDto();

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(routerDTO.getEcs()).getDto();

		// vRouter Ip
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// vRouter 管理IP
		IpaddressDTO managerDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(routerDTO.getIpaddress()).getDto();
		String url = managerDTO.getDescription();

		// Router所在的宿主机
		ServerDTO serverDTO = (ServerDTO) cmdbuildSoapService.findServer(ecsDTO.getServer()).getDto();
		IpaddressDTO serverIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(routerDTO.getIpaddress()).getDto();

		// Step.1 将Subnet所属的vlan绑定到VM中的vRouter上.
		for (SubnetDTO subnetDTO : subnetDTOs) {

			// 获得网络适配器Id
			VlanDTO vlanDTO = findSuitableVlanDTO(serverDTO, subnetDTO);

			BindingNetworkDevicePortGroupParameter bindingNetworkDevicePortGroupParameter = new BindingNetworkDevicePortGroupParameter();

			bindingNetworkDevicePortGroupParameter.setDatacenter(datacenter);
			bindingNetworkDevicePortGroupParameter.setPortGroupName(vlanDTO.getDescription());
			bindingNetworkDevicePortGroupParameter.setVmName(generateVMName(tenantsDTO, ipaddressDTO));
			bindingNetworkDevicePortGroupParameter.setPortIndex(subnetDTO.getPortIndex());

			// instanceSoapService.bindingNetworkDevicePortGroupInstance(bindingNetworkDevicePortGroupParameter);
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

			// 获得子网对应的VlanId
			HashMap<String, Object> vlanMap = new HashMap<String, Object>();
			vlanMap.put("EQ_subnet", subnetDTO.getId());
			VlanDTO vlanDTO = (VlanDTO) cmdbuildSoapService.findVlanByParams(CMDBuildUtil.wrapperSearchParams(vlanMap))
					.getDto();

			// Step.5 在盛科交换机上创建策略,为不同子网的通讯做配置,重要
			SwitchPolicyParameter switchPolicyParameter = new SwitchPolicyParameter();
			switchPolicyParameter.setHostIp(serverIP.getDescription());
			switchPolicyParameter.setVlanId(vlanDTO.getId());
			// switchesSoapService.createPolicyInSwitch(switchPolicyParameter);

		}

		addressParameters.getConfigFirewallAddressParameters().addAll(addressArrayList);
		// firewallSoapService.configFirewallAddressParameterListByFirewall(addressParameters);

		interfaceParameters.getConfigSystemInterfaceParameters().addAll(interfaceArrayList);
		// firewallSoapService.configSystemInterfaceListByFirewall(interfaceParameters);

		policyParameters.getConfigFirewallPolicyParameters().addAll(policyArrayList);
		// firewallSoapService.configFirewallPolicyParameterListByFirewall(policyParameters);

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
		IdResult idResult = cmdbuildSoapService.createFirewallService(firewallServiceDTO);

		// 获得防火墙对象
		HashMap<String, Object> firewallServiceMap = new HashMap<String, Object>();
		firewallServiceMap.put("EQ_tenants", firewallServiceDTO.getTenants());
		firewallServiceMap.put("EQ_description", firewallServiceDTO.getDescription());
		FirewallServiceDTO queryFirewallServiceDTO = (FirewallServiceDTO) cmdbuildSoapService
				.findFirewallServiceByParams(CMDBuildUtil.wrapperSearchParams(firewallServiceMap)).getDto();

		// 保存防火墙对象至CMDB中
		for (ConfigFirewallServiceCategoryDTO configFirewallServiceCategoryDTO : categoryDTOs) {
			configFirewallServiceCategoryDTO.setFirewallService(queryFirewallServiceDTO.getId());
			cmdbuildSoapService.createconfigFirewallServiceCategory(configFirewallServiceCategoryDTO);
		}

		result.setMessage(idResult.getMessage());
		return result;
	}

	@Override
	public WSResult bindingFirewallService(RouterDTO routerDTO, FirewallServiceDTO firewallServiceDTO) {

		WSResult wsResult = new WSResult();

		routerDTO.setFirewallService(firewallServiceDTO.getId());

		return wsResult;
	}

	@Override
	public WSResult applyEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {

		WSResult result = new WSResult();

		// Step.1 获得未使用的公网IP.

		IpaddressDTO ipaddressDTO = getPublicIpaddress(eipDTO.getIsp());

		if (ipaddressDTO == null) {
			result.setError(WSResult.SYSTEM_ERROR, "公网IP资源不足,请联系管理员.");
			return result;
		}

		// Step.2 将EIP信息写入CMDBuild
		eipDTO.setIdc(ipaddressDTO.getIdc());
		eipDTO.setIpaddress(ipaddressDTO.getId());
		eipDTO.setDescription(ipaddressDTO.getDescription());
		eipDTO.setEipStatus(LookUpConstants.EIPStatus.已使用.getValue());
		IdResult idResult = cmdbuildSoapService.createEip(eipDTO);

		// 获得防火墙对象
		HashMap<String, Object> ipMap = new HashMap<String, Object>();
		ipMap.put("EQ_tenants", eipDTO.getTenants());
		ipMap.put("EQ_ipaddress", eipDTO.getIpaddress());
		EipDTO queryEipDTO = (EipDTO) cmdbuildSoapService.findEipByParams(CMDBuildUtil.wrapperSearchParams(ipMap))
				.getDto();

		// Step.3 将EIP端口信息写入CMDBuild

		for (EipPolicyDTO policyDTO : eipPolicyDTOs) {
			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getEipProtocol()).getDto();
			policyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getSourcePort() + "-"
					+ policyDTO.getTargetPort());
			policyDTO.setEip(queryEipDTO.getId());
			policyDTO.setEipProtocol(policyDTO.getEipProtocol());
			policyDTO.setSourcePort(policyDTO.getSourcePort());
			policyDTO.setTargetPort(policyDTO.getTargetPort());
			cmdbuildSoapService.createEipPolicy(policyDTO);
		}

		// Step.4 更改EIP的状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		result.setMessage(idResult.getMessage());
		return result;
	}

	/**
	 * 获得未使用的公网IP
	 *
	 * @param ispId
	 * @return
	 */
	private IpaddressDTO getPublicIpaddress(Integer ispId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue());
		map.put("EQ_ipaddressPool", LookUpConstants.IPAddressPool.InternetPool.getValue());
		map.put("EQ_isp", ispId);
		return (IpaddressDTO) cmdbuildSoapService.findIpaddressByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	@Override
	public WSResult recoverEIP(Integer eipId) {

		WSResult result = new WSResult();

		/**
		 * Step.1 获得EIP.
		 *
		 * Step.2 初始化公网IP
		 *
		 * Step.3 查询EIP下所有policy并删除
		 *
		 * Step.4 删除EIP
		 *
		 * Step.5 写入日志
		 */
		// Step.1 获得EIP.
		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();
		// Step.2 初始化公网IP
		cmdbuildSoapService.initIpaddress(eipDTO.getIpaddress());
		// Step.3 查询EIP下所有policy并删除

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_eip", eipId);
		List<Object> list = cmdbuildSoapService.getEipPolicyList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		for (Object obj : list) {
			EipPolicyDTO policyDTO = (EipPolicyDTO) obj;
			cmdbuildSoapService.deleteEipPolicy(policyDTO.getId());
		}
		// Step.4 删除EIP
		cmdbuildSoapService.deleteEip(eipId);

		return result;
	}

	@Override
	public WSResult bindingEIP(EipDTO eipDTO, ServiceDTO serviceDTO) {

		WSResult result = new WSResult();
		// Step.1 获得EIP、ECS、ELB的信息

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(serviceDTO.getId()).getDto();

		// Step.2 与其他资源(ECS & ELB)建立关联关系
		// 因为可能绑定ELB或ECS,无法区分.但是ECS和ELB同属一个service,id是不可能重复的,所以先通过ID查询,判断对象是否为null,如果不为null说明绑定的是该服务对象.
		cmdbuildSoapService.createMapEcsEip(ecsDTO.getId(), eipDTO.getId());

		// Step.3 firwall创建虚拟IP

		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
//		eipParameter.setPrivateIP(ecsDTO.getIpaddressDTO().getDescription());
		// if (!WSResult.SUCESS.equals(firewallSoapService.createEIPByFirewall(eipParameter).getCode())) {
		// // 删除关联关系
		// cmdbuildSoapService.deleteMapEcsEip(serviceDTO.getId(), eipDTO.getId());
		// result.setError(WSResult.SYSTEM_ERROR, "EIP关联失败,请联系管理员.");
		// return result;
		// }
		result.setMessage("EIP关联成功");
		return result;

	}

	/**
	 * EipDTO -> EIPParameter
	 *
	 * @param eipDTO
	 * @return
	 */
	private EIPParameter wrapperEIPParameter(EipDTO eipDTO) {
		LookUpDTO isp = (LookUpDTO) cmdbuildSoapService.findLookUp(eipDTO.getIsp()).getDto();
		// 获得租户下所有的EIP.

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", eipDTO.getTenants());
		List<Object> eipList = cmdbuildSoapService.getEipList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		List<String> allPolicies = new ArrayList<String>();
		for (Object obj : eipList) {
			EipDTO dto = (EipDTO) obj;
			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(dto.getIpaddress()).getDto();
			allPolicies.add(ipaddressDTO.getDescription());
		}

		// 获得EIP的策略
		List<EIPPolicyParameter> policyParameters = new ArrayList<EIPPolicyParameter>();

		HashMap<String, Object> policyMap = new HashMap<String, Object>();
		policyMap.put("EQ_eip", eipDTO.getId());
		List<Object> eipPolicies = cmdbuildSoapService.getEipPolicyList(CMDBuildUtil.wrapperSearchParams(policyMap))
				.getDtoList().getDto();

		for (Object obj : eipPolicies) {
			EipPolicyDTO dto = (EipPolicyDTO) obj;
			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(dto.getEipProtocol()).getDto();
			EIPPolicyParameter policyParameter = new EIPPolicyParameter();
			policyParameter.setProtocolText(lookUpDTO.getDescription());
			policyParameter.setSourcePort(dto.getSourcePort());
			policyParameter.setTargetPort(dto.getTargetPort());
			policyParameters.add(policyParameter);
		}
		EIPParameter eipParameter = new EIPParameter();
		eipParameter.setInternetIP(eipDTO.getIpaddressDTO().getDescription());
		eipParameter.setIsp(isp.getId());
		eipParameter.getPolicies().addAll(policyParameters);
		eipParameter.getAllPolicies().addAll(allPolicies);
		return eipParameter;
	}

	@Override
	public WSResult unbindingEIP(EipDTO eipDTO, ServiceDTO serviceDTO) {

		WSResult result = new WSResult();

		// Step.1 获得EIP、ECS、ELB信息
		// Step.2 删除和其他资源(ECS & ELB)的关联关系
		cmdbuildSoapService.deleteMapEcsEip(serviceDTO.getId(), eipDTO.getId());
		// Step.3 firwall删除虚拟IP
		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
		// firewallSoapService.deleteEIPByFirewall(eipParameter);
		return result;
	}

	@Override
	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, List<EipDTO> eipDTOs) {

		WSResult result = new WSResult();

		// Step.1 将DNS信息写入CMDBuild
		dnsDTO.setDomainType(LookUpConstants.DomainType.GSLB.getValue());
		IdResult idResult = cmdbuildSoapService.createDns(dnsDTO);
		// Step.2 将DNS策略信息写入CMDBuild

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", dnsDTO.getDescription());
		map.put("EQ_tenants", dnsDTO.getTenants());

		DnsDTO queryDnsDTO = (DnsDTO) cmdbuildSoapService.findDnsByParams(CMDBuildUtil.wrapperSearchParams(map))
				.getDto();

		for (DnsPolicyDTO policyDTO : dnsPolicyDTOs) {
			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getDnsProtocol()).getDto();

			DnsPolicyDTO dnsPolicyDTO = new DnsPolicyDTO();
			dnsPolicyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getPort());
			dnsPolicyDTO.setPort(policyDTO.getPort());
			dnsPolicyDTO.setDns(queryDnsDTO.getId());
			dnsPolicyDTO.setIpaddress(policyDTO.getIpaddress());
			dnsPolicyDTO.setDnsProtocol(policyDTO.getDnsProtocol());
			cmdbuildSoapService.createDnsPolicy(dnsPolicyDTO);
		}
		// Step.3 创建关联关系

		for (EipDTO eipDTO : eipDTOs) {
			cmdbuildSoapService.createMapEipDns(eipDTO.getId(), queryDnsDTO.getId());
		}

		// Step.4 调用DNS 接口创建DNS对象
		// dnsSoapService.createDNSByDNS(wrapperDNSParameter(queryDnsDTO));

		result.setMessage(idResult.getMessage());
		return result;
	}

	private DNSParameter wrapperDNSParameter(DnsDTO dnsDTO) {

		List<DNSPublicIPParameter> publicIPParameters = new ArrayList<DNSPublicIPParameter>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_dns", dnsDTO.getId());

		List<Object> list = cmdbuildSoapService.getDnsList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto();

		for (Object obj : list) {

			List<DNSPolicyParameter> policyParameters = new ArrayList<DNSPolicyParameter>();

			DnsPolicyDTO policyDTO = (DnsPolicyDTO) obj;

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getDnsProtocol()).getDto();

			DNSPolicyParameter policyParameter = new DNSPolicyParameter();
			policyParameter.setProtocolText(lookUpDTO.getDescription());
			policyParameter.setPort(Integer.valueOf(policyDTO.getPort()));
			policyParameters.add(policyParameter);
			DNSPublicIPParameter publicIPParameter = new DNSPublicIPParameter();
			publicIPParameter.setIpaddress(policyDTO.getIpaddress());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);
			publicIPParameters.add(publicIPParameter);
		}
		DNSParameter parameter = new DNSParameter();
		parameter.setDomianName(dnsDTO.getDomainName());
		parameter.setDomianType(dnsDTO.getDomainTypeText());
		parameter.getPublicIPs().addAll(publicIPParameters);
		return parameter;
	}

	@Override
	public WSResult deleteDNS(Integer dnsId) {
		WSResult result = new WSResult();

		// Step.1 获得dns.
		DnsDTO dnsDTO = (DnsDTO) cmdbuildSoapService.findDns(dnsId).getDto();
		// Step.2 调用dns接口删除dns
		// dnsSoapService.deleteDNSByDNS(wrapperDNSParameter(dnsDTO));
		// Step.3 查询dns下所有policy并删除

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_dns", dnsId);

		List<Object> list = cmdbuildSoapService.getDnsPolicyList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		for (Object obj : list) {
			DnsPolicyDTO policyDTO = (DnsPolicyDTO) obj;
			cmdbuildSoapService.deleteDnsPolicy(policyDTO.getId());
		}
		// Step.4 删除dns
		cmdbuildSoapService.deleteDns(dnsId);

		return result;
	}

}
