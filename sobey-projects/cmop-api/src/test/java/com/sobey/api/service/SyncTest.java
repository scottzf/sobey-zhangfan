package com.sobey.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.EipDTO;

@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTest extends TestCase {

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	@Test
	public void aaa() throws IOException {

		// CMDBuild

		List<String> list = new ArrayList<String>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Object> eipDTOs = cmdbuildSoapService.getEipList(CMDBuildUtil.wrapperSearchParams(map)).getDtoList()
				.getDto();
		for (Object object : eipDTOs) {
			EipDTO eipDTO = (EipDTO) object;
			System.out.println(eipDTO.getDescription());
			list.add(eipDTO.getDescription());
		}

		List<String> list2 = new ArrayList<String>();

		for (String str : FileUtils.readLines(new File("D:\\VIP.txt"), "UTF-8")) {
			if (str.contains("edit")) {
				System.out.println("协议:" + StringUtils.substringBetween(str, "-", "-"));
			} else if (str.contains("set extip")) {
				String[] arr = StringUtils.split(str);
				System.out.println("公网IP:" + arr[2]);

				list2.add(arr[2]);

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

		Collections.sort(list);
		Collections.sort(list2);

		// 判断两个List是否相同
		if (!list.equals(list2)) {

		}

	}

}
