package com.sobey.nagios.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.nagios.entity.NagiosPing;
import com.sobey.nagios.service.NagiosService;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class NagiosTest {

	@Autowired
	private NagiosService service;

	private static final String ITEMID = "1386";
	private static final String IPADDRESS = "172.20.34.1";
	private static final String STARTTIME = "2014-01-16 15:00:00";
	private static final String ENDTIME = "2014-01-16 15:30:00";

	@Test
	public void pingTest() {

		NagiosPingDTO dto = service.ping(ITEMID, IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosPing ping : dto.getNagiosPings()) {
			System.out.println(ping.getRta());
			System.out.println(ping.getPacketLoss());
		}

	}
}
