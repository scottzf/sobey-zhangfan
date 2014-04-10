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
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
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
public class LoadBalancerPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateLoadBalancerPort();
		testFindLoadBalancerPort();
		testGetLoadBalancerPortList();
		testGetLoadBalancerPortPagination();
		testUpdateLoadBalancerPort();
		testDeleteLoadBalancerPort();

	}

	// @Test
	// @Ignore
	public void testFindLoadBalancerPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<LoadBalancerPortDTO> responseParams = cmdbuildSoapService.findLoadBalancerPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<LoadBalancerPortDTO> response = cmdbuildSoapService.findLoadBalancerPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerPortList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<LoadBalancerPortDTO> result = cmdbuildSoapService.getLoadBalancerPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateLoadBalancerPort() {

		LoadBalancerPort loadBalancerPort = TestData.randomLoadBalancerPort();

		LoadBalancerPortDTO loadBalancerPortDTO = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

		IdResult response = cmdbuildSoapService.createLoadBalancerPort(loadBalancerPortDTO);

		assertNotNull(response.getId());

		code = loadBalancerPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateLoadBalancerPort() {

		DTOResult<LoadBalancerPortDTO> response = cmdbuildSoapService.findLoadBalancerPort(id);

		LoadBalancerPortDTO loadBalancerPortDTO = response.getDto();

		loadBalancerPortDTO.setCode(RandomData.randomName("code"));

		loadBalancerPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateLoadBalancerPort(id, loadBalancerPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteLoadBalancerPort() {

		IdResult response = cmdbuildSoapService.deleteLoadBalancerPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerPortPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<LoadBalancerPortDTO> result = cmdbuildSoapService.getLoadBalancerPortPagination(searchParams,
				1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}