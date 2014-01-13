package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.MapEcsAs2;
import com.sobey.cmdbuild.repository.MapEcsAs2Dao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsAs2的service类.
 */
@Service
@Transactional
public class MapEcsAs2Service extends BasicSevcie {

	@Autowired
	private MapEcsAs2Dao mapEcsAs2Dao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsAs2
	 */
	public MapEcsAs2 findMapEcsAs2(Integer id) {
		return mapEcsAs2Dao.findOne(id);
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
	 * @return MapEcsAs2
	 */
	public MapEcsAs2 findMapEcsAs2(Map<String, Object> searchParams) {
		return mapEcsAs2Dao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapEcsAs2
	 * @return MapEcsAs2
	 */
	public MapEcsAs2 saveOrUpdate(MapEcsAs2 mapEcsAs2) {
		return mapEcsAs2Dao.save(mapEcsAs2);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsAs2(Integer id) {
		mapEcsAs2Dao.delete(id);
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
	 * @return List<MapEcsAs2>
	 */
	public List<MapEcsAs2> getMapEcsAs2List(Map<String, Object> searchParams) {
		return mapEcsAs2Dao.findAll(buildSpecification(searchParams));
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsAs2>
	 */
	private Specification<MapEcsAs2> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsAs2.class);
	}

}