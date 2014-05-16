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
import com.sobey.generate.cmdbuild.CompanyDTO;
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

			HashMap<String, String> ecsMap = new HashMap<String, String>();
			ecsMap.put("EQ_code", entry.getKey());

			DTOResult dtoResult = service.findEcs(CMDBuildUtil.wrapperSearchParams(ecsMap));

			EcsDTO ecsDTO = (EcsDTO) dtoResult.getDto();

			ServerDTO serverDTO = (ServerDTO) service.findServer(ecsDTO.getServer()).getDto();

			if (!entry.getValue().equals(serverDTO.getCode())) {

				System.out.println(entry.getKey());
				System.err.println("vcenter中对应的宿主机:" + entry.getValue());
				System.err.println("CMDBuild中对应的宿主机:" + serverDTO.getCode());

				HashMap<String, String> serverMap = new HashMap<String, String>();
				serverMap.put("EQ_code", entry.getValue());

				ServerDTO serverDTO2 = (ServerDTO) service.findServer(CMDBuildUtil.wrapperSearchParams(serverMap))
						.getDto();

				ecsDTO.setServer(serverDTO2.getId());
				service.updateEcs(ecsDTO.getId(), ecsDTO);

			}

		}

	}

	@Test
	public void createCompanyTest() {

		CompanyDTO companyDTO = CMDBuildTestData.randomCompany();

		WSResult wsResult = service.createcompany(companyDTO);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void deleteCompanyTest() {

		WSResult wsResult = service.deleteCompany(117124);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void updateCompanyTest() {

		Integer id = 117125;

		DTOResult dtoResult = service.findCompany(id);
		CompanyDTO dto = (CompanyDTO) dtoResult.getDto();
		dto.setCode("coder");
		dto.setDescription("我是超人啊~!");

		WSResult wsResult = service.updateCompany(id, dto);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	@Test
	public void findCompanyTest() {

		List<Entry> entries = new ArrayList<Entry>();

		SearchParams searchParams = new SearchParams();

		Entry entry = new Entry();
		entry.setKey("EQ_zip");
		entry.setValue("zip4865");

		entries.add(entry);

		ParamsMap paramsMap = new ParamsMap();

		paramsMap.getEntry().addAll(entries);

		searchParams.setParamsMap(paramsMap);

		DTOResult dtoResult = service.findCompany(searchParams);
		CompanyDTO dto = (CompanyDTO) dtoResult.getDto();
		System.out.println(dto.getAddress());
		System.out.println(dto.getCode());
		System.out.println(dto.getRemark());
		System.out.println(dto.getId());
	}

}
