package com.sobey.instance.data;

import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.constans.PowerOperationEnum;
import com.sobey.instance.webservice.response.dto.BindingPortGroupParameter;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.CreatePortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreateStandardSwitchParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.VMDiskParameter;

public class TestData {

	public static CloneVMParameter randomCloneVMParameter() {

		CloneVMParameter parameter = new CloneVMParameter();

		parameter.setDescription("这个一个API测试程序");
		parameter.setGateway("172.16.10.0");
		parameter.setIpaddress("172.16.10.15");
		parameter.setSubNetMask("255.255.255.0");
		parameter.setVmName("liukai");
		parameter.setResourcePool("resgroup-42");
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());

		// Linux
		// parameter.setVmTemplateName("CnetOS6.5");
		// parameter.setVmTemplateOS("Linux");

		// Windows
		parameter.setVmTemplateName("Win2008");
		parameter.setVmTemplateOS("Windows");
		return parameter;
	}

	public static DestroyVMParameter randomDestroyVMParameter() {

		DestroyVMParameter parameter = new DestroyVMParameter();
		parameter.setVmName("liukai");
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		return parameter;
	}

	public static ReconfigVMParameter randomReconfigVMParameter() {
		ReconfigVMParameter parameter = new ReconfigVMParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setVmName("liukai");
		parameter.setCpuNumber(2);
		parameter.setMemoryMB(4096L);
		return parameter;
	}

	public static PowerVMParameter randomPowerVMParameter() {
		PowerVMParameter parameter = new PowerVMParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setVmName("liukai");
		parameter.setPowerOperation(PowerOperationEnum.poweron.toString());
		return parameter;
	}

	public static CreateStandardSwitchParameter randomCreateStandardSwitchParameter() {
		CreateStandardSwitchParameter parameter = new CreateStandardSwitchParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setHostName("172.16.2.31");
		parameter.setVirtualSwitchName("vSwtich1");
		return parameter;
	}

	public static CreatePortGroupParameter randomCreatePortGroupParameter() {
		CreatePortGroupParameter parameter = new CreatePortGroupParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setHostName("172.16.2.31");
		parameter.setVirtualSwitchName("vSwtich1");
		parameter.setVlanId(15);
		parameter.setPortGroupName("testpg2");
		return parameter;
	}

	public static BindingPortGroupParameter randomBindingPortGroupParameter() {
		BindingPortGroupParameter parameter = new BindingPortGroupParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setVmName("liukai");
		parameter.setPortGroupName("testpg2");
		return parameter;
	}

	public static VMDiskParameter randomVMDiskParameter() {
		VMDiskParameter parameter = new VMDiskParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setVmName("liukai");
		parameter.setDiskName("testDB2");
		parameter.setDiskGB("300");
		return parameter;
	}

}
