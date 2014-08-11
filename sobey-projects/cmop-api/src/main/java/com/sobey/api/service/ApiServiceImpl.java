package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.core.utils.MathsUtil;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DTOListResult;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.LogDTO;
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.MapEcsElbDTO;
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.MapEcsEsgDTO;
import com.sobey.generate.cmdbuild.MapEipDnsDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.cmdbuild.VpnDTO;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DNSPolicyParameter;
import com.sobey.generate.dns.DNSPublicIPParameter;
import com.sobey.generate.dns.DnsSoapService;
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
import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.loadbalancer.ELBPolicyParameter;
import com.sobey.generate.loadbalancer.ELBPublicIPParameter;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
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

	@Autowired
	private LoadbalancerSoapService loadbalancerSoapService;

	@Autowired
	private DnsSoapService dnsSoapService;

	// 临时数据
	public static Integer serverId = 143;

	public static Integer storageId = 161;

	@Override
	public void createTenants(TenantsDTO tenantsDTO, Integer agentTypeId) {

		/**
		 * Step.1 创建租户.
		 * 
		 * Step.2 分配Vlan
		 * 
		 * Step.3 创建VPN账号
		 * 
		 * Step.4 创建默认Esg
		 */

		// Step.1 创建租户.
		tenantsDTO.setAclNumber(cmdbuildSoapService.getMaxAclNumber());
		TenantsDTO tenants = createTenantsDTO(tenantsDTO);

		// Step.2 分配Vlan
		VlanDTO vlanDTO = allocationVlan(tenants);

		// Step.3 创建VPN账号
		createVPN(tenants, vlanDTO, agentTypeId);

		// Step.4 创建默认Esg
		createDefaultEsg(tenants, vlanDTO);
	}

	/**
	 * 创建TenantsDTO,并返回TenantsDTO对象.
	 */
	private TenantsDTO createTenantsDTO(TenantsDTO tenantsDTO) {
		cmdbuildSoapService.createTenants(tenantsDTO);
		return getTenantsDTO(tenantsDTO.getDescription());
	}

	/**
	 * 根据 description 返回TenantsDTO对象
	 */
	private TenantsDTO getTenantsDTO(String description) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotBlank(description)) {
			map.put("EQ_description", description);
		}
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (TenantsDTO) cmdbuildSoapService.findTenantsByParams(searchParams).getDto();
	}

	private VlanDTO allocationVlan(TenantsDTO tenantsDTO) {

		// 查询出未被使用的vlan
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_vlanStatus", LookUpConstants.VlanStatus.未使用.getValue().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult dtoListResult = cmdbuildSoapService.getVlanList(searchParams);

		// 将vlan分配给租户,更新vlan状态
		VlanDTO vlanDTO = (VlanDTO) dtoListResult.getDtoList().getDto().get(0);
		vlanDTO.setTenants(tenantsDTO.getId());
		vlanDTO.setVlanStatus(LookUpConstants.VlanStatus.已使用.getValue());
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
	private void createVPN(TenantsDTO tenantsDTO, VlanDTO vlanDTO, Integer agentTypeId) {

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
		vpnDTO.setIdc(vlanDTO.getIdc());
		vpnDTO.setAgentType(agentTypeId);

		cmdbuildSoapService.createVpn(vpnDTO);
	}

	/**
	 * 创建默认Esg
	 */
	private void createDefaultEsg(TenantsDTO tenantsDTO, VlanDTO vlanDTO) {

		// 默认的permit列表包含10.10.1.0和10.10.2.0两个管理网段

		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		RuleParameter permitsRule1 = new RuleParameter();
		permitsRule1.setSource(vlanDTO.getSegment());
		permitsRule1.setSourceNetMask("0.0.0.255");
		permitsRule1.setDestination("10.10.1.0");
		permitsRule1.setDestinationNetMask("0.0.0.255");

		permits.add(permitsRule1);

		RuleParameter permitsRule2 = new RuleParameter();
		permitsRule2.setSource(vlanDTO.getSegment());
		permitsRule2.setSourceNetMask("0.0.0.255");
		permitsRule2.setDestination("10.10.2.0");
		permitsRule2.setDestinationNetMask("0.0.0.255");

		permits.add(permitsRule2);

		// 拒绝所有其他网段访问
		List<RuleParameter> denys = new ArrayList<RuleParameter>();

		RuleParameter denysRule = new RuleParameter();
		denysRule.setSource(vlanDTO.getSegment());
		denysRule.setSourceNetMask("0.0.0.255");
		denysRule.setDestination("0.0.0.0");
		denysRule.setDestinationNetMask("0");

		denys.add(denysRule);

		ESGParameter parameter = new ESGParameter();
		parameter.setAclNumber(tenantsDTO.getAclNumber());
		parameter.setDesc(tenantsDTO.getDescription());
		parameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		parameter.getPermits().addAll(permits);
		parameter.getDenys().addAll(denys);

		switchesSoapService.createESGBySwtich(parameter);

		// 将ESG保存在CMDBuild

		EsgDTO dto = new EsgDTO();
		dto.setDescription("默认策略");
		dto.setIsDefault(true);
		dto.setRemark(tenantsDTO.getDescription() + "的默认策略");
		dto.setTenants(tenantsDTO.getId());
		dto.setAgentType(LookUpConstants.AgentType.H3C.getValue());

		cmdbuildSoapService.createEsg(dto);

		// 将ESG策略保存在CMDBuild
		createEsgPolicy(parameter, dto, tenantsDTO);
	}

	/**
	 * CMDBuild中创建ESG策略
	 * 
	 * @param parameter
	 * @param esgDTO
	 * @param tenantsDTO
	 */
	private void createEsgPolicy(ESGParameter parameter, EsgDTO esgDTO, TenantsDTO tenantsDTO) {

		EsgDTO dto = getEsgDTO(esgDTO, tenantsDTO);

		for (RuleParameter ruleParameter : parameter.getDenys()) {
			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(dto.getId());
			esgPolicyDTO.setPolicyType(LookUpConstants.PolicyType.Deny.getValue());
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Denys-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());
			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}

		for (RuleParameter ruleParameter : parameter.getPermits()) {

			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(dto.getId());
			esgPolicyDTO.setPolicyType(LookUpConstants.PolicyType.Permit.getValue());
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Permits-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());

			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}
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
	public void createECS(EcsDTO ecsDTO) {

		/**
		 * Step.1 获得租户、vlan、未使用的IP信息
		 * 
		 * Step.2 创建虚拟机，
		 * 
		 * Step.3 分配给虚拟机的IP更改为使用状态
		 * 
		 * Step.4 CMDBuild中创建ECS信息
		 * 
		 * Step.5 写入日志
		 */

		// Step.1 获得租户、vlan、未使用的IP、Idc、EcsSpec信息
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IpaddressDTO ipaddressDTO = getUnusedIpaddress(vlanDTO);// 未使用的IP
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();

		// Step.2 创建虚拟机，

		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setVMName(getVMName(tenantsDTO, ipaddressDTO));
		cloneVMParameter.setVMTemplateOS(ecsSpecDTO.getOsTypeText());
		cloneVMParameter.setVMTemplateName(ecsSpecDTO.getImageName());
		cloneVMParameter.setVMSUserName("sobey");
		cloneVMParameter.setSubNetMask(vlanDTO.getNetMask());
		cloneVMParameter.setGateway(vlanDTO.getGateway());
		cloneVMParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		cloneVMParameter.setIpaddress(ipaddressDTO.getDescription());
		cloneVMParameter.setDescription(ecsDTO.getRemark());
		cloneVMParameter.setDatacenter(idcDTO.getRemark());

		instanceSoapService.cloneVMByInstance(cloneVMParameter);

		// Step.3 分配给虚拟机的IP更改为使用状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// Step.4 CMDBuild中创建ECS信息
		ecsDTO.setServer(serverId); // TODO 此处应该通过算法得出负载最轻的Server
		ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		ecsDTO.setIpaddress(ipaddressDTO.getId());
		cmdbuildSoapService.createEcs(ecsDTO);

		// Step.5 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());
	}

	/**
	 * 创建操作日志
	 */
	private void createLog(Integer tenantsId, Integer serviceTypeId, Integer operateTypeId, Integer resultId) {

		LookUpDTO serviceType = (LookUpDTO) cmdbuildSoapService.findLookUp(serviceTypeId).getDto();
		LookUpDTO operateType = (LookUpDTO) cmdbuildSoapService.findLookUp(operateTypeId).getDto();

		LogDTO logDTO = new LogDTO();
		logDTO.setResult(resultId);
		logDTO.setTenants(tenantsId);
		logDTO.setServiceType(serviceTypeId);
		logDTO.setOperateType(operateTypeId);
		logDTO.setDescription(operateType.getDescription() + serviceType.getDescription());

		cmdbuildSoapService.createLog(logDTO);
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
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 关闭VM电源
		 * 
		 * Step.3 销毁虚拟机
		 * 
		 * Step.4 初始化虚拟机的IP状态
		 * 
		 * Step.5 CMDBuild中删除ECS信息
		 * 
		 * Step.6 写入日志
		 */

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 关闭VM电源
		powerOpsECS(ecsId, "poweroff");

		// Step.3 销毁虚拟机

		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setDatacenter(idcDTO.getRemark());
		destroyVMParameter.setVMName(getVMName(tenantsDTO, ipaddressDTO));
		instanceSoapService.destroyVMByInstance(destroyVMParameter);

		// Step.4 初始化虚拟机的IP状态
		cmdbuildSoapService.initIpaddress(ecsDTO.getIpaddress());

		// Step.5 CMDBuild中删除ECS信息
		cmdbuildSoapService.deleteEcs(ecsId);

		// Step.6 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public void powerOpsECS(Integer ecsId, String powerOperation) {

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 修改ECS的运行状态
		 * 
		 * Step.3 操作VM电源
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 修改ECS的运行状态

		if (LookUpConstants.powerOperation.poweroff.equals(powerOperation)) {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.停止.getValue());
		} else {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		}

		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		// Step.3 操作VM电源
		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName(getVMName(tenantsDTO, ipaddressDTO));
		powerVMParameter.setPowerOperation(powerOperation);
		powerVMParameter.setDatacenter(idcDTO.getRemark());
		instanceSoapService.powerVMByInstance(powerVMParameter);

		// Step.4 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());
	}

	/**
	 * 获得vcenter中VM的名称.<br>
	 *
	 * VMWare中虚拟机名称不能重复,所以将VM名称设置为 "用户定义名称-ip地址"
	 *
	 * @return
	 */
	private String getVMName(TenantsDTO tenantsDTO, IpaddressDTO ipaddressDTO) {
		return tenantsDTO.getDescription() + "-" + ipaddressDTO.getDescription();

	}

	@Override
	public void reconfigECS(Integer ecsId, Integer ecsSpecId) {

		/**
		 * Step.1 获得ECS信息
		 * 
		 * Step.2 获得ECS Spec信息
		 * 
		 * Step.3 更新VM内存大小和CPU数量
		 * 
		 * Step.4 更新CMDBuild ECS规格
		 * 
		 * Step.5 写入日志
		 */

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 获得ECS Spec信息
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsSpecId).getDto();

		// Step.3 更新VM内存大小和CPU数量
		Long memoryMb = (long) MathsUtil.mul(ecsSpecDTO.getMemory(), 1024);// 因为vmware的内存单位是MB,cmdbuild是GB,所以此处做一个单位换算.
		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setCPUNumber(ecsSpecDTO.getCpuNumber());
		reconfigVMParameter.setDatacenter(idcDTO.getRemark());
		reconfigVMParameter.setMemoryMB(memoryMb);
		reconfigVMParameter.setVMName(getVMName(tenantsDTO, ipaddressDTO));

		instanceSoapService.reconfigVMByInstance(reconfigVMParameter);

		// Step.4 更新CMDBuild ECS规格
		ecsDTO.setEcsSpec(ecsSpecId);
		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		// Step.5 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.更新.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public String syncVM(String datacenter) {

		/**
		 * Step.1 获得虚拟机和宿主机的实际关联关系
		 * 
		 * Step.2 获得CMDBuild中的ECS和Server的关联关系
		 * 
		 * Step.3 比较两个关联关系,已从vcenter中获得的关联关系为准,对CMDBuild数据进行更新
		 * 
		 * 
		 */

		// Step.1 获得虚拟机和宿主机的实际关联关系
		HashMap<String, String> vcenterMap = wrapperRelationVM(datacenter);

		StringBuffer sb = new StringBuffer();

		for (java.util.Map.Entry<String, String> entry : vcenterMap.entrySet()) {

			// Step.2 获得CMDBuild中的ECS和Server的关联关系
			// 根据VM的名称获得CMDBuild中的对象Ecs
			HashMap<String, String> ecsMap = new HashMap<String, String>();
			ecsMap.put("EQ_description", entry.getKey());
			DTOResult dtoResult = cmdbuildSoapService.findEcsByParams(CMDBuildUtil.wrapperSearchParams(ecsMap));
			EcsDTO ecsDTO = (EcsDTO) dtoResult.getDto();

			// 根据Host的名称获得CMDB中的对象Server
			ServerDTO serverDTO = (ServerDTO) cmdbuildSoapService.findServer(ecsDTO.getServer()).getDto();

			// Step.3 比较两个关联关系,已从vcenter中获得的关联关系为准,对CMDBuild数据进行更新
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

	private HashMap<String, String> wrapperRelationVM(String datacenter) {
		HashMap<String, String> map = new HashMap<String, String>();
		// 将RelationVMParameter转换成HashMap
		for (Entry entry : instanceSoapService.getVMAndHostRelationByInstance(datacenter).getRelationMaps().getEntry()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	public void createES3(Es3DTO es3DTO) {

		/**
		 * Step.1 获得租户、vlan信息
		 * 
		 * Step.2 创建volume
		 * 
		 * Step.3 CMDBuild中创建ES3信息
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 获得租户、vlan信息
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(es3DTO.getTenants()).getDto();

		// Step.2 创建volume
		String VolumeName = es3DTO.getDescription() + tenantsDTO.getId();

		CreateEs3Parameter createEs3Parameter = new CreateEs3Parameter();
		createEs3Parameter.setVolumeName(VolumeName);
		createEs3Parameter.setVolumeSize(Integer.valueOf(es3DTO.getDiskSize().intValue()).toString());
		storageSoapService.createEs3ByStorage(createEs3Parameter);

		// Step.3 CMDBuild中创建ES3信息
		es3DTO.setVolumeName(VolumeName);
		es3DTO.setStorage(storageId);// TODO 通过算法得出负载最轻的Storage(NetappController).

		cmdbuildSoapService.createEs3(es3DTO);

		// Step.4 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public void attachES3(Integer es3Id, Integer ecsId) {

		/**
		 * Step.1 获得ECS、ES3、Storage、IP信息
		 * 
		 * Step.2 将访问IP列表写入netapp controller中
		 * 
		 * Step.3 挂载volume
		 * 
		 * Step.4 将ES3 和ECS的关联关系写入cmdbuild
		 * 
		 * Step.5 写入日志
		 * 
		 */

		// Step.1 获得ECS、ES3、Storage、IP信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// Step.2 将访问IP列表写入netapp controller中
		wirteMountRule(es3dto, ecsDTO);

		// Step.3 挂载volume
		mountEs3(es3dto, ecsDTO);

		// Step.4 将ES3 和ECS的关联关系写入cmdbuild
		cmdbuildSoapService.createMapEcsEs3(ecsId, es3Id);

		// Step.5 写入日志
		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.挂载.getValue(), LookUpConstants.Result.成功.getValue());

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
		mountEs3Parameter.setVolumeName(es3dto.getVolumeName());
		storageSoapService.mountEs3ByStorage(mountEs3Parameter);
	}

	/**
	 * 向netapp控制器写入可挂载的权限.<br>
	 * 
	 * netapp的权限是要将所有的ip都写入控制器中,但是后面会将前面的规则覆盖掉.
	 * 
	 * 为了保证规则完成,需要将es3关联的所有ECS查询出来放入after、before list中.
	 */
	private void wirteMountRule(Es3DTO es3dto, EcsDTO ecsDTO) {

		List<String> list = getEcsIpaddressByEs3(es3dto);

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(es3dto.getVolumeName());
		remountEs3Parameter.getBeforeClientIPaddress().addAll(list);

		list.remove(ecsDTO.getIpaddressDTO().getDescription());// 先去重
		list.add(ecsDTO.getIpaddressDTO().getDescription());// 将需要挂载的ECS IP放入list中.

		remountEs3Parameter.getAfterClientIPaddress().addAll(list);

		storageSoapService.remountEs3ByStorage(remountEs3Parameter);
	}

	/**
	 * 将指定的ES3从netapp控制器中排除权限<br>
	 * 
	 * netapp的权限是要将所有的ip都写入控制器中,但是后面会将前面的规则覆盖掉.
	 * 
	 * 为了保证规则完成,需要将es3关联的所有ECS查询出来放入after、before list中.
	 */
	private void wirteUmountRule(Es3DTO es3dto, EcsDTO ecsDTO) {

		List<String> list = getEcsIpaddressByEs3(es3dto);

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(es3dto.getVolumeName());
		remountEs3Parameter.getBeforeClientIPaddress().addAll(list);

		list.remove(ecsDTO.getIpaddressDTO().getDescription());// 将需要卸载的ECS IP从List中删除.

		remountEs3Parameter.getAfterClientIPaddress().addAll(list);

		storageSoapService.remountEs3ByStorage(remountEs3Parameter);
	}

	/**
	 * 获得ES3关联的所有ECS IP集合.
	 * 
	 * @param es3dto
	 * @return
	 */
	private List<String> getEcsIpaddressByEs3(Es3DTO es3dto) {

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
		return list;
	}

	@Override
	public void detachES3(Integer es3Id, Integer ecsId) {

		/**
		 * Step.1 获得ECS、ES3信息
		 * 
		 * Step.2 ES3的IP从netapp控制器中排除权限<br>
		 * 
		 * Step.3 挂载volume
		 * 
		 * Step.4 将ES3 和ECS的关联关系写入cmdbuild
		 * 
		 * Step.5 写入日志
		 * 
		 */

		// Step.1 获得ECS、ES3、Storage、IP信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// Step.2 ES3的IP从netapp控制器中排除权限<br>
		wirteUmountRule(es3dto, ecsDTO);

		// Step.3 挂载volume
		umountEs3(ecsDTO);

		// Step.4 将ES3 和ECS的关联关系写入cmdbuild
		cmdbuildSoapService.deleteMapEcsEs3(ecsId, es3Id);

		// Step.5 写入日志
		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.卸载.getValue(), LookUpConstants.Result.成功.getValue());

	}

	private void umountEs3(EcsDTO ecsDTO) {

		// ECS的IP
		IpaddressDTO ecsIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		UmountEs3Parameter umountEs3Parameter = new UmountEs3Parameter();
		umountEs3Parameter.setClientIPaddress(ecsIP.getDescription());

		storageSoapService.umountEs3ByStorage(umountEs3Parameter);
	}

	@Override
	public void deleteES3(Integer es3Id) {

		/**
		 * Step.1 获得 ES3信息
		 * 
		 * Step.2 删除volume
		 * 
		 * Step.3 将CMDBuild中的ES3信息删除
		 * 
		 * Step.4 写入日志
		 * 
		 */

		// Step.1 获得 ES3信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();

		// Step.2 删除volume
		DeleteEs3Parameter deleteEs3Parameter = new DeleteEs3Parameter();
		deleteEs3Parameter.setVolumeName(es3dto.getVolumeName());
		storageSoapService.deleteEs3ByStorage(deleteEs3Parameter);

		// Step.3 将CMDBuild中的ES3信息删除
		cmdbuildSoapService.deleteEs3(es3Id);

		// Step.4 写入日志

		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public void allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {

		/**
		 * Step.1 获得未使用的公网IP.
		 * 
		 * Step.2 将EIP信息写入CMDBuild
		 * 
		 * Step.3 将EIP端口信息写入CMDBuild
		 * 
		 * Step.4 更改EIP的状态
		 * 
		 * Step.5 写入日志
		 * 
		 */

		// Step.1 获得未使用的公网IP.
		IpaddressDTO ipaddressDTO = getPublicIpaddress(eipDTO.getIsp());

		// Step.2 将EIP信息写入CMDBuild
		eipDTO.setIdc(ipaddressDTO.getIdc());
		eipDTO.setIpaddress(ipaddressDTO.getId());
		eipDTO.setDescription(ipaddressDTO.getDescription());
		eipDTO.setEipStatus(LookUpConstants.EIPStatus.已使用.getValue()); //
		cmdbuildSoapService.createEip(eipDTO);

		EipDTO dto = getEipDTO(ipaddressDTO.getId(), eipDTO.getTenants());

		// Step.3 将EIP端口信息写入CMDBuild
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

		// Step.4 更改EIP的状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// Step.5 写入日志
		createLog(dto.getTenants(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.更新.getValue(), LookUpConstants.Result.成功.getValue());
	}

	private EipDTO getEipDTO(Integer ipaddressId, Integer tenantsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddress", ipaddressId.toString());
		map.put("EQ_tenants", tenantsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (EipDTO) cmdbuildSoapService.findEipByParams(searchParams).getDto();
	}

	private ElbDTO getElbDTO(Integer ipaddressId, Integer tenantsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddress", ipaddressId.toString());
		map.put("EQ_tenants", tenantsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (ElbDTO) cmdbuildSoapService.findElbByParams(searchParams).getDto();
	}

	/**
	 * 获得未使用的公网IP
	 * 
	 * @return
	 */
	private IpaddressDTO getPublicIpaddress(Integer ispId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue().toString());
		map.put("EQ_ipaddressPool", LookUpConstants.IPAddressPool.InternetPool.getValue().toString());
		map.put("EQ_isp", ispId.toString());
		return getIpaddress(map);
	}

	/**
	 * 获得未使用的VIP
	 * 
	 * @return
	 */
	private IpaddressDTO getVIP() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue().toString());
		map.put("EQ_ipaddressPool", LookUpConstants.IPAddressPool.VIPPool.getValue().toString());
		return getIpaddress(map);
	}

	/**
	 * 获得未使用的ip地址.
	 */
	private IpaddressDTO getUnusedIpaddress(VlanDTO vlanDTO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_vlan", vlanDTO.getId().toString());
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue().toString());
		return getIpaddress(map);
	}

	/**
	 * 根据条件获得IP
	 * 
	 * @param map
	 * @return
	 */
	private IpaddressDTO getIpaddress(HashMap<String, String> map) {
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getIpaddressList(searchParams);
		return (IpaddressDTO) listResult.getDtoList().getDto().get(0);
	}

	@Override
	public void recoverEIP(Integer eipId) {

		System.out.println("eipId:" + eipId);

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
		DTOListResult policyResult = getEipPolicyDTOList(eipId);

		for (Object obj : policyResult.getDtoList().getDto()) {
			EipPolicyDTO policyDTO = (EipPolicyDTO) obj;
			cmdbuildSoapService.deleteEipPolicy(policyDTO.getId());
		}

		// Step.4 删除EIP
		cmdbuildSoapService.deleteEip(eipId);

		// 写入日志
		createLog(eipDTO.getTenants(), LookUpConstants.ServiceType.EIP.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
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
	 * 获得ELB下所有的policy
	 */
	private DTOListResult getElbPolicyDTOList(Integer elbId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_elb", elbId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getElbPolicyList(searchParams);
		return listResult;
	}

	/**
	 * 获得DNS下所有的policy
	 */
	private DTOListResult geDnsPolicyDTOList(Integer dnsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_dns", dnsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getDnsPolicyList(searchParams);
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
		 * Step.1 获得EIP、ECS、ELB的信息
		 * 
		 * Step.2 与其他资源(ECS & ELB)建立关联关系
		 * 
		 * Step.3 firwall创建虚拟IP
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 获得EIP、ECS、ELB的信息
		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(serviceId).getDto();
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(serviceId).getDto();

		// Step.2 与其他资源(ECS & ELB)建立关联关系
		// 因为可能绑定ELB或ECS,无法区分.但是ECS和ELB同属一个service,id是不可能重复的,所以先通过ID查询,判断对象是否为null,如果不为null说明绑定的是该服务对象.
		String privateIP = "";
		if (elbDTO != null) {
			cmdbuildSoapService.createMapEipElb(eipId, serviceId);
			privateIP = elbDTO.getIpaddressDTO().getDescription();
		} else if (ecsDTO != null) {
			cmdbuildSoapService.createMapEcsEip(serviceId, eipId);
			privateIP = ecsDTO.getIpaddressDTO().getDescription();
		}

		// Step.3 firwall创建虚拟IP
		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
		eipParameter.setPrivateIP(privateIP);
		firewallSoapService.createEIPByFirewall(eipParameter);

		// Step.4 写入日志
		createLog(eipDTO.getTenants(), LookUpConstants.ServiceType.EIP.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	/**
	 * EipDTO -> EIPParameter
	 * 
	 * @param eipDTO
	 * @return
	 */
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
		eipParameter.setIsp(isp.getId());
		eipParameter.getPolicies().addAll(policyParameters);
		eipParameter.getAllPolicies().addAll(allPolicies);
		return eipParameter;
	}

	@Override
	public void dissociateEIP(Integer eipId, Integer serviceId) {

		/**
		 * Step.1 获得EIP、ECS、ELB信息
		 * 
		 * Step.2 删除和其他资源(ECS & ELB)的关联关系
		 * 
		 * Step.3 firwall删除虚拟IP
		 * 
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 获得EIP、ECS、ELB信息
		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(serviceId).getDto();
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(serviceId).getDto();

		// Step.2 删除和其他资源(ECS & ELB)的关联关系
		if (ecsDTO != null) {
			cmdbuildSoapService.deleteMapEcsEip(serviceId, eipId);
		} else if (elbDTO != null) {
			cmdbuildSoapService.deleteMapEipElb(eipId, serviceId);
		}

		// Step.3 firwall删除虚拟IP
		EIPParameter eipParameter = wrapperEIPParameter(eipDTO);
		firewallSoapService.deleteEIPByFirewall(eipParameter);

		// Step.4 写入日志
		createLog(eipDTO.getTenants(), LookUpConstants.ServiceType.EIP.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public void createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds) {

		/**
		 * Step.1 获得未使用的VIP.
		 * 
		 * Step.2 将ELB信息写入CMDBuild
		 * 
		 * Step.3 将ELB端口信息写入CMDBuild
		 * 
		 * Step.4 创建关联关系
		 * 
		 * Step.5 调用loadbalancer 接口创建ELB对象
		 * 
		 * Step.6 更改VIP状态
		 * 
		 * Step.7 写入日志
		 * 
		 */

		// Step.1 获得未使用的VIP.
		IpaddressDTO ipaddressDTO = getVIP();

		// Step.2 将ELB信息写入CMDBuild
		elbDTO.setIdc(ipaddressDTO.getIdc());
		elbDTO.setIpaddress(ipaddressDTO.getId());
		elbDTO.setDescription(ipaddressDTO.getDescription());
		cmdbuildSoapService.createElb(elbDTO);

		// Step.3 将ELB端口信息写入CMDBuild

		ElbDTO dto = getElbDTO(ipaddressDTO.getId(), elbDTO.getTenants());

		for (Integer ecsId : ecsIds) {
			cmdbuildSoapService.createMapEcsElb(ecsId, dto.getId());
		}

		for (ElbPolicyDTO policyDTO : elbPolicyDTOs) {

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();

			ElbPolicyDTO elbPolicyDTO = new ElbPolicyDTO();

			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(policyDTO.getIpaddress())).getDto();

			IpaddressDTO ipDto = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

			elbPolicyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getSourcePort() + "-"
					+ policyDTO.getTargetPort());
			elbPolicyDTO.setElb(dto.getId());
			elbPolicyDTO.setElbProtocol(policyDTO.getElbProtocol());
			elbPolicyDTO.setSourcePort(policyDTO.getSourcePort());
			elbPolicyDTO.setTargetPort(policyDTO.getTargetPort());
			elbPolicyDTO.setIpaddress(ipDto.getDescription());

			cmdbuildSoapService.createElbPolicy(elbPolicyDTO);
		}

		// 调用loadbalancer 接口创建ELB对象
		loadbalancerSoapService.createELBByLoadbalancer(wrapperELBParameter(dto));

		// 更改VIP状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// 写入日志
		createLog(elbDTO.getTenants(), LookUpConstants.ServiceType.ELB.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());

	}

	/**
	 * ElbDTO -> ElbParameter
	 * 
	 * @param eipDTO
	 * @return
	 */
	private ELBParameter wrapperELBParameter(ElbDTO elbDTO) {

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();
		List<ELBPolicyParameter> policyParameters = new ArrayList<ELBPolicyParameter>();

		// 查询出ECS和ELB的关联关系
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", elbDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Object> ecsElbDTOs = cmdbuildSoapService.getMapEcsElbList(searchParams).getDtoList().getDto();

		// 删除elb下所有policy,同时填充用于loadbalancer agent的对象.
		for (Object object : ecsElbDTOs) {

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();

			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsElbDTO.getIdObj1())).getDto();
			IpaddressDTO ecsIpaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress())
					.getDto();

			DTOListResult policyResult = getElbPolicyDTOList(Integer.valueOf(mapEcsElbDTO.getIdObj2()));

			for (Object obj : policyResult.getDtoList().getDto()) {

				ElbPolicyDTO policyDTO = (ElbPolicyDTO) obj;

				LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();

				ELBPolicyParameter elbPolicyParameter = new ELBPolicyParameter();
				elbPolicyParameter.setProtocolText(lookUpDTO.getDescription());
				elbPolicyParameter.setSourcePort(policyDTO.getSourcePort());
				elbPolicyParameter.setTargetPort(policyDTO.getTargetPort());

				policyParameters.add(elbPolicyParameter);

				cmdbuildSoapService.deleteElbPolicy(policyDTO.getId());
			}

			publicIPParameter.setIpaddress(ecsIpaddressDTO.getDescription());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);
			publicIPParameters.add(publicIPParameter);
		}

		ELBParameter elbParameter = new ELBParameter();

		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(elbDTO.getIpaddress()).getDto();
		elbParameter.setVip(ipaddressDTO.getDescription());
		elbParameter.getPublicIPs().addAll(publicIPParameters);

		return elbParameter;
	}

	/**
	 * 根据description查询LookUp对象
	 */
	private LookUpDTO getLookUpDTOByDescription(String description) {
		return getLookUpDTO(description, null);
	}

	private LookUpDTO getLookUpDTO(String description, String typeStr) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", description);
		if (StringUtils.isNotBlank(typeStr)) {
			map.put("EQ_type", typeStr);
		}
		return (LookUpDTO) cmdbuildSoapService.findLookUpByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	@Override
	public void deleteELB(Integer elbId) {

		/**
		 * Step.1 获得elb.
		 * 
		 * Step.2 初始化VIP
		 * 
		 * Step.3 查询elb下所有policy并删除
		 * 
		 * Step.4 删除elb
		 * 
		 * Step.5 写入日志
		 */

		// 获得EIP.
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(elbId).getDto();

		// 初始化VIP
		cmdbuildSoapService.initIpaddress(elbDTO.getIpaddress());

		// 删除loadbalancer中的ELB对象
		loadbalancerSoapService.deleteELBByLoadbalancer(wrapperELBParameter(elbDTO));

		// 删除elb下所有policy

		// Step.3 查询EIP下所有policy并删除
		DTOListResult policyResult = getElbPolicyDTOList(elbId);

		for (Object obj : policyResult.getDtoList().getDto()) {
			ElbPolicyDTO policyDTO = (ElbPolicyDTO) obj;
			cmdbuildSoapService.deleteElbPolicy(policyDTO.getId());
		}

		// 删除CMDBuild中的elb
		cmdbuildSoapService.deleteElb(elbId);

		// 写入日志

		createLog(elbDTO.getTenants(), LookUpConstants.ServiceType.ELB.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void associateELB(ELBParameter elbParameter, Integer elbId) {

		/**
		 * Step.1 获得ELB.
		 * 
		 * Step.2 删除cmdbuild中ELB端口的数据和关联关系
		 * 
		 * Step.3 删除loadbalancer上的对象
		 * 
		 * Step.4 CMDBuild中创建ELB端口数据
		 * 
		 * Step.5 在loadbalancer上创建elb对象
		 * 
		 * Step.6 写入日志
		 */

		// 获得ELB.
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(elbId).getDto();

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();
		List<ELBPolicyParameter> policyParameters = new ArrayList<ELBPolicyParameter>();

		// 查询出ECS和ELB的关联关系
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", elbDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Object> ecsElbDTOs = cmdbuildSoapService.getMapEcsElbList(searchParams).getDtoList().getDto();

		// 删除elb下所有policy,同时填充用于loadbalancer agent的对象.
		for (Object object : ecsElbDTOs) {

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();

			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;

			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsElbDTO.getIdObj1())).getDto();
			IpaddressDTO ecsIpaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress())
					.getDto();

			DTOListResult policyResult = getElbPolicyDTOList(Integer.valueOf(mapEcsElbDTO.getIdObj2()));

			for (Object obj : policyResult.getDtoList().getDto()) {

				ElbPolicyDTO policyDTO = (ElbPolicyDTO) obj;

				LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();

				ELBPolicyParameter elbPolicyParameter = new ELBPolicyParameter();
				elbPolicyParameter.setProtocolText(lookUpDTO.getDescription());
				elbPolicyParameter.setSourcePort(policyDTO.getSourcePort());
				elbPolicyParameter.setTargetPort(policyDTO.getTargetPort());

				policyParameters.add(elbPolicyParameter);

				cmdbuildSoapService.deleteElbPolicy(policyDTO.getId());
			}

			publicIPParameter.setIpaddress(ecsIpaddressDTO.getDescription());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);

			publicIPParameters.add(publicIPParameter);
		}

		// 删除CMDBuild中的elb
		cmdbuildSoapService.deleteElb(elbId);

		// 删除关联关系
		for (Object object : ecsElbDTOs) {
			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;
			cmdbuildSoapService.deleteMapEcsElb(Integer.valueOf(mapEcsElbDTO.getIdObj1()),
					Integer.valueOf(mapEcsElbDTO.getIdObj2()));
		}

		// CMDBuild中创建ELB端口数据

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				ElbPolicyDTO policyDTO = new ElbPolicyDTO();

				LookUpDTO lookUpDTO = getLookUpDTOByDescription(policyParameter.getProtocolText());

				policyDTO.setDescription(policyParameter.getProtocolText() + "-" + policyParameter.getSourcePort()
						+ "-" + policyParameter.getTargetPort());
				policyDTO.setElb(elbDTO.getId());
				policyDTO.setElbProtocol(lookUpDTO.getId());
				policyDTO.setSourcePort(policyParameter.getSourcePort());
				policyDTO.setTargetPort(policyParameter.getTargetPort());
				policyDTO.setIpaddress(ipParameter.getIpaddress());

				cmdbuildSoapService.createElbPolicy(policyDTO);
			}

		}

		// 调用loadbalancer 接口创建ELB对象
		loadbalancerSoapService.createELBByLoadbalancer(elbParameter);

		// 在loadbalancer上创建elb对象
		elbParameter.setVip(elbDTO.getIpaddressDTO().getDescription());
		loadbalancerSoapService.createELBByLoadbalancer(elbParameter);

		// 写入日志

		createLog(elbDTO.getTenants(), LookUpConstants.ServiceType.ELB.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void dissociateELB(ELBParameter elbParameter, Integer elbId) {
		/**
		 * Step.1 获得ELB.
		 * 
		 * Step.2 删除cmdbuild中ELB端口的数据和关联关系
		 * 
		 * Step.3 删除loadbalancer上的对象
		 * 
		 * Step.4 CMDBuild中创建ELB端口数据
		 * 
		 * Step.5 在loadbalancer上创建elb对象
		 * 
		 * Step.6 写入日志
		 */

		// 获得ELB.
		ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(elbId).getDto();

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();
		List<ELBPolicyParameter> policyParameters = new ArrayList<ELBPolicyParameter>();

		// 查询出ECS和ELB的关联关系
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", elbDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Object> ecsElbDTOs = cmdbuildSoapService.getMapEcsElbList(searchParams).getDtoList().getDto();

		// 删除elb下所有policy,同时填充用于loadbalancer agent的对象.
		for (Object object : ecsElbDTOs) {

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();

			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;

			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsElbDTO.getIdObj1())).getDto();
			IpaddressDTO ecsIpaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress())
					.getDto();

			DTOListResult policyResult = getElbPolicyDTOList(Integer.valueOf(mapEcsElbDTO.getIdObj2()));

			for (Object obj : policyResult.getDtoList().getDto()) {

				ElbPolicyDTO policyDTO = (ElbPolicyDTO) obj;

				LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();

				ELBPolicyParameter elbPolicyParameter = new ELBPolicyParameter();
				elbPolicyParameter.setProtocolText(lookUpDTO.getDescription());
				elbPolicyParameter.setSourcePort(policyDTO.getSourcePort());
				elbPolicyParameter.setTargetPort(policyDTO.getTargetPort());

				policyParameters.add(elbPolicyParameter);

				cmdbuildSoapService.deleteElbPolicy(policyDTO.getId());
			}

			publicIPParameter.setIpaddress(ecsIpaddressDTO.getDescription());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);

			publicIPParameters.add(publicIPParameter);
		}

		// 删除CMDBuild中的elb
		cmdbuildSoapService.deleteElb(elbId);

		// 删除关联关系
		for (Object object : ecsElbDTOs) {
			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;
			cmdbuildSoapService.deleteMapEcsElb(Integer.valueOf(mapEcsElbDTO.getIdObj1()),
					Integer.valueOf(mapEcsElbDTO.getIdObj2()));
		}

		// CMDBuild中创建ELB端口数据

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				ElbPolicyDTO policyDTO = new ElbPolicyDTO();

				LookUpDTO lookUpDTO = getLookUpDTOByDescription(policyParameter.getProtocolText());

				policyDTO.setDescription(policyParameter.getProtocolText() + "-" + policyParameter.getSourcePort()
						+ "-" + policyParameter.getTargetPort());
				policyDTO.setElb(elbDTO.getId());
				policyDTO.setElbProtocol(lookUpDTO.getId());
				policyDTO.setSourcePort(policyParameter.getSourcePort());
				policyDTO.setTargetPort(policyParameter.getTargetPort());
				policyDTO.setIpaddress(ipParameter.getIpaddress());

				cmdbuildSoapService.createElbPolicy(policyDTO);
			}

		}

		// 调用loadbalancer 接口创建ELB对象
		loadbalancerSoapService.createELBByLoadbalancer(elbParameter);

		// 在loadbalancer上创建elb对象
		elbParameter.setVip(elbDTO.getIpaddressDTO().getDescription());
		loadbalancerSoapService.createELBByLoadbalancer(elbParameter);

		// 写入日志
		createLog(elbDTO.getTenants(), LookUpConstants.ServiceType.ELB.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void createDNS(DNSParameter dnsParameter, Integer tenantsId, Integer idcId, Integer agentTypeId) {

		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setTenants(tenantsId);
		dnsDTO.setIdc(idcId);
		dnsDTO.setDomainName(dnsParameter.getDomianName());
		dnsDTO.setAgentType(agentTypeId);
		dnsDTO.setDescription(dnsParameter.getDomianName());
		cmdbuildSoapService.createDns(dnsDTO);

		DnsDTO dto = getDnsDTO(dnsDTO.getDescription(), tenantsId);

		for (DNSPublicIPParameter publicIPParameter : dnsParameter.getPublicIPs()) {

			IpaddressDTO ipaddressDTO = getIpaddressDTOByDescription(publicIPParameter.getIpaddress(), idcId);

			EcsDTO ecsDTO = getEcsDTO(ipaddressDTO.getId());

			cmdbuildSoapService.createMapEipDns(ecsDTO.getId(), dto.getId());

			for (DNSPolicyParameter policyParameter : publicIPParameter.getPolicyParameters()) {

				LookUpDTO lookUpDTO = getLookUpDTO(policyParameter.getProtocolText(), "DNSProtocol");

				DnsPolicyDTO dnsPolicyDTO = new DnsPolicyDTO();
				dnsPolicyDTO.setDnsProtocol(lookUpDTO.getId());
				dnsPolicyDTO.setPort(policyParameter.getPort().toString());
				dnsPolicyDTO.setDescription(dto.getDomainName());

				cmdbuildSoapService.createDnsPolicy(dnsPolicyDTO);

			}

		}

		dnsSoapService.createDNSByDNS(dnsParameter);

		// 写入日志
		createLog(dto.getTenants(), LookUpConstants.ServiceType.DNS.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());

	}

	private DnsDTO getDnsDTO(String description, Integer tenantsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", description);
		map.put("EQ_tenants", tenantsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (DnsDTO) cmdbuildSoapService.findDnsByParams(searchParams).getDto();
	}

	private EcsDTO getEcsDTO(Integer ipaddressId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_ipaddress", ipaddressId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (EcsDTO) cmdbuildSoapService.findEcsByParams(searchParams).getDto();
	}

	private IpaddressDTO getIpaddressDTOByDescription(String description, Integer idcId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", description);
		if (idcId != null) {
			map.put("EQ_idc", idcId.toString());
		}
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		return (IpaddressDTO) cmdbuildSoapService.findIpaddressByParams(searchParams).getDto();
	}

	@Override
	public void deleteDNS(Integer dnsId) {

		DnsDTO dnsDTO = (DnsDTO) cmdbuildSoapService.findDns(dnsId).getDto();

		cmdbuildSoapService.deleteDns(dnsId);

		LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(dnsDTO.getDomainType()).getDto();
		List<DNSPublicIPParameter> publicIPParameters = new ArrayList<DNSPublicIPParameter>();
		List<DNSPolicyParameter> policyParameters = new ArrayList<DNSPolicyParameter>();

		// 查询出ECS和ELB的关联关系
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", dnsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Object> eipDnsDTOs = cmdbuildSoapService.getMapEipDnsList(searchParams).getDtoList().getDto();

		for (Object object : eipDnsDTOs) {

			DNSPublicIPParameter publicIPParameter = new DNSPublicIPParameter();

			MapEipDnsDTO mapEipDnsDTO = (MapEipDnsDTO) object;

			EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(Integer.valueOf(mapEipDnsDTO.getIdObj1())).getDto();

			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(eipDTO.getIpaddress())
					.getDto();

			DTOListResult policyResult = geDnsPolicyDTOList(Integer.valueOf(mapEipDnsDTO.getIdObj2()));

			for (Object obj : policyResult.getDtoList().getDto()) {

				DnsPolicyDTO policyDTO = (DnsPolicyDTO) obj;

				LookUpDTO protocolLookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getDnsProtocol())
						.getDto();

				DNSPolicyParameter policyParameter = new DNSPolicyParameter();
				policyParameter.setPort(Integer.valueOf(policyDTO.getPort()));
				policyParameter.setProtocolText(protocolLookUpDTO.getDescription());

				policyParameters.add(policyParameter);

				cmdbuildSoapService.deleteElbPolicy(policyDTO.getId());
			}

			publicIPParameter.setIpaddress(ipaddressDTO.getDescription());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);
			publicIPParameters.add(publicIPParameter);

		}

		DNSParameter dnsParameter = new DNSParameter();
		dnsParameter.setDomianName(dnsDTO.getDomainName());
		dnsParameter.setDomianType(lookUpDTO.getDescription());
		dnsParameter.getPublicIPs().addAll(publicIPParameters);

		dnsSoapService.deleteDNSByDNS(dnsParameter);

		// 写入日志

		createLog(dnsDTO.getTenants(), LookUpConstants.ServiceType.DNS.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
	}

	@Override
	public void createESG(Integer tenantsId) {

		/**
		 * Step1. 查询出租户的默认策略.
		 * 
		 * Step2. 写入日志
		 */

		EsgDTO esgDTO = new EsgDTO();
		esgDTO.setAgentType(LookUpConstants.AgentType.H3C.getValue());
		esgDTO.setIsDefault(false);
		esgDTO.setTenants(tenantsId);

		cmdbuildSoapService.createEsg(esgDTO);

		// TODO 缺少策略信息

		// 写入日志

		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void deleteESG(Integer esgId) {

		/**
		 * Step1. 查询Esg信息
		 * 
		 * Step2. 解绑关联信息
		 * 
		 * Step3. 查出租户所有的ESG策略
		 * 
		 * Step4. 去除删除ESG关联的策略后重新执行
		 * 
		 * Step5. 删除CMDBuild的关联信息和ESG信息.
		 * 
		 * Step6. 写入日志
		 */

		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(esgDTO.getTenants()).getDto();

		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);

		List<RuleParameter> denys = new ArrayList<RuleParameter>();
		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		// 获得这个租户下所有的esg下的策略
		for (Object object : geEsgDTOList(esgDTO.getTenants()).getDtoList().getDto()) {

			EsgDTO dto = (EsgDTO) object;

			// 将删除的Esg跳过
			if (!dto.getId().equals(esgId)) {

				for (Object object2 : geEsgPolicyDTOList(dto.getId()).getDtoList().getDto()) {

					EsgPolicyDTO policyDTO = (EsgPolicyDTO) object2;

					RuleParameter ruleParameter = new RuleParameter();

					ruleParameter.setSource(policyDTO.getSourceIP());
					ruleParameter.setSourceNetMask("0.0.0.0");
					ruleParameter.setDestination(policyDTO.getTargetIP());

					ruleParameter.setDestinationNetMask("0.0.0.0");

					if (LookUpConstants.PolicyType.Permit.getValue().equals(policyDTO.getPolicyType())) { // prmits
						denys.add(ruleParameter);
					} else {// denys
						denys.add(ruleParameter);
					}
				}

			}
		}

		ESGParameter esgParameter = new ESGParameter();
		esgParameter.setAclNumber(tenantsDTO.getAclNumber());
		esgParameter.setDesc("");
		esgParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		esgParameter.getDenys().addAll(denys);
		esgParameter.getPermits().addAll(permits);

		switchesSoapService.createESGBySwtich(esgParameter);

		// 删除策略
		for (Object obj : getElbPolicyDTOList(esgId).getDtoList().getDto()) {

			EsgPolicyDTO policyDTO = (EsgPolicyDTO) obj;

			cmdbuildSoapService.deleteEsgPolicy(policyDTO.getId());
		}

		// 删除esg信息
		cmdbuildSoapService.deleteEsg(esgId);

		// 写入日志

		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());

	}

	/**
	 * 获得ESG下所有的policy
	 */
	private DTOListResult geEsgPolicyDTOList(Integer esgId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_esg", esgId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getEsgPolicyList(searchParams);
		return listResult;
	}

	/**
	 * 获得ESG下所有的policy
	 */
	private DTOListResult geEsgDTOList(Integer tenantsId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_tenants", tenantsId.toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult listResult = cmdbuildSoapService.getEsgList(searchParams);
		return listResult;
	}

	@Override
	public void associateESG(Integer ecsId, Integer esgId) {

		cmdbuildSoapService.createMapEcsEsg(ecsId, esgId);

		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(esgDTO.getTenants()).getDto();

		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);

		List<RuleParameter> denys = new ArrayList<RuleParameter>();
		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		// 获得这个租户下所有的esg下的策略
		for (Object object : geEsgDTOList(esgDTO.getTenants()).getDtoList().getDto()) {

			EsgDTO dto = (EsgDTO) object;

			for (Object object2 : geEsgPolicyDTOList(dto.getId()).getDtoList().getDto()) {

				EsgPolicyDTO policyDTO = (EsgPolicyDTO) object2;

				RuleParameter ruleParameter = new RuleParameter();

				ruleParameter.setSource(policyDTO.getSourceIP());
				ruleParameter.setSourceNetMask("0.0.0.0");
				ruleParameter.setDestination(policyDTO.getTargetIP());
				ruleParameter.setDestinationNetMask("0.0.0.0");

				if (LookUpConstants.PolicyType.Permit.getValue().equals(policyDTO.getPolicyType())) { // prmits
					permits.add(ruleParameter);
				} else {// denys
					denys.add(ruleParameter);
				}
			}
		}

		RuleParameter newRuleParameter = new RuleParameter();
		newRuleParameter.setSource(ecsDTO.getIpaddressDTO().getDescription());
		newRuleParameter.setSourceNetMask("0.0.0.0");
		newRuleParameter.setDestination("");// TODO 明日完成
		newRuleParameter.setDestinationNetMask("0.0.0.0");
		permits.add(newRuleParameter);

		ESGParameter esgParameter = new ESGParameter();
		esgParameter.setAclNumber(tenantsDTO.getAclNumber());
		esgParameter.setDesc("");
		esgParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		esgParameter.getDenys().addAll(denys);
		esgParameter.getPermits().addAll(permits);

		switchesSoapService.createESGBySwtich(esgParameter);

		// 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void dissociateESG(Integer esgId) {

		/**
		 * Step1. 查询Esg信息
		 * 
		 * Step2. 解绑关联信息
		 * 
		 * Step3. 查出租户所有的ESG策略
		 * 
		 * Step4. 去除删除ESG关联的策略后重新执行
		 * 
		 * Step5. 删除CMDBuild的关联信息和ESG信息.
		 * 
		 * Step6. 写入日志
		 */

		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(esgDTO.getTenants()).getDto();

		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_idObj2", esgDTO.getId().toString());
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Object> ecsEsgDTOs = cmdbuildSoapService.getMapEcsEsgList(searchParams).getDtoList().getDto();

		for (Object object : ecsEsgDTOs) {
			MapEcsEsgDTO mapEcsEsgDTO = (MapEcsEsgDTO) object;
			cmdbuildSoapService.deleteMapEcsEsg(Integer.valueOf(mapEcsEsgDTO.getIdObj1()), esgId);
		}

		List<RuleParameter> denys = new ArrayList<RuleParameter>();
		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		// 获得这个租户下所有的esg下的策略
		for (Object object : geEsgDTOList(esgDTO.getTenants()).getDtoList().getDto()) {

			EsgDTO dto = (EsgDTO) object;

			// 将删除的Esg跳过
			if (!dto.getId().equals(esgId)) {

				for (Object object2 : geEsgPolicyDTOList(dto.getId()).getDtoList().getDto()) {

					EsgPolicyDTO policyDTO = (EsgPolicyDTO) object2;

					RuleParameter ruleParameter = new RuleParameter();

					ruleParameter.setSource(policyDTO.getSourceIP()); // TODO 此处的源应该是关联的ECS IP target是定义好的ESG中的策略.
					ruleParameter.setSourceNetMask("0.0.0.0"); // TODO 自定义的话只能选择虚拟机,即单IP,所以暂时写死成0.0.0.0
					ruleParameter.setDestination(policyDTO.getTargetIP());

					ruleParameter.setDestinationNetMask("0.0.0.0");

					if (LookUpConstants.PolicyType.Permit.getValue().equals(policyDTO.getPolicyType())) { // prmits
						permits.add(ruleParameter);
					} else {// denys
						denys.add(ruleParameter);
					}
				}

			}
		}

		ESGParameter esgParameter = new ESGParameter();
		esgParameter.setAclNumber(tenantsDTO.getAclNumber());
		esgParameter.setDesc("");
		esgParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		esgParameter.getDenys().addAll(denys);
		esgParameter.getPermits().addAll(permits);

		switchesSoapService.createESGBySwtich(esgParameter);

		// 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public List<TenantsDTO> getTenantsDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<TenantsDTO> list = new ArrayList<TenantsDTO>();
		for (Object obj : cmdbuildSoapService.getTenantsList(searchParams).getDtoList().getDto()) {
			list.add((TenantsDTO) obj);
		}
		return list;
	}

	@Override
	public List<IdcDTO> getIdcDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<IdcDTO> list = new ArrayList<IdcDTO>();
		for (Object obj : cmdbuildSoapService.getIdcList(searchParams).getDtoList().getDto()) {
			list.add((IdcDTO) obj);
		}
		return list;
	}

	@Override
	public List<EcsSpecDTO> getEcsSpecDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<EcsSpecDTO> list = new ArrayList<EcsSpecDTO>();
		for (Object obj : cmdbuildSoapService.getEcsSpecList(searchParams).getDtoList().getDto()) {
			list.add((EcsSpecDTO) obj);
		}
		return list;
	}

	@Override
	public List<EcsDTO> getEcsDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<EcsDTO> list = new ArrayList<EcsDTO>();
		for (Object obj : cmdbuildSoapService.getEcsList(searchParams).getDtoList().getDto()) {
			list.add((EcsDTO) obj);
		}
		return list;
	}

	@Override
	public List<Es3DTO> getEs3DTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<Es3DTO> list = new ArrayList<Es3DTO>();
		for (Object obj : cmdbuildSoapService.getEs3List(searchParams).getDtoList().getDto()) {
			list.add((Es3DTO) obj);
		}
		return list;
	}

	@Override
	public List<EipDTO> getEipDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<EipDTO> list = new ArrayList<EipDTO>();
		for (Object obj : cmdbuildSoapService.getEipList(searchParams).getDtoList().getDto()) {
			list.add((EipDTO) obj);
		}
		return list;
	}

	@Override
	public List<ElbDTO> getElbDTO() {
		HashMap<String, String> map = new HashMap<String, String>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<ElbDTO> list = new ArrayList<ElbDTO>();
		for (Object obj : cmdbuildSoapService.getElbList(searchParams).getDtoList().getDto()) {
			list.add((ElbDTO) obj);
		}
		return list;
	}

}
