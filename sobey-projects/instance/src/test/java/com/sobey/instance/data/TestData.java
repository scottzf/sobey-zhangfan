package com.sobey.instance.data;

import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;

public class TestData {

	public static CloneVMParameter randomCloneVMParameter() {

		CloneVMParameter parameter = new CloneVMParameter();

		parameter.setDescription("这个一个API测试程序");
		parameter.setGateway("10.10.2.1");
		parameter.setIpaddress("10.10.2.81");
		parameter.setSubNetMask("255.255.255.0");
		parameter.setvMName("liukai");
		parameter.setvMSUserName("徽州");

		// Linux
		parameter.setvMTemplateName("CnetOS6.5");
		parameter.setvMTemplateOS("Linux");

		// Windows
		// parameter.setvMTemplateName("WinServer2008R2");
		// parameter.setvMTemplateOS("Windows");
		return parameter;
	}

	public static DestroyVMParameter randomDestroyVMParameter() {

		DestroyVMParameter parameter = new DestroyVMParameter();
		parameter.setvMName("liukai");
		return parameter;
	}

	public static ReconfigVMParameter randomReconfigVMParameter() {
		ReconfigVMParameter parameter = new ReconfigVMParameter();
		parameter.setvMName("liukai");
		parameter.setcPUNumber(4);
		parameter.setMemoryMB(4096L);
		return parameter;
	}

	public static PowerVMParameter randomPowerVMParameter() {
		PowerVMParameter parameter = new PowerVMParameter();
		parameter.setvMName("liukai");
		parameter.setPowerOperation("poweroff");
		return parameter;
	}

}
