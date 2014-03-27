package com.sobey.zabbix.data;

import com.sobey.zabbix.entity.Authenticate;
import com.sobey.zabbix.entity.Params;

public class TestData {

	public static Authenticate randomAuthenticate() {

		Authenticate authenticate = new Authenticate();

		Params params = new Params();

		params.setUser("print");
		params.setPassword("print");

		authenticate.setId("0");
		authenticate.setJsonrpc("2.0");
		authenticate.setMethod("user.login");
		authenticate.setParams(params);

		return authenticate;
	}

}
