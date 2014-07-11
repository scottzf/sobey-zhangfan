package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.repository.MapEcsEs3Dao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsCs2的service类.
 */
@Service
@Transactional
public class MapEcsCs2Service extends BasicSevcie {

	@Autowired
	private MapEcsEs3Dao mapEcsCs2Dao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsCs2
	 */
	public MapEcsEs3 findMapEcsCs2(Integer id) {
		return mapEcsCs2Dao.findOne(id);
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
	 * @return MapEcsCs2
	 */
	public MapEcsEs3 findMapEcsCs2(Map<String, Object> searchParams) {
		return mapEcsCs2Dao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapEcsEs3
	 * @return MapEcsCs2
	 */
	public MapEcsEs3 saveOrUpdate(MapEcsEs3 mapEcsCs2) {
		return mapEcsCs2Dao.save(mapEcsCs2);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsCs2(Integer id) {
		mapEcsCs2Dao.delete(id);
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
	 * @return List<MapEcsCs2>
	 */
	public List<MapEcsEs3> getMapEcsCs2List(Map<String, Object> searchParams) {
		return mapEcsCs2Dao.findAll(buildSpecification(searchParams));
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsCs2>
	 */
	private Specification<MapEcsEs3> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsEs3.class);
	}

}