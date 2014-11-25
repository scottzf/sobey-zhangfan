package com.sobey.switches.test;

import java.io.File;
import java.io.IOException;

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
import com.sobey.switches.webservice.TerminalResultHandle;
import com.sobey.switches.webservice.response.dto.ESGParameter;

/**
 * Esg单元测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EsgTest implements PbulicProperties {

	@Autowired
	private SwitchService service;

	@Test
	public void createEsg() throws IOException {

		ESGParameter parameter = TestData.randomESGParameter();

		String command = service.createEsg(parameter.getAclNumber(), parameter.getVlanId(), parameter.getDesc(),
				parameter.getPermits(), parameter.getDenys());

		System.out.println(command);
		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));
		if (result.contains(TerminalResultHandle.CREATEACL_ERROR)) {
			System.out.println("---------------------------");
			Integer aclNumber = 3000; // 临时数据
			TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, service.deleteEsg(aclNumber), FILE_PATH);

		}
		System.err.println(result);
	}

	@Test
	public void deleteEsg() throws IOException {

		Integer aclNumber = 3001; // 临时数据

		String command = service.deleteEsg(aclNumber);

		System.out.println(command);

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, FILE_PATH);

		String result = FileUtils.readFileToString(new File(FILE_PATH));
		System.err.println(result);
	}

}
