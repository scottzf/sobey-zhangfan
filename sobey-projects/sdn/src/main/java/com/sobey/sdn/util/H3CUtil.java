package com.sobey.sdn.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class H3CUtil {

	/**
	 * 以SSH连接的形式
	 * 
	 * @param cmd
	 * @return
	 */
	public static String getCommandResponse(String ip) throws Exception {
		String cmd = "dis arp | begin " + ip;
		// 连接
		Connection connection = new Connection(SDNPropertiesUtil.getProperty("G4_SW1_CORE_IP"), 22);
		connection.connect();
		// 登陆
		Boolean mark = connection.authenticateWithPassword(SDNPropertiesUtil.getProperty("G4_SW1_CORE_USERNAME"), SDNPropertiesUtil.getProperty("G4_SW1_CORE_PASSWORD"));
		StringBuffer sb = new StringBuffer();
		if (mark == true) {
			// 执行
			Session session = connection.openSession();
			session.execCommand(cmd);
			// 建立流连接输出控制台信息
			InputStream stdout = new StreamGobbler(session.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line.contains(ip)) {
					String macAndPort = StringUtils.substringAfter(line, ip).trim();
					String mac = macAndPort.substring(0, 14);
					sb.append(mac).append("&");
					StringUtils.getCommonPrefix("BAGG");
					int x = macAndPort.indexOf("BAGG");
					String port = macAndPort.substring(x, x + 5);
					sb.append(port);
					break;
				}
			}
			br.close();
		}
		return sb.toString();
	}

	/**
	 * 执行命令返回响应字符串
	 * 
	 * @param username
	 * @param password
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public static String executeCommand(String username, String password, String cmd) throws Exception {
		// 连接
		Connection connection = new Connection(SDNPropertiesUtil.getProperty("G4_SW1_CORE_IP"), 22);
		connection.connect();

		// 登陆
		Boolean mark = connection.authenticateWithPassword(username, password);

		StringBuffer sb = new StringBuffer();

		if (mark == true) {
			// 执行
			Session session = connection.openSession();
			session.execCommand(cmd);
			// 建立流连接输出控制台信息
			InputStream stdout = new StreamGobbler(session.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		}
		return sb.toString();
	}

	/**
	 * 处理命令行返回的结果
	 * 
	 * @param result
	 */
	public void handleCommandResponse(String result) {

	}
}
