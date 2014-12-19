package com.sobey.api.service;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest extends TestCase {

	@Autowired
	public ApiService service;

	@Test
	public void syncVM() {
		System.out.println(service.syncVM("xa"));
	}

	@Test
	public void syncHost() {
		service.syncHost("xa");
	}

	@Test
	public void syncVMIpaddress() {
		service.syncVMIpaddress("xa");
	}

	@Test
	public void syncVolume() {
		service.syncVolume();
	}

	@Test
	public void syncELB() {
		service.syncELB();
	}

	@Test
	public void syncDNS() {
		service.syncDNS();
	}

}
