package com.sobey.cmdbuild.constants;

/**
 * 描述LookUp中ConsumptionsStatus的Description和Id. <br>
 * 
 * <b>注意保持和CMDBuild中表lookup中的数据一致.</b>
 * 
 * <pre>
 * 执行:	Execution	 41 
 * 完成:	Complete 	 42 
 * 到期:	Maturity 	 43
 * </pre>
 * 
 * @author Administrator
 * 
 */
public enum ConsumptionsStatusEnum {

	/**
	 * 执行
	 */
	Execution("Execution", 41),

	/**
	 * 完成
	 */
	Complete("Complete", 42),

	/**
	 * 到期
	 */
	Maturity("Maturity", 43);

	private String name;
	private int value;

	private ConsumptionsStatusEnum(String name, int value) {
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
