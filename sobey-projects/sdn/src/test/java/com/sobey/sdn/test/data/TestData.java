package com.sobey.sdn.test.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.sdn.bean.CreateEipParameter;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.test.testParameter.BindingFirewallParameter;
import com.sobey.sdn.test.testParameter.BindingRouterParameter;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;

public class TestData {

	/**
	 * 一次流程中不同功能测试所需的相同参数
	 */
	static String controlIp = "10.2.253.60";
	static String routerName = "Tenants-jTWsg646-192.168.100.3";

	public static CreateECSParameter randomCreateECSParameter() {

		CreateECSParameter parameter = new CreateECSParameter();

		parameter.setHostIp("10.2.5.23"); // 主机IP
		parameter.setGateway("172.16.6.1"); // 网关
		parameter.setLocalIp("172.16.6.4");// 内网ip
		parameter.setSubNetMask("255.255.255.0");// 子网掩码
		parameter.setTemplateName("Windows 2008 R2 Mod"); // 模板
		parameter.setTemplateOS("Windows");// 操作系统
		parameter.setVlanId(162);
		parameter.setTenantId("TN");
		parameter.setVmName("TEST_172.16.6.4");

		return parameter;
	}

	public static CreateRouterParameter randomCreateRouterParameter() {

		CreateRouterParameter parameter = new CreateRouterParameter();

		parameter.setHostIp("10.2.5.29"); // 主机IP
		parameter.setRouterName(routerName); // 路由名称
		parameter.setControlIp(controlIp); // 端口10更新ip

		return parameter;
	}

	public static BindingRouterParameter randomBindingRouterParameter() {

		BindingRouterParameter parameter = new BindingRouterParameter();

		parameter.setRouterName(routerName);
		parameter.setControlIp(controlIp);
		parameter.setStrategyNo(156);

		List<Subnet> subnets = new ArrayList<Subnet>();

		Subnet subnet1 = new Subnet();
		subnet1.setSubnetName("subnet1");
		subnet1.setSegment("172.16.5.0");
		subnet1.setGateway("172.16.5.1");
		subnet1.setSubnetMask("255.255.255.0");
		subnet1.setPortGroupName("TN_vlan161");
		subnet1.setVlanId(161);
		subnet1.setPortNo(1);

		Subnet subnet2 = new Subnet();
		subnet2.setSubnetName("subnet2");
		subnet2.setSegment("172.16.6.0");
		subnet2.setGateway("172.16.6.1");
		subnet2.setSubnetMask("255.255.255.0");
		subnet2.setPortGroupName("TN_vlan162");
		subnet2.setVlanId(162);
		subnet2.setPortNo(2);

		subnets.add(subnet1);
		subnets.add(subnet2);

		parameter.setSubnets(subnets);
		
		return parameter;
	}

	public static BindingFirewallParameter randomBindingFirewallParameter() {

		BindingFirewallParameter parameter = new BindingFirewallParameter();

		parameter.setControlIp(controlIp);
		parameter.setGateway_CTC("125.71.203.1");
		parameter.setIp_CTC("125.71.203.19");
		parameter.setRouteNo(308);
		parameter.setRouterName(routerName);
		parameter.setStrategyNo(207);
		parameter.setSubnetAddressPoolName("subnet1");
		parameter.setSubnetMask_CTC("255.255.255.0");
		parameter.setSubnetPort("port1");

		return parameter;
	}

	public static CreateEipParameter randomCreateEipParameter() {

		CreateEipParameter parameter = new CreateEipParameter();

		parameter.setvRouterIp(controlIp); // 操纵路由器的管理IP
		parameter.setVipGroupName(SDNConstants.CTC_MAPPING_GROUP_NAME); // eip映射的全局组名
		parameter.setVip("125.71.203.22"); // 公网IP
		parameter.setProtocol("tcp"); // eip访问所使用的协议
		parameter.setProtocolPort(3389); // eip访问的协议端口
		parameter.setPrivateIP("172.16.5.3"); // 内网IP
		parameter.setInternetPortNO(8); // 公网所连路由器的端口序号
		parameter.setSubnetPortNo(1);// 子网所连路由器的端口序号
		parameter.setStrategyNo(18);// 公网端口与子网端口配置策略时的策略号 （每条策略号不能重复）
		String policy = "125.71.203.22-tcp-3389";
		ArrayList<String> allPolicies = new ArrayList<String>();
		allPolicies.add(policy);
		parameter.setAllPolicies(allPolicies);// 所有映射成员集合

		return parameter;

	}
}
