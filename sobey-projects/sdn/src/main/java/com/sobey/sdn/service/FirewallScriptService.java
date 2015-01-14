package com.sobey.sdn.service;

import com.sobey.sdn.constans.SDNConstants;

public class FirewallScriptService {
	/**
	 * 生成配置指定端口IP地址的脚本
	 * 
	 * @param port
	 * @param ip
	 * @param subnetMask
	 * @return
	 */
	public static String generatePortIpConfigScript(int portNo, String ip, String subnetMask) {

		// 配置接口IP地址脚本(网关)（EIP相关 ） 1
		// config system interface
		// edit port3
		// set ip 221.237.156.150 255.255.255.0
		// set allowaccess ping https ssh telnet
		// set type physical
		// set snmp-index 8
		// end
		StringBuilder sb = new StringBuilder();

		sb.append("config system interface").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("port").append(portNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set ip ").append(ip).append(" ").append(subnetMask).append(SDNConstants.ENTER_SIGN);
		sb.append("set allowaccess ping https ssh telnet").append(SDNConstants.ENTER_SIGN);
		sb.append("set type physical").append(SDNConstants.ENTER_SIGN);
		sb.append("set snmp-index 8").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成修改端口10的IP地址的脚本
	 * 
	 * @param ip_update
	 * @return
	 */
	public static String generateupdatePort10IpConfigScript(String ip_update) {
		// 修改端口10的ip
		// config system interface
		// edit port10
		// set ip <ip netmask>
		// end
		// exe backup config flash
		StringBuilder sb = new StringBuilder();

		sb.append("config system interface").append(SDNConstants.ENTER_SIGN);
		sb.append("edit port10").append(SDNConstants.ENTER_SIGN);
		sb.append("set ip ").append(ip_update).append(" ").append(SDNConstants.PORT10_SUBNETMASK)
				.append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);
		sb.append("exe backup config flash").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成配置端口默认路由脚本
	 * 
	 * @param no
	 * @param port
	 * @param gateway
	 * @return
	 */
	public static String generateDefaultRouteConfigScript(int no, String port, String gateway) {

		// 配置默认路由脚本 （EIP相关）
		// config router static
		// edit 100
		// set device "port20"
		// set gateway 221.237.156.1
		// end

		StringBuilder sb = new StringBuilder();

		sb.append("config router static").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(no).append(SDNConstants.ENTER_SIGN);
		sb.append("set device ").append("\"").append(port).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set gateway ").append(gateway).append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成创建地址段脚本
	 * 
	 * @param addressPoolName
	 * @param networkSegment
	 * @param subnetMask
	 * @return
	 */
	public static String generateAddressFieldConfigScript(String addressPoolName, String networkSegment,
			String subnetMask) {
		// 创建地址段 3
		// config firewall address
		// edit "172.16.2.0/24"
		// set subnet 172.16.2.0 255.255.255.0
		// next

		StringBuilder sb = new StringBuilder();

		sb.append("config firewall address").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("\"").append(addressPoolName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set subnet ").append(networkSegment).append(" ").append(subnetMask).append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成子网上公网脚本
	 * 
	 * @param strategyNo
	 * @param sourceSubnet_port
	 * @param targetSubnet_port
	 * @param sourceSubnet_segment
	 * @return
	 */
	public static String generateInternetStrategyConfigScript(int strategyNo, String sourceSubnet_port,
			String targetSubnet_port, String sourceSubnet_segment, String targetSubnet_segment) {
		// 子网上公网 ( EIP相关 ) 5
		// config firewall policy
		// edit 6 （策略号）
		// set srcintf "port3"
		// set dstintf "port5"
		// set srcaddr "172.16.0.0/16"
		// set dstaddr "all"
		// set action accept
		// set schedule "always"
		// set service "ALL"
		// set nat enable
		// next
		// end
		StringBuilder sb = new StringBuilder();

		sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(strategyNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set srcintf ").append("\"").append(sourceSubnet_port).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set dstintf ").append("\"").append(targetSubnet_port).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set srcaddr ").append("\"").append(sourceSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set dstaddr ").append("\"").append(targetSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set action accept").append(SDNConstants.ENTER_SIGN);
		sb.append("set schedule ").append("\"").append("always").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set service ").append("\"").append("ALL").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set nat enable").append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成配置网络间的策略脚本
	 * 
	 * @param strategyNo
	 * @param sourceSubnet_port
	 * @param targetSubnet_port
	 * @param sourceSubnet_segment
	 * @param targetSubnet_segment
	 * @return
	 */
	public static String generateNetworksStrategyConfigScript(int strategyNo, String sourceSubnet_port,
			String targetSubnet_port, String sourceSubnet_segment, String targetSubnet_segment) {
		// 配置子网间的策略 4
		// config firewall policy
		// edit 6
		// set srcintf "port2"
		// set dstintf "port3"
		// set srcaddr "172.16.2.0/24"
		// set dstaddr "172.16.3.0/24"
		// set action accept
		// set schedule "always"
		// set service "ALL"
		// next
		// end
		StringBuilder sb = new StringBuilder();

		sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(strategyNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set srcintf ").append("\"").append(sourceSubnet_port).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set dstintf ").append("\"").append(targetSubnet_port).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set srcaddr ").append("\"").append(sourceSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set dstaddr ").append("\"").append(targetSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set action accept").append(SDNConstants.ENTER_SIGN);
		sb.append("set schedule ").append("\"").append("always").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set service ").append("\"").append("ALL").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

}
