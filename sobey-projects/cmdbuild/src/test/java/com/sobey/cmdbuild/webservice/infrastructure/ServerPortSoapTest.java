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
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
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
public class ServerPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateServerPort();
		testFindServerPort();
		testGetServerPortList();
		testGetServerPortPagination();
		testUpdateServerPort();
		testDeleteServerPort();

	}

	// @Test
	// @Ignore
	public void testFindServerPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<ServerPortDTO> responseParams = cmdbuildSoapService.findServerPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<ServerPortDTO> response = cmdbuildSoapService.findServerPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetServerPortList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<ServerPortDTO> result = cmdbuildSoapService.getServerPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateServerPort() {

		ServerPort serverPort = TestData.randomServerPort();

		ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);

		IdResult response = cmdbuildSoapService.createServerPort(serverPortDTO);

		assertNotNull(response.getId());

		code = serverPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateServerPort() {

		DTOResult<ServerPortDTO> response = cmdbuildSoapService.findServerPort(id);

		ServerPortDTO serverPortDTO = response.getDto();

		serverPortDTO.setCode(RandomData.randomName("code"));

		serverPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateServerPort(id, serverPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteServerPort() {

		IdResult response = cmdbuildSoapService.deleteServerPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetServerPortPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<ServerPortDTO> result = cmdbuildSoapService.getServerPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}