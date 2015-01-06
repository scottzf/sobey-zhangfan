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
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.NicDTO;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.instance.BindingPortGroupParameter;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.VMDiskParameter;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.storage.StorageSoapService;
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
		 */

		// Step.1 将Subnet保存至CMDB中

		cmdbuildSoapService.createSubnet(subnetDTO);

		// Step.2 由Subnet生成Ipaddress,保存至CMDB中

		// 获得创建的Subnet对象
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", subnetDTO.getDescription());
		SubnetDTO querySubnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnetByParams(
				CMDBuildUtil.wrapperSearchParams(map)).getDto();

		// 根据Subnet生成ipaddress List,并保存至CMDB
		List<IpaddressDTO> ipaddressDTOs = generatedIpaddressDTOs(querySubnetDTO);
		for (IpaddressDTO ipaddressDTO : ipaddressDTOs) {
			cmdbuildSoapService.createIpaddress(ipaddressDTO);
		}

		return result;
	}

	private List<IpaddressDTO> generatedIpaddressDTOs(SubnetDTO subnetDTO) {

		List<IpaddressDTO> ipaddressDTOs = new ArrayList<IpaddressDTO>();

		String prefixIP = StringUtils.substringBeforeLast(subnetDTO.getSegment(), ".");

		// IP从2-255 ,共254个IP
		for (int i = 2; i < 256; i++) {
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

	@Override
	public WSResult deleteSubnet(Integer subnetId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 生成vcenter中VM的名称.<br>
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
	 * 生成vcenter中VM的名称.<br>
	 *
	 * VMWare中虚拟机名称不能重复,所以将VM名称设置为 "租户标识符-VlanId"
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

			NicDTO nicDTO = (NicDTO) nicList.get(0);// 随机获得一个网卡.

			HashMap<String, Object> vlanMap = new HashMap<String, Object>();
			vlanMap.put("EQ_nic", nicDTO.getId());
			vlanMap.put("EQ_subnet", subnetDTO.getId());
			VlanDTO vlanDTO = (VlanDTO) cmdbuildSoapService.findVlanByParams(CMDBuildUtil.wrapperSearchParams(vlanMap))
					.getDto();

			if (vlanDTO == null) {
				// 如果为null,表示subnet在该网卡上没有关联的端口组.创建一个新的Vlan.

				Integer vlanId = cmdbuildSoapService.findMaxVlanId(nicDTO.getId(), subnetDTO.getId());

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
				cmdbuildSoapService.createVlan(insertVlanDTO);

				HashMap<String, Object> queryVlanMap = new HashMap<String, Object>();
				queryVlanMap.put("EQ_nic", nicDTO.getId());
				queryVlanMap.put("EQ_subnet", subnetDTO.getId());
				queryVlanMap.put("EQ_description", vlanName);
				VlanDTO queryVlanDTO = (VlanDTO) cmdbuildSoapService.findVlanByParams(
						CMDBuildUtil.wrapperSearchParams(queryVlanMap)).getDto();

				return queryVlanDTO;
			}

			// subnet在该Nic上有关联的端口组(Vlan),返回VlanDTO对象.
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
		 * Step.2 获得端口组Vlan
		 * 
		 * Step.3 创建ECS
		 * 
		 * Step.4 绑定端口组
		 * 
		 * Step.5 保存CMDB
		 * 
		 */

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(ecsDTO.getSubnet()).getDto();
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(subnetDTO.getTenants()).getDto();
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(subnetDTO.getIdc()).getDto();

		// Step.1 获得Server

		ServerDTO serverDTO = findSuitableServerDTO(idcDTO);// 将ECS创建在负载最轻的Server上

		IpaddressDTO ipaddressDTO = findAvailableIPAddressDTO(subnetDTO); // 从子网中选择一个IP.

		// Step.2 获得端口组Vlan
		VlanDTO vlanDTO = findSuitableVlanDTO(serverDTO, subnetDTO);

		// Step.3 创建ECS
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

		instanceSoapService.cloneVMByInstance(cloneVMParameter);

		// Step.4 绑定端口组

		BindingPortGroupParameter bindingPortGroupParameter = new BindingPortGroupParameter();
		bindingPortGroupParameter.setDatacenter(datacenter);
		bindingPortGroupParameter.setPortGroupName(vlanDTO.getDescription());
		bindingPortGroupParameter.setVmName(vmName);

		instanceSoapService.bindingPortGroupInstance(bindingPortGroupParameter);

		// Step.5 保存CMDB

		ecsDTO.setServer(serverDTO.getId());
		ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		cmdbuildSoapService.createEcs(ecsDTO);

		result.setMessage("ECS创建成功");
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
	public WSResult createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteELB(Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingELB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingELB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, Integer[] eipIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteDNS(Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

}
