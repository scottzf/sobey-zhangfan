package com.sobey.firewall.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.PropertiesAbstract;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.service.FirewallService;
import com.sobey.firewall.webservice.response.dto.EIPParameter;

/**
 * Eip单元测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EipTest extends PropertiesAbstract {

	@Autowired
	private FirewallService service;

	@Test
	@Ignore
	public void createEip() {

		EIPParameter parameter = TestData.randomEIPParameter();

		List<String> memberList = Lists.newArrayList();

		String command = service.createEip(parameter, memberList);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

	}

	@Test
	@Ignore
	public void deleteEip() {

		EIPParameter parameter = TestData.randomEIPParameter();

		List<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.219-tcp-80");
		allPolicies.add("119.6.200.219-udp-8080");

		String command = service.deleteEip(parameter, allPolicies);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

	}
}
