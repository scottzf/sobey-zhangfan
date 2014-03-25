package com.sobey.nagios.util;

import org.apache.commons.lang3.StringUtils;

import com.sobey.nagios.entity.NagiosCPULoad;
import com.sobey.nagios.entity.NagiosCPUNumber;
import com.sobey.nagios.entity.NagiosCurrentUsers;
import com.sobey.nagios.entity.NagiosDiskIO;
import com.sobey.nagios.entity.NagiosEth;
import com.sobey.nagios.entity.NagiosMemoryLoad;
import com.sobey.nagios.entity.NagiosPing;
import com.sobey.nagios.entity.NagiosResult;
import com.sobey.nagios.entity.NagiosRootPartition;
import com.sobey.nagios.entity.NagiosSwapUsage;
import com.sobey.nagios.entity.NagiosSystemOS;
import com.sobey.nagios.entity.NagiosTotalProcesses;
import com.sobey.nagios.entity.NagiosUpTime;

/**
 * 对从nagios表中获得output字段进行解析工具类.
 * 
 * @author Administrator
 * 
 */
public class AnalyzeUtil {

	/**
	 * 对ping的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosPing analyzePing(NagiosResult result) {

		/**
		 * 数据库中output的字段类似: PING OK - Packet loss = 0%, RTA = 8.46 ms
		 * 
		 * 1.首先根据","将字符分拆成两段.
		 * 
		 * 2.然后再根据"="再次分拆.
		 * 
		 * 3.最后再进行一次移除,得数字.
		 */

		NagiosPing ping = new NagiosPing();

		String[] array = StringUtils.split(StringUtils.split(result.getOutput(), "|")[0], ",");
		ping.setIpaddress(result.getIpaddress());
		ping.setPacketLoss(StringUtils.remove(StringUtils.trim(StringUtils.split(array[0], "=")[1]), "%"));
		ping.setRta(StringUtils.remove(StringUtils.trim(StringUtils.split(array[1], "=")[1]), " ms"));
		ping.setStartTime(result.getStartTime());
		ping.setEndTime(result.getEndTime());

		return ping;
	}

	/**
	 * 对CurrentUsers的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosCurrentUsers analyzeCurrentUsers(NagiosResult result) {

		/**
		 * 数据库中output的字段类似: USERS OK - 2 users currently logged in
		 * 
		 * 将字符串里的字符串一个一个的进行比较,看是否是数字.如果是数字则装入到指定一个新的字符串中.
		 * 
		 */

		NagiosCurrentUsers users = new NagiosCurrentUsers();

		String userNumber = "";

		// 将字符串里的字符串一个一个的进行比较,看是否是数字.
		if (StringUtils.isNotBlank(result.getOutput())) {
			for (int i = 0; i < result.getOutput().length(); i++) {
				if (result.getOutput().charAt(i) >= 48 && result.getOutput().charAt(i) <= 57) {
					userNumber += result.getOutput().charAt(i);
				}
			}
		} else {
			userNumber = "0";
		}

		users.setIpaddress(result.getIpaddress());
		users.setStartTime(result.getStartTime());
		users.setEndTime(result.getEndTime());
		users.setUsers(Integer.valueOf(userNumber));

		return users;
	}

	/**
	 * 对eth的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosEth analyzeEth(NagiosResult result) {

		/**
		 * 数据库中output的字段类似: OK - The Traffic In is 0.0MB, Out is 0.0MB, Total is 0.0MB. The Check Interval is 600s
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		String[] array = StringUtils.substringsBetween(result.getOutput(), "is ", "MB");

		NagiosEth eth = new NagiosEth();

		eth.setIpaddress(result.getIpaddress());
		eth.setStartTime(result.getStartTime());
		eth.setEndTime(result.getEndTime());
		eth.setTrafficIn(array[0]);
		eth.setTrafficOut(array[1]);
		eth.setTrafficTotal(array[2]);
		eth.setInterval(StringUtils.remove(StringUtils.substringAfterLast(result.getOutput(), "is "), "s"));

		return eth;
	}

	/**
	 * 对TotalProcesses的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosTotalProcesses analyzeTotalProcesses(NagiosResult result) {

		/**
		 * 数据库中output的字段类似: PROCS OK: 106 processes
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		String[] array = StringUtils.split(result.getOutput(), " ");

		NagiosTotalProcesses totalProcesses = new NagiosTotalProcesses();

		totalProcesses.setIpaddress(result.getIpaddress());
		totalProcesses.setStartTime(result.getStartTime());
		totalProcesses.setEndTime(result.getEndTime());
		totalProcesses.setProcesses(array[2] != null ? Integer.valueOf(array[2]) : 0);

		return totalProcesses;
	}

	/**
	 * 对CPULoad的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosCPULoad analyzeCPULoad(NagiosResult result) {

		/**
		 * 数据库中output的字段类似: OK - load average: 0.00, 0.00, 0.00
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		String[] array = StringUtils.split(StringUtils.remove(
				StringUtils.splitByWholeSeparator(result.getOutput(), ": ")[1], ","));

		NagiosCPULoad cpuLoad = new NagiosCPULoad();

		cpuLoad.setIpaddress(result.getIpaddress());
		cpuLoad.setStartTime(result.getStartTime());
		cpuLoad.setEndTime(result.getEndTime());
		cpuLoad.setAverage5(array[0]);
		cpuLoad.setAverage10(array[1]);
		cpuLoad.setAverage15(array[2]);

		return cpuLoad;
	}

	/**
	 * 对RootPartition的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosRootPartition analyzeRootPartition(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:DISK OK - free space: / 11974 MB (79% inode=97%):
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		NagiosRootPartition rootPartition = new NagiosRootPartition();

		rootPartition.setIpaddress(result.getIpaddress());
		rootPartition.setStartTime(result.getStartTime());
		rootPartition.setEndTime(result.getEndTime());
		rootPartition.setFreeSpace(StringUtils.substringsBetween(result.getOutput(), "/ ", " MB")[0]);
		rootPartition.setFreePer(StringUtils.substringsBetween(result.getOutput(), "(", "%")[0]);
		rootPartition.setInode(StringUtils.substringsBetween(result.getOutput(), "=", "%")[0]);

		return rootPartition;
	}

	/**
	 * 对SwapUsage的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosSwapUsage analyzeSwapUsage(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:SWAP OK - 100% free (3999 MB out of 3999 MB)
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		NagiosSwapUsage swapUsage = new NagiosSwapUsage();

		swapUsage.setIpaddress(result.getIpaddress());
		swapUsage.setStartTime(result.getStartTime());
		swapUsage.setEndTime(result.getEndTime());
		swapUsage.setFreePer(StringUtils.substringsBetween(result.getOutput(), "- ", "%")[0]);
		swapUsage.setTotalSpace(StringUtils.substringsBetween(result.getOutput(), "(", " MB")[0]);
		swapUsage.setFreeSpace(StringUtils.substringsBetween(result.getOutput(), "of ", " MB")[0]);

		return swapUsage;
	}

	/**
	 * 对CPUNumber的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosCPUNumber analyzeCPUNumber(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:cpu_num:2
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		NagiosCPUNumber cpuNumber = new NagiosCPUNumber();

		cpuNumber.setIpaddress(result.getIpaddress());
		cpuNumber.setStartTime(result.getStartTime());
		cpuNumber.setEndTime(result.getEndTime());
		cpuNumber.setCpuNumber(Integer.valueOf(StringUtils.split(result.getOutput(), ":")[1]));

		return cpuNumber;
	}

	/**
	 * 对DiskIO的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosDiskIO analyzeDiskIO(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:OK - I/O stats tps=0.84 KB_read/s=0.29 KB_written/s=16.72
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		NagiosDiskIO diskIO = new NagiosDiskIO();

		diskIO.setIpaddress(result.getIpaddress());
		diskIO.setStartTime(result.getStartTime());
		diskIO.setEndTime(result.getEndTime());
		diskIO.setTps(StringUtils.substringsBetween(result.getOutput(), "tps=", " KB_read")[0]);
		diskIO.setRead(StringUtils.substringsBetween(result.getOutput(), "KB_read/s=", " KB_written")[0]);
		diskIO.setWrite(StringUtils.substringAfter(result.getOutput(), "written/s="));

		return diskIO;
	}

	/**
	 * 对MemoryLoad的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosMemoryLoad analyzeMemoryLoad(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:OK - System RAM used: 22% (927220 / 4043792 kB)
		 * 
		 * 将字符串里的字符串按指定的格式分拆
		 * 
		 */

		NagiosMemoryLoad memoryLoad = new NagiosMemoryLoad();

		memoryLoad.setIpaddress(result.getIpaddress());
		memoryLoad.setStartTime(result.getStartTime());
		memoryLoad.setEndTime(result.getEndTime());
		memoryLoad.setUsedPer(StringUtils.substringsBetween(result.getOutput(), "used: ", "%")[0]);
		memoryLoad.setUserd(StringUtils.substringsBetween(result.getOutput(), "(", " /")[0]);
		memoryLoad.setTotal(StringUtils.substringsBetween(result.getOutput(), "/ ", " kB)")[0]);

		return memoryLoad;
	}

	/**
	 * 对SystemOS的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosSystemOS analyzeSystemOS(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:linxu 2.6.18-238 centos5.6
		 * 
		 */

		NagiosSystemOS systemOS = new NagiosSystemOS();

		systemOS.setIpaddress(result.getIpaddress());
		systemOS.setStartTime(result.getStartTime());
		systemOS.setEndTime(result.getEndTime());
		systemOS.setSystemOS(result.getOutput());

		return systemOS;
	}

	/**
	 * 对UpTime的output解析
	 * 
	 * @param result
	 * @return
	 */
	public static NagiosUpTime analyzeUpTime(NagiosResult result) {

		/**
		 * 数据库中output的字段类似:System Uptime - up 43 days, 15 Hours, 31 Minutes
		 * 
		 */

		NagiosUpTime upTime = new NagiosUpTime();

		upTime.setIpaddress(result.getIpaddress());
		upTime.setStartTime(result.getStartTime());
		upTime.setEndTime(result.getEndTime());
		upTime.setUpTime(result.getOutput());

		return upTime;
	}

}
