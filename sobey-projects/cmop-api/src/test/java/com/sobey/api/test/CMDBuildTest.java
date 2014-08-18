package com.sobey.api.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.CMDBuildTestData;
import com.sobey.api.service.CmdbuildService;
import com.sobey.api.service.InstanceService;
import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap.Entry;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.cmdbuild.WSResult;

/**
 * 针对CMDBuild的测试,别忘了导入applicationContext-api.xml. 因为是单元测试,所以没走web.xml那块.因此需要手动指定配置文件.
 * 
 * @author Administrator
 * 
 */
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CMDBuildTest extends TestCase {

	@Autowired
	private CmdbuildService service;

	@Autowired
	private InstanceService instanceService;

	private static String DATACENTER = "XA";

	@Test
	public void test() {

		// 从vcenter中获得关联关系. 遍历Map

		HashMap<String, String> vcenterMap = instanceService.relationVM(DATACENTER);

		for (java.util.Map.Entry<String, String> entry : vcenterMap.entrySet()) {

			HashMap<String, Object> ecsMap = new HashMap<String, Object>();
			ecsMap.put("EQ_code", entry.getKey());

			DTOResult dtoResult = service.findEcs(CMDBuildUtil.wrapperSearchParams(ecsMap));

			EcsDTO ecsDTO = (EcsDTO) dtoResult.getDto();

			ServerDTO serverDTO = (ServerDTO) service.findServer(ecsDTO.getServer()).getDto();

			if (!entry.getValue().equals(serverDTO.getCode())) {

				System.out.println(entry.getKey());
				System.err.println("vcenter中对应的宿主机:" + entry.getValue());
				System.err.println("CMDBuild中对应的宿主机:" + serverDTO.getCode());

				HashMap<String, Object> serverMap = new HashMap<String, Object>();
				serverMap.put("EQ_code", entry.getValue());

				ServerDTO serverDTO2 = (ServerDTO) service.findServer(CMDBuildUtil.wrapperSearchParams(serverMap))
						.getDto();

				ecsDTO.setServer(serverDTO2.getId());
				service.updateEcs(ecsDTO.getId(), ecsDTO);

			}

		}

	}

	@Test
	public void createEcs() {

		EcsDTO dto = CMDBuildTestData.randomEcsDTO();

		WSResult wsResult = service.createEcsDTO(dto);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void deleteEcs() {

		Integer id = 832;

		WSResult wsResult = service.deleteEcs(id);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void updateEcs() {

		Integer id = 832;

		DTOResult dtoResult = service.findEcs(id);
		EcsDTO dto = (EcsDTO) dtoResult.getDto();
		dto.setCode("coder");
		dto.setDescription("我是超人啊~!");

		WSResult wsResult = service.updateEcs(id, dto);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void findEcs() {

		List<Entry> entries = new ArrayList<Entry>();

		SearchParams searchParams = new SearchParams();

		Entry entry = new Entry();
		entry.setKey("EQ_code");
		entry.setValue("coder");

		entries.add(entry);

		ParamsMap paramsMap = new ParamsMap();

		paramsMap.getEntry().addAll(entries);

		searchParams.setParamsMap(paramsMap);

		DTOResult dtoResult = service.findEcs(searchParams);

		EcsDTO dto = (EcsDTO) dtoResult.getDto();
		System.out.println(dto.getCode());
		System.out.println(dto.getRemark());
		System.out.println(dto.getId());
	}

}
