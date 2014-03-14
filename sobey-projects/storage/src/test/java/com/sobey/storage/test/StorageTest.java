package com.sobey.storage.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.JschUtil;
import com.sobey.storage.PropertiesAbstract;
import com.sobey.storage.data.TestData;
import com.sobey.storage.script.NetAppService;
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

	@Autowired
	private NetAppService service;

	@Test
	// @Ignore
	public void CreateVolume() {
		CreateEs3Parameter parameter = TestData.randomCreateEs3Parameter();
		String command = service.createEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	@Ignore
	public void DeleteVolume() {
		DeleteEs3Parameter parameter = TestData.randomDeleteEs3Parameter();
		String command = service.deleteEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	@Ignore
	public void MountVolume() {
		MountEs3Parameter parameter = TestData.randomMountEs3Parameter();
		String command = service.mountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	@Ignore
	public void UmountVolume() {
		UmountEs3Parameter parameter = TestData.randomUmountEs3Parameter();
		String command = service.umountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

	@Test
	@Ignore
	public void RemountVolume() {
		RemountEs3Parameter parameter = TestData.randomRemountEs3Parameter();
		String command = service.remountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);
	}

}
