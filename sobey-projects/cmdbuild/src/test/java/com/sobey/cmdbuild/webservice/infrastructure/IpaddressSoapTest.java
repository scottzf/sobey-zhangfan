package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.TableNameUtil;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class IpaddressSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateIpaddress();
		testFindIpaddress();
		testGetIpaddressList();
		testGetIpaddressPagination();
		testUpdateIpaddress();
		testDeleteIpaddress();

	}

	// @Test
	// @Ignore
	public void testFindIpaddress() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<IpaddressDTO> responseParams = infrastructureService.findIpaddressByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<IpaddressDTO> response = infrastructureService.findIpaddress(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetIpaddressList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<IpaddressDTO> result = infrastructureService.getIpaddressList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateIpaddress() {

		Ipaddress ipaddress = TestData.randomIpaddress();

		IpaddressDTO ipaddressDTO = BeanMapper.map(ipaddress, IpaddressDTO.class);

		IdResult response = infrastructureService.createIpaddress(ipaddressDTO);

		assertNotNull(response.getId());

		code = ipaddress.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateIpaddress() {

		DTOResult<IpaddressDTO> response = infrastructureService.findIpaddress(id);

		IpaddressDTO ipaddressDTO = response.getDto();

		ipaddressDTO.setCode(RandomData.randomName("code"));

		ipaddressDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateIpaddress(id, ipaddressDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteIpaddress() {

		IdResult response = infrastructureService.deleteIpaddress(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetIpaddressPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<IpaddressDTO> result = infrastructureService.getIpaddressPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}