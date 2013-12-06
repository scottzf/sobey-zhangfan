package com.sobey.switches.singletontest;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.data.TestData;
import com.sobey.switches.script.GenerateScript;
import com.sobey.switches.webservice.response.dto.VlanParameter;

/**
 * junit貌似无法启动,故考虑在main中启动CreateVlan的测试方法.
 * 
 * @author Administrator
 * 
 */
public class CreateVlanTest extends PropertiesAbstract {

	public static void main(String[] args) {

		VlanParameter vlanParameter = TestData.randomVlanParameter();
		String accessCommand = GenerateScript.generateCreateVlanScriptOnAccessLayer(vlanParameter.getVlanId(),
				vlanParameter.getGateway(), vlanParameter.getNetMask());
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

		/* 目前测试环境只有一台交换机,故暂时屏蔽,否则会报错 : Socket operation on nonsocket: connect */
		// String coreCommand = GenerateScript.generateCreateVlanScriptOnCoreLayer(vlanParameter.getVlanId(),
		// vlanParameter.getGateway(), vlanParameter.getNetMask());
		// telnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, coreCommand);
	}

}
