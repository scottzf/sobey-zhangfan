package com.sobey.switches.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.switches.utils.JsonRPCUtil;
import com.sobey.switches.utils.SDNPropertiesUtil;
import com.sobey.switches.webservice.response.dto.SwitchPolicyParameter;
import com.sobey.switches.webservice.response.result.WSResult;

/**
 * Switches 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
@Service
public class SwitchService {

	private static Logger logger = LoggerFactory.getLogger(SwitchService.class);

	public WSResult createPolicyInSwitch(SwitchPolicyParameter parameter) {

		WSResult result = new WSResult();

		try {

			// 配置VLAN
			String[] vlanConfig = generateVlanConfigString(parameter.getVlanId()); // 配置面向服务器的接口的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), vlanConfig);

			// 生成交换机执行命令
			String[] interfaceConfig = generateInterfaceConfigString(parameter.getEthName(), parameter.getVlanId()); // 配置面向服务器的接口的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), interfaceConfig); // 交换机ip地址暂时空着

			// 在置顶交换机之间建NVGRE隧道ID
			String[] nvgreConfig = generateNVGREConfigString(parameter.getVlanId()); // 配置NVGRE的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), nvgreConfig); // 交换机ip地址暂时空着

		} catch (IOException e) {
			logger.info("createPolicyInSwitch::" + e);
			result.setError(WSResult.SYSTEM_ERROR, "IO操作错误.");
		}

		return result;
	}

	/**
	 * 生成vlan配置
	 * 
	 * @param vlanId
	 * @return
	 */
	private static String[] generateVlanConfigString(int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + vlanId; // 创建面向服务器的VLAN
		String str4 = "VLAN 4094"; // 创建上行VLAN
		String str5 = "exit"; // 退出VLAN模式
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 生成NVGRE配置
	 * 
	 * @param vlanId
	 * @return
	 */
	private String[] generateNVGREConfigString(int vlanId) {
		// TODO source 和 peer 的IP来源
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式
		String str3 = "source 172.31.255.1"; // 设置NVGRE报文的外层IP源地址
		String str4 = "vlan " + vlanId + " tunnel-id " + vlanId; // 将id为vlanId的VLAN映射到tunnel ID中
		String str5 = "vlan " + vlanId + " peer 172.31.255.2"; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 生成接口配置
	 * 
	 * @param swInterface
	 * @param vlanId
	 * @return
	 */
	private String[] generateInterfaceConfigString(String swInterface, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface " + swInterface; // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + vlanId; // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

}
