package com.sobey.api.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.SwitchTestData;
import com.sobey.api.service.SwitchService;
import com.sobey.generate.switches.WSResult;

/**
 * 针对Switch的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SwitchTest extends TestCase {

	@Autowired
	private SwitchService service;

	@Test
	public void createVlanTest() {

		WSResult result = service.createVlanInCore(SwitchTestData.randomVlanParameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void deleteVlanTest() {

		WSResult result = service.deleteVlanInCore(80);

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void createEsgTest() {

		WSResult result = service.createEsg(SwitchTestData.randomESGParameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void deleteEsgTest() {

		WSResult result = service.deleteEsg(3000);

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

}
