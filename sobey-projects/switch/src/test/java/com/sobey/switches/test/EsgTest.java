package com.sobey.switches.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.PropertiesAbstract;
import com.sobey.switches.data.TestData;
import com.sobey.switches.service.SwitchService;
import com.sobey.switches.webservice.response.dto.ESGParameter;

/**
 * Esg单元测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EsgTest extends PropertiesAbstract {

	@Autowired
	private SwitchService service;

	@Test
	// @Ignore
	public void createEsg() {

		ESGParameter parameter = TestData.randomESGParameter();

		String command = service.createEsg(parameter.getAclNumber(), parameter.getVlanId(), parameter.getDesc(),
				parameter.getPermits(), parameter.getDenys());

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, command);
	}

	@Test
	@Ignore
	public void deleteEsg() {

		Integer aclNumber = 3000; // 临时数据

		String command = service.deleteEsg(aclNumber);

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command);
	}

}
