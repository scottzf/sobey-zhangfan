package com.sobey.firewall.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

public class TestData {

	public static EIPParameter randomEIPParameter() {

		EIPParameter parameter = new EIPParameter();

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
		policies.add(policyParameter2);

		parameter.setPolicies(policies);

		return parameter;
	}

	public static VPNUserParameter randomVPNParameter() {

		VPNUserParameter parameter = new VPNUserParameter();

		parameter.setFirewallPolicyId(2000);
		parameter.setNetMask("255.255.255.0");
		parameter.setVlanId(80);
		parameter.setVpnUser("liukai01");
		parameter.setVpnPassword("liukai01@sobey");

		List<String> segments = Lists.newArrayList();
		String segment = "172.20.17.0";
		String segment2 = "172.20.18.0";
		segments.add(segment);
		segments.add(segment2);

		List<String> ipaddress = Lists.newArrayList();
		String ip = "172.20.19.1";
		ipaddress.add(ip);

		parameter.setSegments(segments);
		parameter.setIpaddress(ipaddress);
		return parameter;
	}

}
