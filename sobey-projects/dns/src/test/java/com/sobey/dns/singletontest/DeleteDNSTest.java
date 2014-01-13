package com.sobey.dns.singletontest;

import com.sobey.dns.data.TestData;
import com.sobey.dns.script.GenerateScript;
import com.sobey.dns.webservice.response.dto.DNSParameter;

/**
 * junit貌似无法启动,故考虑在main中启动DeleteDNS的测试方法.
 * 
 * @author Administrator
 * 
 */
public class DeleteDNSTest extends PropertiesAbstract {

	public static void main(String[] args) {

		DNSParameter parameter = TestData.randomDNSParameter();

		String command = GenerateScript.generateDeleteDNSScript(parameter);
		System.out.println(command);

		// TelnetUtil.execCommand(DNS_IP, DNS_USERNAME, DNS_PASSWORD, command);
	}

}
