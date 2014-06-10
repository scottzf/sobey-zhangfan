package com.sobey.firewall;

import com.sobey.core.utils.PropertiesLoader;

/**
 * 配置文件导入的抽象类
 * 
 * @author Administrator
 * 
 */
public abstract interface PbulicProperties {

	/**
	 * 加载applicationContext.propertie文件
	 */
	static PropertiesLoader FIREWALL_LOADER = new PropertiesLoader("classpath:/firewall.properties");

	/* 防火墙登录 */
	static final String FIREWALL_IP = FIREWALL_LOADER.getProperty("FIREWALL_IP");
	static final String FIREWALL_USERNAME = FIREWALL_LOADER.getProperty("FIREWALL_USERNAME");
	static final String FIREWALL_PASSWORD = FIREWALL_LOADER.getProperty("FIREWALL_PASSWORD");

	static String FILE_PATH = "logs/TerminalInfo.txt";
}
