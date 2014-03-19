package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
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

	DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LookUpDTO> getLookUpPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 *************** Organisation ********************
	 *************************************************/

	// ==============================//
	// =========== Comany ===========//
	// ==============================//

	DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id);

	DTOResult<CompanyDTO> findCompanyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

	DTOListResult<CompanyDTO> getCompanyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Tenants ==========//
	// ==============================//

	DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id);

	DTOResult<TenantsDTO> findTenantsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult updateTenants(@WebParam(name = "id") Integer id, @WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult deleteTenants(@WebParam(name = "id") Integer id);

	DTOListResult<TenantsDTO> getTenantsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<TenantsDTO> getTenantsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Tag =============//
	// ==============================//

	DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id);

	DTOResult<TagDTO> findTagByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult deleteTag(@WebParam(name = "id") Integer id);

	DTOListResult<TagDTO> getTagList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ IDC =============//
	// ==============================//

	DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id);

	DTOResult<IdcDTO> findIdcByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult deleteIdc(@WebParam(name = "id") Integer id);

	DTOListResult<IdcDTO> getIdcList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Rack ============//
	// ==============================//

	DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id);

	DTOResult<RackDTO> findRackByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult deleteRack(@WebParam(name = "id") Integer id);

	DTOListResult<RackDTO> getRackList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<RackDTO> getRackPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 ****************** Financial ********************
	 *************************************************/

	// ==============================//
	// ========= DeviceSpec =========//
	// ==============================//

	DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id);

	DTOResult<DeviceSpecDTO> findDeviceSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id);

	DTOListResult<DeviceSpecDTO> getDeviceSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== EcsSpec ==========//
	// ==============================//

	DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id);

	DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult updateEcsSpec(@WebParam(name = "id") Integer id, @WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult deleteEcsSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== EipSpec ==========//
	// ==============================//

	DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id);

	DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult updateEipSpec(@WebParam(name = "id") Integer id, @WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult deleteEipSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Es3Spec =========//
	// ==============================//

	DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id);

	DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult updateEs3Spec(@WebParam(name = "id") Integer id, @WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult deleteEs3Spec(@WebParam(name = "id") Integer id);

	DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============= Ecs ============//
	// ==============================//

	DTOResult<EcsDTO> findEcs(@WebParam(name = "id") Integer id);

	DTOResult<EcsDTO> findEcsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEcs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult updateEcs(@WebParam(name = "id") Integer id, @WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult deleteEcs(@WebParam(name = "id") Integer id);

	DTOListResult<EcsDTO> getEcsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Cs2 =============//
	// ==============================//

	DTOResult<Cs2DTO> findCs2(@WebParam(name = "id") Integer id);

	DTOResult<Cs2DTO> findCs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createCs2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult deleteCs2(@WebParam(name = "id") Integer id);

	DTOListResult<Cs2DTO> getCs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<Cs2DTO> getCs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============== As2 ===========//
	// ==============================//

	DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id);

	DTOResult<As2DTO> findAs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createAs2(@WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult deleteAs2(@WebParam(name = "id") Integer id);

	DTOListResult<As2DTO> getAs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<As2DTO> getAs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============= Eip ============//
	// ==============================//

	DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id);

	DTOResult<EipDTO> findEipByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult deleteEip(@WebParam(name = "id") Integer id);

	DTOListResult<EipDTO> getEipList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= EipPolicy ==========//
	// ==============================//

	DTOResult<EipPolicyDTO> findEipPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EipPolicyDTO> findEipPolicyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEipPolicy(@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult updateEipPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult deleteEipPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EipPolicyDTO> getEipPolicyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EipPolicyDTO> getEipPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Elb =============//
	// ==============================//

	DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id);

	DTOResult<ElbDTO> findElbByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult deleteElb(@WebParam(name = "id") Integer id);

	DTOListResult<ElbDTO> getElbList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= ElbPolicy ==========//
	// ==============================//

	DTOResult<ElbPolicyDTO> findElbPolicy(@WebParam(name = "id") Integer id);

	DTOResult<ElbPolicyDTO> findElbPolicyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createElbPolicy(@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult updateElbPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult deleteElbPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<ElbPolicyDTO> getElbPolicyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ElbPolicyDTO> getElbPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Dns =============//
	// ==============================//

	DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id);

	DTOResult<DnsDTO> findDnsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createDns(@WebParam(name = "dnsDTO") DnsDTO DnsDTO);

	IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "dnsDTO") DnsDTO DnsDTO);

	IdResult deleteDns(@WebParam(name = "id") Integer id);

	DTOListResult<DnsDTO> getDnsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= DnsPolicy ==========//
	// ==============================//

	DTOResult<DnsPolicyDTO> findDnsPolicy(@WebParam(name = "id") Integer id);

	DTOResult<DnsPolicyDTO> findDnsPolicyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createDnsPolicy(@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO);

	IdResult updateDnsPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO);

	IdResult deleteDnsPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<DnsPolicyDTO> getDnsPolicyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Esg =============//
	// ==============================//

	DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id);

	DTOResult<EsgDTO> findEsgByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult deleteEsg(@WebParam(name = "id") Integer id);

	DTOListResult<EsgDTO> getEsgList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= EsgPolicy ==========//
	// ==============================//

	DTOResult<EsgPolicyDTO> findEsgPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EsgPolicyDTO> findEsgPolicyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEsgPolicy(@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult updateEsgPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult deleteEsgPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EsgPolicyDTO> getEsgPolicyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Vpn =============//
	// ==============================//

	DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id);

	DTOResult<VpnDTO> findVpnByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult deleteVpn(@WebParam(name = "id") Integer id);

	DTOListResult<VpnDTO> getVpnList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= GroupPolicy ========//
	// ==============================//

	DTOResult<GroupPolicyDTO> findGroupPolicy(@WebParam(name = "id") Integer id);

	DTOResult<GroupPolicyDTO> findGroupPolicyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createGroupPolicy(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO);

	IdResult updateGroupPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO);

	IdResult deleteGroupPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<GroupPolicyDTO> getGroupPolicyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<GroupPolicyDTO> getGroupPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= MapEcsEsg ==========//
	// ==============================//

	IdResult createMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId);

	IdResult deleteMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId);

	// ==============================//
	// ========= MapEcsAs2 ==========//
	// ==============================//

	IdResult createMapEcsAs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "as2Id") Integer as2Id);

	IdResult deleteMapEcsAs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "as2Id") Integer as2Id);

	// ==============================//
	// ========= MapEcsCs2 ==========//
	// ==============================//

	IdResult createMapEcsCs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "cs2Id") Integer cs2Id);

	IdResult deleteMapEcsCs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "cs2Id") Integer cs2Id);

	// ==============================//
	// ========= MapEcsEip ==========//
	// ==============================//

	IdResult createMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId);

	IdResult deleteMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId);

	// ==============================//
	// ========= MapEcsElb ==========//
	// ==============================//

	IdResult createMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId);

	IdResult deleteMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId);

	// ==============================//
	// ========= MapEipElb ==========//
	// ==============================//

	IdResult createMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId);

	IdResult deleteMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId);

	// ==============================//
	// ========= MapEipDns ==========//
	// ==============================//

	IdResult createMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId);

	IdResult deleteMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId);

	// ==============================//
	// ====== MapGroupPolicyVlan ====//
	// ==============================//

	IdResult createMapGroupPolicyVlan(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "vlanId") Integer vlanId);

	IdResult deleteMapGroupPolicyVlan(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "vlanId") Integer vlanId);

	// ==============================//
	// === MapGroupPolicyIpaddress ==//
	// ==============================//

	IdResult createMapGroupPolicyIpaddress(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "ipaddressId") Integer ipaddressId);

	IdResult deleteMapGroupPolicyIpaddress(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "ipaddressId") Integer ipaddressId);

	// ==============================//
	// ====== MapVpnGroupPolicy =====//
	// ==============================//

	IdResult createMapVpnGroupPolicy(@WebParam(name = "vpnId") Integer vpnId,
			@WebParam(name = "groupPolicyId") Integer groupPolicyId);

	IdResult deleteMapVpnGroupPolicy(@WebParam(name = "vpnId") Integer vpnId,
			@WebParam(name = "groupPolicyId") Integer groupPolicyId);

	/*************************************************
	 *************** Infrastructure ******************
	 *************************************************/

	// ==============================//
	// =========== Fimas ============//
	// ==============================//

	DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id);

	DTOResult<FimasDTO> findFimasByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimas(@WebParam(name = "fimasDTO") FimasDTO fimasDTO);

	IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "fimasDTO") FimasDTO fimasDTO);

	IdResult deleteFimas(@WebParam(name = "id") Integer id);

	DTOListResult<FimasDTO> getFimasList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasDTO> getFimasPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== FimasBox ==========//
	// ==============================//

	DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id);

	DTOResult<FimasBoxDTO> findFimasBoxByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimasBox(@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult updateFimasBox(@WebParam(name = "id") Integer id, @WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult deleteFimasBox(@WebParam(name = "id") Integer id);

	DTOListResult<FimasBoxDTO> getFimasBoxList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== FimasPort =========//
	// ==============================//

	DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id);

	DTOResult<FimasPortDTO> findFimasPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimasPort(@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult deleteFimasPort(@WebParam(name = "id") Integer id);

	DTOListResult<FimasPortDTO> getFimasPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== Firewall ==========//
	// ==============================//

	DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id);

	DTOResult<FirewallDTO> findFirewallByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult updateFirewall(@WebParam(name = "id") Integer id, @WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult deleteFirewall(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallDTO> getFirewallList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ======== FirewallPort ========//
	// ==============================//

	DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id);

	DTOResult<FirewallPortDTO> findFirewallPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult deleteFirewallPort(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallPortDTO> getFirewallPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== HardDisk ==========//
	// ==============================//

	DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id);

	DTOResult<HardDiskDTO> findHardDiskByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult updateHardDisk(@WebParam(name = "id") Integer id, @WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult deleteHardDisk(@WebParam(name = "id") Integer id);

	DTOListResult<HardDiskDTO> getHardDiskList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== Ipaddress =========//
	// ==============================//

	DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id);

	DTOResult<IpaddressDTO> findIpaddressByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult deleteIpaddress(@WebParam(name = "id") Integer id);

	DTOListResult<IpaddressDTO> getIpaddressList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<IpaddressDTO> getIpaddressPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 分配 IPAddress。将 IPAddress 的状态设置为 “使用”状态
	 * 
	 * @param id
	 * @return IdResult
	 */
	IdResult allocateIpaddress(@WebParam(name = "id") Integer id);

	/**
	 * 批量插入 IPAddress。<br/>
	 * 先判断是否有相同的 code，如果有相同的 code 则跳过。<br/>
	 * 初始的状态为“未使用” 。<br/>
	 * 
	 * @param ipaddressDTOList
	 * @return IdResult
	 */
	IdResult insertIpaddress(@WebParam(name = "ipaddressDTOList") List<IpaddressDTO> ipaddressDTOList);

	/**
	 * 初始化 IPAddress。<br/>
	 * 将 IPAddress 的状态设置为 “未使用”状态<br/>
	 * 
	 * @param id
	 * @return IdResult
	 */
	IdResult initIpaddress(@WebParam(name = "id") Integer id);

	// ==============================//
	// ======== LoadBalancer ========//
	// ==============================//

	DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerDTO> findLoadBalancerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerDTO> getLoadBalancerList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ====== LoadBalancerPort ======//
	// ==============================//

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createLoadBalancerPort(@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Memory ===========//
	// ==============================//

	DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id);

	DTOResult<MemoryDTO> findMemoryByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult deleteMemory(@WebParam(name = "id") Integer id);

	DTOListResult<MemoryDTO> getMemoryList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<MemoryDTO> getMemoryPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== NetappBox =========//
	// ==============================//

	DTOResult<NetappBoxDTO> findNetappBox(@WebParam(name = "id") Integer id);

	DTOResult<NetappBoxDTO> findNetappBoxByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappBox(@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult updateNetappBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult deleteNetappBox(@WebParam(name = "id") Integer id);

	DTOListResult<NetappBoxDTO> getNetappBoxList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappBoxDTO> getNetappBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ====== NetappController ======//
	// ==============================//

	DTOResult<NetappControllerDTO> findNetappController(@WebParam(name = "id") Integer id);

	DTOResult<NetappControllerDTO> findNetappControllerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappController(@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult updateNetappController(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult deleteNetappController(@WebParam(name = "id") Integer id);

	DTOListResult<NetappControllerDTO> getNetappControllerList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappControllerDTO> getNetappControllerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== NetappPort ========//
	// ==============================//

	DTOResult<NetappPortDTO> findNetappPort(@WebParam(name = "id") Integer id);

	DTOResult<NetappPortDTO> findNetappPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappPort(@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO);

	IdResult updateNetappPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO);

	IdResult deleteNetappPort(@WebParam(name = "id") Integer id);

	DTOListResult<NetappPortDTO> getNetappPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappPortDTO> getNetappPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============== Nic ===========//
	// ==============================//

	DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id);

	DTOResult<NicDTO> findNicByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult deleteNic(@WebParam(name = "id") Integer id);

	DTOListResult<NicDTO> getNicList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ NicPort =========//
	// ==============================//

	DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id);

	DTOResult<NicPortDTO> findNicPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult updateNicPort(@WebParam(name = "id") Integer id, @WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult deleteNicPort(@WebParam(name = "id") Integer id);

	DTOListResult<NicPortDTO> getNicPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NicPortDTO> getNicPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Server ==========//
	// ==============================//

	DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id);

	DTOResult<ServerDTO> findServerByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult deleteServer(@WebParam(name = "id") Integer id);

	DTOListResult<ServerDTO> getServerList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ServerDTO> getServerPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== ServerPort ========//
	// ==============================//

	DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id);

	DTOResult<ServerPortDTO> findServerPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult deleteServerPort(@WebParam(name = "id") Integer id);

	DTOListResult<ServerPortDTO> getServerPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Switches =========//
	// ==============================//

	DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id);

	DTOResult<SwitchesDTO> findSwitchesByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult updateSwitches(@WebParam(name = "id") Integer id, @WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult deleteSwitches(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchesDTO> getSwitchesList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<SwitchesDTO> getSwitchesPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= SwitchPort =========//
	// ==============================//

	DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id);

	DTOResult<SwitchPortDTO> findSwitchPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult deleteSwitchPort(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchPortDTO> getSwitchPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<SwitchPortDTO> getSwitchPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Vlan ============//
	// ==============================//

	DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id);

	DTOResult<VlanDTO> findVlanByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult deleteVlan(@WebParam(name = "id") Integer id);

	DTOListResult<VlanDTO> getVlanList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<VlanDTO> getVlanPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 
	 * 批量插入 Vlan. 先判断是否有相同的 code，如果有相同的 code 则跳过.
	 * 
	 * @param vlanDTOList
	 * @return IdResult
	 */
	IdResult insertVlan(@WebParam(name = "vlanDTOList") List<VlanDTO> vlanDTOList);

}
