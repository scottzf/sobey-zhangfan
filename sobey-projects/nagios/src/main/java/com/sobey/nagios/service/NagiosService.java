package com.sobey.nagios.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.nagios.dao.NagiosDao;
import com.sobey.nagios.entity.NagiosPing;
import com.sobey.nagios.entity.NagiosResult;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;

@Service
public class NagiosService {

	@Autowired
	private NagiosDao dao;

	/**
	 * 获得Ping
	 * 
	 * 
	 * @param output
	 * @return
	 */
	public NagiosPingDTO ping(String itemId, String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(itemId, ipaddress, startDate, endDate);

		NagiosPingDTO dto = new NagiosPingDTO();

		ArrayList<NagiosPing> pings = new ArrayList<NagiosPing>();

		for (NagiosResult result : results) {

			NagiosPing ping = new NagiosPing();

			/**
			 * 数据库中output的字段类似: PING OK - Packet loss = 0%, RTA = 8.46 ms
			 * 
			 * 1.首先根据","将字符分拆成两段.
			 * 
			 * 2.然后再根据"="再次分拆.
			 * 
			 * 3.最后再进行一次移除,得数字.
			 */

			String[] array = StringUtils.split(StringUtils.split(result.getOutput(), "|")[0], ",");
			ping.setIpaddress(result.getIpaddress());
			ping.setPacketLoss(StringUtils.remove(StringUtils.trim(StringUtils.split(array[0], "=")[1]), "%"));
			ping.setRta(StringUtils.remove(StringUtils.trim(StringUtils.split(array[1], "=")[1]), " ms"));
			ping.setStartTime(result.getStartTime());
			ping.setEndTime(result.getEndTime());

			pings.add(ping);
		}

		dto.setNagiosPings(pings);

		return dto;
	}

}
