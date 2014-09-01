package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.api.webservice.response.result.WSResult;
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
import com.sobey.generate.cmdbuild.MapEcsEs3DTO;
import com.sobey.generate.cmdbuild.MapEcsEsgDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.TagDTO;
import com.sobey.generate.cmdbuild.TagRelation;
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
import com.sobey.generate.zabbix.ZItemDTO;
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

	// 临时数据
	public static Integer serverId = 143;

	public static Integer storageId = 161;

	/**
	 * 默认管理网段:10.10.1.0
	 */
	private static String default_Segment_1 = "10.10.1.0";

	/**
	 * 默认管理网段:10.10.2.0
	 */
	private static String default_Segment_2 = "10.10.2.0";

	/**
	 * ESG的默认名.
	 */
	private static String default_esg_name = "默认策略";

	private static String default_netapp_ip = "10.10.2.34";

	@Override
	public WSResult createTenants(TenantsDTO tenantsDTO) {

		WSResult result = new WSResult();

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

		// Step.2 分配Vlan,每个租户都会分配一个Vlan.
		VlanDTO vlanDTO = allocationVlan(tenants);

		if (vlanDTO == null) {
			result.setError(WSResult.SYSTEM_ERROR, "Vlan资源不足,请联系管理员.");
			return result;
		}

		// Step.3 创建VPN账号
		createVPN(tenants, vlanDTO);

		// Step.4 创建默认Esg
		createDefaultEsg(tenants, vlanDTO);

		result.setMessage("租户创建成功");
		return result;
	}

	/**
	 * 创建TenantsDTO,并返回TenantsDTO对象.
	 * 
	 * @param tenantsDTO
	 * @return
	 */
	private TenantsDTO createTenantsDTO(TenantsDTO tenantsDTO) {

		cmdbuildSoapService.createTenants(tenantsDTO);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", tenantsDTO.getDescription());

		return (TenantsDTO) cmdbuildSoapService.findTenantsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	/**
	 * 分配Vlan<br>
	 * 
	 * 查出一个未使用的vlan,并将该vlan分配给租户,更新vlan的状态<br>
	 * 
	 * 如果没有未使用的vlan,则返回null
	 * 
	 * @param tenantsDTO
	 * @return
	 */
	private VlanDTO allocationVlan(TenantsDTO tenantsDTO) {

		// 查询出未被使用的vlan
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_vlanStatus", LookUpConstants.VlanStatus.未使用.getValue());
		DTOListResult dtoListResult = cmdbuildSoapService.getVlanList(CMDBuildUtil.wrapperSearchParams(map));

		if (dtoListResult.getDtoList().getDto().isEmpty()) {
			return null;
		}

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
	private void createVPN(TenantsDTO tenantsDTO, VlanDTO vlanDTO) {

		Integer policyId = cmdbuildSoapService.getMaxPolicyId();

		// 在firewall上创建VPN用户
		VPNUserParameter parameter = new VPNUserParameter();
		parameter.setVpnUser(tenantsDTO.getDescription());
		parameter.setVpnPassword(tenantsDTO.getPassword());
		parameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		parameter.setNetMask(vlanDTO.getNetMask());
		parameter.setFirewallPolicyId(policyId);

		// 填充网段
		List<String> segments = new ArrayList<String>();
		segments.add(vlanDTO.getSegment());
		parameter.getSegments().addAll(segments);

		// 填充网关
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
		vpnDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());

		cmdbuildSoapService.createVpn(vpnDTO);
	}

	/**
	 * 创建默认Esg
	 * 
	 * @param tenantsDTO
	 * @param vlanDTO
	 */
	private void createDefaultEsg(TenantsDTO tenantsDTO, VlanDTO vlanDTO) {

		/**
		 * Step.1 为租户创建默认的Acl Number
		 * 
		 * Step.2 将ESG保存在CMDBuild中
		 * 
		 * Step.3 将ESG策略保存在CMDBuild
		 */

		// Step.1 为租户创建默认的Acl Number

		// 允许列表
		List<RuleParameter> permits = new ArrayList<RuleParameter>();

		// 默认的permit列表包含10.10.1.0和10.10.2.0两个管理网段
		RuleParameter permitsRule1 = new RuleParameter();
		permitsRule1.setSource(vlanDTO.getSegment());
		permitsRule1.setSourceNetMask(getNetMask(vlanDTO.getSegment()));
		permitsRule1.setDestination(default_Segment_1);
		permitsRule1.setDestinationNetMask(getNetMask(default_Segment_1));

		permits.add(permitsRule1);

		RuleParameter permitsRule2 = new RuleParameter();
		permitsRule2.setSource(vlanDTO.getSegment());
		permitsRule2.setSourceNetMask(getNetMask(vlanDTO.getSegment()));
		permitsRule2.setDestination(default_Segment_2);
		permitsRule2.setDestinationNetMask(getNetMask(default_Segment_2));

		permits.add(permitsRule2);

		// 拒绝列表
		List<RuleParameter> denys = new ArrayList<RuleParameter>();

		// 拒绝所有其他网段访问
		RuleParameter denysRule = new RuleParameter();
		denysRule.setSource(vlanDTO.getSegment());
		denysRule.setSourceNetMask(getNetMask(vlanDTO.getSegment()));
		denysRule.setDestination("0.0.0.0"); // 表示所有的网段
		denysRule.setDestinationNetMask("0");

		denys.add(denysRule);

		// 交换机上创建Acl
		ESGParameter parameter = new ESGParameter();
		parameter.setAclNumber(tenantsDTO.getAclNumber());
		parameter.setDesc(tenantsDTO.getDescription());
		parameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		parameter.getPermits().addAll(permits);
		parameter.getDenys().addAll(denys);

		switchesSoapService.createESGBySwtich(parameter);

		// Step.2 将ESG保存在CMDBuild中

		EsgDTO dto = new EsgDTO();
		dto.setDescription(default_esg_name);
		dto.setIsDefault(true);
		dto.setIdc(vlanDTO.getIdc());
		dto.setRemark(tenantsDTO.getDescription() + default_esg_name);
		dto.setTenants(tenantsDTO.getId());
		dto.setAgentType(LookUpConstants.AgentType.H3C.getValue());

		cmdbuildSoapService.createEsg(dto);

		// Step.3 将ESG策略保存在CMDBuild
		createEsgPolicyInCMDBuild(parameter, dto, tenantsDTO);
	}

	/**
	 * CMDBuild中创建ESG策略
	 * 
	 * @param parameter
	 * @param esgDTO
	 * @param tenantsDTO
	 */
	private void createEsgPolicyInCMDBuild(ESGParameter parameter, EsgDTO esgDTO, TenantsDTO tenantsDTO) {

		EsgDTO dto = getEsgDTOByDescription(esgDTO);// 获得租户的默认esg.

		// Denys
		for (RuleParameter ruleParameter : parameter.getDenys()) {
			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(dto.getId());
			esgPolicyDTO.setPolicyType(LookUpConstants.PolicyType.Deny.getValue());
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Denys-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());
			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}

		// Permits
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
	 * 根据 description 返回租户下指定的EsgDTO对象
	 */
	private EsgDTO getEsgDTOByDescription(EsgDTO esgDTO) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", esgDTO.getDescription());
		map.put("EQ_tenants", esgDTO.getTenants());
		return (EsgDTO) cmdbuildSoapService.findEsgByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	/**
	 * 将VlanDTO的description进行分解,去掉前缀vlan后,得到数字.<br>
	 * 
	 * 注意: description的格式必须为"Vlan + 数字"<br>
	 * 
	 * eg: vlan100 -> 100
	 * 
	 * @param description
	 * @return
	 */
	private Integer getVlanId(String description) {
		return Integer.valueOf(StringUtils.remove(description.toLowerCase(), "vlan"));
	}

	@Override
	public WSResult createECS(EcsDTO ecsDTO) {

		WSResult result = new WSResult();

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
		cloneVMParameter.setVMName(generateVMName(tenantsDTO, ipaddressDTO));
		cloneVMParameter.setVMTemplateOS(ecsSpecDTO.getOsTypeText());
		cloneVMParameter.setVMTemplateName(ecsSpecDTO.getImageName());
		cloneVMParameter.setVMSUserName("sobey");
		cloneVMParameter.setSubNetMask(vlanDTO.getNetMask());
		cloneVMParameter.setGateway(vlanDTO.getGateway());
		cloneVMParameter.setVlanId(getVlanId(vlanDTO.getDescription()));
		cloneVMParameter.setIpaddress(ipaddressDTO.getDescription());
		cloneVMParameter.setDescription(ecsDTO.getRemark());
		cloneVMParameter.setDatacenter(idcDTO.getRemark());

		if (!WSResult.SUCESS.equals(instanceSoapService.cloneVMByInstance(cloneVMParameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ECS创建失败,请联系管理员.");
			return result;
		}

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

		result.setMessage("ECS创建成功");
		return result;
	}

	/**
	 * 创建操作日志
	 * 
	 * @param tenantsId
	 * @param serviceTypeId
	 *            {@link LookUpConstants}
	 * @param operateTypeId
	 *            {@link LookUpConstants}
	 * @param resultId
	 *            {@link LookUpConstants}
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
	 * 
	 * @param tenantsDTO
	 * @return
	 */
	private VlanDTO getVlanDTO(TenantsDTO tenantsDTO) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsDTO.getId());
		return (VlanDTO) cmdbuildSoapService.findVlanByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
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
		 * Step.5 删除zabbix中的监控
		 * 
		 * Step.6 CMDBuild中删除ECS信息
		 * 
		 * Step.7 写入日志
		 */

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 关闭VM电源
		powerOpsECS(ecsId, LookUpConstants.powerOperation.poweroff.toString());

		// Step.3 销毁虚拟机
		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setDatacenter(idcDTO.getRemark());
		destroyVMParameter.setVMName(generateVMName(tenantsDTO, ipaddressDTO));
		instanceSoapService.destroyVMByInstance(destroyVMParameter);

		// Step.4 初始化虚拟机的IP状态
		cmdbuildSoapService.initIpaddress(ecsDTO.getIpaddress());

		// Step.5 删除zabbix中的监控
		deleteHost(ecsId);

		// Step.6 CMDBuild中删除ECS信息
		cmdbuildSoapService.deleteEcs(ecsId);

		// Step.7 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
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
		 * Step.4 写入日志
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 操作VM电源
		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName(generateVMName(tenantsDTO, ipaddressDTO));
		powerVMParameter.setPowerOperation(powerOperation);
		powerVMParameter.setDatacenter(idcDTO.getRemark());

		if (!WSResult.SUCESS.equals(instanceSoapService.powerVMByInstance(powerVMParameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ECS电源操作失败,请检查ECS电源状态.");
			return result;
		}

		// Step.3 修改ECS的运行状态

		if (LookUpConstants.powerOperation.poweroff.toString().equals(powerOperation)) {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.停止.getValue());
		} else {
			ecsDTO.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		}

		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		// Step.4 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ECS电源操作成功");
		return result;

	}

	/**
	 * 生成vcenter中VM的名称.<br>
	 *
	 * VMWare中虚拟机名称不能重复,所以将VM名称设置为 "用户定义名称-ip地址"
	 * 
	 * eg: xman@sobey.com-10.10.100.1
	 * 
	 * @param tenantsDTO
	 * @param ipaddressDTO
	 * @return
	 */
	private String generateVMName(TenantsDTO tenantsDTO, IpaddressDTO ipaddressDTO) {
		return tenantsDTO.getDescription() + "-" + ipaddressDTO.getDescription();
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
		 * Step.5 写入日志
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS信息
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(ecsDTO.getTenants()).getDto();
		VlanDTO vlanDTO = getVlanDTO(tenantsDTO);
		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(vlanDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		// Step.2 获得ECS Spec信息
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsSpecId).getDto();

		// Step.3 更新VM内存大小和CPU数量

		// MB -> GB ,因为vmware的内存单位是MB,cmdbuild是GB,所以此处做一个单位换算.
		Long memoryMb = (long) MathsUtil.mul(ecsSpecDTO.getMemory(), 1024);

		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setCPUNumber(ecsSpecDTO.getCpuNumber());
		reconfigVMParameter.setDatacenter(idcDTO.getRemark());
		reconfigVMParameter.setMemoryMB(memoryMb);
		reconfigVMParameter.setVMName(generateVMName(tenantsDTO, ipaddressDTO));

		if (!WSResult.SUCESS.equals(instanceSoapService.reconfigVMByInstance(reconfigVMParameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ECS运行状态无法修改配置,请检查ECS电源状态.");
			return result;
		}

		// Step.4 更新CMDBuild ECS规格
		ecsDTO.setEcsSpec(ecsSpecId);
		cmdbuildSoapService.updateEcs(ecsId, ecsDTO);

		// Step.5 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ECS.getValue(),
				LookUpConstants.OperateType.更新.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ECS配置修改成功");
		return result;
	}

	@Override
	public String syncVM(String datacenter) {

		/**
		 * Step.1 获得虚拟机和宿主机的实际关联关系
		 * 
		 * Step.2 获得CMDBuild中的ECS和Server的关联关系
		 * 
		 * Step.3 比较两个关联关系,已从vcenter中获得的关联关系为准,对CMDBuild数据进行更新
		 */

		// Step.1 获得虚拟机和宿主机的实际关联关系
		HashMap<String, String> vcenterMap = wrapperRelationVM(datacenter);

		StringBuffer sb = new StringBuffer();

		for (java.util.Map.Entry<String, String> entry : vcenterMap.entrySet()) {

			// Step.2 获得CMDBuild中的ECS和Server的关联关系
			// 根据VM的名称获得CMDBuild中的对象Ecs
			HashMap<String, Object> ecsMap = new HashMap<String, Object>();
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

				HashMap<String, Object> serverMap = new HashMap<String, Object>();
				serverMap.put("EQ_description", entry.getValue());

				ServerDTO serverDTO2 = (ServerDTO) cmdbuildSoapService.findServerByParams(
						CMDBuildUtil.wrapperSearchParams(serverMap)).getDto();

				ecsDTO.setServer(serverDTO2.getId());
				cmdbuildSoapService.updateEcs(ecsDTO.getId(), ecsDTO);
			}
		}

		return sb.toString();
	}

	/**
	 * 查询VMWare中VM和Host的实际关联关系,并封装成Map.
	 * 
	 * @param datacenter
	 * @return
	 */
	private HashMap<String, String> wrapperRelationVM(String datacenter) {
		HashMap<String, String> map = new HashMap<String, String>();
		// 将RelationVMParameter转换成HashMap
		for (Entry entry : instanceSoapService.getVMAndHostRelationByInstance(datacenter).getRelationMaps().getEntry()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	public WSResult createES3(Es3DTO es3DTO) {

		/**
		 * Step.1 获得租户、vlan信息
		 * 
		 * Step.2 创建volume
		 * 
		 * Step.3 CMDBuild中创建ES3信息
		 * 
		 * Step.4 写入日志
		 */

		WSResult result = new WSResult();

		// Step.1 获得租户、vlan信息
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(es3DTO.getTenants()).getDto();

		// Step.2 创建volume
		// 卷名在netapp中应该是唯一的,故实际的卷名: 租户定义的名称+租户ID
		String VolumeName = es3DTO.getDescription() + tenantsDTO.getId();

		CreateEs3Parameter createEs3Parameter = new CreateEs3Parameter();
		createEs3Parameter.setVolumeName(VolumeName);
		createEs3Parameter.setVolumeSize(Integer.valueOf(es3DTO.getDiskSize().intValue()).toString());
		createEs3Parameter.setClientIPaddress(default_netapp_ip);// TODO 默认加上控制器IP

		if (!WSResult.SUCESS.equals(storageSoapService.createEs3ByStorage(createEs3Parameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ES3创建失败,请联系管理员.");
			return result;
		}

		// Step.3 CMDBuild中创建ES3信息
		es3DTO.setVolumeName(VolumeName);
		es3DTO.setStorage(storageId);// TODO 通过算法得出负载最轻的Storage(NetappController).

		cmdbuildSoapService.createEs3(es3DTO);

		// Step.4 写入日志
		createLog(tenantsDTO.getId(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ES3创建成功");
		return result;
	}

	@Override
	public WSResult attachES3(Integer es3Id, Integer ecsId) {

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

		WSResult result = new WSResult();

		// Step.1 获得ECS、ES3、Storage、IP信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// Step.2 将访问IP列表写入netapp controller中
		wirteMountRule(es3dto, ecsDTO);

		// Step.3 挂载volume

		// netapp的控制IP
		IpaddressDTO storageIP = (IpaddressDTO) cmdbuildSoapService
				.findIpaddress(es3dto.getStorageDTO().getIpaddress()).getDto();

		// ECS的IP
		IpaddressDTO ecsIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		MountEs3Parameter mountEs3Parameter = new MountEs3Parameter();
		mountEs3Parameter.setClientIPaddress(ecsIP.getDescription());
		mountEs3Parameter.setNetAppIPaddress(storageIP.getDescription());
		mountEs3Parameter.setVolumeName(es3dto.getVolumeName());

		if (!WSResult.SUCESS.equals(storageSoapService.mountEs3ByStorage(mountEs3Parameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ES3挂载失败,请联系管理员.");
			return result;
		}

		// Step.4 将ES3 和ECS的关联关系写入cmdbuild
		cmdbuildSoapService.createMapEcsEs3(ecsId, es3Id);

		// Step.5 写入日志
		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.挂载.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ES3挂载成功");
		return result;

	}

	/**
	 * 向netapp控制器写入可挂载的权限.<br>
	 * 
	 * netapp的权限是要将所有的ip都写入控制器中,但是后面会将前面的规则覆盖掉.<br>
	 * 
	 * 为了保证规则完成,需要将es3关联的所有ECS查询出来放入after、before list中.
	 * 
	 * @param es3dto
	 * @param ecsDTO
	 */
	private void wirteMountRule(Es3DTO es3dto, EcsDTO ecsDTO) {

		List<String> list = getEcsIpaddressByEs3(es3dto);

		// 如果为null加入默认的netapp控制器IP
		if (list.isEmpty()) {
			list.add(default_netapp_ip);
		}

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(es3dto.getVolumeName());
		remountEs3Parameter.getBeforeClientIPaddress().addAll(list);

		list.remove(default_netapp_ip);
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
	 * 
	 * @param es3dto
	 * @param ecsDTO
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
	 * 获得ES3关联的所有ECS的IP集合.
	 * 
	 * @param es3dto
	 * @return
	 */
	private List<String> getEcsIpaddressByEs3(Es3DTO es3dto) {

		// 查询出ECS和ES3的关联关系
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", es3dto.getId());
		List<Object> ecsEs3DTOs = cmdbuildSoapService.getMapEcsEs3List(CMDBuildUtil.wrapperSearchParams(map))
				.getDtoList().getDto();

		List<String> list = new ArrayList<String>();

		for (Object obj : ecsEs3DTOs) {
			MapEcsEs3DTO mapEcsEs3DTO = (MapEcsEs3DTO) obj;
			EcsDTO dto = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEs3DTO.getIdObj1())).getDto();
			list.add(dto.getIpaddressDTO().getDescription());
		}
		return list;
	}

	@Override
	public WSResult detachES3(Integer es3Id, Integer ecsId) {

		/**
		 * Step.1 获得ECS、ES3信息
		 * 
		 * Step.2 ES3的IP从netapp控制器中排除权限<br>
		 * 
		 * Step.3 卸载volume
		 * 
		 * Step.4 将ES3 和ECS的关联关系写入cmdbuild
		 * 
		 * Step.5 写入日志
		 */

		WSResult result = new WSResult();

		// Step.1 获得ECS、ES3、Storage、IP信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();

		// Step.2 ES3的IP从netapp控制器中排除权限<br>
		wirteUmountRule(es3dto, ecsDTO);

		// Step.3 卸载volume
		// ECS的IP
		IpaddressDTO ecsIP = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

		UmountEs3Parameter umountEs3Parameter = new UmountEs3Parameter();
		umountEs3Parameter.setClientIPaddress(ecsIP.getDescription());

		if (!WSResult.SUCESS.equals(storageSoapService.umountEs3ByStorage(umountEs3Parameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ES3卸载失败,请联系管理员.");
			return result;
		}

		// Step.4 将ES3 和ECS的关联关系写入cmdbuild
		cmdbuildSoapService.deleteMapEcsEs3(ecsId, es3Id);

		// Step.5 写入日志
		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.卸载.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ES3卸载成功");
		return result;

	}

	@Override
	public WSResult deleteES3(Integer es3Id) {

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

		WSResult result = new WSResult();

		// Step.1 获得 ES3信息
		Es3DTO es3dto = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();

		// Step.2 删除volume
		DeleteEs3Parameter deleteEs3Parameter = new DeleteEs3Parameter();
		deleteEs3Parameter.setVolumeName(es3dto.getVolumeName());

		if (!WSResult.SUCESS.equals(storageSoapService.deleteEs3ByStorage(deleteEs3Parameter).getCode())) {
			result.setError(WSResult.SYSTEM_ERROR, "ES3删除失败,请联系管理员.");
			return result;
		}

		// Step.3 将CMDBuild中的ES3信息删除
		cmdbuildSoapService.deleteEs3(es3Id);

		// Step.4 写入日志
		createLog(es3dto.getTenants(), LookUpConstants.ServiceType.ES3.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ES3删除成功");
		return result;
	}

	@Override
	public WSResult allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {

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

		result.setMessage("公网IP分配成功");
		return result;
	}

	/**
	 * 获得分配给租户的EIP
	 * 
	 * @param ipaddressId
	 * @param tenantsId
	 * @return
	 */
	private EipDTO getEipDTO(Integer ipaddressId, Integer tenantsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_ipaddress", ipaddressId);
		map.put("EQ_tenants", tenantsId);
		return (EipDTO) cmdbuildSoapService.findEipByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	/**
	 * 获得租户创建的ELB
	 * 
	 * @param ipaddressId
	 * @param tenantsId
	 * @return
	 */
	private ElbDTO getElbDTO(Integer ipaddressId, Integer tenantsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_ipaddress", ipaddressId);
		map.put("EQ_tenants", tenantsId);
		return (ElbDTO) cmdbuildSoapService.findElbByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
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
		return getIpaddress(map);
	}

	/**
	 * 获得未使用的VIP
	 * 
	 * @return
	 */
	private IpaddressDTO getVIP() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue());
		map.put("EQ_ipaddressPool", LookUpConstants.IPAddressPool.VIPPool.getValue());
		return getIpaddress(map);
	}

	/**
	 * 获得未使用的ip地址.
	 * 
	 * @param vlanDTO
	 * @return
	 */
	private IpaddressDTO getUnusedIpaddress(VlanDTO vlanDTO) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_vlan", vlanDTO.getId());
		map.put("EQ_ipaddressStatus", LookUpConstants.IPAddressStatus.未使用.getValue());
		return getIpaddress(map);
	}

	/**
	 * 根据条件获得IP
	 * 
	 * @param map
	 * @return
	 */
	private IpaddressDTO getIpaddress(HashMap<String, Object> map) {
		DTOListResult listResult = cmdbuildSoapService.getIpaddressList(CMDBuildUtil.wrapperSearchParams(map));
		return (IpaddressDTO) listResult.getDtoList().getDto().get(0);
	}

	@Override
	public void recoverEIP(Integer eipId) {

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
	 * 
	 * @param eipId
	 * @return
	 */
	private DTOListResult getEipPolicyDTOList(Integer eipId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_eip", eipId);
		return cmdbuildSoapService.getEipPolicyList(CMDBuildUtil.wrapperSearchParams(map));
	}

	/**
	 * 获得ELB下所有的policy
	 * 
	 * @param elbId
	 * @return
	 */
	private DTOListResult getElbPolicyDTOList(Integer elbId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_elb", elbId);
		return cmdbuildSoapService.getElbPolicyList(CMDBuildUtil.wrapperSearchParams(map));
	}

	/**
	 * 获得DNS下所有的policy
	 * 
	 * @param dnsId
	 * @return
	 */
	private DTOListResult geDnsPolicyDTOList(Integer dnsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_dns", dnsId);
		return cmdbuildSoapService.getDnsPolicyList(CMDBuildUtil.wrapperSearchParams(map));
	}

	/**
	 * 获得租户下所有EIP的所有policy
	 * 
	 * @param eipDTO
	 * @return
	 */
	private DTOListResult getEipDTOListByTenants(EipDTO eipDTO) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", eipDTO.getTenants());
		return cmdbuildSoapService.getEipList(CMDBuildUtil.wrapperSearchParams(map));
	}

	@Override
	public WSResult associateEIP(Integer eipId, Integer serviceId) {

		/**
		 * Step.1 获得EIP、ECS、ELB的信息
		 * 
		 * Step.2 与其他资源(ECS & ELB)建立关联关系
		 * 
		 * Step.3 firwall创建虚拟IP
		 * 
		 * Step.4 写入日志
		 */

		WSResult result = new WSResult();

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

		if (!WSResult.SUCESS.equals(firewallSoapService.createEIPByFirewall(eipParameter).getCode())) {

			// 删除关联关系
			if (elbDTO != null) {
				cmdbuildSoapService.deleteMapEipElb(eipId, serviceId);
			} else if (ecsDTO != null) {
				cmdbuildSoapService.deleteMapEcsEip(serviceId, eipId);
			}

			result.setError(WSResult.SYSTEM_ERROR, "EIP关联失败,请联系管理员.");
			return result;
		}

		// Step.4 写入日志
		createLog(eipDTO.getTenants(), LookUpConstants.ServiceType.EIP.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

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
		DTOListResult allPoliciesResult = getEipDTOListByTenants(eipDTO);
		List<String> allPolicies = new ArrayList<String>();

		System.out.println(allPoliciesResult.getDtoList().getDto().isEmpty());

		for (Object obj : allPoliciesResult.getDtoList().getDto()) {
			EipDTO dto = (EipDTO) obj;

			System.out.println("dto:" + dto.getIpaddress());

			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(dto.getIpaddress()).getDto();

			allPolicies.add(ipaddressDTO.getDescription());
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
	public WSResult createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds) {

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

		WSResult result = new WSResult();

		// Step.1 获得未使用的VIP.
		IpaddressDTO ipaddressDTO = getVIP();

		if (ipaddressDTO == null) {
			result.setError(WSResult.SYSTEM_ERROR, "VIP资源不足,请联系管理员.");
			return result;
		}

		// Step.2 将ELB信息写入CMDBuild
		elbDTO.setIdc(ipaddressDTO.getIdc());
		elbDTO.setIpaddress(ipaddressDTO.getId());
		elbDTO.setDescription(ipaddressDTO.getDescription());
		cmdbuildSoapService.createElb(elbDTO);

		// Step.3 将ELB端口信息写入CMDBuild

		ElbDTO dto = getElbDTO(ipaddressDTO.getId(), elbDTO.getTenants());

		for (ElbPolicyDTO policyDTO : elbPolicyDTOs) {

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(policyDTO.getIpaddress())).getDto();
			IpaddressDTO ipDto = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();

			ElbPolicyDTO elbPolicyDTO = new ElbPolicyDTO();

			elbPolicyDTO.setElb(dto.getId());
			elbPolicyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getSourcePort() + "-"
					+ policyDTO.getTargetPort());
			elbPolicyDTO.setElbProtocol(policyDTO.getElbProtocol());
			elbPolicyDTO.setSourcePort(policyDTO.getSourcePort());
			elbPolicyDTO.setTargetPort(policyDTO.getTargetPort());
			elbPolicyDTO.setIpaddress(ipDto.getDescription());

			cmdbuildSoapService.createElbPolicy(elbPolicyDTO);
		}

		// Step.4 创建关联关系
		for (Integer ecsId : ecsIds) {
			cmdbuildSoapService.createMapEcsElb(ecsId, dto.getId());
		}

		// Step.5 调用loadbalancer 接口创建ELB对象
		loadbalancerSoapService.createELBByLoadbalancer(wrapperELBParameter(dto));

		// Step.6 更改VIP状态
		cmdbuildSoapService.allocateIpaddress(ipaddressDTO.getId());

		// Step.7 写入日志
		createLog(elbDTO.getTenants(), LookUpConstants.ServiceType.ELB.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());

		result.setMessage("ELB创建成功");
		return result;
	}

	/**
	 * ElbDTO -> ElbParameter
	 * 
	 * @param eipDTO
	 * @return
	 */
	private ELBParameter wrapperELBParameter(ElbDTO elbDTO) {

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();

		DTOListResult policyResult = getElbPolicyDTOList(elbDTO.getId());

		for (Object obj : policyResult.getDtoList().getDto()) {

			List<ELBPolicyParameter> policyParameters = new ArrayList<ELBPolicyParameter>();

			ElbPolicyDTO policyDTO = (ElbPolicyDTO) obj;

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getElbProtocol()).getDto();

			ELBPolicyParameter elbPolicyParameter = new ELBPolicyParameter();
			elbPolicyParameter.setProtocolText(lookUpDTO.getDescription());
			elbPolicyParameter.setSourcePort(policyDTO.getSourcePort());
			elbPolicyParameter.setTargetPort(policyDTO.getTargetPort());

			policyParameters.add(elbPolicyParameter);

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();
			publicIPParameter.setIpaddress(policyDTO.getIpaddress());
			publicIPParameter.getPolicyParameters().addAll(policyParameters);
			publicIPParameters.add(publicIPParameter);
		}

		ELBParameter elbParameter = new ELBParameter();

		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(elbDTO.getIpaddress()).getDto();
		elbParameter.setVip(ipaddressDTO.getDescription());
		elbParameter.getPublicIPs().addAll(publicIPParameters);

		return elbParameter;
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
	public void createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, Integer[] eipIds) {

		/**
		 * 
		 * Step.1 将DNS信息写入CMDBuild
		 * 
		 * Step.2 将DNS策略信息写入CMDBuild
		 * 
		 * Step.3 创建关联关系
		 * 
		 * Step.4 调用DNS 接口创建DNS对象
		 * 
		 * Step.5 写入日志
		 * 
		 */

		// Step.1 将DNS信息写入CMDBuild
		dnsDTO.setDomainType(LookUpConstants.DomainType.GSLB.getValue());
		cmdbuildSoapService.createDns(dnsDTO);

		// Step.2 将DNS策略信息写入CMDBuild
		DnsDTO dto = getDnsDTO(dnsDTO.getDescription(), dnsDTO.getTenants());

		for (DnsPolicyDTO policyDTO : dnsPolicyDTOs) {

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getDnsProtocol()).getDto();

			EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(Integer.valueOf(policyDTO.getIpaddress())).getDto();

			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(eipDTO.getIpaddress())
					.getDto();

			DnsPolicyDTO dnsPolicyDTO = new DnsPolicyDTO();

			dnsPolicyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getPort());
			dnsPolicyDTO.setPort(policyDTO.getPort());
			dnsPolicyDTO.setDns(dto.getId());
			dnsPolicyDTO.setIpaddress(ipaddressDTO.getDescription());
			dnsPolicyDTO.setDnsProtocol(policyDTO.getDnsProtocol());

			cmdbuildSoapService.createDnsPolicy(dnsPolicyDTO);
		}

		// Step.3 创建关联关系
		for (Integer eipId : eipIds) {
			cmdbuildSoapService.createMapEipDns(eipId, dto.getId());
		}

		// Step.4 调用DNS 接口创建DNS对象
		dnsSoapService.createDNSByDNS(wrapperDNSParameter(dto));

		// Step.5 写入日志
		createLog(dto.getTenants(), LookUpConstants.ServiceType.DNS.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());
	}

	/**
	 * DnsDTO -> DNSParameter
	 * 
	 * @param eipDTO
	 * @return
	 */
	private DNSParameter wrapperDNSParameter(DnsDTO dnsDTO) {

		List<DNSPublicIPParameter> publicIPParameters = new ArrayList<DNSPublicIPParameter>();

		DTOListResult policyResult = geDnsPolicyDTOList(dnsDTO.getId());

		for (Object obj : policyResult.getDtoList().getDto()) {

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

	private DnsDTO getDnsDTO(String description, Integer tenantsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", description);
		map.put("EQ_tenants", tenantsId);
		return (DnsDTO) cmdbuildSoapService.findDnsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	@Override
	public void deleteDNS(Integer dnsId) {

		/**
		 * Step.1 获得dns.
		 * 
		 * Step.2 调用dns接口删除dns
		 * 
		 * Step.3 查询dns下所有policy并删除
		 * 
		 * Step.4 删除dns
		 * 
		 * Step.5 写入日志
		 */

		// Step.1 获得dns.
		DnsDTO dnsDTO = (DnsDTO) cmdbuildSoapService.findDns(dnsId).getDto();

		// Step.2 调用dns接口删除dns
		dnsSoapService.deleteDNSByDNS(wrapperDNSParameter(dnsDTO));

		// Step.3 查询dns下所有policy并删除
		DTOListResult policyResult = geDnsPolicyDTOList(dnsId);

		for (Object obj : policyResult.getDtoList().getDto()) {

			DnsPolicyDTO policyDTO = (DnsPolicyDTO) obj;

			cmdbuildSoapService.deleteDnsPolicy(policyDTO.getId());
		}

		// Step.4 删除dns
		cmdbuildSoapService.deleteDns(dnsId);

		// Step.5 写入日志
		createLog(dnsDTO.getTenants(), LookUpConstants.ServiceType.DNS.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());
	}

	/**
	 * 获得租户的默认ESG
	 * 
	 * @param tenantsDTO
	 * @return
	 */
	private EsgDTO getDefaultEsgDTO(TenantsDTO tenantsDTO) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsDTO.getId());
		map.put("EQ_isDefault", Boolean.TRUE);
		return (EsgDTO) cmdbuildSoapService.findEsgByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	@Override
	public void createESG(EsgDTO esgDTO, List<EsgPolicyDTO> esgPolicyDTOs) {

		/**
		 * Step.1 查询出租户和租户默认策略.
		 * 
		 * Step.2 将ESG信息写入CMDBuild
		 * 
		 * Step.3 将ESG策略信息写入CMDBuild
		 * 
		 * Step.4 写入日志
		 * 
		 */

		// Step.1 查询出租户的默认策略.
		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(esgDTO.getTenants()).getDto();

		EsgDTO defaultEsgDTO = getDefaultEsgDTO(tenantsDTO);

		// Step.2 将ESG信息写入CMDBuild
		esgDTO.setIdc(defaultEsgDTO.getIdc());
		esgDTO.setIsDefault(false);
		cmdbuildSoapService.createEsg(esgDTO);

		// Step.3 将ESG策略信息写入CMDBuild
		EsgDTO dto = getEsgDTOByDescription(esgDTO);

		for (EsgPolicyDTO policyDTO : esgPolicyDTOs) {

			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(policyDTO.getPolicyType()).getDto();

			if (StringUtils.isNotBlank(policyDTO.getTargetIP())) {

				EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
				esgPolicyDTO.setDescription(lookUpDTO.getDescription() + "-" + policyDTO.getTargetIP());

				esgPolicyDTO.setEsg(dto.getId());
				esgPolicyDTO.setTargetIP(policyDTO.getTargetIP());
				esgPolicyDTO.setPolicyType(policyDTO.getPolicyType());
				cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
			}
		}

		// Step.4 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.创建.getValue(), LookUpConstants.Result.成功.getValue());
	}

	private ESGParameter wrapperESGParameter(EsgDTO esgDTO) {

		TenantsDTO tenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenants(esgDTO.getTenants()).getDto();

		// 要先将switch中的acl删除再重新封装参数创建一个新的acl
		switchesSoapService.deleteESGBySwtich(tenantsDTO.getAclNumber());

		ESGParameter esgParameter = new ESGParameter();
		esgParameter.setAclNumber(tenantsDTO.getAclNumber());
		esgParameter.setDesc(esgDTO.getDescription());
		esgParameter.setVlanId(getVlanDTO(tenantsDTO).getId());

		// 获得租户所有的ESG

		List<RuleParameter> permits = new ArrayList<RuleParameter>();
		List<RuleParameter> denys = new ArrayList<RuleParameter>();

		// 非默认ESG
		List<EsgDTO> esgDTOs = getEsgDTO();

		for (EsgDTO dto : esgDTOs) {

			for (Object object : geEsgPolicyDTOList(dto.getId()).getDtoList().getDto()) {

				EsgPolicyDTO policyDTO = (EsgPolicyDTO) object;

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("EQ_idObj2", dto.getId());
				SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
				List<Object> ecsEsgDTOs = cmdbuildSoapService.getMapEcsEsgList(searchParams).getDtoList().getDto();

				for (Object mapObj : ecsEsgDTOs) {

					MapEcsEsgDTO mapEcsEsgDTO = (MapEcsEsgDTO) mapObj;

					EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEsgDTO.getIdObj1()))
							.getDto();

					IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress())
							.getDto();

					RuleParameter ruleParameter = new RuleParameter();
					ruleParameter.setSource(ipaddressDTO.getDescription());
					ruleParameter.setSourceNetMask(getNetMask(ipaddressDTO.getDescription()));
					ruleParameter.setDestination(policyDTO.getTargetIP());
					ruleParameter.setDestinationNetMask(getNetMask(policyDTO.getTargetIP()));

					if (LookUpConstants.PolicyType.Permit.getValue().equals(policyDTO.getPolicyType())) { // prmits
						permits.add(ruleParameter);
					} else {// denys
						denys.add(ruleParameter);
					}

				}

			}
		}

		// 默认ESG
		EsgDTO defaultEsgDTO = getDefaultEsgDTO(tenantsDTO);
		for (Object object : geEsgPolicyDTOList(defaultEsgDTO.getId()).getDtoList().getDto()) {

			EsgPolicyDTO policyDTO = (EsgPolicyDTO) object;

			RuleParameter ruleParameter = new RuleParameter();
			ruleParameter.setSource(policyDTO.getSourceIP());
			ruleParameter.setSourceNetMask(getNetMask(policyDTO.getSourceIP()));
			ruleParameter.setDestination(policyDTO.getTargetIP());
			ruleParameter.setDestinationNetMask(getNetMask(policyDTO.getTargetIP()));

			if (LookUpConstants.PolicyType.Permit.getValue().equals(policyDTO.getPolicyType())) { // prmits
				permits.add(ruleParameter);
			} else {// denys
				denys.add(ruleParameter);
			}

		}

		esgParameter.getPermits().addAll(permits);
		esgParameter.getDenys().addAll(denys);

		return esgParameter;
	}

	/**
	 * 字符串结尾为0返回"0.0.0.255",表示网关. 字符串结尾不为0返回"0.0.0.0",表示单个IP.<br>
	 * 
	 * eg: 10.10.100.0 -> 0.0.0.255 , 10.10.100.1 -> 0.0.0.0
	 * 
	 * @param paramter
	 * @return
	 */
	private String getNetMask(String paramter) {
		return "0".equals(StringUtils.split(paramter, ".")[3]) ? "0.0.0.255" : "0.0.0.0";
	}

	@Override
	public void deleteESG(Integer esgId) {

		/**
		 * Step.1 查询Esg信息
		 * 
		 * Step.2 删除CMDBuild中ESG的策略
		 * 
		 * Step.3 删除CMDBuild的ESG信息.
		 * 
		 * Step.4 去除删除ESG关联的策略后重新执行
		 * 
		 * Step.5 写入日志
		 */

		// Step1. 查询Esg信息
		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		// Step2.删除CMDBuild中ESG的策略
		for (Object obj : geEsgPolicyDTOList(esgId).getDtoList().getDto()) {

			EsgPolicyDTO policyDTO = (EsgPolicyDTO) obj;

			cmdbuildSoapService.deleteEsgPolicy(policyDTO.getId());
		}

		// Step3. 删除CMDBuild的ESG信息.
		cmdbuildSoapService.deleteEsg(esgId);

		// Step4. 去除删除ESG关联的策略后重新执行
		switchesSoapService.createESGBySwtich(wrapperESGParameter(esgDTO));

		// Step5. 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.删除.getValue(), LookUpConstants.Result.成功.getValue());

	}

	/**
	 * 获得ESG下所有的policy
	 */
	private DTOListResult geEsgPolicyDTOList(Integer esgId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_esg", esgId);
		return cmdbuildSoapService.getEsgPolicyList(CMDBuildUtil.wrapperSearchParams(map));
	}

	@Override
	public void associateESG(Integer ecsId, Integer esgId) {

		/**
		 * Step.1 查询Esg信息
		 * 
		 * Step.2 创建关联关系
		 * 
		 * Step.3 调用switch接口创建acl number.
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 查询Esg信息
		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		// Step.2 创建关联关系
		cmdbuildSoapService.createMapEcsEsg(ecsId, esgId);

		// Step.3 调用switch接口创建acl number.
		switchesSoapService.createESGBySwtich(wrapperESGParameter(esgDTO));

		// Step.4 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public void dissociateESG(Integer ecsId, Integer esgId) {

		/**
		 * Step.1 查询Esg信息
		 * 
		 * Step.2 删除关联关系
		 * 
		 * Step.3 调用switch接口创建acl number.
		 * 
		 * Step.4 写入日志
		 */

		// Step.1 查询Esg信息
		EsgDTO esgDTO = (EsgDTO) cmdbuildSoapService.findEsg(esgId).getDto();

		// Step.2 删除关联关系
		cmdbuildSoapService.deleteMapEcsEsg(ecsId, esgId);

		// Step.3 调用switch接口创建acl number.
		switchesSoapService.createESGBySwtich(wrapperESGParameter(esgDTO));

		// Step.4 写入日志
		createLog(esgDTO.getTenants(), LookUpConstants.ServiceType.ESG.getValue(),
				LookUpConstants.OperateType.应用规则至.getValue(), LookUpConstants.Result.成功.getValue());

	}

	@Override
	public List<TenantsDTO> getTenantsDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		List<TenantsDTO> list = new ArrayList<TenantsDTO>();
		for (Object obj : cmdbuildSoapService.getTenantsList(searchParams).getDtoList().getDto()) {
			list.add((TenantsDTO) obj);
		}
		return list;
	}

	@Override
	public List<IdcDTO> getIdcDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<IdcDTO> list = new ArrayList<IdcDTO>();
		for (Object obj : cmdbuildSoapService.getIdcList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((IdcDTO) obj);
		}
		return list;
	}

	@Override
	public List<EcsSpecDTO> getEcsSpecDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<EcsSpecDTO> list = new ArrayList<EcsSpecDTO>();
		for (Object obj : cmdbuildSoapService.getEcsSpecList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto()) {
			list.add((EcsSpecDTO) obj);
		}
		return list;
	}

	@Override
	public List<EcsDTO> getEcsDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<EcsDTO> list = new ArrayList<EcsDTO>();
		for (Object obj : cmdbuildSoapService.getEcsList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((EcsDTO) obj);
		}
		return list;
	}

	@Override
	public List<Es3DTO> getEs3DTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Es3DTO> list = new ArrayList<Es3DTO>();
		for (Object obj : cmdbuildSoapService.getEs3List(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((Es3DTO) obj);
		}
		return list;
	}

	@Override
	public List<EipDTO> getEipDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<EipDTO> list = new ArrayList<EipDTO>();
		for (Object obj : cmdbuildSoapService.getEipList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((EipDTO) obj);
		}
		return list;
	}

	@Override
	public List<ElbDTO> getElbDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ElbDTO> list = new ArrayList<ElbDTO>();
		for (Object obj : cmdbuildSoapService.getElbList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((ElbDTO) obj);
		}
		return list;
	}

	@Override
	public List<DnsDTO> getDnsDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<DnsDTO> list = new ArrayList<DnsDTO>();
		for (Object obj : cmdbuildSoapService.getDnsList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((DnsDTO) obj);
		}
		return list;
	}

	@Override
	public List<EsgDTO> getEsgDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_isDefault", Boolean.FALSE);
		List<EsgDTO> list = new ArrayList<EsgDTO>();
		for (Object obj : cmdbuildSoapService.getEsgList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((EsgDTO) obj);
		}
		return list;
	}

	@Override
	public void createTag(TagDTO tagDTO) {
		cmdbuildSoapService.createTag(tagDTO);
	}

	@Override
	public void deleteTag(Integer tagId) {
		cmdbuildSoapService.deleteTag(tagId);
	}

	@Override
	public void associateTag(Integer tagId, Integer serviceId) {
		cmdbuildSoapService.createMapTagService(tagId, serviceId);

	}

	@Override
	public void dssociateTag(Integer tagId, Integer serviceId) {
		cmdbuildSoapService.deleteMapTagService(tagId, serviceId);
	}

	@Override
	public List<TagDTO> getTagDTO() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<TagDTO> list = new ArrayList<TagDTO>();
		for (Object obj : cmdbuildSoapService.getTagList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto()) {
			list.add((TagDTO) obj);
		}
		return list;
	}

	@Override
	public List<TagRelation> getTagRelation(Integer serviceId) {
		return cmdbuildSoapService.getTagRelation(serviceId);
	}

	@Override
	public void deleteHost(Integer ecsId) {
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();
		zabbixSoapService.deleleHost(ipaddressDTO.getDescription());
	}

	@Override
	public ZItemDTO getItem(Integer ecsId, String itemKey) {
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();
		return zabbixSoapService.getZItem(ipaddressDTO.getDescription(), itemKey);
	}

}
