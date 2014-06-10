package com.sobey.nagios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.core.mapper.BeanMapper;
import com.sobey.nagios.constans.NagiosItemEnum;
import com.sobey.nagios.dao.NagiosDao;
import com.sobey.nagios.entity.NagiosCPULoad;
import com.sobey.nagios.entity.NagiosCPUNumber;
import com.sobey.nagios.entity.NagiosCurrentUsers;
import com.sobey.nagios.entity.NagiosDiskIO;
import com.sobey.nagios.entity.NagiosEth;
import com.sobey.nagios.entity.NagiosMemoryLoad;
import com.sobey.nagios.entity.NagiosPing;
import com.sobey.nagios.entity.NagiosResult;
import com.sobey.nagios.entity.NagiosRootPartition;
import com.sobey.nagios.entity.NagiosStream;
import com.sobey.nagios.entity.NagiosStreamResult;
import com.sobey.nagios.entity.NagiosSwapUsage;
import com.sobey.nagios.entity.NagiosSystemOS;
import com.sobey.nagios.entity.NagiosTotalProcesses;
import com.sobey.nagios.entity.NagiosUpTime;
import com.sobey.nagios.util.AnalyzeUtil;
import com.sobey.nagios.webservice.response.dto.NagiosCPULoadDTO;
import com.sobey.nagios.webservice.response.dto.NagiosCPUNumberDTO;
import com.sobey.nagios.webservice.response.dto.NagiosCurrentUsersDTO;
import com.sobey.nagios.webservice.response.dto.NagiosDiskIODTO;
import com.sobey.nagios.webservice.response.dto.NagiosEthDTO;
import com.sobey.nagios.webservice.response.dto.NagiosMemoryLoadDTO;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;
import com.sobey.nagios.webservice.response.dto.NagiosRootPartitionDTO;
import com.sobey.nagios.webservice.response.dto.NagiosStreamDTO;
import com.sobey.nagios.webservice.response.dto.NagiosSwapUsageDTO;
import com.sobey.nagios.webservice.response.dto.NagiosSystemOSDTO;
import com.sobey.nagios.webservice.response.dto.NagiosTotalProcessesDTO;
import com.sobey.nagios.webservice.response.dto.NagiosUpTimeDTO;

/**
 * 将查询出的监控结果根据监控类别进行封装.
 * 
 * @author Administrator
 * 
 */
@Service
public class NagiosService {

	@Autowired
	private NagiosDao dao;

	public NagiosStreamDTO stream() {

		List<NagiosStreamResult> results = dao.getNagiosStreamResult();

		ArrayList<NagiosStream> streams = new ArrayList<NagiosStream>();

		streams = (ArrayList<NagiosStream>) BeanMapper.mapList(results, NagiosStream.class);

		NagiosStreamDTO dto = new NagiosStreamDTO();

		dto.setNagiosStreams(streams);

		return dto;
	}

	public NagiosPingDTO ping(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Ping.toString(), ipaddress, startDate, endDate);

		ArrayList<NagiosPing> pings = new ArrayList<NagiosPing>();

		for (NagiosResult result : results) {

			pings.add(AnalyzeUtil.analyzePing(result));
		}

		NagiosPingDTO dto = new NagiosPingDTO();

		dto.setNagiosPings(pings);

		return dto;
	}

	public NagiosCPULoadDTO cpuLoad(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Current_Load.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosCPULoad> loads = new ArrayList<NagiosCPULoad>();

		for (NagiosResult result : results) {

			loads.add(AnalyzeUtil.analyzeCPULoad(result));
		}

		NagiosCPULoadDTO dto = new NagiosCPULoadDTO();

		dto.setNagiosCPULoads(loads);

		return dto;

	}

	public NagiosCurrentUsersDTO currentUsers(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Current_Users.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosCurrentUsers> users = new ArrayList<NagiosCurrentUsers>();

		for (NagiosResult result : results) {

			users.add(AnalyzeUtil.analyzeCurrentUsers(result));
		}

		NagiosCurrentUsersDTO dto = new NagiosCurrentUsersDTO();

		dto.setNagiosCurrentUsers(users);

		return dto;

	}

	public NagiosRootPartitionDTO rootPartition(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Root_Partition.toString(), ipaddress,
				startDate, endDate);

		ArrayList<NagiosRootPartition> rootPartitions = new ArrayList<NagiosRootPartition>();

		for (NagiosResult result : results) {

			rootPartitions.add(AnalyzeUtil.analyzeRootPartition(result));
		}

		NagiosRootPartitionDTO dto = new NagiosRootPartitionDTO();

		dto.setNagiosRootPartitions(rootPartitions);

		return dto;
	}

	public NagiosSwapUsageDTO swapUsage(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Swap_Usage.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosSwapUsage> swapUsages = new ArrayList<NagiosSwapUsage>();

		for (NagiosResult result : results) {

			swapUsages.add(AnalyzeUtil.analyzeSwapUsage(result));
		}

		NagiosSwapUsageDTO dto = new NagiosSwapUsageDTO();

		dto.setNagiosSwapUsages(swapUsages);

		return dto;
	}

	public NagiosTotalProcessesDTO totalProcesse(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Total_Processes.toString(), ipaddress,
				startDate, endDate);

		ArrayList<NagiosTotalProcesses> processes = new ArrayList<NagiosTotalProcesses>();

		for (NagiosResult result : results) {

			processes.add(AnalyzeUtil.analyzeTotalProcesses(result));
		}

		NagiosTotalProcessesDTO dto = new NagiosTotalProcessesDTO();

		dto.setNagiosTotalProcesses(processes);

		return dto;
	}

	public NagiosCPUNumberDTO cpuNumber(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.CPU_Num.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosCPUNumber> cpuNumbers = new ArrayList<NagiosCPUNumber>();

		for (NagiosResult result : results) {

			cpuNumbers.add(AnalyzeUtil.analyzeCPUNumber(result));
		}

		NagiosCPUNumberDTO dto = new NagiosCPUNumberDTO();

		dto.setNagiosCPUNumbers(cpuNumbers);

		return dto;
	}

	public NagiosDiskIODTO diskIO(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.DiskIO.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosDiskIO> diskIOs = new ArrayList<NagiosDiskIO>();

		for (NagiosResult result : results) {

			diskIOs.add(AnalyzeUtil.analyzeDiskIO(result));
		}

		NagiosDiskIODTO dto = new NagiosDiskIODTO();

		dto.setNagiosDiskIOs(diskIOs);

		return dto;
	}

	public NagiosEthDTO eth(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Eth.toString(), ipaddress, startDate, endDate);

		ArrayList<NagiosEth> eths = new ArrayList<NagiosEth>();

		for (NagiosResult result : results) {

			eths.add(AnalyzeUtil.analyzeEth(result));
		}

		NagiosEthDTO dto = new NagiosEthDTO();

		dto.setNagiosEths(eths);

		return dto;

	}

	public NagiosMemoryLoadDTO memoryLoad(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Mem_Size.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosMemoryLoad> memoryLoads = new ArrayList<NagiosMemoryLoad>();

		for (NagiosResult result : results) {

			memoryLoads.add(AnalyzeUtil.analyzeMemoryLoad(result));
		}

		NagiosMemoryLoadDTO dto = new NagiosMemoryLoadDTO();

		dto.setNagiosMemoryLoads(memoryLoads);

		return dto;
	}

	public NagiosSystemOSDTO systemOS(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.System_OS.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosSystemOS> systemOSs = new ArrayList<NagiosSystemOS>();

		for (NagiosResult result : results) {

			systemOSs.add(AnalyzeUtil.analyzeSystemOS(result));
		}

		NagiosSystemOSDTO dto = new NagiosSystemOSDTO();

		dto.setNagiosSystemOSs(systemOSs);

		return dto;
	}

	public NagiosUpTimeDTO upTime(String ipaddress, String startDate, String endDate) {

		List<NagiosResult> results = dao.getNagiosResult(NagiosItemEnum.Uptime.toString(), ipaddress, startDate,
				endDate);

		ArrayList<NagiosUpTime> upTimes = new ArrayList<NagiosUpTime>();

		for (NagiosResult result : results) {

			upTimes.add(AnalyzeUtil.analyzeUpTime(result));
		}

		NagiosUpTimeDTO dto = new NagiosUpTimeDTO();

		dto.setNagiosUpTimes(upTimes);

		return dto;
	}

}
