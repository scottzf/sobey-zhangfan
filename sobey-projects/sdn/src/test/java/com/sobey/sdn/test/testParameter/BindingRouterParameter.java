package com.sobey.sdn.test.testParameter;

import java.util.List;

import com.sobey.sdn.bean.Subnet;

public class BindingRouterParameter {

	private String vRouterHostIp;  //vRouter所在宿主机IP
	
	private String routerName; //vRouter名称

	private String controlIp; //管理vRouter的IP

	private int strategyNo; //策略号

	private List<Subnet> subnets;

	public String getvRouterHostIp() {
		return vRouterHostIp;
	}

	public void setvRouterHostIp(String vRouterHostIp) {
		this.vRouterHostIp = vRouterHostIp;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String getControlIp() {
		return controlIp;
	}

	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}

	public List<Subnet> getSubnets() {
		return subnets;
	}

	public void setSubnets(List<Subnet> subnets) {
		this.subnets = subnets;
	}

	public int getStrategyNo() {
		return strategyNo;
	}

	public void setStrategyNo(int strategyNo) {
		this.strategyNo = strategyNo;
	}

}
