package com.sobey.zabbix.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sobey.core.mapper.JsonMapper;
import com.sobey.zabbix.constans.ItemEnum;
import com.sobey.zabbix.data.TestData;
import com.sobey.zabbix.entity.Authenticate;
import com.sobey.zabbix.entity.ZItem;
import com.sobey.zabbix.service.ZabbixApiService;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-zabbix.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ZabbixTest extends TestCase {

	private static String ZABBIX_API_URL = "http://113.142.30.36:82/zabbix/api_jsonrpc.php";

	@Autowired
	public ZabbixApiService service;

	// @Test
	public void test() throws JSONException, IOException {
		// System.out.println(service.getItem("10.10.2.111", ItemEnum.Free_disk_space_on.getName()));

		HashMap<String, String> map = getDrule("cdtest");

		String druleid = "";
		String iprange = "";

		for (Entry<String, String> entity : map.entrySet()) {
			druleid = entity.getKey();
			iprange = entity.getValue();
		}

		System.out.println(iprange + ",10.10.100.1");

		updateDrule(druleid, iprange, "cdtest");

	}

	@Test
	public void insert() throws IOException {

		String templateId = getTemplateId("streaming"); // 模板Id

		List<String> list = FileUtils.readLines(new File("D:\\sour.txt"));

		System.out.println(list.size());

		for (String name : list) {

			String[] arr = StringUtils.split(name, ":");

			// createItem(templateId, name);
			createItem(templateId, arr[1], arr[0]);
			createTrigger(templateId, arr[1], arr[0]);
		}

		System.out.println("Over!");
	}

	// @Test
	public void getTemplate() throws JSONException, IOException {

		/**
		 * 注意模板和host是写在一个表中的.
		 */
		String templateId = getTemplateId("streaming"); // 模板Id
		getItem(templateId);
		getTrigger(templateId);

	}

	// @Test
	public void zabbixAPITest() throws JsonGenerationException, JsonMappingException, IOException, JSONException {

		String hostId = getHostId("10.10.2.111");

		System.out.println("hostId:" + hostId);

		ZItem item = getItem(hostId, ItemEnum.traffic_in.getName());
		System.out.println(item.getLastValue());
		System.out.println(item.getUnits());

	}

	public static HashMap<String, String> getDrule(String name) throws JSONException, IOException {

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "drule.get");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());

		jsonObj.put("params",
				(new JSONObject().put("filter", (new JSONObject()).put("name", name)).put("output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(subResult(node, "druleid"), subResult(node, "iprange"));

		return map;
	}

	public static String updateDrule(String druleid, String iprange, String name) throws JSONException, IOException {

		// https://www.zabbix.com/documentation/2.2/manual/discovery/network_discovery/rule

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "drule.update");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());

		jsonObj.put("params", (new JSONObject().put("druleid", druleid)).put("iprange", iprange));

		System.out.println(jsonObj);
		String resStr = executeZabbixMethod(jsonObj);
		System.err.println(resStr);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return subResult(node, "druleids");
	}

	public static String getDcheck(String druleid) throws JSONException, IOException {

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "dcheck.get");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());

		jsonObj.put("params", (new JSONObject().put("druleids", druleid)).put("output", "extend"));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return subResult(node, "dcheckid");
	}

	public static String deleteHost(String hostId) throws JSONException, IOException {

		String[] arr = new String[1];
		arr[0] = hostId;

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.delete");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());

		jsonObj.put("params", arr);

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return subResult(node, "hostids");

	}

	/**
	 * 根据模板名称获得模板的ID
	 * 
	 * @param templateName
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String getTemplateId(String templateName) throws IOException, JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "template.get");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());

		jsonObj.put("params", (new JSONObject().put("filter", (new JSONObject()).putOpt("name", templateName)).put(
				"output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return subResult(node, "templateid");
	}

	/**
	 * 根据hostId和监控键值,获得监控对象.
	 * 
	 * key在zabbix中组态 -> 主机 -> 项目 中可查看
	 * 
	 * @param hostId
	 *            主机Id
	 * @param key
	 *            监控键值
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static ZItem getItem(String hostId, String key) throws IOException, JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "item.get");
		jsonObj.put("params",
				(new JSONObject().put("search", (new JSONObject()).put("key_", key)).put("hostids", hostId).put(
						"output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		System.err.println(resStr);

		JsonNode node = new ObjectMapper().readTree(resStr);

		ZItem item = new ZItem();
		item.setLogTimefmt(subResult(node, "logtimefmt"));
		item.setType(subResult(node, "type"));
		item.setInventoryLink(subResult(node, "inventory_link"));
		item.setPassword(subResult(node, "password"));
		item.setUserName(subResult(node, "username"));
		item.setLastlogSize(subResult(node, "lastlogsize"));
		item.setDataType(subResult(node, "data_type"));
		item.setDescription(subResult(node, "description"));
		item.setTrapperHosts(subResult(node, "trapper_hosts"));
		item.setPrivateKey(subResult(node, "privatekey"));
		item.setValueMapId(subResult(node, "valuemapid"));
		item.setStatus(subResult(node, "status"));
		item.setDelta(subResult(node, "delta"));
		item.setmTime(subResult(node, "mtime"));
		item.setLastclock(subResult(node, "lastclock"));
		item.setLastValue(subResult(node, "lastvalue"));
		item.setDelay(subResult(node, "delay"));
		item.setTrends(subResult(node, "trends"));
		item.setValueType(subResult(node, "value_type"));
		item.setPort(subResult(node, "port"));
		item.setAuthType(subResult(node, "authtype"));
		item.setLastns(subResult(node, "lastns"));
		item.setItemId(subResult(node, "itemid"));
		item.setPublicKey(subResult(node, "publickey"));
		item.setPrevValue(subResult(node, "prevvalue"));
		item.setName(subResult(node, "name"));
		item.setFlags(subResult(node, "flags"));
		item.setTemplateId(subResult(node, "templateid"));
		item.setDelayflex(subResult(node, "delay_flex"));
		item.setParams(subResult(node, "params"));
		item.setMultiplier(subResult(node, "multiplier"));
		item.setUnits(subResult(node, "units"));
		item.setKey(subResult(node, "key_"));
		item.setHistory(subResult(node, "history"));
		item.setHostId(subResult(node, "hostid"));
		item.setFormula(subResult(node, "formula"));

		return item;
	}

	public static ZItem getItem(String templateid) throws IOException, JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "item.get");
		jsonObj.put("params", (new JSONObject().put("search", (new JSONObject()).put("key_", "check_xa193_hztv2-m3u8"))
				.put("hostids", templateid).put("output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);
		System.err.println(resStr);
		return null;
	}

	/**
	 * 创建监控项
	 * 
	 * @param name
	 * @param hostid
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void createItem(String hostid, String name) throws JSONException, IOException {
		createItem(hostid, name, name);
	}

	public static void createItem(String hostid, String name, String key) throws JSONException, IOException {

		// mdn1 1546
		// mdn2 1548
		// sour 1550
		String[] applications = new String[1];
		applications[0] = "1550";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "item.create");
		jsonObj.put(
				"params",
				(new JSONObject().put("name", name).put("applications", applications).put("key_", key)
						.put("hostid", hostid).put("valuemapid", "14").put("value_type", "3").put("interfaceid", "0")
						.put("type", 0).put("delay", 300)));

		System.out.println(executeZabbixMethod(jsonObj));
	}

	public static void getTrigger(String hostid) throws JSONException, IOException {

		String[] arr = new String[1];
		arr[0] = "1455";// 应用集ID

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "trigger.get");
		jsonObj.put("params", (new JSONObject()).put("hostids", hostid).put("output", "extend"));
		System.out.println(executeZabbixMethod(jsonObj));
	}

	public static void createTrigger(String hostid, String name, String key) throws IOException {

		String expression = "{streaming:" + key + ".last(#5,0)}=0";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "trigger.create");
		jsonObj.put(
				"params",
				(new JSONObject()).put("description", name).put("expression", expression).put("priority", 3)
						.put("hostids", hostid));
		System.out.println(executeZabbixMethod(jsonObj));
	}

	private static String subResult(JsonNode node, String value) {
		return StringUtils.substringBetween(node.findValues(value).toString(), "[\"", "\"]");

	}

	/**
	 * 根据host name获得host Id
	 * 
	 * @param ip
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String getHostId(String name) throws IOException, JSONException {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.get");
		jsonObj.put("auth", getToken());

		jsonObj.put("params",
				(new JSONObject().put("filter", (new JSONObject()).put("name", name)).put("output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return node.findValues("hostid").size() != 0 ? StringUtils.remove(node.findValues("hostid").get(1).toString(),
				"\"") : "";
	}

	/**
	 * 获得host list
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String getHostList() throws IOException, JSONException {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.get");
		jsonObj.put("auth", getToken());

		jsonObj.put(
				"params",
				(new JSONObject().put("filter", (new JSONObject()).putOpt("host", "")).put("output", new String[] {
						"hostid", "name" })));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);
		return node.toString();
	}

	/**
	 * 获得验证令牌
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String getToken() throws IOException {

		Authenticate authenticate = TestData.randomAuthenticate();

		JsonMapper jsonMapper = new JsonMapper();

		// 将entity转换成json格式的字符串
		String jsonStr = jsonMapper.toJson(authenticate);

		// 将json通过post方式发送到指定的URL上
		String resultStr = executeZabbixMethod(new JSONObject(jsonStr));

		JsonNode node = new ObjectMapper().readTree(resultStr);

		return StringUtils.remove(node.get("result").toString(), "\"");

	}

	/**
	 * 用POST方式访问zabbix api
	 * 
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	public static String executeZabbixMethod(JSONObject jsonObject) throws IOException {

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(ZABBIX_API_URL);

		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));

		CloseableHttpResponse response = client.execute(httpPost);

		HttpEntity entity = response.getEntity();

		InputStream inputStream = entity.getContent();

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		return br.readLine().toString();
	}

}
