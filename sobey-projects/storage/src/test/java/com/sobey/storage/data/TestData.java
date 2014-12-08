package com.sobey.storage.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.Es3SizeParameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;

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

	public static MountEs3Parameter randomMountEs3Parameter() {

		MountEs3Parameter parameter = new MountEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setClientIP("10.10.101.1");
		parameter.setControllerIP(controllerIP);
		parameter.setUsername(username);
		parameter.setPassword(password);

		return parameter;

	}

	public static UmountEs3Parameter randomUmountEs3Parameter() {

		UmountEs3Parameter parameter = new UmountEs3Parameter();

		parameter.setClientIP("10.10.101.1");

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

	public static Es3SizeParameter randomEs3SizeParameter() {

		Es3SizeParameter parameter = new Es3SizeParameter();

		parameter.setControllerIP(controllerIP);
		parameter.setUsername(username);
		parameter.setPassword(password);
		parameter.setVolumeName("liukai");

		return parameter;
	}

}
