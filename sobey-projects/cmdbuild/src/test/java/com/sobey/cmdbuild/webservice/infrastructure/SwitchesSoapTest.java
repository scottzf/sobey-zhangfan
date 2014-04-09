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
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
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
public class SwitchesSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateSwitches();
		testFindSwitches();
		testGetSwitchesList();
		testGetSwitchesPagination();
		testUpdateSwitches();
		testDeleteSwitches();

	}

	// @Test
	// @Ignore
	public void testFindSwitches() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<SwitchesDTO> responseParams = cmdbuildSoapService.findSwitchesByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<SwitchesDTO> response = cmdbuildSoapService.findSwitches(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetSwitchesList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<SwitchesDTO> result = cmdbuildSoapService.getSwitchesList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateSwitches() {

		Switches switches = TestData.randomSwitches();

		SwitchesDTO switchesDTO = BeanMapper.map(switches, SwitchesDTO.class);

		IdResult response = cmdbuildSoapService.createSwitches(switchesDTO);

		assertNotNull(response.getId());

		code = switches.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateSwitches() {

		DTOResult<SwitchesDTO> response = cmdbuildSoapService.findSwitches(id);

		SwitchesDTO switchesDTO = response.getDto();

		switchesDTO.setCode(RandomData.randomName("code"));

		switchesDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateSwitches(id, switchesDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteSwitches() {

		IdResult response = cmdbuildSoapService.deleteSwitches(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetSwitchesPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<SwitchesDTO> result = cmdbuildSoapService.getSwitchesPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}