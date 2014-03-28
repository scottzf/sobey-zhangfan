package com.sobey.switches.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
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

	private static String FILE_PATH = "logs/TerminalInfo.txt";

	@Test
	public void createVlan() {

		VlanParameter parameter = TestData.randomVlanParameter();

		String accessCommand = service.createVlanOnAccessLayer(parameter.getVlanId(), parameter.getGateway(),
				parameter.getNetMask());
		// System.out.println(accessCommand);

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand, FILE_PATH);

	}

	@Test
	public void deleteVlan() throws IOException {

		Integer vlanId = 80; // 临时数据

		String accessCommand = service.deleteVlanOnAccessLayer(vlanId);

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand, FILE_PATH);

		// 文本会有类似"VLAN(s) do(es) not exist."的错误,表示vlan不存在,通过对比是否包含"VLAN(s) do(es) not exist"来判断他是否出错
		String result = FileUtils.readFileToString(new File(FILE_PATH));
		assertTrue(!result.contains("VLAN(s) do(es) not exist"));

	}

}
