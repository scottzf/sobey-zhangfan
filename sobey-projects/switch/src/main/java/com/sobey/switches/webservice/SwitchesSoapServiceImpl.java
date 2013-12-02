package com.sobey.switches.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.webservice.response.dto.ESGParameter;
import com.sobey.switches.webservice.response.dto.VlanParameter;
import com.sobey.switches.webservice.response.result.WSResult;
import com.sobey.switches.webservice.script.GenerateScript;

@WebService(serviceName = "SwitchesService", endpointInterface = "com.sobey.switches.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
public class SwitchesSoapServiceImpl implements SwitchesSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	public static PropertiesLoader TELNET_LOADER = new PropertiesLoader("classpath:/telnet.properties");

	private static final String ip = TELNET_LOADER.getProperty("TELENT_IP");
	private static final String username = TELNET_LOADER.getProperty("TELENT_USERNAME");
	private static final String password = TELNET_LOADER.getProperty("TELENT_PASSWORD");

	@Override
	public WSResult createVlanBySwtich(@WebParam(name = "vlanParameter") VlanParameter vlanParameter) {

		String command = GenerateScript.generateCreateVlanScript(vlanParameter.getVlanId(), vlanParameter.getGateway(),
				vlanParameter.getNetMask());

		System.err.println(command);

		TelnetUtil.execCommand(ip, username, password, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteVlanBySwtich(@WebParam(name = "vlanId") Integer vlanId) {

		String command = GenerateScript.generateDeleteVlanScript(vlanId);

		System.err.println(command);

		TelnetUtil.execCommand(ip, username, password, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult createESGBySwtich(@WebParam(name = "esgParameter") ESGParameter esgParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteESGBySwtich(@WebParam(name = "aclNumber") Integer aclNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
