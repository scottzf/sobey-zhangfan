package com.sobey.sdn.util;

import com.sobey.core.utils.PropertiesLoader;

public class SDNPropertiesUtil {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader PROPERTY_LOADER = new PropertiesLoader("classpath:/sdn.properties");
	
	public static String getProperty(String key){
		String value = PROPERTY_LOADER.getProperty(key);
		return value;
	}
}
