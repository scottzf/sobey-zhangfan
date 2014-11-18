package com.sobey.loadbalancer.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.citrix.netscaler.nitro.exception.nitro_exception;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbservice;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver;
import com.citrix.netscaler.nitro.resource.config.gslb.gslbvserver_gslbservice_binding;
import com.citrix.netscaler.nitro.service.nitro_service;
import com.citrix.netscaler.nitro.service.nitro_service.OnerrorEnum;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.loadbalancer.data.TestData;
import com.sobey.loadbalancer.service.LoadbalanceService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;

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

	private nitro_service getnitro_service() {
		nitro_service client = null;
		try {
			client = new nitro_service(LOADBALANCER_IP, LOADBALANCER_PROTOCOL);
			client.set_credential(LOADBALANCER_USERNAME, LOADBALANCER_PASSWORD);
			client.set_onerror(OnerrorEnum.CONTINUE);
			client.set_warning(true);
			client.set_certvalidation(false);
			client.set_hostnameverification(false);
			client.login();
		} catch (nitro_exception e) {
			e.printStackTrace();
		} // 创建nitro的session
		catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

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
	public void get_gslbvserver() {

		/**
		 * 获得GSLB的VS
		 */

		try {
			gslbvserver[] result = gslbvserver.get(getnitro_service());
			for (gslbvserver gslbvserver2 : result) {
				System.out.println("get_gslbvserver - name= " + gslbvserver2.get_name() + ", servicetype= "
						+ gslbvserver2.get_servicetype());
			}
		} catch (nitro_exception e) {
			System.out.println("Exception::get_gslbvserver::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::get_gslbvserver::message=" + e);
		}
	}

	@Test
	public void get_gslbservice() {

		/**
		 * 根据serviceName获得详情
		 */
		try {
			gslbservice result = gslbservice.get(getnitro_service(), "113.142.30.14");
			System.out.println("get_gslbservice - servicename= " + result.get_servicename() + ", servicetype= "
					+ result.get_servicetype());
		} catch (nitro_exception e) {
			System.out.println("Exception::get_gslbservice::errorcode=" + e.getErrorCode() + ",message="
					+ e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::get_gslbservice::message=" + e);
		}
	}

	@Test
	public void get_gslbvserver_service_binding() {

		/**
		 * 获得VS关联的service
		 */

		try {
			gslbvserver_gslbservice_binding[] result = gslbvserver_gslbservice_binding.get(getnitro_service(),
					"lztest.sobeycache.com");
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					System.out.println("get_gslbvserver_service_binding - vserver name= " + result[i].get_name()
							+ ", servicename= " + result[i].get_servicename());
				}
			} else {
				System.out.println("Exception::get_gslbvserver_service_binding - Done");
			}
		} catch (nitro_exception e) {
			System.out.println("Exception::get_gslbvserver_service_binding::errorcode=" + e.getErrorCode()
					+ ",message=" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception::get_gslbvserver_service_binding::message=" + e);
		}
	}

}
