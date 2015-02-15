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
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.repository.StoragePortDao;
import com.sobey.cmdbuild.webservice.response.dto.StoragePortDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * StoragePort的service类.
 */
@Service
@Transactional
public class StoragePortService extends BasicSevcie {

	@Autowired
	private StoragePortDao storagePortDao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<StoragePort>
	 */
	private Specification<StoragePort> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), StoragePort.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteStoragePort(Integer id) {
		storagePortDao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return StoragePort
	 */
	public StoragePort findStoragePort(Integer id) {
		return storagePortDao.findOne(id);
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
	 * @return StoragePort
	 */
	public StoragePort findStoragePort(Map<String, Object> searchParams) {
		return storagePortDao.findOne(buildSpecification(searchParams));
	}

	/**
	 * StoragePortDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<StoragePortDTO>
	 */
	public PaginationResult<StoragePortDTO> getStoragePortDTOPagination(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {

		Page<StoragePort> page = getStoragePortPage(searchParams, pageNumber, pageSize);

		List<StoragePortDTO> dtos = BeanMapper.mapList(page.getContent(), StoragePortDTO.class);

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
	 * @return List<StoragePort>
	 */
	public List<StoragePort> getStoragePortList(Map<String, Object> searchParams) {
		return storagePortDao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<StoragePort>
	 */
	private Page<StoragePort> getStoragePortPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<StoragePort> spec = buildSpecification(searchParams);

		return storagePortDao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param StoragePort
	 * @return StoragePort
	 */
	public StoragePort saveOrUpdate(StoragePort StoragePort) {
		return storagePortDao.save(StoragePort);
	}
}