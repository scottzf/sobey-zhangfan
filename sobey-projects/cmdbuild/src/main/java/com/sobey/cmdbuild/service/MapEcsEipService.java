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
import com.sobey.cmdbuild.entity.MapEcsEip;
import com.sobey.cmdbuild.repository.MapEcsEipDao;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEipDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsEip的service类.
 */
@Service
@Transactional
public class MapEcsEipService extends BasicSevcie {

	@Autowired
	private MapEcsEipDao mapEcsEipDao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsEip>
	 */
	private Specification<MapEcsEip> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsEip.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsEip(Integer id) {
		mapEcsEipDao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsEip
	 */
	public MapEcsEip findMapEcsEip(Integer id) {
		return mapEcsEipDao.findOne(id);
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
	 * @return MapEcsEip
	 */
	public MapEcsEip findMapEcsEip(Map<String, Object> searchParams) {
		return mapEcsEipDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * MapEcsEipDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<MapEcsEipDTO>
	 */
	public PaginationResult<MapEcsEipDTO> getMapEcsEipDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<MapEcsEip> page = getMapEcsEipPage(searchParams, pageNumber, pageSize);

		List<MapEcsEipDTO> dtos = BeanMapper.mapList(page.getContent(), MapEcsEipDTO.class);

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
	 * @return List<MapEcsEip>
	 */
	public List<MapEcsEip> getMapEcsEipList(Map<String, Object> searchParams) {
		return mapEcsEipDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<MapEcsEip>
	 */
	private Page<MapEcsEip> getMapEcsEipPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<MapEcsEip> spec = buildSpecification(searchParams);

		return mapEcsEipDao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapEcsEip
	 * @return MapEcsEip
	 */
	public MapEcsEip saveOrUpdate(MapEcsEip mapEcsEip) {
		return mapEcsEipDao.save(mapEcsEip);
	}

}