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
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
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
public class FimasBoxSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateFimasBox();
		testFindFimasBox();
		testGetFimasBoxList();
		testGetFimasBoxPagination();
		testUpdateFimasBox();
		// testDeleteFimasBox();

	}

	// @Test
	// @Ignore
	public void testFindFimasBox() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<FimasBoxDTO> responseParams = cmdbuildSoapService.findFimasBoxByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<FimasBoxDTO> response = cmdbuildSoapService.findFimasBox(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetFimasBoxList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<FimasBoxDTO> result = cmdbuildSoapService.getFimasBoxList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	@Test
	// @Ignore
	public void testCreateFimasBox() {

		FimasBox fimasBox = TestData.randomFimasBox();

		FimasBoxDTO fimasBoxDTO = BeanMapper.map(fimasBox, FimasBoxDTO.class);

		IdResult response = cmdbuildSoapService.createFimasBox(fimasBoxDTO);

		assertNotNull(response.getId());

		code = fimasBox.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateFimasBox() {

		DTOResult<FimasBoxDTO> response = cmdbuildSoapService.findFimasBox(id);

		FimasBoxDTO fimasBoxDTO = response.getDto();

		fimasBoxDTO.setCode(RandomData.randomName("code"));

		fimasBoxDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateFimasBox(id, fimasBoxDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteFimasBox() {

		IdResult response = cmdbuildSoapService.deleteFimasBox(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetFimasBoxPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<FimasBoxDTO> result = cmdbuildSoapService.getFimasBoxPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}