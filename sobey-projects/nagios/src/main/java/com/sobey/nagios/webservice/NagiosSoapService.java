package com.sobey.nagios.webservice;

import javax.jws.WebService;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;

/**
 * Nagios对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "NagiosSoapService", targetNamespace = WsConstants.NS)
public interface NagiosSoapService {

	NagiosPingDTO getNagiosPing(String itemId, String ipaddress, String startDate, String endDate);

}
