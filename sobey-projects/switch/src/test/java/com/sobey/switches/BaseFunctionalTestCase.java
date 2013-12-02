package com.sobey.switches;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.test.jetty.JettyFactory;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase Class执行前中重新载入默认数据.
 * 
 * @author calvin
 */
public class BaseFunctionalTestCase {
	protected static String baseUrl;

	protected static Server jettyServer;

	protected static SimpleDriverDataSource dataSource;

	protected static PropertiesLoader propertiesLoader = new PropertiesLoader("classpath:/application.properties",
			"classpath:/application.functional.properties", "classpath:/application.functional-local.properties");

	private static Logger logger = LoggerFactory.getLogger(BaseFunctionalTestCase.class);

	@BeforeClass
	public static void initFunctionalTestEnv() throws Exception {

		baseUrl = propertiesLoader.getProperty("baseUrl");

		Boolean isEmbedded = new URL(baseUrl).getHost().equals("localhost")
				&& propertiesLoader.getBoolean("embeddedForLocal");

		if (isEmbedded) {
			startJettyOnce();
		}

	}

	/**
	 * 启动Jetty服务器, 仅启动一次.
	 */
	protected static void startJettyOnce() throws Exception {
		if (jettyServer == null) {
			System.out.println("[HINT] Don't forget to set -XX:MaxPermSize=128m");

			jettyServer = JettyFactory.createServerInSource(new URL(baseUrl).getPort(), SwitchServer.CONTEXT);
			JettyFactory.setTldJarNames(jettyServer, SwitchServer.TLD_JAR_NAMES);
			jettyServer.start();

			logger.info("Jetty Server started at {}", baseUrl);
		}
	}

}
