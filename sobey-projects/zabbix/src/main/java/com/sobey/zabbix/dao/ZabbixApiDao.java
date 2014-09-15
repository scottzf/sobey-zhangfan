package com.sobey.zabbix.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sobey.core.mapper.JsonMapper;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.zabbix.constans.ItemEnum;
import com.sobey.zabbix.entity.Authenticate;
import com.sobey.zabbix.entity.Params;
import com.sobey.zabbix.webservice.response.dto.ZHistoryItemDTO;
import com.sobey.zabbix.webservice.response.dto.ZItemDTO;

/**
 * 针对zabbix api的使用
 * 
 * https://www.zabbix.com/documentation/2.2/manual/api
 * 
 * @author Administrator
 *
 */
@Repository
public class ZabbixApiDao {

	private static PropertiesLoader ZABBIX_LOADER = new PropertiesLoader("classpath:/zabbix.properties");

	private static final String ZABBIX_API_URL = ZABBIX_LOADER.getProperty("ZABBIX_API_URL");
	private static final String ZABBIX_USERNAME = ZABBIX_LOADER.getProperty("ZABBIX_USERNAME");
	private static final String ZABBIX_PASSWORD = ZABBIX_LOADER.getProperty("ZABBIX_PASSWORD");

	/**
	 * 根据hostId删除Host
	 * 
	 * @param hostId
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public void deleteHost(String hostId) throws JSONException, IOException {

		String[] arr = new String[1];
		arr[0] = hostId;

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.delete");
		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("params", arr);

		executeZabbixMethod(jsonObj);
	}

	/**
	 * 根据hostId 和itemkey 获得监控内容
	 * 
	 * @param hostId
	 * @param itemKey
	 *            监控项 {@link ItemEnum }
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ZItemDTO getItem(String hostId, String itemKey) throws IOException, JSONException {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("auth", getToken());
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "item.get");
		jsonObj.put("params",
				(new JSONObject().put("search", (new JSONObject()).put("key_", itemKey)).put("hostids", hostId).put(
						"output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		ZItemDTO item = new ZItemDTO();

		if (node != null) {

			item.setItemid(subResult(node, "itemid"));
			item.setClock(subResult(node, "lastclock"));
			item.setValue(subResult(node, "lastvalue"));
			item.setUnits(subResult(node, "units"));
		}

		return item;
	}

	/**
	 * 根据hostId 和itemkey 获得历史监控内容
	 * 
	 * @param hostId
	 * @param itemKey
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ZHistoryItemDTO gethistory(String hostId, String itemKey) throws IOException, JSONException {

		int limits = 10; // 历史数据显示数量

		ZItemDTO zItemDTO = getItem(hostId, itemKey);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "history.get");
		jsonObj.put("auth", getToken());
		jsonObj.put(
				"params",
				(new JSONObject().put("output", "extend").put("limit", limits).put("sortfield", "clock")
						.put("sortorder", "DESC").put("history", 0).put("itemids", zItemDTO.getItemid()).put("hostids",
						hostId)));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode root = new ObjectMapper().readTree(resStr);

		JsonNode data = root.path("result");

		ArrayList<ZItemDTO> zItemDTOs = new ArrayList<ZItemDTO>();

		for (int i = 0; i < limits; i++) {

			JsonNode node = data.get(i);

			System.out.println(node);
			if (node != null) {

				ZItemDTO itemDTO = new ZItemDTO();
				itemDTO.setClock(subResult(node.get("clock")));
				itemDTO.setItemid(subResult(node.get("itemid")));
				itemDTO.setUnits(""); // api中没有单位
				itemDTO.setValue(subResult(node.get("value")));
				zItemDTOs.add(itemDTO);
			}
		}

		ZHistoryItemDTO zHistoryItemDTO = new ZHistoryItemDTO();
		zHistoryItemDTO.setzItemDTOs(zItemDTOs);

		return zHistoryItemDTO;
	}

	/**
	 * 根据Host name 获得其Id.
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getHostId(String name) throws IOException, JSONException {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.get");
		jsonObj.put("auth", getToken());

		jsonObj.put("params",
				(new JSONObject().put("filter", (new JSONObject()).put("name", name)).put("output", "extend")));

		String resStr = executeZabbixMethod(jsonObj);

		JsonNode node = new ObjectMapper().readTree(resStr);

		return subResult(node, "hostid");
	}

	/**
	 * 用POST方式访问zabbix api
	 * 
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	private static String executeZabbixMethod(JSONObject jsonObject) throws IOException {

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

	/**
	 * 获得验证令牌
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String getToken() throws IOException {

		Params params = new Params();
		params.setUser(ZABBIX_USERNAME);
		params.setPassword(ZABBIX_PASSWORD);

		Authenticate authenticate = new Authenticate(params);

		JsonMapper jsonMapper = new JsonMapper();

		// 将entity转换成json格式的字符串
		String jsonStr = jsonMapper.toJson(authenticate);

		// 将json通过post方式发送到指定的URL上
		String resultStr = executeZabbixMethod(new JSONObject(jsonStr));

		JsonNode node = new ObjectMapper().readTree(resultStr);

		return subResult(node, "result");

	}

	/**
	 * 对返回的json字符串进行解析处理
	 * 
	 * eg:
	 * 
	 * ["net.if.in[eth1]"] -> net.if.in[eth1]
	 * 
	 * @param node
	 * @param value
	 * @return
	 */
	private static String subResult(JsonNode node, String value) {
		return StringUtils.substringBetween(node.findValues(value).toString(), "[\"", "\"]");
	}

	/**
	 * 对返回的json字符串进行解析处理
	 * 
	 * eg:
	 * 
	 * "net.if.in[eth1]" -> net.if.in[eth1]
	 * 
	 * @param node
	 * @return
	 */
	private static String subResult(JsonNode node) {
		return StringUtils.substringBetween(node.toString(), "\"", "\"");
	}

}
