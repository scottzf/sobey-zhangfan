package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.core.utils.MathsUtil;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DTOListResult;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.LogDTO;
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.cmdbuild.VpnDTO;
import com.sobey.generate.firewall.EIPParameter;
import com.sobey.generate.firewall.EIPPolicyParameter;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.firewall.VPNUserParameter;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.RelationVMParameter.RelationMaps.Entry;
import com.sobey.generate.storage.CreateEs3Parameter;
import com.sobey.generate.storage.DeleteEs3Parameter;
import com.sobey.generate.storage.MountEs3Parameter;
import com.sobey.generate.storage.RemountEs3Parameter;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.storage.UmountEs3Parameter;
import com.sobey.generate.switches.ESGParameter;
import com.sobey.generate.switches.RuleParameter;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.switches.VlanParameter;

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

	// 临时数据
	public static Integer idcId = 99;
	public static Integer agentTypeId = 80;
	public static Integer VLANSTATUSID_UNUSE = 1442;
	public static Integer VLANSTATUSID_USED = 1443;
	public static Integer serverId = 344;
	public static Integer EcsStatusId = 34;
	public static Integer resultId = 63;
	/**
	 * 未使用
	 */
	public static String IPADDRESSSTATUS_UNUSE = "74";

	@Override
	public void createTenants(TenantsDTO tenantsDTO) {

		/**
		 * step.1 创建租户.
		 * 
		 * step.2 分配Vlan
		 * 
		 * step.3 创建VPN账号
		 * 
		 * step.4 创建默认Esg
		 */

		// step.1 创建租户.
		TenantsDTO tenants = createTenantsDTO(tenantsDTO);

		// step.2 分配Vlan
		VlanDTO vlanDTO = allocationVlan(tenants);

		// step.3 创建VPN账号
		createVPN(tenants, vlanDTO);

		// step.4 创建默认Esg
		createDefaultEsg(tenants, vlanDTO);
	}

	/**
	 * 创建TenantsDTO,并返回TenantsDTO对象.
	 */
	private TenantsDTO createTenantsDTO(TenantsDTO tenantsDTO) {
		cmdbuildSoapService.createTenants(tenantsDTO);
		return getTenantsDTO(tenantsDTO.getDescription());
	}

	private VlanDTO allocationVlan(TenantsDTO tenantsDTO) {

		// 查询出未被使用的vlan
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_vlanStatus", VLANSTATUSID_UNUSE.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult dtoListResult = cmdbuildSoapService.getVlanList(searchParams);

		// 将vlan分配给租户
		VlanDTO vlanDTO = (VlanDTO) dtoListResult.getDtoList().getDto().get(0);
		vlanDTO.setTenants(tenantsDTO.getId());
		vlanDTO.setVlanStatus(VLANSTATUSID_USED);
		cmdbuildSoapService.updateVlan(vlanDTO.getId(), vlanDTO);

		// 分配了vlan后,在vmware上创建分布式端口组
		// TODO 暂时注释,待放到测试环境下再开启
		// instanceSoapService.createPortGroupByInstance(getVlanId(vlanDTO.getDescription()), DEFAULT_IDC);

		// 交换机上创建vlan
		VlanParameter vlanParameter = new VlanParameter();
		vlanParameter.setGateway(vlanDTO.getGateway());
		vlanParameter.setNetMask(vlanDTO.getNetMask());
		vlanParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		switchesSoapService.createVlanByCoreSwtich(vlanParameter);
		// TODO 测试环境下只开启核心交换机
		// switchesSoapService.createVlanByAccessSwtich(vlanParameter);

		return vlanDTO;
	}

	/**
	 * 创建VPN账号
	 * 
	 * @param tenantsDTO
	 * @param vlanDTO
	 */
	private void createVPN(TenantsDTO tenantsDTO, VlanDTO vlanDTO) {

		Integer policyId = cmdbuildSoapService.getMaxPolicyId();

		// 在firewall上创建VPN用户
		VPNUserParameter parameter = new VPNUserParameter();
		parameter.setVpnUser(tenantsDTO.getDescription());
		parameter.setVpnPassword(tenantsDTO.getPassword());
		parameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		parameter.setNetMask(vlanDTO.getNetMask());
		parameter.setFirewallPolicyId(policyId);

		List<String> segments = new ArrayList<String>();
		segments.add(vlanDTO.getSegment());
		parameter.getSegments().addAll(segments);

		List<String> ipaddressList = new ArrayList<String>();
		ipaddressList.add(vlanDTO.getGateway());
		parameter.getIpaddress().addAll(ipaddressList);

		firewallSoapService.createVPNUserByFirewall(parameter);

		// 在CMDBuild中创建VPNUser信息
		VpnDTO vpnDTO = new VpnDTO();
		vpnDTO.setTenants(tenantsDTO.getId());
		vpnDTO.setRemark(tenantsDTO.getDescription() + "的VPN账号");
		vpnDTO.setPolicyId(policyId);
		vpnDTO.setPassword(tenantsDTO.getPassword());
		vpnDTO.setDescription(tenantsDTO.getDescription());
		vpnDTO.setIdc(idcId);
		vpnDTO.setAgentType(agentTypeId);

		cmdbuildSoapService.createVpn(vpnDTO);
	}

	/**
	 * 创建默认Esg
	 */
	private void createDefaultEsg(TenantsDTO tenantsDTO, VlanDTO vlanDTO) {

		// 在switch上创建Esg
		// 默认的permit列表包含10.10.1.0和10.10.2.0两个管理网段,同时拒绝所有其他网段访问

		Integer aclNumber = cmdbuildSoapService.getMaxAclNumber();

		ESGParameter parameter = new ESGParameter();

		parameter.setAclNumber(aclNumber);
		parameter.setDesc(tenantsDTO.getDescription());
		parameter.setVlanId(getVlanId(vlanDTO.getDescription()));

		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		RuleParameter permitsRule1 = new RuleParameter();
		permitsRule1.setSource("10.10.100.0");
		permitsRule1.setSourceNetMask("0.0.0.255");
		permitsRule1.setDestination("10.10.1.0");
		permitsRule1.setDestinationNetMask("0.0.0.255");
		permits.add(permitsRule1);

		RuleParameter permitsRule2 = new RuleParameter();
		permitsRule2.setSource("10.10.100.0");
		permitsRule2.setSourceNetMask("0.0.0.255");
		permitsRule2.setDestination("10.10.2.0");
		permitsRule2.setDestinationNetMask("0.0.0.255");

		permits.add(permitsRule2);

		parameter.getPermits().addAll(permits);

		List<RuleParameter> denys = new ArrayList<RuleParameter>();

		RuleParameter denysRule = new RuleParameter();
		denysRule.setSource(vlanDTO.getSegment());
		denysRule.setSourceNetMask("0.0.0.255");
		denysRule.setDestination("0.0.0.0");
		denysRule.setDestinationNetMask("0");

		denys.add(denysRule);
		parameter.getDenys().addAll(denys);

		switchesSoapService.createESGBySwtich(parameter);

		// 将ESG保存在CMDBuild

		EsgDTO dto = new EsgDTO();
		dto.setAclNumber(aclNumber);
		dto.setDescription("默认策略");
		dto.setIsDefault(true);
		dto.setRemark(tenantsDTO.getDescription() + "的默认策略");
		dto.setTenants(tenantsDTO.getId());
		dto.setAgentType(agentTypeId);
		dto.setIdc(idcId);
		cmdbuildSoapService.createEsg(dto);

		// 保存ESG相关的策略
		createEsgPolicy(parameter, dto, tenantsDTO);

	}

	private void createEsgPolicy(ESGParameter parameter, EsgDTO esgDTO, TenantsDTO tenantsDTO) {

		EsgDTO dto = getEsgDTO(esgDTO, tenantsDTO);

		for (RuleParameter ruleParameter : parameter.getDenys()) {
			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(dto.getId());
			esgPolicyDTO.setPolicyType(98);
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Denys-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());
			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}

		for (RuleParameter ruleParameter : parameter.getPermits()) {

			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(dto.getId());
			esgPolicyDTO.setPolicyType(36);
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Permits-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());

			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}
	}

	/**
	 * 根据 description 返回TenantsDTO对象
	 */
	private TenantsDTO getTenantsDTO(String description) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", description);
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (TenantsDTO) cmdbuildSoapService.findTenantsByParams(searchParams).getDto();
	}

	/**
	 * 根据 description 返回EsgDTO对象
	 */
	private EsgDTO getEsgDTO(EsgDTO esgDTO, TenantsDTO tenantsDTO) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", esgDTO.getDescription());
		map.put("EQ_tenants", tenantsDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (EsgDTO) cmdbuildSoapService.findEsgByParams(searchParams).getDto();
	}

	/**
	 * 将VlanDTO的description进行分解,去掉前缀vlan后,得到数字.<br>
	 * 
	 * 注意: description的格式必须为"Vlan + 数字" eg: vlan100
	 */
	private Integer getVlanId(String description) {
		return Integer.valueOf(StringUtils.remove(description, "vlan"));
	}

	@Override
	public void createECS(Integer tenantsId, CloneVMParameter cloneVMParameter) {

		/**
		 * step.1 获得租户、vlan、未使用的IP信息
		 * 
		 * step.2 创建虚拟机，同时将IP更改为使用状态
		 * 
		 * step.3 CMDBuild中创建ECS信息
		 * 
		 * step.4 写入日志
		 */

		// 获得租户
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(tenantsId).getDto();

		// 获得租户所属vlan
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);

		// 获得未使用的IP
		IpaddressDTO ipaddressDTO = getUnusedIpaddress(vlanDTO);

		// 创建虚拟机
		createVM(cloneVMParameter, vlanDTO, ipaddressDTO);

		// 将分配给虚拟机的IP更改为使用状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// CMDBuild中插入一条ECS信息
		createECS(cloneVMParameter, vlanDTO, ipaddressDTO, tenantsDTO);

		// TODO 应该提供一个枚举常量
		createLog(tenantsDTO.getId(), 26, 48, resultId);
	}

	/**
	 * 创建操作日志
	 */
	private void createLog(Integer tenantsId, Integer serviceTypeId, Integer operateTypeId, Integer resultId) {

		LookUpDTO serviceType = (LookUpDTO) cmdbuildSoapService.findLookUp(serviceTypeId).getDto();
		LookUpDTO operateType = (LookUpDTO) cmdbuildSoapService.findLookUp(operateTypeId).getDto();

		String description = operateType.getDescription() + serviceType.getDescription();

		LogDTO logDTO = new LogDTO();
		logDTO.setResult(resultId);
		logDTO.setTenants(tenantsId);
		logDTO.setServiceType(serviceTypeId);
		logDTO.setOperateType(operateTypeId);
		logDTO.setDescription(description);

		cmdbuildSoapService.createLog(logDTO);
	}

	/**
	 * 创建虚拟机
	 */
	private void createVM(CloneVMParameter cloneVMParameter, VlanDTO vlanDTO, IpaddressDTO ipaddressDTO) {

		// VMWare中虚拟机名称不能重复,所以将VM名称设置为 "用户定义名称-ip地址"
		String vmname = cloneVMParameter.getVMName() + "-" + ipaddressDTO.getDescription();

		cloneVMParameter.setVMName(vmname);
		cloneVMParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		cloneVMParameter.setGateway(vlanDTO.getGateway());
		cloneVMParameter.setSubNetMask(vlanDTO.getNetMask());
		cloneVMParameter.setIpaddress(ipaddressDTO.getDescription());
		cloneVMParameter.setVMSUserName("sobey");
		cloneVMParameter.setVMTemplateOS(getEcsSpec(cloneVMParameter.getVMTemplateName()).getOsTypeText());

		instanceSoapService.cloneVMByInstance(cloneVMParameter);
	}

	private void createECS(CloneVMParameter cloneVMParameter, VlanDTO vlanDTO, IpaddressDTO ipaddressDTO,
			TenantsDTO tenantsDTO) {

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setAgentType(agentTypeId);
		ecsDTO.setDescription(cloneVMParameter.getVMName());
		ecsDTO.setEcsSpec(getEcsSpec(cloneVMParameter.getVMTemplateName()).getId());
		ecsDTO.setEcsStatus(EcsStatusId);
		ecsDTO.setIdc(vlanDTO.getIdc());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		ecsDTO.setServer(serverId); // TODO 此处应该通过算法得出负载最轻的Server
		ecsDTO.setTenants(tenantsDTO.getId());
		ecsDTO.setRemark(cloneVMParameter.getDatacenter());

		cmdbuildSoapService.createEcs(ecsDTO);
	}

	private EcsSpecDTO getEcsSpec(String name) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_imageName", name);
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (EcsSpecDTO) cmdbuildSoapService.findEcsSpecByParams(searchParams).getDto();
	}

	/**
	 * 获得未使用的ip地址.
	 */
	private IpaddressDTO getUnusedIpaddress(VlanDTO vlanDTO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_vlan", vlanDTO.getId().toString());
		map.put("EQ_ipaddressStatus", IPADDRESSSTATUS_UNUSE);
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getIpaddressList(searchParams);
		return (IpaddressDTO) listResult.getDtoList().getDto().get(0);
	}

	/**
	 * 获得tenants的vlan
	 */
	private VlanDTO getVlanDTO(TenantsDTO tenantsDTO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_tenants", tenantsDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (VlanDTO) cmdbuildSoapService.findVlanByParams(searchParams).getDto();
	}

	@Override
	public void destroyECS(Integer ecsId) {

		/**
		 * step.1 获得ECS信息
		 * 
		 * step.2 关闭VM电源
		 * 
		 * step.3 销毁虚拟机，同时初始化IP状态
		 * 
		 * step.4 CMDBuild中删除ECS信息
		 * 
		 * step.5 写入日志
		 */

		// 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// 关闭VM电源
		powerOpsECS(ecsId, "poweroff");

		// 销毁虚拟机
		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setDatacenter("xa");
		destroyVMParameter.setVMName(ecsDTO.getDescription());
		instanceSoapService.destroyVMByInstance(destroyVMParameter);

		// 初始化IP
		cmdbuildSoapService.initIpaddress(ecsDTO.getIpaddress());

		// CMDBuild中删除ECS信息
		cmdbuildSoapService.deleteEcs(ecsId);

		// 写入日志
		createLog(ecsDTO.getTenants(), 26, 76, resultId);
	}

	@Override
	public void powerOpsECS(Integer ecsId, String powerOperation) {

		/**
		 * step.1 获得ECS信息
		 * 
		 * step.2 关闭VM电源
		 */

		// 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		ecsDTO.setEcsStatus(71);
		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName(ecsDTO.getDescription());
		powerVMParameter.setPowerOperation(powerOperation);
		powerVMParameter.setDatacenter("xa");
		instanceSoapService.powerVMByInstance(powerVMParameter);

		// 写入日志
		createLog(ecsDTO.getTenants(), 26, 75, resultId);

	}

	@Override
	public void reconfigECS(Integer ecsId, Integer ecsSpecId) {

		/**
		 * step.1 获得ECS信息
		 * 
		 * step.2 获得ECS Spec信息
		 * 
		 * step.3 更新VM内存大小和CPU数量
		 * 
		 * step.4 更新CMDBuild ECS规格
		 */

		// 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// 获得ECS Spec信息
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsSpecId).getDto();

		// 更新VM内存大小和CPU数量
		Long memoryMb = (long) MathsUtil.mul(ecsSpecDTO.getMemory(), 1024);// 因为vmware的内存单位是MB,cmdbuild是GB,所以此处做一个单位换算.

		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setCPUNumber(ecsSpecDTO.getCpuNumber());
		reconfigVMParameter.setDatacenter("xa");
		reconfigVMParameter.setMemoryMB(memoryMb);
		reconfigVMParameter.setVMName(ecsDTO.getDescription());

		instanceSoapService.reconfigVMByInstance(reconfigVMParameter);

		// 更新CMDBuild ECS规格
		ecsDTO.setEcsSpec(ecsSpecId);
		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		// TODO 日志

	}

	@Override
	public String syncVM(String datacenter) {

		StringBuffer sb = new StringBuffer();

		// 从vcenter中获得VM和Host的关联关系
		HashMap<String, String> vcenterMap = relationVM(datacenter);

		for (java.util.Map.Entry<String, String> entry : vcenterMap.entrySet()) {

			HashMap<String, String> ecsMap = new HashMap<String, String>();

			System.out.println(entry.getKey());
			ecsMap.put("EQ_description", entry.getKey());

			// 根据VM的名称获得CMDBuild中的对象Ecs
			DTOResult dtoResult = cmdbuildSoapService.findEcsByParams(CMDBuildUtil.wrapperSearchParams(ecsMap));

			EcsDTO ecsDTO = (EcsDTO) dtoResult.getDto();

			// 根据SHost的名称获得CMDB中的对象Server
			ServerDTO serverDTO = (ServerDTO) cmdbuildSoapService.findServer(ecsDTO.getServer()).getDto();

			// 比较VM从vcenter获取的关联关系中的Host名称和根据CMDBuild中查询得到的关联的Host名称是否相同
			// 如果不相同,则更改CMDBuild中关联的Server对象.
			if (!entry.getValue().equals(serverDTO.getCode())) {

				sb.append("vcenter中对应的宿主机:" + entry.getValue() + "<br>");
				sb.append("CMDBuild中对应的宿主机:" + serverDTO.getDescription() + "<br>");
				sb.append("--------------------------------------------------");

				HashMap<String, String> serverMap = new HashMap<String, String>();
				serverMap.put("EQ_description", entry.getValue());

				ServerDTO serverDTO2 = (ServerDTO) cmdbuildSoapService.findServerByParams(
						CMDBuildUtil.wrapperSearchParams(serverMap)).getDto();

				ecsDTO.setServer(serverDTO2.getId());
				cmdbuildSoapService.updateEcs(ecsDTO.getId(), ecsDTO);

			}

		}
		return sb.toString();
	}

	private HashMap<String, String> relationVM(String datacenter) {

		HashMap<String, String> map = new HashMap<String, String>();

		// 将RelationVMParameter转换成HashMap
		for (Entry entry : instanceSoapService.getVMAndHostRelationByInstance(datacenter).getRelationMaps().getEntry()) {
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	@Override
	public void createES3(Integer tenantsId, CreateEs3Parameter createEs3Parameter) {

		/**
		 * step.1 获得租户、vlan、未使用的IP信息
		 * 
		 * step.2 创建volume
		 * 
		 * step.3 CMDBuild中创建ES3信息
		 * 
		 * step.4 写入日志
		 */

		String VolumeName = createEs3Parameter.getVolumeName();

		// 获得租户
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(tenantsId).getDto();

		// 获得租户所属vlan
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);

		// 创建volume
		createEs3Parameter.setVolumeName(VolumeName + tenantsDTO.getId());
		storageSoapService.createEs3ByStorage(createEs3Parameter);

		// CMDBuild中创建ES3信息
		Es3DTO es3DTO = new Es3DTO();
		es3DTO.setAgentType(agentTypeId);
		es3DTO.setDescription(createEs3Parameter.getVolumeName());
		es3DTO.setIdc(vlanDTO.getIdc());
		es3DTO.setTenants(tenantsDTO.getId());
		// TODO 注意单位,脚本用的MB,而页面是GB,测试环境无法创建GB大小的volume.
		es3DTO.setDiskSize(Double.valueOf(createEs3Parameter.getVolumeSize()));
		es3DTO.setVolumeName(VolumeName);
		es3DTO.setStorage(365);// TODO 通过算法得出负载最轻的netappcontroller.
		es3DTO.setEs3Type(73);

		cmdbuildSoapService.createEs3(es3DTO);

		// TODO 应该提供一个枚举常量
		createLog(tenantsDTO.getId(), 91, 48, resultId);
	}

	@Override
	public void attachES3(Integer es3Id, Integer ecsId) {

		/**
		 * step.1 获得ECS、ES3、Storage、IP信息
		 * 
		 * step.2 将访问IP列表写入netapp controller中
		 * 
		 * step.3 挂载volume
		 * 
		 * step.4 将ES3 和ECS的关联关系写入cmdbuild
		 * 
		 * step.5 写入日志
		 * 
		 */

		// 获得ES3
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();

		// 获得ECS
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// 将访问IP列表写入netapp controller中
		wirteMountRule(es3dto, ecsDTO);

		// 挂载
		mountEs3(es3dto, ecsDTO);

		// 将ES3 和ECS的关联关系写入cmdbuild
		cmdbuildSoapService.createMapEcsEs3(ecsId, es3Id);

		// 写入日志
		createLog(ecsDTO.getTenants(), 91, 75, resultId);

	}

	private void mountEs3(Es3DTO es3dto, EcsDTO ecsDTO) {

		// netapp的控制IP
		IpaddressDTO storageIP = (IpaddressDTO) cmdbuildSoapService
				.findIpaddress(es3dto.getStorageDTO().getIpaddress()).getDto();

		// ECS的IP
		IpaddressDTO ecsIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		MountEs3Parameter mountEs3Parameter = new MountEs3Parameter();
		mountEs3Parameter.setClientIPaddress(ecsIP.getDescription());
		mountEs3Parameter.setNetAppIPaddress(storageIP.getDescription());
		mountEs3Parameter.setVolumeName(es3dto.getDescription());
		storageSoapService.mountEs3ByStorage(mountEs3Parameter);
	}

	/**
	 * 向netapp控制器写入可挂载的权限.
	 */
	private void wirteMountRule(Es3DTO es3dto, EcsDTO ecsDTO) {

		/**
		 * netapp的权限是要将所有的ip都写入控制器中,但是后面会将前面的规则覆盖掉.
		 * 
		 * 为了保证规则完成,需要将es3关联的所有ECS查询出来放入after、before list中.
		 */

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(es3dto.getDescription());

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", es3dto.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);

		// 查询出ECS和ES3的关联关系
		List<Object> ecsEs3DTOs = cmdbuildSoapService.getMapEcsEs3List(searchParams).getDtoList().getDto();

		List<String> list = new ArrayList<String>();

		for (Object obj : ecsEs3DTOs) {
			MapEcsEs3DTO mapEcsEs3DTO = (MapEcsEs3DTO) obj;
			EcsDTO dto = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEs3DTO.getIdObj1())).getDto();
			list.add(dto.getIpaddressDTO().getDescription());
		}

		remountEs3Parameter.getBeforeClientIPaddress().addAll(list);

		// 将需要挂载的ECS IP放入list中.
		list.remove(ecsDTO.getIpaddressDTO().getDescription());// 先去重
		list.add(ecsDTO.getIpaddressDTO().getDescription());
		remountEs3Parameter.getAfterClientIPaddress().addAll(list);

		storageSoapService.remountEs3ByStorage(remountEs3Parameter);
	}

	@Override
	public void detachES3(Integer es3Id, Integer ecsId) {

		/**
		 * step.1 获得ECS、ES3、Storage、IP信息
		 * 
		 * step.2 将访问IP列表写入netapp controller中
		 * 
		 * step.3 挂载volume
		 * 
		 * step.4 将ES3 和ECS的关联关系写入cmdbuild
		 * 
		 * step.5 写入日志
		 * 
		 */

		// 获得ES3
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();

		// 获得ECS
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		wirteUmountRule(es3dto, ecsDTO);

		// 卸载Es3
		umountEs3(ecsDTO);

		cmdbuildSoapService.deleteMapEcsEs3(ecsId, es3Id);

		// 写入日志
		createLog(ecsDTO.getTenants(), 26, 76, resultId);

	}

	private void umountEs3(EcsDTO ecsDTO) {

		// ECS的IP
		IpaddressDTO ecsIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		UmountEs3Parameter umountEs3Parameter = new UmountEs3Parameter();
		umountEs3Parameter.setClientIPaddress(ecsIP.getDescription());

		storageSoapService.umountEs3ByStorage(umountEs3Parameter);
	}

	/**
	 * 向netapp控制器写入可挂载的权限.
	 */
	private void wirteUmountRule(Es3DTO es3dto, EcsDTO ecsDTO) {

		/**
		 * netapp的权限是要将所有的ip都写入控制器中,但是后面会将前面的规则覆盖掉.
		 * 
		 * 为了保证规则完成,需要将es3关联的所有ECS查询出来放入after、before list中.
		 */

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(es3dto.getDescription());

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", es3dto.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);

		// 查询出ECS和ES3的关联关系
		List<Object> ecsEs3DTOs = cmdbuildSoapService.getMapEcsEs3List(searchParams).getDtoList().getDto();

		List<String> list = new ArrayList<String>();

		for (Object obj : ecsEs3DTOs) {
			MapEcsEs3DTO mapEcsEs3DTO = (MapEcsEs3DTO) obj;
			EcsDTO dto = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEs3DTO.getIdObj1())).getDto();
			list.add(dto.getIpaddressDTO().getDescription());
		}

		remountEs3Parameter.getBeforeClientIPaddress().addAll(list);

		// 将需要挂载的ECS IP放入list中.
		list.remove(ecsDTO.getIpaddressDTO().getDescription());
		remountEs3Parameter.getAfterClientIPaddress().addAll(list);

		storageSoapService.remountEs3ByStorage(remountEs3Parameter);
	}

	@Override
	public void deleteES3(Integer es3Id) {

		// 获得ES3
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();

		// 删除volume
		DeleteEs3Parameter deleteEs3Parameter = new DeleteEs3Parameter();
		deleteEs3Parameter.setVolumeName(es3dto.getDescription());
		storageSoapService.deleteEs3ByStorage(deleteEs3Parameter);

		// cmdbuild 中删除Es3
		cmdbuildSoapService.deleteEs3(es3Id);

		// 写入日志
		createLog(es3dto.getTenants(), 26, 76, resultId);
	}

	@Override
	public void allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {

		/**
		 * step.1 获得未使用的公网IP.
		 * 
		 * step.2 将EIP和EIP端口信息写入CMDBuild
		 * 
		 * step.3 写入日志
		 * 
		 */

		// 获得未使用的公网IP.
		IpaddressDTO ipaddressDTO = getUnusedPublicIpaddress(eipDTO.getIsp());

		// 将EIP信息写入CMDBuild
		eipDTO.setAgentType(agentTypeId);
		eipDTO.setEipStatus(35);
		eipDTO.setIdc(idcId);
		eipDTO.setIpaddress(ipaddressDTO.getId());
		eipDTO.setDescription(ipaddressDTO.getDescription());
		cmdbuildSoapService.createEip(eipDTO);

		EipDTO dto = getEipDTO(ipaddressDTO.getId(), eipDTO.getTenants());

		// 将EIP端口信息写入CMDBuild
		for (EipPolicyDTO policyDTO : eipPolicyDTOs) {

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getEipProtocol()).getDto();

			policyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getSourcePort() + "-"
					+ policyDTO.getTargetPort());
			policyDTO.setEip(dto.getId());
			policyDTO.setEipProtocol(policyDTO.getEipProtocol());
			policyDTO.setSourcePort(policyDTO.getSourcePort());
			policyDTO.setTargetPort(policyDTO.getTargetPort());

			cmdbuildSoapService.createEipPolicy(policyDTO);
		}

		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// 写入日志
		createLog(eipDTO.getTenants(), 26, 76, resultId);
	}

	private EipDTO getEipDTO(Integer ipaddressId, Integer tenantsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddress", ipaddressId.toString());
		map.put("EQ_tenants", tenantsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (EipDTO) cmdbuildSoapService.findEipByParams(searchParams).getDto();

	}

	/**
	 * 获得未使用的公网IP
	 * 
	 * @return
	 */
	private IpaddressDTO getUnusedPublicIpaddress(Integer ispId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddressStatus", IPADDRESSSTATUS_UNUSE);
		map.put("EQ_ipaddressPool", "28");
		map.put("EQ_isp", ispId.toString());// 65 29
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getIpaddressList(searchParams);
		return (IpaddressDTO) listResult.getDtoList().getDto().get(0);
	}

	@Override
	public void recoverEIP(Integer eipId) {

		/**
		 * step.1 获得EIP.
		 * 
		 * step.2 初始化公网IP
		 * 
		 * step.3 查询EIP下所有policy并删除
		 * 
		 * step.4 删除EIP
		 * 
		 * step.5 写入日志
		 */

		// 获得EIP.
		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();

		// 初始化公网IP
		cmdbuildSoapService.initIpaddress(eipDTO.getIpaddress());

		// 查询EIP下所有policy并删除
		DTOListResult policyResult = getEipPolicyDTOList(eipId);

		for (Object obj : policyResult.getDtoList().getDto()) {
			EipPolicyDTO policyDTO = (EipPolicyDTO) obj;
			cmdbuildSoapService.deleteEipPolicy(policyDTO.getId());
		}
		// 删除EIP
		cmdbuildSoapService.deleteEip(eipId);

		// 写入日志
		createLog(eipDTO.getTenants(), 26, 76, resultId);
	}

	/**
	 * 获得EIP下所有的policy
	 */
	private DTOListResult getEipPolicyDTOList(Integer eipId) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_eip", eipId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getEipPolicyList(searchParams);
		return listResult;
	}

	/**
	 * 获得租户下所有EIP的所有policy
	 */
	private DTOListResult getEipPolicyDTOListByTenants(EipDTO eipDTO) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_tenants", eipDTO.getTenants().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getEipPolicyList(searchParams);
		return listResult;
	}

	@Override
	public void associateEIP(Integer eipId, Integer serviceId) {

		/**
		 * step.1 获得EIP.
		 * 
		 * step.2 与其他资源(ECS & ELB)建立关联关系
		 * 
		 * step.2 firwall创建虚拟IP
		 * 
		 * 
		 * step.3 更新eip状态
		 * 
		 * 
		 * step.4 写入日志
		 */

		String privateIP = "";

		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();

		// 因为可能绑定ELB或ECS,无法区分.但是ECS和ELB同属一个service,id是不可能重复的.
		// 所以先通过ID查询,判断对象是否为null,如果不为null说明绑定的是该服务对象.
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(serviceId).getDto();
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(serviceId).getDto();

		if (elbDTO != null) {
			cmdbuildSoapService.createMapEipElb(eipId, serviceId);
			privateIP = elbDTO.getIpaddressDTO().getDescription();
		} else if (ecsDTO != null) {
			cmdbuildSoapService.createMapEcsEip(serviceId, eipId);
			privateIP = ecsDTO.getIpaddressDTO().getDescription();
		}

		// 防火墙上创建eip
		createEIPInFirewall(eipDTO, privateIP);

		// 更新EIP状态
		eipDTO.setEipStatus(72); // 72:已使用 35:未使用
		cmdbuildSoapService.updateEip(eipId, eipDTO);

		// 写入日志
		createLog(eipDTO.getTenants(), 26, 76, resultId);
	}

	/**
	 * 在防火墙上创建EIP
	 */
	private void createEIPInFirewall(EipDTO eipDTO, String privateIP) {

		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
		eipParameter.setPrivateIP(privateIP);

		// 调用防火墙agent的接口
		firewallSoapService.createEIPByFirewall(eipParameter);

	}

	private EIPParameter wrapperEIPParameter(EipDTO eipDTO) {

		LookUpDTO isp = (LookUpDTO) cmdbuildSoapService.findLookUp(eipDTO.getIsp()).getDto();

		// 获得租户下所有的EIP策略.
		DTOListResult allPoliciesResult = getEipPolicyDTOListByTenants(eipDTO);
		List<String> allPolicies = new ArrayList<String>();
		if (allPoliciesResult.getDtoList() != null) {

			for (Object obj : allPoliciesResult.getDtoList().getDto()) {
				EipDTO dto = (EipDTO) obj;
				allPolicies.add(dto.getIpaddressDTO().getDescription());
			}
		}

		// 获得EIP的策略
		List<EIPPolicyParameter> policyParameters = new ArrayList<EIPPolicyParameter>();
		DTOListResult policiesResult = getEipPolicyDTOList(eipDTO.getId());

		for (Object obj : policiesResult.getDtoList().getDto()) {

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
		eipParameter.setIsp(Integer.valueOf(isp.getCode()));
		eipParameter.getPolicies().addAll(policyParameters);
		eipParameter.getAllPolicies().addAll(allPolicies);
		return eipParameter;

	}

	@Override
	public void dissociateEIP(Integer eipId, Integer serviceId) {

		/**
		 * step.1 获得EIP.
		 * 
		 * step.2 删除和其他资源(ECS & ELB)的关联关系
		 * 
		 * step.3 firwall删除虚拟IP
		 * 
		 * step.4 更新CMDBuild中EIP的状态为"未使用"
		 * 
		 * step.5 写入日志
		 */

		// 获得EIP.
		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();

		// 删除和其他资源(ECS & ELB)的关联关系

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(serviceId).getDto();
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(serviceId).getDto();

		if (ecsDTO != null) {
			cmdbuildSoapService.deleteMapEcsEip(serviceId, eipId);
		} else if (elbDTO != null) {
			cmdbuildSoapService.deleteMapEipElb(eipId, serviceId);
		}

		// firwall删除虚拟IP
		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
		firewallSoapService.deleteEIPByFirewall(eipParameter);

		// 更新CMDBuild中EIP的状态为"未使用"
		eipDTO.setEipStatus(35);
		cmdbuildSoapService.updateEip(eipId, eipDTO);

		// 写入日志
		createLog(eipDTO.getTenants(), 26, 76, resultId);

	}
}
