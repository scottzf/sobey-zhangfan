package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DTOListResult;
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.cmdbuild.VlanDTO;
import com.sobey.generate.cmdbuild.VpnDTO;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.firewall.VPNUserParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.switches.ESGParameter;
import com.sobey.generate.switches.RuleParameter;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.switches.VlanParameter;

@Service
public class ApiService {

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

	public void createTenants(TenantsDTO tenantsDTO) {

		/**
		 * step.1 创建租户.
		 * 
		 * step.2 分配Vlan
		 * 
		 * step.3 创建VPN账号
		 * 
		 * step.4 创建默认Esg
		 * 
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
		map.put("EQ_tenants", "");
		SearchParams searchParams = CMDBuildUtil.wrapperSearchParams(map);
		DTOListResult dtoListResult = cmdbuildSoapService.getVlanList(searchParams);

		// 将vlan分配给租户
		VlanDTO vlanDTO = (VlanDTO) dtoListResult.getDtoList().getDto().get(0);
		vlanDTO.setTenants(tenantsDTO.getId());
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

		Integer policyId = 2000;// TODO 需要查询数据库里最大的值从2000起

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

		Integer aclNumber = 3000; // TODO还是需要查询CMDBuild中的最大值. 从3000起

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
		dto.setDescription(tenantsDTO.getDescription() + "的默认策略");
		dto.setDescription("默认策略");
		dto.setIsDefault(true);
		dto.setRemark(tenantsDTO.getDescription() + "的默认策略");
		dto.setTenants(tenantsDTO.getId());
		dto.setAgentType(agentTypeId);
		dto.setIdc(idcId);
		cmdbuildSoapService.createEsg(dto);

		System.err.println(tenantsDTO.getId().toString());
		System.err.println(dto.getDescription());

		// 保存ESG相关的策略
		createEsgPolicy(parameter, dto.getDescription());

	}

	private void createEsgPolicy(ESGParameter parameter, String description) {

		EsgDTO esgDTO = getEsgDTO(description);

		for (RuleParameter ruleParameter : parameter.getDenys()) {
			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(esgDTO.getId());
			esgPolicyDTO.setPolicyType(98);
			esgPolicyDTO.setSourceIP(ruleParameter.getSource());
			esgPolicyDTO.setTargetIP(ruleParameter.getDestination());
			esgPolicyDTO.setDescription("Denys-" + ruleParameter.getSource() + "-" + ruleParameter.getDestination());
			cmdbuildSoapService.createEsgPolicy(esgPolicyDTO);
		}

		for (RuleParameter ruleParameter : parameter.getPermits()) {

			EsgPolicyDTO esgPolicyDTO = new EsgPolicyDTO();
			esgPolicyDTO.setEsg(esgDTO.getId());
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
	private EsgDTO getEsgDTO(String description) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EQ_description", description);
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

}
