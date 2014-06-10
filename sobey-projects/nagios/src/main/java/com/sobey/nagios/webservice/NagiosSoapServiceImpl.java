package com.sobey.nagios.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.service.NagiosService;
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

@WebService(serviceName = "NagiosSoapService", endpointInterface = "com.sobey.nagios.webservice.NagiosSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NagiosSoapServiceImpl implements NagiosSoapService {

	@Autowired
	private NagiosService service;

	@Override
	public NagiosPingDTO getNagiosPing(String ipaddress, String startDate, String endDate) {
		return service.ping(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosCPULoadDTO getNagiosCPULoad(String ipaddress, String startDate, String endDate) {
		return service.cpuLoad(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosCurrentUsersDTO getNagiosCurrentUsers(String ipaddress, String startDate, String endDate) {
		return service.currentUsers(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosRootPartitionDTO getNagiosRootPartition(String ipaddress, String startDate, String endDate) {
		return service.rootPartition(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosSwapUsageDTO getNagiosSwapUsage(String ipaddress, String startDate, String endDate) {
		return service.swapUsage(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosTotalProcessesDTO getNagiosTotalProcesse(String ipaddress, String startDate, String endDate) {
		return service.totalProcesse(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosCPUNumberDTO getNagiosCPUNumber(String ipaddress, String startDate, String endDate) {
		return service.cpuNumber(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosDiskIODTO getNagiosDiskIO(String ipaddress, String startDate, String endDate) {
		return service.diskIO(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosEthDTO getNagiosEth(String ipaddress, String startDate, String endDate) {
		return service.eth(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosMemoryLoadDTO getNagiosMemoryLoad(String ipaddress, String startDate, String endDate) {
		return service.memoryLoad(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosSystemOSDTO getNagiosSystemOS(String ipaddress, String startDate, String endDate) {
		return service.systemOS(ipaddress, startDate, endDate);
	}

	@Override
	public NagiosUpTimeDTO getNagiosUpTime(String ipaddress, String startDate, String endDate) {
		return service.upTime(ipaddress, startDate, endDate);
	}

}
