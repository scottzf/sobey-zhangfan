package com.sobey.sdn.service;

import java.util.List;

import com.sobey.core.utils.Collections3;
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
		sb.append("execute backup config flash").append(SDNConstants.ENTER_SIGN);

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
		// end

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

	/**
	 * 映射EIP与内网ip对应关系
	 * 
	 * <pre>
	 * config firewall vip
	 * edit "119.6.200.219-tcp-8080"
	 * set extip 119.6.200.219
	 * set extintf "port8"
	 * set portforward enable
	 * set mappedip 172.28.25.105
	 * set protocol udp /tcp
	 * set extport 8080 变量 端口
	 * set mappedport 8080 变量 端口
	 * next
	 * end
	 * </pre>
	 * 
	 * @param vip
	 * @param protocol
	 * @param protocolPortNo
	 * @param portNo
	 * @param localIp
	 * @return
	 */
	public static String generateEIpMappingToLocalIpConfigScript(String vip, String protocol, int protocolPort,
			int portNo, String localIp) {

		// EIP与内网ip对应关系

		StringBuilder sb = new StringBuilder();

		String vipName = generateVIPMappingName(vip, protocol, protocolPort);

		sb.append("config firewall vip").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("\"").append(vipName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set extip ").append(vip).append(SDNConstants.ENTER_SIGN);
		sb.append("set extintf ").append("\"").append("port").append(portNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set portforward enable").append(SDNConstants.ENTER_SIGN);
		sb.append("set mappedip ").append(localIp).append(SDNConstants.ENTER_SIGN);
		sb.append("set protocol ").append(protocol).append(SDNConstants.ENTER_SIGN);
		sb.append("set extport ").append(protocolPort).append(SDNConstants.ENTER_SIGN);
		sb.append("set mappedport ").append(protocolPort).append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 将映射关系加入VIP组里
	 * 
	 * @param vipGroupName
	 * @param protNo
	 * @param members
	 * @return
	 */
	public static String generateAddEipMappingToGroupConfigScript(String vipGroupName, int protNo, List<String> members) {

		// 将映射关系加入VIP组成
		// config firewall vipgrp
		// edit "CTC_ALL_Server"
		// set interface "port8"
		// set member "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80"
		// end

		StringBuilder sb = new StringBuilder();

		sb.append("config firewall vipgrp").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("\"").append(vipGroupName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set interface ").append("\"").append("port").append(protNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set member ").append(generateFormatString(members)).append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * EIP策略创建
	 * 
	 * @param strategyNo
	 * @param ePortNo
	 * @param subnetPortNo
	 * @param dstaddr
	 * @return
	 */
	public static String generateCreateEipStrategyConfigScript(int strategyNo, int ePortNo, int subnetPortNo,
			String dstaddr) {

		// EIP策略创建
		// config firewall policy
		// edit 20
		// set srcintf "port8"
		// set dstintf "port2"
		// set srcaddr "all"
		// set dstaddr "CTC_ALL_Server"
		// set action accept
		// set schedule "always"
		// set service "ALL"
		// next
		// end

		StringBuilder sb = new StringBuilder();

		sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(strategyNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set srcintf ").append("\"").append("port").append(ePortNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set dstintf ").append("\"").append("port").append(subnetPortNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set srcaddr ").append("\"").append("all").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set dstaddr ").append("\"").append(dstaddr).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set action accept").append(SDNConstants.ENTER_SIGN);
		sb.append("set schedule ").append("\"").append("always").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set service ").append("\"").append("ALL").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 配置VPN用户账号和密码
	 * 
	 * <pre>
	 * config user local
	 * edit "liukai"
	 * set type password
	 * set passwd liukai@sobey
	 * next
	 * end
	 * </pre>
	 * 
	 * @param vpnUserName
	 * @param vpnPassword
	 * @return
	 */
	public static String generateVpnUserConfigScript(String vpnUserName, String vpnPassword) {

		StringBuilder sb = new StringBuilder();

		sb.append("config user local").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("\"").append(vpnUserName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set type password").append(SDNConstants.ENTER_SIGN);
		sb.append("set passwd ").append(vpnPassword).append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 配置VPN用户组
	 * 
	 * <pre>
	 * config user group
	 * edit "hujun1_gr"
	 * set member "hujun1" "zhangfan" "kai"
	 * next
	 * end
	 * </pre>
	 * 
	 * @param vpnGroupName
	 * @param userNames
	 * @return
	 */
	public static String generateVpnUserGroupConfigScript(String vpnGroupName, List<String> userNames) {

		StringBuilder sb = new StringBuilder();

		sb.append("config user group").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append("\"").append(vpnGroupName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set member ").append(generateFormatString(userNames)).append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 配置VPN策略
	 * 
	 * <pre>
	 * config firewall policy
	 * edit 6
	 * set srcintf "port8"
	 * set dstintf "port1"
	 * set srcaddr "all"
	 * set dstaddr "SubNet1"
	 * set action ssl-vpn
	 * set identity-based enable
	 * config identity-based-policy
	 * edit 1
	 * set schedule "always"
	 * set groups "hujun1_gr"
	 * set service "ALL"
	 * set sslvpn-portal "full-access"
	 * next
	 * end
	 * next
	 * end
	 * </pre>
	 * 
	 * @param strategyNo
	 * @param internetPortNo
	 * @param subnetPortNo
	 * @param subnetAddressPoolName
	 * @param vpnGroupNo
	 * @param vpnGroupName
	 * @return
	 */
	public static String generateVpnStrategyConfigScript(int strategyNo, int internetPortNo, int subnetPortNo,
			String subnetAddressPoolName, int vpnGroupNo, String vpnGroupName) {

		StringBuilder sb = new StringBuilder();

		sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(strategyNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set srcintf ").append("\"").append("port").append(internetPortNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set dstintf ").append("\"").append("port").append(subnetPortNo).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set srcaddr ").append("\"").append("all").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set dstaddr ").append("\"").append(subnetAddressPoolName).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sb.append("set action ssl-vpn").append(SDNConstants.ENTER_SIGN);
		sb.append("set identity-based enable").append(SDNConstants.ENTER_SIGN);
		sb.append("config identity-based-policy").append(SDNConstants.ENTER_SIGN);
		sb.append("edit ").append(vpnGroupNo).append(SDNConstants.ENTER_SIGN);
		sb.append("set schedule ").append("\"").append("always").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set groups ").append("\"").append(vpnGroupName).append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set service ").append("\"").append("ALL").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("set sslvpn-portal ").append("\"").append("full-access").append("\"").append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);
		sb.append("next").append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	/**
	 * 生成VIP映射名称.<br>
	 * 生成规则: internetIP-protocolText-targetPort<br>
	 * Example:<b> 119.6.200.219-tcp-8080 </b>
	 * 
	 * @param internetIP
	 *            外网IP
	 * @param protocolText
	 *            协议
	 * @param targetPort
	 *            目标端口
	 * @return
	 */
	private static String generateVIPMappingName(String vip, String protocol, int protocolPortNo) {
		return vip + "-" + protocol + "-" + protocolPortNo;
	}

	/**
	 * 对集合里的字符串进行组合,生成指定格式的字符串.<br>
	 * 
	 * Example:<b> "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80" </b>
	 * 
	 * @param list
	 *            字符串集合
	 * @return
	 */
	private static String generateFormatString(List<String> list) {
		return Collections3.convertToString(list, "\"", "\" ");
	}
}
