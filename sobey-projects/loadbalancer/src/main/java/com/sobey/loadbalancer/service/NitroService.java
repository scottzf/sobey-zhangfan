package com.sobey.loadbalancer.service;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.base.base_response;
import com.citrix.netscaler.nitro.resource.config.basic.server;
import com.citrix.netscaler.nitro.resource.config.basic.service;
import com.citrix.netscaler.nitro.resource.config.basic.servicegroup_servicegroupmember_binding;
import com.citrix.netscaler.nitro.resource.config.lb.lbmonitor;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver_service_binding;
import com.citrix.netscaler.nitro.resource.config.ns.nsconfig;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPolicyParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPublicIPParameter;

@Service
public class NitroService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader LOADBALANCER_LOADER = new PropertiesLoader("classpath:/loadbalancer.properties");

	/**
	 * IP
	 */
	private static final String LOADBALANCER_IP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_IP");

	/**
	 * Username
	 */
	private static final String LOADBALANCER_USERNAME = LOADBALANCER_LOADER.getProperty("LOADBALANCER_USERNAME");

	/**
	 * password
	 */
	private static final String LOADBALANCER_PASSWORD = LOADBALANCER_LOADER.getProperty("LOADBALANCER_PASSWORD");

	/**
	 * protocol
	 */
	private static final String LOADBALANCER_PROTOCOL = LOADBALANCER_LOADER.getProperty("LOADBALANCER_PROTOCOL");

	/**
	 * persistencetype
	 */
	private static final String LOADBALANCER_PERSISTENCETYPE = LOADBALANCER_LOADER
			.getProperty("LOADBALANCER_PERSISTENCETYPE");

	/**
	 * lbmethod
	 */
	private static final String LOADBALANCER_LBMETHOD = LOADBALANCER_LOADER.getProperty("LOADBALANCER_LBMETHOD");

	/**
	 * clttimeout
	 */
	private static final String LOADBALANCER_CLTTIMEOUT = LOADBALANCER_LOADER.getProperty("LOADBALANCER_CLTTIMEOUT");

	/**
	 * servicegroupname
	 */
	private static final String LOADBALANCER_SERVICEGROUPNAME = LOADBALANCER_LOADER
			.getProperty("LOADBALANCER_SERVICEGROUPNAME");

	/**
	 * server默认端口
	 */
	private static final String LOADBALANCER_DEFAULT_SERVER_PORT = LOADBALANCER_LOADER
			.getProperty("LOADBALANCER_DEFAULT_SERVER_PORT");

	/**
	 * 创建DNS
	 * 
	 * @param elbParameter
	 * @return
	 */
	public boolean createElb(ELBParameter elbParameter) {

		nitro_service ns_session = null;

		try {

			ns_session = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL); // 创建nitro的session
			ns_session.login(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);// 登录

			nsconfig.save(ns_session);

			// Step.1 创建Virtual Servers
			createlbvserver(ns_session, elbParameter);

			// // Step.2 创建Servers
			createServicegroupServicegroupmemberBinding(ns_session, elbParameter);

			// Step.3 创建Services
			createService(ns_session, elbParameter);

			// Step.4 创建lbvserverServiceBinding
			createlbvserverServiceBinding(ns_session, elbParameter);

			// Step.5 绑定 lb monitor
			createlbmonitor(ns_session, elbParameter);

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

	public boolean deleteElb(ELBParameter elbParameter) {

		nitro_service ns_session = null;

		try {

			ns_session = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL); // 创建nitro的session
			ns_session.login(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);// 登录

			nsconfig.save(ns_session);

			// step.1 删除service
			deleteService(ns_session, elbParameter);

			// step.2 删除lbvserver
			deletelbvserver(ns_session, elbParameter);

			// step.3 删除server
			deleteServer(ns_session, elbParameter);

			// step.4 删除lb monitor
			deletemonitor(ns_session, elbParameter);

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
	 * 删除lb monitor
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void deletemonitor(nitro_service ns_session, ELBParameter elbParameter) throws Exception {
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				lbmonitor mon = new lbmonitor();
				mon.set_monitorname(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
				mon.set_type(policyParameter.getProtocolText());
				lbmonitor.delete(ns_session, mon);
			}
		}
	}

	/**
	 * 删除server
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void deleteServer(nitro_service ns_session, ELBParameter elbParameter) throws Exception {
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			server.delete(ns_session, ipParameter.getIpaddress());
		}
	}

	/**
	 * 删除lbvserver
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void deletelbvserver(nitro_service ns_session, ELBParameter elbParameter) throws Exception {

		HashSet<String> set = new HashSet<String>();
		// 将重复的去掉
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				set.add(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
			}
		}

		for (String serviceName : set) {
			lbvserver.delete(ns_session, serviceName);
		}
	}

	/**
	 * 删除service
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void deleteService(nitro_service ns_session, ELBParameter elbParameter) throws Exception {

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				service.delete(
						ns_session,
						generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
								policyParameter.getSourcePort()));
			}
		}
	}

	/**
	 * 创建lbvserverServiceBinding
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void createlbvserverServiceBinding(nitro_service ns_session, ELBParameter elbParameter) throws Exception {
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				lbvserver_service_binding bindsvc = new lbvserver_service_binding();
				bindsvc = new lbvserver_service_binding();
				bindsvc.set_name(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
				bindsvc.set_servicename(generateServiceName(ipParameter.getIpaddress(),
						policyParameter.getProtocolText(), policyParameter.getSourcePort()));
				bindsvc.set_weight((long) 20);

				lbvserver_service_binding.add(ns_session, bindsvc);
			}
		}
	}

	/**
	 * 绑定 lb monitor
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private void createlbmonitor(nitro_service ns_session, ELBParameter elbParameter) throws Exception {
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				lbmonitor monitor = new lbmonitor();
				monitor.set_monitorname(generateServiceName(ipParameter.getIpaddress(),
						policyParameter.getProtocolText(), policyParameter.getSourcePort()));
				monitor.set_type(policyParameter.getProtocolText());
				lbmonitor.add(ns_session, monitor);
			}
		}
	}

	/**
	 * 创建Virtual Servers
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private static void createlbvserver(nitro_service ns_session, ELBParameter elbParameter) throws Exception {

		// 将重复的去掉
		HashSet<String> set = new HashSet<String>();
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				set.add(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
			}
		}

		for (String serviceName : set) {
			lbvserver newlb = new lbvserver();
			newlb.set_name(serviceName);
			newlb.set_servicetype(StringUtils.split(serviceName, "-")[1]);
			newlb.set_ipv46(StringUtils.split(serviceName, "-")[0]);
			newlb.set_port(Integer.valueOf(StringUtils.split(serviceName, "-")[2]));
			newlb.set_persistencetype(LOADBALANCER_PERSISTENCETYPE);
			newlb.set_lbmethod(LOADBALANCER_LBMETHOD);
			newlb.set_clttimeout(Integer.valueOf(LOADBALANCER_CLTTIMEOUT));
			lbvserver.add(ns_session, newlb);
		}
	}

	/**
	 * 创建Servers
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	private static void createServicegroupServicegroupmemberBinding(nitro_service ns_session, ELBParameter elbParameter)
			throws Exception {

		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

			servicegroup_servicegroupmember_binding obj = new servicegroup_servicegroupmember_binding();
			obj.set_servicegroupname(LOADBALANCER_SERVICEGROUPNAME);
			obj.set_ip(ipParameter.getIpaddress());
			obj.set_port(Integer.valueOf(LOADBALANCER_DEFAULT_SERVER_PORT));
			servicegroup_servicegroupmember_binding.add(ns_session, obj);
		}
	}

	/**
	 * 创建gslb vserver
	 * 
	 * @param ns_session
	 * @param elbParameter
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static void createService(nitro_service ns_session, ELBParameter elbParameter) throws Exception {
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				service svc = new service();
				svc.set_name(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
				svc.set_ip(ipParameter.getIpaddress());
				svc.set_port(policyParameter.getSourcePort());
				svc.set_servicetype(policyParameter.getProtocolText());
				base_response result = service.add(ns_session, svc);
			}
		}
	}

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

}
