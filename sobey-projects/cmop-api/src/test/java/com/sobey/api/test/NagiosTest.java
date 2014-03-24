package com.sobey.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.service.NagiosService;
import com.sobey.generate.nagios.NagiosPing;
import com.sobey.generate.nagios.NagiosPingDTO;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class NagiosTest {

	private static final String ITEMID = "1386";
	private static final String IPADDRESS = "172.20.34.1";
	private static final String STARTTIME = "2014-01-16 15:00:00";
	private static final String ENDTIME = "2014-01-16 15:30:00";

	@Autowired
	private NagiosService service;

	@Test
	public void ping() {
		NagiosPingDTO dto = service.getNagiosPing(ITEMID, IPADDRESS, STARTTIME, ENDTIME);
		for (NagiosPing ping : dto.getNagiosPings()) {
			System.out.println(ping.getIpaddress());
			System.out.println(ping.getPacketLoss());
			System.out.println(ping.getRta());
			System.out.println(ping.getStartTime());
			System.out.println(ping.getEndTime());
			System.out.println("******");

		}
	}
}
