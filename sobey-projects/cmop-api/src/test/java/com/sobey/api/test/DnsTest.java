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
import com.sobey.api.service.DnsService;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DNSPolicyParameter;
import com.sobey.generate.dns.DNSPublicIPParameter;

/**
 * 针对Dns的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DnsTest extends TestCase {

	@Autowired
	private DnsService service;

	private DNSParameter getParameter() {

		List<DNSPublicIPParameter> publicIPs = Lists.newArrayList();
		List<DNSPolicyParameter> policyParameters = new ArrayList<DNSPolicyParameter>();
		List<DNSPolicyParameter> policyParameters2 = new ArrayList<DNSPolicyParameter>();

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
		ipParameter.getPolicyParameters().addAll(policyParameters);

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
		ipParameter2.getPolicyParameters().addAll(policyParameters2);

		publicIPs.add(ipParameter2);

		DNSParameter parameter = new DNSParameter();
		parameter.setDomianName("mdnftp.sobeycache.com");
		parameter.setDomianType("gslb");
		parameter.getPublicIPs().addAll(publicIPs);

		return parameter;
	}

	@Test
	public void createDNSTest() {
		assertEquals(service.createDNS(getParameter()).getCode(), "0");
	}

	// @Test
	public void deleteDNSTest() {
		assertEquals(service.deleteDNS(getParameter()).getCode(), "0");
	}

}
