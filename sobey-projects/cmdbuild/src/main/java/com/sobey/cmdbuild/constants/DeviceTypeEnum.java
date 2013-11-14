package com.sobey.cmdbuild.constants;

public enum DeviceTypeEnum {

	/**
	 * 
	 */
	SERVER("Server", 1),

	/**
	 * 
	 */
	SWITCH("Switch", 2),

	/**
	 * 
	 */
	FIMAS("Fimas", 3),

	/**
	 * 
	 */
	FIREWALL("Firewall", 4),

	/**
	 * 
	 */
	NETAPPCONTROLLER("NetAppController", 5),

	/**
	 * 
	 */
	LOADBALANCE("LoadBalance", 6);

	private String name;
	private int value;

	private DeviceTypeEnum(String name, int value) {
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
