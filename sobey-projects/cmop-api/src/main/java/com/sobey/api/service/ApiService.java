package com.sobey.api.service;

import java.util.List;

import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallPolicyDTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.ProducedDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.VMRCDTO;

public interface ApiService {

	// ==================== Tenants ====================//

	public WSResult createTenants(TenantsDTO tenantsDTO);

	// ==================== Subnet ====================//

	public WSResult createSubnet(SubnetDTO subnetDTO);

	// ==================== ECS ====================//

	public WSResult createECS(EcsDTO ecsDTO);

	public WSResult destroyECS(Integer ecsId);

	public WSResult powerOpsECS(Integer ecsId, String powerOperation);

	// ==================== ES3 ====================//

	public WSResult createES3(Es3DTO es3DTO, Integer ecsId);

	public WSResult deleteES3(Integer es3Id);

	// ==================== Router ====================//

	public WSResult createRouter(RouterDTO routerDTO, FirewallServiceDTO firewallServiceDTO);

	public WSResult bindingRouter(List<SubnetDTO> subnetDTOs, RouterDTO routerDTO);

	// ==================== Firewall Service ====================//

	public WSResult createFirewallService(FirewallServiceDTO firewallServiceDTO,
			List<FirewallPolicyDTO> firewallPolicyDTOs);

	public WSResult bindingFirewallService(RouterDTO routerDTO, FirewallServiceDTO firewallServiceDTO);

	// ==================== EIP ====================//

	public WSResult applyEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs);

	public WSResult recoverEIP(Integer eipId);

	public WSResult bindingEIP(EipDTO eipDTO, ServiceDTO serviceDTO);

	public WSResult unbindingEIP(EipDTO eipDTO, ServiceDTO serviceDTO);

	// ==================== DNS ====================//

	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, List<EipDTO> eipDTOs);

	public WSResult deleteDNS(Integer dnsId);

	// ==================== VMRC ====================//

	public VMRCDTO findVMRCDTO(EcsDTO ecsDTO);

	// ==================== Produced ====================//

	public WSResult createProduced(ProducedDTO producedDTO);

}
