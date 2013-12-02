package com.sobey.switches.data;

import com.sobey.switches.webservice.response.dto.VlanParameter;

public class TestData {

	public static VlanParameter randomVlanParameter() {

		VlanParameter parameter = new VlanParameter();

		parameter.setVlanId(80);
		parameter.setGateway("172.21.71.254");
		parameter.setNetMask("255.255.255.0");

		return parameter;
	}

}
