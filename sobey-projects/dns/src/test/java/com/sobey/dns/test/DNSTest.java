package com.sobey.dns.test;

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
import com.sobey.dns.data.TestData;
import com.sobey.dns.service.DnsService;
import com.sobey.dns.webservice.response.dto.DNSParameter;

/**
 * DNS单元测试.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DNSTest extends TestCase {

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

	@Autowired
	private DnsService service;

	@Test
	public void createGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		assertTrue(service.createGSLB(parameter));
	}

	@Test
	public void deleteGSLB() {
		DNSParameter parameter = TestData.randomDNSParameter();
		assertTrue(service.deleteGSLB(parameter));
	}

	private nitro_service getnitro_service() {
		nitro_service client = null;
		try {
			client = new nitro_service(DNS_IP, DNS_PROTOCOL);
			client.set_credential(DNS_USERNAME, DNS_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();
		} catch (nitro_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建nitro的session
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	@Test
	public void get_lbvserver() {

		/**
		 * 获得所有的ELB
		 */
		try {
			lbvserver[] result = lbvserver.get(getnitro_service());
			if (result != null) {
				System.out.println("get_lbvserver result::length=" + result.length);
				for (lbvserver lbvserver2 : result) {
					System.out.println(lbvserver2.get_name());
				}

			} else {
				System.out.println("Exception::get_lbvserver - Done");
			}
		} catch (nitro_exception e) {
			System.out
					.println("Exception::get_lbvserver::errorcode=" + e.getErrorCode() + ",message=" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::get_lbvserver::message=" + e);
		}
	}

	@Test
	public void getlbvserver_bindings() {

		/**
		 * 获得ELB的策略信息
		 */
		try {
			lbvserver_binding obj = new lbvserver_binding();
			obj.set_name("10.10.2.55-HTTP-80");
			lbvserver_binding result = lbvserver_binding.get(getnitro_service(), "10.10.2.55-HTTP-80");
			if (result.get_lbvserver_service_bindings() != null) {
				System.out.println("getlbvserver_bindings result::length="
						+ result.get_lbvserver_service_bindings().length);
				for (int i = 0; i < result.get_lbvserver_service_bindings().length; i++) {
					System.out.println("svc name " + result.get_lbvserver_service_bindings()[i].get_servicename());
				}
			} else {
				System.out.println("getlbvserver_bindings - Done");
			}
		} catch (nitro_exception e) {
			System.out.println("Exception::getlbvserver_bindings::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::getlbvserver_bindings::message=" + e);
		}
	}

}
