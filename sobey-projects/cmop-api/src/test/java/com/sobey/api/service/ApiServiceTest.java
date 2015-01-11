package com.sobey.api.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.TestData;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.ConfigFirewallServiceCategoryDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
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
	public void bindingRouter() {

		Integer routerId = 2834;

		RouterDTO routerDTO = (RouterDTO) cmdbuildSoapService.findRouter(routerId).getDto();

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(185).getDto();
		SubnetDTO subnetDTO2 = (SubnetDTO) cmdbuildSoapService.findSubnet(1463).getDto();

		List<SubnetDTO> subnetDTOs = new ArrayList<SubnetDTO>();
		subnetDTOs.add(subnetDTO);
		subnetDTOs.add(subnetDTO2);

		service.bindingRouter(subnetDTOs, routerDTO);
	}

	@Test
	public void createFirewallService() {
		FirewallServiceDTO firewallServiceDTO = new FirewallServiceDTO();
		List<ConfigFirewallServiceCategoryDTO> categoryDTOs = new ArrayList<ConfigFirewallServiceCategoryDTO>();
		service.createFirewallService(firewallServiceDTO, categoryDTOs);
	}

	@Test
	public void bindingFirewallService() {

		RouterDTO routerDTO = new RouterDTO();
		List<FirewallServiceDTO> firewallServiceDTOs = new ArrayList<FirewallServiceDTO>();
		service.bindingFirewallService(routerDTO, firewallServiceDTOs);
	}

}
