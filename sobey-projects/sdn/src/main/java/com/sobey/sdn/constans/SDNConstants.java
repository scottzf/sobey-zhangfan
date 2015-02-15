package com.sobey.sdn.constans;

public class SDNConstants {

	public static final String SWITCH_NAME = "vSwitch2"; // 标准虚拟网络交换机

	public static final String ENTER_SIGN = "\r"; // 换行符

	public static final String FIREWALL_USERNAME = "admin"; // vRouter 用户名

	public static final String FIREWALL_PASSWORD = "mcloud@sobey.com"; // vRouter 密码

	public static final String PORT10_SUBNETMASK = "255.255.254.0"; // 修改vRouter端口10的网段掩码

	public static final String VROUTER_PORT10_MODEL_IP = "10.2.253.253"; // 修改vRouter端口10的网段掩码

	public static final String VROUTER_REGISTER_CMD = "exec update-now"; // vRouter注册命令

	public static final String VROUTER_TEMPLATE = "vRouter_MOD_5.0.10"; // vRouter模板

	public static final String CTC_DEFAULT_PORT = "port8"; // 电信默认连接端口

	public static final String CTC_DEFAULT_PORTGROUPNAME = "ISP_CTC_VLAN1000"; // 电信默认连接端口组

	public static final int CTC_DEFAULT_PORTNO = 8; // 电信默认连接端口序号

	public static final String CTC_DEFAULT_IP = "221.237.156.153"; // 电信默认连接端口序号

	public static final String CTC_MAPPING_GROUP_NAME = "CTC_ALL_Server"; // 电信默认eip映射组名

	public static final String VCENTER_IP = "10.2.7.250"; // 登陆VCENTER的IP地址

	public static final String VCENTER_USERNAME = "administrator@vsphere.local"; // 登陆VCENTER的用户名
	
	public static final String VCENTER_PASSWORD = "Newmed!@s0bey"; // 登陆VCENTER的密码

}
