package com.sobey.dns.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.dns.data.TestData;
import com.sobey.dns.service.NitroService;
import com.sobey.dns.webservice.response.dto.DNSParameter;

/**
 * DNS单元测试.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DNSTest extends TestCase {

	@Autowired
	private NitroService service;

	@Test
	public void createGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		assertTrue(service.createGSLB(parameter));
	}

	@Test
	public void deleteGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		assertTrue(service.deleteGSLB(parameter));
	}

}
