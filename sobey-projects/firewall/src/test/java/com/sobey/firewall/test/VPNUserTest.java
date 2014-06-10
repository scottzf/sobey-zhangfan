package com.sobey.firewall.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.PbulicProperties;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.service.FirewallService;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VPNUserTest implements PbulicProperties {

	@Autowired
	private FirewallService service;

	@Test
	public void createVPNUser() throws IOException {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.createVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.err.println(result);
	}

	@Test
	public void deleteVPNUser() throws IOException {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.deleteVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.err.println(result);

	}

	@Test
	public void ChangeAccesssAddressIntoVPNUser() throws IOException {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.changeAccesssAddressIntoVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.err.println(result);
	}

}
