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
	 * 描述LookUp中ServiceType的Description和Id.
	 * 
	 * <pre>
	 * ECS	 26 
	 * ES3 	 91 
	 * EIP 	 93
	 * ELB 	 92
	 * DNS 	 94
	 * ESG 	 95
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum ServiceType {

		ECS("ECS", 26),

		ES3("ES3", 91),

		EIP("EIP", 93),

		ELB("ELB", 92),

		DNS("DNS", 94),

		ESG("ESG", 95);

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
	 * 已使用	 41 
	 * 未使用 	 74
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum IPAddressStatus {

		已使用("已使用", 41),

		未使用("未使用", 74);

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

	/**
	 * 描述LookUp中VlanStatus的Description和Id.
	 * 
	 * <pre>
	 * 已使用	 100 
	 * 未使用 	 99
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum VlanStatus {

		已使用("已使用", 100),

		未使用("未使用", 99);

		private String name;
		private Integer value;

		private VlanStatus(String name, Integer value) {
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

	public enum TagType {

		上级标签("上级标签", 45),

		子标签("子标签", 46);

		private String name;
		private Integer value;

		private TagType(String name, Integer value) {
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
