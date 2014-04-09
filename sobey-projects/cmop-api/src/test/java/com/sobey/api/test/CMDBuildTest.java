package com.sobey.api.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.api.data.CMDBuildTestData;
import com.sobey.api.service.CmdbuildService;
import com.sobey.generate.cmdbuild.CompanyDTO;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap.Entry;
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

	// @Test
	public void createCompanyTest() {

		CompanyDTO companyDTO = CMDBuildTestData.randomCompany();

		WSResult wsResult = service.createcompany(companyDTO);
		System.out.println(wsResult.getCode());
		System.out.println(wsResult.getMessage());

	}

	// @Test
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

	// @Test
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
