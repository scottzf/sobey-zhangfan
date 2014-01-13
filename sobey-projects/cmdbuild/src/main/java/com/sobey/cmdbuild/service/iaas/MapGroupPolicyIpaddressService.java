package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.MapGroupPolicyIpaddress;
import com.sobey.cmdbuild.repository.MapGroupPolicyIpaddressDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * MapGroupPolicyIpaddress的service类.
 */
@Service
@Transactional
public class MapGroupPolicyIpaddressService extends BasicSevcie {

	@Autowired
	private MapGroupPolicyIpaddressDao mapGroupPolicyIpaddressDao;

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return MapGroupPolicyIpaddress
	 */
	public MapGroupPolicyIpaddress findMapGroupPolicyIpaddress(Integer id) {
		return mapGroupPolicyIpaddressDao.findOne(id);
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
	 * @return MapGroupPolicyIpaddress
	 */
	public MapGroupPolicyIpaddress findMapGroupPolicyIpaddress(Map<String, Object> searchParams) {
		return mapGroupPolicyIpaddressDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param MapGroupPolicyIpaddress
	 * @return MapGroupPolicyIpaddress
	 */
	public MapGroupPolicyIpaddress saveOrUpdate(MapGroupPolicyIpaddress mapGroupPolicyIpaddress) {
		return mapGroupPolicyIpaddressDao.save(mapGroupPolicyIpaddress);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteMapGroupPolicyIpaddress(Integer id) {
		mapGroupPolicyIpaddressDao.delete(id);
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
	 * @return List<MapGroupPolicyIpaddress>
	 */
	public List<MapGroupPolicyIpaddress> getMapGroupPolicyIpaddressList(Map<String, Object> searchParams) {
		return mapGroupPolicyIpaddressDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<MapGroupPolicyIpaddress>
	 */
	private Specification<MapGroupPolicyIpaddress> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), MapGroupPolicyIpaddress.class);
	}

}