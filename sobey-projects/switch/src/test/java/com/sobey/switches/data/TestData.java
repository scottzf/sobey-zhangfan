package com.sobey.switches.data;

import com.sobey.switches.webservice.response.dto.SwitchPolicyParameter;

public class TestData {

	public static SwitchPolicyParameter randomSwitchPolicyParameter() {
		SwitchPolicyParameter parameter = new SwitchPolicyParameter();
		parameter.setVlanId(80);
		parameter.setHostIp("10.10.90.9");
		return parameter;
	}

}
