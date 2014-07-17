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
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.LogDTO;
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.cmdbuild.VpnDTO;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.firewall.VPNUserParameter;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.RelationVMParameter.RelationMaps.Entry;
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

}
