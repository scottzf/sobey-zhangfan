package com.sobey.api.test;

import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.service.InstanceService;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;

/**
 * 针对VM的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMTest extends TestCase {

	@Autowired
	private InstanceService service;

	private static String DATACENTER = "XA";

	@Test
	public void desoroyVM() {
		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setVMName("liukai");
		destroyVMParameter.setDatacenter(DATACENTER);
		assertEquals(service.desoroyVM(destroyVMParameter).getCode(), "0");
	}

	@Test
	public void cloneVM() {
		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setDescription("这个一个API测试程序");
		cloneVMParameter.setDatacenter(DATACENTER);
		cloneVMParameter.setGateway("10.10.2.1");
		cloneVMParameter.setIpaddress("10.10.2.90");
		cloneVMParameter.setSubNetMask("255.255.255.0");
		cloneVMParameter.setVMName("liukai");
		cloneVMParameter.setVMSUserName("cmop");
		cloneVMParameter.setVMTemplateName("CnetOSTmp");
		cloneVMParameter.setVMTemplateOS("Linux");
		assertEquals(service.cloneVM(cloneVMParameter).getCode(), "0");
	}

	@Test
	public void powerVM() {
		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName("liukai");
		powerVMParameter.setDatacenter(DATACENTER);
		powerVMParameter.setPowerOperation("poweron");
		assertEquals(service.powerVM(powerVMParameter).getCode(), "0");
	}

	@Test
	public void reconfigVM() {
		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setVMName("liukai");
		reconfigVMParameter.setCPUNumber(4);
		reconfigVMParameter.setMemoryMB(4096L);
		reconfigVMParameter.setDatacenter(DATACENTER);
		assertEquals(service.reconfigVM(reconfigVMParameter).getCode(), "0");
	}

	@Test
	public void relationVMTest() {
		for (Entry<String, String> entry : service.relationVM(DATACENTER).entrySet()) {
			System.out.println(entry.getValue() + " : " + entry.getKey());
		}
	}

	@Test
	public void sync() {
		service.syncVM(DATACENTER);
	}

}
