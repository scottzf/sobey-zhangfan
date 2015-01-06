package com.sobey.firewall.data;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.RouterParameter;
import com.sobey.firewall.webservice.response.dto.SubnetParameter;
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

	public static RouterParameter randomRouterParameter() {

		ArrayList<SubnetParameter> subnetParameters = new ArrayList<SubnetParameter>();

		SubnetParameter subnetParameterA = new SubnetParameter();
		subnetParameterA.setGateway("173.20.10.254");
		subnetParameterA.setSegment("173.20.10.0/24");
		subnetParameterA.setSubnetMask("255.255.255.0");
		subnetParameterA.setPolicyId(10);
		subnetParameterA.setPortName("port10");

		SubnetParameter subnetParameterB = new SubnetParameter();
		subnetParameterB.setGateway("173.20.11.254");
		subnetParameterB.setSegment("173.20.11.0/24");
		subnetParameterB.setSubnetMask("255.255.255.0");
		subnetParameterB.setPolicyId(11);
		subnetParameterB.setPortName("port11");

		SubnetParameter subnetParameterC = new SubnetParameter();
		subnetParameterC.setGateway("173.20.12.254");
		subnetParameterC.setSegment("173.20.12.0/24");
		subnetParameterC.setSubnetMask("255.255.255.0");
		subnetParameterC.setPolicyId(12);
		subnetParameterC.setPortName("port12");

		subnetParameters.add(subnetParameterA);
		subnetParameters.add(subnetParameterB);
		subnetParameters.add(subnetParameterC);

		RouterParameter parameter = new RouterParameter();

		parameter.setUrl("192.168.1.1");
		parameter.setUserName("admin");
		parameter.setPassword("admin");
		parameter.setSubnetParameters(subnetParameters);
		return parameter;
	}

	public static SubnetParameter randomSubnetParameter() {

		SubnetParameter parameter = new SubnetParameter();

		parameter.setGateway("173.20.10.254");
		parameter.setSegment("173.20.10.0/24");
		parameter.setSubnetMask("255.255.255.0");
		parameter.setPolicyId(10);
		parameter.setPortName("port10");

		return parameter;
	}

}
