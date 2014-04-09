package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
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
public class FirewallPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateFirewallPort();
		testFindFirewallPort();
		testGetFirewallPortList();
		testGetFirewallPortPagination();
		testUpdateFirewallPort();
		testDeleteFirewallPort();

	}

	// @Test
	// @Ignore
	public void testFindFirewallPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		SearchParams searchParams = new SearchParams();
		searchParams.getParamsMap().put("EQ_code", code);

		DTOResult<FirewallPortDTO> responseParams = cmdbuildSoapService.findFirewallPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<FirewallPortDTO> response = cmdbuildSoapService.findFirewallPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetFirewallPortList() {

		SearchParams searchParams = new SearchParams();

		DTOListResult<FirewallPortDTO> result = cmdbuildSoapService.getFirewallPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateFirewallPort() {

		FirewallPort firewallPort = TestData.randomFirewallPort();

		FirewallPortDTO firewallPortDTO = BeanMapper.map(firewallPort, FirewallPortDTO.class);

		IdResult response = cmdbuildSoapService.createFirewallPort(firewallPortDTO);

		assertNotNull(response.getId());

		code = firewallPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateFirewallPort() {

		DTOResult<FirewallPortDTO> response = cmdbuildSoapService.findFirewallPort(id);

		FirewallPortDTO firewallPortDTO = response.getDto();

		firewallPortDTO.setCode(RandomData.randomName("code"));

		firewallPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateFirewallPort(id, firewallPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteFirewallPort() {

		IdResult response = cmdbuildSoapService.deleteFirewallPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetFirewallPortPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<FirewallPortDTO> result = cmdbuildSoapService.getFirewallPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}