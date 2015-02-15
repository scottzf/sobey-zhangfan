package com.sobey.switches.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import com.sobey.switches.constans.RPCConstants;

/**
 * 盛科RPC API相关操作工具类
 * 
 * @author Administrator
 *
 */
public class JsonRPCUtil {

	/**
	 * 获得mac地址对应的交换机端口
	 * 
	 * @param whSwitch
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public static String getSwitchPortByMac(String whSwitch, String mac) throws Exception {
		/**
		 * 根据核心交换机的接口获得接入层交换机ip
		 */
		String switchIp = getSwitchIPByInterfaceStr(whSwitch);

		if (StringUtils.isEmpty(switchIp)) {
			throw new Exception("端口没有接入交换机");
		}

		// 构建需要执行的命令
		String[] cmds = new String[] { "show mac address-table | begin " + mac };

		/**
		 * RPC API获得mac address-table响应字符串
		 */
		String responseStr = executeJsonRPCRequest(switchIp, cmds);

		/**
		 * 处理响应字符串
		 */
		String switchPort = getSwitchPortByProcessResponseStr(responseStr, mac);

		return switchPort;
	}

	/**
	 * Apache HTTP client以POST方式执行CLI命令
	 * 
	 * @throws Exception
	 */
	public static String executeJsonRPCRequest(String url, String[] cmds) throws IOException {

		JSONObject jsonObject = getJsonScript(cmds);

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(generateSwitchAPIRequestURL(url));
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

		CloseableHttpResponse response = client.execute(httpPost);

		HttpEntity entity = response.getEntity();

		InputStream inputStream = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String responseStr = br.readLine().toString();

		return responseStr;
	}

	/**
	 * 生成盛科API访问URL
	 * 
	 * @return
	 */
	private static String generateSwitchAPIRequestURL(String ip) {
		return "http://" + ip + ":80/command-api";
	}

	/**
	 * 根据接口字符串获得对应交换机的URL
	 * 
	 * @param whSwitch
	 * @return
	 */
	private static String getSwitchIPByInterfaceStr(String whSwitch) {

		if (RPCConstants.PORT_SWITCH_TOR_A.equals(whSwitch)) {
			return SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL");
		}
		if (RPCConstants.PORT_SWITCH_TOR_B.equals(whSwitch)) {
			return SDNPropertiesUtil.getProperty("TOR-B_SWITCH_URL");
		}
		return null;
	}

	/**
	 * 通过处理命令响应字符串得到交换机端口字符串
	 * 
	 * @param responseStr
	 * @return
	 */
	private static String getSwitchPortByProcessResponseStr(String responseStr, String mac) {

		// 截取对应Mac的端口字符串
		String macPortStr = StringUtils.substringBetween(responseStr, mac, "\\n");

		// 去掉字符串中含有“dynamic”空格
		String port = StringUtils.remove(macPortStr, RPCConstants.RPC_MACADDRESS_EXCESSSTR).trim();

		return port;
	}

	/**
	 * 根据命令集合获取json对象
	 * 
	 * @param cmds
	 * @return
	 */
	public static JSONObject getJsonScript(String[] cmds) {

		/**
		 * 命令参数列表
		 */
		List<JSONObject> paramsObjects = new ArrayList<JSONObject>();

		/**
		 * 命令参数
		 */
		JSONObject paramsObject = new JSONObject();
		// 设置命令参数中的固定属性
		paramsObject.put("format", RPCConstants.RPC_API_FORMAT); // 期望命令返回格式
		paramsObject.put("version", RPCConstants.RPC_API_VERSION); // 命令版本号
		// 命令参数中的可变命令行列表
		paramsObject.put("cmds", cmds);

		paramsObjects.add(paramsObject);

		JSONObject resultObject = new JSONObject();
		// 设置请求参数中的固定属性
		resultObject.put("jsonrpc", RPCConstants.RPC_API_JSONRPC); // JSON RPC协议版本号
		resultObject.put("id", RPCConstants.RPC_API_ID); // JSON RPC协议中的UID
		resultObject.put("method", RPCConstants.RPC_API_METHOD); // 运行交换机CLI命令的方法
		resultObject.put("params", paramsObjects); // 命令参数
		System.out.println(resultObject);
		return resultObject;
	}
}
