package com.sobey.loadbalancer.webservice;

import javax.jws.WebParam;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.loadbalancer.script.GenerateScript;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

public class LoadbalancerSoapServiceImpl implements LoadbalancerSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader LOADBALANCER_LOADER = new PropertiesLoader("classpath:/LOADBALANCER.properties");

	/* LOADBALANCER登录 */
	protected static final String LOADBALANCER_IP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_IP");
	protected static final String LOADBALANCER_USERNAME = LOADBALANCER_LOADER.getProperty("LOADBALANCER_USERNAME");
	protected static final String LOADBALANCER_PASSWORD = LOADBALANCER_LOADER.getProperty("LOADBALANCER_PASSWORD");

	@Override
	public WSResult createELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		String command = GenerateScript.generateCreateELBScript(parameter);

		TelnetUtil.execCommand(LOADBALANCER_IP, LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		String command = GenerateScript.generateDeleteELBScript(parameter);

		TelnetUtil.execCommand(LOADBALANCER_IP, LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteELBPortByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		String command = GenerateScript.generateDeleteELBPortScript(parameter);

		TelnetUtil.execCommand(LOADBALANCER_IP, LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

}
