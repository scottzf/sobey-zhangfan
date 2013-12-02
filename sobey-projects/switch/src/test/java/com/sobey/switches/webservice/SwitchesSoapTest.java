package com.sobey.switches.webservice;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.switches.BaseFunctionalTestCase;
import com.sobey.switches.data.TestData;
import com.sobey.switches.webservice.response.dto.VlanParameter;
import com.sobey.switches.webservice.response.result.WSResult;

/**
 * Switch交换机 SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class SwitchesSoapTest extends BaseFunctionalTestCase {

	@Autowired
	protected SwitchesSoapService service;

	@Test
	// @Ignore
	public void createVlan() {
		VlanParameter vlanParameter = TestData.randomVlanParameter();
		WSResult result = service.createVlanBySwtich(vlanParameter);
		assertEquals("0", result.getCode());
	}

	@Test
	@Ignore
	public void deleteVlan() {
		WSResult result = service.deleteVlanBySwtich(80);
		assertEquals("0", result.getCode());
	}

}
