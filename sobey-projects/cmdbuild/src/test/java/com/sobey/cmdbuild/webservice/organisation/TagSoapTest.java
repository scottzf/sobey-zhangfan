package com.sobey.cmdbuild.webservice.organisation;

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
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

/**
 * Tag SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class TagSoapTest extends BaseFunctionalTestCase {

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
		IdResult response = cmdbuildSoapService.deleteTag(id);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_description", description);
		searchParams.setParamsMap(paramsMap);

		DTOResult<TagDTO> dtoResult = cmdbuildSoapService.findTagByParams(searchParams);

		assertEquals(description, dtoResult.getDto().getDescription());

		id = dtoResult.getDto().getId();// 设置id
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		DTOListResult<TagDTO> result = cmdbuildSoapService.getTagList(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		PaginationResult<TagDTO> result = cmdbuildSoapService.getTagPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		Tag tag = TestData.randomTag();
		// Tag tag = TestData.randomSubTag(); 子标签
		TagDTO dto = BeanMapper.map(tag, TagDTO.class);
		IdResult response = cmdbuildSoapService.createTag(dto);

		assertNotNull(response.getId());

		description = dto.getDescription();
	}

	public void update() {

		DTOResult<TagDTO> response = cmdbuildSoapService.findTag(id);
		TagDTO dto = response.getDto();
		dto.setDescription(dto.getDescription() + "Update");
		IdResult result = cmdbuildSoapService.updateTag(id, dto);
		assertEquals("0", result.getCode());
	}
}
