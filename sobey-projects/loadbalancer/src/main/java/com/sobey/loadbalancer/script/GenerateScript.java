package com.sobey.loadbalancer.script;

import java.util.List;

import com.google.common.collect.Lists;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.loadbalancer.constans.SymbolEnum;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPolicyParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPublicIPParameter;

/**
 * LOADBALANCER 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader LOADBALANCER_LOADER = new PropertiesLoader("classpath:/loadbalancer.properties");

	/**
	 * gslb
	 */
	private static final String LOADBALANCER_GSLB = LOADBALANCER_LOADER.getProperty("LOADBALANCER_GSLB");

	/**
	 * maxClient
	 */
	private static final String LOADBALANCER_MAXCLIENT = LOADBALANCER_LOADER.getProperty("LOADBALANCER_MAXCLIENT");

	/**
	 * maxReq
	 */
	private static final String LOADBALANCER_MAXREQ = LOADBALANCER_LOADER.getProperty("LOADBALANCER_MAXREQ");

	/**
	 * cip
	 */
	private static final String LOADBALANCER_CIP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_CIP");

	/**
	 * usip
	 */
	private static final String LOADBALANCER_USIP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_USIP");

	/**
	 * useproxyport
	 */
	private static final String LOADBALANCER_USEPROXYPORT = LOADBALANCER_LOADER
			.getProperty("LOADBALANCER_USEPROXYPORT");

	/**
	 * sp
	 */
	private static final String LOADBALANCER_SP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_SP");

	/**
	 * cltTimeout
	 */
	private static final String LOADBALANCER_CLTTIMEOUT = LOADBALANCER_LOADER.getProperty("LOADBALANCER_CLTTIMEOUT");

	/**
	 * svrTimeout
	 */
	private static final String LOADBALANCER_SVRTIMEOUT = LOADBALANCER_LOADER.getProperty("LOADBALANCER_SVRTIMEOUT");

	/**
	 * lbMethod
	 */
	private static final String LOADBALANCER_LBMETHOD = LOADBALANCER_LOADER.getProperty("LOADBALANCER_LBMETHOD");

	/**
	 * CKA
	 */
	private static final String LOADBALANCER_CKA = LOADBALANCER_LOADER.getProperty("LOADBALANCER_CKA");

	/**
	 * TCPB
	 */
	private static final String LOADBALANCER_TCPB = LOADBALANCER_LOADER.getProperty("LOADBALANCER_TCPB");

	/**
	 * CMP
	 */
	private static final String LOADBALANCER_CMP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_CMP");

	/**
	 * persistenceType
	 */
	private static final String LOADBALANCER_PERSISTENCETYPE = LOADBALANCER_LOADER
			.getProperty("LOADBALANCER_PERSISTENCETYPE");

	/**
	 * 生成 ServiceName. <br>
	 * 生成规则: IP-protocolText-Port<br>
	 * Example:<b> 119.6.200.219-tcp-8080 </b>
	 * 
	 * @param ip
	 *            IP地址
	 * @param protocolText
	 *            协议
	 * @param port
	 *            目标端口
	 * @return
	 */
	private static String generateServiceName(String ip, String protocolText, Integer port) {
		return ip + "-" + protocolText + "-" + port;
	}

	/**
	 * 
	 * 生成在<b>Loadbalancer</b>执行的创建ELB脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * add server 172.20.0.94 172.20.0.94
	 * add server 172.20.0.99 172.20.0.99
	 * 
	 * add service 172.20.0.94-tcp-8080 172.20.0.94 tcp 8080 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.94-tcp-80 172.20.0.94 tcp 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.99-tcp-8080 172.20.0.99 tcp 8080 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.99-tcp-80 172.20.0.99 tcp 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * 
	 * bind lb monitor tcp 172.20.0.94-tcp-8080
	 * bind lb monitor tcp 172.20.0.94-tcp-80
	 * bind lb monitor tcp 172.20.0.99-tcp-8080
	 * bind lb monitor tcp 172.20.0.99-tcp-80
	 * 
	 * add lb vserver 10.0.8.72-tcp-8080 tcp 10.0.8.72 8080 -persistenceType COOKIEINSERT -lbMethod ROUNDROBIN -cltTimeout 180
	 * add lb vserver 10.0.8.72-tcp-80 tcp 10.0.8.72 80 -persistenceType COOKIEINSERT -lbMethod ROUNDROBIN -cltTimeout 180
	 * 
	 * bind lb vserver 10.0.8.72-tcp-8080 172.20.0.94-tcp-8080
	 * bind lb vserver 10.0.8.72-tcp-80 172.20.0.94-tcp-80
	 * bind lb vserver 10.0.8.72-tcp-8080 172.20.0.99-tcp-8080
	 * bind lb vserver 10.0.8.72-tcp-80 172.20.0.99-tcp-80
	 * 
	 * </pre>
	 * 
	 * @param ELBParameter
	 *            {@link elbParameter}
	 * @param symbol
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateCreateELBScript(ELBParameter elbParameter, String symbol) {

		/*
		 * 1.增加Server服务器配置
		 * 
		 * 2.增加services服务配置包括IP地址、服务端口
		 * 
		 * 3.增加监控
		 * 
		 * 4.配置Vserver添加Vserver,先判断Vserver是否存在
		 * 
		 * 5.将相关services加入到Vserver中
		 */

		StringBuilder sb = new StringBuilder();

		// TODO 查询出需要保存的vipName 待完成. 思路:将vipName保存至嵌入式数据库中
		List<String> list = Lists.newArrayList();

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			// Step.1
			sb.append("add server ").append(ipParameter.getIpaddress()).append(" ").append(ipParameter.getIpaddress())
					.append(symbol);
		}
		sb.append(symbol);

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				// Step.2
				sb.append("add service ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(" ").append(ipParameter.getIpaddress())
						.append(" ").append(policyParameter.getProtocolText()).append(" ")
						.append(policyParameter.getSourcePort()).append(" -gslb ").append(LOADBALANCER_GSLB)
						.append(" -maxClient ").append(LOADBALANCER_MAXCLIENT).append(" -maxReq ")
						.append(LOADBALANCER_MAXREQ).append(" -cip ").append(LOADBALANCER_CIP).append(" -usip ")
						.append(LOADBALANCER_USIP).append(" -useproxyport ").append(LOADBALANCER_USEPROXYPORT)
						.append(" -sp ").append(LOADBALANCER_SP).append(" -cltTimeout ")
						.append(LOADBALANCER_CLTTIMEOUT).append(" -svrTimeout ").append(LOADBALANCER_SVRTIMEOUT)
						.append(" -CKA ").append(LOADBALANCER_CKA).append(" -TCPB ").append(LOADBALANCER_TCPB)
						.append(" -CMP ").append(LOADBALANCER_CMP).append(symbol);

			}
		}

		sb.append(symbol);

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				// Step.3
				sb.append("bind lb monitor tcp ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(symbol);
			}
		}

		sb.append(symbol);

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				String vipName = generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getTargetPort());

				// 判断 Vserver存在的话,跳过,如果不存在,创建新的Vserver
				if (!list.contains(vipName)) {
					// Step.4
					sb.append("add lb vserver ").append(vipName).append(" ").append(policyParameter.getProtocolText())
							.append(" ").append(elbParameter.getVip()).append(" ")
							.append(policyParameter.getTargetPort()).append(" -persistenceType ")
							.append(LOADBALANCER_PERSISTENCETYPE).append(" -lbMethod ").append(LOADBALANCER_LBMETHOD)
							.append(" -cltTimeout ").append(LOADBALANCER_CLTTIMEOUT).append(symbol);

					list.add(vipName);
				}
			}
		}

		sb.append(symbol);

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				// Step.5
				sb.append("bind lb vserver ")
						.append(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
								policyParameter.getTargetPort()))
						.append(" ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(symbol);
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * 生成在<b>Loadbalancer</b>执行的创建ELB脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * add server 172.20.0.94 172.20.0.94
	 * add server 172.20.0.99 172.20.0.99
	 * 
	 * add service 172.20.0.94-tcp-8080 172.20.0.94 tcp 8080 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.94-tcp-80 172.20.0.94 tcp 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.99-tcp-8080 172.20.0.99 tcp 8080 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * add service 172.20.0.99-tcp-80 172.20.0.99 tcp 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA YES -TCPB NO -CMP NO
	 * 
	 * bind lb monitor tcp 172.20.0.94-tcp-8080
	 * bind lb monitor tcp 172.20.0.94-tcp-80
	 * bind lb monitor tcp 172.20.0.99-tcp-8080
	 * bind lb monitor tcp 172.20.0.99-tcp-80
	 * 
	 * add lb vserver 10.0.8.72-tcp-8080 tcp 10.0.8.72 8080 -persistenceType COOKIEINSERT -lbMethod ROUNDROBIN -cltTimeout 180
	 * add lb vserver 10.0.8.72-tcp-80 tcp 10.0.8.72 80 -persistenceType COOKIEINSERT -lbMethod ROUNDROBIN -cltTimeout 180
	 * 
	 * bind lb vserver 10.0.8.72-tcp-8080 172.20.0.94-tcp-8080
	 * bind lb vserver 10.0.8.72-tcp-80 172.20.0.94-tcp-80
	 * bind lb vserver 10.0.8.72-tcp-8080 172.20.0.99-tcp-8080
	 * bind lb vserver 10.0.8.72-tcp-80 172.20.0.99-tcp-80
	 * 
	 * </pre>
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @return
	 */
	public static String generateCreateELBScript(ELBParameter elbParameter) {
		return generateCreateELBScript(elbParameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * * 生成在<b>Loadbalancer</b>执行的删除ELB脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm server 172.20.0.94
	 * rm service 172.20.0.94-tcp-8080
	 * rm lb vserver 10.0.8.72-tcp-8080 172.20.0.94-tcp-8080
	 * rm service 172.20.0.94-tcp-80
	 * rm lb vserver 10.0.8.72-tcp-80 172.20.0.94-tcp-80
	 * 
	 * rm server 172.20.0.99
	 * rm service 172.20.0.99-tcp-8080
	 * rm lb vserver 10.0.8.72-tcp-8080 172.20.0.99-tcp-8080
	 * rm service 172.20.0.99-tcp-80
	 * rm lb vserver 10.0.8.72-tcp-80 172.20.0.99-tcp-80
	 * </pre>
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @param symbol
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteELBScript(ELBParameter elbParameter, String symbol) {
		/*
		 * 1.删除servers服务
		 * 
		 * 2.删除services服务
		 * 
		 * 3.删除Vserver VIP与内网主机对应关系
		 */

		StringBuilder sb = new StringBuilder();

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			// Step.1
			sb.append("rm server ").append(ipParameter.getIpaddress()).append(symbol);

			// Step.2
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				sb.append("rm service ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(symbol);

				// Step.3
				sb.append("rm lb vserver ")
						.append(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
								policyParameter.getTargetPort()))
						.append(" ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(symbol);
			}
			sb.append(symbol);
		}

		return sb.toString();
	}

	/**
	 * 生成在<b>Loadbalancer</b>执行的删除ELB脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm server 172.20.0.94
	 * rm service 172.20.0.94-tcp-8080
	 * rm lb vserver 10.0.8.72-tcp-8080 172.20.0.94-tcp-8080
	 * rm service 172.20.0.94-tcp-80
	 * rm lb vserver 10.0.8.72-tcp-80 172.20.0.94-tcp-80
	 * 
	 * rm server 172.20.0.99
	 * rm service 172.20.0.99-tcp-8080
	 * rm lb vserver 10.0.8.72-tcp-8080 172.20.0.99-tcp-8080
	 * rm service 172.20.0.99-tcp-80
	 * rm lb vserver 10.0.8.72-tcp-80 172.20.0.99-tcp-80
	 * </pre>
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @return
	 */
	public static String generateDeleteELBScript(ELBParameter elbParameter) {
		return generateDeleteELBScript(elbParameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>Loadbalancer</b>执行的删除ELB端口脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm service 172.20.0.94-tcp-8080
	 * rm service 172.20.0.94-tcp-80
	 * 
	 * rm service 172.20.0.99-tcp-8080
	 * rm service 172.20.0.99-tcp-80
	 * </pre>
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @param symbol
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteELBPortScript(ELBParameter elbParameter, String symbol) {
		/*
		 * 
		 * 1.删除services服务
		 */

		StringBuilder sb = new StringBuilder();

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			// Step.1
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				sb.append("rm service ")
						.append(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort())).append(symbol);

			}
			sb.append(symbol);
		}

		return sb.toString();
	}

	/**
	 * 生成在<b>Loadbalancer</b>执行的删除ELB端口脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm service 172.20.0.94-tcp-8080
	 * rm service 172.20.0.94-tcp-80
	 * 
	 * rm service 172.20.0.99-tcp-8080
	 * rm service 172.20.0.99-tcp-80
	 * </pre>
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @return
	 */
	public static String generateDeleteELBPortScript(ELBParameter elbParameter) {
		return generateDeleteELBPortScript(elbParameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}
}
