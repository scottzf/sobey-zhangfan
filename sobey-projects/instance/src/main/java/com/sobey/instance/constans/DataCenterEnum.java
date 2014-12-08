package com.sobey.instance.constans;

/**
 * 数据中心的定义
 * 增加常量说明
 * @author Administrator
 * 
 */
public enum DataCenterEnum {

	CD("cd"), XA("xa");

	private String datacenter;

	private DataCenterEnum(String datacenter) {
		this.datacenter = datacenter;
	}

	@Override
	public String toString() {
		return String.valueOf(this.datacenter);
	}

}
