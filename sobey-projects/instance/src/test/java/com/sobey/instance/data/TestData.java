package com.sobey.instance.data;

import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;

public class TestData {

	public static CloneVMParameter randomCloneVMParameter() {

		CloneVMParameter parameter = new CloneVMParameter();

		parameter.setDescription("这个一个API测试程序");
		parameter.setGateway("10.10.2.1");
		parameter.setIpaddress("10.10.2.93");
		parameter.setSubNetMask("255.255.255.0");
		parameter.setvMName("oncommand");
		parameter.setvMSUserName("Sobey");
		parameter.setVlanId(2);
		parameter.setDatacenter(DataCenterEnum.西安核心数据中心.toString());

		// Linux
		// parameter.setvMTemplateName("CnetOS6.5");
		// parameter.setvMTemplateOS("Linux");

		// Windows
		parameter.setvMTemplateName("WinServer2008R2");
		parameter.setvMTemplateOS("Windows");
		return parameter;
	}

	public static DestroyVMParameter randomDestroyVMParameter() {

		DestroyVMParameter parameter = new DestroyVMParameter();
		parameter.setvMName("liukai");
		parameter.setDatacenter(DataCenterEnum.西安核心数据中心.toString());
		return parameter;
	}

	public static ReconfigVMParameter randomReconfigVMParameter() {
		ReconfigVMParameter parameter = new ReconfigVMParameter();
		parameter.setDatacenter(DataCenterEnum.西安核心数据中心.toString());
		parameter.setvMName("liukai");
		parameter.setcPUNumber(4);
		parameter.setMemoryMB(4096L);
		return parameter;
	}

	public static PowerVMParameter randomPowerVMParameter() {
		PowerVMParameter parameter = new PowerVMParameter();
		parameter.setDatacenter(DataCenterEnum.西安核心数据中心.toString());
		parameter.setvMName("liukai");
		parameter.setPowerOperation("poweroff");
		return parameter;
	}

}
