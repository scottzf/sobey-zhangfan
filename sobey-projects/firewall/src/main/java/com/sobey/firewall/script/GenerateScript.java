package com.sobey.firewall.script;

import com.sobey.firewall.constans.SymbolEnum;

/**
 * Firewall 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
public class GenerateScript {

	/**
	 * 生成在<b>接入层交换机</b>执行的创建Vlan脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * vlan  80 
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param gateway
	 *            网关
	 * @param netMask
	 *            子网掩码
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateCreateVlanScriptOnAccessLayer(Integer vlanId, String gateway, String netMask,
			String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>接入层交换机</b>执行的创建Vlan脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * vlan  80 
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param gateway
	 *            网关
	 * @param netMask
	 *            子网掩码
	 * @return
	 */
	public static String generateCreateVlanScriptOnAccessLayer(Integer vlanId, String gateway, String netMask) {
		return generateCreateVlanScriptOnAccessLayer(vlanId, gateway, netMask, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

}
