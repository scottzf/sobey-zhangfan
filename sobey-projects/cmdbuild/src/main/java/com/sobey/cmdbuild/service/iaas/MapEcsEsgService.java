package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.MapEcsEsg;
import com.sobey.cmdbuild.repository.MapEcsEsgDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapEcsEsg的service类.
 */
@Service
@Transactional
public class MapEcsEsgService extends BasicSevcie {

	@Autowired
	private MapEcsEsgDao mapEcsEsgDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapEcsEsg
	 */
	public MapEcsEsg findMapEcsEsg(Integer id) {
		return mapEcsEsgDao.findOne(id);
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
	 * @return MapEcsEsg
	 */
	public MapEcsEsg findMapEcsEsg(Map<String, Object> searchParams) {
		return mapEcsEsgDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param mapEcsEsg
	 * @return MapEcsEsg
	 */
	public MapEcsEsg saveOrUpdate(MapEcsEsg mapEcsEsg) {
		return mapEcsEsgDao.save(mapEcsEsg);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapEcsEsg(Integer id) {
		mapEcsEsgDao.delete(id);
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
	 *            动态查询条件Map * @return List<MapEcsEsg>
	 */
	public List<MapEcsEsg> getMapEcsEsgList(Map<String, Object> searchParams) {
		return mapEcsEsgDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapEcsEsg>
	 */
	private Specification<MapEcsEsg> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapEcsEsg.class);
	}

}