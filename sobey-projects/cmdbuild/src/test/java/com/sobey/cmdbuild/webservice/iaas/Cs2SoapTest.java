package com.sobey.cmdbuild.webservice.iaas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Cs2;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class Cs2SoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateCs2();
		testFindCs2();
		testGetCs2List();
		testGetCs2Pagination();
		testUpdateCs2();
		testDeleteCs2();

	}

	// @Test
	// @Ignore
	public void testFindCs2() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<Cs2DTO> responseParams = cmdbuildSoapService.findCs2ByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<Cs2DTO> response = cmdbuildSoapService.findCs2(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetCs2List() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<Cs2DTO> result = cmdbuildSoapService.getCs2List(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateCs2() {

		Cs2 cs2 = TestData.randomCs2();

		Cs2DTO cs2DTO = BeanMapper.map(cs2, Cs2DTO.class);

		IdResult response = cmdbuildSoapService.createCs2(cs2DTO);

		assertNotNull(response.getId());

		code = cs2.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateCs2() {

		DTOResult<Cs2DTO> response = cmdbuildSoapService.findCs2(id);

		Cs2DTO cs2DTO = response.getDto();

		cs2DTO.setCode(RandomData.randomName("code"));

		cs2DTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateCs2(id, cs2DTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteCs2() {

		IdResult response = cmdbuildSoapService.deleteCs2(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetCs2Pagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<Cs2DTO> result = cmdbuildSoapService.getCs2Pagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}