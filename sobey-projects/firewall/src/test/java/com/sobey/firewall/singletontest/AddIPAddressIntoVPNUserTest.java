package com.sobey.firewall.singletontest;

import com.sobey.firewall.constans.SymbolEnum;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.script.GenerateScript;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

/**
 * junit貌似无法启动,故考虑在main中启动AddIPAddressIntoVPNUser的测试方法.
 * 
 * @author Administrator
 * 
 */
public class AddIPAddressIntoVPNUserTest extends PropertiesAbstract {

	public static void main(String[] args) {

		VPNUserParameter parameter = TestData.randomVPNParameter();

		String command = GenerateScript.generateAddIPAddressIntoVPNUserScript(parameter,
				SymbolEnum.DEFAULT_SYMBOL.getName());
		System.out.println(command);

		// TelnetUtil.execCommand(FIREWALL_IP, FIREWALL_USERNAME, FIREWALL_PASSWORD, command);
	}

}
