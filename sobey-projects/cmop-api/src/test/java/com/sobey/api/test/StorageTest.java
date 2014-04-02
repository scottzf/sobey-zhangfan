package com.sobey.api.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.StorageTestData;
import com.sobey.api.service.StorageService;
import com.sobey.generate.storage.WSResult;

/**
 * 针对Storage的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StorageTest extends TestCase {

	@Autowired
	private StorageService service;

	@Test
	public void createEs3Test() {
		WSResult result = service.createEs3(StorageTestData.randomCreateEs3Parameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	// @Test
	public void deleteEs3Test() {
		WSResult result = service.deleteEs3(StorageTestData.randomDeleteEs3Parameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	// @Test
	public void mountEs3Test() {
		WSResult result = service.mountEs3(StorageTestData.randomMountEs3Parameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	// @Test
	public void umountEs3Test() {
		WSResult result = service.umountEs3(StorageTestData.randomUmountEs3Parameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	// @Test
	public void remountEs3Test() {
		WSResult result = service.remountEs3(StorageTestData.randomRemountEs3Parameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

}
