package com.sobey.api.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.LoadbalancerTestData;
import com.sobey.api.service.LoadbalancerService;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LoadbalancerTest extends TestCase {

	@Autowired
	private LoadbalancerService service;

	@Test
	public void createELBTest() {
		assertEquals(service.createElb(LoadbalancerTestData.randomELBParameter()).getCode(), "0");
	}

	@Test
	public void deleteELBTest() {
		assertEquals(service.deleteElb(LoadbalancerTestData.randomELBParameter()).getCode(), "0");
	}

}
