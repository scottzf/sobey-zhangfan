package com.sobey.cmdbuild.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.repository.MapEcsEs3Dao;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEs3DTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsEs3的service类.
 */
@Service
@Transactional
public class MapEcsEs3Service extends BasicSevcie {

	@Autowired
	private MapEcsEs3Dao mapEcsEs3Dao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsEs3>
	 */
	private Specification<MapEcsEs3> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsEs3.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsEs3(Integer id) {
		mapEcsEs3Dao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsEs3
	 */
	public MapEcsEs3 findMapEcsEs3(Integer id) {
		return mapEcsEs3Dao.findOne(id);
	}

	/**
	 * 根据自定义动态查询条件获得对象.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return MapEcsEs3
	 */
	public MapEcsEs3 findMapEcsEs3(Map<String, Object> searchParams) {
		return mapEcsEs3Dao.findOne(buildSpecification(searchParams));
	}

	/**
	 * MapEcsEs3DTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<MapEcsEs3DTO>
	 */
	public PaginationResult<MapEcsEs3DTO> getMapEcsEs3DTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<MapEcsEs3> page = getMapEcsEs3Page(searchParams, pageNumber, pageSize);

		List<MapEcsEs3DTO> dtos = BeanMapper.mapList(page.getContent(), MapEcsEs3DTO.class);

		return fillPaginationResult(page, dtos);
	}

	/**
	 * 根据自定义动态查询条件获得对象集合.
	 * 
	 * 将条件查询放入searchParams中. 查询条件可查询{@link SearchFilter}类.
	 * 
	 * <pre>
	 * searchParams.put(&quot;EQ_status&quot;, 'A');
	 * </pre>
	 * 
	 * @param searchParams
	 *            动态查询条件Map
	 * @return List<MapEcsEs3>
	 */
	public List<MapEcsEs3> getMapEcsEs3List(Map<String, Object> searchParams) {
		return mapEcsEs3Dao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<MapEcsEs3>
	 */
	private Page<MapEcsEs3> getMapEcsEs3Page(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<MapEcsEs3> spec = buildSpecification(searchParams);

		return mapEcsEs3Dao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param mapEcsEs3
	 * @return MapEcsEs3
	 */
	public MapEcsEs3 saveOrUpdate(MapEcsEs3 mapEcsEs3) {
		return mapEcsEs3Dao.save(mapEcsEs3);
	}

}