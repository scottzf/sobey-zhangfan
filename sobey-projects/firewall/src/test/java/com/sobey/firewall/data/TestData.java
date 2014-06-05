package com.sobey.firewall.data;

import java.util.ArrayList;

import com.google.common.collect.Lists;
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
		policyParameter.setSourcePort(80);
		policyParameter.setTargetPort(80);

		policies.add(policyParameter);

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
