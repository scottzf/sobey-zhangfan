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
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
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
public class NetappBoxSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNetappBox();
		testFindNetappBox();
		testGetNetappBoxList();
		testGetNetappBoxPagination();
		testUpdateNetappBox();
		// testDeleteNetappBox();

	}

	// @Test
	// @Ignore
	public void testFindNetappBox() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<NetappBoxDTO> responseParams = cmdbuildSoapService.findNetappBoxByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NetappBoxDTO> response = cmdbuildSoapService.findNetappBox(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNetappBoxList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<NetappBoxDTO> result = cmdbuildSoapService.getNetappBoxList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	@Test
	// @Ignore
	public void testCreateNetappBox() {

		NetappBox netappBox = TestData.randomNetappBox();

		NetappBoxDTO netappBoxDTO = BeanMapper.map(netappBox, NetappBoxDTO.class);

		IdResult response = cmdbuildSoapService.createNetappBox(netappBoxDTO);

		assertNotNull(response.getId());

		code = netappBox.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNetappBox() {

		DTOResult<NetappBoxDTO> response = cmdbuildSoapService.findNetappBox(id);

		NetappBoxDTO netappBoxDTO = response.getDto();

		netappBoxDTO.setCode(RandomData.randomName("code"));

		netappBoxDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateNetappBox(id, netappBoxDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNetappBox() {

		IdResult response = cmdbuildSoapService.deleteNetappBox(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNetappBoxPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<NetappBoxDTO> result = cmdbuildSoapService.getNetappBoxPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}