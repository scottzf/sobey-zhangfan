package com.sobey.dns.script;

import org.apache.commons.lang3.StringUtils;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.dns.constans.SymbolEnum;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DNSPolicyParameter;
import com.sobey.dns.webservice.response.dto.DNSPublicIPParameter;

/**
 * DNS 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	/**
	 * maxClient
	 */
	private static final String DNS_MAXCLIENT = DNS_LOADER.getProperty("DNS_MAXCLIENT");

	/**
	 * cltTimeout
	 */
	private static final String DNS_CLTTIMEOUT = DNS_LOADER.getProperty("DNS_CLTTIMEOUT");

	/**
	 * svrTimeout
	 */
	private static final String DNS_SVRTIMEOUT = DNS_LOADER.getProperty("DNS_SVRTIMEOUT");

	/**
	 * downStateFlush
	 */
	private static final String DNS_DOWNSTATEFLUSH = DNS_LOADER.getProperty("DNS_DOWNSTATEFLUSH");

	/**
	 * TTL
	 */
	private static final String DNS_TTL = DNS_LOADER.getProperty("DNS_TTL");

	/**
	 * lbMethod
	 */
	private static final String DNS_LBMETHOD = DNS_LOADER.getProperty("DNS_LBMETHOD");

	/**
	 * backup
	 */
	private static final String DNS_BACKUP = DNS_LOADER.getProperty("DNS_BACKUP");

	/**
	 * tolerance
	 */
	private static final String DNS_TOLERANCE = DNS_LOADER.getProperty("DNS_TOLERANCE");

	/**
	 * appflowLog
	 */
	private static final String DNS_APPFLOWLOG = DNS_LOADER.getProperty("DNS_APPFLOWLOG");

	/**
	 * 生成 siteName. <br>
	 * 
	 * 保留外网IP前三位,用"xxx"替换结尾. <br>
	 * 
	 * Example: <b>113.142.30.xxx</b>
	 * 
	 * @param ipParameter
	 *            {@link DNSPublicIPParameter}
	 * @return
	 */
	private static String generateSiteName(DNSPublicIPParameter ipParameter) {

		// 获得最后一个"."的索引
		int index = StringUtils.lastIndexOf(ipParameter.getIpaddress(), ".");

		return StringUtils.substring(ipParameter.getIpaddress(), 0, index + 1) + "xxx";
	}

	/**
	 * 生成 ServiceName. <br>
	 * 
	 * 格式:外网IP-端口. <br>
	 * 
	 * Example: <b>113.142.30.108-80</b>
	 * 
	 * 
	 * @param ipParameter
	 *            {@link DNSPublicIPParameter}
	 * @param dnsPolicyParameter
	 *            {@linkplain DNSPolicyParameter}
	 * @return
	 */
	private static String generateServiceName(DNSPublicIPParameter ipParameter, DNSPolicyParameter dnsPolicyParameter) {
		return ipParameter.getIpaddress() + "-" + dnsPolicyParameter.getTargetPort();
	}

	/**
	 * 
	 * 生成在<b>DNS</b>执行的创建dns脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * add server 113.142.30.109 113.142.30.109
	 * add gslb service 113.142.30.109-80 113.142.30.109 HTTP 80 -publicIP 113.142.30.109 -publicPort 80 -maxClient 0 -siteName 113.142.30.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * bind lb monitor tcp 113.142.30.109
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.142.30.109
	 * 
	 * add server 113.200.74.140 113.200.74.140
	 * add gslb service 113.200.74.140-80 113.200.74.140 HTTP 80 -publicIP 113.200.74.140 -publicPort 80 -maxClient 0 -siteName 113.200.74.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * bind lb monitor tcp 113.200.74.140
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.200.74.140
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -domainName mdnftp.sobeycache.com -TTL 5
	 * 
	 * add gslb vserver mdnftp.sobeycache.com HTTP -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * set gslb vserver mdnftp.sobeycache.com HTTP -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -appflowLog DISABLED
	 * </pre>
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @param symbol
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateCreateDNSScript(DNSParameter dnsParameter, String symbol) {

		/*
		 * 1.增加Server服务器配置
		 * 
		 * 2.增加services服务配置包括IP地址、服务端口
		 * 
		 * 3.配置状态检测协议(munitors)服务.选择状态检测TCP协议
		 * 
		 * 4.配置DNS域名和解析服务IP地址对应关系
		 * 
		 * 5.配置解决协议http,配置DNS解析策略为静态策略
		 */

		StringBuilder sb = new StringBuilder();

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			// Step.1
			sb.append("add server ").append(ipParameter.getIpaddress()).append(" ").append(ipParameter.getIpaddress())
					.append(symbol);

			// Step.2
			for (DNSPolicyParameter dnsPolicyParameter : ipParameter.getPolicyParameters()) {
				sb.append("add ").append(dnsParameter.getDomianType()).append(" service ")
						.append(generateServiceName(ipParameter, dnsPolicyParameter)).append(" ")
						.append(ipParameter.getIpaddress()).append(" ").append(dnsPolicyParameter.getProtocolText())
						.append(" ").append(dnsPolicyParameter.getSourcePort()).append(" -publicIP ")
						.append(ipParameter.getIpaddress()).append(" -publicPort ")
						.append(dnsPolicyParameter.getTargetPort()).append(" -maxClient ").append(DNS_MAXCLIENT)
						.append(" -siteName ").append(generateSiteName(ipParameter)).append(" -cltTimeout ")
						.append(DNS_CLTTIMEOUT).append(" -svrTimeout ").append(DNS_SVRTIMEOUT)
						.append(" -downStateFlush ").append(DNS_DOWNSTATEFLUSH).append(symbol);
			}

			// Step.3
			sb.append("bind lb monitor tcp ").append(ipParameter.getIpaddress()).append(symbol);
			sb.append("bind ").append(dnsParameter.getDomianType()).append(" vserver ")
					.append(dnsParameter.getDomianName()).append(" -serviceName ").append(ipParameter.getIpaddress())
					.append(symbol);
		}

		// Step.4
		sb.append("bind ").append(dnsParameter.getDomianType()).append(" vserver ")
				.append(dnsParameter.getDomianName()).append(" -domainName ").append(dnsParameter.getDomianName())
				.append(" -TTL ").append(DNS_TTL).append(symbol);

		// Step.5
		sb.append("add ").append(dnsParameter.getDomianType()).append(" vserver ").append(dnsParameter.getDomianName())
				.append(" HTTP").append(" -lbMethod ").append(DNS_LBMETHOD).append(" -backupLBMethod ")
				.append(DNS_BACKUP).append(" -tolerance ").append(DNS_TOLERANCE).append(" -appflowLog ")
				.append(DNS_APPFLOWLOG).append(symbol);

		sb.append("set ").append(dnsParameter.getDomianType()).append(" vserver ").append(dnsParameter.getDomianName())
				.append(" HTTP").append(" -lbMethod ").append(DNS_LBMETHOD).append(" -backupLBMethod ")
				.append(DNS_BACKUP).append(" -appflowLog ").append(DNS_APPFLOWLOG).append(symbol);

		return sb.toString();
	}

	/**
	 * 
	 * 生成在<b>DNS</b>执行的创建dns脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * add server 113.142.30.109 113.142.30.109
	 * add gslb service 113.142.30.109-80 113.142.30.109 HTTP 80 -publicIP 113.142.30.109 -publicPort 80 -maxClient 0 -siteName 113.142.30.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * bind lb monitor tcp 113.142.30.109
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.142.30.109
	 * 
	 * add server 113.200.74.140 113.200.74.140
	 * add gslb service 113.200.74.140-80 113.200.74.140 HTTP 80 -publicIP 113.200.74.140 -publicPort 80 -maxClient 0 -siteName 113.200.74.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * bind lb monitor tcp 113.200.74.140
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.200.74.140
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -domainName mdnftp.sobeycache.com -TTL 5
	 * 
	 * add gslb vserver mdnftp.sobeycache.com HTTP -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * set gslb vserver mdnftp.sobeycache.com HTTP -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -appflowLog DISABLED
	 * </pre>
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @return
	 */
	public static String generateCreateDNSScript(DNSParameter dnsParameter) {
		return generateCreateDNSScript(dnsParameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>DNS</b>执行的删除dns脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm server 113.142.30.109
	 * rm service 113.142.30.109-80
	 * 
	 * rm server 113.200.74.140
	 * rm service 113.200.74.140-80
	 * 
	 * rm gslb vserver mdnftp.sobeycache.com
	 * save ns config
	 * </pre>
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @param symbol
	 *            (用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteDNSScript(DNSParameter dnsParameter, String symbol) {

		/*
		 * 1.删除servers服务
		 * 
		 * 2.删除services服务
		 * 
		 * 3.删除域名和IP对应关系
		 */

		StringBuilder sb = new StringBuilder();

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			// Step.1
			sb.append("rm server ").append(ipParameter.getIpaddress()).append(symbol);

			// Step.2
			for (DNSPolicyParameter dnsPolicyParameter : ipParameter.getPolicyParameters()) {
				sb.append("rm service ").append(generateServiceName(ipParameter, dnsPolicyParameter)).append(symbol);
			}
		}

		// Step.3
		sb.append("rm ").append(dnsParameter.getDomianType()).append(" vserver ").append(dnsParameter.getDomianName())
				.append(symbol);
		sb.append("save ns config").append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>DNS</b>执行的删除dns脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * rm server 113.142.30.109
	 * rm service 113.142.30.109-80
	 * 
	 * rm server 113.200.74.140
	 * rm service 113.200.74.140-80
	 * 
	 * rm gslb vserver mdnftp.sobeycache.com
	 * save ns config
	 * </pre>
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @return
	 */
	public static String generateDeleteDNSScript(DNSParameter dnsParameter) {
		return generateDeleteDNSScript(dnsParameter, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

}
