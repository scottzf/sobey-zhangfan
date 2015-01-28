package com.sobey.switches.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.switches.PbulicProperties;
import com.sobey.switches.data.TestData;
import com.sobey.switches.service.SwitchService;

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

	@Test
	public void createPolicyInSwitch() {
		service.createSinglePolicy(TestData.randomSwitchPolicyParameter());
	}

}
