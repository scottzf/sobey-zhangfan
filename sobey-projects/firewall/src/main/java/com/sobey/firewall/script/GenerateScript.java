package com.sobey.firewall.script;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.core.utils.Collections3;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.firewall.constans.SymbolEnum;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.EIPPolicyParameter;

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
	protected static PropertiesLoader FIREWALL_LOADER = new PropertiesLoader("classpath:/firewall.properties");

	/* 脚本参数 */

	/**
	 * extintf
	 */
	protected static final String FIREWALL_EXTINTF = FIREWALL_LOADER.getProperty("FIREWALL_EXTINTF");

	/**
	 * portforward
	 */
	protected static final String FIREWALL_PORTFORWARD = FIREWALL_LOADER.getProperty("FIREWALL_PORTFORWARD");

	/**
	 * 联通
	 */
	protected static final String FIREWALL_CNC = FIREWALL_LOADER.getProperty("FIREWALL_CNC");

	/**
	 * 电信
	 */
	protected static final String FIREWALL_CTC = FIREWALL_LOADER.getProperty("FIREWALL_CTC");

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
	 * 对集合里的对象进行组合,生成指定格式的字符串.<br>
	 * 
	 * Example:<b> "119.6.200.219-tcp-8080" "119.6.200.219-tcp-80" </b>
	 * 
	 * @param list
	 *            集合
	 * @return
	 */
	private static String generateFormatString(List<String> list) {
		return Collections3.convertToString(list, "\"", "\" ");
	}

	/**
	 * 根据EIPParameter中的ISP属性获得VIP组.
	 * 
	 * ISP运营商 0: 中国电信 1:中国联通
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @return
	 */
	private static String getVipgrpByISP(EIPParameter parameter) {
		return "0".equals(parameter.getIsp()) ? FIREWALL_CTC : FIREWALL_CNC;
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
			sb.append("edit").append(" ").append("\"").append(vipName).append("\"").append(symbol);
			sb.append("set extip").append(" ").append(parameter.getInternetIP()).append(symbol);
			sb.append("set extintf").append(" ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
			sb.append("set portforward").append(" ").append(FIREWALL_PORTFORWARD).append(symbol);
			sb.append("set mappedip").append(" ").append(parameter.getPrivateIP()).append(symbol);

			// 当协议为udp时,增加协议的设置,为tcp时,不需要设置.
			if ("udp".equalsIgnoreCase(policy.getProtocolText())) {
				sb.append("set protocol udp").append(symbol);
			}
			sb.append("set extport").append(" ").append(policy.getSourcePort()).append(symbol);
			sb.append("set mappedport").append(" ").append(policy.getTargetPort()).append(symbol);
			sb.append("next").append(symbol);
			sb.append("end").append(symbol);
			sb.append(symbol);
		}

		sb.append("config firewall vipgrp").append(symbol);
		sb.append("edit").append(" ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(symbol);
		sb.append("set interface").append(" ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
		sb.append("set member").append(" ").append(generateFormatString(allPolicies)).append(symbol);
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
		sb.append("edit").append(" ").append("\"").append(getVipgrpByISP(parameter)).append("\"").append(symbol);
		sb.append("set interface").append(" ").append("\"").append(FIREWALL_EXTINTF).append("\"").append(symbol);
		sb.append("set member").append(" ").append(generateFormatString(allPolicies)).append(symbol);
		sb.append("end").append(symbol);
		sb.append(symbol);

		// Step.4
		for (String policy : policies) {
			sb.append("config firewall vip").append(symbol);
			sb.append("edit").append(" ").append(policy).append(symbol);
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

}
