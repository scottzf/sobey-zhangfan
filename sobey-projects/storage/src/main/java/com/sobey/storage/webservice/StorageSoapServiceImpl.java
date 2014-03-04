package com.sobey.storage.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.core.utils.JschUtil;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.script.GenerateScript;
import com.sobey.storage.webservice.response.dto.NetAppParameter;
import com.sobey.storage.webservice.response.result.WSResult;

@WebService(serviceName = "StorageSoapService", endpointInterface = "com.sobey.storage.webservice.StorageSoapService", targetNamespace = WsConstants.NS)
public class StorageSoapServiceImpl implements StorageSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader STORAGE_LOADER = new PropertiesLoader("classpath:/storage.properties");

	/* netapp controller登录 */
	protected static final String STORAGE_IP = STORAGE_LOADER.getProperty("STORAGE_IP");
	protected static final String STORAGE_USERNAME = STORAGE_LOADER.getProperty("STORAGE_USERNAME");
	protected static final String STORAGE_PASSWORD = STORAGE_LOADER.getProperty("STORAGE_PASSWORD");

	@Override
	public WSResult createEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter) {

		String command = GenerateScript.generateCreateEs3Script(netAppParameter);

		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter) {

		String command = GenerateScript.generateDeleteEs3Script(netAppParameter);

		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult mountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter) {

		String command = GenerateScript.generateMountEs3Script(netAppParameter);

		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult umountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter) {

		String command = GenerateScript.generateUmountEs3Script(netAppParameter);

		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult remountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter) {

		String command = GenerateScript.generateRemountEs3Script(netAppParameter);

		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

}
