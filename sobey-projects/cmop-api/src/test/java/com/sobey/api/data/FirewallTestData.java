package com.sobey.api.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.generate.firewall.EIPParameter;
import com.sobey.generate.firewall.EIPPolicyParameter;
import com.sobey.generate.firewall.VPNUserParameter;

public class FirewallTestData {

	public static EIPParameter randomEIPParameter() {

		ArrayList<String> allPolicies = Lists.newArrayList();
		allPolicies.add("192.168.1.1");

		EIPParameter parameter = new EIPParameter();

		parameter.getAllPolicies().addAll(allPolicies);
		parameter.setInternetIP("119.6.200.219");
		parameter.setIsp(1);
		parameter.setPrivateIP("172.28.25.105");

		List<EIPPolicyParameter> policies = Lists.newArrayList();

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

		parameter.getPolicies().addAll(policies);

		return parameter;
	}

	public static VPNUserParameter randomVPNParameter() {

		VPNUserParameter parameter = new VPNUserParameter();

		parameter.setFirewallPolicyId(2000);
		parameter.setNetMask("255.255.255.0");
		parameter.setVlanId(80);
		parameter.setVpnUser("liukai01");
		parameter.setVpnPassword("liukai01@sobey");

		ArrayList<String> segments = Lists.newArrayList();
		String segment = "172.20.17.0";
		String segment2 = "172.20.18.0";
		segments.add(segment);
		segments.add(segment2);

		ArrayList<String> ipaddress = Lists.newArrayList();
		String ip = "172.20.19.1";
		ipaddress.add(ip);

		parameter.getSegments().addAll(segments);
		parameter.getIpaddress().addAll(ipaddress);
		return parameter;
	}

}
