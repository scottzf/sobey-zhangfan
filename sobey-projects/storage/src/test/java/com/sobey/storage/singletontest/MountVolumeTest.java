package com.sobey.storage.singletontest;

import com.sobey.core.utils.JschUtil;
import com.sobey.storage.data.TestData;
import com.sobey.storage.script.GenerateScript;
import com.sobey.storage.webservice.response.dto.NetAppParameter;

/**
 * junit貌似无法启动,故考虑在main中启动MountVolume的测试方法.
 * 
 * @author Administrator
 * 
 */
public class MountVolumeTest extends PropertiesAbstract {

	public static void main(String[] args) {

		NetAppParameter netAppParameter = TestData.randomNetAppParameter();

		String command = GenerateScript.generateMountEs3Script(netAppParameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

	}

}
