package com.sobey.core.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 用jsch实现ssh远程执行shell命令.<br>
 * ps:多command需要确认是否能通过管道来表达.<br>
 * eg: <b>cd~ | ls -l</b><br>
 * 调用JschUtil的时候用<b>";"</b> 替换<b>"|"</b> 符号即可<br>
 * eg: <b>cd~ ; ls -l</b><br>
 * 
 * @author Administrator
 * 
 */
public class JschUtil {

	/**
	 * 通过SSH协议远程执行脚本命令.
	 * 
	 * @param host
	 *            主机IP地址
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本信息
	 * @return
	 */
	public static boolean execCommand(String host, String username, String password, String command) {

		boolean flag = true;

		JSch jsch = new JSch();

		try {

			Session session = jsch.getSession(username, host, 22);

			session.setPassword(password);

			session.setConfig("StrictHostKeyChecking", "no");

			session.connect(10 * 1000);

			Channel channel = session.openChannel("shell");

			InputStream is = new ByteArrayInputStream(command.getBytes());

			channel.setInputStream(is);

			channel.setOutputStream(System.out);

			channel.connect(15 * 1000);

			Thread.sleep(7 * 1000);

			channel.disconnect();
			session.disconnect();

		} catch (JSchException e) {
			System.out.println("SSH链接失败");
			flag = false;
		} catch (InterruptedException e) {
			System.out.println("SSH中断");
			flag = false;
		}

		return flag;
	}

	/**
	 * 通过SSH协议远程执行脚本命令,并创建一个txt文件保存执行脚本后的终端信息.
	 * 
	 * @param host
	 *            主机IP地址
	 * @param username
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param command
	 *            脚本信息
	 * @param filePath
	 *            文件保存内容,推荐用相对路径. eg:logs/TerminalInfo.txt
	 * @return
	 */
	public static boolean execCommand(String host, String username, String password, String command, String filePath) {

		boolean flag = true;

		JSch jsch = new JSch();

		try {

			Session session = jsch.getSession(username, host, 22);

			session.setPassword(password);

			session.setConfig("StrictHostKeyChecking", "no");

			session.connect(10 * 1000);

			Channel channel = session.openChannel("shell");

			InputStream is = new ByteArrayInputStream(command.getBytes());

			channel.setInputStream(is);

			// 将命令输出到一个文件中.
			File outputFileName = new File(filePath);

			FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);

			channel.setOutputStream(fileOutputStream);

			channel.connect(15 * 1000);

			Thread.sleep(7 * 1000);

			channel.disconnect();
			session.disconnect();

		} catch (JSchException e) {
			System.out.println("SSH链接失败");
			flag = false;
		} catch (InterruptedException e) {
			System.out.println("SSH中断");
			flag = false;
		} catch (FileNotFoundException e) {
			flag = false;
		}

		return flag;
	}

}
