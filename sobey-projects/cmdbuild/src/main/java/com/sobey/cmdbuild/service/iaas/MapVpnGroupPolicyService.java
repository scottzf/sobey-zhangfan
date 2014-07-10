package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.repository.MapVpnGroupPolicyDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapVpnGroupPolicy的service类.
 */
@Service
@Transactional
public class MapVpnGroupPolicyService extends BasicSevcie {

	@Autowired
	private MapVpnGroupPolicyDao mapVpnGroupPolicyDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapVpnGroupPolicy
	 */
	public MapVpnGroupPolicy findMapVpnGroupPolicy(Integer id) {
		return mapVpnGroupPolicyDao.findOne(id);
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
	 * @return MapVpnGroupPolicy
	 */
	public MapVpnGroupPolicy findMapVpnGroupPolicy(Map<String, Object> searchParams) {
		return mapVpnGroupPolicyDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapVpnGroupPolicy
	 * @return MapVpnGroupPolicy
	 */
	public MapVpnGroupPolicy saveOrUpdate(MapVpnGroupPolicy mapVpnGroupPolicy) {
		return mapVpnGroupPolicyDao.save(mapVpnGroupPolicy);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapVpnGroupPolicy(Integer id) {
		mapVpnGroupPolicyDao.delete(id);
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
	 *            动态查询条件Map * @return List<MapVpnGroupPolicy>
	 */
	public List<MapVpnGroupPolicy> getMapVpnGroupPolicyList(Map<String, Object> searchParams) {
		return mapVpnGroupPolicyDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapVpnGroupPolicy>
	 */
	private Specification<MapVpnGroupPolicy> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapVpnGroupPolicy.class);
	}

}