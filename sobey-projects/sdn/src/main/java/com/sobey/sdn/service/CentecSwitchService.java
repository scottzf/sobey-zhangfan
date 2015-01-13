package com.sobey.sdn.service;

import org.apache.commons.lang3.StringUtils;

import com.sobey.sdn.util.JsonRPCUtil;
import com.sobey.sdn.util.SDNPropertiesUtil;

public class CentecSwitchService {

	/**
	 * 生成配置VLAN的命令脚本
	 * 
	 * @param vlanId
	 * @return
	 */
	public static String[] generateVlanConfigString(int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + vlanId; // 创建面向服务器的VLAN
		String str4 = "VLAN 4094"; // 创建上行VLAN
		String str5 = "exit"; // 退出VLAN模式
		String str6 = "copy running-config startup-config"; // 保存配置信息
		String[] cmds = { str1, str2, str3, str4, str5, str6 };
		return cmds;
	}

	/**
	 * 生成配置面向服务器的接口命令脚本
	 * 
	 * @param swInterface
	 * @param vlanId
	 * @return
	 */
	public static String[] generateInterfaceConfigString(String swInterface, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface " + swInterface; // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + vlanId; // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 生成配置NVGRE命令脚本
	 * 
	 * @param swUrl
	 * @param vlanId
	 * @return
	 */
	public static String[] generateNvgreConfigString(String swUrl, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式
		String sourceIp = SDNPropertiesUtil.getProperty("TOR-B_SWITCH_NVGRE_SOURCEIP"); // 置顶交换机源IP
		String peerIp = SDNPropertiesUtil.getProperty("TOR-A_SWITCH_NVGRE_SOURCEIP"); // 置顶交换机peer IP
		if ("10.2.2.8".equals(swUrl)) {
			sourceIp = SDNPropertiesUtil.getProperty("TOR-A_SWITCH_NVGRE_SOURCEIP");
			peerIp = SDNPropertiesUtil.getProperty("TOR-B_SWITCH_NVGRE_SOURCEIP");
		}
		String str3 = "source " + sourceIp; // 设置NVGRE报文的外层IP源地址
		String str4 = "vlan " + vlanId + " tunnel-id " + vlanId; // 将id为vlanId的VLAN映射到tunnel ID中
		String str5 = "vlan " + vlanId + " peer " + peerIp; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	public static void makeVlanAccessInterface(String vRouterHostIp, int vlanId) throws Exception {
		/**
		 * 暂时从固定资源列表中获得交换机接口
		 */
		String whichSWAndSwInterface = HostRelationMap.relationMap.get(vRouterHostIp);
		String whichSW = StringUtils.substringBefore(whichSWAndSwInterface, " "); // 取空格前字符串
		String swInterface = StringUtils.substringAfter(whichSWAndSwInterface, " "); // 取空格后字符串

		String swIp = getSwIpByMark(whichSW);

		String[] interfaceConfig_cmds = generateInterfaceConfigString(swInterface, vlanId);

		JsonRPCUtil.executeJsonRPCRequest(swIp, interfaceConfig_cmds); // 执行
	}

	private static String getSwIpByMark(String whichSW) {
		if ("TOR-A".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-A_SWITCH_IP");
		}
		if ("TOR-B".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-B_SWITCH_IP");
		}
		return null;
	}
}
