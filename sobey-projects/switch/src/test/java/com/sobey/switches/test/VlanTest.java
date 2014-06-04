package com.sobey.switches.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.PbulicProperties;
import com.sobey.switches.data.TestData;
import com.sobey.switches.service.SwitchService;
import com.sobey.switches.webservice.response.dto.VlanParameter;

/**
 * Vlan单元测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VlanTest extends TestCase implements PbulicProperties {

	@Autowired
	private SwitchService service;

	/**
	 * access配置文件中,不同交换机之间的分割符号.
	 */
	private static final String SEPARATOR_CHARS = ",";

	@Test
	public void createVlanInCore() throws IOException {

		VlanParameter parameter = TestData.randomVlanParameter();

		String command = service.createVlanOnCoreLayer(parameter.getVlanId(), parameter.getGateway(),
				parameter.getNetMask());

		System.out.println(command);

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));

		System.out.println(result);

		assertTrue(result.contains("Error: The IP address you entered overlaps with another interface!"));
	}

	@Test
	public void deleteVlanInCore() throws IOException {

		Integer vlanId = 80; // 临时数据

		String command = service.deleteVlanOnAccessLayer(vlanId);

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, FILE_PATH);

		// 文本会有类似"VLAN(s) do(es) not exist."的错误,表示vlan不存在,通过对比是否包含"VLAN(s) do(es) not exist"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.out.println(result);
		assertTrue(result.contains("VLAN(s) do(es) not exist"));

	}

	@Test
	public void createVlanInAccess() throws IOException, InterruptedException {

		VlanParameter parameter = TestData.randomVlanParameter();

		String command = service.createVlanOnAccessLayer(parameter.getVlanId(), parameter.getGateway(),
				parameter.getNetMask());

		System.out.println(command);

		String[] ips = StringUtils.split(ACCESS_IP, SEPARATOR_CHARS);
		String[] names = StringUtils.split(ACCESS_USERNAME, SEPARATOR_CHARS);
		String[] passwords = StringUtils.split(ACCESS_PASSWORD, SEPARATOR_CHARS);

		for (int i = 0; i < ips.length; i++) {

			// Format:接入层交换机IP地址 - VlanId - index
			String filePath = getFilePath(ips[i] + "-" + parameter.getVlanId().toString() + "-" + i);

			TelnetUtil.execCommand(ips[i], names[i], passwords[i], command, filePath);

			String resultStr = FileUtils.readFileToString(new File(filePath));
			System.out.println(resultStr);

			// 每次执行完成,暂停2s,避免nonsocket的错误
			Thread.sleep(2000);
		}

	}

	@Test
	public void deleteVlanInAccess() throws IOException, InterruptedException {

		Integer vlanId = 80; // 临时数据

		String command = service.deleteVlanOnAccessLayer(vlanId);

		System.out.println(command);

		String[] ips = StringUtils.split(ACCESS_IP, SEPARATOR_CHARS);
		String[] names = StringUtils.split(ACCESS_USERNAME, SEPARATOR_CHARS);
		String[] passwords = StringUtils.split(ACCESS_PASSWORD, SEPARATOR_CHARS);

		for (int i = 0; i < ips.length; i++) {

			// Format:接入层交换机IP地址 - VlanId - index
			String filePath = getFilePath(ips[i] + "-" + vlanId + "-" + i);

			TelnetUtil.execCommand(ips[i], names[i], passwords[i], command, filePath);

			String resultStr = FileUtils.readFileToString(new File(filePath));
			System.out.println(resultStr);

			// 文本会有类似"VLAN(s) do(es) not exist."的错误,表示vlan不存在,通过对比是否包含"VLAN(s) do(es) not exist"来判断他是否出错
			assertTrue(resultStr.contains("VLAN(s) do(es) not exist"));

			// 每次执行完成,暂停2s,避免nonsocket的错误
			Thread.sleep(2000);
		}

	}

	/**
	 * 获得文件的相对路径,文件名自定义.
	 * 
	 * @param input
	 * @return
	 */
	private static String getFilePath(String input) {
		return "logs/" + input + ".txt";
	}

}
