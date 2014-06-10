package com.sobey.api.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.DNSTestData;
import com.sobey.api.service.DnsService;

/**
 * 针对Dns的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DnsTest extends TestCase {

	@Autowired
	private DnsService service;

	@Test
	public void createDNSTest() {
		assertEquals(service.createDNS(DNSTestData.randomDNSParameter()).getCode(), "0");
	}

	@Test
	public void deleteDNSTest() {
		assertEquals(service.deleteDNS(DNSTestData.randomDNSParameter()).getCode(), "0");
	}

}
