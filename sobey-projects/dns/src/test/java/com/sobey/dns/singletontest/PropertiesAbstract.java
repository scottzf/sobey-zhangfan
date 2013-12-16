package com.sobey.dns.singletontest;

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
	protected static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	/* 防火墙登录 */
	protected static final String DNS_IP = DNS_LOADER.getProperty("DNS_IP");
	protected static final String DNS_USERNAME = DNS_LOADER.getProperty("DNS_USERNAME");
	protected static final String DNS_PASSWORD = DNS_LOADER.getProperty("DNS_PASSWORD");

}
