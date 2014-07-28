package com.sobey.api.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DNSPolicyParameter;
import com.sobey.generate.dns.DNSPublicIPParameter;

public class DNSTestData {

	public static DNSParameter randomDNSParameter() {

		List<DNSPublicIPParameter> publicIPs = Lists.newArrayList();
		List<DNSPolicyParameter> policyParameters = new ArrayList<DNSPolicyParameter>();
		List<DNSPolicyParameter> policyParameters2 = new ArrayList<DNSPolicyParameter>();

		DNSPolicyParameter policyParameter = new DNSPolicyParameter();
		policyParameter.setProtocolText("tcp");
		policyParameter.setPort(8080);
		policyParameters.add(policyParameter);

		DNSPolicyParameter policyParameter2 = new DNSPolicyParameter();
		policyParameter2.setProtocolText("tcp");
		policyParameter2.setPort(80);

		policyParameters.add(policyParameter2);

		DNSPublicIPParameter ipParameter = new DNSPublicIPParameter();

		ipParameter.setIpaddress("113.142.30.109");
		ipParameter.getPolicyParameters().addAll(policyParameters);

		publicIPs.add(ipParameter);

		DNSPolicyParameter policyParameter3 = new DNSPolicyParameter();
		policyParameter3.setProtocolText("tcp");
		policyParameter3.setPort(8080);
		policyParameters2.add(policyParameter3);

		DNSPolicyParameter policyParameter4 = new DNSPolicyParameter();
		policyParameter4.setProtocolText("tcp");
		policyParameter4.setPort(80);

		policyParameters2.add(policyParameter4);

		DNSPublicIPParameter ipParameter2 = new DNSPublicIPParameter();

		ipParameter2.setIpaddress("113.200.74.140");
		ipParameter2.getPolicyParameters().addAll(policyParameters2);

		publicIPs.add(ipParameter2);

		DNSParameter parameter = new DNSParameter();
		parameter.setDomianName("mdnftp.sobeycache.com");
		parameter.setDomianType("gslb");
		parameter.getPublicIPs().addAll(publicIPs);

		return parameter;
	}

}
