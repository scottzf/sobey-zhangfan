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
import com.sobey.storage.PbulicProperties;
import com.sobey.storage.constans.MethodEnum;
import com.sobey.storage.data.TestData;
import com.sobey.storage.service.NetAppService;
import com.sobey.storage.webservice.TerminalResultHandle;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;
import com.sobey.storage.webservice.response.result.WSResult;

/**
 * storage测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StorageTest extends TestCase implements PbulicProperties {

	@Autowired
	private NetAppService service;

	private static String FILE_PATH = "logs/TerminalInfo.txt";

	@Test
	public void CreateVolume() throws IOException {

		CreateEs3Parameter parameter = TestData.randomCreateEs3Parameter();
		String command = service.createEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"vol create: Volume 'liukai' already exists"的错误,通过对比是否包含"already exists"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		// System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.create).getMessage());
		assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.create).getCode(), WSResult.SYSTEM_ERROR);
	}

	@Test
	public void DeleteVolume() throws IOException {
		DeleteEs3Parameter parameter = TestData.randomDeleteEs3Parameter();
		String command = service.deleteEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"vol destroy: No volume named 'liukai' exists"的错误,通过对比是否包含" No volume named"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		// System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.delete).getMessage());
		assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.delete).getCode(), WSResult.SYSTEM_ERROR);

	}

	@Test
	public void MountVolume() throws IOException {
		MountEs3Parameter parameter = TestData.randomMountEs3Parameter();
		String command = service.mountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"/mnt/123 is busy or already mounted"的错误,表示已经被挂载,通过对比是否包含"already mounted"来判断他是否出错
		// 文本会有类似"mkdir: cannot create directory"的错误,表示没有这个volume,通过对比是否包含"mkdir: cannot create directory"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		// System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.mount).getMessage());
		assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.mount).getCode(), WSResult.SYSTEM_ERROR);
	}

	@Test
	public void UmountVolume() throws IOException {
		UmountEs3Parameter parameter = TestData.randomUmountEs3Parameter();
		String command = service.umountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);
		// 文本会有类似"umount: /mnt/123: not mounted"的错误,表示未挂载所以无法卸载,通过对比是否包含"not mounted"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		// System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.umount).getMessage());
		assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.umount).getCode(), WSResult.SYSTEM_ERROR);
	}

	@Test
	public void RemountVolume() throws IOException {
		RemountEs3Parameter parameter = TestData.randomRemountEs3Parameter();
		String command = service.remountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);
		// 文本会有类似"exportfs [Line 1]: no such directory, /vol/liukai not exported"的错误,表示没有这个volume所以无法卸载,通过对比是否包含"not exported"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		// System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.remount).getMessage());
		assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.remount).getCode(), WSResult.SYSTEM_ERROR);
	}

}
