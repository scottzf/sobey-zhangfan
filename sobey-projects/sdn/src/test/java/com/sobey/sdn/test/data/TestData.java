package com.sobey.sdn.test.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.test.testParameter.BindingFirewallParameter;
import com.sobey.sdn.test.testParameter.BindingRouterParameter;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;

public class TestData {

	public static BindingFirewallParameter randomBindingFirewallParameter() {

		BindingFirewallParameter parameter = new BindingFirewallParameter();

		parameter.setControlIp("10.2.253.86");
		parameter.setGateway_CTC("221.237.156.1");
		parameter.setIp_CTC("221.237.156.153");
		parameter.setRouteNo(308);
		parameter.setRouterName("zhangfanTest-vRouter112");
		parameter.setStrategyNo(207);
		parameter.setSubnetAddressPoolName("subnet1");
		parameter.setSubnetMask_CTC("255.255.255.0");
		parameter.setSubnetPort("port1");

		return parameter;
	}

	public static BindingRouterParameter randomBindingRouterParameter() {

		BindingRouterParameter parameter = new BindingRouterParameter();

		parameter.setRouterName("zhangfanTest-vRouter112");
		parameter.setControlIp("10.2.253.86");
		parameter.setStrategyNo(176);

		List<Subnet> subnets = new ArrayList<Subnet>();

		Subnet subnet1 = new Subnet();
		subnet1.setSubnetName("subnet1");
		subnet1.setSegment("192.168.200.0");
		subnet1.setGateway("192.168.200.1");
		subnet1.setSubnetMask("255.255.255.0");
		subnet1.setPortGroupName("zhangfanTest_TN 200");
		subnet1.setPortNo(1);

		Subnet subnet2 = new Subnet();
		subnet2.setSubnetName("subnet2");
		subnet2.setSegment("192.168.230.0");
		subnet2.setGateway("192.168.230.1");
		subnet2.setSubnetMask("255.255.255.0");
		subnet2.setPortGroupName("zhangfanTest_TN 300");
		subnet2.setPortNo(2);

		subnets.add(subnet1);
		subnets.add(subnet2);

		return parameter;
	}

	public static CreateECSParameter randomCreateECSParameter() {

		CreateECSParameter parameter = new CreateECSParameter();

		parameter.setGateway("192.168.170.1"); // 网关
		parameter.setHostIp("10.2.5.23");
		parameter.setLocalIp("192.168.170.6");// 内网ip
		parameter.setSubNetMask("255.255.255.0");// 子网掩码
		parameter.setTemplateName("Windows 2008 R2 Mod"); // 模板
		parameter.setTemplateOS("Windows");// 操作系统
		parameter.setVlanId(700);
		parameter.setTenantId("zhangfanTest");
		parameter.setVmName("192.168.170.6");

		return parameter;
	}

	public static CreateRouterParameter randomCreateRouterParameter() {

		CreateRouterParameter parameter = new CreateRouterParameter();

		parameter.setHostIp("10.2.5.25"); // 主机IP
		parameter.setRouterName("zhangfanTest-vRouter112"); // 路由名称
		parameter.setControlIp("10.2.253.86"); // 端口10更新ip

		return parameter;
	}
}
