package com.sobey.switches.constans;

import java.util.HashMap;
import java.util.Map;

public class HostRelationMap {

	public static Map<String, String> relationMap = new HashMap<String, String>();

	static {
		relationMap.put("10.2.5.21", "TOR-A eth-0-2");
		relationMap.put("10.2.5.22", "TOR-A eth-0-4");
		relationMap.put("10.2.5.23", "TOR-B eth-0-5");
		relationMap.put("10.2.5.25", "TOR-A eth-0-10");
		relationMap.put("10.2.5.26", "TOR-A eth-0-12");
		relationMap.put("10.2.5.27", "TOR-A eth-0-14");
		relationMap.put("10.2.5.28", "TOR-A eth-0-16");
		relationMap.put("10.2.5.29", "TOR-A eth-0-18");

	}
}
