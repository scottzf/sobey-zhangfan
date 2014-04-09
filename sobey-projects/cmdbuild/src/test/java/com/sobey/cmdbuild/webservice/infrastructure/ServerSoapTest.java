package com.sobey.cmdbuild.webservice.infrastructure;

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
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
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
public class ServerSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateServer();
		testFindServer();
		testGetServerList();
		testGetServerPagination();
		testUpdateServer();
		testDeleteServer();

	}

	// @Test
	// @Ignore
	public void testFindServer() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<ServerDTO> responseParams = cmdbuildSoapService.findServerByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<ServerDTO> response = cmdbuildSoapService.findServer(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetServerList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<ServerDTO> result = cmdbuildSoapService.getServerList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateServer() {

		Server server = TestData.randomServer();

		ServerDTO serverDTO = BeanMapper.map(server, ServerDTO.class);

		IdResult response = cmdbuildSoapService.createServer(serverDTO);

		assertNotNull(response.getId());

		code = server.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateServer() {

		DTOResult<ServerDTO> response = cmdbuildSoapService.findServer(id);

		ServerDTO serverDTO = response.getDto();

		serverDTO.setCode(RandomData.randomName("code"));

		serverDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateServer(id, serverDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteServer() {

		IdResult response = cmdbuildSoapService.deleteServer(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetServerPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<ServerDTO> result = cmdbuildSoapService.getServerPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}