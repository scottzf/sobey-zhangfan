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
import com.sobey.switches.webservice.response.dto.VlanParameter;

/**
 * Vlan单元测试
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VlanTest extends PropertiesAbstract {

	@Autowired
	private SwitchService service;

	@Test
	// @Ignore
	public void createVlan() {

		VlanParameter parameter = TestData.randomVlanParameter();

		String accessCommand = service.createVlanOnAccessLayer(parameter.getVlanId(), parameter.getGateway(),
				parameter.getNetMask());

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

	}

	@Test
	@Ignore
	public void deleteVlan() {

		Integer vlanId = 80; // 临时数据

		String accessCommand = service.deleteVlanOnAccessLayer(vlanId);

		TelnetUtil.execCommand(ACCESS_IP, ACCESS_USERNAME, ACCESS_PASSWORD, accessCommand);

	}

}
