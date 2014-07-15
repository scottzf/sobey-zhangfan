package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.service.iaas.MapEcsEs3Service;

/**
 * MapEcsEs3 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MapEcsEs3Test {

	@Autowired
	public MapEcsEs3Service ecsEs3Service;

	@Test
	public void save() {

		MapEcsEs3 mapEcsEs3 = TestData.randomMapEcsEs3();

		MapEcsEs3 map = ecsEs3Service.saveOrUpdate(mapEcsEs3);

		assertNotNull(map.getId());
	}

	@Test
	public void delete() {

		Map<String, Object> searchParamsMap = Maps.newHashMap();
		searchParamsMap.put("EQ_idObj1", "639");
		searchParamsMap.put("EQ_idObj2", "654");

		MapEcsEs3 mapEcsEs3 = ecsEs3Service.findMapEcsEs3(searchParamsMap);

		mapEcsEs3.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

		ecsEs3Service.saveOrUpdate(mapEcsEs3);

	}

}
