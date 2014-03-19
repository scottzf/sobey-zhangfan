package com.sobey.firewall.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.PropertiesAbstract;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.service.FirewallService;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VPNUserTest extends PropertiesAbstract {

	@Autowired
	private FirewallService service;

	@Test
	@Ignore
	public void createVPNUser() {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.createVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);
	}

	@Test
	@Ignore
	public void deleteVPNUser() {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.deleteVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

	}

	@Test
	@Ignore
	public void ChangeAccesssAddressIntoVPNUser() {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = service.changeAccesssAddressIntoVPNUser(parameter);

		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);
	}

}
