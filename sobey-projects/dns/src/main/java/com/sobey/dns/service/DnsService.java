package com.sobey.dns.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbservice;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbservice_lbmonitor_binding;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver_domain_binding;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver_gslbservice_binding;
import com.citrix.netscaler.nitro.resource.config.ns.nsconfig;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.citrix.netscaler.nitro.service.nitro_service.OnerrorEnum;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DNSPolicyParameter;
import com.sobey.dns.webservice.response.dto.DNSPublicIPParameter;

@Service
public class DnsService {

	private static Logger logger = LoggerFactory.getLogger(DnsService.class);

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
	@SuppressWarnings("unused")
	private static final String DNS_DEFAULT_SERVER_PORT = DNS_LOADER.getProperty("DNS_DEFAULT_SERVER_PORT");

	/**
	 * servicegroupname
	 */
	@SuppressWarnings("unused")
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
	 * 创建域名类型为GSLB的DNS
	 * 
	 * @param dnsParameter
	 * @return
	 */
	public boolean createGSLB(DNSParameter dnsParameter) {

		nitro_service client = null;

		try {

			client = new nitro_service(DNS_IP, DNS_PROTOCOL); // 创建nitro的session
			client.set_credential(DNS_USERNAME, DNS_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			// Step.1 创建vserver
			add_gslb_vserver(client, dnsParameter);

			// Step.2 创建Service
			add_gslb_service(client, dnsParameter);

			// Step.3 将vserver和serviceName绑定
			bind_gslbvserver_gslbservice(client, dnsParameter);

			// Step.4 将vserver和domainName绑定
			bind_gslbvs_domain(client, dnsParameter);

			// Step.5 绑定监控
			bind_gslbservice_lbmonitor(client, dnsParameter);

			// Step.5 保存配置
			saveconfig(client);

			client.logout();// 登出

		} catch (nitro_exception e) {
			logger.info("Exception::createGSLB::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.info("Exception::createGSLB::message=" + e);
			return false;
		}
		return true;
	}

	public boolean deleteGSLB(DNSParameter dnsParameter) {

		nitro_service client = null;

		try {

			client = new nitro_service(DNS_IP, DNS_PROTOCOL); // 创建nitro的session
			client.set_credential(DNS_USERNAME, DNS_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			// Step.1 解除vserver和domainName绑定
			unbind_gslbvserver_gslbservice(client, dnsParameter);

			// Step.2 删除gslb vserver
			rm_gslb_vserver(client, dnsParameter);

			// Step.3 删除gslb Service
			rm_gslb_service(client, dnsParameter);

			// Step.4 保存配置
			saveconfig(client);

			client.logout();// 登出

		} catch (nitro_exception e) {
			logger.info("Exception::deleteGSLB::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.info("Exception::deleteGSLB::message=" + e);
			return false;
		}
		return true;
	}

	/**
	 * 删除gslb Service
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void rm_gslb_service(nitro_service service, DNSParameter dnsParameter) {

		try {

			for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
				for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
					gslbservice.delete(service,
							gslbservice.get(service, generateServiceName(ipParameter, policyParameter)));
				}
			}

		} catch (nitro_exception e) {
			logger.info("Exception::rm_gslb_service::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::rm_gslb_service::message=" + e);
		}

	}

	/**
	 * 删除gslb vserver
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void rm_gslb_vserver(nitro_service service, DNSParameter dnsParameter) {

		try {

			gslbvserver.delete(service, gslbvserver.get(service, dnsParameter.getDomianName()));

		} catch (nitro_exception e) {
			logger.info("Exception::rm_gslb_vserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::rm_gslb_vserver::message=" + e);
		}

	}

	/**
	 * 绑定监控
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void bind_gslbservice_lbmonitor(nitro_service service, DNSParameter dnsParameter) {

		try {

			for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

				for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

					gslbservice_lbmonitor_binding binding = new gslbservice_lbmonitor_binding();

					binding.set_monitor_name("tcp");
					binding.set_servicename(generateServiceName(ipParameter, policyParameter));

					gslbservice_lbmonitor_binding.add(service, binding);

				}

			}

		} catch (nitro_exception e) {
			logger.info("Exception::bind_gslbservice_lbmonitor::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::bind_gslbservice_lbmonitor::message=" + e);
		}
	}

	/**
	 * 创建gslb service
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void add_gslb_service(nitro_service service, DNSParameter dnsParameter) {

		try {

			for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

				for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

					gslbservice gsvc = new gslbservice();

					gsvc.set_servicename(generateServiceName(ipParameter, policyParameter));
					gsvc.set_ip(ipParameter.getIpaddress());
					gsvc.set_maxclient(Integer.valueOf(DNS_MAXCLIENT));
					gsvc.set_port(policyParameter.getTargetPort());
					gsvc.set_sitename(generateSiteName(ipParameter));
					gsvc.set_servicetype(policyParameter.getProtocolText());

					gsvc.set_clttimeout(Integer.valueOf(DNS_CLTTIMEOUT));
					gsvc.set_svrtimeout(Integer.valueOf(DNS_SVRTIMEOUT));
					gsvc.set_downstateflush(DNS_DOWNSTATEFLUSH);

					gslbservice.add(service, gsvc);

				}

			}

		} catch (nitro_exception e) {
			logger.info("Exception::add_gslb_service::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::add_gslb_service::message=" + e);
		}

	}

	/**
	 * 创建gslb vserver
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void add_gslb_vserver(nitro_service service, DNSParameter dnsParameter) {

		// vserver协议必须和service的协议相同,
		String servicetype = "";
		if (!dnsParameter.getPublicIPs().isEmpty()
				&& !dnsParameter.getPublicIPs().get(0).getPolicyParameters().isEmpty()) {
			servicetype = dnsParameter.getPublicIPs().get(0).getPolicyParameters().get(0).getProtocolText();
		}

		gslbvserver gv = new gslbvserver();

		try {

			gv.set_name(dnsParameter.getDomianName());
			gv.set_servicetype(servicetype);
			gv.set_lbmethod(DNS_LBMETHOD);
			gv.set_backuplbmethod(DNS_BACKUPLBMETHOD);
			gv.set_tolerance(Integer.valueOf(DNS_TOLERANCE));
			gv.set_appflowlog(DNS_APPFLOWLOG);

			gslbvserver.add(service, gv);

		} catch (nitro_exception e) {
			logger.info("Exception::add_gslb_vserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::add_gslb_vserver::message=" + e);
		}

	}

	/**
	 * 保存配置
	 * 
	 * @param service
	 */
	private static void saveconfig(nitro_service service) {

		try {
			nsconfig.save(service);
		} catch (nitro_exception e) {
			logger.info("Exception::saveconfig::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::saveconfig::message=" + e);
		}

	}

	/**
	 * 将vserver和domainName绑定
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void bind_gslbvs_domain(nitro_service service, DNSParameter dnsParameter) {

		try {
			gslbvserver_domain_binding obj = new gslbvserver_domain_binding();
			obj.set_name(dnsParameter.getDomianName());
			obj.set_domainname(dnsParameter.getDomianName());
			obj.set_ttl(Integer.valueOf(DNS_TTL));
			gslbvserver_domain_binding.add(service, obj);
		} catch (nitro_exception e) {
			logger.info("Exception::bind_gslbvs_domain::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::bind_gslbvs_domain::message=" + e);
		}

	}

	/**
	 * 解除vserver和domainName绑定
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void unbind_gslbvserver_gslbservice(nitro_service service, DNSParameter dnsParameter) {

		try {
			for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {
				for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
					gslbvserver_gslbservice_binding obj = new gslbvserver_gslbservice_binding();
					obj.set_name(dnsParameter.getDomianName());
					obj.set_servicename(generateServiceName(ipParameter, policyParameter));
					gslbvserver_gslbservice_binding.delete(service, obj);
				}
			}

		} catch (nitro_exception e) {
			logger.info("Exception::unbind_gslbvserver_gslbservice::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::unbind_gslbvserver_gslbservice::message=" + e);
		}
	}

	/**
	 * 将vserver和serviceName绑定
	 * 
	 * @param service
	 * @param dnsParameter
	 */
	private static void bind_gslbvserver_gslbservice(nitro_service service, DNSParameter dnsParameter) {

		try {
			for (DNSPublicIPParameter ipParameter : dnsParameter.getPublicIPs()) {

				for (DNSPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
					gslbvserver_gslbservice_binding obj = new gslbvserver_gslbservice_binding();
					obj.set_name(dnsParameter.getDomianName());
					obj.set_servicename(generateServiceName(ipParameter, policyParameter));
					gslbvserver_gslbservice_binding.add(service, obj);
				}
			}

		} catch (nitro_exception e) {
			logger.info("Exception::bind_gslbvserver_gslbservice::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::bind_gslbvserver_gslbservice::message=" + e);
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
