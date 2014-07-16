package com.sobey.cmdbuild.webservice.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class CustomSoapTest extends BaseFunctionalTestCase {

	@Test
	public void testAll() {
		System.out.println(cmdbuildSoapService.getMaxAclNumber());
		System.out.println(cmdbuildSoapService.getMaxPolicyId());

	}

}