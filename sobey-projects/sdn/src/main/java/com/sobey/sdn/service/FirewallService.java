package com.sobey.sdn.service;

import com.sobey.sdn.bean.CreateEipParameter;
import com.sobey.sdn.bean.VPNParameter;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.util.SshUtil;

public class FirewallService {

	/**
	 * 注册更新防火墙
	 * 
	 * @return
	 */
	public static String registerFirewall() {

		// ssh远程访问
		String result = SshUtil.executeCommand(SDNConstants.VROUTER_PORT10_MODEL_IP, SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, SDNConstants.VROUTER_REGISTER_CMD);

		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 修改vRouter管理IP
	 * 
	 * @param ip_update
	 * @return
	 */
	public static String updateFirewallManageIp(String ip_update) {

		// 生成修改vRouter管理IP脚本
		String updatePort10IpConfigScript = FirewallScriptService.generateupdatePort10IpConfigScript(ip_update);

		String result = SshUtil.executeCommand(SDNConstants.VROUTER_PORT10_MODEL_IP, SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, updatePort10IpConfigScript);

		if (result != null) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 配置端口IP地址
	 * 
	 * @param vRouterIp
	 * @param portNo
	 * @param portIp
	 * @param subnetMask
	 */
	public static void configurationPortIp(String vRouterIp, int portNo, String portIp, String subnetMask) {

		// 生成脚本
		String interfaceAddressConfigScript = FirewallScriptService.generatePortIpConfigScript(portNo, portIp,
				subnetMask);

		// 执行脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				interfaceAddressConfigScript);
	}

	/**
	 * 配置端口的网关
	 * 
	 * @param vRouterIp
	 * @param port
	 * @param routeNo
	 * @param gateway
	 */
	public static void configurationPortGateway(String vRouterIp, String port, int routeNo, String gateway) {

		// 生成脚本
		String defaultRouteConfigScript = FirewallScriptService
				.generateDefaultRouteConfigScript(routeNo, port, gateway);

		// 执行脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				defaultRouteConfigScript);
	}

	/**
	 * 创建地址段
	 * 
	 * @param vRouterIp
	 * @param addressPoolName
	 * @param networkSegment
	 * @param subnetMask
	 */
	public static void createAddressPool(String vRouterIp, String addressPoolName, String networkSegment,
			String subnetMask) {

		// 生成脚本
		String addressFieldConfigScript = FirewallScriptService.generateAddressFieldConfigScript(addressPoolName,
				networkSegment, subnetMask);

		// 执行脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				addressFieldConfigScript);
	}

	/**
	 * 配置网络之间的策略(双向配置)
	 * 
	 * @param vRouterIp
	 * @param strategyNo
	 * @param sourcePort
	 * @param targetPort
	 * @param sourceAddressPool
	 * @param targetAddressPool
	 */
	public static void configurationNetworksStrategy(String vRouterIp, int strategyNo, String sourcePort,
			String targetPort, String sourceAddressPool, String targetAddressPool) {

		// 生成正向脚本
		String forward_networksStrategyConfigScript = FirewallScriptService.generateNetworksStrategyConfigScript(
				strategyNo, sourcePort, targetPort, sourceAddressPool, targetAddressPool);

		// 执行正向脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				forward_networksStrategyConfigScript);

		// 生成反向脚本
		String reverse_networksStrategyConfigScript = FirewallScriptService.generateNetworksStrategyConfigScript(
				strategyNo + 1, targetPort, sourcePort, targetAddressPool, sourceAddressPool);

		// 执行反向脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				reverse_networksStrategyConfigScript);

	}

	/**
	 * 配置内网与以太网之间的策略(双向配置)
	 * 
	 * @param vRouterIp
	 * @param strategyNo
	 * @param sourcePort
	 * @param targetPort
	 * @param sourceAddressPool
	 * @param targetAddressPool
	 */
	public static void configurationInternetStrategy(String vRouterIp, int strategyNo, String sourcePort,
			String targetPort, String sourceAddressPool, String targetSubnet_segment) {

		// 生成正向脚本
		String forward_internetStrategyConfigScript = FirewallScriptService.generateInternetStrategyConfigScript(
				strategyNo, sourcePort, targetPort, sourceAddressPool, "all");

		// 执行正向脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				forward_internetStrategyConfigScript);

		// 生成反向脚本
		String reverse_internetStrategyConfigScript = FirewallScriptService.generateInternetStrategyConfigScript(
				strategyNo + 1, targetPort, sourcePort, "all", sourceAddressPool);

		// 执行反向脚本
		SshUtil.executeCommand(vRouterIp, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				reverse_internetStrategyConfigScript);

	}

	public static void createEIp(CreateEipParameter eipParameter) {

		// 生成映射EIP与内网ip对应关系脚本
		String eIpMappingToLocalIpConfigScript = FirewallScriptService.generateEIpMappingToLocalIpConfigScript(
				eipParameter.getVip(), eipParameter.getProtocol(), eipParameter.getProtocolPort(),
				eipParameter.getInternetPortNO(), eipParameter.getPrivateIP());
		// 执行
		SshUtil.executeCommand(eipParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, eIpMappingToLocalIpConfigScript);

		String addEipMappingToGroupConfigScript = FirewallScriptService.generateAddEipMappingToGroupConfigScript(
				eipParameter.getVipGroupName(), eipParameter.getInternetPortNO(), eipParameter.getAllPolicies());

		// 执行
		SshUtil.executeCommand(eipParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, addEipMappingToGroupConfigScript);

		String createEipStrategyConfigScript = FirewallScriptService.generateCreateEipStrategyConfigScript(
				eipParameter.getStrategyNo(), eipParameter.getInternetPortNO(), eipParameter.getSubnetPortNo(),
				eipParameter.getVipGroupName());

		// 执行
		SshUtil.executeCommand(eipParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, createEipStrategyConfigScript);

	}

	public static void createVPN(VPNParameter vpnParameter) {

		// 生成配置VPN用户账号和密码脚本
		String vpnUserConfigScript = FirewallScriptService.generateVpnUserConfigScript(vpnParameter.getVpnUserName(),
				vpnParameter.getVpnPassword());

		// 执行
		SshUtil.executeCommand(vpnParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, vpnUserConfigScript);

		// 生成配置VPN用户组脚本
		String vpnUserGroupConfigScript = FirewallScriptService.generateVpnUserGroupConfigScript(
				vpnParameter.getVpnGroupName(), vpnParameter.getUserNames());

		// 执行
		SshUtil.executeCommand(vpnParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, vpnUserGroupConfigScript);

		// 生成配置VPN策略脚本
		String vpnStrategyConfigScript = FirewallScriptService.generateVpnStrategyConfigScript(
				vpnParameter.getStrategyNo(), vpnParameter.getInternetPortNo(), vpnParameter.getSubnetPortNo(),
				vpnParameter.getSubnetAddressPoolName(), vpnParameter.getVpnGroupNo(), vpnParameter.getVpnGroupName());

		// 执行
		SshUtil.executeCommand(vpnParameter.getvRouterIp(), SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD, vpnStrategyConfigScript);

	}
}
