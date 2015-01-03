package com.sobey.switches.data;

import com.sobey.switches.webservice.response.dto.SwitchPolicyParameter;

public class TestData {

	public static SwitchPolicyParameter randomSwitchPolicyParameter() {
		SwitchPolicyParameter parameter = new SwitchPolicyParameter();
		parameter.setVlanId(80);
		parameter.setEthName("eth-0-26");
		return parameter;
	}

}
