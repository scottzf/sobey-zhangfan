package com.sobey.storage.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;

/**
 * netapp 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
@Service
public class NetAppService {

	/**
	 * 生成在<b>netapp</b>执行的创建volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 *  /root/sc/createvol.sh liukai 20 10.10.1.6
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link CreateEs3Parameter}
	 * @return
	 */
	public String createEs3(CreateEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/createvol.sh ").append(parameter.getVolumeName()).append(" ")
				.append(parameter.getVolumeSize()).append(" ").append(parameter.getClientIPaddress()).append(" \n");
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
	 * @param parameter
	 *            {@link DeleteEs3Parameter}
	 * @return
	 */
	public String deleteEs3(DeleteEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/delvol.sh ").append(parameter.getVolumeName()).append(" \n");
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
	 * @param parameter
	 *            {@link MountEs3Parameter}
	 * @return
	 */
	public String mountEs3(MountEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/mountdisk.sh ").append(parameter.getClientIPaddress()).append(" ")
				.append(parameter.getNetAppIPaddress()).append(" ").append(parameter.getVolumeName()).append(" \n");
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
	 * @param parameter
	 *            {@link UmountEs3Parameter}
	 * @return
	 */
	public String umountEs3(UmountEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/umountdiks.sh ").append(parameter.getClientIPaddress()).append(" \n");
		return sb.toString();
	}

	/**
	 * 生成在<b>netapp</b>执行的修改volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/remo.sh liukai 10.10.1.6:10.10.1.18 10.10.1.6:10.10.1.18:10.10.1.20
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link RemountEs3Parameter}
	 * @return
	 */
	public String remountEs3(RemountEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/remo.sh ").append(parameter.getVolumeName()).append(" ")
				.append(JoinString(parameter.getBeforeClientIPaddress())).append(" ")
				.append(JoinString(parameter.getAfterClientIPaddress())).append(" \n");
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
