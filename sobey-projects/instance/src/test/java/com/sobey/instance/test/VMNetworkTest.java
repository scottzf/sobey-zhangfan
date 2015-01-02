package com.sobey.instance.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.data.TestData;
import com.sobey.instance.service.NetworkService;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMNetworkTest extends TestCase {

	@Autowired
	private NetworkService service;

	// private static final String url = "https://172.20.0.19/sdk";
	// private static final String user = "administrator@vsphere.local";
	// private static final String password = "Newmed!@s0bey";

	// private static final String url = "https://172.16.2.252/sdk";
	// private static final String user = "administrator@vsphere.local";
	// private static final String password = "vmware";

	@Test
	public void createStandardSwitch() {
		service.createStandardSwitch(TestData.randomCreateStandardSwitchParameter());
	}

	@Test
	public void createPortGroup() {
		System.out.println(service.createPortGroup(TestData.randomCreatePortGroupParameter()).getMessage());
	}

	@Test
	public void bindingPortGroup() {
		System.out.println(service.bindingPortGroup(TestData.randomBindingPortGroupParameter()).getMessage());
	}

}
