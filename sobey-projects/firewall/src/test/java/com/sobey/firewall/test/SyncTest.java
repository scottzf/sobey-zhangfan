package com.sobey.firewall.test;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.firewall.PbulicProperties;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.webservice.response.dto.RouterParameter;
import com.sobey.firewall.webservice.response.dto.SubnetParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest implements PbulicProperties {

	private static final String ENTER_SIGN = "\r";

	/**
	 * config system interface
	 * 
	 * edit "port9"
	 * 
	 * set ip 173.20.10.254 255.255.255.0
	 * 
	 * set allowaccess ping https ssh telnet http
	 * 
	 * set type physical
	 * 
	 * set snmp-index 8
	 */
	@Test
	public void configSystemInterface() {

		// 创建子网 网关
		// 创建公网IP 单个IP vRouter创建公网IP
		
		//Subnet 1:N
		//EIP    1:N

		// 配置接口IP地址脚本

		StringBuilder sb = new StringBuilder();

		SubnetParameter subnetParameter = TestData.randomSubnetParameter();

		sb.append("config system interface").append(ENTER_SIGN);
		sb.append("edit ").append("\"").append(subnetParameter.getPortName()).append("\"").append(ENTER_SIGN);
		sb.append("set ip ").append(subnetParameter.getGateway()).append(" ").append(subnetParameter.getSubnetMask())
				.append(ENTER_SIGN);
		sb.append("set allowaccess ping https ssh telnet http").append(ENTER_SIGN);
		sb.append("set type physical").append(ENTER_SIGN);
		sb.append("set snmp-index 8").append(ENTER_SIGN);

		System.out.println(sb.toString());
	}

	/**
	 * config router static
	 * 
	 * edit 100
	 * 
	 * set device port1
	 * 
	 * set gateway 221.237.156.1
	 */
	@Test
	public void configRouterStatic() {

		// 配置默认路由脚本(公网IP相关)
		// 创建公网IP 单个IP vRouter创建公网IP

		Integer routerId = 100; // 路由的编号 和策略编号不同体系.
		String ipaddress = "221.237.156.1"; // 电信网关
		String portName = "port1"; // 电信的接口名称

		StringBuilder sb = new StringBuilder();

		sb.append("config router static").append(ENTER_SIGN);
		sb.append("edit ").append(routerId).append(ENTER_SIGN);

		sb.append("set device ").append(portName).append(ENTER_SIGN);
		sb.append("set gateway ").append(ipaddress).append(ENTER_SIGN);

		System.out.println(sb.toString());
	}

	/**
	 * config firewall address
	 * 
	 * edit "173.20.10.0/24"
	 * 
	 * set subnet 173.20.10.254 255.255.255.0
	 * 
	 * next
	 */
	@Test
	public void configFirewallAddress() {

		// 创建地址段
		// 配置两个子网通讯

		RouterParameter routerParameter = TestData.randomRouterParameter();

		ArrayList<SubnetParameter> list = routerParameter.getSubnetParameters();

		StringBuilder sb = new StringBuilder();

		for (SubnetParameter subnetParameter : list) {

			sb.append("config firewall address").append(ENTER_SIGN);
			sb.append("edit ").append("\"").append(subnetParameter.getSegment()).append("\"").append(ENTER_SIGN);
			sb.append("set subnet ").append(subnetParameter.getGateway()).append(" ")
					.append(subnetParameter.getSubnetMask()).append(ENTER_SIGN);
			sb.append("next").append(ENTER_SIGN);
		}
		System.out.println(sb.toString());
	}

	/**
	 * config firewall policy
	 * 
	 * edit 10
	 * 
	 * set srcintf "port9"
	 * 
	 * set srcaddr "173.20.10.0/24"
	 * 
	 * set dstintf "port10"
	 * 
	 * set dstaddr "173.20.11.0/24"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 * 
	 * edit 10
	 * 
	 * set srcintf "port9"
	 * 
	 * set srcaddr "173.20.10.0/24"
	 * 
	 * set dstintf "port10"
	 * 
	 * set dstaddr "173.20.12.0"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 * 
	 * edit 11
	 * 
	 * set srcintf "port10"
	 * 
	 * set srcaddr "173.20.11.0/24"
	 * 
	 * set dstintf "port9"
	 * 
	 * set dstaddr "173.20.10.0/24"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 * 
	 * edit 11
	 * 
	 * set srcintf "port10"
	 * 
	 * set srcaddr "173.20.11.0/24"
	 * 
	 * set dstintf "port10"
	 * 
	 * set dstaddr "173.20.12.0"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 * 
	 * edit 12
	 * 
	 * set srcintf "port10"
	 * 
	 * set srcaddr "173.20.12.0"
	 * 
	 * set dstintf "port9"
	 * 
	 * set dstaddr "173.20.10.0/24"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 * 
	 * edit 12
	 * 
	 * set srcintf "port10"
	 * 
	 * set srcaddr "173.20.12.0/24"
	 * 
	 * set dstintf "port10"
	 * 
	 * set dstaddr "173.20.11.0/24"
	 * 
	 * set schedule "always"
	 * 
	 * set service "ALL"
	 * 
	 * next
	 */
	@Test
	public void configFirewallPolicy() {

		// 子网通信

		StringBuilder sb = new StringBuilder();

		RouterParameter routerParameter = TestData.randomRouterParameter();

		ArrayList<SubnetParameter> list = routerParameter.getSubnetParameters();

		sb.append("config firewall policy").append(ENTER_SIGN);

		for (int i = 0; i < list.size(); i++) {

			SubnetParameter subnet = list.get(i);

			// new 一个新的list出来,将传递进来的list填充进去,并将循环中的自身对象remove出去,这样就达到源对应多个目标的目的.
			ArrayList<SubnetParameter> parameters = new ArrayList<SubnetParameter>();
			parameters.addAll(list);
			parameters.remove(subnet);

			for (SubnetParameter subnetParameter : parameters) {

				sb.append("edit ").append(subnet.getPolicyId()).append(ENTER_SIGN);

				sb.append("set srcintf ").append("\"").append(subnet.getPortName()).append("\"").append(ENTER_SIGN);
				sb.append("set srcaddr ").append("\"").append(subnet.getSegment()).append("\"").append(ENTER_SIGN);

				sb.append("set dstintf ").append("\"").append(subnetParameter.getPortName()).append("\"")
						.append(ENTER_SIGN);
				sb.append("set dstaddr ").append("\"").append(subnetParameter.getSegment()).append("\"")
						.append(ENTER_SIGN);
				sb.append("set schedule ").append("\"").append("always").append("\"").append(ENTER_SIGN);
				sb.append("set service ").append("\"").append("ALL").append("\"").append(ENTER_SIGN);
				sb.append("next").append(ENTER_SIGN);
			}

		}

		System.out.println(sb.toString());
	}

}
