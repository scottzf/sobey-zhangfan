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
import com.sobey.instance.webservice.response.dto.RunNetworkDeviceVMParameter;
import com.sobey.instance.webservice.response.dto.RunVMParameter;
import com.sobey.instance.webservice.response.dto.VMDiskParameter;

public class TestData {

	public static CloneVMParameter randomCloneVMParameter() {

		CloneVMParameter parameter = new CloneVMParameter();

		parameter.setDescription("CMOP");
		parameter.setVmName("random-1");
		parameter.setResourcePool("resgroup-133");
		parameter.setHostId("host-236");
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());

		// Linux
		parameter.setVmTemplateName("random-1");
		parameter.setVmTemplateOS("Linux");

		// Windows
		// parameter.setVmTemplateName("Windows 2008 R2 Mod");
		// parameter.setVmTemplateOS("Windows");
		return parameter;
	}

	public static CloneVMParameter randomCloneNetworkDeviceParameter() {

		CloneVMParameter parameter = new CloneVMParameter();

		parameter.setDescription("这个一个API测试程序");
		parameter.setVmName("Random2");
		parameter.setResourcePool("resgroup-133");
		parameter.setHostId("host-236");
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());

		// Linux
		parameter.setVmTemplateName("CnetOS6.5");
		parameter.setVmTemplateOS("Linux");

		// Windows
		// parameter.setVmTemplateName("vRouter_Telnet");
		// parameter.setVmTemplateOS("Linux");
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
		parameter.setVmName("random-1");
		parameter.setCpuNumber(1);
		parameter.setMemoryMB(1024);
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
		parameter.setVmName("Tenants-IGSnaJvT-192.168.1.2");
		parameter.setDiskName("testDB2");
		parameter.setDiskGB("10");
		return parameter;
	}

	public static RunVMParameter randomRunVMParameter() {

		RunVMParameter parameter = new RunVMParameter();
		parameter.setCpuNumber(2);
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setGateway("192.168.30.1");
		parameter.setIpaddress("192.168.30.100");
		parameter.setMemoryMB(2408);
		parameter.setSubNetMask("255.255.255.0");
		parameter.setTempVMName("random-1");
		parameter.setVmName("liukai");
		parameter.setVmTemplateOS("Linux");

		return parameter;
	}

	public static RunNetworkDeviceVMParameter randomRunNetworkDeviceVMParameter() {

		RunNetworkDeviceVMParameter parameter = new RunNetworkDeviceVMParameter();
		parameter.setDatacenter(DataCenterEnum.成都核心数据中心.toString());
		parameter.setCpuNumber(1);
		parameter.setMemoryMB(1024);
		parameter.setTempVMName("router_Random1");
		parameter.setVmName("liukai router");
		return parameter;
	}

}
