package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sobey.api.service.NagiosService;
import com.sobey.generate.nagios.NagiosPingDTO;

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
	 * 刷新Ping页面
	 */
	@RequestMapping(value = "/ping/", method = RequestMethod.POST)
	public @ResponseBody NagiosPingDTO ping() {

		return service.getNagiosPing("172.20.34.1", null, null);
	}

}
