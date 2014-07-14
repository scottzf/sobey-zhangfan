package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.LookUpConstants;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.Log;
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3DTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.LogDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageDTO;
import com.sobey.cmdbuild.webservice.response.dto.StoragePortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.cmdbuild.webservice.response.result.SearchParams;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.Identities;
import com.sobey.core.utils.TableNameUtil;

@WebService(serviceName = "CmdbuildSoapService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
// @Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CmdbuildSoapServiceImpl extends BasicSoapSevcie implements CmdbuildSoapService {

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<LookUpDTO> findLookUp(@WebParam(name = "id") Integer id) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(id);

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(searchParams.getParamsMap());

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<LookUpDTO> result = new DTOListResult<LookUpDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.lookUpService.getLookUpList(searchParams.getParamsMap()),
					LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LookUpDTO> getLookUpPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<LookUpDTO> result = new PaginationResult<LookUpDTO>();

		try {

			return comm.lookUpService.getLookUpDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenants(Integer id) {

		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(id);

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			TenantsDTO dto = BeanMapper.map(tenants, TenantsDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenantsByParams(SearchParams searchParams) {

		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(searchParams.getParamsMap());

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			TenantsDTO dto = BeanMapper.map(tenants, TenantsDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}

	}

	@Override
	public IdResult createTenants(TenantsDTO tenantsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", tenantsDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsService.findTenants(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象s
			Tenants tenants = BeanMapper.map(tenantsDTO, Tenants.class);
			tenants.setCode("Tenants-" + Identities.randomBase62(8));
			tenants.setUser(DEFAULT_USER);
			tenants.setId(0);

			BeanValidators.validateWithException(validator, tenants);

			comm.tenantsService.saveOrUpdate(tenants);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTenants(Integer id, TenantsDTO tenantsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(id);

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", tenantsDTO.getDescription());

			Validate.isTrue(
					comm.tenantsService.findTenants(paramsMap) == null
							|| tenants.getDescription().equals(tenantsDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(tenantsDTO, Tenants.class), tenants);

			tenants.setUser(DEFAULT_USER);
			tenants.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			tenants.setIdClass(TableNameUtil.getTableName(Tenants.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, tenants);

			comm.tenantsService.saveOrUpdate(tenants);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTenants(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(id);

			Validate.notNull(tenants, ERROR.INPUT_NULL);

			tenants.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.tenantsService.saveOrUpdate(tenants);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TenantsDTO> getTenantsList(SearchParams searchParams) {

		DTOListResult<TenantsDTO> result = new DTOListResult<TenantsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.tenantsService.getTenantsList(searchParams.getParamsMap()),
					TenantsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TenantsDTO> getTenantsPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<TenantsDTO> result = new PaginationResult<TenantsDTO>();

		try {

			return comm.tenantsService.getTenantsDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTag(Integer id) {

		DTOResult<TagDTO> result = new DTOResult<TagDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(id);

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			TagDTO dto = BeanMapper.map(tag, TagDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			if (dto.getParentTag() != null) {
				dto.setParentTagDTO(findTag(dto.getParentTag()).getDto());
			}

			// 查询出Lookup中的description,并将其设置到DTO中增加的String字段中.
			dto.setTagTypeText(findLookUp(dto.getTagType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTagByParams(SearchParams searchParams) {

		DTOResult<TagDTO> result = new DTOResult<TagDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(searchParams.getParamsMap());

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			TagDTO dto = BeanMapper.map(tag, TagDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			if (dto.getParentTag() != null) {
				dto.setParentTagDTO(findTag(dto.getParentTag()).getDto());
			}

			// 查询出Lookup中的description,并将其设置到DTO中增加的String字段中.
			dto.setTagTypeText(findLookUp(dto.getTagType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createTag(TagDTO tagDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tagDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的description,如果有相同的description名称，则不能创建.
			// 如果类型为子标签,则相同上级标签下不能有相同的子标签名称
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_tenants", tagDTO.getTenants());
			paramsMap.put("EQ_description", tagDTO.getDescription());

			if (LookUpConstants.TagType.子标签.getValue().equals(tagDTO.getTagType())) {
				paramsMap.put("EQ_parentTag", tagDTO.getParentTag());
			}

			Validate.isTrue(comm.tagService.findTag(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Tag tag = BeanMapper.map(tagDTO, Tag.class);
			tag.setCode("Tag-" + Identities.randomBase62(8));
			tag.setUser(DEFAULT_USER);
			tag.setId(0);

			BeanValidators.validateWithException(validator, tag);

			comm.tagService.saveOrUpdate(tag);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTag(Integer id, TagDTO tagDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tagDTO, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(id);

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的description,如果有相同的description名称，则不能创建.
			// 如果类型为子标签,则相同上级标签下不能有相同的子标签名称
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_tenants", tagDTO.getTenants());
			paramsMap.put("EQ_description", tagDTO.getDescription());

			if (LookUpConstants.TagType.子标签.getValue().equals(tagDTO.getTagType())) {
				paramsMap.put("EQ_parentTag", tagDTO.getParentTag());
			}

			Validate.isTrue(
					comm.tagService.findTag(paramsMap) == null || tag.getDescription().equals(tagDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(tagDTO, Tag.class), tag);

			tag.setUser(DEFAULT_USER);
			tag.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			tag.setIdClass(TableNameUtil.getTableName(Tag.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, tag);

			comm.tagService.saveOrUpdate(tag);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTag(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(id);

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			tag.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.tagService.saveOrUpdate(tag);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TagDTO> getTagList(SearchParams searchParams) {

		DTOListResult<TagDTO> result = new DTOListResult<TagDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.tagService.getTagList(searchParams.getParamsMap()), TagDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TagDTO> getTagPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<TagDTO> result = new PaginationResult<TagDTO>();

		try {

			return comm.tagService.getTagDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IdcDTO> findIdc(Integer id) {

		DTOResult<IdcDTO> result = new DTOResult<IdcDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Idc idc = comm.idcService.findIdc(id);

			Validate.notNull(idc, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(idc, IdcDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IdcDTO> findIdcByParams(SearchParams searchParams) {

		DTOResult<IdcDTO> result = new DTOResult<IdcDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Idc idc = comm.idcService.findIdc(searchParams.getParamsMap());

			Validate.notNull(idc, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(idc, IdcDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createIdc(IdcDTO idcDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(idcDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", idcDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcService.findIdc(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Idc idc = BeanMapper.map(idcDTO, Idc.class);
			idc.setCode("IDC-" + Identities.randomBase62(8));
			idc.setUser(DEFAULT_USER);
			idc.setId(0);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, idc);

			comm.idcService.saveOrUpdate(idc);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIdc(Integer id, IdcDTO idcDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(idcDTO, ERROR.INPUT_NULL);

			Idc idc = comm.idcService.findIdc(id);

			Validate.notNull(idc, ERROR.OBJECT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", idcDTO.getDescription());

			Validate.isTrue(
					comm.idcService.findIdc(paramsMap) == null || idc.getDescription().equals(idcDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(idcDTO, Idc.class), idc);

			idc.setUser(DEFAULT_USER);
			idc.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			idc.setIdClass(TableNameUtil.getTableName(Idc.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, idc);

			comm.idcService.saveOrUpdate(idc);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult deleteIdc(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Idc idc = comm.idcService.findIdc(id);

			Validate.notNull(idc, ERROR.OBJECT_NULL);

			idc.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.idcService.saveOrUpdate(idc);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IdcDTO> getIdcList(SearchParams searchParams) {

		DTOListResult<IdcDTO> result = new DTOListResult<IdcDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.idcService.getIdcList(searchParams.getParamsMap()), IdcDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IdcDTO> getIdcPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<IdcDTO> result = new PaginationResult<IdcDTO>();

		try {

			return comm.idcService.getIdcDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<RackDTO> findRack(Integer id) {

		DTOResult<RackDTO> result = new DTOResult<RackDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Rack rack = comm.rackService.findRack(id);

			Validate.notNull(rack, ERROR.OBJECT_NULL);

			RackDTO dto = BeanMapper.map(rack, RackDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// 查询出Lookup中的description,并将其设置到DTO中增加的String字段中.
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHeightText(findLookUp(dto.getHeight()).getDto().getDescription());
			dto.setPowerText(findLookUp(dto.getPower()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RackDTO> findRackByParams(SearchParams searchParams) {

		DTOResult<RackDTO> result = new DTOResult<RackDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Rack rack = comm.rackService.findRack(searchParams.getParamsMap());

			Validate.notNull(rack, ERROR.OBJECT_NULL);

			RackDTO dto = BeanMapper.map(rack, RackDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// 查询出Lookup中的description,并将其设置到DTO中增加的String字段中.
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHeightText(findLookUp(dto.getHeight()).getDto().getDescription());
			dto.setPowerText(findLookUp(dto.getPower()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createRack(RackDTO rackDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(rackDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误,同一IDC下Rack名字不能相同
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", rackDTO.getDescription());
			paramsMap.put("EQ_idc", rackDTO.getIdc());

			Validate.isTrue(comm.rackService.findRack(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Rack rack = BeanMapper.map(rackDTO, Rack.class);
			rack.setCode("Rack-" + Identities.randomBase62(8));
			rack.setUser(DEFAULT_USER);
			rack.setId(0);

			BeanValidators.validateWithException(validator, rack);

			comm.rackService.saveOrUpdate(rack);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateRack(Integer id, RackDTO rackDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(rackDTO, ERROR.INPUT_NULL);

			Rack rack = comm.rackService.findRack(id);

			Validate.notNull(rack, ERROR.OBJECT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误,同一IDC下Rack名字不能相同
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", rackDTO.getDescription());
			paramsMap.put("EQ_idc", rackDTO.getIdc());

			Validate.isTrue(
					comm.rackService.findRack(paramsMap) == null
							|| rack.getDescription().equals(rackDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(rackDTO, Rack.class), rack);
			rack.setUser(DEFAULT_USER);
			rack.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			rack.setIdClass(TableNameUtil.getTableName(Rack.class));

			BeanValidators.validateWithException(validator, rack);

			comm.rackService.saveOrUpdate(rack);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteRack(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Rack rack = comm.rackService.findRack(id);

			Validate.notNull(rack, ERROR.OBJECT_NULL);

			rack.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.rackService.saveOrUpdate(rack);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<RackDTO> getRackList(SearchParams searchParams) {

		DTOListResult<RackDTO> result = new DTOListResult<RackDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.rackService.getRackList(searchParams.getParamsMap()), RackDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<RackDTO> getRackPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<RackDTO> result = new PaginationResult<RackDTO>();

		try {

			return comm.rackService.getRackDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(Integer id) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(findLookUp(dto.getHeight()).getDto().getDescription());
			dto.setMaintenanceText(findLookUp(dto.getMaintenance()).getDto().getDescription());
			dto.setPowerText(findLookUp(dto.getPower()).getDto().getDescription());
			dto.setDeviceTypeText(findLookUp(dto.getDeviceType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(SearchParams searchParams) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(searchParams.getParamsMap());

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(findLookUp(dto.getHeight()).getDto().getDescription());
			dto.setMaintenanceText(findLookUp(dto.getMaintenance()).getDto().getDescription());
			dto.setPowerText(findLookUp(dto.getPower()).getDto().getDescription());
			dto.setDeviceTypeText(findLookUp(dto.getDeviceType()).getDto().getDescription());

			result.setDto(dto);
			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDeviceSpec(DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", deviceSpecDTO.getDescription());
			paramsMap.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的description，如果有相同的description则不能创建.
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			deviceSpec.setCode("DeviceSpec-" + Identities.randomBase62(8));
			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setId(0);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDeviceSpec(Integer id, DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", deviceSpecDTO.getDescription());
			paramsMap.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的description，如果有相同的description则不能创建.
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(paramsMap) == null
					|| deviceSpec.getDescription().equals(deviceSpecDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(deviceSpecDTO, DeviceSpec.class), deviceSpec);

			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			deviceSpec.setIdClass(TableNameUtil.getTableName(DeviceSpec.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDeviceSpec(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			deviceSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(SearchParams searchParams) {

		DTOListResult<DeviceSpecDTO> result = new DTOListResult<DeviceSpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.deviceSpecService.getDeviceSpecList(searchParams.getParamsMap()),
					DeviceSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<DeviceSpecDTO> result = new PaginationResult<DeviceSpecDTO>();

		try {

			return comm.deviceSpecService.getDeviceSpecDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpec(Integer id) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			EcsSpecDTO dto = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

			// LookUp
			dto.setOsTypeText(findLookUp(dto.getOsType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(SearchParams searchParams) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(searchParams.getParamsMap());

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			EcsSpecDTO dto = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

			// LookUp
			dto.setOsTypeText(findLookUp(dto.getOsType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEcsSpec(EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", ecsSpecDTO.getDescription());

			Validate.isTrue(comm.ecsSpecService.findEcsSpec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			ecsSpec.setCode("EcsSpec-" + Identities.randomBase62(8));
			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setId(0);

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsSpec(Integer id, EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", ecsSpecDTO.getDescription());

			Validate.isTrue(
					comm.ecsSpecService.findEcsSpec(paramsMap) == null
							|| ecsSpec.getDescription().equals(ecsSpecDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ecsSpecDTO, EcsSpec.class), ecsSpec);

			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ecsSpec.setIdClass(TableNameUtil.getTableName(EcsSpec.class));

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsSpec(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			ecsSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList(SearchParams searchParams) {

		DTOListResult<EcsSpecDTO> result = new DTOListResult<EcsSpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.ecsSpecService.getEcsSpecList(searchParams.getParamsMap()),
					EcsSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<EcsSpecDTO> result = new PaginationResult<EcsSpecDTO>();

		try {

			return comm.ecsSpecService.getEcsSpecDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LogDTO> findLog(Integer id) {

		DTOResult<LogDTO> result = new DTOResult<LogDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Log log = comm.logService.findLog(id);

			Validate.notNull(log, ERROR.OBJECT_NULL);

			LogDTO dto = BeanMapper.map(log, LogDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setServiceTypeText(findLookUp(dto.getServiceType()).getDto().getDescription());
			dto.setOperateTypeText(findLookUp(dto.getOperateType()).getDto().getDescription());
			dto.setResultText(findLookUp(dto.getResult()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<LogDTO> findLogByParams(SearchParams searchParams) {

		DTOResult<LogDTO> result = new DTOResult<LogDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Log log = comm.logService.findLog(searchParams.getParamsMap());

			Validate.notNull(log, ERROR.OBJECT_NULL);

			LogDTO dto = BeanMapper.map(log, LogDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setServiceTypeText(findLookUp(dto.getServiceType()).getDto().getDescription());
			dto.setOperateTypeText(findLookUp(dto.getOperateType()).getDto().getDescription());
			dto.setResultText(findLookUp(dto.getResult()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}

	}

	@Override
	public IdResult createLog(LogDTO logDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(logDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", logDTO.getDescription());

			Validate.isTrue(comm.logService.findLog(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Log log = BeanMapper.map(logDTO, Log.class);
			log.setCode("Log-" + Identities.randomBase62(8));
			log.setUser(DEFAULT_USER);
			log.setId(0);

			BeanValidators.validateWithException(validator, log);

			comm.logService.saveOrUpdate(log);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LogDTO> getLogList(SearchParams searchParams) {

		DTOListResult<LogDTO> result = new DTOListResult<LogDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.logService.getLogList(searchParams.getParamsMap()), LogDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LogDTO> getLogPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<LogDTO> result = new PaginationResult<LogDTO>();

		try {

			return comm.logService.getLogDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcs(Integer id) {

		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			// Reference
			// dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcsByParams(SearchParams searchParams) {

		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(searchParams.getParamsMap());

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			// Reference
			// dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}

	}

	@Override
	public IdResult createEcs(EcsDTO ecsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", ecsDTO.getDescription());

			Validate.isTrue(comm.ecsService.findEcs(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象s
			Ecs ecs = BeanMapper.map(ecsDTO, Ecs.class);
			ecs.setCode("ECS-" + Identities.randomBase62(8));
			ecs.setUser(DEFAULT_USER);
			ecs.setId(0);

			BeanValidators.validateWithException(validator, ecs);

			comm.ecsService.saveOrUpdate(ecs);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult updateEcs(Integer id, EcsDTO ecsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", ecsDTO.getDescription());

			Validate.isTrue(
					comm.ecsService.findEcs(paramsMap) == null || ecs.getDescription().equals(ecsDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ecsDTO, Ecs.class), ecs);

			ecs.setUser(DEFAULT_USER);
			ecs.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ecs.setIdClass(TableNameUtil.getTableName(Ecs.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, ecs);

			comm.ecsService.saveOrUpdate(ecs);

			return result;

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcs(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.INPUT_NULL);

			ecs.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.ecsService.saveOrUpdate(ecs);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsDTO> getEcsList(SearchParams searchParams) {

		DTOListResult<EcsDTO> result = new DTOListResult<EcsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.ecsService.getEcsList(searchParams.getParamsMap()), EcsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsDTO> getEcsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<EcsDTO> result = new PaginationResult<EcsDTO>();

		try {

			return comm.ecsService.getEcsDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3DTO> findEs3(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<Es3DTO> findEs3ByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEs3(Es3DTO es3dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEs3(Integer id, Es3DTO es3dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEs3(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<Es3DTO> getEs3List(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<Es3DTO> getEs3Pagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipDTO> findEip(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipDTO> findEipByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEip(EipDTO eipDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEip(Integer id, EipDTO eipDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEip(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EipDTO> getEipList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EipDTO> getEipPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbDTO> findElb(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbDTO> findElbByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createElb(ElbDTO elbDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateElb(Integer id, ElbDTO elbDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteElb(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ElbDTO> getElbList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ElbDTO> getElbPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsDTO> findDns(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsDTO> findDnsByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createDns(DnsDTO dnsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateDns(Integer id, DnsDTO dnsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteDns(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<DnsDTO> getDnsList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DnsDTO> getDnsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgDTO> findEsg(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgDTO> findEsgByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEsg(EsgDTO esgDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEsg(Integer id, EsgDTO esgDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEsg(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EsgDTO> getEsgList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EsgDTO> getEsgPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VpnDTO> findVpn(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VpnDTO> findVpnByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createVpn(VpnDTO vpnDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateVpn(Integer id, VpnDTO vpnDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteVpn(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<VpnDTO> getVpnList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<VpnDTO> getVpnPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEipPolicy(EipPolicyDTO eipPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEipPolicy(Integer id, EipPolicyDTO eipPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEipPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EipPolicyDTO> getEipPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EipPolicyDTO> getEipPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createElbPolicy(ElbPolicyDTO elbPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateElbPolicy(Integer id, ElbPolicyDTO elbPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteElbPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ElbPolicyDTO> getElbPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ElbPolicyDTO> getElbPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createDnsPolicy(DnsPolicyDTO dnsPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateDnsPolicy(Integer id, DnsPolicyDTO dnsPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteDnsPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<DnsPolicyDTO> getDnsPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicyByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createEsgPolicy(EsgPolicyDTO esgPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateEsgPolicy(Integer id, EsgPolicyDTO esgPolicyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteEsgPolicy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<EsgPolicyDTO> getEsgPolicyList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEsg(Integer ecsId, Integer esgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEsg(Integer ecsId, Integer esgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEs3(Integer ecsId, Integer es3Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEs3(Integer ecsId, Integer es3Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsEip(Integer ecsId, Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsEip(Integer ecsId, Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEcsElb(Integer ecsId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEcsElb(Integer ecsId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEipElb(Integer eipId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEipElb(Integer eipId, Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapEipDns(Integer eipId, Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapEipDns(Integer eipId, Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMapTagService(Integer tagId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMapTagService(Integer tagId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDisk(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDiskByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createHardDisk(HardDiskDTO hardDiskDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateHardDisk(Integer id, HardDiskDTO hardDiskDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteHardDisk(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<HardDiskDTO> getHardDiskList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<HardDiskDTO> getHardDiskPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<MemoryDTO> findMemory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<MemoryDTO> findMemoryByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createMemory(MemoryDTO memoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateMemory(Integer id, MemoryDTO memoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteMemory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<MemoryDTO> getMemoryList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<MemoryDTO> getMemoryPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicDTO> findNic(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicDTO> findNicByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createNic(NicDTO nicDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateNic(Integer id, NicDTO nicDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteNic(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<NicDTO> getNicList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<NicDTO> getNicPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageBoxDTO> findStorageBox(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StorageBoxDTO> findStorageBoxByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createStorageBox(StorageBoxDTO storageBoxDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateStorageBox(Integer id, StorageBoxDTO storageBoxDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteStorageBox(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<StorageBoxDTO> getStorageBoxList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<StorageBoxDTO> getStorageBoxPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallDTO> findFirewall(Integer id) {

		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			FirewallDTO dto = BeanMapper.map(firewall, FirewallDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallDTO> findFirewallByParams(SearchParams searchParams) {

		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(searchParams.getParamsMap());

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			FirewallDTO dto = BeanMapper.map(firewall, FirewallDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFirewall(FirewallDTO firewallDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", firewallDTO.getDescription());

			Validate.isTrue(comm.firewallService.findFirewall(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Firewall firewall = BeanMapper.map(firewallDTO, Firewall.class);
			firewall.setCode("Firewall-" + Identities.randomBase62(8));
			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));
			firewall.setUser(DEFAULT_USER);
			firewall.setId(0);

			BeanValidators.validateWithException(validator, firewall);

			comm.firewallService.saveOrUpdate(firewall);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewall(Integer id, FirewallDTO firewallDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", firewallDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.firewallService.findFirewall(paramsMap) == null
							|| firewall.getDescription().equals(firewallDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallDTO, Firewall.class), firewall);

			firewall.setUser(DEFAULT_USER);
			firewall.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, firewall);

			comm.firewallService.saveOrUpdate(firewall);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewall(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			firewall.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.firewallService.saveOrUpdate(firewall);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallDTO> getFirewallList(SearchParams searchParams) {

		DTOListResult<FirewallDTO> result = new DTOListResult<FirewallDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.firewallService.getFirewallList(searchParams.getParamsMap()),
					FirewallDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallDTO> getFirewallPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<FirewallDTO> result = new PaginationResult<FirewallDTO>();

		try {

			return comm.firewallService.getFirewallDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancer(Integer id) {

		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);

			LoadBalancerDTO dto = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancerByParams(SearchParams searchParams) {

		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(searchParams.getParamsMap());

			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);

			LoadBalancerDTO dto = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createLoadBalancer(LoadBalancerDTO loadBalancerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", loadBalancerDTO.getDescription());

			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			LoadBalancer loadBalancer = BeanMapper.map(loadBalancerDTO, LoadBalancer.class);
			loadBalancer.setCode("LoadBalancer-" + Identities.randomBase62(8));
			loadBalancer.setUser(DEFAULT_USER);
			loadBalancer.setId(0);

			BeanValidators.validateWithException(validator, loadBalancer);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancer(Integer id, LoadBalancerDTO loadBalancerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", loadBalancerDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(paramsMap) == null
					|| loadBalancer.getDescription().equals(loadBalancerDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerDTO, LoadBalancer.class), loadBalancer);

			loadBalancer.setUser(DEFAULT_USER);
			loadBalancer.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, loadBalancer);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancer(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Validate.notNull(id, ERROR.OBJECT_NULL);

			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));

			loadBalancer.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerDTO> getLoadBalancerList(SearchParams searchParams) {

		DTOListResult<LoadBalancerDTO> result = new DTOListResult<LoadBalancerDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.loadBalancerService.getLoadBalancerList(searchParams.getParamsMap()), LoadBalancerDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<LoadBalancerDTO> result = new PaginationResult<LoadBalancerDTO>();

		try {

			return comm.loadBalancerService.getLoadBalancerDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServer(Integer id) {

		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Validate.notNull(server, ERROR.OBJECT_NULL);

			ServerDTO dto = BeanMapper.map(server, ServerDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServerByParams(SearchParams searchParams) {

		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(searchParams.getParamsMap());

			Validate.notNull(server, ERROR.OBJECT_NULL);

			ServerDTO dto = BeanMapper.map(server, ServerDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createServer(ServerDTO serverDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", serverDTO.getDescription());

			Validate.isTrue(comm.serverService.findServer(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Server server = BeanMapper.map(serverDTO, Server.class);
			server.setCode("Server-" + Identities.randomBase62(8));
			server.setUser(DEFAULT_USER);
			server.setId(0);

			BeanValidators.validateWithException(validator, server);

			comm.serverService.saveOrUpdate(server);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServer(Integer id, ServerDTO serverDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", serverDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.serverService.findServer(paramsMap) == null
							|| server.getDescription().equals(serverDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverDTO, Server.class), server);

			server.setUser(DEFAULT_USER);
			server.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			server.setIdClass(TableNameUtil.getTableName(Server.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, server);

			comm.serverService.saveOrUpdate(server);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServer(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Validate.notNull(server, ERROR.OBJECT_NULL);

			server.setIdClass(TableNameUtil.getTableName(Server.class));

			server.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.serverService.saveOrUpdate(server);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerDTO> getServerList(SearchParams searchParams) {

		DTOListResult<ServerDTO> result = new DTOListResult<ServerDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.serverService.getServerList(searchParams.getParamsMap()),
					ServerDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerDTO> getServerPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<ServerDTO> result = new PaginationResult<ServerDTO>();

		try {

			return comm.serverService.getServerDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StorageDTO> findStorage(Integer id) {

		DTOResult<StorageDTO> result = new DTOResult<StorageDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Storage storage = comm.storageService.findStorage(id);

			Validate.notNull(storage, ERROR.OBJECT_NULL);

			StorageDTO dto = BeanMapper.map(storage, StorageDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<StorageDTO> findStorageByParams(SearchParams searchParams) {

		DTOResult<StorageDTO> result = new DTOResult<StorageDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Storage storage = comm.storageService.findStorage(searchParams.getParamsMap());

			Validate.notNull(storage, ERROR.OBJECT_NULL);

			StorageDTO dto = BeanMapper.map(storage, StorageDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createStorage(StorageDTO storageDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storageDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storageDTO.getDescription());

			Validate.isTrue(comm.storageService.findStorage(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Storage storage = BeanMapper.map(storageDTO, Storage.class);
			storage.setCode("Storage-" + Identities.randomBase62(8));
			storage.setUser(DEFAULT_USER);
			storage.setId(0);

			BeanValidators.validateWithException(validator, storage);

			comm.storageService.saveOrUpdate(storage);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateStorage(Integer id, StorageDTO storageDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storageDTO, ERROR.INPUT_NULL);

			Storage storage = comm.storageService.findStorage(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storageDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.storageService.findStorage(paramsMap) == null
							|| storage.getDescription().equals(storageDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(storageDTO, Storage.class), storage);

			storage.setUser(DEFAULT_USER);
			storage.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			storage.setIdClass(TableNameUtil.getTableName(Storage.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, storage);

			comm.storageService.saveOrUpdate(storage);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteStorage(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Storage storage = comm.storageService.findStorage(id);

			Validate.notNull(storage, ERROR.OBJECT_NULL);

			storage.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.storageService.saveOrUpdate(storage);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StorageDTO> getStorageList(SearchParams searchParams) {

		DTOListResult<StorageDTO> result = new DTOListResult<StorageDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.storageService.getStorageList(searchParams.getParamsMap()),
					StorageDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StorageDTO> getStoragePagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<StorageDTO> result = new PaginationResult<StorageDTO>();

		try {

			return comm.storageService.getStorageDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitches(Integer id) {

		DTOResult<SwitchesDTO> result = new DTOResult<SwitchesDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Validate.notNull(switches, ERROR.OBJECT_NULL);

			SwitchesDTO dto = BeanMapper.map(switches, SwitchesDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitchesByParams(SearchParams searchParams) {

		DTOResult<SwitchesDTO> result = new DTOResult<SwitchesDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(searchParams.getParamsMap());

			Validate.notNull(switches, ERROR.OBJECT_NULL);

			SwitchesDTO dto = BeanMapper.map(switches, SwitchesDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createSwitches(SwitchesDTO switchesDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", switchesDTO.getDescription());

			Validate.isTrue(comm.switchesService.findSwitches(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Switches switches = BeanMapper.map(switchesDTO, Switches.class);
			switches.setCode("Switch-" + Identities.randomBase62(8));
			switches.setUser(DEFAULT_USER);
			switches.setId(0);

			BeanValidators.validateWithException(validator, switches);

			comm.switchesService.saveOrUpdate(switches);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitches(Integer id, SwitchesDTO switchesDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", switchesDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.switchesService.findSwitches(paramsMap) == null
							|| switches.getDescription().equals(switchesDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchesDTO, Switches.class), switches);
			switches.setUser(DEFAULT_USER);
			switches.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switches.setIdClass(TableNameUtil.getTableName(Switches.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, switches);

			comm.switchesService.saveOrUpdate(switches);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitches(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Validate.notNull(switches, ERROR.OBJECT_NULL);

			switches.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.switchesService.saveOrUpdate(switches);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchesDTO> getSwitchesList(SearchParams searchParams) {

		DTOListResult<SwitchesDTO> result = new DTOListResult<SwitchesDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.switchesService.getSwitchesList(searchParams.getParamsMap()),
					SwitchesDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchesDTO> getSwitchesPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<SwitchesDTO> result = new PaginationResult<SwitchesDTO>();

		try {

			return comm.switchesService.getSwitchesDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddress(Integer id) {

		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			IpaddressDTO dto = BeanMapper.map(ipaddress, IpaddressDTO.class);

			// Reference
			dto.setVlanDTO(findVlan(dto.getVlan()).getDto());

			// LookUp
			if (dto.getIsp() != null) {
				dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
			}
			dto.setIpAddressPoolText(findLookUp(dto.getIpAddressPool()).getDto().getDescription());
			dto.setIpAddressStatusText(findLookUp(dto.getIpAddressStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddressByParams(SearchParams searchParams) {

		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(searchParams.getParamsMap());

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			IpaddressDTO dto = BeanMapper.map(ipaddress, IpaddressDTO.class);

			// Reference
			dto.setVlanDTO(findVlan(dto.getVlan()).getDto());

			// LookUp
			if (dto.getIsp() != null) {
				dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
			}
			dto.setIpAddressPoolText(findLookUp(dto.getIpAddressPool()).getDto().getDescription());
			dto.setIpAddressStatusText(findLookUp(dto.getIpAddressStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createIpaddress(IpaddressDTO ipaddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);

			// 验证description是否在统一Vlan下唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", ipaddressDTO.getDescription());
			paramsMap.put("EQ_vlan", ipaddressDTO.getVlan());

			Validate.isTrue(comm.ipaddressService.findIpaddress(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Ipaddress ipaddress = BeanMapper.map(ipaddressDTO, Ipaddress.class);
			ipaddress.setIpaddressStatus(LookUpConstants.IPAddressStatus.未使用.getValue());
			ipaddress.setCode("IP-" + Identities.randomBase62(8));
			ipaddress.setUser(DEFAULT_USER);
			ipaddress.setId(0);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, ipaddress);

			comm.ipaddressService.saveOrUpdate(ipaddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIpaddress(Integer id, IpaddressDTO ipaddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.INPUT_NULL);

			// 验证description是否在统一Vlan下唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", ipaddressDTO.getDescription());
			paramsMap.put("EQ_vlan", ipaddressDTO.getVlan());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressService.findIpaddress(paramsMap) == null
					|| ipaddress.getDescription().equals(ipaddressDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ipaddressDTO, Ipaddress.class), ipaddress);

			ipaddress.setUser(DEFAULT_USER);
			ipaddress.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, ipaddress);

			comm.ipaddressService.saveOrUpdate(ipaddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteIpaddress(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			ipaddress.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.ipaddressService.saveOrUpdate(ipaddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IpaddressDTO> getIpaddressList(SearchParams searchParams) {

		DTOListResult<IpaddressDTO> result = new DTOListResult<IpaddressDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.ipaddressService.getIpaddressList(searchParams.getParamsMap()),
					IpaddressDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IpaddressDTO> getIpaddressPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<IpaddressDTO> result = new PaginationResult<IpaddressDTO>();

		try {

			return comm.ipaddressService.getIpaddressDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult allocateIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult insertIpaddress(List<IpaddressDTO> ipaddressDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult initIpaddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<VlanDTO> findVlan(Integer id) {

		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			VlanDTO dto = BeanMapper.map(vlan, VlanDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanDTO> findVlanByParams(SearchParams searchParams) {

		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(searchParams.getParamsMap());

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			VlanDTO dto = BeanMapper.map(vlan, VlanDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createVlan(VlanDTO vlanDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", vlanDTO.getDescription());

			Validate.isTrue(comm.vlanService.findVlan(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Vlan vlan = BeanMapper.map(vlanDTO, Vlan.class);
			vlan.setCode("Vlan-" + Identities.randomBase62(8));
			vlan.setUser(DEFAULT_USER);
			vlan.setId(0);

			BeanValidators.validateWithException(validator, vlan);

			comm.vlanService.saveOrUpdate(vlan);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVlan(Integer id, VlanDTO vlanDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", vlanDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.vlanService.findVlan(paramsMap) == null
							|| vlan.getDescription().equals(vlanDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vlanDTO, Vlan.class), vlan);

			vlan.setUser(DEFAULT_USER);
			vlan.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, vlan);

			comm.vlanService.saveOrUpdate(vlan);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVlan(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			vlan.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.vlanService.saveOrUpdate(vlan);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VlanDTO> getVlanList(SearchParams searchParams) {

		DTOListResult<VlanDTO> result = new DTOListResult<VlanDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.vlanService.getVlanList(searchParams.getParamsMap()), VlanDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VlanDTO> getVlanPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<VlanDTO> result = new PaginationResult<VlanDTO>();

		try {

			return comm.vlanService.getVlanDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult insertVlan(List<VlanDTO> vlanDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createFirewallPort(FirewallPortDTO firewallPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateFirewallPort(Integer id, FirewallPortDTO firewallPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteFirewallPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<FirewallPortDTO> getFirewallPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createLoadBalancerPort(LoadBalancerPortDTO loadBalancerPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateLoadBalancerPort(Integer id, LoadBalancerPortDTO loadBalancerPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteLoadBalancerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StoragePortDTO> findStoragePort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<StoragePortDTO> findStoragePortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createStoragePort(StoragePortDTO storagePortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateStoragePort(Integer id, StoragePortDTO storagePortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteStoragePort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<StoragePortDTO> getStoragePortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<StoragePortDTO> getStoragePortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createServerPort(ServerPortDTO serverPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateServerPort(Integer id, ServerPortDTO serverPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteServerPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<ServerPortDTO> getServerPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<ServerPortDTO> getServerPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createSwitchPort(SwitchPortDTO switchPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateSwitchPort(Integer id, SwitchPortDTO switchPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteSwitchPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<SwitchPortDTO> getSwitchPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<SwitchPortDTO> getSwitchPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicPortDTO> findNicPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOResult<NicPortDTO> findNicPortByParams(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult createNicPort(NicPortDTO nicPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult updateNicPort(Integer id, NicPortDTO nicPortDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult deleteNicPort(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTOListResult<NicPortDTO> getNicPortList(SearchParams searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<NicPortDTO> getNicPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
