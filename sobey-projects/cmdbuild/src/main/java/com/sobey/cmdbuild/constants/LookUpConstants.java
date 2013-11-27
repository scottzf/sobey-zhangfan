package com.sobey.cmdbuild.constants;

/**
 * CMDBuild数据库中 LookUp的enum字典.<br>
 * 
 * <b>注意保持和CMDBuild中表lookup中的数据(Id & Description)一致.</b>
 * 
 * @author Administrator
 * 
 */
public class LookUpConstants {

	/**
	 * 描述LookUp中ConsumptionsStatus的Description和Id.
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
	public enum ConsumptionsStatus {

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
		private Integer value;

		private ConsumptionsStatus(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 描述LookUp中ServiceType的Description和Id.
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
	public enum ServiceType {

		ECS("ECS", 35),

		ES3("ES3", 36),

		EIP("EIP", 37),

		ELB("ELB", 38),

		DNS("DNS", 39),

		ESG("ESG", 40),

		VPN("VPN", 0);

		private String name;
		private Integer value;

		private ServiceType(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 描述LookUp中IPAddressStatus的Description和Id.
	 * 
	 * <pre>
	 * 使用中	 49 
	 * 未使用 	 50
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum IPAddressStatus {

		使用中("使用中", 49),

		未使用("未使用", 50);

		private String name;
		private Integer value;

		private IPAddressStatus(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Integer getValue() {
			return value;
		}
	}

}
