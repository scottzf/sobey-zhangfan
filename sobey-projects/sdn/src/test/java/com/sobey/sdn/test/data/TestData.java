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

		parameter.setRouterName("zhangfanTest-vRouter555");
		parameter.setControlIp("10.2.253.80");
		parameter.setStrategyNo(176);

		List<Subnet> subnets = new ArrayList<Subnet>();

		Subnet subnet1 = new Subnet();
		subnet1.setSubnetName("subnet1");
		subnet1.setSegment("192.168.100.0");
		subnet1.setGateway("192.168.100.1");
		subnet1.setSubnetMask("255.255.255.0");
		subnet1.setPortGroupName("Tenants-FmRsraMU-22");
		subnet1.setPortNo(1);

		Subnet subnet2 = new Subnet();
		subnet2.setSubnetName("subnet2");
		subnet2.setSegment("192.168.200.0");
		subnet2.setGateway("192.168.200.1");
		subnet2.setSubnetMask("255.255.255.0");
		subnet2.setPortGroupName("Tenants-FmRsraMU-23");
		subnet2.setPortNo(2);

		subnets.add(subnet1);
		subnets.add(subnet2);

		return parameter;
	}

	public static CreateECSParameter randomCreateECSParameter() {

		CreateECSParameter parameter = new CreateECSParameter();

		parameter.setGateway("172.16.3.1"); // 网关
		parameter.setHostIp("10.2.5.23");
		parameter.setLocalIp("172.16.3.5");// 内网ip
		parameter.setSubNetMask("255.255.255.0");// 子网掩码
		parameter.setTemplateName("Windows 7  64  40G MOD"); // 模板
		parameter.setTemplateOS("Windows");// 操作系统
		parameter.setVlanId(23);
		parameter.setTenantId("TN");
		parameter.setVmName("zf_172.16.3.5");

		return parameter;
	}

	public static CreateRouterParameter randomCreateRouterParameter() {

		CreateRouterParameter parameter = new CreateRouterParameter();

		parameter.setHostIp("10.2.5.25"); // 主机IP
		parameter.setRouterName("zhangfan-vRouter_new"); // 路由名称
		parameter.setControlIp("10.2.253.90"); // 端口10更新ip

		return parameter;
	}
}
