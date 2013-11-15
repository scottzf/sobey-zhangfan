package com.sobey.cmdbuild.constants;

public enum DevicePortTypeEnum {

	/**
	 * 
	 */
	SERVERPORT("ServerPort", 1),

	/**
	 * 
	 */
	SWITCHPORT("SwitchPort", 2),

	/**
	 * 
	 */
	FIMASPORT("FimasPort", 3),

	/**
	 * 
	 */
	FIREWALLPORT("FirewallPort", 4),

	/**
	 * 
	 */
	LOADBALANCEPORT("LoadBalancePort", 5),

	/**
	 * 
	 */
	NETAPPPORT("NetAppPort", 6);

	private String name;
	private int value;

	private DevicePortTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

}
