package com.sobey.api.service;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.constans.DataCenterEnum;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest extends TestCase {

	@Autowired
	public ApiService service;

	private static String datacenter = DataCenterEnum.西安核心数据中心.toString();

	@Test
	public void syncVM() {
		service.syncVM(datacenter);
	}

	@Test
	public void syncHost() {
		service.syncHost(datacenter);
	}

	@Test
	public void syncVMIpaddress() {
		service.syncVMIpaddress(datacenter);
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
