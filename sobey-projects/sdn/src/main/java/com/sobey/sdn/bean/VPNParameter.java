package com.sobey.sdn.bean;

import java.util.List;

public class VPNParameter {

	private String vRouterIp;  //vRouter的管理Ip
	
	private String vpnUserName;  //VPN用户名

	private String vpnPassword;  //VPN密码
	
	private String vpnGroupName;  //用户组名
	
	private List<String> userNames;  //用户名集合
	
	private int strategyNo;  //防火墙中的策略ID
	
	private int internetPortNo;  //公网所连路由器的端口序号
		
    private int subnetPortNo;   //子网所连路由器的端口序号
    
    private String subnetAddressPoolName;   //子网对应地址池名称
    
    private int vpnGroupNo;   //用户组名编号

	public String getvRouterIp() {
		return vRouterIp;
	}

	public void setvRouterIp(String vRouterIp) {
		this.vRouterIp = vRouterIp;
	}

	public void setInternetPortNo(int internetPortNo) {
		this.internetPortNo = internetPortNo;
	}

	public String getVpnUserName() {
		return vpnUserName;
	}

	public void setVpnUserName(String vpnUserName) {
		this.vpnUserName = vpnUserName;
	}

	public String getVpnPassword() {
		return vpnPassword;
	}

	public void setVpnPassword(String vpnPassword) {
		this.vpnPassword = vpnPassword;
	}

	public String getVpnGroupName() {
		return vpnGroupName;
	}

	public void setVpnGroupName(String vpnGroupName) {
		this.vpnGroupName = vpnGroupName;
	}

	public List<String> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	public int getStrategyNo() {
		return strategyNo;
	}

	public void setStrategyNo(int strategyNo) {
		this.strategyNo = strategyNo;
	}

	public int getInternetPortNo() {
		return internetPortNo;
	}

	public void setInternetPortNO(int internetPortNo) {
		this.internetPortNo = internetPortNo;
	}

	public int getSubnetPortNo() {
		return subnetPortNo;
	}

	public void setSubnetPortNo(int subnetPortNo) {
		this.subnetPortNo = subnetPortNo;
	}

	public String getSubnetAddressPoolName() {
		return subnetAddressPoolName;
	}

	public void setSubnetAddressPoolName(String subnetAddressPoolName) {
		this.subnetAddressPoolName = subnetAddressPoolName;
	}

	public int getVpnGroupNo() {
		return vpnGroupNo;
	}

	public void setVpnGroupNo(int vpnGroupNo) {
		this.vpnGroupNo = vpnGroupNo;
	}

}
