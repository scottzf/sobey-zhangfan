package com.sobey.loadbalancer.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver;
import com.citrix.netscaler.nitro.resource.config.lb.lbvserver_binding;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.citrix.netscaler.nitro.service.nitro_service.OnerrorEnum;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.loadbalancer.data.TestData;
import com.sobey.loadbalancer.service.LoadbalanceService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ElbPolicySync;
import com.sobey.loadbalancer.webservice.response.dto.ElbSync;

/**
 * Elb单元测试.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ElbTest extends TestCase {

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

	@Autowired
	private LoadbalanceService service;

	@Test
	public void createElb() {
		ELBParameter parameter = TestData.randomELBParameter();
		assertTrue(service.createElb(parameter));
	}

	@Test
	public void deleteElb() {
		ELBParameter parameter = TestData.randomELBParameter();
		assertTrue(service.deleteElb(parameter));
	}

	@Test
	public void get_lbvserver() {

		nitro_service client = null;

		/**
		 * 获得所有的ELB
		 */
		try {

			client = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL);
			client.set_credential(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();

			lbvserver[] result = lbvserver.get(client);

			List<ElbSync> syncs = new ArrayList<ElbSync>();

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

				System.out.println(syncs);
			} else {
				System.out.println("Exception::get_lbvserver - Done");
			}

			client.logout();// 登出

		} catch (nitro_exception e) {
			System.out
					.println("Exception::get_lbvserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::get_lbvserver::message=" + e);
		}
	}

}
