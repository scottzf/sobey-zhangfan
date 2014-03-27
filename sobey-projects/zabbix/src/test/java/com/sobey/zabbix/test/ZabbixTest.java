package com.sobey.zabbix.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sobey.core.mapper.JsonMapper;
import com.sobey.zabbix.data.TestData;
import com.sobey.zabbix.entity.Authenticate;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ZabbixTest extends TestCase {

	private static String ZABBIX_API_URL = "http://172.20.0.101/zabbix/api_jsonrpc.php";

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException, JSONException {

		System.err.println(getHostId("172.30.0.2"));

	}

	public static String getHostId(String ip) throws IOException, JSONException {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "host.get");
		jsonObj.put("auth", getToken());

		jsonObj.put("params",
				(new JSONObject().put("filter", (new JSONObject()).putOpt("ip", ip)).put("output", "extend")));

		String resObj = executeZabbixMethod(jsonObj.toString());

		JsonNode node = new ObjectMapper().readTree(resObj);

		return StringUtils.remove(node.findValues("hostid").get(1).toString(), "\"");
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

		String resObj = executeZabbixMethod(jsonObj.toString());

		JsonNode node = new ObjectMapper().readTree(resObj);
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
		String resultStr = executeZabbixMethod(jsonStr);

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
	public static String executeZabbixMethod(String jsonStr) throws IOException {

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(ZABBIX_API_URL);

		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(jsonStr, "UTF-8"));

		CloseableHttpResponse response = client.execute(httpPost);

		HttpEntity entity = response.getEntity();

		InputStream inputStream = entity.getContent();

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		return br.readLine().toString();
	}

}
