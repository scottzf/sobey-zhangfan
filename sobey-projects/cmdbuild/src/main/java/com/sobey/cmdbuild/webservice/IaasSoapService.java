package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;

import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

//@WebService(name = "IaasSoapService", targetNamespace = WsConstants.NS)
public interface IaasSoapService {

	// ==============================//
	// =========== Ecs ===========//
	// ==============================//

	DTOResult<EcsDTO> findECS(@WebParam(name = "id") Integer id);

	DTOResult<EcsDTO> findECSByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createECS(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	DTOListResult<EcsDTO> getECSList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult resizeECSs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult DestroyECSs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult powerOpsECS(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult ModifyECSAttributes(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	DTOListResult<EcsDTO> reportECS(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Cs2 ===========//
	// ==============================//

	DTOResult<Cs2DTO> findCs2(@WebParam(name = "id") Integer id);

	DTOResult<Cs2DTO> findCs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createCs2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult deleteCs2(@WebParam(name = "id") Integer id);

	DTOListResult<Cs2DTO> getCs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<Cs2DTO> getCs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult resizeCS2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult operateCS2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult ModifyCS2Attributes(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	DTOListResult<Cs2DTO> reportCS2(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== As2 ===========//
	// ==============================//

	DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id);

	DTOResult<As2DTO> findAs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createAs2(@WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult deleteAs2(@WebParam(name = "id") Integer id);

	DTOListResult<As2DTO> getAs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<As2DTO> getAs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult resizeAS2(@WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult operateAS2(@WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult ModifyAS2Attributes(@WebParam(name = "as2DTO") As2DTO as2DTO);

	DTOListResult<As2DTO> reportAS2(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Eip ===========//
	// ==============================//

	DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id);

	DTOResult<EipDTO> findEipByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult deleteEip(@WebParam(name = "id") Integer id);

	DTOListResult<EipDTO> getEipList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult resizeEIP(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult allocateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult associateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult dissociateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult changeEIPBandwidth(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult operateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult ModifyEIPAttributes(@WebParam(name = "eipDTO") EipDTO eipDTO);

	DTOListResult<EipDTO> reportEIP(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Elb ===========//
	// ==============================//

	DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id);

	DTOResult<ElbDTO> findElbByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult deleteElb(@WebParam(name = "id") Integer id);

	DTOListResult<ElbDTO> getElbList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult allocateELB(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult modifyELBAttributes(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	DTOListResult<ElbDTO> reportELB(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Dns ===========//
	// ==============================//

	DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id);

	DTOResult<DnsDTO> findDnsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createDns(@WebParam(name = "DnsDTO") DnsDTO DnsDTO);

	IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "DnsDTO") DnsDTO DnsDTO);

	IdResult deleteDns(@WebParam(name = "id") Integer id);

	DTOListResult<DnsDTO> getDnsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult modifyDNSAttributes(@WebParam(name = "DnsDTO") DnsDTO DnsDTO);

	DTOListResult<DnsDTO> reportDNS(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Esg ===========//
	// ==============================//

	DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id);

	DTOResult<EsgDTO> findEsgByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "EsgDTO") EsgDTO esgDTO);

	IdResult deleteEsg(@WebParam(name = "id") Integer id);

	DTOListResult<EsgDTO> getEsgList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult ModifyESGAttributes(@WebParam(name = "esgDTO") EsgDTO esgDTO);

	DTOListResult<EsgDTO> reportESG(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== Vpn ===========//
	// ==============================//

	DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id);

	DTOResult<VpnDTO> findVpnByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "VpnDTO") VpnDTO vpnDTO);

	IdResult deleteVpn(@WebParam(name = "id") Integer id);

	DTOListResult<VpnDTO> getVpnList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	IdResult ModifyESGAttributes(@WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult changeVPNPassword(@WebParam(name = "id") Integer id);

	DTOListResult<VpnDTO> reportVPN(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// =========== GroupPolicy ===========//
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

	IdResult ModifyGroupPolicyAttributes(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO);

}
