package com.sobey.storage.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;

public class TestData {

	public static CreateEs3Parameter randomCreateEs3Parameter() {

		CreateEs3Parameter parameter = new CreateEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setVolumeSize("20");
		parameter.setClientIPaddress("10.10.1.42");

		return parameter;
	}

	public static DeleteEs3Parameter randomDeleteEs3Parameter() {

		DeleteEs3Parameter parameter = new DeleteEs3Parameter();

		parameter.setVolumeName("liukai");

		return parameter;
	}

	public static MountEs3Parameter randomMountEs3Parameter() {

		MountEs3Parameter parameter = new MountEs3Parameter();

		parameter.setVolumeName("liukai");
		parameter.setNetAppIPaddress("10.10.1.6");
		parameter.setClientIPaddress("10.10.1.42");

		return parameter;

	}

	public static UmountEs3Parameter randomUmountEs3Parameter() {

		UmountEs3Parameter parameter = new UmountEs3Parameter();

		parameter.setClientIPaddress("10.10.1.42");

		return parameter;
	}

	public static RemountEs3Parameter randomRemountEs3Parameter() {

		RemountEs3Parameter parameter = new RemountEs3Parameter();

		parameter.setVolumeName("liukai");

		List<String> beforeClientIPaddress = new ArrayList<String>();
		beforeClientIPaddress.add("10.10.1.6");
		beforeClientIPaddress.add("10.10.1.42");

		List<String> afterClientIPaddress = new ArrayList<String>();
		afterClientIPaddress.add("10.10.1.6");
		afterClientIPaddress.add("10.10.1.41");
		afterClientIPaddress.add("10.10.1.42");

		parameter.setBeforeClientIPaddress(beforeClientIPaddress);
		parameter.setAfterClientIPaddress(afterClientIPaddress);

		return parameter;
	}

}
