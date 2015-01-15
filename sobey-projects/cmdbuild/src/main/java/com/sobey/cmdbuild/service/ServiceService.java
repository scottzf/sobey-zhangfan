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
import com.sobey.cmdbuild.repository.ServiceDao;
import com.sobey.cmdbuild.webservice.response.dto.ServiceDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * Service的service类.
 */
@Service
@Transactional
public class ServiceService extends BasicSevcie {

	@Autowired
	private ServiceDao serviceDao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<com.sobey.cmdbuild.entity.Service>
	 */
	private Specification<com.sobey.cmdbuild.entity.Service> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), com.sobey.cmdbuild.entity.Service.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteService(Integer id) {
		serviceDao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return Service
	 */
	public com.sobey.cmdbuild.entity.Service findService(Integer id) {
		return serviceDao.findOne(id);
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
	 * @return Service
	 */
	public com.sobey.cmdbuild.entity.Service findService(Map<String, Object> searchParams) {
		return serviceDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * ServiceDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<ServiceDTO>
	 */
	public PaginationResult<ServiceDTO> getServiceDTOPagination(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		Page<com.sobey.cmdbuild.entity.Service> page = getServicePage(searchParams, pageNumber, pageSize);

		List<ServiceDTO> dtos = BeanMapper.mapList(page.getContent(), ServiceDTO.class);

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
	 * @return List<com.sobey.cmdbuild.entity.Service>
	 */
	public List<com.sobey.cmdbuild.entity.Service> getServiceList(Map<String, Object> searchParams) {
		return serviceDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<com.sobey.cmdbuild.entity.Service>
	 */
	private Page<com.sobey.cmdbuild.entity.Service> getServicePage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<com.sobey.cmdbuild.entity.Service> spec = buildSpecification(searchParams);

		return serviceDao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param Service
	 * @return Service
	 */
	public com.sobey.cmdbuild.entity.Service saveOrUpdate(com.sobey.cmdbuild.entity.Service service) {
		return serviceDao.save(service);
	}
}
