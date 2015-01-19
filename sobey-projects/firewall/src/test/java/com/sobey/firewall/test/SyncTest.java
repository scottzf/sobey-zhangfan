package com.sobey.firewall.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.SSHUtil;
import com.sobey.firewall.PbulicProperties;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.service.FirewallService;
import com.sobey.firewall.webservice.response.dto.AuthenticateFirewallParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameters;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameter;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameter;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameter;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameters;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest implements PbulicProperties {

	private static final String ENTER_SIGN = "\r";

	@Test
	public void aaa() throws IOException {
		// StringBuilder sb = new StringBuilder();
		// sb.append("config system interface").append(ENTER_SIGN);
		// sb.append("edit ").append("port10").append(ENTER_SIGN);
		// sb.append("set ip ").append("").append(" ").append("255.255.255.0")
		// .append(ENTER_SIGN);
		// sb.append("end").append(ENTER_SIGN);
		// sb.append("exe backup config flash").append(ENTER_SIGN);
		// System.out.println(sb.toString());

		ConfigSystemInterfaceParameter configSystemInterfaceParameter = new ConfigSystemInterfaceParameter();

		configSystemInterfaceParameter.setGateway("10.2.253.60");
		configSystemInterfaceParameter.setInterfaceName("port10");
		configSystemInterfaceParameter.setSubnetMask("255.255.255.0");
		configSystemInterfaceParameter.setUserName("admin");
		configSystemInterfaceParameter.setPassword("mcloud@sobey.com");
		configSystemInterfaceParameter.setUrl("10.2.253.253");

		String command = service.modifyConfigSystemInterfaceScrip(configSystemInterfaceParameter);

		SSHUtil.executeCommand(configSystemInterfaceParameter.getUrl(), configSystemInterfaceParameter.getUserName(),
				configSystemInterfaceParameter.getPassword(), command);

	}

	/**
	 * 
	 <pre>
	 * config system interface
	 * edit "port9"
	 * set ip 173.20.10.254 255.255.255.0
	 * set allowaccess ping https ssh telnet http
	 * set type physical
	 * set snmp-index 8
	 * </pre>
	 */
	@Test
	public void configSystemInterface() {

		// 创建子网 网关
		// 创建公网IP 单个IP vRouter创建公网IP

		// 配置接口IP地址脚本

		StringBuilder sb = new StringBuilder();

		ConfigSystemInterfaceParameter parameter = TestData.randomConfigSystemInterfaceParameter();

		sb.append("config system interface").append(ENTER_SIGN);
		sb.append("edit ").append("\"").append("port8").append("\"").append(ENTER_SIGN);
		sb.append("set ip ").append("125.71.203.23").append(" ").append("255.255.255.0").append(ENTER_SIGN);
		sb.append("set allowaccess ping https ssh telnet http").append(ENTER_SIGN);
		sb.append("set type physical").append(ENTER_SIGN);
		// sb.append("set snmp-index 8").append(ENTER_SIGN);

		System.out.println(sb.toString());
	}

	/**
	 * <pre>
	 * config router static
	 * edit 199
	 * set device port1
	 * set gateway 221.237.156.1
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@Test
	public void configRouterStatic() throws IOException {

		// 配置默认路由脚本(公网IP相关)
		// 创建公网IP 单个IP vRouter创建公网IP

		ConfigRouterStaticParameter parameter = TestData.randomConfigRouterStaticParameter();

		StringBuilder sb = new StringBuilder();

		sb.append("config router static").append(ENTER_SIGN);
		sb.append("edit ").append(0).append(ENTER_SIGN);
		sb.append("set device ").append("port8").append(ENTER_SIGN);
		sb.append("set gateway ").append("125.71.203.1").append(ENTER_SIGN);

		System.out.println(sb.toString());
//		SSHUtil.executeCommand("10.2.253.60", "admin", "mcloud@sobey.com", sb.toString());

	}

	/**
	 * <pre>
	 * config firewall address
	 * edit "173.20.10.0/24"
	 * set subnet 173.20.10.254 255.255.255.0
	 * next
	 * </pre>
	 */
	@Test
	public void configFirewallAddress() {

		// 创建地址段
		// 配置两个子网通讯

		ConfigFirewallAddressParameter parameter = TestData.randomConfigFirewallAddressParameter();

		StringBuilder sb = new StringBuilder();
		sb.append("config firewall address").append(ENTER_SIGN);
		sb.append("edit ").append("\"").append(parameter.getSegment()).append("\"").append(ENTER_SIGN);
		sb.append("set subnet ").append(parameter.getSegment()).append(" ").append(parameter.getSubnetMask())
				.append(ENTER_SIGN);
		sb.append("next").append(ENTER_SIGN);
		System.out.println(sb.toString());
	}

	@Autowired
	private FirewallService service;

	/**
	 * <pre>
	 * config firewall policy
	 * edit 58
	 * set srcintf "port5"
	 * set srcaddr "172.20.20.0/24"
	 * set dstintf "port6"
	 * set dstaddr "172.20.30./24"
	 * set schedule "always"
	 * set service "ALL"
	 * next
	 * </pre>
	 */
	@Test
	public void configFirewallPolicy() {
		// 子网通信

		ConfigFirewallPolicyParameter parameter = TestData.randomConfigFirewallPolicyParameter();

		StringBuilder sb = new StringBuilder();
		sb.append("config firewall policy").append(ENTER_SIGN);
		sb.append("edit ").append(parameter.getPolicyId()).append(ENTER_SIGN);
		sb.append("set srcintf ").append("\"").append(parameter.getSrcintf()).append("\"").append(ENTER_SIGN);
		sb.append("set srcaddr ").append("\"").append(parameter.getSrcaddr()).append("\"").append(ENTER_SIGN);
		sb.append("set dstintf ").append("\"").append(parameter.getDstintf()).append("\"").append(ENTER_SIGN);
		sb.append("set dstaddr ").append("\"").append(parameter.getDstaddr()).append("\"").append(ENTER_SIGN);
		sb.append("set schedule ").append("\"").append("always").append("\"").append(ENTER_SIGN);
		sb.append("set service ").append("\"").append("ALL").append("\"").append(ENTER_SIGN);

		if (StringUtils.equalsIgnoreCase("Internet", parameter.getPolicyType())) {
			sb.append("set nat enable").append(ENTER_SIGN);
		}
		sb.append("next").append(ENTER_SIGN);

		System.out.println(sb.toString());
	}

	@Test
	public void configFirewallAddressScrip() {

		ConfigFirewallAddressParameter configFirewallAddressParameter = new ConfigFirewallAddressParameter();
		configFirewallAddressParameter.setSegment("192.168.100.0/24");
		configFirewallAddressParameter.setSubnetMask("255.255.255.0");

		ConfigFirewallAddressParameter configFirewallAddressParameterB = new ConfigFirewallAddressParameter();
		configFirewallAddressParameterB.setSegment("192.168.200.0/24");
		configFirewallAddressParameterB.setSubnetMask("255.255.255.0");

		ArrayList<ConfigFirewallAddressParameter> configFirewallAddressParameters = new ArrayList<ConfigFirewallAddressParameter>();
		configFirewallAddressParameters.add(configFirewallAddressParameter);
		configFirewallAddressParameters.add(configFirewallAddressParameterB);

		ConfigFirewallAddressParameters firewallAddressParameters = new ConfigFirewallAddressParameters();
		firewallAddressParameters.setUserName("admin");
		firewallAddressParameters.setPassword("mcloud@sobey.com");
		firewallAddressParameters.setUrl("http://10.2.253.62");
		firewallAddressParameters.setConfigFirewallAddressParameters(configFirewallAddressParameters);

		String command = service.configFirewallAddressScrip(firewallAddressParameters);
		System.out.println(command);
	}

	@Test
	public void configSystemInterfaceScrip() {

		ConfigSystemInterfaceParameter configSystemInterfaceParameter = new ConfigSystemInterfaceParameter();
		configSystemInterfaceParameter.setGateway("192.168.100.0/24");
		configSystemInterfaceParameter.setInterfaceName("port1");
		configSystemInterfaceParameter.setSubnetMask("255.255.255.0");

		ConfigSystemInterfaceParameter configSystemInterfaceParameterB = new ConfigSystemInterfaceParameter();
		configSystemInterfaceParameterB.setGateway("192.168.200.0/24");
		configSystemInterfaceParameterB.setInterfaceName("port2");
		configSystemInterfaceParameterB.setSubnetMask("255.255.255.0");

		ArrayList<ConfigSystemInterfaceParameter> arrayList = new ArrayList<ConfigSystemInterfaceParameter>();
		arrayList.add(configSystemInterfaceParameter);
		arrayList.add(configSystemInterfaceParameterB);

		ConfigSystemInterfaceParameters configSystemInterfaceParameters = new ConfigSystemInterfaceParameters();
		configSystemInterfaceParameters.setUserName("admin");
		configSystemInterfaceParameters.setPassword("mcloud@sobey.com");
		configSystemInterfaceParameters.setUrl("http://10.2.253.62");
		configSystemInterfaceParameters.setConfigSystemInterfaceParameters(arrayList);

		String command = service.configSystemInterfaceScrip(configSystemInterfaceParameters);
		System.out.println(command);
	}

	@Test
	public void PurgeConfigFirewallPolicy() throws IOException {

		AuthenticateFirewallParameter authenticateFirewallParameter = new AuthenticateFirewallParameter();

		authenticateFirewallParameter.setUserName("admin");
		authenticateFirewallParameter.setPassword("mcloud@sobey.com");
		authenticateFirewallParameter.setUrl("10.2.253.60");

		String command = service.PurgeConfigFirewallPolicyScrip(authenticateFirewallParameter);
		System.out.println(command);

		SSHUtil.executeCommand(authenticateFirewallParameter.getUrl(), authenticateFirewallParameter.getUserName(),
				authenticateFirewallParameter.getPassword(), command);
	}

}
