package com.sobey.zabbix.data;

import com.sobey.zabbix.entity.Authenticate;
import com.sobey.zabbix.entity.Params;

public class TestData {

	public static Authenticate randomAuthenticate() {

		Params params = new Params();

		params.setUser("Admin");
		params.setPassword("zabbix");

		Authenticate authenticate = new Authenticate(params);

		return authenticate;
	}

}
