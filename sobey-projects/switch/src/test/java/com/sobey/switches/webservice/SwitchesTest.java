package com.sobey.switches.webservice;

import org.junit.Ignore;
import org.junit.Test;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.data.TestData;
import com.sobey.switches.script.GenerateScript;
import com.sobey.switches.webservice.response.dto.VlanParameter;

public class SwitchesTest {

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

	@Test
	@Ignore
	public void createVlan() {

		VlanParameter vlanParameter = TestData.randomVlanParameter();
		String accessCommand = GenerateScript.generateCreateVlanScriptOnAccessLayer(vlanParameter.getVlanId(),
				vlanParameter.getGateway(), vlanParameter.getNetMask());
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		String coreCommand = GenerateScript.generateCreateVlanScriptOnCoreLayer(vlanParameter.getVlanId(),
				vlanParameter.getGateway(), vlanParameter.getNetMask());
		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);
	}

	@Test
	public void deleteVlan() {

		Integer vlanId = 80;

		String accessCommand = GenerateScript.generateDeleteVlanScriptOnAccessLayer(vlanId);
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		String coreCommand = GenerateScript.generateDeleteVlanScriptOnCoreLayer(vlanId);
		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);
	}

}
