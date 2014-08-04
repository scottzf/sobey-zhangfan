package com.sobey.api.constans;

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
	 * 描述LookUp中OperateType的Description和Id.
	 * 
	 * <pre>
	 * 创建	  
	 * 更新 	 
	 * 删除 	  
	 * 关闭 	  
	 * 启动 	 
	 * 应用规则至 	  
	 * 释放	  
	 * 挂载	  
	 * 应用规则至 	 
	 * 卸载
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum OperateType {

		创建("创建", 48),

		更新("更新", 75),

		删除("删除", 76),

		关闭("关闭", 101),

		启动("启动", 102),

		应用规则至("应用规则至", 103),

		释放("释放", 104),

		挂载("挂载", 105),

		卸载("卸载", 95);

		private String name;
		private Integer value;

		private OperateType(String name, Integer value) {
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
	 * ECS	 
	 * ES3 	 
	 * EIP 	
	 * ELB 	
	 * DNS 	
	 * ESG
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
	 * 描述LookUp中AgentType的Description和Id.
	 * 
	 * <pre>
	 * Fortigate	 
	 * Netscaler 	 
	 * VMware 	
	 * H3C 	
	 * NetApp
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum AgentType {

		Fortigate("Fortigate", 79),

		Netscaler("Netscaler", 78),

		VMware("VMware", 47),

		H3C("H3C", 81),

		NetApp("NetApp", 80);

		private String name;
		private Integer value;

		private AgentType(String name, Integer value) {
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
	 * 描述ES3Type中AgentType的Description和Id.
	 * 
	 * <pre>
	 * 高IOPS
	 * 高吞吐
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum ES3Type {

		高IOPS("高IOPS", 73),

		高吞吐("高吞吐", 44);

		private String name;
		private Integer value;

		private ES3Type(String name, Integer value) {
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
	 * ECS电源操作
	 * 
	 * 
	 * @author Administrator
	 * 
	 */
	public enum powerOperation {
		poweroff, poweron;
	}

	/**
	 * 描述LookUp中Result的Description和Id.
	 * 
	 * <pre>
	 * 成功	 
	 * 失败
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum Result {

		成功("成功", 63),

		失败("失败", 49);

		private String name;
		private Integer value;

		private Result(String name, Integer value) {
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
	 * 描述LookUp中ECSStatus的VlanStatus和Id.
	 * 
	 * <pre>
	 * 运行
	 * 已使用
	 * </pre>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum ECSStatus {

		运行("运行", 34),

		停止("停止", 71);

		private String name;
		private Integer value;

		private ECSStatus(String name, Integer value) {
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

	public enum IPAddressPool {

		PrivatePool("PrivatePool", 66),

		InternetPool("InternetPool", 28),

		VIPPool("VIPPool", 67);

		private String name;
		private Integer value;

		private IPAddressPool(String name, Integer value) {
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

	public enum ISP {

		中国电信("中国电信", 29),

		中国联通("中国联通", 65);

		private String name;
		private Integer value;

		private ISP(String name, Integer value) {
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

	public enum EIPStatus {

		未使用("未使用", 35),

		已使用("已使用", 72);

		private String name;
		private Integer value;

		private EIPStatus(String name, Integer value) {
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

	public enum PolicyType {

		Deny("Deny", 98),

		Permit("Permit", 36);

		private String name;
		private Integer value;

		private PolicyType(String name, Integer value) {
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
