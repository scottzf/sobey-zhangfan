package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "IaasSoapService", targetNamespace = WsConstants.NS)
public interface IaasSoapService {

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

}
