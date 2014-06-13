package com.sobey.switches.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.switches.PbulicProperties;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest implements PbulicProperties {

	// @Test
	public void readSwtichCFGForAclNumber() throws IOException {
		String result = FileUtils.readFileToString(new File("D:\\h3c_startup.1.cfg"), "GBK");
		String str = StringUtils.substringAfter(result, "#\r\nacl");
		String str2 = StringUtils.substringBefore("acl" + str, "#\r\nvlan");
		System.out.println(str2);
		File file = new File("D:\\aclNumber.txt");
		FileUtils.writeStringToFile(file, str2, "UTF-8");
	}

	// @Test
	public void readSwtichCFGForVlan() throws IOException {
		String result = FileUtils.readFileToString(new File("D:\\h3c_startup.1.cfg"), "GBK");
		String str = StringUtils.substringAfter(result, "#\r\ninterface Vlan-interface");
		String str2 = StringUtils.substringBefore("interface Vlan-interface" + str, "#\r\ninterface GigabitEthernet");
		File file = new File("D:\\vlan.txt");
		FileUtils.writeStringToFile(file, str2, "UTF-8");
	}

	@Test
	public void readVlan() throws IOException {
		List<String> list = FileUtils.readLines(new File("D:\\vlan.txt"), "UTF-8");
		for (String str : list) {
			if (str.contains("interface Vlan-interface")) {
				System.out.println("vlan Id:" + StringUtils.substringAfterLast(str, "interface Vlan-interface"));
			} else if (str.contains("ip address")) {
				String[] arr = StringUtils.split(StringUtils.substringAfterLast(str, "ip address "));
				System.out.println("网段:" + arr[0]);
				System.out.println("子网掩码:" + arr[1]);
			} else if (str.contains("packet-filter")) {
				String[] arr = StringUtils.split(str);
				System.out.println("关联的Acl Nubmer:" + arr[1]);
			}
		}

	}

	// @Test
	public void readACLNumber() throws IOException {

		List<String> list = FileUtils.readLines(new File("D:\\aclNumber.txt"), "UTF-8");

		// key为ACL Number的ID,value为这个ACL Number下的策略
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();

		List<String> strs = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).contains("acl number")) {

				if (i == 0) {

					strs.add(list.get(i));

					map.put(list.get(i), strs);

					continue;
				} else {

					strs = new ArrayList<String>();

					strs.add(list.get(i));

					map.put(list.get(i), strs);
				}

			} else {
				strs.add(list.get(i));
			}
		}

		for (Entry<String, List<String>> entry : map.entrySet()) {
			System.err.println(entry.getValue());
			analyzeAclNumber(entry.getValue());
			System.out.println("************");
		}
	}

	private void analyzeAclNumber(List<String> list) {

		for (String str : list) {

			if (str.contains("acl number")) {
				System.out.println("acl number:" + StringUtils.substringAfterLast(str, "acl number "));
			} else if (str.contains("description")) {
				System.out.println("description:" + StringUtils.substringAfterLast(str, "description "));
			} else if (str.contains("permit ip source")) {

				String s1 = StringUtils.substringBetween(str, "ip source ", " destination");
				String[] arr = StringUtils.split(s1);
				System.out.println("允许源IP:" + arr[0]);
				System.out.println("允许源掩码:" + arr[1]);

				String s2 = StringUtils.substringAfter(str, "destination ");
				String[] arr2 = StringUtils.split(s2);
				System.out.println("允许目标IP:" + arr2[0]);
				System.out.println("允许目标掩码:" + arr2[1]);

			} else if (str.contains("deny ip source")) {

				String s1 = StringUtils.substringBetween(str, "ip source ", " destination");
				String[] arr = StringUtils.split(s1);
				System.out.println("拒绝源IP:" + arr[0]);
				System.out.println("拒绝源掩码:" + arr[1]);

				String s2 = StringUtils.substringAfter(str, "destination ");
				String[] arr2 = StringUtils.split(s2);
				System.out.println("拒绝目标IP:" + arr2[0]);
				System.out.println("拒绝目标掩码:" + arr2[1]);
			}
		}
	}

}
