package com.sobey.firewall.singletontest;

import com.sobey.core.utils.PropertiesLoader;

/**
 * 配置文件导入的抽象类
 * 
 * @author Administrator
 * 
 */
public abstract class PropertiesAbstract {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader FIREWALL_LOADER = new PropertiesLoader("classpath:/firewall.properties");

	/* 防火墙登录 */
	protected static final String FIREWALL_IP = FIREWALL_LOADER.getProperty("FIREWALL_IP");
	protected static final String FIREWALL_USERNAME = FIREWALL_LOADER.getProperty("FIREWALL_USERNAME");
	protected static final String FIREWALL_PASSWORD = FIREWALL_LOADER.getProperty("FIREWALL_PASSWORD");

	/* 脚本参数 */

	/**
	 * extintf
	 */
	protected static final String FIREWALL_EXTINTF = FIREWALL_LOADER.getProperty("FIREWALL_EXTINTF");

	/**
	 * portforward
	 */
	protected static final String FIREWALL_PORTFORWARD = FIREWALL_LOADER.getProperty("FIREWALL_PORTFORWARD");

	/**
	 * 联通
	 */
	protected static final String FIREWALL_CNC = FIREWALL_LOADER.getProperty("FIREWALL_CNC");

	/**
	 * 电信
	 */
	protected static final String FIREWALL_CTC = FIREWALL_LOADER.getProperty("FIREWALL_CTC");

}
