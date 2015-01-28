package com.sobey.switches.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.switches.constans.HostRelationMap;
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

	/**
	 * 用于创建一个Subnet时,同一Subnet的通信.
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult createSinglePolicy(SwitchPolicyParameter parameter) {

		WSResult result = new WSResult();

		try {

			String switchUrl = getSwitchUrl(parameter);

			// 配置VLAN
			String[] vlanConfig = generateVlanConfigString(parameter); // 配置面向服务器的接口的命令

			JsonRPCUtil.executeJsonRPCRequest(switchUrl, vlanConfig);

			// 生成交换机执行命令
			String[] interfaceConfig = generateInterfaceConfigString(parameter); // 配置面向服务器的接口的命令

			JsonRPCUtil.executeJsonRPCRequest(switchUrl, interfaceConfig); // 交换机ip地址暂时空着

			// 在置顶交换机之间建NVGRE隧道ID
			String[] nvgreConfig = generateNVGREConfigString(switchUrl, parameter); // 配置NVGRE的命令

			JsonRPCUtil.executeJsonRPCRequest(switchUrl, nvgreConfig); // 交换机ip地址暂时空着

		} catch (Exception e) {
			logger.info("createSinglePolicy::" + e);
			result.setError(WSResult.SYSTEM_ERROR, "交换机策略创建错误,请联系系统管理员.");
		}

		return result;
	}

	/**
	 * 不同子网之间的通信.(Subnent绑定Router)
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult createMultiplePolicy(SwitchPolicyParameter parameter) {

		WSResult result = new WSResult();

		try {

			String switchUrl = getSwitchUrl(parameter);

			// 生成交换机执行命令
			String[] interfaceConfig = generateInterfaceConfigString(parameter); // 配置面向服务器的接口的命令

			JsonRPCUtil.executeJsonRPCRequest(switchUrl, interfaceConfig); // 交换机ip地址暂时空着

		} catch (Exception e) {
			logger.info("createMultiplePolicy::" + e);
			result.setError(WSResult.SYSTEM_ERROR, "交换机策略创建错误,请联系系统管理员.");
		}

		return result;
	}

	/**
	 * 生成配置VLAN的命令脚本
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
		String str6 = "copy running-config startup-config"; // 保存配置信息
		String[] cmds = { str1, str2, str3, str4, str5, str6 };
		return cmds;
	}

	/**
	 * 生成NVGRE配置
	 * 
	 * @param vlanId
	 * @return
	 */
	private String[] generateNVGREConfigString(String swUrl, SwitchPolicyParameter parameter) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式

		String sourceIp = SDNPropertiesUtil.getProperty("TOR-B_SWITCH_NVGRE_SOURCEIP"); // 置顶交换机源IP
		String peerIp = SDNPropertiesUtil.getProperty("TOR-A_SWITCH_NVGRE_SOURCEIP"); // 置顶交换机peer IP
		if ("10.2.2.8".equals(swUrl)) {
			sourceIp = SDNPropertiesUtil.getProperty("TOR-A_SWITCH_NVGRE_SOURCEIP");
			peerIp = SDNPropertiesUtil.getProperty("TOR-B_SWITCH_NVGRE_SOURCEIP");
		}

		String str3 = "source " + sourceIp; // 设置NVGRE报文的外层IP源地址
		// 将id为vlanId的VLAN映射到tunnel ID中
		String str4 = "vlan " + parameter.getVlanId() + " tunnel-id " + parameter.getTunnelId();
		String str5 = "vlan " + parameter.getVlanId() + " peer " + peerIp; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 生成配置面向服务器的接口命令脚本
	 * 
	 * @param swInterface
	 * @param vlanId
	 * @return
	 * @throws Exception
	 */
	private String[] generateInterfaceConfigString(SwitchPolicyParameter parameter) throws Exception {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface " + getInterfaceName(parameter); // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + parameter.getVlanId(); // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	/**
	 * 获得宿主机在盛科交换机中的网卡名.eg : eth-0-2
	 * 
	 * @param parameter
	 * @return
	 */
	private String getInterfaceName(SwitchPolicyParameter parameter) {
		String value = HostRelationMap.relationMap.get(parameter.getHostIp());
		return StringUtils.substringAfter(value, " ");
	}

	/**
	 * 获得交换机名称. eg TOR-A
	 * 
	 * @param parameter
	 * @return
	 */
	private String getSwitchUrl(SwitchPolicyParameter parameter) {

		String value = HostRelationMap.relationMap.get(parameter.getHostIp());

		String name = StringUtils.substringBefore(value, " ");

		return getSwIpByMark(name);
	}

	/**
	 * 临时方法,用以获得交换机URL
	 * 
	 * @param whichSW
	 * @return
	 */
	private String getSwIpByMark(String whichSW) {
		if ("TOR-A".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-A_SWITCH_IP");
		}
		if ("TOR-B".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-B_SWITCH_IP");
		}
		return null;
	}

}
