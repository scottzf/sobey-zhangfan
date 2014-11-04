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
import com.sobey.storage.webservice.response.dto.Es3SizeParameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
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
	public void CreateVolume() {
		CreateEs3Parameter parameter = TestData.randomCreateEs3Parameter();
		WSResult result = service.createEs3(parameter);
		System.out.println(result.getCode());
		System.out.println(result.getMessage());

	}

	@Test
	public void DeleteVolume() {
		DeleteEs3Parameter parameter = TestData.randomDeleteEs3Parameter();
		WSResult result = service.deleteEs3(parameter);
		System.out.println(result.getCode());
		System.out.println(result.getMessage());
	}

	@Test
	public void modifyVolumeRule() {
		ModifytEs3RuleParameter parameter = TestData.randomModifytEs3RuleParameter();
		WSResult result = service.modifyEs3Rule(parameter);
		System.out.println(result.getCode());
		System.out.println(result.getMessage());
	}

	@Test
	public void MountVolume() throws IOException {
		MountEs3Parameter parameter = TestData.randomMountEs3Parameter();
		String command = service.mountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"/mnt/123 is busy or already mounted"的错误,表示已经被挂载,通过对比是否包含"already mounted"来判断他是否出错
		// 文本会有类似"mkdir: cannot create directory"的错误,表示没有这个volume,通过对比是否包含"mkdir: cannot create directory"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.mount).getMessage());
		// assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.mount).getCode(), WSResult.SYSTEM_ERROR);
	}

	@Test
	public void UmountVolume() throws IOException {
		UmountEs3Parameter parameter = TestData.randomUmountEs3Parameter();
		String command = service.umountEs3(parameter);
		JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, FILE_PATH);
		// 文本会有类似"umount: /mnt/123: not mounted"的错误,表示未挂载所以无法卸载,通过对比是否包含"not mounted"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		System.out.println(TerminalResultHandle.ResultHandle(result, MethodEnum.umount).getMessage());
		// assertEquals(TerminalResultHandle.ResultHandle(result, MethodEnum.umount).getCode(), WSResult.SYSTEM_ERROR);
	}

	@Test
	public void VolumeSize() {

		Es3SizeParameter parameter = TestData.randomEs3SizeParameter();
		System.out.println(service.getEs3SizeTotal(parameter));
		System.out.println(service.getEs3SizeUsed(parameter));
	}

}
