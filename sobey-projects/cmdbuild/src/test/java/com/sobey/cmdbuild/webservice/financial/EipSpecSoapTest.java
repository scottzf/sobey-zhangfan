package com.sobey.cmdbuild.webservice.financial;

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
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
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
public class EipSpecSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	// @Test
	public void testAll() {
		testCreateEipSpec();
		testFindEipSpec();
		testGetEipSpecList();
		testGetEipSpecPagination();
		testUpdateEipSpec();
		// testDeleteEipSpec();

	}

	// @Test
	// @Ignore
	public void testFindEipSpec() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<EipSpecDTO> responseParams = cmdbuildSoapService.findEipSpecByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EipSpecDTO> response = cmdbuildSoapService.findEipSpec(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEipSpecList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<EipSpecDTO> result = cmdbuildSoapService.getEipSpecList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	@Test
	// @Ignore
	public void testCreateEipSpec() {

		EipSpec eipSpec = TestData.randomEipSpec();

		EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);

		IdResult response = cmdbuildSoapService.createEipSpec(eipSpecDTO);

		assertNotNull(response.getId());

		code = eipSpec.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEipSpec() {

		DTOResult<EipSpecDTO> response = cmdbuildSoapService.findEipSpec(id);

		EipSpecDTO eipSpecDTO = response.getDto();

		eipSpecDTO.setCode(RandomData.randomName("code"));

		eipSpecDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateEipSpec(id, eipSpecDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEipSpec() {

		IdResult response = cmdbuildSoapService.deleteEipSpec(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEipSpecPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<EipSpecDTO> result = cmdbuildSoapService.getEipSpecPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}