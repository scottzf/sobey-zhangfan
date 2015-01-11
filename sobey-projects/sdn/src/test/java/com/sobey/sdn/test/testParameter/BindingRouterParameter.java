package com.sobey.sdn.test.testParameter;

import java.util.List;

import com.sobey.sdn.bean.Subnet;

public class BindingRouterParameter {

	private String routerName; //

	private String controlIp; //

	private int strategyNo; //

	private List<Subnet> subnets;

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
