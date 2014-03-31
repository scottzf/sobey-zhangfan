package com.sobey.switches.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sobey.switches.constans.SymbolEnum;
import com.sobey.switches.webservice.response.dto.RuleParameter;

/**
 * Switches 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
@Service
public class SwitchService {

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
	public String createVlanOnAccessLayer(Integer vlanId, String gateway, String netMask, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
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
	public String createVlanOnAccessLayer(Integer vlanId, String gateway, String netMask) {
		return createVlanOnAccessLayer(vlanId, gateway, netMask, SymbolEnum.DEFAULT_SYMBOL.getName());
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
	public String createVlanOnCoreLayer(Integer vlanId, String gateway, String netMask, String symbol) {

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
	 * 生成在<b>核心交换机</b>执行的创建Vlan脚本,默认换行符号<br>
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
	public String createVlanOnCoreLayer(Integer vlanId, String gateway, String netMask) {
		return createVlanOnCoreLayer(vlanId, gateway, netMask, SymbolEnum.DEFAULT_SYMBOL.getName());
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
	public String deleteVlanOnAccessLayer(Integer vlanId, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("undo vlan").append(" ").append(vlanId).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>接入层交换机</b>执行的删除Vlan脚本,默认换行符号<br>
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
	public String deleteVlanOnAccessLayer(Integer vlanId) {
		return deleteVlanOnAccessLayer(vlanId, SymbolEnum.DEFAULT_SYMBOL.getName());
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
	public String deleteVlanOnCoreLayer(Integer vlanId, String symbol) {

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
	 * 生成在<b>核心交换机</b>执行的删除Vlan脚本,默认换行符号<br>
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
	public String deleteVlanOnCoreLayer(Integer vlanId) {
		return deleteVlanOnCoreLayer(vlanId, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>交换机</b>执行的创建ESG脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * acl number 3014
	 * description gdsyxh
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.11 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.109 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.6 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.14.48 0.0.0.0
	 * rule deny ip source 172.20.27.0 0.0.0.255 destination 172.20.0.0 0.0.255.255
	 * rule deny ip source 172.20.27.0 0.0.0.255 destination 172.30.0.0 0.0.255.255
	 * rule permit ip
	 * quit
	 * 
	 * interface Vlan-interface 71
	 * packet-filter 3014 inbound
	 * quit
	 * save
	 * y
	 * y
	 * </pre>
	 * 
	 * @param aclNumber
	 *            acl编号(3000-3999)
	 * @param vlanId
	 *            Vlan编号
	 * @param desc
	 *            描述
	 * @param permits
	 *            许可ip列表
	 * @param denys
	 *            拒绝ip列表
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public String createEsg(Integer aclNumber, Integer vlanId, String desc, List<RuleParameter> permits,
			List<RuleParameter> denys, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("acl number").append(" ").append(aclNumber).append(symbol);
		sb.append("description").append(" ").append(desc).append(symbol);

		for (RuleParameter ruleParameter : permits) {
			sb.append("rule permit ip source").append(" ").append(ruleParameter.getSource()).append(" ")
					.append(ruleParameter.getSourceNetMask()).append(" ").append("destination").append(" ")
					.append(ruleParameter.getDestination()).append(" ").append(ruleParameter.getDestinationNetMask())
					.append(symbol);
		}

		for (RuleParameter ruleParameter : denys) {
			sb.append("rule deny ip source").append(" ").append(ruleParameter.getSource()).append(" ")
					.append(ruleParameter.getSourceNetMask()).append(" ").append("destination").append(" ")
					.append(ruleParameter.getDestination()).append(" ").append(ruleParameter.getDestinationNetMask())
					.append(symbol);
		}

		sb.append("rule  permit ip").append(symbol);
		sb.append("quit").append(symbol);

		sb.append("interface Vlan-interface").append(" ").append(vlanId).append(symbol);
		sb.append("packet-filter").append(" ").append(aclNumber).append(" ").append("inbound").append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>交换机</b>执行的创建ESG脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * acl number 3014
	 * description gdsyxh
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.11 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.109 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.0.6 0.0.0.0
	 * rule permit ip source 172.20.27.0 0.0.0.255 destination 172.20.14.48 0.0.0.0
	 * rule deny ip source 172.20.27.0 0.0.0.255 destination 172.20.0.0 0.0.255.255
	 * rule deny ip source 172.20.27.0 0.0.0.255 destination 172.30.0.0 0.0.255.255
	 * rule permit ip
	 * quit
	 * 
	 * interface Vlan-interface 71
	 * packet-filter 3014 inbound
	 * quit
	 * save
	 * y
	 * y
	 * </pre>
	 * 
	 * @param aclNumber
	 *            acl编号(3000-3999)
	 * @param vlanId
	 *            Vlan编号
	 * @param desc
	 *            描述
	 * @param permits
	 *            许可ip列表
	 * @param denys
	 *            拒绝ip列表
	 * @param symbol
	 *            换行符号(用于区分在scrip或web中的显示效果)
	 * @return
	 */
	public String createEsg(Integer aclNumber, Integer vlanId, String desc, List<RuleParameter> permits,
			List<RuleParameter> denys) {
		return createEsg(aclNumber, vlanId, desc, permits, denys, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

	/**
	 * 生成在<b>交换机</b>执行的删除ESG脚本.<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo acl number 3014
	 * quit
	 * save
	 * y
	 * y
	 * </pre>
	 * 
	 * @param aclNumber
	 *            acl编号(3000-3999)
	 * @return
	 */
	public String deleteEsg(Integer aclNumber, String symbol) {

		StringBuilder sb = new StringBuilder();

		sb.append("system-view").append(symbol);
		sb.append("undo acl number").append(" ").append(aclNumber).append(symbol);
		sb.append("quit").append(symbol);
		sb.append("save").append(symbol);
		sb.append("y").append(symbol);
		sb.append("y").append(symbol);
		sb.append("quit").append(symbol);
		sb.append(symbol);

		return sb.toString();
	}

	/**
	 * 生成在<b>交换机</b>执行的删除ESG脚本,默认换行符号<br>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * system-view
	 * undo acl number 3014
	 * quit
	 * save
	 * y
	 * y
	 * </pre>
	 * 
	 * @param aclNumber
	 *            acl编号(3000-3999)
	 * @return
	 */
	public String deleteEsg(Integer aclNumber) {
		return deleteEsg(aclNumber, SymbolEnum.DEFAULT_SYMBOL.getName());
	}

}
