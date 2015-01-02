package com.sobey.instance.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.data.TestData;
import com.sobey.instance.service.DiskService;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMDiskTest {

	@Autowired
	private DiskService service;

	@Test
	public void createVMDisk() {
		System.out.println(service.createVMDisk(TestData.randomVMDiskParameter()).getMessage());
	}

	@Test
	public void deleteVMDisk() {
		System.out.println(service.deleteVMDisk(TestData.randomVMDiskParameter()).getMessage());
	}

}
