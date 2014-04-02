package com.sobey.api.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.sobey.api.data.FirewallTestData;
import com.sobey.api.service.FirewallService;
import com.sobey.generate.firewall.WSResult;

/**
 * 针对Firewall的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FirewallTest extends TestCase {

	@Autowired
	private FirewallService service;

	@Test
	public void createEipTest() {

		List<String> memberList = Lists.newArrayList();

		WSResult result = service.createEip(FirewallTestData.randomEIPParameter(), memberList);

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void deleteEipTest() {

		List<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.219-tcp-80");
		allPolicies.add("119.6.200.219-udp-8080");

		WSResult result = service.deleteEip(FirewallTestData.randomEIPParameter(), allPolicies);

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void createVPNTest() {

		WSResult result = service.createVPNUser(FirewallTestData.randomVPNParameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void deleteVPNTest() {

		WSResult result = service.deleteVPNUser(FirewallTestData.randomVPNParameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

	@Test
	public void changeVPNUserAccesssAddressTest() {

		WSResult result = service.changeVPNUserAccesssAddress(FirewallTestData.randomVPNParameter());

		System.out.println(result.getMessage());
		assertEquals(result.getCode(), "0");
	}

}
