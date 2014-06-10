package com.sobey.api.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.loadbalancer.ELBPolicyParameter;
import com.sobey.generate.loadbalancer.ELBPublicIPParameter;

public class LoadbalancerTestData {

	public static ELBParameter randomELBParameter() {

		List<ELBPublicIPParameter> publicIPs = Lists.newArrayList();
		List<ELBPolicyParameter> policyParameters = new ArrayList<ELBPolicyParameter>();
		List<ELBPolicyParameter> policyParameters2 = new ArrayList<ELBPolicyParameter>();

		ELBPolicyParameter policyParameter = new ELBPolicyParameter();
		policyParameter.setProtocolText("HTTP");
		policyParameter.setSourcePort(80);
		policyParameter.setTargetPort(80);
		policyParameters.add(policyParameter);

		ELBPublicIPParameter ipParameter = new ELBPublicIPParameter();

		ipParameter.setIpaddress("172.20.0.99");
		ipParameter.getPolicyParameters().addAll(policyParameters);

		ELBPolicyParameter policyParameter2 = new ELBPolicyParameter();
		policyParameter2.setProtocolText("HTTP");
		policyParameter2.setSourcePort(80);
		policyParameter2.setTargetPort(80);
		policyParameters2.add(policyParameter2);

		ELBPublicIPParameter ipParameter2 = new ELBPublicIPParameter();

		ipParameter2.setIpaddress("172.20.0.94");
		ipParameter2.getPolicyParameters().addAll(policyParameters2);

		publicIPs.add(ipParameter);
		publicIPs.add(ipParameter2);

		ELBParameter parameter = new ELBParameter();
		parameter.setVip("10.0.8.72");
		parameter.getPublicIPs().addAll(publicIPs);

		return parameter;
	}

}
