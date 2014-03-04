package com.sobey.storage.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.storage.webservice.response.dto.NetAppParameter;

public class TestData {

	public static NetAppParameter randomNetAppParameter() {

		NetAppParameter parameter = new NetAppParameter();

		parameter.setVolumeName("liukai");
		parameter.setVolumeSize("20");
		parameter.setNetAppIPaddress("10.10.1.6");
		parameter.setClientIPaddress("10.10.1.42");

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
