package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.EipEntity;
import com.sobey.api.entity.ElbEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.FirewallPolicyEntity;
import com.sobey.api.entity.FirewallServiceEntity;
import com.sobey.api.entity.RouterEntity;
import com.sobey.api.entity.SubnetEntity;
import com.sobey.api.entity.TenantsEntity;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.ConfigFirewallServiceCategoryDTO;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.IpaddressDTO;
import com.sobey.generate.cmdbuild.LookUpDTO;
import com.sobey.generate.cmdbuild.MapEcsEipDTO;
import com.sobey.generate.cmdbuild.MapEipDnsDTO;
import com.sobey.generate.cmdbuild.MapEipElbDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.switches.SwitchesSoapService;
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

	private EcsDTO findEcsDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (EcsDTO) cmdbuildSoapService.findEcsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private EipDTO findEipDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (EipDTO) cmdbuildSoapService.findEipByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private Es3DTO findEs3DTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (Es3DTO) cmdbuildSoapService.findEs3ByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private DnsDTO findDnsDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (DnsDTO) cmdbuildSoapService.findDnsByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private SubnetDTO findSubnetDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (SubnetDTO) cmdbuildSoapService.findSubnetByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private RouterDTO findRouterDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (RouterDTO) cmdbuildSoapService.findRouterByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private ServiceDTO findServiceDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (ServiceDTO) cmdbuildSoapService.findServiceByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();
	}

	private FirewallServiceDTO findFirewallServiceDTO(Integer tenantsId, String code) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsId);
		map.put("EQ_code", code);
		return (FirewallServiceDTO) cmdbuildSoapService.findFirewallByParams(CMDBuildUtil.wrapperSearchParams(map))
				.getDto();
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

	private static Integer getPortFromProtocol(String protocol) {
		return "HTTPS".equals(protocol.toUpperCase()) ? 443 : 80;
	}

	@Override
	public DTOResult<TenantsEntity> findTenants(String accessKey) {

		DTOResult<TenantsEntity> result = new DTOResult<TenantsEntity>();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);

		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "租户不存在.");
			return result;
		}

		TenantsEntity entity = new TenantsEntity(tenantsDTO.getCode(), tenantsDTO.getDescription(),
				tenantsDTO.getEmail(), tenantsDTO.getPhone(), tenantsDTO.getCompany());
		result.setDto(entity);

		return result;
	}

	@Override
	public WSResult createTenants(String company, String name, String email, String password, String phone) {

		TenantsDTO tenantsDTO = new TenantsDTO();

		tenantsDTO.setCompany(company);
		tenantsDTO.setDescription(email);
		tenantsDTO.setEmail(email);
		tenantsDTO.setPassword(password);
		tenantsDTO.setPhone(phone);

		WSResult result = new WSResult();

		result.setMessage(apiService.createTenants(tenantsDTO).getMessage());

		return result;

	}

	@Override
	public DTOResult<SubnetEntity> findSubnet(String code, String accessKey) {

		DTOResult<SubnetEntity> result = new DTOResult<SubnetEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		SubnetDTO subnetDTO = findSubnetDTO(tenantsDTO.getId(), code);
		if (subnetDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "Subnet不存在.");
			return result;
		}

		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(subnetDTO.getIdc()).getDto();

		SubnetEntity entity = new SubnetEntity(idcDTO.getDescription(), subnetDTO.getGateway(), subnetDTO.getCode(),
				subnetDTO.getNetMask(), subnetDTO.getRemark(), subnetDTO.getSegment(), subnetDTO.getDescription());
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult createSubnet(String subnetName, String segment, String gateway, String netmask, String idc,
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

		SubnetDTO subnetDTO = new SubnetDTO();

		subnetDTO.setIdc(idcDTO.getId());
		subnetDTO.setGateway(gateway);
		subnetDTO.setNetMask(netmask);
		subnetDTO.setTenants(tenantsDTO.getId());
		subnetDTO.setSegment(segment);
		subnetDTO.setDescription(subnetName);

		result.setMessage(apiService.createSubnet(subnetDTO).getMessage());

		return result;

	}

	@Override
	public DTOResult<EcsEntity> findECS(String code, String accessKey) {

		DTOResult<EcsEntity> result = new DTOResult<EcsEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), code);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}

		IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(ecsDTO.getIdc()).getDto();
		IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress()).getDto();
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();
		LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(ecsDTO.getEcsStatus()).getDto();

		EcsEntity entity = new EcsEntity(ecsDTO.getCpuNumber(), ecsDTO.getDescription(), idcDTO.getDescription(),
				ecsDTO.getCode(), ipaddressDTO.getDescription(), ecsDTO.isIsDesktop(), ecsDTO.isIsGpu(),
				ecsDTO.getMemorySize(), ecsDTO.getRemark(), ecsSpecDTO.getDescription(), lookUpDTO.getDescription());
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult createECS(String ecsName, String subnetCode, String remark, String ecsSpec, String idc,
			String accessKey) {

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

		SubnetDTO subnetDTO = findSubnetDTO(tenantsDTO.getId(), subnetCode);

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setAgentType(LookUpConstants.AgentType.VMware.getValue());
		ecsDTO.setDescription(ecsName);
		ecsDTO.setEcsSpec(ecsSpecDTO.getId());
		ecsDTO.setIdc(idcDTO.getId());
		ecsDTO.setRemark(remark);
		ecsDTO.setTenants(tenantsDTO.getId());
		ecsDTO.setSubnet(subnetDTO.getId());

		result.setMessage(apiService.createECS(ecsDTO).getMessage());
		return result;
	}

	@Override
	public WSResult destroyECS(String code, String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), code);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}
		apiService.destroyECS(ecsDTO.getId());
		result.setMessage("销毁成功.");
		return result;

	}

	@Override
	public WSResult powerOpsECS(String code, String powerOperation, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), code);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}
		return apiService.powerOpsECS(ecsDTO.getId(), powerOperation);
	}

	@Override
	public WSResult reconfigECS(String code, String ecsSpec, String accessKey) {

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
		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), code);
		if (ecsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
			return result;
		}
		return apiService.reconfigECS(ecsDTO.getId(), ecsSpecDTO.getId());
	}

	@Override
	public DTOResult<Es3Entity> findES3(String code, String accessKey) {

		DTOResult<Es3Entity> result = new DTOResult<Es3Entity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), code);
		if (es3DTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3不存在.");
			return result;
		}

		LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(es3DTO.getEs3Type()).getDto();

		Es3Entity entity = new Es3Entity(es3DTO.getTotalSize() + "GB", code, lookUpDTO.getDescription(),
				es3DTO.getCode(), es3DTO.getRemark());
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String ecsCode,
			String remark, String accessKey) {

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

		EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsCode);

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(ecsDTO.getSubnet()).getDto();

		Es3DTO es3DTO = new Es3DTO();
		es3DTO.setAgentType(LookUpConstants.AgentType.VMware.getValue());
		es3DTO.setDescription(es3Name);
		es3DTO.setTotalSize(es3Size.toString());
		es3DTO.setEs3Type(lookUpDTO.getId());
		es3DTO.setIdc(idcDTO.getId());
		es3DTO.setVolumeName(es3Name);
		es3DTO.setTenants(tenantsDTO.getId());
		es3DTO.setRemark(remark);
		es3DTO.setSubnet(subnetDTO.getId());
		return apiService.createES3(es3DTO, ecsDTO.getId());
	}

	@Override
	public WSResult deleteES3(String code, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), code);
		if (es3DTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3不存在.");
			return result;
		}
		result.setMessage("删除成功.");

		return apiService.deleteES3(es3DTO.getId());
	}

	@Override
	public DTOResult<DnsEntity> findDNS(String code, String accessKey) {
		DTOResult<DnsEntity> result = new DTOResult<DnsEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), code);
		if (dnsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "DNS不存在.");
			return result;
		}

		List<EipEntity> eipEntities = new ArrayList<EipEntity>();

		// 查询出DNS关联的EIP信息
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", dnsDTO.getId());
		List<Object> list = cmdbuildSoapService.getMapEipDnsList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		for (Object object : list) {
			MapEipDnsDTO mapEipDnsDTO = (MapEipDnsDTO) object;
			EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(Integer.valueOf(mapEipDnsDTO.getIdObj1())).getDto();
			EipEntity entity = new EipEntity(eipDTO.getCode(), eipDTO.getDescription(), eipDTO.getRemark(),
					eipDTO.getBandwidthText(), eipDTO.getIspText());
			eipEntities.add(entity);
		}
		DnsEntity entity = new DnsEntity(dnsDTO.getCode(), code, dnsDTO.getRemark(), eipEntities);
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult createDNS(String domainName, String eipCodes, String protocols, String idc, String remark,
			String accessKey) {
		
		System.out.println("*********API1***************");

		WSResult result = new WSResult();

		String[] eipCodesArray = StringUtils.split(eipCodes, ",");
		String[] protocolsArray = StringUtils.split(protocols, ",");

		if (protocolsArray.length != eipCodesArray.length) {
			result.setError(WSResult.PARAMETER_ERROR, "参数错误.");
			return result;
		}

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		if (findDnsDTO(tenantsDTO.getId(), domainName) != null) {
			result.setError(WSResult.PARAMETER_ERROR, "域名已存在.");
			return result;
		}

		IdcDTO idcDTO = findIdcDTO(idc);
		if (idcDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "IDC不存在.");
			return result;
		}

		List<EipDTO> eipDTOs = new ArrayList<EipDTO>();

		System.out.println("*********API2***************");

		List<DnsPolicyDTO> dnsPolicyDTOs = new ArrayList<DnsPolicyDTO>();
		for (int i = 0; i < protocolsArray.length; i++) {

			EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipCodesArray[i]);

			if (eipDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
				return result;
			}
			eipDTOs.add(eipDTO);

			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(eipDTO.getIpaddress())
					.getDto();

			LookUpDTO lookUpDTO = findLookUpDTO(protocolsArray[i], "DNSProtocol");
			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}

			DnsPolicyDTO policyDTO = new DnsPolicyDTO();
			policyDTO.setDnsProtocol(lookUpDTO.getId());
			policyDTO.setPort(getPortFromProtocol(protocolsArray[i]).toString());
			policyDTO.setIpaddress(ipaddressDTO.getDescription());
			dnsPolicyDTOs.add(policyDTO);
		}

		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		dnsDTO.setTenants(tenantsDTO.getId());
		dnsDTO.setDomainName(domainName);
		dnsDTO.setDescription(domainName);
		dnsDTO.setRemark(remark);
		dnsDTO.setIdc(idcDTO.getId());

		System.out.println("*********API3***************");

		result.setMessage(apiService.createDNS(dnsDTO, dnsPolicyDTOs, eipDTOs).getMessage());
		return result;

	}

	@Override
	public WSResult deleteDNS(String code, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), code);
		apiService.deleteDNS(dnsDTO.getId());
		return result;
	}

	@Override
	public DTOResult<RouterEntity> findRouter(String code, String accessKey) {

		DTOResult<RouterEntity> result = new DTOResult<RouterEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		RouterDTO routerDTO = findRouterDTO(tenantsDTO.getId(), code);
		if (routerDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "Router不存在.");
			return result;
		}

		List<SubnetEntity> subnetEntities = new ArrayList<SubnetEntity>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_router", routerDTO.getId());

		List<Object> list = cmdbuildSoapService.getSubnetList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		for (Object object : list) {
			SubnetDTO subnetDTO = (SubnetDTO) object;
			IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(subnetDTO.getIdc()).getDto();
			SubnetEntity entity = new SubnetEntity(idcDTO.getDescription(), subnetDTO.getGateway(),
					subnetDTO.getCode(), subnetDTO.getNetMask(), subnetDTO.getRemark(), subnetDTO.getSegment(),
					subnetDTO.getDescription());
			subnetEntities.add(entity);
		}

		RouterEntity entity = new RouterEntity(routerDTO.getDescription(), subnetEntities);
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult createRouter(String routerName, String subnetCode, String remark, String routerSpec, String idc,
			String accessKey) {

		WSResult result = new WSResult();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		EcsSpecDTO ecsSpecDTO = findEcsSpecDTO(routerSpec);
		if (ecsSpecDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "规格不存在.");
			return result;
		}

		IdcDTO idcDTO = findIdcDTO(idc);
		if (idcDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "IDC不存在.");
			return result;
		}

		SubnetDTO subnetDTO = findSubnetDTO(tenantsDTO.getId(), subnetCode);

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		ecsDTO.setDescription(routerName);
		ecsDTO.setEcsSpec(ecsSpecDTO.getId());
		ecsDTO.setIdc(idcDTO.getId());
		ecsDTO.setRemark(remark);
		ecsDTO.setTenants(tenantsDTO.getId());
		ecsDTO.setSubnet(subnetDTO.getId());

		result.setMessage(apiService.createECS(ecsDTO).getMessage());

		return result;
	}

	@Override
	public DTOResult<FirewallServiceEntity> findFirewallService(String code, String accessKey) {

		DTOResult<FirewallServiceEntity> result = new DTOResult<FirewallServiceEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		FirewallServiceDTO firewallServiceDTO = findFirewallServiceDTO(tenantsDTO.getId(), code);
		if (firewallServiceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "防火墙不存在.");
			return result;
		}

		List<FirewallPolicyEntity> policyEntities = new ArrayList<FirewallPolicyEntity>();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("EQ_firewallService", firewallServiceDTO.getId());

		List<Object> list = cmdbuildSoapService
				.getconfigFirewallServiceCategoryList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList().getDto();

		for (Object object : list) {
			ConfigFirewallServiceCategoryDTO categoryDTO = (ConfigFirewallServiceCategoryDTO) object;

			FirewallPolicyEntity policyEntity = new FirewallPolicyEntity(categoryDTO.getAction(),
					categoryDTO.getAddress(), categoryDTO.getDirection(), categoryDTO.getEndPort(),
					categoryDTO.getFirewallService(), categoryDTO.getDescription(), categoryDTO.getProtocol(),
					categoryDTO.getStartPort());
			policyEntities.add(policyEntity);
		}

		FirewallServiceEntity entity = new FirewallServiceEntity(firewallServiceDTO.getDescription(), policyEntities);

		result.setDto(entity);

		return result;

	}

	@Override
	public WSResult createFirewallService(String firewallServiceName, String directions, String rulesNames,
			String protocols, String actions, String startPorts, String endPorts, String ipaddresses, String idc,
			String accessKey) {

		WSResult result = new WSResult();

		String[] directionsArray = StringUtils.split(directions, ",");
		String[] rulesNamesArray = StringUtils.split(rulesNames, ",");
		String[] protocolsArray = StringUtils.split(protocols, ",");
		String[] actionsArray = StringUtils.split(actions, ",");
		String[] startPortsArray = StringUtils.split(startPorts, ",");
		String[] endPortsArray = StringUtils.split(endPorts, ",");
		String[] ipaddressesArray = StringUtils.split(ipaddresses, ",");

		if (directionsArray.length != actionsArray.length) {
			result.setError(WSResult.PARAMETER_ERROR, "参数错误.");
			return result;
		}

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		FirewallServiceDTO firewallServiceDTO = new FirewallServiceDTO();
		firewallServiceDTO.setDescription(firewallServiceName);
		firewallServiceDTO.setTenants(tenantsDTO.getId());
		cmdbuildSoapService.createFirewallService(firewallServiceDTO);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_tenants", tenantsDTO.getId());
		map.put("EQ_description", firewallServiceName);

		FirewallServiceDTO queryFirewallServiceDTO = (FirewallServiceDTO) cmdbuildSoapService
				.findFirewallServiceByParams(CMDBuildUtil.wrapperSearchParams(map)).getDto();

		for (int i = 0; i < directionsArray.length; i++) {

			ConfigFirewallServiceCategoryDTO categoryDTO = new ConfigFirewallServiceCategoryDTO();
			categoryDTO.setAction(actionsArray[i]);
			categoryDTO.setAddress(ipaddressesArray[i]);
			categoryDTO.setDescription(rulesNamesArray[i]);
			categoryDTO.setDirection(directionsArray[i]);
			categoryDTO.setStartPort(Integer.valueOf(startPortsArray[i]));
			categoryDTO.setEndPort(Integer.valueOf(endPortsArray[i]));
			categoryDTO.setTenants(tenantsDTO.getId());
			categoryDTO.setProtocol(protocolsArray[i]);
			categoryDTO.setFirewallService(queryFirewallServiceDTO.getId());

			cmdbuildSoapService.createconfigFirewallServiceCategory(categoryDTO);
		}

		result.setMessage(queryFirewallServiceDTO.getCode());
		return result;
	}

	@Override
	public DTOResult<EipEntity> findEIP(String eipCode, String accessKey) {

		DTOResult<EipEntity> result = new DTOResult<EipEntity>();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipCode);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}
		// 查询出EIP关联的ECS信息
		List<EcsEntity> ecsEntities = new ArrayList<EcsEntity>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", eipDTO.getId());
		List<Object> list = cmdbuildSoapService.getMapEcsEipList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		for (Object object : list) {
			MapEcsEipDTO mapEcsEipDTO = (MapEcsEipDTO) object;
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEipDTO.getIdObj1())).getDto();

			IdcDTO idcDTO = (IdcDTO) cmdbuildSoapService.findIdc(ecsDTO.getIdc()).getDto();
			IpaddressDTO ipaddressDTO = (IpaddressDTO) cmdbuildSoapService.findIpaddress(ecsDTO.getIpaddress())
					.getDto();
			EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsDTO.getEcsSpec()).getDto();
			LookUpDTO lookUpDTO = (LookUpDTO) cmdbuildSoapService.findLookUp(ecsDTO.getEcsStatus()).getDto();

			EcsEntity entity = new EcsEntity(ecsDTO.getCpuNumber(), ecsDTO.getDescription(), idcDTO.getDescription(),
					ecsDTO.getCode(), ipaddressDTO.getDescription(), ecsDTO.isIsDesktop(), ecsDTO.isIsGpu(),
					ecsDTO.getMemorySize(), ecsDTO.getRemark(), ecsSpecDTO.getDescription(), lookUpDTO.getDescription());
			ecsEntities.add(entity);
		}
		// 查询出EIP关联的ELB信息
		List<ElbEntity> elbEntities = new ArrayList<ElbEntity>();
		HashMap<String, Object> elbMap = new HashMap<String, Object>();
		elbMap.put("EQ_idObj1", eipDTO.getId());
		List<Object> mapEipElblist = cmdbuildSoapService.getMapEipElbList(CMDBuildUtil.wrapperSearchParams(elbMap))
				.getDtoList().getDto();
		for (Object object : mapEipElblist) {
			MapEipElbDTO mapEcsElbDTO = (MapEipElbDTO) object;
			ElbDTO elbDTO = (ElbDTO) cmdbuildSoapService.findElb(Integer.valueOf(mapEcsElbDTO.getIdObj2())).getDto();
			ElbEntity entity = new ElbEntity(elbDTO.getCode(), elbDTO.getDescription(), null);
			elbEntities.add(entity);
		}
		EipEntity entity = new EipEntity(eipDTO.getCode(), eipDTO.getDescription(), eipDTO.getRemark(),
				eipDTO.getBandwidthText(), eipDTO.getIspText(), ecsEntities, elbEntities);
		result.setDto(entity);
		return result;
	}

	@Override
	public WSResult allocateEIP(String isp, String protocols, String sourcePorts, String targetPorts, String bandwidth,
			String remark, String accessKey) {

		String[] protocolsArray = StringUtils.split(protocols, ",");
		String[] sourcePortsArray = StringUtils.split(sourcePorts, ",");
		String[] targetPortsArray = StringUtils.split(targetPorts, ",");

		WSResult result = new WSResult();
		if (protocolsArray.length != sourcePortsArray.length || targetPortsArray.length != protocolsArray.length
				|| sourcePortsArray.length != targetPortsArray.length) {
			result.setError(WSResult.PARAMETER_ERROR, "参数错误.");
			return result;
		}
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
		for (int i = 0; i < targetPortsArray.length; i++) {
			LookUpDTO upDTO = findLookUpDTO(protocolsArray[i], "EIPProtocol");
			if (upDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}
			EipPolicyDTO policyDTO = new EipPolicyDTO();
			policyDTO.setEipProtocol(upDTO.getId());
			policyDTO.setSourcePort(Integer.valueOf(sourcePortsArray[i]));
			policyDTO.setTargetPort(Integer.valueOf(targetPortsArray[i]));
			eipPolicyDTOs.add(policyDTO);
		}
		EipDTO eipDTO = new EipDTO();
		eipDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		eipDTO.setBandwidth(1);
		eipDTO.setRemark(remark);
		eipDTO.setTenants(tenantsDTO.getId());
		eipDTO.setIsp(lookUpDTO.getId());
		return apiService.applyEIP(eipDTO, eipPolicyDTOs);

	}

	@Override
	public WSResult recoverEIP(String eipCode, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipCode);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}
		apiService.recoverEIP(eipDTO.getId());
		return result;
	}

	@Override
	public WSResult associateEIP(String eipCode, String serviceCode, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipCode);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}
		ServiceDTO serviceDTO = findServiceDTO(tenantsDTO.getId(), serviceCode);
		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}
		return apiService.bindingEIP(eipDTO, serviceDTO);
	}

	@Override
	public WSResult dissociateEIP(String eipCode, String serviceCode, String accessKey) {

		WSResult result = new WSResult();
		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}
		EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipCode);
		if (eipDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
			return result;
		}
		ServiceDTO serviceDTO = findServiceDTO(tenantsDTO.getId(), serviceCode);
		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}
		apiService.unbindingEIP(eipDTO, serviceDTO);
		return result;
	}

}
