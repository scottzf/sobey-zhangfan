package com.sobey.cmdbuild.constants;

/**
 * 描述LookUp中ServiceType的Description和Id. <br>
 * 
 * <b>注意保持和CMDBuild中表lookup中的数据一致.</b>
 * 
 * <pre>
 * ECS	 35 
 * ES3 	 36 
 * EIP 	 37
 * ELB 	 38
 * DNS 	 39
 * ESG 	 40
 * VPN 	 未定
 * </pre>
 * 
 * @author Administrator
 * 
 */
public enum ServiceTypeEnum {

	ECS("ECS", 35),

	ES3("ES3", 36),

	EIP("EIP", 37),

	ELB("ELB", 38),

	DNS("DNS", 39),

	ESG("ESG", 40),

	VPN("VPN", 0);

	private String name;
	private int value;

	private ServiceTypeEnum(String name, int value) {
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
