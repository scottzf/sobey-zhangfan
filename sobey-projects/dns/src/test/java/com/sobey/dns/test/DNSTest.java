package com.sobey.dns.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.dns.data.TestData;
import com.sobey.dns.service.DnsService;
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
	private DnsService service;

	@Test
	public void createGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		System.out.println(service.createGSLB(parameter).getMessage());
	}

	@Test
	public void deleteGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		System.out.println(service.deleteGSLB(parameter).getMessage());
	}

}
