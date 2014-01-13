package com.sobey.loadbalancer.singletontest;

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
	protected static PropertiesLoader LOADBALANCER_LOADER = new PropertiesLoader("classpath:/LOADBALANCER.properties");

	/* LOADBALANCER登录 */
	protected static final String LOADBALANCER_IP = LOADBALANCER_LOADER.getProperty("LOADBALANCER_IP");
	protected static final String LOADBALANCER_USERNAME = LOADBALANCER_LOADER.getProperty("LOADBALANCER_USERNAME");
	protected static final String LOADBALANCER_PASSWORD = LOADBALANCER_LOADER.getProperty("LOADBALANCER_PASSWORD");

}
