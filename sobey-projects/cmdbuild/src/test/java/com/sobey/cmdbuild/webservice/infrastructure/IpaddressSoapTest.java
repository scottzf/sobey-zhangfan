package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
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
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class IpaddressSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	@Ignore
	public void save() {
		Ipaddress ipaddress = TestData.randomIpaddress();
		IpaddressDTO ipaddressDTO = BeanMapper.map(ipaddress, IpaddressDTO.class);
		IdResult response = infrastructureService.createIpaddress(ipaddressDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void update() {

		Integer id = 469;

		DTOResult<IpaddressDTO> response = infrastructureService.findIpaddress(id);
		IpaddressDTO dto = response.getDto();
		dto.setDescription("我是超人啊~!");
		IdResult result = infrastructureService.updateIpaddress(id, dto);
		assertNotNull(result.getId());
	}

	@Test
	@Ignore
	public void delete() {
		Integer id = 527;
		IdResult response = infrastructureService.deleteIpaddress(id);
		assertNotNull(response.getId());
	}

	// @Test
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

	@Test
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

	/**
	 * 批量添加测试，预期返回结果，如果某个添加成功的会返回对应错误
	 */
	@Test
	// @Ignore
	public void testInsertIPAddress() {

		List<IpaddressDTO> list = BeanMapper.mapList(TestData.randomIpaddressList(10), IpaddressDTO.class);

		IdResult results = infrastructureService.insertIPAddress(list);
		System.err.println(results.getMessage());
		assertEquals("0", results.getCode());

	}

	/**
	 * 批量添加包含一些code重复的数据.
	 */
	@Test
	// @Ignore
	public void testInsertIPAddress2() {

		// 已有的IP
		List<IpaddressDTO> list = infrastructureService.getIpaddressPagination(new HashMap<String, Object>(), 1, 5)
				.getGetContent();

		// 将随机数据插入IPList
		list.addAll(BeanMapper.mapList(TestData.randomIpaddressList(5), IpaddressDTO.class));

		IdResult results = infrastructureService.insertIPAddress(list);
		System.err.println(results.getMessage());
		assertEquals("0", results.getCode());

	}

	// @Test
	// @Ignore
	public void testInitIPAddress() {

		int id = 0;// Ipaddress对象的id

		IdResult results = infrastructureService.initIPAddress(id);// 设置状态为未使用

		assertEquals("0", results.getCode());

	}

	// @Test
	// @Ignore
	public void testAllocateIPAddress() {

		int id = 0;// Ipaddress对象的id

		IdResult results = infrastructureService.allocateIPAddress(id);// 设置状态为使用

		assertEquals("0", results.getCode());
	}

}