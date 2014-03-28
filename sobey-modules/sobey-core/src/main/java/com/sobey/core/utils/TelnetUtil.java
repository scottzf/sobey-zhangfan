package com.sobey.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

/**
 * commons-net 帮助类.
 * 
 * 用于以telnet方式远程执行脚本.
 * 
 * @author Administrator
 * 
 */
public class TelnetUtil implements Runnable, TelnetNotificationHandler {

	private static TelnetClient tc = null;

	private InputStream in;

	private PrintStream out;

	/**
	 * 终端输出
	 */
	// public static TerminalInfo terminalInfo = null;

	/**
	 * 默认端口
	 */
	private static final int default_port = 23;

	public TelnetUtil() {
		super();
	}

	/**
	 * 用于以telnet方式远程执行脚本,并创建一个txt文件保存执行脚本后的终端信息.
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param port
	 *            IP的端口
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本字符串
	 * @param filePath
	 *            文件保存内容,推荐用相对路径. eg:logs/TerminalInfo.txt
	 */
	public static void execCommand(String ip, Integer port, String username, String password, String command,
			String filePath) {
		Thread t = new Thread(new TelnetUtil(ip, port, username, password, command, filePath));
		t.start();
	}

	/**
	 * 用于以telnet方式远程执行脚本.
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param port
	 *            IP的端口
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本字符串
	 */
	public static void execCommand(String ip, Integer port, String username, String password, String command) {
		execCommand(ip, port, username, password, command, null);
	}

	/**
	 * 用于以telnet方式远程执行脚本. 默认端口为 23
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本字符串
	 */
	public static void execCommand(String ip, String username, String password, String command) {
		execCommand(ip, default_port, username, password, command, null);
	}

	/**
	 * 用于以telnet方式远程执行脚本. 默认端口为 23,并创建一个txt文件保存执行脚本后的终端信息.
	 * 
	 * @param ip
	 *            主机IP地址
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本字符串
	 * @param filePath
	 *            文件保存内容,推荐用相对路径. eg:logs/TerminalInfo.txt
	 */
	public static void execCommand(String ip, String username, String password, String command, String filePath) {
		execCommand(ip, default_port, username, password, command, filePath);
	}

	public TelnetUtil(String ip, Integer port, String username, String password, String command, String filePath) {

		if (intconnect(ip, port)) {

			write(username);
			write(password);
			write(command);

			// 获得终端显示的字符串,并将其写入文本中.
			try {
				if (StringUtils.isNotBlank(filePath)) {
					File outputFileName = new File(filePath);
					FileUtils.writeStringToFile(outputFileName, readUntil());
					disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void receivedNegotiation(int negotiation_code, int option_code) {
		String command = null;
		if (negotiation_code == TelnetNotificationHandler.RECEIVED_DO) {
			command = "DO";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_DONT) {
			command = "DONT";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WILL) {
			command = "WILL";
		} else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WONT) {
			command = "WONT";
		}
		System.out.println("Received " + command + " for option code " + option_code);

	}

	@Override
	public void run() {
		InputStream instr = tc.getInputStream();
		try {
			byte[] buff = new byte[1024];
			int ret_read = 0;
			do {
				ret_read = instr.read(buff);
				if (ret_read > 0) {
					System.out.print(new String(buff, 0, ret_read));
				}
			} while (ret_read >= 0);
		} catch (Exception e) {
			System.err.println("Exception while reading socket:" + e.getMessage());
		}
		try {
			tc.disconnect();
		} catch (Exception e) {
			System.err.println("Exception while closing telnet:" + e.getMessage());
		}
	}

	private void write(String command) {
		try {
			out.println(command);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean intconnect(String ip, int port) {
		try {
			tc = new TelnetClient();
			TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
			EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
			SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);

			tc.addOptionHandler(ttopt);
			tc.addOptionHandler(echoopt);
			tc.addOptionHandler(gaopt);
			tc.connect(ip, port);
			in = tc.getInputStream();
			out = new PrintStream(tc.getOutputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tc.disconnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	private void disconnect() throws IOException {

		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		tc.disconnect();

	}

	public String readUntil() {

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line; // 用来保存每行读取的内容
		try {
			line = reader.readLine();
			while (line != null) { // 如果 line 为空说明读完了
				sb.append(line); // 将读到的内容添加到 buffer 中
				sb.append("\n"); // 添加换行符
				line = reader.readLine(); // 读取下一行
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
