package com.sobey.api.service;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.CloneVMParameter;
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
		cloneVMParameter.setVMName("MDC");
		cloneVMParameter.setVMTemplateName("CnetOS6.5");

		service.createECS(tenantsId, cloneVMParameter);
	}
}
