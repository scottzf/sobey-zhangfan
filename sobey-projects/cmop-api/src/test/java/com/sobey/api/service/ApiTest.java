package com.sobey.api.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.storage.CreateEs3Parameter;
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

	// @Test
	public void createtenants() {

		TenantsDTO tenantsDTO = new TenantsDTO();
		tenantsDTO.setDescription("liu@163.com");
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

		Integer tenantsId = 1418;

		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setDatacenter("xa");
		cloneVMParameter.setDescription("这个是公共功能模块测试");
		cloneVMParameter.setVMName("liukai");
		cloneVMParameter.setVMTemplateName("CnetOS6.5");

		service.createECS(tenantsId, cloneVMParameter);
	}

	@Test
	public void destroyECS() {
		service.destroyECS(1585);
	}

	@Test
	public void powerECS() {
		service.powerOpsECS(1612, "poweron");
	}

	@Test
	public void reconfigECS() {
		service.reconfigECS(1612, 1503);
	}

	@Test
	public void syncVM() {
		System.out.println(service.syncVM("xa"));
	}

	@Test
	public void createEs3() {
		Integer tenantsId = 1418;
		CreateEs3Parameter createEs3Parameter = new CreateEs3Parameter();
		createEs3Parameter.setVolumeName("Sobey");
		createEs3Parameter.setVolumeSize("20");
		service.createES3(tenantsId, createEs3Parameter);
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
		//policyParameter.setSourcePort(NetworkUtil.getPortFromProtocol(protocols[i]));
		service.dissociateEIP(1940, 1612);
	}
}
