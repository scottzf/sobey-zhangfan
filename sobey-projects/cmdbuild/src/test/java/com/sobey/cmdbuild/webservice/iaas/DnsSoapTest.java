package com.sobey.cmdbuild.webservice.iaas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class DnsSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	// @Test
	public void testAll() {
		testCreateDns();
		testFindDns();
		testGetDnsList();
		testGetDnsPagination();
		testUpdateDns();
		testDeleteDns();

	}

	// @Test
	// @Ignore
	public void testFindDns() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<DnsDTO> responseParams = cmdbuildSoapService.findDnsByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<DnsDTO> response = cmdbuildSoapService.findDns(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetDnsList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<DnsDTO> result = cmdbuildSoapService.getDnsList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	/**
	 * 
	 */
	@Test
	// @Ignore
	public void testCreateDns() {

		Dns dns = TestData.randomDns();

		DnsDTO dnsDTO = BeanMapper.map(dns, DnsDTO.class);

		IdResult response = cmdbuildSoapService.createDns(dnsDTO);

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", "code8590");
		searchParams.setParamsMap(paramsMap);

		// DTOResult<DnsDTO> dtoResult = cmdbuildSoapService.findDnsByParams(searchParams);
		//
		// // System.err.println("code2:"+dnsDTO.getCode());
		// int eipId = 117154;
		// int dnsId = dtoResult.getDto().getId();
		// System.out.println("*********************");
		// System.out.println(dnsId);
		// System.out.println("*********************");
		//
		// cmdbuildSoapService.createMapEipDns(eipId, dnsId);

		assertNotNull(response.getId());

		code = dns.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateDns() {

		DTOResult<DnsDTO> response = cmdbuildSoapService.findDns(id);

		DnsDTO dnsDTO = response.getDto();

		dnsDTO.setCode(RandomData.randomName("code"));

		dnsDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateDns(id, dnsDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteDns() {

		IdResult response = cmdbuildSoapService.deleteDns(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetDnsPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<DnsDTO> result = cmdbuildSoapService.getDnsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}