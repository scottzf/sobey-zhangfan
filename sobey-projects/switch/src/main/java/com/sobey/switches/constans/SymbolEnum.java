package com.sobey.switches.constans;

/**
 * 换行符号Enum
 * 
 * @author Administrator
 * 
 */
public enum SymbolEnum {

	/**
	 * 默认的换行符号
	 */
	DEFAULT_SYMBOL("\r"),

	/**
	 * 用于web页面的换行符号
	 */
	WEB_SYMBOL("<br>");

	private String name;

	private SymbolEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
