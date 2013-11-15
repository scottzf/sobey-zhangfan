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
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class EsgSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEsg();
		testFindEsg();
		testGetEsgList();
		testGetEsgPagination();
		testUpdateEsg();
		testDeleteEsg();

	}

	// @Test
	// @Ignore
	public void testFindEsg() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<EsgDTO> responseParams = iaasSoapService.findEsgByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EsgDTO> response = iaasSoapService.findEsg(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEsgList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<EsgDTO> result = iaasSoapService.getEsgList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEsg() {

		Esg esg = TestData.randomEsg();

		EsgDTO esgDTO = BeanMapper.map(esg, EsgDTO.class);

		IdResult response = iaasSoapService.createEsg(esgDTO);

		assertNotNull(response.getId());

		code = esg.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEsg() {

		DTOResult<EsgDTO> response = iaasSoapService.findEsg(id);

		EsgDTO esgDTO = response.getDto();

		esgDTO.setCode(RandomData.randomName("code"));

		esgDTO.setDescription(RandomData.randomName("update"));

		IdResult result = iaasSoapService.updateEsg(id, esgDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEsg() {

		IdResult response = iaasSoapService.deleteEsg(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEsgPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<EsgDTO> result = iaasSoapService.getEsgPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}