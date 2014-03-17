package com.sobey.cmdbuild.webservice.iaas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.GroupPolicy;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class GroupPolicySoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateGroupPolicy();
		testFindGroupPolicy();
		testGetGroupPolicyList();
		testGetGroupPolicyPagination();
		testUpdateGroupPolicy();
		testDeleteGroupPolicy();

	}

	// @Test
	// @Ignore
	public void testFindGroupPolicy() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<GroupPolicyDTO> responseParams = cmdbuildSoapService.findGroupPolicyByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<GroupPolicyDTO> response = cmdbuildSoapService.findGroupPolicy(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetGroupPolicyList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<GroupPolicyDTO> result = cmdbuildSoapService.getGroupPolicyList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateGroupPolicy() {

		GroupPolicy groupPolicy = TestData.randomGroupPolicy();

		GroupPolicyDTO groupPolicyDTO = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);

		IdResult response = cmdbuildSoapService.createGroupPolicy(groupPolicyDTO);

		assertNotNull(response.getId());

		code = groupPolicy.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateGroupPolicy() {

		DTOResult<GroupPolicyDTO> response = cmdbuildSoapService.findGroupPolicy(id);

		GroupPolicyDTO groupPolicyDTO = response.getDto();

		groupPolicyDTO.setCode(RandomData.randomName("code"));

		groupPolicyDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateGroupPolicy(id, groupPolicyDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteGroupPolicy() {

		IdResult response = cmdbuildSoapService.deleteGroupPolicy(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetGroupPolicyPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<GroupPolicyDTO> result = cmdbuildSoapService.getGroupPolicyPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}