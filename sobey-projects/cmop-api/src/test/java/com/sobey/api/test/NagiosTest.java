package com.sobey.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.service.NagiosService;
import com.sobey.generate.nagios.NagiosCPULoad;
import com.sobey.generate.nagios.NagiosCPULoadDTO;
import com.sobey.generate.nagios.NagiosCPUNumber;
import com.sobey.generate.nagios.NagiosCPUNumberDTO;
import com.sobey.generate.nagios.NagiosCurrentUsers;
import com.sobey.generate.nagios.NagiosCurrentUsersDTO;
import com.sobey.generate.nagios.NagiosDiskIO;
import com.sobey.generate.nagios.NagiosDiskIODTO;
import com.sobey.generate.nagios.NagiosEth;
import com.sobey.generate.nagios.NagiosEthDTO;
import com.sobey.generate.nagios.NagiosMemoryLoad;
import com.sobey.generate.nagios.NagiosMemoryLoadDTO;
import com.sobey.generate.nagios.NagiosPing;
import com.sobey.generate.nagios.NagiosPingDTO;
import com.sobey.generate.nagios.NagiosRootPartition;
import com.sobey.generate.nagios.NagiosRootPartitionDTO;
import com.sobey.generate.nagios.NagiosSwapUsage;
import com.sobey.generate.nagios.NagiosSwapUsageDTO;
import com.sobey.generate.nagios.NagiosSystemOS;
import com.sobey.generate.nagios.NagiosSystemOSDTO;
import com.sobey.generate.nagios.NagiosTotalProcesses;
import com.sobey.generate.nagios.NagiosTotalProcessesDTO;
import com.sobey.generate.nagios.NagiosUpTime;
import com.sobey.generate.nagios.NagiosUpTimeDTO;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class NagiosTest {

	private static final String IPADDRESS = "172.20.34.1";
	private static final String STARTTIME = "2014-01-16 15:00:00";
	private static final String ENDTIME = "2014-01-16 15:30:00";

	@Autowired
	private NagiosService service;

	@Test
	public void upTimeTets() {

		NagiosUpTimeDTO dto = service.getNagiosUpTime(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosUpTime upTime : dto.getNagiosUpTimes()) {
			System.out.println(upTime.getUpTime());
			System.out.println("***************");
		}

	}

	@Test
	public void systemOSTest() {

		NagiosSystemOSDTO dto = service.getNagiosSystemOS(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosSystemOS systemOS : dto.getNagiosSystemOSs()) {
			System.out.println(systemOS.getSystemOS());
			System.out.println("***************");
		}
	}

	@Test
	public void memoryLoadTest() {

		NagiosMemoryLoadDTO dto = service.getNagiosMemoryLoad(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosMemoryLoad memoryLoad : dto.getNagiosMemoryLoads()) {
			System.out.println(memoryLoad.getUsedPer());
			System.out.println(memoryLoad.getUsed());
			System.out.println(memoryLoad.getTotal());
			System.out.println("***************");
		}
	}

	@Test
	public void diskIOTest() {

		NagiosDiskIODTO dto = service.getNagiosDiskIO(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosDiskIO diskIO : dto.getNagiosDiskIOs()) {
			System.out.println(diskIO.getTps());
			System.out.println(diskIO.getRead());
			System.out.println(diskIO.getWrite());
			System.out.println("***************");
		}
	}

	@Test
	public void cpuNumberTest() {

		NagiosCPUNumberDTO dto = service.getNagiosCPUNumber(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosCPUNumber cpuNumber : dto.getNagiosCPUNumbers()) {
			System.out.println(cpuNumber.getCpuNumber());
			System.out.println("***************");
		}
	}

	@Test
	public void swapUsageTest() {

		NagiosSwapUsageDTO dto = service.getNagiosSwapUsage(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosSwapUsage swapUsage : dto.getNagiosSwapUsages()) {
			System.out.println(swapUsage.getFreeSpace());
			System.out.println(swapUsage.getFreePer());
			System.out.println(swapUsage.getTotalSpace());
			System.out.println("***************");
		}
	}

	@Test
	public void rootPartitionTest() {

		NagiosRootPartitionDTO dto = service.getNagiosRootPartition(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosRootPartition rootPartition : dto.getNagiosRootPartitions()) {
			System.out.println(rootPartition.getFreeSpace());
			System.out.println(rootPartition.getFreePer());
			System.out.println(rootPartition.getInode());
			System.out.println("***************");
		}
	}

	@Test
	public void cpuLoadTest() {

		NagiosCPULoadDTO dto = service.getNagiosCPULoad(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosCPULoad cpuLoad : dto.getNagiosCPULoads()) {
			System.out.println(cpuLoad.getAverage5());
			System.out.println(cpuLoad.getAverage10());
			System.out.println(cpuLoad.getAverage15());
			System.out.println("***************");
		}
	}

	@Test
	public void totalProcesseTest() {

		NagiosTotalProcessesDTO dto = service.getNagiosTotalProcesses(IPADDRESS, STARTTIME, ENDTIME);

		for (NagiosTotalProcesses totalProcesses : dto.getNagiosTotalProcesses()) {
			System.out.println(totalProcesses.getProcesses());
			System.out.println("***************");
		}
	}

	@Test
	public void ethTest() {

		NagiosEthDTO dto = service.getNagiosEth(IPADDRESS, STARTTIME, ENDTIME);
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

		NagiosPingDTO dto = service.getNagiosPing(IPADDRESS, null, null);

		for (NagiosPing ping : dto.getNagiosPings()) {
			System.out.println(ping.getRta());
			System.out.println(ping.getPacketLoss());
			System.out.println("***************");
		}
	}

	@Test
	public void currentUsersTest() {

		NagiosCurrentUsersDTO dto = service.getNagiosCurrentUsers(IPADDRESS, STARTTIME, ENDTIME);
		for (NagiosCurrentUsers users : dto.getNagiosCurrentUsers()) {
			System.out.println(users.getUsers());
			System.out.println("***************");
		}
	}
}
