package com.sobey.storage.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.JschUtil;
import com.sobey.storage.PropertiesAbstract;
import com.sobey.storage.data.TestData;
import com.sobey.storage.script.GenerateScript;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;

/**
 * storage测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StorageTest extends PropertiesAbstract {

	@Test
	public void CreateVolume() {
		CreateEs3Parameter parameter = TestData.randomCreateEs3Parameter();
		String command = GenerateScript.generateCreateEs3Script(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	public void DeleteVolume() {
		DeleteEs3Parameter parameter = TestData.randomDeleteEs3Parameter();
		String command = GenerateScript.generateDeleteEs3Script(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	public void MountVolume() {
		MountEs3Parameter parameter = TestData.randomMountEs3Parameter();
		String command = GenerateScript.generateMountEs3Script(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	public void UmountVolume() {
		UmountEs3Parameter parameter = TestData.randomUmountEs3Parameter();
		String command = GenerateScript.generateUmountEs3Script(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	public void RemountVolume() {
		RemountEs3Parameter parameter = TestData.randomRemountEs3Parameter();
		String command = GenerateScript.generateRemountEs3Script(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

}
