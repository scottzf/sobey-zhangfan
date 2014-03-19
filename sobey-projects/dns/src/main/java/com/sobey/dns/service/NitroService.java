package com.sobey.dns.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.config.basic.server;
import com.citrix.netscaler.nitro.resource.config.basic.servicegroup_servicegroupmember_binding;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbservice;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver_domain_binding;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver_gslbservice_binding;
import com.citrix.netscaler.nitro.resource.config.ns.nsconfig;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DNSPolicyParameter;
import com.sobey.dns.webservice.response.dto.DNSPublicIPParameter;

@Service
public class NitroService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	/**
	 * IP
	 */
	private static final String DNS_IP = DNS_LOADER.getProperty("DNS_IP");

	/**
	 * Username
	 */
	private static final String DNS_USERNAME = DNS_LOADER.getProperty("DNS_USERNAME");

	/**
	 * password
	 */
	private static final String DNS_PASSWORD = DNS_LOADER.getProperty("DNS_PASSWORD");

	/**
	 * protocol
	 */
	private static final String DNS_PROTOCOL = DNS_LOADER.getProperty("DNS_PROTOCOL");

	/**
	 * server默认端口 80
	 */
	private static final String DNS_DEFAULT_SERVER_PORT = DNS_LOADER.getProperty("DNS_DEFAULT_SERVER_PORT");

	/**
	 * servicegroupname
	 */
	private static final String DNS_SERVICEGROUPNAME = DNS_LOADER.getProperty("DNS_SERVICEGROUPNAME");

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
	 * backupLBMethod
	 */
	private static final String DNS_BACKUPLBMETHOD = DNS_LOADER.getProperty("DNS_BACKUPLBMETHOD");

	/**
	 * tolerance
	 */
	private static final String DNS_TOLERANCE = DNS_LOADER.getProperty("DNS_TOLERANCE");

	/**
	 * appflowLog
	 */
	private static final String DNS_APPFLOWLOG = DNS_LOADER.getProperty("DNS_APPFLOWLOG");

	/**
	 * 创建DNS
	 * 
	 * @param dnsParameter
	 * @return
	 */
	public boolean createDns(DNSParameter dnsParameter) {

		nitro_service ns_session = null;

		try {

			ns_session = new nitro_service(DNS_IP, DNS_PROTOCOL); // 创建nitro的session
			ns_session.login(DNS_USERNAME, DNS_PASSWORD);// 登录

			nsconfig.save(ns_session);

			// Step.1 创建Servers
			createSevers(ns_session, dnsParameter);

			// Step.2 创建gslb service
			createGslbService(ns_session, dnsParameter);

			// Step.3 创建gslb vserver
			createGslbvserver(ns_session, dnsParameter);

			// Step.4 将vserver和domainName绑定
			createGslbvserverDomainBinding(ns_session, dnsParameter);

			// Step.5 将vserver和serviceName绑定
			createGslbvserverGslbserviceBinding(ns_session, dnsParameter);

			ns_session.logout();// 登出

		} catch (nitro_exception e) {
			System.out.println("设备链接失败");
			return false;
		} catch (Exception e) {
			System.out.println("登录失败");
			return false;
		}
		ns_session.set_timeout(3600);
		return true;
	}

	public boolean deleteDns(DNSParameter dnsParameter) {

		nitro_service ns_session = null;

		try {

			ns_session = new nitro_service(DNS_IP, DNS_PROTOCOL); // 创建nitro的session
			ns_session.login(DNS_USERNAME, DNS_PASSWORD);// 登录

			nsconfig.save(ns_session);

			// Step.1 删除gslb Service
			deleteGslbService(ns_session, dnsParameter);

			// Step.2 删除gslb vserver
			deleteGslbvserver(ns_session, dnsParameter);

			// Step.3 删除lb Servers
			deleteServers(ns_session, dnsParameter);

			ns_session.logout();// 登出

		} catch (nitro_exception e) {
			System.out.println("设备链接失败");
			return false;
		} catch (Exception e) {
			System.out.println("登录失败");
			return false;
		}
		ns_session.set_timeout(3600);
		return true;
	}

	/**
	 * 删除gslb Service
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void deleteGslbService(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {
		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
			for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				gslbservice.delete(ns_session,
						gslbservice.get(ns_session, generateServiceName(ipParameter, policyParameter)));

			}
		}
	}

	/**
	 * 删除gslb vserver
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void deleteGslbvserver(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {
		gslbvserver.delete(ns_session, gslbvserver.get(ns_session, dnsParameter.getDomianName()));
	}

	/**
	 * 删除lb Servers
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void deleteServers(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {
		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
			server.delete(ns_session, ipParameter.getIpaddress());
		}
	}

	/**
	 * 创建Servers
	 * 
	 * @param ns_session
	 * @param serverIP
	 * @throws Exception
	 */
	private static void createSevers(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {
		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
			servicegroup_servicegroupmember_binding obj = new servicegroup_servicegroupmember_binding();
			obj.set_servicegroupname(DNS_SERVICEGROUPNAME);
			obj.set_port(Integer.valueOf(DNS_DEFAULT_SERVER_PORT));
			obj.set_ip(ipParameter.getIpaddress());
			servicegroup_servicegroupmember_binding.add(ns_session, obj);
		}
	}

	/**
	 * 创建gslb service
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void createGslbService(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				gslbservice gsvc = new gslbservice();

				gsvc.set_servername(ipParameter.getIpaddress());
				gsvc.set_servicename(generateServiceName(ipParameter, policyParameter));
				gsvc.set_ipaddress(ipParameter.getIpaddress());
				gsvc.set_servicetype(policyParameter.getProtocolText());
				gsvc.set_publicip(ipParameter.getIpaddress());
				gsvc.set_port(policyParameter.getTargetPort());
				gsvc.set_sitename(generateSiteName(ipParameter));
				gsvc.set_maxclient(Integer.valueOf(DNS_MAXCLIENT));
				gsvc.set_clttimeout(Integer.valueOf(DNS_CLTTIMEOUT));
				gsvc.set_svrtimeout(Integer.valueOf(DNS_SVRTIMEOUT));
				gsvc.set_downstateflush(DNS_DOWNSTATEFLUSH);

				gslbservice.add(ns_session, gsvc);

			}

		}
	}

	/**
	 * 创建gslb vserver
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void createGslbvserver(nitro_service ns_session, DNSParameter dnsParameter) throws Exception {

		// vserver协议必须和service的协议相同,
		String servicetype = "";
		if (!dnsParameter.getPublicIPs().isEmpty()
				&& !dnsParameter.getPublicIPs().get(0).getPolicyParameters().isEmpty()) {
			servicetype = dnsParameter.getPublicIPs().get(0).getPolicyParameters().get(0).getProtocolText();
		}

		gslbvserver gv = new gslbvserver();
		gv.set_name(dnsParameter.getDomianName());
		gv.set_servicetype(servicetype);
		gv.set_lbmethod(DNS_LBMETHOD);
		gv.set_backuplbmethod(DNS_BACKUPLBMETHOD);
		gv.set_tolerance(Integer.valueOf(DNS_TOLERANCE));
		gv.set_appflowlog(DNS_APPFLOWLOG);
		gslbvserver.add(ns_session, gv);
	}

	/**
	 * 将vserver和domainName绑定
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void createGslbvserverDomainBinding(nitro_service ns_session, DNSParameter dnsParameter)
			throws Exception {
		gslbvserver_domain_binding gd = new gslbvserver_domain_binding();
		gd.set_name(dnsParameter.getDomianName());
		gd.set_domainname(dnsParameter.getDomianName());
		gd.set_ttl(Integer.valueOf(DNS_TTL));
		gslbvserver_domain_binding.add(ns_session, gd);
	}

	/**
	 * 将vserver和serviceName绑定
	 * 
	 * @param ns_session
	 * @param dnsParameter
	 * @throws Exception
	 */
	private static void createGslbvserverGslbserviceBinding(nitro_service ns_session, DNSParameter dnsParameter)
			throws Exception {

		for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

			for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

				gslbvserver_gslbservice_binding gs = new gslbvserver_gslbservice_binding();

				gs = new gslbvserver_gslbservice_binding();
				gs.set_servicename(generateServiceName(ipParameter, policyParameter));
				gs.set_name(dnsParameter.getDomianName());
				gs.set_weight(1);

				gslbvserver_gslbservice_binding.add(ns_session, gs);

			}
		}
	}

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

}
