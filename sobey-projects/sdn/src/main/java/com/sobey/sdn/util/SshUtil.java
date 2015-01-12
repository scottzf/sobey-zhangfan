package com.sobey.sdn.util;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class SshUtil {
	/**
	 * ssh远程访问，执行指令
	 * 
	 * @param ip
	 * @param username
	 * @param password
	 * @param cmd
	 * @return
	 */
	public static String executeCommand(String ip, String username, String password, String cmd) {
		try {
			// 连接
			Connection connection = new Connection(ip, 22);
			connection.connect();

			// 登陆
			Boolean mark = connection.authenticateWithPassword(username, password);

			if (mark) {
				// 执行
				Session session = connection.openSession();
				session.execCommand(cmd);
			}
		} catch (IOException e) {
			return "远程连接异常";
		}
		return null;
	}
}
