package com.sobey.dns.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DNSPolicyParameter;
import com.sobey.dns.webservice.response.dto.DNSPublicIPParameter;

public class TestData {

	public static DNSParameter randomDNSParameter() {

		List<DNSPublicIPParameter> publicIPs = Lists.newArrayList();
		List<DNSPolicyParameter> policyParameters = Lists.newArrayList();
		List<DNSPolicyParameter> policyParameters2 = Lists.newArrayList();

		DNSPolicyParameter policyParameter = new DNSPolicyParameter();
		policyParameter.setProtocolText("tcp");
		policyParameter.setSourcePort(8080);
		policyParameter.setTargetPort(8080);
		policyParameters.add(policyParameter);

		DNSPolicyParameter policyParameter2 = new DNSPolicyParameter();
		policyParameter2.setProtocolText("tcp");
		policyParameter2.setSourcePort(80);
		policyParameter2.setTargetPort(80);

		policyParameters.add(policyParameter2);

		DNSPublicIPParameter ipParameter = new DNSPublicIPParameter();

		ipParameter.setIpaddress("113.142.30.109");
		ipParameter.setPolicyParameters(policyParameters);

		publicIPs.add(ipParameter);

		DNSPolicyParameter policyParameter3 = new DNSPolicyParameter();
		policyParameter3.setProtocolText("tcp");
		policyParameter3.setSourcePort(8080);
		policyParameter3.setTargetPort(8080);
		policyParameters2.add(policyParameter3);

		DNSPolicyParameter policyParameter4 = new DNSPolicyParameter();
		policyParameter4.setProtocolText("tcp");
		policyParameter4.setSourcePort(80);
		policyParameter4.setTargetPort(80);

		policyParameters2.add(policyParameter4);

		DNSPublicIPParameter ipParameter2 = new DNSPublicIPParameter();

		ipParameter2.setIpaddress("113.200.74.140");
		ipParameter2.setPolicyParameters(policyParameters2);

		publicIPs.add(ipParameter2);

		DNSParameter parameter = new DNSParameter();
		parameter.setDomianName("mdnftp.sobeycache.com");
		parameter.setDomianType("gslb");
		parameter.setPublicIPs(publicIPs);

		return parameter;
	}

}
