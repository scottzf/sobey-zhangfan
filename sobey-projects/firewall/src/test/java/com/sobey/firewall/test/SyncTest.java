package com.sobey.firewall.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.firewall.PbulicProperties;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest implements PbulicProperties {

	@Test
	public void readVIP() throws IOException {
		String result = FileUtils.readFileToString(new File("D:\\cmop_test"), "UTF-8");
		String str = StringUtils.substringBetween(result, "config firewall vip", "config firewall vipgrp");
		String str2 = StringUtils.substringBefore(str, "end");
		File file = new File("D:\\VIP.txt");
		FileUtils.writeStringToFile(file, str2, "UTF-8");
	}

	@Test
	public void readVIPPolicy() throws IOException {

		List<String> list = FileUtils.readLines(new File("D:\\VIP.txt"), "UTF-8");

		for (String str : list) {
			if (str.contains("edit")) {
				System.out.println("协议:" + StringUtils.substringBetween(str, "-", "-"));
			} else if (str.contains("set extip")) {
				String[] arr = StringUtils.split(str);
				System.out.println("公网IP:" + arr[2]);
			} else if (str.contains("set mappedip")) {
				String[] arr = StringUtils.split(str);
				System.out.println("映射IP:" + arr[2]);
			} else if (str.contains("set extport")) {
				String[] arr = StringUtils.split(str);
				System.out.println("输出端口:" + arr[2]);
			} else if (str.contains("set mappedport")) {
				String[] arr = StringUtils.split(str);
				System.out.println("映射端口端口:" + arr[2]);
			} else {

			}
		}

	}

}
