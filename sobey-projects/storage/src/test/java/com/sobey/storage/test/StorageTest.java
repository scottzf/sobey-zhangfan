package com.sobey.storage.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.storage.PbulicProperties;
import com.sobey.storage.data.TestData;
import com.sobey.storage.service.NetAppService;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
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

}
