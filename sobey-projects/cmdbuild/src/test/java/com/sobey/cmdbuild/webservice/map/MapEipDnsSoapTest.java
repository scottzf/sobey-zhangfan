package com.sobey.cmdbuild.webservice.map;

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
import com.sobey.cmdbuild.entity.MapEipDns;
import com.sobey.cmdbuild.webservice.response.dto.MapEipDnsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

/**
 * MapEipDns SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class MapEipDnsSoapTest extends BaseFunctionalTestCase {

	@Test
	public void testAll() {
		save();
		find();
		getList();
		getPagination();
		delete();
	}

	public void delete() {
		IdResult response = cmdbuildSoapService.deleteMapEipDns(TestData.eipId, TestData.dnsId);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.eipId);
		paramsMap.put("EQ_idObj2", TestData.dnsId);
		searchParams.setParamsMap(paramsMap);

		DTOResult<MapEipDnsDTO> dtoResult = cmdbuildSoapService.findMapEipDnsByParams(searchParams);

		System.out.println(dtoResult.getDto().getId());
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.eipId);
		searchParams.setParamsMap(paramsMap);

		DTOListResult<MapEipDnsDTO> result = cmdbuildSoapService.getMapEipDnsList(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.eipId);
		searchParams.setParamsMap(paramsMap);

		PaginationResult<MapEipDnsDTO> result = cmdbuildSoapService.getMapEipDnsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		MapEipDns map = TestData.randomMapEipDns();
		MapEipDnsDTO dto = BeanMapper.map(map, MapEipDnsDTO.class);
		IdResult response = cmdbuildSoapService.createMapEipDns(Integer.valueOf(dto.getIdObj1()),
				Integer.valueOf(dto.getIdObj2()));
		assertNotNull(response.getId());
	}

}
