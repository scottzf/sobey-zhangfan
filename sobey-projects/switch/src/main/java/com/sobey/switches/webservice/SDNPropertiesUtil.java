package com.sobey.switches.webservice;

import com.sobey.core.utils.PropertiesLoader;

public class SDNPropertiesUtil {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader PROPERTY_LOADER = new PropertiesLoader("classpath:/switch.properties");

	public static String getProperty(String key) {
		String value = PROPERTY_LOADER.getProperty(key);
		return value;
	}
}
