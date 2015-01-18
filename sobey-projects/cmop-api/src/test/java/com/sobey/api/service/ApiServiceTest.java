package com.sobey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.data.TestData;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallPolicyDTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.IdResult;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest extends TestCase {

	@Autowired
	private ApiService service;

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	@Test
	public void createTenants() {
		TenantsDTO tenantsDTO = TestData.randomTenantsDTO();
		service.createTenants(tenantsDTO);
	}

	@Test
	public void createSubnet() {
		SubnetDTO subnetDTO = TestData.randomSubnetDTO();
		service.createSubnet(subnetDTO);
	}

	@Test
	public void createECS() {
		EcsDTO ecsDTO = TestData.randomEcsDTO();
		service.createECS(ecsDTO);
	}

	@Test
	public void createRouter() {
		EcsDTO ecsDTO = TestData.randomRouterDTO();
		service.createRouter(ecsDTO);
	}

	@Test
	public void createES3() {

		Es3DTO es3DTO = TestData.randomEs3DTO();
		service.createES3(es3DTO, 2742);
	}

	@Test
	public void bindingRouter() {

		Integer routerId = 2805;

		RouterDTO routerDTO = (RouterDTO) cmdbuildSoapService.findRouter(routerId).getDto();

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(192).getDto();
		SubnetDTO subnetDTO2 = (SubnetDTO) cmdbuildSoapService.findSubnet(1462).getDto();

		List<SubnetDTO> subnetDTOs = new ArrayList<SubnetDTO>();
		subnetDTOs.add(subnetDTO);
		subnetDTOs.add(subnetDTO2);

		service.bindingRouter(subnetDTOs, routerDTO);
	}

	@Test
	public void createFirewallService() {

		FirewallServiceDTO firewallServiceDTO = TestData.randomFirewallServiceDTO();

		List<FirewallPolicyDTO> firewallPolicyDTOs = TestData.randomFirewallPolicyDTOs();

		service.createFirewallService(firewallServiceDTO, firewallPolicyDTOs);
	}

	@Test
	public void bindingFirewallService() {

		Integer routerId = 2805;
		Integer firewalserviceId = 2820;

		RouterDTO routerDTO = (RouterDTO) cmdbuildSoapService.findRouter(routerId).getDto();

		FirewallServiceDTO firewallServiceDTO = (FirewallServiceDTO) cmdbuildSoapService.findFirewallService(
				firewalserviceId).getDto();

		service.bindingFirewallService(routerDTO, firewallServiceDTO);
	}

	@Test
	public void createEIP() {

		EipDTO eipDTO = TestData.randomEipDTO();

		IdResult idResult = cmdbuildSoapService.createEip(eipDTO);

		HashMap<String, Object> ipMap = new HashMap<String, Object>();
		ipMap.put("EQ_code", idResult.getMessage());
		EipDTO queryEipDTO = (EipDTO) cmdbuildSoapService.findEipByParams(CMDBuildUtil.wrapperSearchParams(ipMap))
				.getDto();

		EipPolicyDTO policyDTO = new EipPolicyDTO();
		policyDTO.setDescription("123");
		policyDTO.setEip(queryEipDTO.getId());
		policyDTO.setEipProtocol(38);
		policyDTO.setSourcePort(80);
		policyDTO.setTargetPort(80);
		
		cmdbuildSoapService.createEipPolicy(policyDTO);

	}

	@Test
	public void bindingEIP() {

		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(2886).getDto();
		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(2742).getDto();

		service.bindingEIP(eipDTO, serviceDTO);
	}

	@Test
	public void bindingDNS() {

		DnsDTO dnsDTO = TestData.randomDnsDTO();

		List<DnsPolicyDTO> dnsPolicyDTOs = TestData.randomDnsPolicyDTOs();

		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(2850).getDto();
		List<EipDTO> eipDTOs = new ArrayList<EipDTO>();
		eipDTOs.add(eipDTO);

		service.createDNS(dnsDTO, dnsPolicyDTOs, eipDTOs);

	}
}
