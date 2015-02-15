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
import com.sobey.cmdbuild.entity.MapEcsElb;
import com.sobey.cmdbuild.repository.MapEcsElbDao;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsElbDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsElb的service类.
 */
@Service
@Transactional
public class MapEcsElbService extends BasicSevcie {

	@Autowired
	private MapEcsElbDao mapEcsElbDao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsElb>
	 */
	private Specification<MapEcsElb> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsElb.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsElb(Integer id) {
		mapEcsElbDao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsElb
	 */
	public MapEcsElb findMapEcsElb(Integer id) {
		return mapEcsElbDao.findOne(id);
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
	 * @return MapEcsElb
	 */
	public MapEcsElb findMapEcsElb(Map<String, Object> searchParams) {
		return mapEcsElbDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * MapEcsElbDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<MapEcsElbDTO>
	 */
	public PaginationResult<MapEcsElbDTO> getMapEcsElbDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<MapEcsElb> page = getMapEcsElbPage(searchParams, pageNumber, pageSize);

		List<MapEcsElbDTO> dtos = BeanMapper.mapList(page.getContent(), MapEcsElbDTO.class);

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
	 * @return List<MapEcsElb>
	 */
	public List<MapEcsElb> getMapEcsElbList(Map<String, Object> searchParams) {
		return mapEcsElbDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<MapEcsElb>
	 */
	private Page<MapEcsElb> getMapEcsElbPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<MapEcsElb> spec = buildSpecification(searchParams);

		return mapEcsElbDao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapEcsElb
	 * @return MapEcsElb
	 */
	public MapEcsElb saveOrUpdate(MapEcsElb mapEcsElb) {
		return mapEcsElbDao.save(mapEcsElb);
	}

}