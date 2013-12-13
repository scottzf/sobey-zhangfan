package com.sobey.firewall.singletontest;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.script.GenerateScript;
import com.sobey.firewall.webservice.response.dto.EIPParameter;

/**
 * junit貌似无法启动,故考虑在main中启动CreateEIP的测试方法.
 * 
 * @author Administrator
 * 
 */
public class CreateEIPTest extends PropertiesAbstract {

	public static void main(String[] args) {

		EIPParameter parameter = TestData.randomEIPParameter();

		List<String> memberList = Lists.newArrayList();

		String command = GenerateScript.generateCreateEIPScript(parameter, memberList);
		System.out.println(command);

		TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);
	}

}
