package com.sobey.loadbalancer.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.loadbalancer.data.TestData;
import com.sobey.loadbalancer.service.LoadbalanceService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;

/**
 * Elb单元测试.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ElbTest extends TestCase {

	@Autowired
	private LoadbalanceService service;

	@Test
	public void createElb() {
		ELBParameter parameter = TestData.randomELBParameter();
		System.out.println(service.createElb(parameter).getMessage());
	}

	@Test
	public void deleteElb() {
		ELBParameter parameter = TestData.randomELBParameter();
		System.out.println(service.deleteElb(parameter).getMessage());
	}

}
