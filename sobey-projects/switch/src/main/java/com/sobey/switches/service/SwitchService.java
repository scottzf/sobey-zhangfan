package com.sobey.switches.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.switches.utils.H3CUtil;
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
			String[] vlanConfig = generateVlanConfigString(parameter); // 配置面向服务器的接口的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), vlanConfig);

			// 生成交换机执行命令
			String[] interfaceConfig = generateInterfaceConfigString(parameter); // 配置面向服务器的接口的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), interfaceConfig); // 交换机ip地址暂时空着

			// 在置顶交换机之间建NVGRE隧道ID
			String[] nvgreConfig = generateNVGREConfigString(parameter); // 配置NVGRE的命令
			JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), nvgreConfig); // 交换机ip地址暂时空着

		} catch (Exception e) {
			logger.info("createPolicyInSwitch::" + e);
			result.setError(WSResult.SYSTEM_ERROR, "交换机策略创建错误,请联系系统管理员.");
		}

		return result;
	}

	private String getEthNameFromCoreSwtich(String hostIP) throws Exception {

		// 根据主机IP,在核心交换机获得与该主机相连的交换机相关信息
		// TODO 备注：此处username和password为登录核心交换机用户名和密码，类似于登录vcenter的用户名密码，在程序中写死
		String macAndPort = H3CUtil.getCommandResponse(hostIP, "username", "password"); // 获得核心交换机上经过处理的相应结果字符串

		String mac = macAndPort.substring(0, macAndPort.indexOf("&")); // 根据处理规则得出mac地址
		String whichSW = macAndPort.substring(macAndPort.indexOf("&") + 1); // 根据处理规则得出对应接口
		String swInterface = JsonRPCUtil.getSwitchPortByMac(whichSW, mac); // 获得主机与交换机哪个接口相连

		return swInterface;

	}

	/**
	 * 生成vlan配置
	 * 
	 * @param vlanId
	 * @return
	 */
	private String[] generateVlanConfigString(SwitchPolicyParameter parameter) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + parameter.getVlanId(); // 创建面向服务器的VLAN
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
	private String[] generateNVGREConfigString(SwitchPolicyParameter parameter) {
		// TODO source 和 peer 的IP来源
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式
		String str3 = "source 172.31.255.1"; // 设置NVGRE报文的外层IP源地址

		// 将id为vlanId的VLAN映射到tunnel ID中
		String str4 = "vlan " + parameter.getVlanId() + " tunnel-id " + parameter.getVlanId();
		String str5 = "vlan " + parameter.getVlanId() + " peer 172.31.255.2"; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 生成接口配置
	 * 
	 * @param swInterface
	 * @param vlanId
	 * @return
	 * @throws Exception
	 */
	private String[] generateInterfaceConfigString(SwitchPolicyParameter parameter) throws Exception {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface " + getEthNameFromCoreSwtich(parameter.getHostIp()); // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + parameter.getVlanId(); // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

}
