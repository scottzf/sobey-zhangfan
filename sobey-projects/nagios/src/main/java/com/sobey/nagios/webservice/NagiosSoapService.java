package com.sobey.nagios.webservice;

import javax.jws.WebService;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.webservice.response.dto.NagiosCPULoadDTO;
import com.sobey.nagios.webservice.response.dto.NagiosCPUNumberDTO;
import com.sobey.nagios.webservice.response.dto.NagiosCurrentUsersDTO;
import com.sobey.nagios.webservice.response.dto.NagiosDiskIODTO;
import com.sobey.nagios.webservice.response.dto.NagiosEthDTO;
import com.sobey.nagios.webservice.response.dto.NagiosMemoryLoadDTO;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;
import com.sobey.nagios.webservice.response.dto.NagiosRootPartitionDTO;
import com.sobey.nagios.webservice.response.dto.NagiosSwapUsageDTO;
import com.sobey.nagios.webservice.response.dto.NagiosSystemOSDTO;
import com.sobey.nagios.webservice.response.dto.NagiosTotalProcessesDTO;
import com.sobey.nagios.webservice.response.dto.NagiosUpTimeDTO;

/**
 * Nagios对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "NagiosSoapService", targetNamespace = WsConstants.NS)
public interface NagiosSoapService {

	NagiosCPULoadDTO getNagiosCPULoad(String ipaddress, String startDate, String endDate);

	NagiosCurrentUsersDTO getNagiosCurrentUsers(String ipaddress, String startDate, String endDate);

	NagiosPingDTO getNagiosPing(String ipaddress, String startDate, String endDate);

	NagiosRootPartitionDTO getNagiosRootPartition(String ipaddress, String startDate, String endDate);

	NagiosSwapUsageDTO getNagiosSwapUsage(String ipaddress, String startDate, String endDate);

	NagiosTotalProcessesDTO getNagiosTotalProcesse(String ipaddress, String startDate, String endDate);

	NagiosCPUNumberDTO getNagiosCPUNumber(String ipaddress, String startDate, String endDate);

	NagiosDiskIODTO getNagiosDiskIO(String ipaddress, String startDate, String endDate);

	NagiosEthDTO getNagiosEth(String ipaddress, String startDate, String endDate);

	NagiosMemoryLoadDTO getNagiosMemoryLoad(String ipaddress, String startDate, String endDate);

	NagiosSystemOSDTO getNagiosSystemOS(String ipaddress, String startDate, String endDate);

	NagiosUpTimeDTO getNagiosUpTime(String ipaddress, String startDate, String endDate);

}
