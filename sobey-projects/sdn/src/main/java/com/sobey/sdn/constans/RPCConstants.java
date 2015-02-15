package com.sobey.sdn.constans;

import java.util.HashSet;
import java.util.Set;
/**
 * 操作盛科交换机的常量
 * @author Administrator
 *
 */
public class RPCConstants {

	public static final String RPC_API_JSONRPC = "2.0"; // JSON RPC协议版本号

	public static final String RPC_API_ID = "70853aff-af77-420e-8f3c-fa9430733a19"; // JSON RPC协议中的UID

	public static final String RPC_API_METHOD = "executeCmds"; // 运行交换机CLI命令的方法

	public static final String RPC_API_FORMAT = "text"; // 期望命令返回格式, 可以是’text’或者’json’, 默认’text’

	public static final String RPC_API_VERSION = "1"; // 命令版本号

	public static final String PORT_SWITCH_TOR_A = "BAGG7";  //核心交换机对应10.2.2.8交换机的接口
	
	public static final String PORT_SWITCH_TOR_B = "BAGG8"; //核心交换机对应10.2.2.9交换机的接口
	
	public static final String RPC_MACADDRESS_EXCESSSTR = "dynamic"; //mac address-table中交换机端口前多余的字符串
	
	public static final String model = ""; //
	
	public static final Set<String> hostSet = new HashSet<String>();  //资源计算机ip地址集合
	static{
		hostSet.add("172.16.3.11");
		hostSet.add("172.16.3.12");
		hostSet.add("172.16.3.13");
		hostSet.add("172.16.3.14");
		hostSet.add("172.16.3.15");
		hostSet.add("172.16.3.16");
		hostSet.add("172.16.3.17");
		hostSet.add("172.16.3.18");
		hostSet.add("172.16.3.19");
		hostSet.add("172.16.3.20");
		hostSet.add("172.16.3.21");
		hostSet.add("172.16.3.22");
	}
	
}
