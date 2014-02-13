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
	 * add server 113.200.74.140 113.200.74.140
	 * 
	 * add gslb service 113.142.30.109-80 113.142.30.109 tcp 80 -publicIP 113.142.30.109 -publicPort 80 -maxClient 0 -siteName 113.142.30.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * add gslb service 113.200.74.140-80 113.200.74.140 tcp 80 -publicIP 113.200.74.140 -publicPort 80 -maxClient 0 -siteName 113.200.74.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * 
	 * bind lb monitor tcp 113.142.30.109-80
	 * bind lb monitor tcp 113.200.74.140-80
	 * 
	 * add gslb vserver mdnftp.sobeycache.com tcp -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * add gslb vserver mdnftp.sobeycache.com tcp -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.142.30.109-80
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.200.74.140-80
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -domainName mdnftp.sobeycache.com -TTL 5
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
		 * 4.配置vserver
		 * 
		 * 5.将service服务绑定在vserver上
		 * 
		 * 6.将vserver和域名进行绑定
		 */

		StringBuilder sb = new StringBuilder();

		// 协议
		String protocol = "";

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			// Step.1
			sb.append("add server ").append(ipParameter.getIpaddress()).append(" ").append(ipParameter.getIpaddress())
					.append(symbol);
		}

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

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

				// 获得dns的协议,避免Step4重复循环.
				protocol = dnsPolicyParameter.getProtocolText();
			}

		}

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
			for (DNSPolicyParameter dnsPolicyParameter : ipParameter.getPolicyParameters()) {
				// Step.3
				sb.append("bind lb monitor tcp ").append(generateServiceName(ipParameter, dnsPolicyParameter))
						.append(symbol);
			}
		}

		// Step.4
		sb.append("add ").append(dnsParameter.getDomianType()).append(" vserver ").append(dnsParameter.getDomianName())
				.append(" ").append(protocol).append(" -lbMethod ").append(DNS_LBMETHOD).append(" -backupLBMethod ")
				.append(DNS_BACKUP).append(" -tolerance ").append(DNS_TOLERANCE).append(" -appflowLog ")
				.append(DNS_APPFLOWLOG).append(symbol);

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
			for (DNSPolicyParameter dnsPolicyParameter : ipParameter.getPolicyParameters()) {
				// Step.5
				sb.append("bind ").append(dnsParameter.getDomianType()).append(" vserver ")
						.append(dnsParameter.getDomianName()).append(" -serviceName ")
						.append(generateServiceName(ipParameter, dnsPolicyParameter)).append(symbol);
			}
		}

		// Step.6
		sb.append("bind ").append(dnsParameter.getDomianType()).append(" vserver ")
				.append(dnsParameter.getDomianName()).append(" -domainName ").append(dnsParameter.getDomianName())
				.append(" -TTL ").append(DNS_TTL).append(symbol);

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
	 * add server 113.200.74.140 113.200.74.140
	 * 
	 * add gslb service 113.142.30.109-80 113.142.30.109 tcp 80 -publicIP 113.142.30.109 -publicPort 80 -maxClient 0 -siteName 113.142.30.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * add gslb service 113.200.74.140-80 113.200.74.140 tcp 80 -publicIP 113.200.74.140 -publicPort 80 -maxClient 0 -siteName 113.200.74.xxx -cltTimeout 180 -svrTimeout 360 -downStateFlush DISABLED
	 * 
	 * bind lb monitor tcp 113.142.30.109-80
	 * bind lb monitor tcp 113.200.74.140-80
	 * 
	 * add gslb vserver mdnftp.sobeycache.com tcp -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * add gslb vserver mdnftp.sobeycache.com tcp -lbMethod STATICPROXIMITY  -backupLBMethod ROUNDROBIN -tolerance 0 -appflowLog DISABLED
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.142.30.109-80
	 * bind gslb vserver mdnftp.sobeycache.com -serviceName 113.200.74.140-80
	 * 
	 * bind gslb vserver mdnftp.sobeycache.com -domainName mdnftp.sobeycache.com -TTL 5
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
	 * 
	 * rm server 113.200.74.140
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
		 * 2.删除域名和IP对应关系
		 */

		StringBuilder sb = new StringBuilder();

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			// Step.1
			sb.append("rm server ").append(ipParameter.getIpaddress()).append(symbol);

		}

		// Step.2
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
	 * 
	 * rm server 113.200.74.140
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
