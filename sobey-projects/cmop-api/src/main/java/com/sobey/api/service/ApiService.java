package com.sobey.api.service;

import java.util.List;

import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.storage.CreateEs3Parameter;

public interface ApiService {

	public void createTenants(TenantsDTO tenantsDTO, Integer agentTypeId);

	public List<TenantsDTO> getTenantsDTO();
	
	public List<IdcDTO> getIdcDTO();
	
	public List<EcsSpecDTO> getEcsSpecDTO();

	public void createECS(EcsDTO ecsDTO);

	public void destroyECS(Integer ecsId);

	public void powerOpsECS(Integer ecsId, String powerOperation);

	public void reconfigECS(Integer ecsId, Integer ecsSpecId);

	public String syncVM(String datacenter);

	public void createES3(Integer tenantsId, CreateEs3Parameter createEs3Parameter, Integer Es3Type, Integer agentTypeId);

	public void attachES3(Integer es3Id, Integer ecsId);

	public void detachES3(Integer es3Id, Integer ecsId);

	public void deleteES3(Integer es3Id);

	public void allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs, Integer agentTypeId);

	public void recoverEIP(Integer eipId);

	public void associateEIP(Integer eipId, Integer serviceId);

	public void dissociateEIP(Integer eipId, Integer serviceId);

	public void createELB(ELBParameter elbParameter, Integer tenantsId, Integer agentTypeId);

	public void deleteELB(Integer elbId);

	public void associateELB(ELBParameter elbParameter, Integer elbId);

	public void dissociateELB(ELBParameter elbParameter, Integer elbId);

	public void createDNS(DNSParameter dnsParameter, Integer tenantsId, Integer idcId, Integer agentTypeId);

	public void deleteDNS(Integer dnsId);

	public void createESG(Integer tenantsId);

	public void deleteESG(Integer esgId);

	public void associateESG(Integer ecsId, Integer esgId);

	public void dissociateESG(Integer esgId);

}
