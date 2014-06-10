package com.sobey.api.utils;

/**
 * 网络相关的一些帮助方法.
 * 
 * @author Administrator
 *
 */
public class NetworkUtil {

	/**
	 * 根据协议获得默认的端口.
	 * 
	 * <pre>
	 * HTTP:		80
	 * HTTPS、SSL:	443
	 * </pre>
	 * 
	 * @param protocol
	 * @return
	 */
	public static Integer getPortFromProtocol(String protocol) {

		if ("HTTPS".equals(protocol.toUpperCase()) || "SSL".equals(protocol.toUpperCase())) {
			return 443;
		} else {
			return 80;
		}

	}

}
