package com.sobey.firewall.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fortinet.fmg.ws.r200806.FortiManagerWSxml;
import com.fortinet.fmg.ws.r200806.InvalidServicePassException;
import com.fortinet.fmg.ws.r200806.ServicePass;
import com.fortinet.fmg.ws.r200806.TooManyEntriesException;
import com.sobey.firewall.PbulicProperties;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SDKTest implements PbulicProperties {

	@Test
	public void readVIP() throws IOException, InvalidServicePassException, TooManyEntriesException {

		ServicePass servicePass = new ServicePass();
		servicePass.setUserID("admin");
		servicePass.setPassword("Newmed!@s0bey");

		FortiManagerWSxml fortiManagerWSxml = new FortiManagerWSxml();

		// fortiManagerWSxml.getFortiManagerWSxml().getDeviceList(servicePass, adom, detail, errorMsg, deviceInfo,
		// deviceDetail);

	}

}
