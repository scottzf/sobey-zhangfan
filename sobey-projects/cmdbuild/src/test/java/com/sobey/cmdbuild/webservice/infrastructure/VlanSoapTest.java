package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class VlanSoapTest extends BaseFunctionalTestCase {

	/**
	 * 全局id
	 */
	private Integer id = 0;

	/**
	 * 全局Description
	 */
	private String description = "";

	public static Date startDate = new Date(System.currentTimeMillis());

	@Test
	public void insert() {

		for (int i = 100; i < 131; i++) {

			Vlan vlan = new Vlan();

			vlan.setRemark("vlan" + i);
			vlan.setSegment("172.28." + i + ".0");
			vlan.setNetMask("255.255.255.0");
			vlan.setGateway("172.28." + i + ".254");

			vlan.setId(0);
			vlan.setDescription("vlan" + i + "_cd");
			vlan.setBeginDate(startDate);
			// vlan.setTenants(tenantsId);
			vlan.setIdc(527);
			vlan.setVlanStatus(99);

			VlanDTO dto = BeanMapper.map(vlan, VlanDTO.class);
			cmdbuildSoapService.createVlan(dto);

		}

	}

	@Test
	public void testAll() {
		save();
		find();
		getList();
		getPagination();
		update();
		delete();
	}

	public void delete() {
		IdResult response = cmdbuildSoapService.deleteVlan(id);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_description", description);
		searchParams.setParamsMap(paramsMap);

		DTOResult<VlanDTO> dtoResult = cmdbuildSoapService.findVlanByParams(searchParams);

		assertEquals(description, dtoResult.getDto().getDescription());

		id = dtoResult.getDto().getId();// 设置id
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		DTOListResult<VlanDTO> result = cmdbuildSoapService.getVlanList(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		searchParams.setParamsMap(paramsMap);

		PaginationResult<VlanDTO> result = cmdbuildSoapService.getVlanPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		Vlan vlan = TestData.randomVlan();
		VlanDTO dto = BeanMapper.map(vlan, VlanDTO.class);
		IdResult response = cmdbuildSoapService.createVlan(dto);
		assertNotNull(response.getId());

		description = dto.getDescription();// 设置Description
	}

	public void update() {

		DTOResult<VlanDTO> response = cmdbuildSoapService.findVlan(id);
		VlanDTO dto = response.getDto();
		dto.setDescription(dto.getDescription() + "Update");
		IdResult result = cmdbuildSoapService.updateVlan(id, dto);
		assertEquals("0", result.getCode());
	}

}