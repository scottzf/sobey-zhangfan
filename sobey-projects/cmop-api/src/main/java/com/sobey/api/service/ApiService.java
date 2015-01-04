package com.sobey.api.service;

import java.util.List;

import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

public interface ApiService {

	// ==================== Tenants ====================//

	public WSResult createTenants(TenantsDTO tenantsDTO);

	// ==================== Subnet ====================//

	public WSResult createSubnet();

	public WSResult deleteSubnet();

	// ==================== ECS ====================//

	public WSResult createECS(EcsDTO ecsDTO);

	public WSResult destroyECS(Integer ecsId);

	public WSResult powerOpsECS(Integer ecsId, String powerOperation);

	public WSResult reconfigECS(Integer ecsId, Integer ecsSpecId);

	// ==================== ES3 ====================//

	public WSResult createES3(Es3DTO es3DTO, String vmName);

	public WSResult deleteES3(Integer es3Id);

	// ==================== EIP ====================//

	public WSResult applyEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs);

	public WSResult recoverEIP(Integer eipId);

	public WSResult bindingEIP(Integer eipId, Integer serviceId);

	public WSResult unbindingEIP(Integer eipId, Integer serviceId);

	// ==================== ELB ====================//

	public WSResult createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds);

	public WSResult deleteELB(Integer elbId);

	public WSResult bindingELB();

	public WSResult unbindingELB();

	// ==================== DNS ====================//

	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, Integer[] eipIds);

	public WSResult deleteDNS(Integer dnsId);

	// ==================== Router ====================//

	public WSResult createRouter();

	public WSResult deleteRouter();

	public WSResult bindingRouter();

	public WSResult unbindingRouter();

	// ==================== Firewall ====================//

	public WSResult createFirewall();

	public WSResult deleteFirewall();

	public WSResult bindingFirewall();

	public WSResult unbindingFirewall();

}
