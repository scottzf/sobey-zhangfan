package com.sobey.loadbalancer.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPolicyParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPublicIPParameter;

public class TestData {

	public static ELBParameter randomELBParameter() {

		List<ELBPublicIPParameter> publicIPs = Lists.newArrayList();
		List<ELBPolicyParameter> policyParameters = Lists.newArrayList();
		List<ELBPolicyParameter> policyParameters2 = Lists.newArrayList();

		ELBPolicyParameter policyParameter = new ELBPolicyParameter();
		policyParameter.setProtocolText("tcp");
		policyParameter.setSourcePort(8080);
		policyParameter.setTargetPort(8080);
		policyParameters.add(policyParameter);

		ELBPolicyParameter policyParameter2 = new ELBPolicyParameter();
		policyParameter2.setProtocolText("tcp");
		policyParameter2.setSourcePort(80);
		policyParameter2.setTargetPort(80);

		policyParameters.add(policyParameter2);

		ELBPublicIPParameter ipParameter = new ELBPublicIPParameter();

		ipParameter.setIpaddress("172.20.0.94");
		ipParameter.setPolicyParameters(policyParameters);

		publicIPs.add(ipParameter);

		ELBPolicyParameter policyParameter3 = new ELBPolicyParameter();
		policyParameter3.setProtocolText("tcp");
		policyParameter3.setSourcePort(8080);
		policyParameter3.setTargetPort(8080);
		policyParameters2.add(policyParameter3);

		ELBPolicyParameter policyParameter4 = new ELBPolicyParameter();
		policyParameter4.setProtocolText("tcp");
		policyParameter4.setSourcePort(80);
		policyParameter4.setTargetPort(80);

		policyParameters2.add(policyParameter4);

		ELBPublicIPParameter ipParameter2 = new ELBPublicIPParameter();

		ipParameter2.setIpaddress("172.20.0.99");
		ipParameter2.setPolicyParameters(policyParameters2);

		publicIPs.add(ipParameter2);

		ELBParameter parameter = new ELBParameter();
		parameter.setVip("10.0.8.72");
		parameter.setPublicIPs(publicIPs);

		return parameter;
	}

}
