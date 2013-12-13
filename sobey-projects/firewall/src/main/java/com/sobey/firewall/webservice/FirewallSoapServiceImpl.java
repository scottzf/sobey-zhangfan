package com.sobey.firewall.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.constans.WsConstants;
import com.sobey.firewall.script.GenerateScript;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;
import com.sobey.firewall.webservice.response.result.WSResult;

@WebService(serviceName = "FirewallSoapService", endpointInterface = "com.sobey.firewall.webservice.FirewallSoapService", targetNamespace = WsConstants.NS)
public class FirewallSoapServiceImpl implements FirewallSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader FIREWALL_LOADER = new PropertiesLoader("classpath:/firewall.properties");

	/* 防火墙登录 */
	protected static final String FIREWALL_IP = FIREWALL_LOADER.getProperty("FIREWALL_IP");
	protected static final String FIREWALL_USERNAME = FIREWALL_LOADER.getProperty("FIREWALL_USERNAME");
	protected static final String FIREWALL_PASSWORD = FIREWALL_LOADER.getProperty("FIREWALL_PASSWORD");

	@Override
	public WSResult createEIPByFirewall(@WebParam(name = "EIPParameter") EIPParameter parameter,
			@WebParam(name = "allPolicies") List<String> allPolicies) {

		String command = GenerateScript.generateCreateEIPScript(parameter, allPolicies);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteEIPByFirewall(@WebParam(name = "EIPParameter") EIPParameter parameter,
			@WebParam(name = "allPolicies") List<String> allPolicies) {

		String command = GenerateScript.generateDeleteEIPScript(parameter, allPolicies);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult createVPNUserByFirewall(@WebParam(name = "VPNUserParameter") VPNUserParameter parameter) {

		String command = GenerateScript.generateCreateVPNUserScript(parameter);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteVPNUserByFirewall(@WebParam(name = "VPNUserParameter") VPNUserParameter parameter) {

		String command = GenerateScript.generateDeleteVPNUserScript(parameter);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult changeVPNUserAccesssAddressByFirewall(
			@WebParam(name = "VPNUserParameter") VPNUserParameter parameter) {

		String command = GenerateScript.generateChangeAccesssAddressIntoVPNUserScript(parameter);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

}
