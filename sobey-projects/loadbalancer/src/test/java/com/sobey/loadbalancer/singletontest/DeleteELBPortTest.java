package com.sobey.loadbalancer.singletontest;

import com.sobey.loadbalancer.data.TestData;
import com.sobey.loadbalancer.script.GenerateScript;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;

/**
 * junit貌似无法启动,故考虑在main中启动DeleteELBPort的测试方法.
 * 
 * @author Administrator
 * 
 */
public class DeleteELBPortTest extends PropertiesAbstract {

	public static void main(String[] args) {

		ELBParameter parameter = TestData.randomELBParameter();

		String command = GenerateScript.generateDeleteELBPortScript(parameter);
		System.out.println(command);

		// TelnetUtil.execCommand(DNS_IP, DNS_USERNAME, DNS_PASSWORD, command);
	}

}
