package com.sobey.switches.script;

import com.sobey.switches.constans.SymbolEnum;

/**
 * Switches 脚本模板生成类.
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
	 * 生成在<b>接入层交换机</b>执行的创建Vlan脚本.<br>
	 * 默认换行符号为 <b>" \r"</b>
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

	/**
	 * 生成在<b>核心交换机</b>执行的创建Vlan脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * vlan 80
	 * quit
	 * interface Vlan-interface 80
	 * ip address 172.21.71.254 255.255.255.0
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
	public static String generateCreateVlanScriptOnCoreLayer(Integer vlanId, String gateway, String netMask,
			String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("interface Vlan-interface").append(" ").append(vlanId).append(symbol);
		sb.append("ip address").append(" ").append(gateway).append(" ").append(netMask).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>核心交换机</b>执行的创建Vlan脚本.<br>
	 * 默认换行符号为 <b>" \r"</b>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * vlan 80
	 * quit
	 * interface Vlan-interface 80
	 * ip address 172.21.71.254 255.255.255.0
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
	public static String generateCreateVlanScriptOnCoreLayer(Integer vlanId, String gateway, String netMask) {
		return generateCreateVlanScriptOnCoreLayer(vlanId, gateway, netMask, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>接入层交换机</b>执行的删除Vlan脚本
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo vlan 80
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteVlanScriptOnAccessLayer(Integer vlanId, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("undo vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>接入层交换机</b>执行的删除Vlan脚本<br>
	 * 默认换行符号为 <b>" \r"</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo vlan 80
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteVlanScriptOnAccessLayer(Integer vlanId) {
		return generateDeleteVlanScriptOnAccessLayer(vlanId, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>核心交换机</b>执行的删除Vlan脚本<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo vlan 80
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteVlanScriptOnCoreLayer(Integer vlanId, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("undo vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>核心交换机</b>执行的删除Vlan脚本<br>
	 * 默认换行符号为 <b>" \r"</b><br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo vlan 80
	 * quit
	 * save
	 * y
	 * y
	 * quit
	 * </pre>
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public static String generateDeleteVlanScriptOnCoreLayer(Integer vlanId) {
		return generateDeleteVlanScriptOnCoreLayer(vlanId, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

}
