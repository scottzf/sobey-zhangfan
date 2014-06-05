package com.sobey.firewall.data;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

public class TestData {

	public static EIPParameter randomEIPParameter() {

		ArrayList<String> allPolicies = Lists.newArrayList();

		EIPParameter parameter = new EIPParameter();

		parameter.setAllPolicies(allPolicies);
		parameter.setInternetIP("119.6.200.219");
		parameter.setIsp(1);
		parameter.setPrivateIP("172.28.25.105");

		ArrayList<EIPPolicyParameter> policies = Lists.newArrayList();

		EIPPolicyParameter policyParameter = new EIPPolicyParameter();
		policyParameter.setProtocolText("udp");
		policyParameter.setSourcePort(8080);
		policyParameter.setTargetPort(8080);

		EIPPolicyParameter policyParameter2 = new EIPPolicyParameter();
		policyParameter2.setProtocolText("tcp");
		policyParameter2.setSourcePort(80);
		policyParameter2.setTargetPort(80);

		policies.add(policyParameter);
		// policies.add(policyParameter2);

		parameter.setPolicies(policies);

		return parameter;
	}

	public static VPNUserParameter randomVPNParameter() {

		VPNUserParameter parameter = new VPNUserParameter();

		parameter.setFirewallPolicyId(2000);
		parameter.setNetMask("255.255.255.0");
		parameter.setVlanId(80);
		parameter.setVpnUser("liukai01");
		parameter.setVpnPassword("123456");

		ArrayList<String> segments = Lists.newArrayList();
		String segment = "10.10.2.1";
		segments.add(segment);

		ArrayList<String> ipaddress = Lists.newArrayList();
		String ip = "10.10.2.25";
		ipaddress.add(ip);

		parameter.setSegments(segments);
		parameter.setIpaddress(ipaddress);
		return parameter;
	}

}
