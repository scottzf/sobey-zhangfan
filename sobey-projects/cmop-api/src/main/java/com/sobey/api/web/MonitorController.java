package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sobey.api.service.NagiosService;
import com.sobey.generate.nagios.NagiosCPULoadDTO;
import com.sobey.generate.nagios.NagiosDiskIODTO;
import com.sobey.generate.nagios.NagiosEthDTO;
import com.sobey.generate.nagios.NagiosMemoryLoadDTO;
import com.sobey.generate.nagios.NagiosPingDTO;
import com.sobey.generate.nagios.NagiosRootPartitionDTO;
import com.sobey.generate.nagios.NagiosSwapUsageDTO;
import com.sobey.generate.nagios.NagiosTotalProcessesDTO;

/**
 * 监控 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorController {

	@Autowired
	private NagiosService service;

	/**
	 * 跳转到Ping页面
	 */
	@RequestMapping(value = "/ping/")
	public String pingPage() {
		return "monitor/ping";
	}

	/**
	 * AJAX请求Ping数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/ping/", method = RequestMethod.POST)
	public @ResponseBody NagiosPingDTO ping(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosPing(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/memory/")
	public String memoryPage() {
		return "monitor/memory";
	}

	/**
	 * AJAX请求memory数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/memory/", method = RequestMethod.POST)
	public @ResponseBody NagiosMemoryLoadDTO memory(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosMemoryLoad(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/eth/")
	public String ethPage() {
		return "monitor/eth";
	}

	/**
	 * AJAX请求eth数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/eth/", method = RequestMethod.POST)
	public @ResponseBody NagiosEthDTO eth(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosEth(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/rootPartition/")
	public String rootPartitionPage() {
		return "monitor/rootPartition";
	}

	/**
	 * AJAX请求RootPartition数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/rootPartition/", method = RequestMethod.POST)
	public @ResponseBody NagiosRootPartitionDTO rootPartition(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosRootPartition(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/swapUsage/")
	public String swapUsagePage() {
		return "monitor/swapUsage";
	}

	/**
	 * AJAX请求SwapUsage数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/swapUsage/", method = RequestMethod.POST)
	public @ResponseBody NagiosSwapUsageDTO swapUsage(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosSwapUsage(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/processes/")
	public String processesPage() {
		return "monitor/processes";
	}

	/**
	 * AJAX请求TotalProcesses数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/processes/", method = RequestMethod.POST)
	public @ResponseBody NagiosTotalProcessesDTO processes(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosTotalProcesses(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/diskIO/")
	public String diskIOPage() {
		return "monitor/diskIO";
	}

	/**
	 * AJAX请求DiskIO数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/diskIO/", method = RequestMethod.POST)
	public @ResponseBody NagiosDiskIODTO diskIO(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosDiskIO(ipaddress, startDate, endDate);
	}

	@RequestMapping(value = "/cpu/")
	public String cpuPage() {
		return "monitor/cpu";
	}

	/**
	 * AJAX请求CPU Load数据
	 * 
	 * @param ipaddress
	 *            监控IP地址
	 * @param startDate
	 *            监控开始时间
	 * @param endDate
	 *            监控结束时间
	 * @return
	 */
	@RequestMapping(value = "/cpu/", method = RequestMethod.POST)
	public @ResponseBody NagiosCPULoadDTO cpu(@RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
		return service.getNagiosCPULoad(ipaddress, startDate, endDate);
	}
}
