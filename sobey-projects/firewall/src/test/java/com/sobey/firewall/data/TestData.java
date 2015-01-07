package com.sobey.firewall.data;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameter;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameter;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameter;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

public class TestData {

	public static EIPParameter randomEIPParameter() {

		ArrayList<String> allPolicies = Lists.newArrayList();

		allPolicies.add("119.6.200.204");

		EIPParameter parameter = new EIPParameter();
		parameter.setAllPolicies(allPolicies);
		parameter.setInternetIP("119.6.200.203");
		parameter.setIsp(1);
		parameter.setPrivateIP("10.10.2.51");

		ArrayList<EIPPolicyParameter> policies = Lists.newArrayList();

		EIPPolicyParameter policyParameter = new EIPPolicyParameter();
		policyParameter.setProtocolText("tcp");
		policyParameter.setSourcePort(10);
		policyParameter.setTargetPort(10);

		policies.add(policyParameter);

		parameter.setPolicies(policies);

		return parameter;
	}

	public static VPNUserParameter randomVPNParameter() {

		VPNUserParameter parameter = new VPNUserParameter();

		parameter.setPolicyId(2000);
		parameter.setNetMask("255.255.255.0");
		parameter.setVlanId(10);
		parameter.setVpnUser("liukai01");
		parameter.setVpnPassword("123456");

		ArrayList<String> segments = Lists.newArrayList();
		String segment = "10.10.2.1";
		segments.add(segment);

		ArrayList<String> ipaddress = Lists.newArrayList();
		String ip = "10.10.2.25";
		ipaddress.add(ip);

		parameter.setSegments(segments);
		parameter.setIpaddresses(ipaddress);
		return parameter;
	}

	public static ConfigSystemInterfaceParameter randomConfigSystemInterfaceParameter() {

		ConfigSystemInterfaceParameter parameter = new ConfigSystemInterfaceParameter();
		parameter.setGateway("173.20.10.254");
		parameter.setInterfaceName("port9");
		parameter.setSubnetMask("255.255.255.0");

		return parameter;
	}

	public static ConfigRouterStaticParameter randomConfigRouterStaticParameter() {

		ConfigRouterStaticParameter parameter = new ConfigRouterStaticParameter();
		parameter.setInterfaceName("port1");
		parameter.setRouterId(199);
		parameter.setIspGateway("221.237.156.1");

		return parameter;
	}

	public static ConfigFirewallAddressParameter randomConfigFirewallAddressParameter() {
		ConfigFirewallAddressParameter parameter = new ConfigFirewallAddressParameter();
		parameter.setGateway("173.20.10.254");
		parameter.setSegment("173.20.10.0/24");
		parameter.setSubnetMask("255.255.255.0");
		return parameter;
	}

	public static ConfigFirewallPolicyParameter randomConfigFirewallPolicyParameter() {

		ConfigFirewallPolicyParameter parameter = new ConfigFirewallPolicyParameter();
		parameter.setSrcintf("port5");
		parameter.setSrcaddr("172.20.20.0/24");
		parameter.setDstintf("port6");
		parameter.setDstaddr("172.20.30./24");
		parameter.setPolicyId(58);
		parameter.setPolicyType("Subnet");

		return parameter;
	}

}
