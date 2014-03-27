package com.sobey.api.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.sobey.api.service.LoadbalancerService;
import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.loadbalancer.ELBPolicyParameter;
import com.sobey.generate.loadbalancer.ELBPublicIPParameter;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LoadbalancerTest extends TestCase {

	@Autowired
	private LoadbalancerService service;

	private ELBParameter getParameter() {

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

	@Test
	public void createELBTest() {
		assertEquals(service.createElb(getParameter()).getCode(), "0");
	}

	@Test
	public void deleteELBTest() {
		assertEquals(service.deleteElb(getParameter()).getCode(), "0");
	}

}
