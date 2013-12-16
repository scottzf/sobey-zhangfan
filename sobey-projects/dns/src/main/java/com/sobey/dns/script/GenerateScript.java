package com.sobey.dns.script;

import com.sobey.core.utils.PropertiesLoader;

/**
 * DNS 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	/**
	 * extintf
	 */
	private static final String DNS_EXTINTF = DNS_LOADER.getProperty("DNS_EXTINTF");

}
