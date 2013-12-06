package com.sobey.switches.singletontest;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.data.TestData;
import com.sobey.switches.script.GenerateScript;
import com.sobey.switches.webservice.response.dto.ESGParameter;

/**
 * junit貌似无法启动,故考虑在main中启动createVlan的测试方法.
 * 
 * @author Administrator
 * 
 */
public class CreateESGTest extends PropertiesAbstract {

	public static void main(String[] args) {

		ESGParameter esgParameter = TestData.randomESGParameter();

		String command = GenerateScript.generateCreateESGScript(esgParameter.getAclNumber(), esgParameter.getVlanId(),
				esgParameter.getDesc(), esgParameter.getPermits(), esgParameter.getDenys());

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, command);

	}

}
