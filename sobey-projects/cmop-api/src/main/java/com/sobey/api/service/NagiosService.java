package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.nagios.NagiosCPULoadDTO;
import com.sobey.generate.nagios.NagiosCPUNumberDTO;
import com.sobey.generate.nagios.NagiosCurrentUsersDTO;
import com.sobey.generate.nagios.NagiosDiskIODTO;
import com.sobey.generate.nagios.NagiosEthDTO;
import com.sobey.generate.nagios.NagiosMemoryLoadDTO;
import com.sobey.generate.nagios.NagiosPingDTO;
import com.sobey.generate.nagios.NagiosRootPartitionDTO;
import com.sobey.generate.nagios.NagiosSoapService;
import com.sobey.generate.nagios.NagiosSwapUsageDTO;
import com.sobey.generate.nagios.NagiosSystemOSDTO;
import com.sobey.generate.nagios.NagiosTotalProcessesDTO;
import com.sobey.generate.nagios.NagiosUpTimeDTO;

/**
 * 实现nagios webservice
 * 
 * @author Administrator
 * 
 */
@Service
public class NagiosService {

	@Autowired
	private NagiosSoapService service;

	public NagiosPingDTO getNagiosPing(String ipaddress, String startDate, String endDate) {
		return service.getNagiosPing(ipaddress, startDate, endDate);
	}

	public NagiosUpTimeDTO getNagiosUpTime(String ipaddress, String startDate, String endDate) {
		return service.getNagiosUpTime(ipaddress, startDate, endDate);
	}

	public NagiosSystemOSDTO getNagiosSystemOS(String ipaddress, String startDate, String endDate) {
		return service.getNagiosSystemOS(ipaddress, startDate, endDate);
	}

	public NagiosMemoryLoadDTO getNagiosMemoryLoad(String ipaddress, String startDate, String endDate) {
		return service.getNagiosMemoryLoad(ipaddress, startDate, endDate);
	}

	public NagiosDiskIODTO getNagiosDiskIO(String ipaddress, String startDate, String endDate) {
		return service.getNagiosDiskIO(ipaddress, startDate, endDate);
	}

	public NagiosCPUNumberDTO getNagiosCPUNumber(String ipaddress, String startDate, String endDate) {
		return service.getNagiosCPUNumber(ipaddress, startDate, endDate);
	}

	public NagiosSwapUsageDTO getNagiosSwapUsage(String ipaddress, String startDate, String endDate) {
		return service.getNagiosSwapUsage(ipaddress, startDate, endDate);
	}

	public NagiosRootPartitionDTO getNagiosRootPartition(String ipaddress, String startDate, String endDate) {
		return service.getNagiosRootPartition(ipaddress, startDate, endDate);
	}

	public NagiosCPULoadDTO getNagiosCPULoad(String ipaddress, String startDate, String endDate) {
		return service.getNagiosCPULoad(ipaddress, startDate, endDate);
	}

	public NagiosTotalProcessesDTO getNagiosTotalProcesses(String ipaddress, String startDate, String endDate) {
		return service.getNagiosTotalProcesse(ipaddress, ipaddress, endDate);
	}

	public NagiosEthDTO getNagiosEth(String ipaddress, String startDate, String endDate) {
		return service.getNagiosEth(ipaddress, ipaddress, endDate);
	}

	public NagiosCurrentUsersDTO getNagiosCurrentUsers(String ipaddress, String startDate, String endDate) {
		return service.getNagiosCurrentUsers(ipaddress, ipaddress, endDate);
	}

}
