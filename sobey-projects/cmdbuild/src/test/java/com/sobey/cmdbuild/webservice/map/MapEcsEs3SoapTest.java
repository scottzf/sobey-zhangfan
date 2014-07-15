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
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEs3DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.mapper.BeanMapper;

/**
 * MapEcsEs3 SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class MapEcsEs3SoapTest extends BaseFunctionalTestCase {

	@Test
	public void testAll() {
		save();
		find();
		getList();
		getPagination();
		delete();
	}

	public void delete() {
		IdResult response = cmdbuildSoapService.deleteMapEcsEs3(TestData.ecsId, TestData.es3Id);
		assertNotNull(response.getId());
	}

	public void find() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.ecsId);
		paramsMap.put("EQ_idObj2", TestData.es3Id);
		searchParams.setParamsMap(paramsMap);

		DTOResult<MapEcsEs3DTO> dtoResult = cmdbuildSoapService.findMapEcsEs3ByParams(searchParams);

		System.out.println(dtoResult.getDto().getId());
	}

	public void getList() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.ecsId);
		searchParams.setParamsMap(paramsMap);

		DTOListResult<MapEcsEs3DTO> result = cmdbuildSoapService.getMapEcsEs3List(searchParams);
		System.out.println("返回的查询结果数量:" + result.getDtos().size());
	}

	public void getPagination() {

		SearchParams searchParams = new SearchParams();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("EQ_idObj1", TestData.ecsId);
		searchParams.setParamsMap(paramsMap);

		PaginationResult<MapEcsEs3DTO> result = cmdbuildSoapService.getMapEcsEs3Pagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的分页查询结果数量:" + result.getGetTotalElements());
	}

	public void save() {

		MapEcsEs3 map = TestData.randomMapEcsEs3();
		MapEcsEs3DTO dto = BeanMapper.map(map, MapEcsEs3DTO.class);
		IdResult response = cmdbuildSoapService.createMapEcsEs3(Integer.valueOf(dto.getIdObj1()),
				Integer.valueOf(dto.getIdObj2()));
		assertNotNull(response.getId());
	}

}
