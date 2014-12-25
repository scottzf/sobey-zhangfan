package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.constans.ES3MonitorItemEnum;
import com.sobey.api.constans.ItemEnum;
import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.EipEntity;
import com.sobey.api.entity.ElbEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.EsgEntity;
import com.sobey.api.entity.EsgPolicyEntity;
import com.sobey.api.entity.ServiceEntity;
import com.sobey.api.entity.TagEntity;
import com.sobey.api.entity.TenantsEntity;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.core.utils.Encodes;
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
import com.sobey.generate.cmdbuild.MapEcsEipDTO;
import com.sobey.generate.cmdbuild.MapEcsElbDTO;
import com.sobey.generate.cmdbuild.MapEcsEsgDTO;
import com.sobey.generate.cmdbuild.MapEipDnsDTO;
import com.sobey.generate.cmdbuild.MapEipElbDTO;
import com.sobey.generate.cmdbuild.MapTagServiceDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.StorageDTO;
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
import com.sobey.test.data.RandomData;

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

		if (findEcsDTO(tenantsDTO.getId(), ecsName) != null) {
			result.setError(WSResult.PARAMETER_ERROR, "ECS名称已存在.");
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

		result.setMessage("销毁成功.");
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
	public DTOResult<EcsEntity> findECS(String ecsName, String accessKey) {

		DTOResult<EcsEntity> result = new DTOResult<EcsEntity>();

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

		EcsEntity entity = new EcsEntity(ecsDTO.getCode(), ecsDTO.getDescription(), ecsDTO.getRemark(), ecsDTO
				.getIdcDTO().getDescription(), ecsDTO.getIpaddressDTO().getDescription(), ecsDTO.getEcsSpecDTO()
				.getDescription(), ecsDTO.getEcsStatusText());

		result.setDto(entity);

		return result;
	}

	@Override
	public DTOResult<Es3Entity> findES3(String es3Name, String accessKey) {

		DTOResult<Es3Entity> result = new DTOResult<Es3Entity>();

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

		Es3Entity entity = new Es3Entity(es3DTO.getCode(), es3DTO.getRemark(), es3DTO.getDescription(),
				es3DTO.getTotalSize() + "GB", es3DTO.getEs3TypeText());

		result.setDto(entity);

		return result;
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

		if (findEs3DTO(tenantsDTO.getId(), es3Name) != null) {
			result.setError(WSResult.PARAMETER_ERROR, "ES3名称已存在.");
			return result;
		}

		Es3DTO es3DTO = new Es3DTO();
		es3DTO.setAgentType(LookUpConstants.AgentType.NetApp.getValue());
		es3DTO.setDescription(es3Name);
		es3DTO.setTotalSize(es3Size.toString());
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

		result.setMessage("删除成功.");
		return apiService.deleteES3(es3DTO.getId());
	}

	@Override
	public DTOResult<EipEntity> findEIP(String eipName, String accessKey) {

		DTOResult<EipEntity> result = new DTOResult<EipEntity>();

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

		// 查询出EIP关联的ECS信息
		List<EcsEntity> ecsEntities = new ArrayList<EcsEntity>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", eipDTO.getId());
		List<Object> list = cmdbuildSoapService.getMapEcsEipList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();

		for (Object object : list) {

			MapEcsEipDTO mapEcsEipDTO = (MapEcsEipDTO) object;
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEipDTO.getIdObj1())).getDto();
			EcsEntity entity = new EcsEntity(ecsDTO.getCode(), ecsDTO.getDescription(), ecsDTO.getRemark(), ecsDTO
					.getIdcDTO().getDescription(), ecsDTO.getIpaddressDTO().getDescription(), ecsDTO.getEcsSpecDTO()
					.getDescription(), ecsDTO.getEcsStatusText());

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

		EipEntity entity = new EipEntity(eipDTO.getCode(), eipName, eipDTO.getRemark(), eipDTO.getBandwidthText(),
				eipDTO.getIspText(), ecsEntities, elbEntities);

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
	public WSResult associateEIP(String eipName, String serviceId, String accessKey) {

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

		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(Integer.valueOf(serviceId)).getDto();

		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		return apiService.associateEIP(eipDTO.getId(), serviceDTO.getId());
	}

	@Override
	public WSResult dissociateEIP(String eipName, String serviceId, String accessKey) {

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

		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(Integer.valueOf(serviceId)).getDto();

		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.dissociateEIP(eipDTO.getId(), serviceDTO.getId());

		return result;
	}

	@Override
	public DTOResult<ElbEntity> findELB(String elbName, String accessKey) {

		DTOResult<ElbEntity> result = new DTOResult<ElbEntity>();

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

		List<EcsEntity> ecsEntities = new ArrayList<EcsEntity>();

		// 查询出ELB负载的ECS信息
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", elbDTO.getId());
		List<Object> list = cmdbuildSoapService.getMapEcsElbList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		for (Object object : list) {

			MapEcsElbDTO mapEcsElbDTO = (MapEcsElbDTO) object;
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsElbDTO.getIdObj1())).getDto();
			EcsEntity entity = new EcsEntity(ecsDTO.getCode(), ecsDTO.getDescription(), ecsDTO.getRemark(), ecsDTO
					.getIdcDTO().getDescription(), ecsDTO.getIpaddressDTO().getDescription(), ecsDTO.getEcsSpecDTO()
					.getDescription(), ecsDTO.getEcsStatusText());

			ecsEntities.add(entity);

		}

		ElbEntity entity = new ElbEntity(elbDTO.getCode(), elbName, ecsEntities);

		result.setDto(entity);

		return result;
	}

	@Override
	public WSResult createELB(String ecsNames, String protocols, String accessKey) {

		String[] ecsNamesArray = StringUtils.split(ecsNames, ",");
		String[] protocolsArray = StringUtils.split(protocols, ",");

		WSResult result = new WSResult();

		if (protocolsArray.length != ecsNamesArray.length) {
			result.setError(WSResult.PARAMETER_ERROR, "参数错误.");
			return result;
		}

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		List<ElbPolicyDTO> elbPolicyDTOs = new ArrayList<ElbPolicyDTO>();

		Integer[] ecsIds = new Integer[protocolsArray.length];// 存放ecsId的数组

		for (int i = 0; i < protocolsArray.length; i++) {

			EcsDTO ecsDTO = findEcsDTO(tenantsDTO.getId(), ecsNamesArray[i]);
			if (ecsDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "ECS不存在.");
				return result;
			}

			LookUpDTO lookUpDTO = findLookUpDTO(protocolsArray[i], "ELBProtocol");
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

		result.setMessage("删除成功.");
		return result;
	}

	@Override
	public DTOResult<DnsEntity> findDNS(String dnsName, String accessKey) {

		DTOResult<DnsEntity> result = new DTOResult<DnsEntity>();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		DnsDTO dnsDTO = findDnsDTO(tenantsDTO.getId(), dnsName);
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

		DnsEntity entity = new DnsEntity(dnsDTO.getCode(), dnsName, dnsDTO.getRemark(), eipEntities);

		result.setDto(entity);

		return result;

	}

	@Override
	public WSResult createDNS(String domainName, String eipNames, String protocols, String remark, String accessKey) {

		String[] eipNamesArray = StringUtils.split(eipNames, ",");
		String[] protocolsArray = StringUtils.split(protocols, ",");

		WSResult result = new WSResult();

		if (protocolsArray.length != eipNamesArray.length) {
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

		Integer[] eipIds = new Integer[protocolsArray.length];// 存放eipId的数组

		List<DnsPolicyDTO> dnsPolicyDTOs = new ArrayList<DnsPolicyDTO>();

		for (int i = 0; i < protocolsArray.length; i++) {

			EipDTO eipDTO = findEipDTO(tenantsDTO.getId(), eipNamesArray[i]);
			if (eipDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "EIP不存在.");
				return result;
			}

			LookUpDTO lookUpDTO = findLookUpDTO(protocolsArray[i], "DNSProtocol");
			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "协议不存在.");
				return result;
			}

			eipIds[i] = eipDTO.getId();

			DnsPolicyDTO policyDTO = new DnsPolicyDTO();
			policyDTO.setDnsProtocol(lookUpDTO.getId());
			policyDTO.setPort(getPortFromProtocol(protocolsArray[i]).toString());
			policyDTO.setIpaddress(eipIds[i].toString());
			dnsPolicyDTOs.add(policyDTO);

		}

		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		dnsDTO.setTenants(tenantsDTO.getId());
		dnsDTO.setDomainName(domainName);
		dnsDTO.setDescription(domainName);
		dnsDTO.setRemark(remark);

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
	public DTOResult<EsgEntity> findESG(String esgName, String accessKey) {

		DTOResult<EsgEntity> result = new DTOResult<EsgEntity>();

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

		// 查询出ESG关联的ECS信息
		List<EcsEntity> ecsEntities = new ArrayList<EcsEntity>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj2", esgDTO.getId());
		List<Object> list = cmdbuildSoapService.getMapEcsEsgList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		for (Object object : list) {

			MapEcsEsgDTO mapEcsEsgDTO = (MapEcsEsgDTO) object;
			EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(Integer.valueOf(mapEcsEsgDTO.getIdObj1())).getDto();
			EcsEntity entity = new EcsEntity(ecsDTO.getCode(), ecsDTO.getDescription(), ecsDTO.getRemark(), ecsDTO
					.getIdcDTO().getDescription(), ecsDTO.getIpaddressDTO().getDescription(), ecsDTO.getEcsSpecDTO()
					.getDescription(), ecsDTO.getEcsStatusText());

			ecsEntities.add(entity);

		}

		// 查询出ESG关联的policy信息
		List<EsgPolicyEntity> policyEntities = new ArrayList<EsgPolicyEntity>();
		HashMap<String, Object> policyMap = new HashMap<String, Object>();
		policyMap.put("EQ_idObj2", esgDTO.getId());

		for (Object object : cmdbuildSoapService.getEsgPolicyList(CMDBuildUtil.wrapperSearchParams(policyMap))
				.getDtoList().getDto()) {

			EsgPolicyDTO policyDTO = (EsgPolicyDTO) object;

			EsgPolicyEntity entity = new EsgPolicyEntity(policyDTO.getCode(), policyDTO.getDescription(),
					policyDTO.getSourceIP(), policyDTO.getTargetIP(), policyDTO.getPolicyTypeText());

			policyEntities.add(entity);

		}

		EsgEntity entity = new EsgEntity(esgDTO.getCode(), esgName, esgDTO.isIsDefault(), policyEntities, ecsEntities);

		result.setDto(entity);

		return result;
	}

	@Override
	public WSResult createESG(String esgName, String policyTypes, String targetIPs, String remark, String accessKey) {

		// 多个策略用","区分
		String[] policyTypesArray = StringUtils.split(policyTypes, ",");
		String[] targetIPsArray = StringUtils.split(targetIPs, ",");

		WSResult result = new WSResult();

		if (policyTypesArray.length != targetIPsArray.length) {
			result.setError(WSResult.PARAMETER_ERROR, "参数错误.");
			return result;
		}

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		if (findEsgDTO(tenantsDTO.getId(), esgName) != null) {
			result.setError(WSResult.PARAMETER_ERROR, "ESG名称已存在.");
			return result;
		}

		List<EsgPolicyDTO> esgPolicyDTOs = new ArrayList<EsgPolicyDTO>();

		for (int i = 0; i < policyTypesArray.length; i++) {

			LookUpDTO lookUpDTO = findLookUpDTO(policyTypesArray[i], "PolicyType");

			if (lookUpDTO == null) {
				result.setError(WSResult.PARAMETER_ERROR, "策略类型不存在.");
				return result;
			}

			EsgPolicyDTO policyDTO = new EsgPolicyDTO();
			policyDTO.setPolicyType(lookUpDTO.getId());
			policyDTO.setTargetIP(targetIPsArray[i]);
			esgPolicyDTOs.add(policyDTO);
		}

		EsgDTO esgDTO = new EsgDTO();
		esgDTO.setAgentType(LookUpConstants.AgentType.H3C.getValue());
		esgDTO.setTenants(tenantsDTO.getId());
		esgDTO.setDescription(esgName);
		esgDTO.setRemark(remark);

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
	public DTOResult<TagEntity> findTag(String tagName, String accessKey) {

		DTOResult<TagEntity> result = new DTOResult<TagEntity>();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		TagDTO tagDTO = findTagDTO(tenantsDTO.getId(), tagName);
		if (tagDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "Tag不存在.");
			return result;
		}

		// 查询出Tag关联的service信息
		List<ServiceEntity> serviceEntities = new ArrayList<ServiceEntity>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_idObj1", tagDTO.getId());

		List<Object> list = cmdbuildSoapService.getMapTagServiceList(CMDBuildUtil.wrapperSearchParams(map))
				.getDtoList().getDto();

		for (Object object : list) {

			MapTagServiceDTO mapTagServiceDTO = (MapTagServiceDTO) object;

			ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(
					Integer.valueOf(mapTagServiceDTO.getIdObj2())).getDto();

			ServiceEntity entity = new ServiceEntity(serviceDTO.getCode(), serviceDTO.getDescription(),
					serviceDTO.getRemark());

			serviceEntities.add(entity);
		}

		TagEntity entity = new TagEntity(tagDTO.getCode(), tagName, serviceEntities);

		result.setDto(entity);

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

		if (findTagDTO(tenantsDTO.getId(), tagName) != null) {
			result.setError(WSResult.PARAMETER_ERROR, "标签已存在.");
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
	public WSResult associateTag(String tagName, String serviceId, String accessKey) {

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

		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(Integer.valueOf(serviceId)).getDto();

		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.associateTag(tagDTO.getId(), serviceDTO.getId());

		return result;
	}

	@Override
	public WSResult dssociateTag(String tagName, String serviceId, String accessKey) {

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

		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(Integer.valueOf(serviceId)).getDto();

		if (serviceDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "服务资源不存在.");
			return result;
		}

		apiService.dssociateTag(tagDTO.getId(), serviceDTO.getId());

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

		return apiService.getCurrentData(ecsDTO.getId(), ItemEnum.map.get(itemKey));
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

		return apiService.getHistoryData(ecsDTO.getId(), ItemEnum.map.get(itemKey));
	}

	@Override
	public WSResult createTenants(String company, String name, String email, String password, String phone) {

		TenantsDTO tenantsDTO = new TenantsDTO();
		tenantsDTO.setAccessKey(Encodes.encodeBase64(RandomData.randomName("accesskey").getBytes()));
		tenantsDTO.setCompany(company);
		tenantsDTO.setDescription(email);
		tenantsDTO.setEmail(email);
		tenantsDTO.setPassword(password);
		tenantsDTO.setPhone(phone);
		return apiService.createTenants(tenantsDTO);
	}

	@Override
	public DTOResult<TenantsEntity> findTenants(String accessKey) {

		DTOResult<TenantsEntity> result = new DTOResult<TenantsEntity>();

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			result.setError(WSResult.PARAMETER_ERROR, "权限鉴证失败.");
			return result;
		}

		TenantsEntity entity = new TenantsEntity(tenantsDTO.getCode(), tenantsDTO.getDescription(),
				tenantsDTO.getEmail(), tenantsDTO.getPhone(), tenantsDTO.getCompany());

		result.setDto(entity);

		return result;

	}

	@Override
	public ZItemDTO getStorageCurrentData(String es3Name, String itemKey, String accessKey) {

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			return null;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), es3Name);
		if (es3DTO == null) {
			return null;
		}

		StorageDTO storageDTO = (StorageDTO) cmdbuildSoapService.findStorage(es3DTO.getStorage()).getDto();
		if (storageDTO == null) {
			return null;
		}

		return zabbixSoapService.getZItem(storageDTO.getDescription(), getZabbixKey(itemKey, es3Name));
	}

	/**
	 * 根据不同的Es3监控枚举参数转换成zabbix中的键值.<br>
	 * 
	 * eg: VolStatus[/vol/db_sobeyedu/]
	 * 
	 * @param itemKey
	 * @param es3Name
	 * @return
	 */
	private String getZabbixKey(String itemKey, String es3Name) {

		String value = "";

		switch (ES3MonitorItemEnum.valueOf(itemKey)) {

		case 存储已用空间:

			value = "VolSpace[/vol/" + es3Name + "/]";

			break;

		case 存储已用空间百分比:

			value = "VolSpacePercent[/vol/" + es3Name + "/]";

			break;

		case 存储总大小:

			// TODO zabbix无该键值
			break;

		case 存储总大小百分比:

			// TODO zabbix无该键值
			break;
		case 存储状态:

			value = "VolStatus[/vol/" + es3Name + "/]";

			break;

		default:
			break;
		}

		return value;

	}

	@Override
	public ZHistoryItemDTO getStorageHistoryData(String es3Name, String itemKey, String accessKey) {

		TenantsDTO tenantsDTO = findTenantsDTO(accessKey);
		if (tenantsDTO == null) {
			return null;
		}

		Es3DTO es3DTO = findEs3DTO(tenantsDTO.getId(), es3Name);
		if (es3DTO == null) {
			return null;
		}

		StorageDTO storageDTO = (StorageDTO) cmdbuildSoapService.findStorage(es3DTO.getStorage()).getDto();
		if (storageDTO == null) {
			return null;
		}

		return zabbixSoapService.getZHistoryItem(storageDTO.getDescription(), getZabbixKey(itemKey, es3Name));
	}

}
