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
		IdResult response = cmdbuildSoapService.deleteEcs(id);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_description", description);
		searchParams.setParamsMap(paramsMap);

		DTOResult<EcsDTO> dtoResult = cmdbuildSoapService.findEcsByParams(searchParams);

		assertEquals(description, dtoResult.getDto().getDescription());

		id = dtoResult.getDto().getId();// 设置id
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		DTOListResult<EcsDTO> result = cmdbuildSoapService.getEcsList(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		PaginationResult<EcsDTO> result = cmdbuildSoapService.getEcsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		Ecs ecs = TestData.randomEcs();
		EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);
		IdResult response = cmdbuildSoapService.createEcs(dto);

		assertNotNull(response.getId());

		description = dto.getDescription();// 设置Description
	}

	public void update() {

		DTOResult<EcsDTO> response = cmdbuildSoapService.findEcs(id);
		EcsDTO dto = response.getDto();
		dto.setDescription(dto.getDescription() + "Update");
		IdResult result = cmdbuildSoapService.updateEcs(id, dto);
		assertEquals("0", result.getCode());
	}
}