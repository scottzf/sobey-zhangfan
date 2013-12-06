package com.sobey.switches.singletontest;

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
	protected static PropertiesLoader CORE_LOADER = new PropertiesLoader("classpath:/Core.properties");
	protected static PropertiesLoader ACCESS_LOADER = new PropertiesLoader("classpath:/Access.properties");

	/* 核心交换机 */
	protected static final String CORE_IP = CORE_LOADER.getProperty("CORE_IP");
	protected static final String CORE_USERNAME = CORE_LOADER.getProperty("CORE_USERNAME");
	protected static final String CORE_PASSWORD = CORE_LOADER.getProperty("CORE_PASSWORD");

	/* 接入层交换机,可能有数量不定的接入层交换机,待后续优化 */
	protected static final String ACCESS_IP = ACCESS_LOADER.getProperty("ACCESS_IP");
	protected static final String ACCESS_USERNAME = ACCESS_LOADER.getProperty("ACCESS_USERNAME");
	protected static final String ACCESS_PASSWORD = ACCESS_LOADER.getProperty("ACCESS_PASSWORD");

}
