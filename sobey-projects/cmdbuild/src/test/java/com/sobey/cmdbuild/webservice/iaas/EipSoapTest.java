package com.sobey.cmdbuild.webservice.iaas;

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
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class EipSoapTest extends BaseFunctionalTestCase {

	/**
	 * 全局id
	 */
	private Integer id = 0;

	/**
	 * 全局Description
	 */
	private String description = "";

	@Test
	public void testAll() {
		save();
		find();
		getList();
		getPagination();
		update();
		delete();
	}

	public void delete() {
		IdResult response = cmdbuildSoapService.deleteEip(id);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_description", description);
		searchParams.setParamsMap(paramsMap);

		DTOResult<EipDTO> dtoResult = cmdbuildSoapService.findEipByParams(searchParams);

		assertEquals(description, dtoResult.getDto().getDescription());

		id = dtoResult.getDto().getId();// 设置id
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		DTOListResult<EipDTO> result = cmdbuildSoapService.getEipList(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		PaginationResult<EipDTO> result = cmdbuildSoapService.getEipPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		Eip eip = TestData.randomEip();
		EipDTO dto = BeanMapper.map(eip, EipDTO.class);
		IdResult response = cmdbuildSoapService.createEip(dto);

		assertNotNull(response.getId());

		description = dto.getDescription();// 设置Description
	}

	public void update() {

		DTOResult<EipDTO> response = cmdbuildSoapService.findEip(id);
		EipDTO dto = response.getDto();
		dto.setDescription(dto.getDescription() + "Update");
		IdResult result = cmdbuildSoapService.updateEip(id, dto);
		assertEquals("0", result.getCode());
	}
}