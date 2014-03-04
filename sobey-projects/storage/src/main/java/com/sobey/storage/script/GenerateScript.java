package com.sobey.storage.script;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sobey.storage.webservice.response.dto.NetAppParameter;

/**
 * netapp 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 生成在<b>netapp</b>执行的创建volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 *  /root/sc/createvol.sh liukai 20 10.10.1.6
	 * </pre>
	 * 
	 * @param netAppParameter
	 * @return
	 */
	public static String generateCreateEs3Script(NetAppParameter netAppParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/createvol.sh ").append(netAppParameter.getVolumeName()).append(" ")
				.append(netAppParameter.getVolumeSize()).append(" ").append(netAppParameter.getClientIPaddress())
				.append(" \n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 生成在<b>netapp</b>执行的删除volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/delvol.sh liukai
	 * </pre>
	 * 
	 * @param netAppParameter
	 * @return
	 */
	public static String generateDeleteEs3Script(NetAppParameter netAppParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/delvol.sh ").append(netAppParameter.getVolumeName()).append(" \n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 生成在<b>netapp</b>执行的挂载volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/mountdisk.sh 10.10.1.18 10.10.1.6 liukai
	 * </pre>
	 * 
	 * @param netAppParameter
	 * @return
	 */
	public static String generateMountEs3Script(NetAppParameter netAppParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/mountdisk.sh ").append(netAppParameter.getClientIPaddress()).append(" ")
				.append(netAppParameter.getNetAppIPaddress()).append(" ").append(netAppParameter.getVolumeName())
				.append(" \n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 生成在<b>netapp</b>执行的卸载volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/umountdiks.sh 10.10.1.18
	 * </pre>
	 * 
	 * @param netAppParameter
	 * @return
	 */
	public static String generateUmountEs3Script(NetAppParameter netAppParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/umountdiks.sh ").append(netAppParameter.getClientIPaddress()).append(" \n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 生成在<b>netapp</b>执行的修改volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/remo.sh liukai 10.10.1.6:10.10.1.18:10.10.1.6:10.10.1.18:10.10.1.20
	 * </pre>
	 * 
	 * @param netAppParameter
	 * @return
	 */
	public static String generateRemountEs3Script(NetAppParameter netAppParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/remo.sh ").append(netAppParameter.getVolumeName()).append(" ")
				.append(JoinString(netAppParameter.getBeforeClientIPaddress())).append(" ")
				.append(JoinString(netAppParameter.getAfterClientIPaddress())).append(" \n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 将集合以符号或其他字符串为间隔组成新的字符串
	 * 
	 * @param list
	 * @return
	 */
	private static String JoinString(List<String> list) {
		return StringUtils.join(list, ":");
	}

}
