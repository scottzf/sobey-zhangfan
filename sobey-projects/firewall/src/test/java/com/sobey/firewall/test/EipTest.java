package com.sobey.firewall.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.PbulicProperties;
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
public class EipTest implements PbulicProperties {

	@Autowired
	private FirewallService service;

	@Test
	public void createEip() throws IOException {

		EIPParameter parameter = TestData.randomEIPParameter();

		String command = service.createEip(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.err.println(result);

	}

	@Test
	public void deleteEip() throws IOException {

		EIPParameter parameter = TestData.randomEIPParameter();

		List<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.219-tcp-80");
		allPolicies.add("119.6.200.219-udp-8080");

		parameter.getAllPolicies().addAll(allPolicies);

		String command = service.deleteEip(parameter);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.err.println(result);

	}
}
