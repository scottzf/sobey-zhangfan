package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.nagios.NagiosPingDTO;
import com.sobey.generate.nagios.NagiosSoapService;

@Service
public class NagiosService {

	@Autowired
	private NagiosSoapService service;

	public NagiosPingDTO getNagiosPing(String itemId, String ipaddress, String startDate, String endDate) {
		return service.getNagiosPing(itemId, ipaddress, startDate, endDate);
	}

}
