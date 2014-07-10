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
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
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
public class NetappControllerSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNetappController();
		testFindNetappController();
		testGetNetappControllerList();
		testGetNetappControllerPagination();
		testUpdateNetappController();
		testDeleteNetappController();

	}

	// @Test
	// @Ignore
	public void testFindNetappController() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<NetappControllerDTO> responseParams = cmdbuildSoapService.findNetappControllerByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NetappControllerDTO> response = cmdbuildSoapService.findNetappController(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNetappControllerList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOListResult<NetappControllerDTO> result = cmdbuildSoapService.getNetappControllerList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNetappController() {

		Storage netappController = TestData.randomNetappController();

		NetappControllerDTO netappControllerDTO = BeanMapper.map(netappController, NetappControllerDTO.class);

		IdResult response = cmdbuildSoapService.createNetappController(netappControllerDTO);

		assertNotNull(response.getId());

		code = netappController.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNetappController() {

		DTOResult<NetappControllerDTO> response = cmdbuildSoapService.findNetappController(id);

		NetappControllerDTO netappControllerDTO = response.getDto();

		netappControllerDTO.setCode(RandomData.randomName("code"));

		netappControllerDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateNetappController(id, netappControllerDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNetappController() {

		IdResult response = cmdbuildSoapService.deleteNetappController(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNetappControllerPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<NetappControllerDTO> result = cmdbuildSoapService.getNetappControllerPagination(searchParams,
				1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}