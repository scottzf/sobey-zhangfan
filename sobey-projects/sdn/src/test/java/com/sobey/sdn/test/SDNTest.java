package com.sobey.sdn.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.sdn.bean.CreateEipParameter;
import com.sobey.sdn.bean.VPNParameter;
import com.sobey.sdn.service.impl.SDNServiceImpl;
import com.sobey.sdn.test.data.TestData;
import com.sobey.sdn.test.testParameter.BindingFirewallParameter;
import com.sobey.sdn.test.testParameter.BindingRouterParameter;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SDNTest extends TestCase {

	@Autowired
	private SDNServiceImpl sdnService;

	@Test
	public void createECS() throws Exception {

		CreateECSParameter createECSParameter = TestData.randomCreateECSParameter();

		sdnService.createECS(createECSParameter);
	}

	@Test
	public void createRouter() throws Exception {

		CreateRouterParameter createRouterParameter = TestData.randomCreateRouterParameter();

		sdnService.createRouter(createRouterParameter);

	}

	@Test
	public void bindingRouter() throws Exception {

		BindingRouterParameter bindingRouterParameter = TestData.randomBindingRouterParameter();

		sdnService.bindingRouter(bindingRouterParameter);

	}

	@Test
	public void bindingFirewall() throws Exception {

		BindingFirewallParameter parameter = TestData.randomBindingFirewallParameter();

		sdnService.bindingFirewall(parameter);
	}
	
	@Test
	public void createEip() throws Exception {
		
		CreateEipParameter parameter = TestData.randomCreateEipParameter();
		
		sdnService.createEip(parameter);
		
	}
	
	@Test
	public void createVPNUser() throws Exception {
		
		VPNParameter parameter = TestData.randomVPNParameter();
		
		sdnService.createVPNUser(parameter);
	}

}
