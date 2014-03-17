package com.sobey.cmdbuild.webservice.iaas;

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
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class VpnSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateVpn();
		testFindVpn();
		testGetVpnList();
		testGetVpnPagination();
		testUpdateVpn();
		testDeleteVpn();

	}

	// @Test
	// @Ignore
	public void testFindVpn() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<VpnDTO> responseParams = cmdbuildSoapService.findVpnByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<VpnDTO> response = cmdbuildSoapService.findVpn(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetVpnList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<VpnDTO> result = cmdbuildSoapService.getVpnList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateVpn() {

		Vpn vpn = TestData.randomVpn();

		VpnDTO vpnDTO = BeanMapper.map(vpn, VpnDTO.class);

		IdResult response = cmdbuildSoapService.createVpn(vpnDTO);

		assertNotNull(response.getId());

		code = vpn.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateVpn() {

		DTOResult<VpnDTO> response = cmdbuildSoapService.findVpn(id);

		VpnDTO vpnDTO = response.getDto();

		vpnDTO.setCode(RandomData.randomName("code"));

		vpnDTO.setDescription(RandomData.randomName("update"));

		IdResult result = cmdbuildSoapService.updateVpn(id, vpnDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteVpn() {

		IdResult response = cmdbuildSoapService.deleteVpn(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetVpnPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<VpnDTO> result = cmdbuildSoapService.getVpnPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}