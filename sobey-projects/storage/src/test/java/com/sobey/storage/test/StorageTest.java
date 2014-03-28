package com.sobey.storage.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.JschUtil;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.storage.data.TestData;
import com.sobey.storage.service.NetAppService;
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
public class StorageTest extends TestCase {

	@Autowired
	private NetAppService service;

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader STORAGE_LOADER = new PropertiesLoader("classpath:/storage.properties");

	/* netapp controller登录 */
	protected static final String STORAGE_IP = STORAGE_LOADER.getProperty("STORAGE_IP");
	protected static final String STORAGE_USERNAME = STORAGE_LOADER.getProperty("STORAGE_USERNAME");
	protected static final String STORAGE_PASSWORD = STORAGE_LOADER.getProperty("STORAGE_PASSWORD");

	private static String FILE_PATH = "logs/TerminalInfo.txt";

	// @Test
	public void CreateVolume() throws IOException {

		CreateEs3Parameter parameter = TestData.randomCreateEs3Parameter();
		String command = service.createEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"vol create: Volume 'liukai' already exists"的错误,通过对比是否包含"already exists"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		assertTrue(result.contains("already exists"));
	}

	// @Test
	public void DeleteVolume() throws IOException {
		DeleteEs3Parameter parameter = TestData.randomDeleteEs3Parameter();
		String command = service.deleteEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"vol destroy: No volume named 'liukai' exists"的错误,通过对比是否包含" No volume named"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		assertTrue(result.contains("No volume named"));

	}

	// @Test
	public void MountVolume() throws IOException {
		MountEs3Parameter parameter = TestData.randomMountEs3Parameter();
		String command = service.mountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"/mnt/123 is busy or already mounted"的错误,表示已经被挂载,通过对比是否包含"already mounted"来判断他是否出错
		// 文本会有类似"mkdir: cannot create directory"的错误,表示没有这个volume,通过对比是否包含"mkdir: cannot create directory"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		assertTrue(result.contains("mkdir: cannot create directory"));
		assertTrue(result.contains("already mounted"));
	}

	// @Test
	public void UmountVolume() throws IOException {
		UmountEs3Parameter parameter = TestData.randomUmountEs3Parameter();
		String command = service.umountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		// 文本会有类似"umount: /mnt/123: not mounted"的错误,表示未挂载所以无法卸载,通过对比是否包含"already mounted"来判断他是否出错
		assertTrue(result.contains("not mounted"));
	}

	@Test
	public void RemountVolume() throws IOException {
		RemountEs3Parameter parameter = TestData.randomRemountEs3Parameter();
		String command = service.remountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		// 文本会有类似"exportfs [Line 1]: no such directory, /vol/liukai not exported"的错误,表示没有这个volume所以无法卸载,通过对比是否包含"already mounted"来判断他是否出错
		assertTrue(result.contains("not mounted"));
	}

}
