package com.sobey.storage.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;

/**
 * 测试数据
 * 
 * @author Administrator
 *
 */
public class TestData {

	private static String controllerIP = "10.10.2.34";
	private static String username = "root";
	private static String password = "XA@S0bey";

	public static CreateEs3Parameter randomCreateEs3Parameter() {

		CreateEs3Parameter parameter = new CreateEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setVolumeSize("20");
		parameter.setControllerIP(controllerIP);
		parameter.setUsername(username);
		parameter.setPassword(password);

		return parameter;
	}

	public static DeleteEs3Parameter randomDeleteEs3Parameter() {

		DeleteEs3Parameter parameter = new DeleteEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setControllerIP(controllerIP);
		parameter.setUsername(username);
		parameter.setPassword(password);

		return parameter;
	}

	public static ModifytEs3RuleParameter randomModifytEs3RuleParameter() {

		ModifytEs3RuleParameter parameter = new ModifytEs3RuleParameter();

		parameter.setControllerIP(controllerIP);
		parameter.setUsername(username);
		parameter.setPassword(password);

		parameter.setVolumeName("liukai");

		List<String> clientIPs = new ArrayList<String>();
		clientIPs.add("10.10.101.1");
		clientIPs.add("10.10.101.2");

		parameter.setClientIPs(clientIPs);

		return parameter;
	}

}
