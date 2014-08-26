package com.sobey.api.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.TagRelation;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.test.data.RandomData;

/**
 * IdcService 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiTest extends TestCase {

	@Autowired
	public ApiService service;

	@Test
	public void getTag() {
		List<TagRelation> list = service.getTagRelation(2542);
		for (TagRelation tagRelation : list) {
			System.out.println(tagRelation.getServiceName());
			System.out.println(tagRelation.getTagName());
		}
	}

	// @Test
	public void createtenants() {

		TenantsDTO tenantsDTO = new TenantsDTO();
		tenantsDTO.setDescription("kai8406@gmail.com");
		tenantsDTO.setPhone(RandomData.randomName("phone"));
		tenantsDTO.setRemark(RandomData.randomName("remark"));
		tenantsDTO.setPassword(RandomData.randomName("password"));
		tenantsDTO.setEmail(RandomData.randomName("email"));
		tenantsDTO.setAccessKey(RandomData.randomName("accessKey"));
		tenantsDTO.setCompany(RandomData.randomName("company"));
		tenantsDTO.setCreateInfo(RandomData.randomName("createInfo"));

		service.createTenants(tenantsDTO);
	}

	@Test
	public void createECS() {

		Integer tenantsId = 344;
		Integer ecsSpecId = 130;
		Integer idcId = 110;

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setTenants(tenantsId);
		ecsDTO.setEcsSpec(ecsSpecId);
		ecsDTO.setRemark("测试环境");
		ecsDTO.setIdc(idcId);
		ecsDTO.setDescription("云非编-2");

		service.createECS(ecsDTO);
	}

	@Test
	public void destroyECS() {
		Integer ecsId = 445;
		service.destroyECS(ecsId);
	}

	@Test
	public void powerECS() {
		Integer ecsId = 430;
		service.powerOpsECS(ecsId, "poweroff");
	}

	@Test
	public void reconfigECS() {
		Integer ecsSpecId = 130;
		Integer ecsId = 430;
		service.reconfigECS(ecsId, ecsSpecId);
	}

	@Test
	public void syncVM() {
		System.out.println(service.syncVM("xa"));
	}

	@Test
	public void createEs3() {
		// Integer tenantsId = 1418;
		// CreateEs3Parameter createEs3Parameter = new CreateEs3Parameter();
		// createEs3Parameter.setVolumeName("Sobey");
		// createEs3Parameter.setVolumeSize("20");
		// service.createES3(tenantsId, createEs3Parameter, LookUpConstants.ES3Type.高IOPS.getValue(),
		// LookUpConstants.AgentType.NetApp.getValue());
	}

	@Test
	public void attachES3() {
		service.attachES3(1780, 1766);
	}

	@Test
	public void detachES3() {
		service.detachES3(1780, 1766);
	}

	@Test
	public void deleteES3() {
		service.deleteES3(1780);
	}

	@Test
	public void allocateEIP() {

		Integer tenantsId = 1418;
		Integer ispId = 65;
		// Integer ispId = 29;

		EipDTO eipDTO = new EipDTO();
		eipDTO.setBandwidth(1);
		eipDTO.setTenants(tenantsId);
		eipDTO.setIsp(ispId);
		eipDTO.setAgentType(LookUpConstants.ISP.中国联通.getValue());

		List<EipPolicyDTO> eipPolicyDTOs = new ArrayList<EipPolicyDTO>();

		EipPolicyDTO policyDTO = new EipPolicyDTO();
		policyDTO.setEipProtocol(38);
		policyDTO.setSourcePort(80);
		policyDTO.setTargetPort(80);
		eipPolicyDTOs.add(policyDTO);

		EipPolicyDTO policyDTO2 = new EipPolicyDTO();
		policyDTO2.setEipProtocol(38);
		policyDTO2.setSourcePort(443);
		policyDTO2.setTargetPort(443);
		eipPolicyDTOs.add(policyDTO2);

		service.allocateEIP(eipDTO, eipPolicyDTOs);
	}

	@Test
	public void recoverEIP() {
		service.recoverEIP(1916);
	}

	@Test
	public void associateEIP() {
		service.associateEIP(1940, 1612);
	}

	@Test
	public void dissociateEIP() {
		service.dissociateEIP(1940, 1612);
	}

	@Test
	public void aa() {
		// policyParameter.setSourcePort(NetworkUtil.getPortFromProtocol(protocols[i]));
		service.dissociateEIP(1940, 1612);
	}
}
