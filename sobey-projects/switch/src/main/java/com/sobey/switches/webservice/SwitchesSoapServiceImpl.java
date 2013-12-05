package com.sobey.switches.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.script.GenerateScript;
import com.sobey.switches.webservice.response.dto.ESGParameter;
import com.sobey.switches.webservice.response.dto.VlanParameter;
import com.sobey.switches.webservice.response.result.WSResult;

@WebService(serviceName = "SwitchesService", endpointInterface = "com.sobey.switches.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
public class SwitchesSoapServiceImpl implements SwitchesSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	public static PropertiesLoader CORE_LOADER = new PropertiesLoader("classpath:/Core.properties");
	public static PropertiesLoader ACCESS_LOADER = new PropertiesLoader("classpath:/Access.properties");

	/* 核心交换机 */
	private static final String CORE_IP = CORE_LOADER.getProperty("CORE_IP");
	private static final String CORE_USERNAME = CORE_LOADER.getProperty("CORE_USERNAME");
	private static final String CORE_PASSWORD = CORE_LOADER.getProperty("CORE_PASSWORD");

	/* 接入层交换机,可能有数量不定的接入层交换机,待后续优化 */
	private static final String ACCESS_IP = ACCESS_LOADER.getProperty("ACCESS_IP");
	private static final String ACCESS_USERNAME = ACCESS_LOADER.getProperty("ACCESS_USERNAME");
	private static final String ACCESS_PASSWORD = ACCESS_LOADER.getProperty("ACCESS_PASSWORD");

	@Override
	public WSResult createVlanBySwtich(@WebParam(name = "vlanParameter") VlanParameter vlanParameter) {

		/*
		 * Vlan创建的顺序是先在若干个交换机上执行脚本,然后在一个核心交换机上执行脚本.
		 */

		String accessCommand = GenerateScript.generateCreateVlanScriptOnAccessLayer(vlanParameter.getVlanId(),
				vlanParameter.getGateway(), vlanParameter.getNetMask());
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		String coreCommand = GenerateScript.generateCreateVlanScriptOnCoreLayer(vlanParameter.getVlanId(),
				vlanParameter.getGateway(), vlanParameter.getNetMask());
		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteVlanBySwtich(@WebParam(name = "vlanId") Integer vlanId) {

		/*
		 * Vlan删除的顺序是先在若干个交换机上执行脚本,然后在一个核心交换机上执行脚本.
		 */

		String accessCommand = GenerateScript.generateDeleteVlanScriptOnAccessLayer(vlanId);
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		String coreCommand = GenerateScript.generateDeleteVlanScriptOnCoreLayer(vlanId);
		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);

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