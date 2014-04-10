package com.sobey.cmdbuild.webservice.organisation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

/**
 * Rack SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class RackSoapTest extends BaseFunctionalTestCase {

	@Test
	// @Ignore
	public void find() {
		Integer id = 218;
		DTOResult<RackDTO> response = cmdbuildSoapService.findRack(id);
		assertNotNull(response.getDto().getBrandText());
		assertNotNull(response.getDto().getHeightText());
		assertNotNull(response.getDto().getPowerText());
		assertNotNull(response.getDto().getIdcDTO());
		assertEquals("code2027", response.getDto().getCode());
	}

	@Test
	@Ignore
	public void getList() {
		SearchParams searchParams = new SearchParams();
		DTOListResult<RackDTO> result = cmdbuildSoapService.getRackList(searchParams);
		assertEquals("0", result.getCode());
	}

	@Test
	@Ignore
	public void save() {
		Rack rack = TestData.randomRack();
		RackDTO rackDTO = BeanMapper.map(rack, RackDTO.class);
		IdResult response = cmdbuildSoapService.createRack(rackDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void update() {
		Integer id = 115;
		DTOResult<RackDTO> response = cmdbuildSoapService.findRack(id);
		RackDTO rackDTO = response.getDto();
		rackDTO.setDescription("冬天来了啊");
		IdResult result = cmdbuildSoapService.updateRack(id, rackDTO);
		assertNotNull(result.getId());
	}

	@Test
	@Ignore
	public void delete() {
		Integer id = 115;
		IdResult response = cmdbuildSoapService.deleteTag(id);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void getPagination() {

		SearchParams searchParams = new SearchParams();

		PaginationResult<RackDTO> result = cmdbuildSoapService.getRackPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}
}
