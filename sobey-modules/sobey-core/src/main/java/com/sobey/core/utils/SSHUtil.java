package com.sobey.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * SSH链接Util类
 * 
 * @author Administrator
 *
 */
public class SSHUtil {

	/**
	 * SSH默认端口
	 */
	private static final int DEFAULT_SSH_PORT = 22;

	/**
	 * 通过SSH执行脚本
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param cmd
	 *            命令
	 * @param port
	 *            端口
	 * @throws IOException
	 */
	public static void executeCommand(String ip, String username, String password, String cmd, int port)
			throws IOException {

		// 连接
		Connection connection = new Connection(ip, port);
		connection.connect();

		// 登陆
		Boolean isAuthenticated = connection.authenticateWithPassword(username, password);

		if (isAuthenticated) {
			// 执行
			Session session = connection.openSession();
			session.execCommand(cmd);

			// 建立流连接输出控制台信息
			InputStream stdout = new StreamGobbler(session.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				System.out.println(line);
				if (line == null) {
					break;
				}
			}
			br.close();
		}
		connection.close();
	}

	/**
	 * 通过SSH执行脚本,默认端口22
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param cmd
	 *            命令
	 * @throws IOException
	 */
	public static void executeCommand(String ip, String username, String password, String cmd) throws IOException {
		executeCommand(ip, username, password, cmd, DEFAULT_SSH_PORT);
	}

}
