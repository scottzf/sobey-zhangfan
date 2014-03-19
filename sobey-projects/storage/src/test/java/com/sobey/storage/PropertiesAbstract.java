package com.sobey.storage;

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
	private static PropertiesLoader STORAGE_LOADER = new PropertiesLoader("classpath:/storage.properties");

	/* netapp controller登录 */
	protected static final String STORAGE_IP = STORAGE_LOADER.getProperty("STORAGE_IP");
	protected static final String STORAGE_USERNAME = STORAGE_LOADER.getProperty("STORAGE_USERNAME");
	protected static final String STORAGE_PASSWORD = STORAGE_LOADER.getProperty("STORAGE_PASSWORD");

}
