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
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.TagDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.zabbix.ZHistoryItemDTO;
import com.sobey.generate.zabbix.ZItemDTO;
import com.sobey.generate.zabbix.ZabbixSoapService;

@Service
public class RestfulServiceImpl implements RestfulService {

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

	@Autowired
	private ApiService apiService;

	private static Integer getPortFromProtocol(String protocol) {
		return "HTTPS".equals(protocol.toUpperCase()) ? 443 : 80;
	}

	private EcsSpecDTO findEcsSpecDTO(String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", description);
		return (EcsSpecDTO) cmdbuildSoapService.findEcsSpecByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private LookUpDTO findLookUpDTO(String description, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", description);
		map.put("EQ_type", type);
		return (LookUpDTO) cmdbuildSoapService.findLookUpByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private IdcDTO findIdcDTO(String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", description);
		return (IdcDTO) cmdbuildSoapService.findIdcByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private TenantsDTO findTenantsDTO(String accessKey) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_accessKey", accessKey);
		return (TenantsDTO) cmdbuildSoapService.findTenantsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private EcsDTO findEcsDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (EcsDTO) cmdbuildSoapService.findEcsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private Es3DTO findEs3DTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (Es3DTO) cmdbuildSoapService.findEs3ByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private EipDTO findEipDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (EipDTO) cmdbuildSoapService.findEipByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private ElbDTO findElbDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (ElbDTO) cmdbuildSoapService.findElbByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private DnsDTO findDnsDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (DnsDTO) cmdbuildSoapService.findDnsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private EsgDTO findEsgDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (EsgDTO) cmdbuildSoapService.findEsgByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private TagDTO findTagDTO(Integer tenantsId, String description) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_description", description);
		return (TagDTO) cmdbuildSoapService.findTagByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	@Override
	public WSResult createECS(String ecsName, String remark, String ecsSpec, String idc, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsSpecDTO ecsSpecDTO = findEcsSpecDTO(ecsSpec);
		if (ecsSpecDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "规格不存在.");
			return result;
		}

		IdcDTO idcDTO = findIdcDTO(idc);
		if (idcDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "IDC不存在.");
			return result;
		}

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setAgentType(LookUpConstants.AgentType.VMware.getValue());
		ecsDTO.setDescription(ecsName);
		ecsDTO.setEcsSpec(ecsSpecDTO.getId());
		ecsDTO.setIdc(idcDTO.getId());
		ecsDTO.setRemark(remark);
		ecsDTO.setTenants(tenantsDTO.getId());

		return apiService.createECS(ecsDTO);
	}

	@Override
	public WSResult destroyECS(String ecsName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);

		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		apiService.destroyECS(ecsDTO.getId());

		return result;
	}

	@Override
	public WSResult powerOpsECS(String ecsName, String powerOperation, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		return apiService.powerOpsECS(ecsDTO.getId(), powerOperation);
	}

	@Override
	public WSResult reconfigECS(String ecsName, String ecsSpec, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);

		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsSpecDTO ecsSpecDTO = findEcsSpecDTO(ecsSpec);
		if (ecsSpecDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "规格不存在.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		return apiService.reconfigECS(ecsDTO.getId(), ecsSpecDTO.getId());
	}

	@Override
	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String remark,
			String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);

		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		IdcDTO idcDTO = findIdcDTO(idc);
		if (idcDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "IDC不存在.");
			return result;
		}

		LookUpDTO lookUpDTO = findLookUpDTO(es3Type, "ES3Type");
		if (lookUpDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3Type不存在.");
			return result;
		}

		Es3DTO es3DTO = new Es3DTO();
		es3DTO.setAgentType(LookUpConstants.AgentType.NetApp.getValue());
		es3DTO.setDescription(es3Name);
		es3DTO.setDiskSize(es3Size);// TODO 注意单位,脚本用的MB,而页面是GB,测试环境无法创建GB大小的volume.
		es3DTO.setEs3Type(lookUpDTO.getId());
		es3DTO.setIdc(idcDTO.getId());
		es3DTO.setVolumeName(es3Name);
		es3DTO.setTenants(tenantsDTO.getId());
		es3DTO.setRemark(remark);

		return apiService.createES3(es3DTO);
	}

	@Override
	public WSResult attachES3(String es3Name, String ecsName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), es3Name);
		if (es3DTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3不存在.");
			return result;
		}

		return apiService.attachES3(es3DTO.getId(), ecsDTO.getId());
	}

	@Override
	public WSResult detachES3(String es3Name, String ecsName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), es3Name);
		if (es3DTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3不存在.");
			return result;
		}

		return apiService.detachES3(es3DTO.getId(), ecsDTO.getId());

	}

	@Override
	public WSResult deleteES3(String es3Name, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), es3Name);
		if (es3DTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3不存在.");
			return result;
		}

		return apiService.deleteES3(es3DTO.getId());
	}

	@Override
	public WSResult allocateEIP(String isp, String[] protocols, Integer[] sourcePorts, Integer[] targetPorts,
			String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		LookUpDTO lookUpDTO = findLookUpDTO(isp, "ISP");
		if (lookUpDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ISP不存在.");
			return result;
		}

		List<EipPolicyDTO> eipPolicyDTOs = new ArrayList<EipPolicyDTO>();

		for (int i = 0; i < targetPorts.length; i++) {

			LookUpDTO upDTO = findLookUpDTO(protocols[i], "EIPProtocol");

			if (upDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}

			EipPolicyDTO policyDTO = new EipPolicyDTO();
			policyDTO.setEipProtocol(upDTO.getId());
			policyDTO.setSourcePort(sourcePorts[i]);
			policyDTO.setTargetPort(targetPorts[i]);
			eipPolicyDTOs.add(policyDTO);
		}

		EipDTO eipDTO = new EipDTO();
		eipDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		eipDTO.setBandwidth(1);
		eipDTO.setTenants(tenantsDTO.getId());
		eipDTO.setIsp(lookUpDTO.getId());

		return apiService.allocateEIP(eipDTO, eipPolicyDTOs);
	}

	@Override
	public WSResult recoverEIP(String eipName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipName);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}

		apiService.recoverEIP(eipDTO.getId());

		return result;
	}

	@Override
	public WSResult associateEIP(String eipName, String serviceName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipName);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}

		Integer serviceId = null;

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), serviceName);
		ElbDTO elbDTO = findElbDTO(tenantsDTO.getId(), serviceName);

		if (ecsDTO != null) {
			serviceId = ecsDTO.getId();
		}

		if (elbDTO != null) {
			serviceId = elbDTO.getId();
		}

		if (serviceId == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		return apiService.associateEIP(eipDTO.getId(), serviceId);
	}

	@Override
	public WSResult dissociateEIP(String eipName, String serviceName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipName);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}

		Integer serviceId = null;

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), serviceName);
		ElbDTO elbDTO = findElbDTO(tenantsDTO.getId(), serviceName);

		if (ecsDTO != null) {
			serviceId = ecsDTO.getId();
		}

		if (elbDTO != null) {
			serviceId = elbDTO.getId();
		}

		if (serviceId == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.dissociateEIP(eipDTO.getId(), serviceId);

		return result;
	}

	@Override
	public WSResult createELB(String[] ecsNames, String[] protocols, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		List<ElbPolicyDTO> elbPolicyDTOs = new ArrayList<ElbPolicyDTO>();

		Integer[] ecsIds = new Integer[protocols.length];// 存放ecsId的数组

		for (int i = 0; i < protocols.length; i++) {

			EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsNames[i]);
			if (ecsDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
				return result;
			}

			LookUpDTO lookUpDTO = findLookUpDTO(protocols[i], "ELBProtocol");
			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}

			ecsIds[i] = ecsDTO.getId();

			ElbPolicyDTO policyDTO = new ElbPolicyDTO();
			policyDTO.setElbProtocol(lookUpDTO.getId());
			policyDTO.setSourcePort(getPortFromProtocol(lookUpDTO.getDescription()));
			policyDTO.setTargetPort(getPortFromProtocol(lookUpDTO.getDescription()));
			policyDTO.setIpaddress(ecsDTO.getId().toString());
			elbPolicyDTOs.add(policyDTO);
		}

		ElbDTO elbDTO = new ElbDTO();
		elbDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		elbDTO.setTenants(tenantsDTO.getId());

		return apiService.createELB(elbDTO, elbPolicyDTOs, ecsIds);
	}

	@Override
	public WSResult deleteELB(String elbName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		ElbDTO elbDTO = findElbDTO(tenantsDTO.getId(), elbName);
		if (elbDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ELB不存在.");
			return result;
		}
		apiService.deleteELB(elbDTO.getId());
		return result;
	}

	@Override
	public WSResult createDNS(String domainName, String[] eipNames, String[] protocols, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		Integer[] eipIds = new Integer[protocols.length];// 存放eipId的数组

		List<DnsPolicyDTO> dnsPolicyDTOs = new ArrayList<DnsPolicyDTO>();

		for (int i = 0; i < protocols.length; i++) {

			EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipNames[i]);
			if (eipDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
				return result;
			}

			LookUpDTO lookUpDTO = findLookUpDTO(protocols[i], "DNSProtocol");
			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}

			eipIds[i] = eipDTO.getId();

			DnsPolicyDTO policyDTO = new DnsPolicyDTO();
			policyDTO.setDnsProtocol(lookUpDTO.getId());
			policyDTO.setPort(getPortFromProtocol(protocols[i]).toString());
			policyDTO.setIpaddress(eipIds[i].toString());
			dnsPolicyDTOs.add(policyDTO);

		}

		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		dnsDTO.setTenants(tenantsDTO.getId());
		dnsDTO.setDomainName(domainName);
		dnsDTO.setDescription(domainName);

		apiService.createDNS(dnsDTO, dnsPolicyDTOs, eipIds);

		return result;
	}

	@Override
	public WSResult deleteDNS(String domainName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), domainName);

		apiService.deleteDNS(dnsDTO.getId());

		return result;
	}

	@Override
	public WSResult createESG(String esgName, String[] policyTypes, String[] targetIPs, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		List<EsgPolicyDTO> esgPolicyDTOs = new ArrayList<EsgPolicyDTO>();

		for (int i = 0; i < policyTypes.length; i++) {

			LookUpDTO lookUpDTO = findLookUpDTO(policyTypes[i], "PolicyType");

			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "策略类型不存在.");
				return result;
			}

			EsgPolicyDTO policyDTO = new EsgPolicyDTO();
			policyDTO.setPolicyType(lookUpDTO.getId());
			policyDTO.setTargetIP(targetIPs[i]);
			esgPolicyDTOs.add(policyDTO);
		}

		EsgDTO esgDTO = new EsgDTO();
		esgDTO.setAgentType(LookUpConstants.AgentType.H3C.getValue());
		esgDTO.setTenants(tenantsDTO.getId());
		esgDTO.setDescription(esgName);

		apiService.createESG(esgDTO, esgPolicyDTOs);

		return result;
	}

	@Override
	public WSResult deleteESG(String esgName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EsgDTO esgDTO = findEsgDTO(tenantsDTO.getId(), esgName);
		if (esgDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ESG不存在.");
			return result;
		}

		apiService.deleteESG(esgDTO.getId());

		return result;
	}

	@Override
	public WSResult associateESG(String ecsName, String esgName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		EsgDTO esgDTO = findEsgDTO(tenantsDTO.getId(), esgName);
		if (esgDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ESG不存在.");
			return result;
		}

		apiService.associateESG(ecsDTO.getId(), esgDTO.getId());

		return result;

	}

	@Override
	public WSResult dissociateESG(String ecsName, String esgName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		EsgDTO esgDTO = findEsgDTO(tenantsDTO.getId(), esgName);
		if (esgDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ESG不存在.");
			return result;
		}

		apiService.dissociateESG(ecsDTO.getId(), esgDTO.getId());

		return result;
	}

	@Override
	public WSResult createTag(String tagName, String parentTag, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		String tagType = "上级标签";// 默认是上级标签

		// 如果有父标签
		if (StringUtils.isNotBlank(parentTag)) {
			TagDTO tagDTO = findTagDTO(tenantsDTO.getId(), parentTag);
			if (tagDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "上级标签不存在.");
				return result;
			}

			tagType = "子标签"; // 该标签有上级标签,故属于子标签类型
			tagDTO.setParentTag(tagDTO.getId());
		}

		LookUpDTO lookUpDTO = findLookUpDTO(tagType, "TagType");
		if (lookUpDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "标签类型不存在.");
			return result;
		}

		TagDTO tagDTO = new TagDTO();
		tagDTO.setTagType(lookUpDTO.getId());
		tagDTO.setTenants(tenantsDTO.getId());
		tagDTO.setDescription(tagName);

		apiService.createTag(tagDTO);

		return result;
	}

	@Override
	public WSResult deleteTag(String tagName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		TagDTO tagDTO = findTagDTO(tenantsDTO.getId(), tagName);
		if (tagDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "标签不存在失败.");
			return result;
		}

		apiService.deleteTag(tagDTO.getId());

		return result;
	}

	@Override
	public WSResult associateTag(String tagName, String serviceName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		TagDTO tagDTO = findTagDTO(tenantsDTO.getId(), tagName);
		if (tagDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "标签不存在失败.");
			return result;
		}

		Integer serviceId = null;

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), serviceName);
		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), serviceName);
		ElbDTO elbDTO = findElbDTO(tenantsDTO.getId(), serviceName);
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), serviceName);
		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), serviceName);
		EsgDTO esgDTO = findEsgDTO(tenantsDTO.getId(), serviceName);

		if (ecsDTO != null) {
			serviceId = ecsDTO.getId();
		}

		if (es3DTO != null) {
			serviceId = es3DTO.getId();
		}

		if (elbDTO != null) {
			serviceId = elbDTO.getId();
		}

		if (eipDTO != null) {
			serviceId = eipDTO.getId();
		}

		if (dnsDTO != null) {
			serviceId = dnsDTO.getId();
		}

		if (esgDTO != null) {
			serviceId = esgDTO.getId();
		}

		if (serviceId == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.associateTag(tagDTO.getId(), serviceId);

		return result;
	}

	@Override
	public WSResult dssociateTag(String tagName, String serviceName, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		TagDTO tagDTO = findTagDTO(tenantsDTO.getId(), tagName);
		if (tagDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "标签不存在失败.");
			return result;
		}

		Integer serviceId = null;

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), serviceName);
		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), serviceName);
		ElbDTO elbDTO = findElbDTO(tenantsDTO.getId(), serviceName);
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), serviceName);
		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), serviceName);
		EsgDTO esgDTO = findEsgDTO(tenantsDTO.getId(), serviceName);

		if (ecsDTO != null) {
			serviceId = ecsDTO.getId();
		}

		if (es3DTO != null) {
			serviceId = es3DTO.getId();
		}

		if (elbDTO != null) {
			serviceId = elbDTO.getId();
		}

		if (eipDTO != null) {
			serviceId = eipDTO.getId();
		}

		if (dnsDTO != null) {
			serviceId = dnsDTO.getId();
		}

		if (esgDTO != null) {
			serviceId = esgDTO.getId();
		}

		if (serviceId == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.dssociateTag(tagDTO.getId(), serviceId);

		return result;
	}

	@Override
	public ZItemDTO getCurrentData(String ecsName, String itemKey, String accessKey) {

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			return null;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			return null;
		}

		return apiService.getCurrentData(ecsDTO.getId(), itemKey);
	}

	@Override
	public ZHistoryItemDTO getHistoryData(String ecsName, String itemKey, String accessKey) {

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			return null;
		}

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsName);
		if (ecsDTO == null) {
			return null;
		}

		return apiService.getHistoryData(ecsDTO.getId(), itemKey);
	}
}
