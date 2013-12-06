package com.sobey.switches.singletontest;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.script.GenerateScript;

/**
 * junit貌似无法启动,故考虑在main中启动DeleteESG的测试方法.
 * 
 * @author Administrator
 * 
 */
public class DeleteESGTest extends PropertiesAbstract {

	public static void main(String[] args) {

		Integer aclNumber = 3000;

		String command = GenerateScript.generateDeleteESGScript(aclNumber);

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, command);

	}

}
