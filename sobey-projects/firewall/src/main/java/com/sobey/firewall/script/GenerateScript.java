package com.sobey.firewall.script;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.core.utils.Collections3;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.firewall.constans.SymbolEnum;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

/**
 * Firewall 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader FIREWALL_LOADER = new PropertiesLoader("classpath:/firewall.properties");

	/* 脚本参数 */

	/**
	 * extintf
	 */
	private static final String FIREWALL_EXTINTF = FIREWALL_LOADER.getProperty("FIREWALL_EXTINTF");

	/**
	 * portforward
	 */
	private static final String FIREWALL_PORTFORWARD = FIREWALL_LOADER.getProperty("FIREWALL_PORTFORWARD");

	/**
	 * 联通
	 */
	private static final String FIREWALL_CNC = FIREWALL_LOADER.getProperty("FIREWALL_CNC");

	/**
	 * 电信
	 */
	private static final String FIREWALL_CTC = FIREWALL_LOADER.getProperty("FIREWALL_CTC");

	/**
	 * sslvpn-portal
	 */
	private static final String FIREWALL_SSLVPN_PORTAL = FIREWALL_LOADER.getProperty("FIREWALL_SSLVPN_PORTAL");

	/**
	 * srcintf
	 */
	private static final String FIREWALL_SRCINTF = FIREWALL_LOADER.getProperty("FIREWALL_SRCINTF");

	/**
	 * dstintf
	 */
	private static final String FIREWALL_DSTINTF = FIREWALL_LOADER.getProperty("FIREWALL_DSTINTF");

	/**
	 * srcaddr
	 */
	private static final String FIREWALL_SRCADDR = FIREWALL_LOADER.getProperty("FIREWALL_SRCADDR");

	/**
	 * schedule
	 */
	private static final String FIREWALL_SCHEDULE = FIREWALL_LOADER.getProperty("FIREWALL_SCHEDULE");

	/**
	 * service
	 */
	private static final String FIREWALL_SERVICE = FIREWALL_LOADER.getProperty("FIREWALL_SERVICE");

	/**
	 * identity-based-policy
	 */
	private static final String FIREWALL_IDENTITY_BASED_POLICY_ID = FIREWALL_LOADER
			.getProperty("FIREWALL_IDENTITY_BASED_POLICY_ID");

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
	private static String generateVIPMappingName(String internetIP, String protocolText, Integer targetPort) {
		return internetIP + "-" + protocolText + "-" + targetPort;
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

	/**
	 * 根据EIPParameter中的ISP属性获得VIP组.<br>
	 * 
	 * <b>ISP运营商 0: 中国电信 1:中国联通</b>
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @return
	 */
	private static String getVipgrpByISP(EIPParameter parameter) {
		return "0".equals(parameter.getIsp().toString()) ? FIREWALL_CTC : FIREWALL_CNC;
	}

	/**
	 * 生成IP的address name.<br>
	 * 
	 * 生成的名称以0/32结尾.<br>
	 * 
	 * <b>eg.172.20.18.5/32<b><br>
	 * 
	 * <em>address name结尾的数字是由算法得到的,为简单实现功能,直接的默认IP以/32结尾</em>
	 * 
	 * @param ipaddress
	 *            ipaddress
	 * @return
	 */
	private static String generateAddressNameByIP(String ipaddress) {
		return ipaddress + "/32";
	}

	/**
	 * 生成网关的address name.<br>
	 * 
	 * 生成的名称以0/24结尾.<br>
	 * 
	 * <b>eg.172.20.18.0/24<b><br>
	 * 
	 * <em>address name结尾的数字是由算法得到的,为简单实现功能,直接的默认网关以/24结尾</em>
	 * 
	 * @param segment
	 *            网关
	 * @return
	 */
	private static String generateAddressNameBySegment(String segment) {
		return segment + "/24";
	}

	/**
	 * 生成vlan user group 的Name.<br>
	 * 
	 * Example: <b>vlan80-gr</b>
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter}
	 * @return
	 */
	private static String generateVlanGroupName(VPNUserParameter parameter) {
		return "vlan" + parameter.getVlanId() + "-gr";
	}

	/**
	 * 生成在<b>防火墙</b>执行的创建EIP脚本.<br>
	 * <b>注意,在set member后添加的映射名,是包含了所有租户的EIP的映射名</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall vip
	 * edit "119.6.200.219-tcp-8080"  
	 * set extip 119.6.200.219
	 * set extintf "wan1"	
	 * set portforward enable
	 * set mappedip 172.28.25.105
	 * set protocol udp           
	 * set extport 8080
	 * set mappedport 8080
	 * next
	 * end
	 * 
	 * config firewall vip
	 * edit "119.6.200.219-tcp-80"  
	 * set extip 119.6.200.219
	 * set extintf "wan1"	
	 * set portforward enable
	 * set mappedip 172.28.25.105
	 * set protocol udp           
	 * set extport 80
	 * set mappedport 80
	 * next
	 * end
	 * 
	 * config firewall  vipgrp
	 * edit "CNC_All_Services"
	 * set interface "wan1"
	 * set member  "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80"   
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @param 换行符号
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateCreateEIPScript(EIPParameter parameter, List<String> allPolicies, String symbol) {

		StringBuilder sb = new StringBuilder();

		// 生成端口的映射策略脚本. 一个端口对应一条脚本
		for (EIPPolicyParameter policy : parameter.getPolicies()) {

			String vipName = generateVIPMappingName(parameter.getInternetIP(), policy.getProtocolText(),
					policy.getTargetPort());

			allPolicies.add(vipName);

			sb.append("config firewall vip").append(symbol);
			sb.append("edit ").append("\"").append(vipName).append("\"").append(symbol);
			sb.append("set extip ").append(parameter.getInternetIP()).append(symbol);
			sb.append("set extintf ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
			sb.append("set portforward ").append(FIREWALL_PORTFORWARD).append(symbol);
			sb.append("set mappedip ").append(parameter.getPrivateIP()).append(symbol);

			// 当协议为udp时,增加协议的设置,为tcp时,不需要设置.
			if ("udp".equalsIgnoreCase(policy.getProtocolText())) {
				sb.append("set protocol udp").append(symbol);
			}
			sb.append("set extport ").append(policy.getSourcePort()).append(symbol);
			sb.append("set mappedport ").append(policy.getTargetPort()).append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);
		}

		sb.append("config firewall vipgrp").append(symbol);
		sb.append("edit ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(symbol);
		sb.append("set interface ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
		sb.append("set member ").append(generateFormatString(allPolicies)).append(symbol);
		sb.append("end").append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>防火墙</b>执行的创建EIP脚本,默认换行符号<br>
	 * <b>注意,在set member后添加的映射名,是包含了所有租户的EIP的映射名</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall vip
	 * edit "119.6.200.219-tcp-8080"  
	 * set extip 119.6.200.219
	 * set extintf "wan1"	
	 * set portforward enable
	 * set mappedip 172.28.25.105
	 * set protocol udp           
	 * set extport 8080
	 * set mappedport 8080
	 * next
	 * end
	 * 
	 * config firewall vip
	 * edit "119.6.200.219-tcp-80"  
	 * set extip 119.6.200.219
	 * set extintf "wan1"	
	 * set portforward enable
	 * set mappedip 172.28.25.105
	 * set protocol udp           
	 * set extport 80
	 * set mappedport 80
	 * next
	 * end
	 * 
	 * config firewall  vipgrp
	 * edit "CNC_All_Services"
	 * set interface "wan1"
	 * set member  "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80"   
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @return
	 */
	public static String generateCreateEIPScript(EIPParameter parameter, List<String> allPolicies) {
		return generateCreateEIPScript(parameter, allPolicies, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>防火墙</b>执行的删除EIP脚本.<br>
	 * <b>注意,在set member后添加的映射名,是包含了所有租户的EIP的映射名</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall  vipgrp
	 * edit "CNC_All_Services"
	 * set interface "wan1"
	 * set member "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80"   
	 * end
	 * 
	 * config firewall vip
	 * delet 113.142.30.220-tcp-8082
	 * end
	 * 
	 * config firewall vip
	 * delet 113.142.30.220-tcp-8083
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteEIPScript(EIPParameter parameter, List<String> allPolicies, String symbol) {

		/*
		 * 1.获得所有租户的VIP策略组名集合.
		 * 
		 * 2.在所有VIP策略组名集合中移除删除EIP的VIP组策略
		 * 
		 * 3.再次设置VIP策略组,将步骤2得到的集合进行VIP组的绑定,这样删除的EIP就和VIP组进行解绑
		 * 
		 * 4.删除解绑的VIP组策略
		 */

		StringBuilder sb = new StringBuilder();

		List<String> policies = Lists.newArrayList();

		for (EIPPolicyParameter policy : parameter.getPolicies()) {
			policies.add(generateVIPMappingName(parameter.getInternetIP(), policy.getProtocolText(),
					policy.getTargetPort()));
		}

		// Step.2
		allPolicies.removeAll(policies);

		// Step.3
		sb.append("config firewall vipgrp").append(symbol);
		sb.append("edit ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(symbol);
		sb.append("set interface ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
		sb.append("set member ").append(generateFormatString(allPolicies)).append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		// Step.4
		for (String policy : policies) {
			sb.append("config firewall vip").append(symbol);
			sb.append("edit ").append(policy).append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);
		}

		return sb.toString();
	}

	/**
	 * 生成在<b>防火墙</b>执行的删除EIP脚本,默认换行符号<br>
	 * <b>注意,在set member后添加的映射名,是包含了所有租户的EIP的映射名</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall  vipgrp
	 * edit "CNC_All_Services"
	 * set interface "wan1"
	 * set member "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80"   
	 * end
	 * 
	 * config firewall vip
	 * delet 113.142.30.220-tcp-8082
	 * end
	 * 
	 * config firewall vip
	 * delet 113.142.30.220-tcp-8083
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @return
	 */
	public static String generateDeleteEIPScript(EIPParameter parameter, List<String> allPolicies) {
		return generateDeleteEIPScript(parameter, allPolicies, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 
	 * 生成在<b>防火墙</b>执行的创建VPN脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall address
	 * edit "172.20.19.1/32"
	 * set subnet 172.20.19.1 255.255.255.255
	 * next
	 * end
	 * 
	 * config firewall address
	 * edit "172.20.17.0/24"
	 * set subnet 172.20.17.0 255.255.255.0
	 * next
	 * end
	 * 
	 * config firewall address
	 * edit "172.20.18.0/24"
	 * set subnet 172.20.18.0 255.255.255.0
	 * next
	 * end
	 * 
	 * config user local
	 * edit "liukai"
	 * set type password 
	 * edit passwd liukai@sobey
	 * next
	 * end
	 * 
	 * config user group
	 * edit "vlan80-gr"
	 * set sslvpn-portal "full-access"
	 * set member "liukai"
	 * next
	 * end
	 * 
	 * config firewall policy
	 * edit 2000
	 * set srcintf "wan1"
	 * set dstintf "internal"
	 * set srcaddr "all"
	 * set dstaddr "172.20.19.1/32" "172.20.17.0/24" "172.20.18.0/24" 
	 * set action ssl-vpn
	 * 
	 * config identity-based-policy
	 * edit 1
	 * set schedule "always"
	 * set groups "vlan80-gr"
	 * set service "ANY"
	 * next
	 * end
	 * 
	 * next
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter}
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateCreateVPNUserScript(VPNUserParameter parameter, String symbol) {

		StringBuilder sb = new StringBuilder();

		List<String> dstaddrs = Lists.newArrayList();

		/*
		 * 如果是单IP,则接下来的两个参数应该为: 单IP 和 255.255.255.255
		 * 
		 * 如果是网关,则接下来的两个参数应该为: 网关 和 255.255.255.0
		 */

		for (String ipaddress : parameter.getIpaddress()) {
			sb.append("config firewall address").append(symbol);
			sb.append("edit ").append("\"").append(generateAddressNameByIP(ipaddress)).append("\"").append(symbol);
			sb.append("set subnet ").append(ipaddress).append(" 255.255.255.255").append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);

			dstaddrs.add(generateAddressNameByIP(ipaddress));
		}

		for (String segment : parameter.getSegments()) {
			sb.append("config firewall address").append(symbol);
			sb.append("edit ").append("\"").append(generateAddressNameBySegment(segment)).append("\"").append(symbol);
			sb.append("set subnet ").append(segment).append(" 255.255.255.0").append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);

			dstaddrs.add(generateAddressNameBySegment(segment));
		}

		sb.append("config user local").append(symbol);
		sb.append("edit ").append("\"").append(parameter.getVpnUser()).append("\"").append(symbol);
		sb.append("set type password ").append(symbol);
		sb.append("edit passwd ").append(parameter.getVpnPassword()).append(symbol);
		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		sb.append("config user group").append(symbol);
		sb.append("edit ").append("\"").append(generateVlanGroupName(parameter)).append("\"").append(symbol);
		sb.append("set sslvpn-portal ").append("\"").append(FIREWALL_SSLVPN_PORTAL).append("\"").append(symbol);
		sb.append("set member ").append("\"").append(parameter.getVpnUser()).append("\"").append(symbol);
		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		sb.append("config firewall policy").append(symbol);
		sb.append("edit ").append(parameter.getFirewallPolicyId()).append(symbol);
		sb.append("set srcintf ").append("\"").append(FIREWALL_SRCINTF).append("\"").append(symbol);
		sb.append("set dstintf ").append("\"").append(FIREWALL_DSTINTF).append("\"").append(symbol);
		sb.append("set srcaddr ").append("\"").append(FIREWALL_SRCADDR).append("\"").append(symbol);
		sb.append("set dstaddr ").append(generateFormatString(dstaddrs)).append(symbol);
		sb.append("set action ssl-vpn").append(symbol);
		sb.append(symbol);

		sb.append("config identity-based-policy").append(symbol);
		sb.append("edit ").append(FIREWALL_IDENTITY_BASED_POLICY_ID).append(symbol);
		sb.append("set schedule ").append("\"").append(FIREWALL_SCHEDULE).append("\"").append(symbol);
		sb.append("set groups ").append("\"").append(generateVlanGroupName(parameter)).append("\"").append(symbol);
		sb.append("set service ").append("\"").append(FIREWALL_SERVICE).append("\"").append(symbol);
		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 
	 * 生成在<b>防火墙</b>执行的创建VPN脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall address
	 * edit "172.20.19.1/32"
	 * set subnet 172.20.19.1 255.255.255.255
	 * next
	 * end
	 * 
	 * config firewall address
	 * edit "172.20.17.0/24"
	 * set subnet 172.20.17.0 255.255.255.0
	 * next
	 * end
	 * 
	 * config firewall address
	 * edit "172.20.18.0/24"
	 * set subnet 172.20.18.0 255.255.255.0
	 * next
	 * end
	 * 
	 * config user local
	 * edit "liukai"
	 * set type password 
	 * edit passwd liukai@sobey
	 * next
	 * end
	 * 
	 * config user group
	 * edit "vlan80-gr"
	 * set sslvpn-portal "full-access"
	 * set member "liukai"
	 * next
	 * end
	 * 
	 * config firewall policy
	 * edit 2000
	 * set srcintf "wan1"
	 * set dstintf "internal"
	 * set srcaddr "all"
	 * set dstaddr "172.20.19.1/32" "172.20.17.0/24" "172.20.18.0/24" 
	 * set action ssl-vpn
	 * 
	 * config identity-based-policy
	 * edit 1
	 * set schedule "always"
	 * set groups "vlan80-gr"
	 * set service "ANY"
	 * next
	 * end
	 * 
	 * next
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter}
	 * @return
	 */
	public static String generateCreateVPNUserScript(VPNUserParameter parameter) {
		return generateCreateVPNUserScript(parameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 
	 * 生成在<b>防火墙</b>执行的创建VPN脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall address
	 * edit  "172.20.18.0/24"
	 * set subnet 172.20.18.0 255.255.255.0
	 * next
	 * end
	 * 
	 * config firewall policy
	 * edit 2000
	 * set srcintf "wan1"
	 * set dstintf "internal"
	 * set srcaddr "all"
	 * set dstaddr "172.20.17.0/24" "172.20.18.0/24"
	 * set action ssl-vpn
	 * 
	 * config identity-based-policy
	 * edit 1
	 * set schedule "always"
	 * set groups "vlan80-gr"
	 * set service "ANY"
	 * next
	 * end
	 * 
	 * next
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 * @param symbol
	 * @return
	 */
	public static String generateAddIPAddressIntoVPNUserScript(VPNUserParameter parameter, String symbol) {

		StringBuilder sb = new StringBuilder();

		List<String> dstaddrs = Lists.newArrayList();

		/*
		 * 如果是单IP,则接下来的两个参数应该为: 单IP 和 255.255.255.255
		 * 
		 * 如果是网关,则接下来的两个参数应该为: 网关 和 255.255.255.0
		 */

		for (String ipaddress : parameter.getIpaddress()) {
			sb.append("config firewall address").append(symbol);
			sb.append("edit ").append("\"").append(generateAddressNameByIP(ipaddress)).append("\"").append(symbol);
			sb.append("set subnet ").append(ipaddress).append(" 255.255.255.255").append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);

			dstaddrs.add(generateAddressNameByIP(ipaddress));
		}

		for (String segment : parameter.getSegments()) {
			sb.append("config firewall address").append(symbol);
			sb.append("edit ").append("\"").append(generateAddressNameBySegment(segment)).append("\"").append(symbol);
			sb.append("set subnet ").append(segment).append(" 255.255.255.0").append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);

			dstaddrs.add(generateAddressNameBySegment(segment));
		}

		sb.append("config firewall policy").append(symbol);
		sb.append("edit ").append(parameter.getFirewallPolicyId()).append(symbol);
		sb.append("set srcintf ").append("\"").append(FIREWALL_SRCINTF).append("\"").append(symbol);
		sb.append("set dstintf ").append("\"").append(FIREWALL_DSTINTF).append("\"").append(symbol);
		sb.append("set srcaddr ").append("\"").append(FIREWALL_SRCADDR).append("\"").append(symbol);
		sb.append("set dstaddr ").append("\"").append(generateFormatString(dstaddrs)).append("\"").append(symbol);

		sb.append("set action ssl-vpn").append(symbol);
		sb.append(symbol);

		sb.append("config identity-based-policy").append(symbol);
		sb.append("edit ").append(FIREWALL_IDENTITY_BASED_POLICY_ID).append(symbol);
		sb.append("set schedule ").append("\"").append(FIREWALL_SCHEDULE).append("\"").append(symbol);
		sb.append("set groups ").append("\"").append(generateVlanGroupName(parameter)).append("\"").append(symbol);
		sb.append("set service ").append("\"").append(FIREWALL_SERVICE).append("\"").append(symbol);
		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		sb.append("next").append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}
}
