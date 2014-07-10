package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
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
public class FimasPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateFimasPort();
		testFindFimasPort();
		testGetFimasPortList();
		testGetFimasPortPagination();
		testUpdateFimasPort();
		testDeleteFimasPort();

	}

	// @Test
	// @Ignore
	public void testFindFimasPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<FimasPortDTO> responseParams = cmdbuildSoapService.findFimasPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<FimasPortDTO> response = cmdbuildSoapService.findFimasPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetFimasPortList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<FimasPortDTO> result = cmdbuildSoapService.getFimasPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateFimasPort() {

		FimasPort fimasPort = TestData.randomFimasPort();

		FimasPortDTO fimasPortDTO = BeanMapper.map(fimasPort, FimasPortDTO.class);

		IdResult response = cmdbuildSoapService.createFimasPort(fimasPortDTO);

		assertNotNull(response.getId());

		code = fimasPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateFimasPort() {

		DTOResult<FimasPortDTO> response = cmdbuildSoapService.findFimasPort(id);

		FimasPortDTO fimasPortDTO = response.getDto();

		fimasPortDTO.setCode(RandomData.randomName("code"));

		fimasPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateFimasPort(id, fimasPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteFimasPort() {

		IdResult response = cmdbuildSoapService.deleteFimasPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetFimasPortPagination() {

		SearchParams searchParams = new SearchParams();
		PaginationResult<FimasPortDTO> result = cmdbuildSoapService.getFimasPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}