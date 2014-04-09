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
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class EcsSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEcs();
		testFindEcs();
		testGetEcsList();
		testGetEcsPagination();

	}

	// @Test
	// @Ignore
	public void testFindEcs() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<EcsDTO> responseParams = cmdbuildSoapService.findEcsByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EcsDTO> response = cmdbuildSoapService.findEcs(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEcsList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<EcsDTO> result = cmdbuildSoapService.getEcsList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEcs() {

		Ecs ecs = TestData.randomEcs();

		EcsDTO ecsDTO = BeanMapper.map(ecs, EcsDTO.class);

		IdResult response = cmdbuildSoapService.createEcs(ecsDTO);

		assertNotNull(response.getId());

		code = ecs.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testGetEcsPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<EcsDTO> result = cmdbuildSoapService.getEcsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}