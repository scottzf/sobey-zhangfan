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
import com.sobey.cmdbuild.entity.As2;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class As2SoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateAs2();
		testFindAs2();
		testGetAs2List();
		testGetAs2Pagination();
		testUpdateAs2();
		testDeleteAs2();

	}

	// @Test
	// @Ignore
	public void testFindAs2() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<As2DTO> responseParams = cmdbuildSoapService.findAs2ByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<As2DTO> response = cmdbuildSoapService.findAs2(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetAs2List() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<As2DTO> result = cmdbuildSoapService.getAs2List(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateAs2() {

		As2 as2 = TestData.randomAs2();

		As2DTO as2DTO = BeanMapper.map(as2, As2DTO.class);

		IdResult response = cmdbuildSoapService.createAs2(as2DTO);

		assertNotNull(response.getId());

		code = as2.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateAs2() {

		DTOResult<As2DTO> response = cmdbuildSoapService.findAs2(id);

		As2DTO as2DTO = response.getDto();

		as2DTO.setCode(RandomData.randomName("code"));

		as2DTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateAs2(id, as2DTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteAs2() {

		IdResult response = cmdbuildSoapService.deleteAs2(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetAs2Pagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<As2DTO> result = cmdbuildSoapService.getAs2Pagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}