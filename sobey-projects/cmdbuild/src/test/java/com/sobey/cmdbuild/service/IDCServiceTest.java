package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.service.infrastructure.CustomService;
import com.sobey.cmdbuild.service.organisation.IdcService;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagRelation;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

/**
 * IdcService 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class IDCServiceTest {

	@Autowired
	public IdcService service;

	@Autowired
	public CustomService customService;

	@Test
	public void getTagRelationBySeviceId() {

		List<TagRelation> list = customService.getTagRelation(2542);

		for (TagRelation tagRelation : list) {
			System.out.println(tagRelation);
		}

	}

	@Test
	public void selectMaxAclNumber() {
		System.out.println(customService.selectMaxAclNumber());
	}

	@Test
	public void selectMaxPolicyId() {
		System.out.println(customService.selectMaxPolicyId());
	}

	public Idc find() {
		Map<String, Object> searchParamsMap = Maps.newHashMap();
		searchParamsMap.put("EQ_code", "code5");
		return service.findIdc(searchParamsMap);
	}

	@Test
	public void list() {
		Map<String, Object> searchParamsMap = Maps.newHashMap();
		searchParamsMap.put("EQ_code", "code5");
		List<Idc> list = service.getIdcList(searchParamsMap);
		System.out.println("数量:" + list.size());
		assertNotNull(list.size());
	}

	@Test
	public void pagination() {

		Map<String, Object> searchParamsMap = Maps.newHashMap();

		PaginationResult<IdcDTO> result = service.getIdcDTOPagination(searchParamsMap, 1, 10);
		System.out.println(result.getGetTotalElements());
		assertNotNull(result.getGetTotalElements());

	}

	@Test
	public void save() {
		Idc idc = service.saveOrUpdate(TestData.randomIdc());
		System.out.println(idc.getId());
		System.out.println(idc.getCode());
		assertNotNull(idc.getId());
	}

	@Test
	public void update() {
		Idc idc = this.find();
		idc.setDescription("西安");
		System.out.println(service.saveOrUpdate(idc).getDescription());
		assertEquals("西安", service.saveOrUpdate(idc).getDescription());
	}
}
