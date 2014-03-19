package com.sobey.instance.test;

import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.data.TestData;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMTest extends TestCase {

	@Autowired
	private VMService service;

	@Test
	public void cloneVM() {
		CloneVMParameter parameter = TestData.randomCloneVMParameter();
		assertTrue(service.cloneVM(parameter));
	}

	// @Test
	public void destroyVM() {
		DestroyVMParameter parameter = TestData.randomDestroyVMParameter();
		assertTrue(service.destroyVM(parameter));
	}

	// @Test
	public void reconfigVM() {
		ReconfigVMParameter parameter = TestData.randomReconfigVMParameter();
		assertTrue(service.reconfigVM(parameter));
	}

	// @Test
	public void powerVM() {
		PowerVMParameter parameter = TestData.randomPowerVMParameter();
		assertTrue(service.powerVM(parameter));
	}

	// @Test
	public void getVM() {
		RelationVMParameter parameter = service.getVMAndHostRelation();
		for (Entry<String, String> element : parameter.getRelationMaps().entrySet()) {
			System.out.println("VM:" + element.getKey());
			System.out.println("Host:" + element.getValue());
			System.out.println("************************");
		}

	}

}
