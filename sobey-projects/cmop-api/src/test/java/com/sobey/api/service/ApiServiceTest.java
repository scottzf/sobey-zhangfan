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

import com.sobey.api.constans.DataCenterEnum;
import com.sobey.api.data.TestData;
import com.sobey.api.service.data.ConstansData;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.core.utils.Identities;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallPolicyDTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.IdResult;
import com.sobey.generate.cmdbuild.ProducedDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.ServiceDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.InstanceSoapService;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest extends TestCase {

	@Autowired
	private ApiService service;

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	@Autowired
	private InstanceSoapService instanceSoapService;

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
		RouterDTO routerDTO = TestData.randomRouterDTO();

		Integer fsId = 1499;
		FirewallServiceDTO firewallServiceDTO = (FirewallServiceDTO) cmdbuildSoapService.findFirewallService(fsId)
				.getDto();

		service.createRouter(routerDTO, firewallServiceDTO);
	}

	@Test
	public void createES3() {
		Es3DTO es3DTO = TestData.randomEs3DTO();
		service.createES3(es3DTO);
	}

	@Test
	public void bindingES3() {
		Integer es3Id = 3181;
		Integer ecsId = 3265;
		Es3DTO es3DTO = (Es3DTO) cmdbuildSoapService.findEs3(es3Id).getDto();
		EcsDTO ecsDTO = (EcsDTO) cmdbuildSoapService.findEcs(ecsId).getDto();
		service.bindingES3(es3DTO, ecsDTO);
	}

	@Test
	public void bindingRouter() {

		Integer routerId = 1453;

		RouterDTO routerDTO = (RouterDTO) cmdbuildSoapService.findRouter(routerId).getDto();

		SubnetDTO subnetDTO = (SubnetDTO) cmdbuildSoapService.findSubnet(165).getDto();
		SubnetDTO subnetDTO2 = (SubnetDTO) cmdbuildSoapService.findSubnet(1466).getDto();

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

		Integer routerId = 1453;
		Integer firewalserviceId = 2788;

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
		policyDTO.setDescription("25.71.203.22");
		policyDTO.setEip(queryEipDTO.getId());
		policyDTO.setEipProtocol(38);
		policyDTO.setSourcePort(80);
		policyDTO.setTargetPort(80);

		cmdbuildSoapService.createEipPolicy(policyDTO);

	}

	@Test
	public void bindingEIP() {

		Integer eipId = 2799;
		Integer ecsId = 1440;

		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(eipId).getDto();
		ServiceDTO serviceDTO = (ServiceDTO) cmdbuildSoapService.findService(ecsId).getDto();

		service.bindingEIP(eipDTO, serviceDTO);
	}

	@Test
	public void bindingDNS() {

		DnsDTO dnsDTO = TestData.randomDnsDTO();

		List<DnsPolicyDTO> dnsPolicyDTOs = TestData.randomDnsPolicyDTOs();

		EipDTO eipDTO = (EipDTO) cmdbuildSoapService.findEip(2799).getDto();
		List<EipDTO> eipDTOs = new ArrayList<EipDTO>();
		eipDTOs.add(eipDTO);

		service.createDNS(dnsDTO, dnsPolicyDTOs, eipDTOs);
	}

	@Test
	public void createProduced() {

		Integer ecsSpecId = TestData.randomProducedDTO().getEcsSpec();
		EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) cmdbuildSoapService.findEcsSpec(ecsSpecId).getDto();

		instanceSoapService.createFolderOnParentByInstance(DataCenterEnum.成都核心数据中心.toString(),
				ecsSpecDTO.getDescription(), "Produced");

		for (int i = 0; i < 8; i++) {
			ProducedDTO producedDTO = TestData.randomProducedDTO();
			producedDTO.setDescription(ecsSpecDTO.getDescription() + "-" + Identities.randomBase62(8));
			service.createProduced(producedDTO);
			instanceSoapService.moveVMByInstance(DataCenterEnum.成都核心数据中心.toString(), producedDTO.getDescription(),
					ecsSpecDTO.getDescription());
		}
	}

	@Test
	public void queryVMInFolder() {

		/**
		 * 将文件夹的vm同步至CMDB中
		 */

		HashMap<String, Object> allMap = new HashMap<String, Object>();
		List<Object> allECSSPec = cmdbuildSoapService.getEcsSpecList(CMDBuildUtil.wrapperSearchParams(allMap))
				.getDtoList().getDto();

		for (Object object : allECSSPec) {
			EcsSpecDTO ecsSpecDTO = (EcsSpecDTO) object;

			// vcenter中的数据
			List<Object> list = instanceSoapService
					.queryVMInFolderByInstance(DataCenterEnum.成都核心数据中心.toString(), ecsSpecDTO.getDescription())
					.getDtoList().getDto();

			List<Object> list2 = new ArrayList<Object>();
			list2.addAll(list);

			// CMDB中的数据
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("EQ_ecsSpec", ecsSpecDTO.getId());
			List<Object> objs = cmdbuildSoapService.getProducedList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
					.getDto();

			List<Object> objs2 = new ArrayList<Object>();
			objs2.addAll(objs);

			// 得到cmdb中,vcenter不存在的数据.
			objs2.removeAll(list2);

			// 删除
			for (Object obj2 : objs2) {

				ProducedDTO producedDTO = (ProducedDTO) obj2;

				cmdbuildSoapService.deleteProduced(producedDTO.getId());
			}

			for (Object obj : list) {

				String vmName = (String) obj;

				map.put("EQ_description", vmName);
				ProducedDTO dto = (ProducedDTO) cmdbuildSoapService.findProducedByParams(
						CMDBuildUtil.wrapperSearchParams(map)).getDto();

				if (dto == null) {

					ProducedDTO producedDTO = new ProducedDTO();
					producedDTO.setEcsSpec(ecsSpecDTO.getId());
					producedDTO.setDescription(vmName);
					producedDTO.setIdc(ConstansData.idcId);
					cmdbuildSoapService.createProduced(producedDTO);
				}

			}
		}

	}
}
