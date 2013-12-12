package com.sobey.firewall.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNParameter;

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

	public static VPNParameter randomVPNParameter() {

		VPNParameter parameter = new VPNParameter();

		parameter.setFirewallPolicyId(2000);
		parameter.setIpaddress(null);
		parameter.setNetMask("255.255.255.0");
		parameter.setSegment("172.20.17.0");
		parameter.setVlanId(80);
		parameter.setVpnUser("liukai");
		parameter.setVpnPassword("liukai@sobey");

		return parameter;
	}

	/**
	 * 单IP测试数据
	 * 
	 * @return
	 */
	public static VPNParameter randomVPNParameterSingletonIP() {

		VPNParameter parameter = new VPNParameter();

		parameter.setFirewallPolicyId(2000);
		parameter.setIpaddress("172.20.17.5");
		parameter.setNetMask("255.255.255.0");
		parameter.setSegment("172.20.17.0");
		parameter.setVlanId(80);
		parameter.setVpnUser("liukai");
		parameter.setVpnPassword("liukai@sobey");

		return parameter;
	}

}
