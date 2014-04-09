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
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
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
public class EipSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEip();
		testFindEip();
		testGetEipList();
		testGetEipPagination();
		testUpdateEip();
		testDeleteEip();

	}

	// @Test
	// @Ignore
	public void testFindEip() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<EipDTO> responseParams = cmdbuildSoapService.findEipByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EipDTO> response = cmdbuildSoapService.findEip(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEipList() {

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOListResult<EipDTO> result = cmdbuildSoapService.getEipList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEip() {

		Eip eip = TestData.randomEip();

		EipDTO eipDTO = BeanMapper.map(eip, EipDTO.class);

		IdResult response = cmdbuildSoapService.createEip(eipDTO);

		assertNotNull(response.getId());

		code = eip.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEip() {

		DTOResult<EipDTO> response = cmdbuildSoapService.findEip(id);

		EipDTO eipDTO = response.getDto();

		eipDTO.setCode(RandomData.randomName("code"));

		eipDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateEip(id, eipDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEip() {

		IdResult response = cmdbuildSoapService.deleteEip(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEipPagination() {

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		PaginationResult<EipDTO> result = cmdbuildSoapService.getEipPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}