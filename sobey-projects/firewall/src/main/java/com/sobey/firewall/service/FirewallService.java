package com.sobey.firewall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sobey.core.utils.Collections3;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;

/**
 * Firewall 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
@Service
public class FirewallService {

	private static final String DEFAULT_SYMBOL = "\r";

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
	 * <b>注意:<br>
	 * 1.在set member后添加的映射名,是包含了所有租户的EIP的映射名;<br>
	 * 2.防火墙中的虚拟IP组中的组成员不能为空;<br>
	 * 3.如果IP名存在,command会报错,需在CMDB中约束</b><br>
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
	public String createEip(EIPParameter parameter, List<String> allPolicies) {

		StringBuilder sb = new StringBuilder();

		// 生成端口的映射策略脚本. 一个端口对应一条脚本
		for (EIPPolicyParameter policy : parameter.getPolicies()) {

			String vipName = generateVIPMappingName(parameter.getInternetIP(), policy.getProtocolText(),
					policy.getTargetPort());

			allPolicies.add(vipName);

			sb.append("config firewall vip").append(DEFAULT_SYMBOL);
			sb.append("edit ").append("\"").append(vipName).append("\"").append(DEFAULT_SYMBOL);
			sb.append("set extip ").append(parameter.getInternetIP()).append(DEFAULT_SYMBOL);
			sb.append("set extintf ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(DEFAULT_SYMBOL);
			sb.append("set portforward ").append(FIREWALL_PORTFORWARD).append(DEFAULT_SYMBOL);
			sb.append("set mappedip ").append(parameter.getPrivateIP()).append(DEFAULT_SYMBOL);

			// 当协议为udp时,增加协议的设置,为tcp时,不需要设置.
			if ("udp".equalsIgnoreCase(policy.getProtocolText())) {
				sb.append("set protocol udp").append(DEFAULT_SYMBOL);
			}
			sb.append("set extport ").append(policy.getSourcePort()).append(DEFAULT_SYMBOL);
			sb.append("set mappedport ").append(policy.getTargetPort()).append(DEFAULT_SYMBOL);
			sb.append("next").append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);
		}

		sb.append("config firewall vipgrp").append(DEFAULT_SYMBOL);
		sb.append("edit ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set interface ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set member ").append(generateFormatString(allPolicies)).append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append("quit").append(DEFAULT_SYMBOL);

		return sb.toString();
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
	 * @return
	 */
	public String deleteEip(EIPParameter parameter, List<String> allPolicies) {

		/*
		 * 1.获得所有租户的VIP策略组名集合.
		 * 
		 * 2.在所有VIP策略组名集合中移除删除EIP的VIP组策略(重点)
		 * 
		 * 3.再次设置VIP策略组,将步骤2得到的集合进行VIP组的绑定,这样删除的EIP就和VIP组进行解绑
		 * 
		 * 4.删除解绑的VIP组策略
		 */

		StringBuilder sb = new StringBuilder();

		List<String> policies = Lists.newArrayList();

		// 获得要删除EIP的映射策略名
		for (EIPPolicyParameter policy : parameter.getPolicies()) {
			policies.add(generateVIPMappingName(parameter.getInternetIP(), policy.getProtocolText(),
					policy.getTargetPort()));
		}

		// Step.2 从所有的映射策略中移除要删除的eip映射策略.
		allPolicies.removeAll(policies);

		System.out.println(generateFormatString(allPolicies));

		// Step.3
		sb.append("config firewall vipgrp").append(DEFAULT_SYMBOL);
		sb.append("edit ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set interface ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set member ").append(generateFormatString(allPolicies)).append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		// Step.4
		for (String policy : policies) {
			sb.append("config firewall vip").append(DEFAULT_SYMBOL);
			sb.append("delet ").append(policy).append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);
		}
		sb.append("quit").append(DEFAULT_SYMBOL);

		return sb.toString();
	}

	/**
	 * 
	 * 生成在<b>防火墙</b>执行的创建VPN User脚本.<br>
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
	public String createVPNUser(VPNUserParameter parameter) {

		StringBuilder sb = new StringBuilder();

		List<String> dstaddrs = Lists.newArrayList();

		/*
		 * 如果是单IP,则接下来的两个参数应该为: 单IP 和 255.255.255.255
		 * 
		 * 如果是网关,则接下来的两个参数应该为: 网关 和 255.255.255.0
		 */

		for (String ipaddress : parameter.getIpaddress()) {
			sb.append("config firewall address").append(DEFAULT_SYMBOL);
			sb.append("edit ").append("\"").append(generateAddressNameByIP(ipaddress)).append("\"")
					.append(DEFAULT_SYMBOL);
			sb.append("set subnet ").append(ipaddress).append(" 255.255.255.255").append(DEFAULT_SYMBOL);
			sb.append("next").append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);

			dstaddrs.add(generateAddressNameByIP(ipaddress));
		}

		for (String segment : parameter.getSegments()) {
			sb.append("config firewall address").append(DEFAULT_SYMBOL);
			sb.append("edit ").append("\"").append(generateAddressNameBySegment(segment)).append("\"")
					.append(DEFAULT_SYMBOL);
			sb.append("set subnet ").append(segment).append(" 255.255.255.0").append(DEFAULT_SYMBOL);
			sb.append("next").append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);

			dstaddrs.add(generateAddressNameBySegment(segment));
		}

		sb.append("config user local").append(DEFAULT_SYMBOL);
		sb.append("edit ").append("\"").append(parameter.getVpnUser()).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set type password ").append(DEFAULT_SYMBOL);
		sb.append("edit passwd ").append(parameter.getVpnPassword()).append(DEFAULT_SYMBOL);
		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("config user group").append(DEFAULT_SYMBOL);
		sb.append("edit ").append("\"").append(generateVlanGroupName(parameter)).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set sslvpn-portal ").append("\"").append(FIREWALL_SSLVPN_PORTAL).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set member ").append("\"").append(parameter.getVpnUser()).append("\"").append(DEFAULT_SYMBOL);
		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("config firewall policy").append(DEFAULT_SYMBOL);
		sb.append("edit ").append(parameter.getFirewallPolicyId()).append(DEFAULT_SYMBOL);
		sb.append("set srcintf ").append("\"").append(FIREWALL_SRCINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set dstintf ").append("\"").append(FIREWALL_DSTINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set srcaddr ").append("\"").append(FIREWALL_SRCADDR).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set dstaddr ").append(generateFormatString(dstaddrs)).append(DEFAULT_SYMBOL);
		sb.append("set action ssl-vpn").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("config identity-based-policy").append(DEFAULT_SYMBOL);
		sb.append("edit ").append(FIREWALL_IDENTITY_BASED_POLICY_ID).append(DEFAULT_SYMBOL);
		sb.append("set schedule ").append("\"").append(FIREWALL_SCHEDULE).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set groups ").append("\"").append(generateVlanGroupName(parameter)).append("\"")
				.append(DEFAULT_SYMBOL);
		sb.append("set service ").append("\"").append(FIREWALL_SERVICE).append("\"").append(DEFAULT_SYMBOL);
		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append("quit").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		return sb.toString();
	}

	/**
	 * 
	 * 生成在<b>防火墙</b>执行的在VPNUser组增加或删除一个IP或网段的脚本.<br>
	 * 
	 * <b>注:segments 和 ipaddress 集合中,包含的应该是用户所有的segment和ip.<br>
	 * 调用接口前,如果想执行新增操作,需要查询出用户所有的可访问段集合,再将新增的访问段add至集合.<br>
	 * 如果想执行删除操作,需要查询出用户所有的可访问段集合,再将需要删除的的访问段remove出集合.<br>
	 * </b>
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
	public String changeAccesssAddressIntoVPNUser(VPNUserParameter parameter) {

		/*
		 * 1.增加要访问的地址段
		 * 
		 * 2.在编辑原来用户组的策略，在要访问的目的地址，增加要访问地址段
		 */

		StringBuilder sb = new StringBuilder();

		List<String> dstaddrs = Lists.newArrayList();

		// Step.1

		/*
		 * 如果是单IP,则接下来的两个参数应该为: 单IP 和 255.255.255.255
		 * 
		 * 如果是网关,则接下来的两个参数应该为: 网关 和 255.255.255.0
		 */
		for (String ipaddress : parameter.getIpaddress()) {
			sb.append("config firewall address").append(DEFAULT_SYMBOL);
			sb.append("edit ").append("\"").append(generateAddressNameByIP(ipaddress)).append("\"")
					.append(DEFAULT_SYMBOL);
			sb.append("set subnet ").append(ipaddress).append(" 255.255.255.255").append(DEFAULT_SYMBOL);
			sb.append("next").append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);

			dstaddrs.add(generateAddressNameByIP(ipaddress));
		}

		for (String segment : parameter.getSegments()) {
			sb.append("config firewall address").append(DEFAULT_SYMBOL);
			sb.append("edit ").append("\"").append(generateAddressNameBySegment(segment)).append("\"")
					.append(DEFAULT_SYMBOL);
			sb.append("set subnet ").append(segment).append(" 255.255.255.0").append(DEFAULT_SYMBOL);
			sb.append("next").append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);

			dstaddrs.add(generateAddressNameBySegment(segment));
		}

		// Step.2
		sb.append("config firewall policy").append(DEFAULT_SYMBOL);
		sb.append("edit ").append(parameter.getFirewallPolicyId()).append(DEFAULT_SYMBOL);
		sb.append("set srcintf ").append("\"").append(FIREWALL_SRCINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set dstintf ").append("\"").append(FIREWALL_DSTINTF).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set srcaddr ").append("\"").append(FIREWALL_SRCADDR).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set dstaddr ").append(generateFormatString(dstaddrs)).append(DEFAULT_SYMBOL);

		sb.append("set action ssl-vpn").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("config identity-based-policy").append(DEFAULT_SYMBOL);
		sb.append("edit ").append(FIREWALL_IDENTITY_BASED_POLICY_ID).append(DEFAULT_SYMBOL);
		sb.append("set schedule ").append("\"").append(FIREWALL_SCHEDULE).append("\"").append(DEFAULT_SYMBOL);
		sb.append("set groups ").append("\"").append(generateVlanGroupName(parameter)).append("\"")
				.append(DEFAULT_SYMBOL);
		sb.append("set service ").append("\"").append(FIREWALL_SERVICE).append("\"").append(DEFAULT_SYMBOL);
		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		sb.append("next").append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append("quit").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		return sb.toString();
	}

	/**
	 * 生成在<b>防火墙</b>执行的删除VPNUser的脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * config firewall policy
	 * delete 2000
	 * end
	 * 
	 * config user group
	 * delete vlan80-gr
	 * end
	 * 
	 * config user local
	 * delete liukai
	 * end
	 * 
	 * config firewall address
	 * delete 172.20.19.1/32
	 * end
	 * 
	 * config firewall address
	 * delete 172.20.17.0/24
	 * end
	 * 
	 * config firewall address
	 * delete 172.20.18.0/24
	 * end
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter}
	 * @return
	 */
	public String deleteVPNUser(VPNUserParameter parameter) {

		/*
		 * 1.删除租户VPN策略
		 * 
		 * 2.删除租户VPN用户组
		 * 
		 * 3.删除VPN用户名和VPN密码
		 * 
		 * 4.删除VPN要访问IP地址池
		 */

		StringBuilder sb = new StringBuilder();

		List<String> addressList = Lists.newArrayList();

		// Step.1

		/*
		 * 如果是单IP,则接下来的两个参数应该为: 单IP 和 255.255.255.255
		 * 
		 * 如果是网关,则接下来的两个参数应该为: 网关 和 255.255.255.0
		 */
		for (String ipaddress : parameter.getIpaddress()) {
			addressList.add(generateAddressNameByIP(ipaddress));
		}

		for (String segment : parameter.getSegments()) {
			addressList.add(generateAddressNameBySegment(segment));
		}

		// Step.1
		sb.append("config firewall policy").append(DEFAULT_SYMBOL);
		sb.append("delete ").append(parameter.getFirewallPolicyId()).append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		// Step.2
		sb.append("config user group").append(DEFAULT_SYMBOL);
		sb.append("delete ").append(generateVlanGroupName(parameter)).append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		// Step.3
		sb.append("config user local").append(DEFAULT_SYMBOL);
		sb.append("delete ").append(parameter.getVpnUser()).append(DEFAULT_SYMBOL);
		sb.append("end").append(DEFAULT_SYMBOL);
		sb.append(DEFAULT_SYMBOL);

		// step.4
		for (String address : addressList) {
			sb.append("config firewall address").append(DEFAULT_SYMBOL);
			sb.append("delete ").append(address).append(DEFAULT_SYMBOL);
			sb.append("end").append(DEFAULT_SYMBOL);
			sb.append(DEFAULT_SYMBOL);
		}

		sb.append("quit").append(DEFAULT_SYMBOL);

		return sb.toString();
	}

}
