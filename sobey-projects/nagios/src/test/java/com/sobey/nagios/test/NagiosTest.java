package com.sobey.nagios.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.nagios.entity.NagiosCPULoad;
import com.sobey.nagios.entity.NagiosCPUNumber;
import com.sobey.nagios.entity.NagiosCurrentUsers;
import com.sobey.nagios.entity.NagiosDiskIO;
import com.sobey.nagios.entity.NagiosEth;
import com.sobey.nagios.entity.NagiosMemoryLoad;
import com.sobey.nagios.entity.NagiosPing;
import com.sobey.nagios.entity.NagiosRootPartition;
import com.sobey.nagios.entity.NagiosSwapUsage;
import com.sobey.nagios.entity.NagiosSystemOS;
import com.sobey.nagios.entity.NagiosTotalProcesses;
import com.sobey.nagios.entity.NagiosUpTime;
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

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class NagiosTest {

	@Autowired
	private NagiosService service;

	private static final String IPADDRESS = "172.20.34.1";
	private static final String STARTTIME = "2014-01-16 15:00:00";
	private static final String ENDTIME = "2014-01-16 15:30:00";

	@Test
	public void upTimeTets() {

		NagiosUpTimeDTO dto = service.upTime(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosUpTime upTime : dto.getNagiosUpTimes()) {
			System.out.println(upTime.getUpTime());
			System.out.println("***************");
		}

	}

	@Test
	public void systemOSTest() {

		NagiosSystemOSDTO dto = service.systemOS(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosSystemOS systemOS : dto.getNagiosSystemOSs()) {
			System.out.println(systemOS.getSystemOS());
			System.out.println("***************");
		}
	}

	@Test
	public void memoryLoadTest() {

		NagiosMemoryLoadDTO dto = service.memoryLoad(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosMemoryLoad memoryLoad : dto.getNagiosMemoryLoads()) {
			System.out.println(memoryLoad.getUsedPer());
			System.out.println(memoryLoad.getUsed());
			System.out.println(memoryLoad.getTotal());
			System.out.println("***************");
		}
	}

	@Test
	public void diskIOTest() {

		NagiosDiskIODTO dto = service.diskIO(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosDiskIO diskIO : dto.getNagiosDiskIOs()) {
			System.out.println(diskIO.getTps());
			System.out.println(diskIO.getRead());
			System.out.println(diskIO.getWrite());
			System.out.println("***************");
		}
	}

	@Test
	public void cpuNumberTest() {

		NagiosCPUNumberDTO dto = service.cpuNumber(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosCPUNumber cpuNumber : dto.getNagiosCPUNumbers()) {
			System.out.println(cpuNumber.getCpuNumber());
			System.out.println("***************");
		}
	}

	@Test
	public void swapUsageTest() {

		NagiosSwapUsageDTO dto = service.swapUsage(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosSwapUsage swapUsage : dto.getNagiosSwapUsages()) {
			System.out.println(swapUsage.getFreeSpace());
			System.out.println(swapUsage.getFreePer());
			System.out.println(swapUsage.getTotalSpace());
			System.out.println("***************");
		}
	}

	@Test
	public void rootPartitionTest() {

		NagiosRootPartitionDTO dto = service.rootPartition(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosRootPartition rootPartition : dto.getNagiosRootPartitions()) {
			System.out.println(rootPartition.getFreeSpace());
			System.out.println(rootPartition.getFreePer());
			System.out.println(rootPartition.getInode());
			System.out.println("***************");
		}
	}

	@Test
	public void cpuLoadTest() {

		NagiosCPULoadDTO dto = service.cpuLoad(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosCPULoad cpuLoad : dto.getNagiosCPULoads()) {
			System.out.println(cpuLoad.getAverage5());
			System.out.println(cpuLoad.getAverage10());
			System.out.println(cpuLoad.getAverage15());
			System.out.println("***************");
		}
	}

	@Test
	public void totalProcesseTest() {

		NagiosTotalProcessesDTO dto = service.totalProcesse(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosTotalProcesses totalProcesses : dto.getNagiosTotalProcesses()) {
			System.out.println(totalProcesses.getProcesses());
			System.out.println("***************");
		}
	}

	@Test
	public void ethTest() {

		NagiosEthDTO dto = service.eth(IPADDRESS, STARTTIME, ENDTIME);
		for (NagiosEth eth : dto.getNagiosEths()) {
			System.out.println(eth.getTrafficOut());
			System.out.println(eth.getTrafficIn());
			System.out.println(eth.getTrafficTotal());
			System.out.println(eth.getInterval());
			System.out.println("***************");
		}
	}

	@Test
	public void pingTest() {

		NagiosPingDTO dto = service.ping(IPADDRESS, null, null);

		for (NagiosPing ping : dto.getNagiosPings()) {
			System.out.println(ping.getRta());
			System.out.println(ping.getPacketLoss());
			System.out.println("***************");
		}
	}

	@Test
	public void currentUsersTest() {

		NagiosCurrentUsersDTO dto = service.currentUsers(IPADDRESS, STARTTIME, ENDTIME);
		for (NagiosCurrentUsers users : dto.getNagiosCurrentUsers()) {
			System.out.println(users.getUsers());
			System.out.println("***************");
		}
	}
}
