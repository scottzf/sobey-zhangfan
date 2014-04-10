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
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
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
public class LoadBalancerSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateLoadBalancer();
		testFindLoadBalancer();
		testGetLoadBalancerList();
		testGetLoadBalancerPagination();
		testUpdateLoadBalancer();
		testDeleteLoadBalancer();

	}

	// @Test
	// @Ignore
	public void testFindLoadBalancer() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_code", code);
		searchParams.setParamsMap(paramsMap);

		DTOResult<LoadBalancerDTO> responseParams = cmdbuildSoapService.findLoadBalancerByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<LoadBalancerDTO> response = cmdbuildSoapService.findLoadBalancer(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<LoadBalancerDTO> result = cmdbuildSoapService.getLoadBalancerList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateLoadBalancer() {

		LoadBalancer loadBalancer = TestData.randomLoadBalancer();

		LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

		IdResult response = cmdbuildSoapService.createLoadBalancer(loadBalancerDTO);

		assertNotNull(response.getId());

		code = loadBalancer.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateLoadBalancer() {

		DTOResult<LoadBalancerDTO> response = cmdbuildSoapService.findLoadBalancer(id);

		LoadBalancerDTO loadBalancerDTO = response.getDto();

		loadBalancerDTO.setCode(RandomData.randomName("code"));

		loadBalancerDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateLoadBalancer(id, loadBalancerDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteLoadBalancer() {

		IdResult response = cmdbuildSoapService.deleteLoadBalancer(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerPagination() {

		SearchParams searchParams = new SearchParams();
		PaginationResult<LoadBalancerDTO> result = cmdbuildSoapService.getLoadBalancerPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}