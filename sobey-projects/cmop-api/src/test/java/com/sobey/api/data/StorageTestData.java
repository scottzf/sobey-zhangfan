package com.sobey.api.data;

import com.sobey.generate.storage.CreateEs3Parameter;
import com.sobey.generate.storage.DeleteEs3Parameter;
import com.sobey.generate.storage.MountEs3Parameter;
import com.sobey.generate.storage.UmountEs3Parameter;

public class StorageTestData {

	private static String host = "10.10.2.34";
	private static String userName = "root";
	private static String password = "XA@S0bey";

	public static CreateEs3Parameter randomCreateEs3Parameter() {

		CreateEs3Parameter parameter = new CreateEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setVolumeSize("20");
		parameter.setControllerIP(host);
		parameter.setUsername(userName);
		parameter.setPassword(password);

		return parameter;
	}

	public static DeleteEs3Parameter randomDeleteEs3Parameter() {

		DeleteEs3Parameter parameter = new DeleteEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setControllerIP(host);
		parameter.setUsername(userName);
		parameter.setPassword(password);

		return parameter;
	}

	public static MountEs3Parameter randomMountEs3Parameter() {

		MountEs3Parameter parameter = new MountEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setClientIP("10.10.101.1");
		parameter.setControllerIP(host);
		parameter.setUsername(userName);
		parameter.setPassword(password);

		return parameter;

	}

	public static UmountEs3Parameter randomUmountEs3Parameter() {

		UmountEs3Parameter parameter = new UmountEs3Parameter();

		parameter.setClientIP("10.10.101.1");
		parameter.setControllerIP(host);
		parameter.setUsername(userName);
		parameter.setPassword(password);

		return parameter;
	}

}
