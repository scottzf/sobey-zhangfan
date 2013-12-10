package com.sobey.firewall.singletontest;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.script.GenerateScript;
import com.sobey.firewall.webservice.response.dto.EIPParameter;

/**
 * junit貌似无法启动,故考虑在main中启动DeleteEIP的测试方法.
 * 
 * @author Administrator
 * 
 */
public class DeleteEIPTest extends PropertiesAbstract {

	public static void main(String[] args) {

		EIPParameter parameter = TestData.randomEIPParameter();

		List<String> memberList = Lists.newArrayList();

		String command = GenerateScript.generateDeleteEIPScript(parameter, memberList);
		System.out.println(command);

		// TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);
	}

}
