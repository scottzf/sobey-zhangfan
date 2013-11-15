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
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class ElbSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateElb();
		testFindElb();
		testGetElbList();
		testGetElbPagination();
		testUpdateElb();
		testDeleteElb();

	}

	// @Test
	// @Ignore
	public void testFindElb() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<ElbDTO> responseParams = iaasSoapService.findElbByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<ElbDTO> response = iaasSoapService.findElb(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetElbList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<ElbDTO> result = iaasSoapService.getElbList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateElb() {

		Elb elb = TestData.randomElb();

		ElbDTO elbDTO = BeanMapper.map(elb, ElbDTO.class);

		IdResult response = iaasSoapService.createElb(elbDTO);

		assertNotNull(response.getId());

		code = elb.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateElb() {

		DTOResult<ElbDTO> response = iaasSoapService.findElb(id);

		ElbDTO elbDTO = response.getDto();

		elbDTO.setCode(RandomData.randomName("code"));

		elbDTO.setDescription(RandomData.randomName("update"));

		IdResult result = iaasSoapService.updateElb(id, elbDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteElb() {

		IdResult response = iaasSoapService.deleteElb(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetElbPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<ElbDTO> result = iaasSoapService.getElbPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}