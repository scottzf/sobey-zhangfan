package com.sobey.storage;

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
	static PropertiesLoader STORAGE_LOADER = new PropertiesLoader("classpath:/storage.properties");

	/* netapp controller登录 */
	static final String STORAGE_IP = STORAGE_LOADER.getProperty("STORAGE_IP");
	static final String STORAGE_USERNAME = STORAGE_LOADER.getProperty("STORAGE_USERNAME");
	static final String STORAGE_PASSWORD = STORAGE_LOADER.getProperty("STORAGE_PASSWORD");

}
