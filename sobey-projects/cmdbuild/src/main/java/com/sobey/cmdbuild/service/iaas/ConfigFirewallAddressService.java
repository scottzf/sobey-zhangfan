package com.sobey.cmdbuild.service.iaas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.ConfigFirewallAddress;
import com.sobey.cmdbuild.repository.ConfigFirewallAddressDao;
import com.sobey.cmdbuild.service.BasicSevcie;
import com.sobey.cmdbuild.webservice.response.dto.ConfigFirewallAddressDTO;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;

/**
 * ConfigFirewallAddress的service类.
 */
@Service
@Transactional
public class ConfigFirewallAddressService extends BasicSevcie {

	@Autowired
	private ConfigFirewallAddressDao dao;

	/**
	 * 创建动态查询条件组合.
	 * 
	 * 自定义的查询在此进行组合.默认获得状态为"A"的有效对象.
	 * 
	 * @param searchParams
	 * @return Specification<ConfigFirewallAddress>
	 */
	private Specification<ConfigFirewallAddress> buildSpecification(Map<String, Object> searchParams) {

		searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		return DynamicSpecifications.bySearchFilter(filters.values(), ConfigFirewallAddress.class);
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public void deleteConfigFirewallAddress(Integer id) {
		dao.delete(id);
	}

	/**
	 * 根据ID获得对象
	 * 
	 * @param id
	 * @return ConfigFirewallAddress
	 */
	public ConfigFirewallAddress findConfigFirewallAddress(Integer id) {
		return dao.findOne(id);
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
	 * @return ConfigFirewallAddress
	 */
	public ConfigFirewallAddress findConfigFirewallAddress(Map<String, Object> searchParams) {
		return dao.findOne(buildSpecification(searchParams));
	}

	/**
	 * ConfigFirewallAddressDTO webservice分页查询.
	 * 
	 * 将Page<T>重新组织成符合DTO格式的分页格式对象.
	 * 
	 * @param searchParams
	 *            查询语句Map.
	 * @param pageNumber
	 *            当前页数,最小为1.
	 * @param pageSize
	 *            当前页大小,如每页为10行
	 * @return PaginationResult<ConfigFirewallAddressDTO>
	 */
	public PaginationResult<ConfigFirewallAddressDTO> getConfigFirewallAddressDTOPagination(
			Map<String, Object> searchParams, int pageNumber, int pageSize) {

		Page<ConfigFirewallAddress> page = getConfigFirewallAddressPage(searchParams, pageNumber, pageSize);

		List<ConfigFirewallAddressDTO> dtos = BeanMapper.mapList(page.getContent(), ConfigFirewallAddressDTO.class);

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
	 * @return List<ConfigFirewallAddress>
	 */
	public List<ConfigFirewallAddress> getConfigFirewallAddressList(Map<String, Object> searchParams) {
		return dao.findAll(buildSpecification(searchParams));
	}

	/**
	 * Spring-data-jpa自带的分页查询
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<ConfigFirewallAddress>
	 */
	private Page<ConfigFirewallAddress> getConfigFirewallAddressPage(Map<String, Object> searchParams, int pageNumber,
			int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Specification<ConfigFirewallAddress> spec = buildSpecification(searchParams);

		return dao.findAll(spec, pageRequest);
	}

	/**
	 * 新增、保存对象
	 * 
	 * @param ConfigFirewallAddress
	 * @return ConfigFirewallAddress
	 */
	public ConfigFirewallAddress saveOrUpdate(ConfigFirewallAddress configFirewallAddress) {
		return dao.save(configFirewallAddress);
	}
}