package com.sobey.firewall.test;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.firewall.PbulicProperties;
import com.sobey.firewall.data.TestData;
import com.sobey.firewall.webservice.response.dto.RouterParameter;
import com.sobey.firewall.webservice.response.dto.SubnetParameter;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest implements PbulicProperties {

	private static final String ENTER_SIGN = "\r";

	@Test
	public void test() {

		StringBuilder sb = new StringBuilder();

		String segment = " 10.10.10.0";
		String subnetMask = "255.255.255.0";
		String gateway = "10.10.10.254";

		sb.append("config firewall address").append(ENTER_SIGN);
		sb.append("edit ").append("\"").append(segment).append("\"").append(ENTER_SIGN);
		sb.append("set subnet ").append(gateway).append(" ").append(subnetMask).append(ENTER_SIGN);
		sb.append("next");
		System.out.println(sb.toString());
	}

	@Test
	public void a() {

		StringBuilder sb = new StringBuilder();

		RouterParameter routerParameter = TestData.randomRouterParameter();

		ArrayList<SubnetParameter> list = routerParameter.getSubnetParameters();

		for (int i = 0; i < list.size(); i++) {

			SubnetParameter subnet = list.get(i);

			String segment = subnet.getSegment();
			String subnetMask = subnet.getSubnetMask();
			String gateway = subnet.getGateway();

			sb.append("config firewall address").append(ENTER_SIGN);
			sb.append("edit ").append("\"").append(segment).append("\"").append(ENTER_SIGN);
			sb.append("set subnet ").append(gateway).append(" ").append(subnetMask).append(ENTER_SIGN);
			sb.append("next");

			sb.append("config firewall policy").append(ENTER_SIGN);
			sb.append("edit ").append(subnet.getPolicyId()).append(ENTER_SIGN);

			sb.append("set srcintf ").append("\"").append("port").append(subnet.getSegment()).append("\"")
					.append(ENTER_SIGN);
			sb.append("set srcaddr ").append("\"").append(subnet.getSegment()).append("\"").append(ENTER_SIGN);

			// new 一个新的list出来,将传递进来的list填充进去,并将循环中的自身对象remove出去,这样就达到源对应多个目标的目的.
			ArrayList<SubnetParameter> parameters = new ArrayList<SubnetParameter>();
			parameters.addAll(list);
			parameters.remove(subnet);

			for (SubnetParameter subnetParameter : parameters) {
				sb.append("set dstintf ").append("\"").append("port").append(subnetParameter.getSegment()).append("\"")
						.append(ENTER_SIGN);
				sb.append("set dstaddr ").append("\"").append(subnetParameter.getSegment()).append("\"")
						.append(ENTER_SIGN);
			}
			sb.append("set schedule ").append("\"").append("always").append("\"").append(ENTER_SIGN);
			sb.append("set service ").append("\"").append("ALL").append("\"").append(ENTER_SIGN);
			sb.append("next");

		}

		System.out.println(sb.toString());

		// TelnetUtil.execCommand(routerParameter.getIpaddress(), routerParameter.getUserName(),
		// routerParameter.getPassword(), sb.toString());
	}

}
