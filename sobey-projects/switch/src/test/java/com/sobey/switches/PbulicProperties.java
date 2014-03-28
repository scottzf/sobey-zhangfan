package com.sobey.switches;

import com.sobey.core.utils.PropertiesLoader;

/**
 * 配置文件导入的接口
 * 
 * @author Administrator
 * 
 */
public interface PbulicProperties {

	/**
	 * 加载applicationContext.propertie文件
	 */
	static PropertiesLoader CORE_LOADER = new PropertiesLoader("classpath:/Core.properties");
	static PropertiesLoader ACCESS_LOADER = new PropertiesLoader("classpath:/Access.properties");

	/* 核心交换机 */
	static final String CORE_IP = CORE_LOADER.getProperty("CORE_IP");
	static final String CORE_USERNAME = CORE_LOADER.getProperty("CORE_USERNAME");
	static final String CORE_PASSWORD = CORE_LOADER.getProperty("CORE_PASSWORD");

	/* 接入层交换机,可能有数量不定的接入层交换机,待后续优化 */
	static final String ACCESS_IP = ACCESS_LOADER.getProperty("ACCESS_IP");
	static final String ACCESS_USERNAME = ACCESS_LOADER.getProperty("ACCESS_USERNAME");
	static final String ACCESS_PASSWORD = ACCESS_LOADER.getProperty("ACCESS_PASSWORD");

}
