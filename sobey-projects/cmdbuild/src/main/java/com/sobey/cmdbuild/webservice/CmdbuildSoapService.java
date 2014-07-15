package com.sobey.cmdbuild.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
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
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEipDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEs3DTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEipDnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEipElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapTagServiceDTO;
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

/**
 * CMDBuild模块对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "CmdbuildSoapService", targetNamespace = WsConstants.NS)
public interface CmdbuildSoapService {

	// ==============================//
	// =========== LookUp ===========//
	// == 系统默认表,只读取,不写入 ==//

	DTOResult<LookUpDTO> findLookUp(@WebParam(name = "id") Integer id);

	DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<LookUpDTO> getLookUpPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 *************** Organisation ********************
	 *************************************************/

	// ==============================//
	// =========== Tenants ==========//
	// ==============================//

	DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id);

	DTOResult<TenantsDTO> findTenantsByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult updateTenants(@WebParam(name = "id") Integer id, @WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult deleteTenants(@WebParam(name = "id") Integer id);

	DTOListResult<TenantsDTO> getTenantsList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<TenantsDTO> getTenantsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Tag =============//
	// ==============================//

	DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id);

	DTOResult<TagDTO> findTagByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult deleteTag(@WebParam(name = "id") Integer id);

	DTOListResult<TagDTO> getTagList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ IDC =============//
	// ==============================//

	DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id);

	DTOResult<IdcDTO> findIdcByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult deleteIdc(@WebParam(name = "id") Integer id);

	DTOListResult<IdcDTO> getIdcList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Rack ============//
	// ==============================//

	DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id);

	DTOResult<RackDTO> findRackByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult deleteRack(@WebParam(name = "id") Integer id);

	DTOListResult<RackDTO> getRackList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<RackDTO> getRackPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 ****************** Specification ****************
	 *************************************************/

	// ==============================//
	// ========= DeviceSpec =========//
	// ==============================//

	DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id);

	DTOResult<DeviceSpecDTO> findDeviceSpecByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id);

	DTOListResult<DeviceSpecDTO> getDeviceSpecList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== EcsSpec ==========//
	// ==============================//

	DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id);

	DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult updateEcsSpec(@WebParam(name = "id") Integer id, @WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult deleteEcsSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EcsSpecDTO> getEcsSpecPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 ********************** IaaS *********************
	 *************************************************/

	// ==============================//
	// ============ Log =============//
	// ==============================//

	DTOResult<LogDTO> findLog(@WebParam(name = "id") Integer id);

	DTOResult<LogDTO> findLogByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createLog(@WebParam(name = "logDTO") LogDTO logDTO);

	DTOListResult<LogDTO> getLogList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<LogDTO> getLogPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/********** Service **************/

	// ==============================//
	// ============= Ecs ============//
	// ==============================//

	DTOResult<EcsDTO> findEcs(@WebParam(name = "id") Integer id);

	DTOResult<EcsDTO> findEcsByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEcs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult updateEcs(@WebParam(name = "id") Integer id, @WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult deleteEcs(@WebParam(name = "id") Integer id);

	DTOListResult<EcsDTO> getEcsList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Es3 =============//
	// ==============================//

	DTOResult<Es3DTO> findEs3(@WebParam(name = "id") Integer id);

	DTOResult<Es3DTO> findEs3ByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEs3(@WebParam(name = "es3DTO") Es3DTO es3DTO);

	IdResult updateEs3(@WebParam(name = "id") Integer id, @WebParam(name = "es3DTO") Es3DTO es3DTO);

	IdResult deleteEs3(@WebParam(name = "id") Integer id);

	DTOListResult<Es3DTO> getEs3List(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<Es3DTO> getEs3Pagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============= Eip ============//
	// ==============================//

	DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id);

	DTOResult<EipDTO> findEipByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult deleteEip(@WebParam(name = "id") Integer id);

	DTOListResult<EipDTO> getEipList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Elb =============//
	// ==============================//

	DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id);

	DTOResult<ElbDTO> findElbByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult deleteElb(@WebParam(name = "id") Integer id);

	DTOListResult<ElbDTO> getElbList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Dns =============//
	// ==============================//

	DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id);

	DTOResult<DnsDTO> findDnsByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createDns(@WebParam(name = "dnsDTO") DnsDTO dnsDTO);

	IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "dnsDTO") DnsDTO dnsDTO);

	IdResult deleteDns(@WebParam(name = "id") Integer id);

	DTOListResult<DnsDTO> getDnsList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Esg =============//
	// ==============================//

	DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id);

	DTOResult<EsgDTO> findEsgByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult deleteEsg(@WebParam(name = "id") Integer id);

	DTOListResult<EsgDTO> getEsgList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Vpn =============//
	// ==============================//

	DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id);

	DTOResult<VpnDTO> findVpnByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult deleteVpn(@WebParam(name = "id") Integer id);

	DTOListResult<VpnDTO> getVpnList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/********** Policy **************/

	// ==============================//
	// ========= EipPolicy ==========//
	// ==============================//

	DTOResult<EipPolicyDTO> findEipPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EipPolicyDTO> findEipPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEipPolicy(@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult updateEipPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult deleteEipPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EipPolicyDTO> getEipPolicyList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EipPolicyDTO> getEipPolicyPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= ElbPolicy ==========//
	// ==============================//

	DTOResult<ElbPolicyDTO> findElbPolicy(@WebParam(name = "id") Integer id);

	DTOResult<ElbPolicyDTO> findElbPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createElbPolicy(@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult updateElbPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult deleteElbPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<ElbPolicyDTO> getElbPolicyList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<ElbPolicyDTO> getElbPolicyPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= DnsPolicy ==========//
	// ==============================//

	DTOResult<DnsPolicyDTO> findDnsPolicy(@WebParam(name = "id") Integer id);

	DTOResult<DnsPolicyDTO> findDnsPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createDnsPolicy(@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO);

	IdResult updateDnsPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO);

	IdResult deleteDnsPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<DnsPolicyDTO> getDnsPolicyList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= EsgPolicy ==========//
	// ==============================//

	DTOResult<EsgPolicyDTO> findEsgPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EsgPolicyDTO> findEsgPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createEsgPolicy(@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult updateEsgPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult deleteEsgPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EsgPolicyDTO> getEsgPolicyList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/********** Map **************/

	// ==============================//
	// ========= MapEcsEsg ==========//
	// ==============================//

	DTOResult<MapEcsEsgDTO> findMapEcsEsg(@WebParam(name = "id") Integer id);

	DTOResult<MapEcsEsgDTO> findMapEcsEsgByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId);

	IdResult deleteMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId);

	DTOListResult<MapEcsEsgDTO> getMapEcsEsgList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEcsEsgDTO> getMapEcsEsgPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEcsEs3 ==========//
	// ==============================//

	DTOResult<MapEcsEs3DTO> findMapEcsEs3(@WebParam(name = "id") Integer id);

	DTOResult<MapEcsEs3DTO> findMapEcsEs3ByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEcsEs3(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "es3Id") Integer es3Id);

	IdResult deleteMapEcsEs3(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "es3Id") Integer es3Id);

	DTOListResult<MapEcsEs3DTO> getMapEcsEs3List(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEcsEs3DTO> getMapEcsEs3Pagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEcsEip ==========//
	// ==============================//

	DTOResult<MapEcsEipDTO> findMapEcsEip(@WebParam(name = "id") Integer id);

	DTOResult<MapEcsEipDTO> findMapEcsEipByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId);

	IdResult deleteMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId);

	DTOListResult<MapEcsEipDTO> getMapEcsEipList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEcsEipDTO> getMapEcsEipPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEcsElb ==========//
	// ==============================//

	DTOResult<MapEcsElbDTO> findMapEcsElb(@WebParam(name = "id") Integer id);

	DTOResult<MapEcsElbDTO> findMapEcsElbByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId);

	IdResult deleteMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId);

	DTOListResult<MapEcsElbDTO> getMapEcsElbList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEcsElbDTO> getMapEcsElbPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEipElb ==========//
	// ==============================//

	DTOResult<MapEipElbDTO> findMapEipElb(@WebParam(name = "id") Integer id);

	DTOResult<MapEipElbDTO> findMapEipElbByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId);

	IdResult deleteMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId);

	DTOListResult<MapEipElbDTO> getMapEipElbList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEipElbDTO> getMapEipElbPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEipDns ==========//
	// ==============================//

	DTOResult<MapEipDnsDTO> findMapEipDns(@WebParam(name = "id") Integer id);

	DTOResult<MapEipDnsDTO> findMapEipDnsByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId);

	IdResult deleteMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId);

	DTOListResult<MapEipDnsDTO> getMapEipDnsList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapEipDnsDTO> getMapEipDnsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ======= MapTagService ========//
	// ==============================//

	DTOResult<MapTagServiceDTO> findMapTagService(@WebParam(name = "id") Integer id);

	DTOResult<MapTagServiceDTO> findMapTagServiceByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMapTagService(@WebParam(name = "tagId") Integer tagId,
			@WebParam(name = "serviceId") Integer serviceId, Class<?> serviceClassName);

	IdResult deleteMapTagService(@WebParam(name = "tagId") Integer tagId,
			@WebParam(name = "serviceId") Integer serviceId);

	DTOListResult<MapTagServiceDTO> getMapTagServiceList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MapTagServiceDTO> getMapTagServicePagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 *************** Infrastructure ******************
	 *************************************************/

	/********** Component **************/

	// ==============================//
	// ========== HardDisk ==========//
	// ==============================//

	DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id);

	DTOResult<HardDiskDTO> findHardDiskByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult updateHardDisk(@WebParam(name = "id") Integer id, @WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult deleteHardDisk(@WebParam(name = "id") Integer id);

	DTOListResult<HardDiskDTO> getHardDiskList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<HardDiskDTO> getHardDiskPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Memory ===========//
	// ==============================//

	DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id);

	DTOResult<MemoryDTO> findMemoryByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult deleteMemory(@WebParam(name = "id") Integer id);

	DTOListResult<MemoryDTO> getMemoryList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<MemoryDTO> getMemoryPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============== Nic ===========//
	// ==============================//

	DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id);

	DTOResult<NicDTO> findNicByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult deleteNic(@WebParam(name = "id") Integer id);

	DTOListResult<NicDTO> getNicList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== StorageBox =========//
	// ==============================//

	DTOResult<StorageBoxDTO> findStorageBox(@WebParam(name = "id") Integer id);

	DTOResult<StorageBoxDTO> findStorageBoxByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createStorageBox(@WebParam(name = "storageBoxDTO") StorageBoxDTO storageBoxDTO);

	IdResult updateStorageBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "storageBoxDTO") StorageBoxDTO storageBoxDTO);

	IdResult deleteStorageBox(@WebParam(name = "id") Integer id);

	DTOListResult<StorageBoxDTO> getStorageBoxList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<StorageBoxDTO> getStorageBoxPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/********** Device **************/

	// ==============================//
	// ========== Firewall ==========//
	// ==============================//

	DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id);

	DTOResult<FirewallDTO> findFirewallByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult updateFirewall(@WebParam(name = "id") Integer id, @WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult deleteFirewall(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallDTO> getFirewallList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<FirewallDTO> getFirewallPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ======== LoadBalancer ========//
	// ==============================//

	DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerDTO> findLoadBalancerByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerDTO> getLoadBalancerList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Server ==========//
	// ==============================//

	DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id);

	DTOResult<ServerDTO> findServerByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult deleteServer(@WebParam(name = "id") Integer id);

	DTOListResult<ServerDTO> getServerList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<ServerDTO> getServerPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== Storage ==========//
	// ==============================//

	DTOResult<StorageDTO> findStorage(@WebParam(name = "id") Integer id);

	DTOResult<StorageDTO> findStorageByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createStorage(@WebParam(name = "storageDTO") StorageDTO storageDTO);

	IdResult updateStorage(@WebParam(name = "id") Integer id, @WebParam(name = "storageDTO") StorageDTO storageDTO);

	IdResult deleteStorage(@WebParam(name = "id") Integer id);

	DTOListResult<StorageDTO> getStorageList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<StorageDTO> getStoragePagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Switch =========//
	// ==============================//

	DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id);

	DTOResult<SwitchesDTO> findSwitchesByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult updateSwitches(@WebParam(name = "id") Integer id, @WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult deleteSwitches(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchesDTO> getSwitchesList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<SwitchesDTO> getSwitchesPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/********** Network **************/
	// ==============================//
	// ========== Ipaddress =========//
	// ==============================//

	DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id);

	DTOResult<IpaddressDTO> findIpaddressByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult deleteIpaddress(@WebParam(name = "id") Integer id);

	DTOListResult<IpaddressDTO> getIpaddressList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<IpaddressDTO> getIpaddressPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 分配 IPAddress。将 IPAddress 的状态设置为 “使用”状态
	 */
	IdResult allocateIpaddress(@WebParam(name = "id") Integer id);

	/**
	 * 批量插入 IPAddress
	 * 
	 * 先判断是否有相同的 code，如果有相同的 code 则跳过
	 * 
	 * 初始的状态为“未使用”
	 */
	IdResult insertIpaddress(@WebParam(name = "ipaddressDTOList") List<IpaddressDTO> ipaddressDTOList);

	/**
	 * 初始化 IPAddress , 将 IPAddress 的状态设置为 “未使用”状态
	 */
	IdResult initIpaddress(@WebParam(name = "id") Integer id);

	// ==============================//
	// ============ Vlan ============//
	// ==============================//

	DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id);

	DTOResult<VlanDTO> findVlanByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult deleteVlan(@WebParam(name = "id") Integer id);

	DTOListResult<VlanDTO> getVlanList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<VlanDTO> getVlanPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 
	 * 批量插入 Vlan. 先判断是否有相同的 code，如果有相同的 code 则跳过.
	 */
	IdResult insertVlan(@WebParam(name = "vlanDTOList") List<VlanDTO> vlanDTOList);

	/********** Port **************/

	// ==============================//
	// ======== FirewallPort ========//
	// ==============================//

	DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id);

	DTOResult<FirewallPortDTO> findFirewallPortByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult deleteFirewallPort(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallPortDTO> getFirewallPortList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ====== LoadBalancerPort ======//
	// ==============================//

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(
			@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createLoadBalancerPort(@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(
			@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== StoragePort ========//
	// ==============================//

	DTOResult<StoragePortDTO> findStoragePort(@WebParam(name = "id") Integer id);

	DTOResult<StoragePortDTO> findStoragePortByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createStoragePort(@WebParam(name = "storagePortDTO") StoragePortDTO storagePortDTO);

	IdResult updateStoragePort(@WebParam(name = "id") Integer id,
			@WebParam(name = "storagePortDTO") StoragePortDTO storagePortDTO);

	IdResult deleteStoragePort(@WebParam(name = "id") Integer id);

	DTOListResult<StoragePortDTO> getStoragePortList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<StoragePortDTO> getStoragePortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== ServerPort ========//
	// ==============================//

	DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id);

	DTOResult<ServerPortDTO> findServerPortByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult deleteServerPort(@WebParam(name = "id") Integer id);

	DTOListResult<ServerPortDTO> getServerPortList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<ServerPortDTO> getServerPortPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= SwitchPort =========//
	// ==============================//

	DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id);

	DTOResult<SwitchPortDTO> findSwitchPortByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult deleteSwitchPort(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchPortDTO> getSwitchPortList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<SwitchPortDTO> getSwitchPortPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ NicPort =========//
	// ==============================//

	DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id);

	DTOResult<NicPortDTO> findNicPortByParams(@WebParam(name = "searchParams") SearchParams searchParams);

	IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult updateNicPort(@WebParam(name = "id") Integer id, @WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult deleteNicPort(@WebParam(name = "id") Integer id);

	DTOListResult<NicPortDTO> getNicPortList(@WebParam(name = "searchParams") SearchParams searchParams);

	PaginationResult<NicPortDTO> getNicPortPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

}
