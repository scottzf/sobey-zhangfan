package com.sobey.loadbalancer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.config.basic.server;
import com.citrix.netscaler.nitro.resource.config.basic.service;
import com.citrix.netscaler.nitro.resource.config.basic.servicegroup;
import com.citrix.netscaler.nitro.resource.config.basic.servicegroup_servicegroupmember_binding;
import com.citrix.netscaler.nitro.resource.config.lb.lbmonitor_service_binding;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver_binding;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver_service_binding;
import com.citrix.netscaler.nitro.resource.config.ns.nsconfig;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.citrix.netscaler.nitro.service.nitro_service.OnerrorEnum;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPolicyParameter;
import com.sobey.loadbalancer.webservice.response.dto.ELBPublicIPParameter;
import com.sobey.loadbalancer.webservice.response.dto.ElbPolicySync;
import com.sobey.loadbalancer.webservice.response.dto.ElbSync;

@Service
public class LoadbalanceService {

	private static Logger logger = LoggerFactory.getLogger(LoadbalanceService.class);

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

	public List<ElbSync> getElbSyncList() {

		List<ElbSync> syncs = new ArrayList<ElbSync>();

		nitro_service client = null;

		try {

			client = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL); // 创建nitro的session
			client.set_credential(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			lbvserver[] result = lbvserver.get(client);
			if (result != null) {

				for (lbvserver lbvserver2 : result) {

					List<ElbPolicySync> policySyncs = new ArrayList<ElbPolicySync>();

					lbvserver_binding lbvserver_binding = com.citrix.netscaler.nitro.resource.config.lb.lbvserver_binding
							.get(client, lbvserver2.get_name());

					if (lbvserver_binding.get_lbvserver_service_bindings() != null) {
						for (int i = 0; i < lbvserver_binding.get_lbvserver_service_bindings().length; i++) {

							ElbPolicySync policySync = new ElbPolicySync();
							policySync.setElb(lbvserver2.get_ipv46());
							policySync.setElbProtocol(lbvserver_binding.get_lbvserver_service_bindings()[i]
									.get_servicetype());
							policySync.setIpaddress(lbvserver_binding.get_lbvserver_service_bindings()[i].get_ipv46());
							policySync.setSourcePort(lbvserver_binding.get_lbvserver_service_bindings()[i].get_port());
							policySync.setTargetPort(lbvserver_binding.get_lbvserver_service_bindings()[i].get_port());
							policySyncs.add(policySync);

						}
					}

					ElbSync elbSync = new ElbSync();
					elbSync.setVip(lbvserver2.get_ipv46());
					elbSync.setPolicySyncs(policySyncs);
					syncs.add(elbSync);
				}

			} else {
				System.out.println("Exception::getElbSyncList - Done");
			}

			// Step.6 保存配置
			saveconfig(client);

			client.logout();// 登出

		} catch (nitro_exception e) {
			logger.info("Exception::getElbSyncList::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::getElbSyncList::message=" + e);
		}
		return syncs;

	}

	/**
	 * 创建DNS
	 * 
	 * @param elbParameter
	 *            {@link ELBParameter}
	 * @return
	 */
	public boolean createElb(ELBParameter elbParameter) {

		nitro_service client = null;

		try {

			client = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL); // 创建nitro的session
			client.set_credential(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			// Step.1 创建Virtual Servers
			add_lbvserver(client, elbParameter);

			// 添加servicegroup,但只用于初始化,只需执行一次即可
			// add_servicegroup(client, elbParameter);

			// Step.2 创建Servers
			bind_servicegroup_server(client, elbParameter);

			// Step.3 创建Service
			add_service(client, elbParameter);

			// Step.4 创建lbvserverServiceBinding
			addlbvserver_bindings(client, elbParameter);

			// Step.5 添加监控
			bind_lbmonitor_service(client, elbParameter);

			// Step.6 保存配置
			saveconfig(client);

			client.logout();// 登出

		} catch (nitro_exception e) {
			logger.info("Exception::createElb::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.info("Exception::createElb::message=" + e);
			return false;
		}
		return true;
	}

	public boolean deleteElb(ELBParameter elbParameter) {

		nitro_service client = null;

		try {

			client = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL); // 创建nitro的session
			client.set_credential(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			// step.1 删除service
			rm_service(client, elbParameter);

			// step.2 删除lbvserver
			rm_lbvserver(client, elbParameter);

			// step.3 删除server
			rm_server(client, elbParameter);

			// Step.5 保存配置
			saveconfig(client);

			client.logout();// 登出

		} catch (nitro_exception e) {
			logger.info("Exception::deleteElb::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.info("Exception::deleteElb::message=" + e);
			return false;
		}
		return true;
	}

	/**
	 * 添加监控
	 * 
	 * @param client
	 */
	public void bind_lbmonitor_service(nitro_service service, ELBParameter elbParameter) {

		try {

			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
				for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

					lbmonitor_service_binding binding = new lbmonitor_service_binding();
					binding.set_servicename(generateServiceName(ipParameter.getIpaddress(),
							policyParameter.getProtocolText(), policyParameter.getSourcePort()));
					binding.set_monitorname("tcp");
					lbmonitor_service_binding.add(service, binding);
				}
			}

		} catch (nitro_exception e) {
			System.out.println("Exception::bind_lbmonitor_service::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::bind_lbmonitor_service::message=" + e);
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
	 * 删除server
	 * 
	 * @param service
	 * @param elbParameter
	 */
	private void rm_server(nitro_service service, ELBParameter elbParameter) {
		try {

			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
				server.delete(service, ipParameter.getIpaddress());
			}

		} catch (nitro_exception e) {
			logger.info("Exception::rm_server::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::rm_server::message=" + e);
		}
	}

	/**
	 * 删除lbvserver
	 * 
	 * @param service
	 * @param elbParameter
	 */
	private void rm_lbvserver(nitro_service service, ELBParameter elbParameter) {

		// 将重复的去掉
		HashSet<String> set = new HashSet<String>();
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				set.add(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
			}
		}

		try {

			for (String serviceName : set) {
				lbvserver.delete(service, serviceName);
			}

		} catch (nitro_exception e) {
			logger.info("Exception::rm_lbvserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::rm_lbvserver::message=" + e);
		}
	}

	/**
	 * 删除service
	 * 
	 * @param ns_service
	 * @param elbParameter
	 */
	private void rm_service(nitro_service ns_service, ELBParameter elbParameter) {

		try {

			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
				for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
					service.delete(
							ns_service,
							generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
									policyParameter.getSourcePort()));
				}
			}

		} catch (nitro_exception e) {
			logger.info("Exception::rm_service::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::rm_service::message=" + e);
		}
	}

	/**
	 * 绑定vserver和service
	 * 
	 * @param service
	 * @param elbParameter
	 */
	private void addlbvserver_bindings(nitro_service service, ELBParameter elbParameter) {

		try {
			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
				for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

					lbvserver_service_binding bindsvc = new lbvserver_service_binding();

					bindsvc.set_name(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
							policyParameter.getSourcePort()));
					bindsvc.set_servicename(generateServiceName(ipParameter.getIpaddress(),
							policyParameter.getProtocolText(), policyParameter.getSourcePort()));
					bindsvc.set_weight((long) 30);

					lbvserver_service_binding.add(service, bindsvc);
				}
			}

		} catch (nitro_exception e) {
			logger.info("Exception::addlbvserver_bindings::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::addlbvserver_bindings::message=" + e);
		}
	}

	/**
	 * 创建Virtual Servers
	 * 
	 * @param service
	 * @param elbParameter
	 */
	private static void add_lbvserver(nitro_service service, ELBParameter elbParameter) {

		// 将重复的去掉
		HashSet<String> set = new HashSet<String>();
		for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
			for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {
				set.add(generateServiceName(elbParameter.getVip(), policyParameter.getProtocolText(),
						policyParameter.getSourcePort()));
			}
		}

		try {

			for (String serviceName : set) {
				lbvserver obj = new lbvserver();
				obj.set_name(serviceName);
				obj.set_servicetype(StringUtils.split(serviceName, "-")[1]);
				obj.set_ipv46(StringUtils.split(serviceName, "-")[0]);
				obj.set_port(Integer.valueOf(StringUtils.split(serviceName, "-")[2]));
				obj.set_persistencetype(LOADBALANCER_PERSISTENCETYPE);
				obj.set_lbmethod(LOADBALANCER_LBMETHOD);
				obj.set_clttimeout(Integer.valueOf(LOADBALANCER_CLTTIMEOUT));
				lbvserver.add(service, obj);
			}

		} catch (nitro_exception e) {
			logger.info("Exception::add_lbvserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::add_lbvserver::message=" + e);
		}

	}

	/**
	 * 创建Servers
	 * 
	 * @param service
	 * @param elbParameter
	 */
	private static void bind_servicegroup_server(nitro_service service, ELBParameter elbParameter) {

		try {

			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {

				servicegroup_servicegroupmember_binding obj = new servicegroup_servicegroupmember_binding();
				obj.set_servicegroupname(LOADBALANCER_SERVICEGROUPNAME);
				obj.set_ip(ipParameter.getIpaddress());
				obj.set_port(Integer.valueOf(LOADBALANCER_DEFAULT_SERVER_PORT));
				servicegroup_servicegroupmember_binding.add(service, obj);
			}

		} catch (nitro_exception e) {
			logger.info("Exception::bind_servicegroup_server::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::bind_servicegroup_server::message=" + e);
		}

	}

	/**
	 * 添加servicegroup,但只用于初始化,只需执行一次即可
	 * 
	 * @param service
	 * @param elbParameter
	 */
	@SuppressWarnings("unused")
	private static void add_servicegroup(nitro_service service, ELBParameter elbParameter) {
		try {
			servicegroup grp = new servicegroup();
			grp.set_servicegroupname(LOADBALANCER_SERVICEGROUPNAME);
			grp.set_servicetype(servicegroup.servicetypeEnum.HTTP);
			servicegroup.add(service, grp);
		} catch (nitro_exception e) {
			logger.info("Exception::add_servicegroup::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::add_servicegroup::message=" + e);
		}

	}

	/**
	 * 创建service
	 * 
	 * @param ns_service
	 * @param elbParameter
	 */
	private static void add_service(nitro_service ns_service, ELBParameter elbParameter) {

		try {
			for (ELBPublicIPParameter ipParameter : elbParameter.getPublicIPs()) {
				for (ELBPolicyParameter policyParameter : ipParameter.getPolicyParameters()) {

					service svc = new service();
					svc.set_name(generateServiceName(ipParameter.getIpaddress(), policyParameter.getProtocolText(),
							policyParameter.getSourcePort()));
					svc.set_ip(ipParameter.getIpaddress());
					svc.set_port(policyParameter.getSourcePort());
					svc.set_servicetype(policyParameter.getProtocolText());
					service.add(ns_service, svc);

				}
			}
		} catch (nitro_exception e) {
			logger.info("Exception::add_service::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			logger.info("Exception::add_service::message=" + e);
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
