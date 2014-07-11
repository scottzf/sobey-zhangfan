package com.sobey.cmdbuild.webservice.infrastructure;

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
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.webservice.response.dto.StoragePortDTO;
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
public class NetappPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNetappPort();
		testFindNetappPort();
		testGetNetappPortList();
		testGetNetappPortPagination();
		testUpdateNetappPort();
		testDeleteNetappPort();

	}

	// @Test
	// @Ignore
	public void testFindNetappPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<StoragePortDTO> responseParams = cmdbuildSoapService.findNetappPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<StoragePortDTO> response = cmdbuildSoapService.findNetappPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNetappPortList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<StoragePortDTO> result = cmdbuildSoapService.getNetappPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNetappPort() {

		StoragePort netappPort = TestData.randomNetappPort();

		StoragePortDTO netappPortDTO = BeanMapper.map(netappPort, StoragePortDTO.class);

		IdResult response = cmdbuildSoapService.createNetappPort(netappPortDTO);

		assertNotNull(response.getId());

		code = netappPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNetappPort() {

		DTOResult<StoragePortDTO> response = cmdbuildSoapService.findNetappPort(id);

		StoragePortDTO netappPortDTO = response.getDto();

		netappPortDTO.setCode(RandomData.randomName("code"));

		netappPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateNetappPort(id, netappPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNetappPort() {

		IdResult response = cmdbuildSoapService.deleteNetappPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNetappPortPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<StoragePortDTO> result = cmdbuildSoapService.getNetappPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}