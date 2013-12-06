package com.sobey.switches.singletontest;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.script.GenerateScript;

/**
 * junit貌似无法启动,故考虑在main中启动DeleteVlan的测试方法.
 * 
 * @author Administrator
 * 
 */
public class DeleteVlanTest extends PropertiesAbstract {

	public static void main(String[] args) {

		Integer vlanId = 80;

		String accessCommand = GenerateScript.generateDeleteVlanScriptOnAccessLayer(vlanId);
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		/* 目前测试环境只有一台交换机,故暂时屏蔽,否则会报错 : Socket operation on nonsocket: connect */
		// String coreCommand = GenerateScript.generateDeleteVlanScriptOnCoreLayer(vlanId);
		// telnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);
	}

}
