package com.sobey.instance.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.data.TestData;
import com.sobey.instance.service.DiskService;
import com.sobey.instance.service.FolderService;
import com.sobey.instance.service.VMRCService;
import com.sobey.instance.webservice.response.dto.VMRCDTO;
import com.sobey.instance.webservice.response.result.DTOResult;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMDiskTest {

	@Autowired
	private DiskService service;

	@Autowired
	private VMRCService vmrcService;

	@Autowired
	private FolderService folderService;

	@Test
	public void VMRC() {
		DTOResult<VMRCDTO> result = vmrcService.connectVMRC("Tenants-IGSnaJvT-192.168.1.2", "成都核心数据中心");
		System.out.println(result.getDto());
	}

	@Test
	public void createFolder() {
		folderService.createFolder(DataCenterEnum.成都核心数据中心.toString(), "Tenants-2", "vm");
	}

	@Test
	public void moveVM() {
		folderService.moveVM(DataCenterEnum.成都核心数据中心.toString(), "Tenants-r44klzKq-10.2.253.60", "租户");
	}

	@Test
	public void createVMDisk() {
		System.out.println(service.createVMDisk(TestData.randomVMDiskParameter()).getMessage());
	}

	@Test
	public void deleteVMDisk() {
		System.out.println(service.deleteVMDisk(TestData.randomVMDiskParameter()).getMessage());
	}

}
