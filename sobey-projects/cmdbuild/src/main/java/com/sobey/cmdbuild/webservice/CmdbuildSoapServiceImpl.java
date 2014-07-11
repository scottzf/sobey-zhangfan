package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.LookUpConstants;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.DnsPolicy;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.EipPolicy;
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.entity.ElbPolicy;
import com.sobey.cmdbuild.entity.Es3;
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.entity.EsgPolicy;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Log;
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.entity.MapEcsEip;
import com.sobey.cmdbuild.entity.MapEcsElb;
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.entity.MapEcsEsg;
import com.sobey.cmdbuild.entity.MapEipDns;
import com.sobey.cmdbuild.entity.MapEipElb;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.entity.StorageBox;
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3DTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.LogDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageDTO;
import com.sobey.cmdbuild.webservice.response.dto.StoragePortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.TableNameUtil;

@WebService(serviceName = "CmdbuildSoapService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CmdbuildSoapServiceImpl extends BasicSoapSevcie implements CmdbuildSoapService {

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<LookUpDTO> findLookUp(@WebParam(name = "id") Integer id) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(id);

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(searchParams.getParamsMap());

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<LookUpDTO> result = new DTOListResult<LookUpDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.lookUpService.getLookUpList(searchParams.getParamsMap()),
					LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LookUpDTO> getLookUpPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<LookUpDTO> result = new PaginationResult<LookUpDTO>();

		try {

			return comm.lookUpService.getLookUpDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenants(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<TenantsDTO> findTenantsByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createTenants(TenantsDTO tenantsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateTenants(Integer id, TenantsDTO tenantsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteTenants(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<TenantsDTO> getTenantsList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<TenantsDTO> getTenantsPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<TagDTO> findTag(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<TagDTO> findTagByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createTag(TagDTO tagDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateTag(Integer id, TagDTO tagDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteTag(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<TagDTO> getTagList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<TagDTO> getTagPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<IdcDTO> findIdc(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<IdcDTO> findIdcByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createIdc(IdcDTO idcDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateIdc(Integer id, IdcDTO idcDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteIdc(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<IdcDTO> getIdcList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<IdcDTO> getIdcPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<RackDTO> findRack(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<RackDTO> findRackByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createRack(RackDTO rackDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateRack(Integer id, RackDTO rackDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteRack(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<RackDTO> getRackList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<RackDTO> getRackPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createDeviceSpec(DeviceSpecDTO deviceSpecDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateDeviceSpec(Integer id, DeviceSpecDTO deviceSpecDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteDeviceSpec(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpec(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEcsSpec(EcsSpecDTO ecsSpecDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEcsSpec(Integer id, EcsSpecDTO ecsSpecDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEcsSpec(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LogDTO> findLog(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LogDTO> findLogByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createLog(LogDTO logDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateLog(Integer id, LogDTO logDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteLog(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<LogDTO> getLogList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<LogDTO> getLogPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EcsDTO> findEcs(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EcsDTO> findEcsByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEcs(EcsDTO ecsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEcs(Integer id, EcsDTO ecsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEcs(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EcsDTO> getEcsList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EcsDTO> getEcsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<Es3DTO> findEs3(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<Es3DTO> findEs3ByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEs3(Es3DTO es3dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEs3(Integer id, Es3DTO es3dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEs3(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<Es3DTO> getEs3List(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<Es3DTO> getEs3Pagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipDTO> findEip(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipDTO> findEipByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEip(EipDTO eipDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEip(Integer id, EipDTO eipDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEip(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EipDTO> getEipList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EipDTO> getEipPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbDTO> findElb(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbDTO> findElbByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createElb(ElbDTO elbDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateElb(Integer id, ElbDTO elbDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteElb(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ElbDTO> getElbList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ElbDTO> getElbPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsDTO> findDns(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsDTO> findDnsByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createDns(DnsDTO dnsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateDns(Integer id, DnsDTO dnsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteDns(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<DnsDTO> getDnsList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DnsDTO> getDnsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgDTO> findEsg(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgDTO> findEsgByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEsg(EsgDTO esgDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEsg(Integer id, EsgDTO esgDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEsg(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EsgDTO> getEsgList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EsgDTO> getEsgPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VpnDTO> findVpn(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VpnDTO> findVpnByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createVpn(VpnDTO vpnDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateVpn(Integer id, VpnDTO vpnDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteVpn(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<VpnDTO> getVpnList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<VpnDTO> getVpnPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEipPolicy(EipPolicyDTO eipPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEipPolicy(Integer id, EipPolicyDTO eipPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEipPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EipPolicyDTO> getEipPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EipPolicyDTO> getEipPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createElbPolicy(ElbPolicyDTO elbPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateElbPolicy(Integer id, ElbPolicyDTO elbPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteElbPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ElbPolicyDTO> getElbPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ElbPolicyDTO> getElbPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createDnsPolicy(DnsPolicyDTO dnsPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateDnsPolicy(Integer id, DnsPolicyDTO dnsPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteDnsPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<DnsPolicyDTO> getDnsPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEsgPolicy(EsgPolicyDTO esgPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEsgPolicy(Integer id, EsgPolicyDTO esgPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEsgPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EsgPolicyDTO> getEsgPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEsg(Integer ecsId, Integer esgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEsg(Integer ecsId, Integer esgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEs3(Integer ecsId, Integer es3Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEs3(Integer ecsId, Integer es3Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEip(Integer ecsId, Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEip(Integer ecsId, Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsElb(Integer ecsId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsElb(Integer ecsId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEipElb(Integer eipId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEipElb(Integer eipId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEipDns(Integer eipId, Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEipDns(Integer eipId, Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapTagService(Integer tagId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapTagService(Integer tagId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDisk(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDiskByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createHardDisk(HardDiskDTO hardDiskDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateHardDisk(Integer id, HardDiskDTO hardDiskDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteHardDisk(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<HardDiskDTO> getHardDiskList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<HardDiskDTO> getHardDiskPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<MemoryDTO> findMemory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<MemoryDTO> findMemoryByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMemory(MemoryDTO memoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateMemory(Integer id, MemoryDTO memoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMemory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<MemoryDTO> getMemoryList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<MemoryDTO> getMemoryPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicDTO> findNic(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicDTO> findNicByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createNic(NicDTO nicDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateNic(Integer id, NicDTO nicDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteNic(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<NicDTO> getNicList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<NicDTO> getNicPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageBoxDTO> findStorageBox(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageBoxDTO> findStorageBoxByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createStorageBox(StorageBoxDTO storageBoxDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateStorageBox(Integer id, StorageBoxDTO storageBoxDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteStorageBox(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<StorageBoxDTO> getStorageBoxList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<StorageBoxDTO> getStorageBoxPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallDTO> findFirewall(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallDTO> findFirewallByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createFirewall(FirewallDTO firewallDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateFirewall(Integer id, FirewallDTO firewallDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteFirewall(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<FirewallDTO> getFirewallList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<FirewallDTO> getFirewallPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancerByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createLoadBalancer(LoadBalancerDTO loadBalancerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateLoadBalancer(Integer id, LoadBalancerDTO loadBalancerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteLoadBalancer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<LoadBalancerDTO> getLoadBalancerList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerDTO> findServer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerDTO> findServerByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createServer(ServerDTO serverDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateServer(Integer id, ServerDTO serverDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteServer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ServerDTO> getServerList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ServerDTO> getServerPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageDTO> findStorage(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageDTO> findStorageByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createStorage(StorageDTO storageDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateStorage(Integer id, StorageDTO storageDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteStorage(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<StorageDTO> getStorageList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<StorageDTO> getStoragePagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitches(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitchesByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createSwitches(SwitchesDTO switchesDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateSwitches(Integer id, SwitchesDTO switchesDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteSwitches(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<SwitchesDTO> getSwitchesList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<SwitchesDTO> getSwitchesPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddressByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createIpaddress(IpaddressDTO ipaddressDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateIpaddress(Integer id, IpaddressDTO ipaddressDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<IpaddressDTO> getIpaddressList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<IpaddressDTO> getIpaddressPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult allocateIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult insertIpaddress(List<IpaddressDTO> ipaddressDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult initIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VlanDTO> findVlan(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VlanDTO> findVlanByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createVlan(VlanDTO vlanDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateVlan(Integer id, VlanDTO vlanDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteVlan(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<VlanDTO> getVlanList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<VlanDTO> getVlanPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult insertVlan(List<VlanDTO> vlanDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createFirewallPort(FirewallPortDTO firewallPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateFirewallPort(Integer id, FirewallPortDTO firewallPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteFirewallPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<FirewallPortDTO> getFirewallPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createLoadBalancerPort(LoadBalancerPortDTO loadBalancerPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateLoadBalancerPort(Integer id, LoadBalancerPortDTO loadBalancerPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteLoadBalancerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StoragePortDTO> findStoragePort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StoragePortDTO> findStoragePortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createStoragePort(StoragePortDTO storagePortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateStoragePort(Integer id, StoragePortDTO storagePortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteStoragePort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<StoragePortDTO> getStoragePortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<StoragePortDTO> getStoragePortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createServerPort(ServerPortDTO serverPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateServerPort(Integer id, ServerPortDTO serverPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteServerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ServerPortDTO> getServerPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ServerPortDTO> getServerPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createSwitchPort(SwitchPortDTO switchPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateSwitchPort(Integer id, SwitchPortDTO switchPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteSwitchPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<SwitchPortDTO> getSwitchPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<SwitchPortDTO> getSwitchPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicPortDTO> findNicPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicPortDTO> findNicPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createNicPort(NicPortDTO nicPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateNicPort(Integer id, NicPortDTO nicPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteNicPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<NicPortDTO> getNicPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<NicPortDTO> getNicPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
