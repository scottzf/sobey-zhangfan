package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.LookUpConstants;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.DnsPolicy;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.EipPolicy;
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.entity.ElbPolicy;
import com.sobey.cmdbuild.entity.Es3;
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.entity.EsgPolicy;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Log;
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.entity.MapEcsEip;
import com.sobey.cmdbuild.entity.MapEcsElb;
import com.sobey.cmdbuild.entity.MapEcsEs3;
import com.sobey.cmdbuild.entity.MapEcsEsg;
import com.sobey.cmdbuild.entity.MapEipDns;
import com.sobey.cmdbuild.entity.MapEipElb;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.entity.StorageBox;
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3DTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
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
import com.sobey.core.utils.TableNameUtil;

@WebService(serviceName = "CmdbuildSoapService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
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
	public DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id) {

		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(company, CompanyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<CompanyDTO> findCompanyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Company company = comm.companyService.findCompany(searchParams.getParamsMap());

			Validate.notNull(company, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(company, CompanyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(companyDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", companyDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyService.findCompany(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Company company = BeanMapper.map(companyDTO, Company.class);
			company.setUser(DEFAULT_USER);
			company.setId(0);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, company);

			comm.companyService.saveOrUpdate(company);

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
	public IdResult updateCompany(@WebParam(name = "id") Integer id,
			@WebParam(name = "companyDTO") CompanyDTO companyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(companyDTO, ERROR.INPUT_NULL);

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", companyDTO.getCode());

			Validate.isTrue(
					comm.companyService.findCompany(paramsMap) == null
							|| company.getCode().equals(companyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(companyDTO, Company.class), company);

			company.setUser(DEFAULT_USER);
			company.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			company.setIdClass(TableNameUtil.getTableName(Company.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, company);

			comm.companyService.saveOrUpdate(company);

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
	public IdResult deleteCompany(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, ERROR.INPUT_NULL);

			company.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.companyService.saveOrUpdate(company);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<CompanyDTO> result = new PaginationResult<CompanyDTO>();

		try {

			return comm.companyService.getCompanyDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<CompanyDTO> getCompanyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<CompanyDTO> result = new DTOListResult<CompanyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.companyService.getCompanyList(searchParams.getParamsMap()),
					CompanyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id) {

		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(id);

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			TenantsDTO dto = BeanMapper.map(tenants, TenantsDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setCompanyDTO(findCompany(dto.getCompany()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenantsByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(searchParams.getParamsMap());

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			TenantsDTO dto = BeanMapper.map(tenants, TenantsDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setCompanyDTO(findCompany(dto.getCompany()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", tenantsDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsService.findTenants(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Tenants tenants = BeanMapper.map(tenantsDTO, Tenants.class);
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
	public IdResult updateTenants(@WebParam(name = "id") Integer id,
			@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(id);

			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", tenantsDTO.getCode());

			Validate.isTrue(
					comm.tenantsService.findTenants(paramsMap) == null
							|| tenants.getCode().equals(tenantsDTO.getCode()), ERROR.OBJECT_DUPLICATE);

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
	public IdResult deleteTenants(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<TenantsDTO> getTenantsPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<TenantsDTO> getTenantsList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id) {

		DTOResult<TagDTO> result = new DTOResult<TagDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(id);

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			TagDTO dto = BeanMapper.map(tag, TagDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTagByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<TagDTO> result = new DTOResult<TagDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(searchParams.getParamsMap());

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			TagDTO dto = BeanMapper.map(tag, TagDTO.class);

			// DTO中增加复杂对象的属性,获得复杂关联对象DTO,set进DTO中.
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tagDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的code,如果有相同的code名称，则不能创建.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", tagDTO.getCode());
			paramsMap.put("EQ_tenants", tagDTO.getTenants());

			Validate.isTrue(comm.tagService.findTag(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Tag tag = BeanMapper.map(tagDTO, Tag.class);
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
	public IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tagDTO, ERROR.INPUT_NULL);

			Tag tag = comm.tagService.findTag(id);

			Validate.notNull(tag, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的code如果有相同的code名称，则不能创建.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", tagDTO.getCode());
			paramsMap.put("EQ_tenants", tagDTO.getTenants());

			Validate.isTrue(comm.tagService.findTag(paramsMap) == null || tag.getCode().equals(tagDTO.getCode()),
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
	public IdResult deleteTag(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<TagDTO> getTagList(@WebParam(name = "searchParams") SearchParams searchParams) {
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
	public DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id) {

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
	public DTOResult<IdcDTO> findIdcByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(idcDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", idcDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcService.findIdc(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Idc idc = BeanMapper.map(idcDTO, Idc.class);
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
	public IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(idcDTO, ERROR.INPUT_NULL);

			Idc idc = comm.idcService.findIdc(id);

			Validate.notNull(idc, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", idcDTO.getCode());
			Validate.isTrue(comm.idcService.findIdc(paramsMap) == null || idc.getCode().equals(idcDTO.getCode()),
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
	public IdResult deleteIdc(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<IdcDTO> getIdcList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id) {

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
	public DTOResult<RackDTO> findRackByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(rackDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", rackDTO.getCode());

			Validate.isTrue(comm.rackService.findRack(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Rack rack = BeanMapper.map(rackDTO, Rack.class);
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
	public IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(rackDTO, ERROR.INPUT_NULL);

			Rack rack = comm.rackService.findRack(id);

			Validate.notNull(rack, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", rackDTO.getCode());

			Validate.isTrue(comm.rackService.findRack(paramsMap) == null || rack.getCode().equals(rackDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

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
	public IdResult deleteRack(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<RackDTO> getRackPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<RackDTO> getRackList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(findLookUp(dto.getHight()).getDto().getDescription());
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
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(searchParams.getParamsMap());

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(findLookUp(dto.getHight()).getDto().getDescription());
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
	public IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", deviceSpecDTO.getCode());
			paramsMap.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			deviceSpec.setIdClass(TableNameUtil.getTableName(DeviceSpec.class));
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
	public IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", deviceSpecDTO.getCode());
			paramsMap.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(
					comm.deviceSpecService.findDeviceSpec(paramsMap) == null
							|| deviceSpec.getCode().equals(deviceSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

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
	public IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			deviceSpec.setIdClass(TableNameUtil.getTableName(DeviceSpec.class));

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			EcsSpecDTO dto = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(searchParams.getParamsMap());

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			EcsSpecDTO dto = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(comm.ecsSpecService.findEcsSpec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);

			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ecsSpec.setIdClass(TableNameUtil.getTableName(EcsSpec.class));
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
	public IdResult updateEcsSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(
					comm.ecsSpecService.findEcsSpec(paramsMap) == null
							|| ecsSpec.getCode().equals(ecsSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

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
	public IdResult deleteEcsSpec(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id) {

		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			EipSpecDTO dto = BeanMapper.map(eipSpec, EipSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(searchParams.getParamsMap());

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			EipSpecDTO dto = BeanMapper.map(eipSpec, EipSpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(comm.eipSpecService.findEipSpec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EipSpec eipSpec = BeanMapper.map(eipSpecDTO, EipSpec.class);
			eipSpec.setUser(DEFAULT_USER);
			eipSpec.setIdClass(TableNameUtil.getTableName(EipSpec.class));
			eipSpec.setId(0);

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

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
	public IdResult updateEipSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(
					comm.eipSpecService.findEipSpec(paramsMap) == null
							|| eipSpec.getCode().equals(eipSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipSpecDTO, EipSpec.class), eipSpec);

			eipSpec.setUser(DEFAULT_USER);
			eipSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eipSpec.setIdClass(TableNameUtil.getTableName(EipSpec.class));

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

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
	public IdResult deleteEipSpec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			eipSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EipSpecDTO> result = new PaginationResult<EipSpecDTO>();

		try {

			return comm.eipSpecService.getEipSpecDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<EipSpecDTO> result = new DTOListResult<EipSpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.eipSpecService.getEipSpecList(searchParams.getParamsMap()),
					EipSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id) {

		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			Es3SpecDTO dto = BeanMapper.map(es3Spec, Es3SpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setIopsText(findLookUp(dto.getIops()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(searchParams.getParamsMap());

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			Es3SpecDTO dto = BeanMapper.map(es3Spec, Es3SpecDTO.class);

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setIopsText(findLookUp(dto.getIops()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(comm.es3SpecService.findEs3Spec(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Es3Spec es3Spec = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			es3Spec.setUser(DEFAULT_USER);
			es3Spec.setIdClass(TableNameUtil.getTableName(Es3Spec.class));
			es3Spec.setId(0);

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

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
	public IdResult updateEs3Spec(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(
					comm.es3SpecService.findEs3Spec(paramsMap) == null
							|| es3Spec.getCode().equals(es3SpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(es3SpecDTO, Es3Spec.class), es3Spec);

			es3Spec.setUser(DEFAULT_USER);
			es3Spec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			es3Spec.setIdClass(TableNameUtil.getTableName(Es3Spec.class));

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

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
	public IdResult deleteEs3Spec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			es3Spec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<Es3SpecDTO> result = new PaginationResult<Es3SpecDTO>();

		try {

			return comm.es3SpecService.getEs3SpecDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<Es3SpecDTO> result = new DTOListResult<Es3SpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.es3SpecService.getEs3SpecList(searchParams.getParamsMap()),
					Es3SpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LogDTO> findCs2(@WebParam(name = "id") Integer id) {

		DTOResult<LogDTO> result = new DTOResult<LogDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(id);

			Validate.notNull(cs2, ERROR.OBJECT_NULL);

			LogDTO dto = BeanMapper.map(cs2, LogDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setEs3SpecDTO(findEs3Spec(dto.getEs3Spec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LogDTO> findCs2ByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<LogDTO> result = new DTOResult<LogDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(searchParams.getParamsMap());

			Validate.notNull(cs2, ERROR.OBJECT_NULL);

			LogDTO dto = BeanMapper.map(cs2, LogDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setEs3SpecDTO(findEs3Spec(dto.getEs3Spec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createCs2(@WebParam(name = "cs2DTO") LogDTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", cs2DTO.getCode());

			Validate.isTrue(comm.cs2Service.findCs2(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Cs2 cs2 = BeanMapper.map(cs2DTO, Cs2.class);

			cs2.setUser(DEFAULT_USER);
			cs2.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			cs2.setId(0);

			BeanValidators.validateWithException(validator, cs2);

			comm.cs2Service.saveOrUpdate(cs2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") LogDTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", cs2DTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.cs2Service.findCs2(paramsMap) == null || cs2.getCode().equals(cs2DTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(cs2DTO, Cs2.class), cs2);

			cs2.setIdClass(TableNameUtil.getTableName(Cs2.class));
			cs2.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, cs2);

			comm.cs2Service.saveOrUpdate(cs2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteCs2(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(id);

			Validate.notNull(cs2, ERROR.INPUT_NULL);

			cs2.setIdClass(TableNameUtil.getTableName(Cs2.class));
			cs2.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.cs2Service.saveOrUpdate(cs2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LogDTO> getCs2Pagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<LogDTO> result = new PaginationResult<LogDTO>();

		try {

			return comm.cs2Service.getCs2DTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LogDTO> getCs2List(@WebParam(name = "searchParams") SearchParams searchParams) {
		DTOListResult<LogDTO> result = new DTOListResult<LogDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.cs2Service.getCs2List(searchParams.getParamsMap()), LogDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3DTO> findAs2(@WebParam(name = "id") Integer id) {

		DTOResult<Es3DTO> result = new DTOResult<Es3DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3 as2 = comm.as2Service.findAs2(id);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			Es3DTO dto = BeanMapper.map(as2, Es3DTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(findNetappController(dto.getNetappController()).getDto());

			// LookUp
			dto.setVolumeTypeText(findLookUp(dto.getVolumeType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3DTO> findAs2ByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<Es3DTO> result = new DTOResult<Es3DTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Es3 as2 = comm.as2Service.findAs2(searchParams.getParamsMap());

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			Es3DTO dto = BeanMapper.map(as2, Es3DTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(findNetappController(dto.getNetappController()).getDto());

			// LookUp
			dto.setVolumeTypeText(findLookUp(dto.getVolumeType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createAs2(@WebParam(name = "as2DTO") Es3DTO as2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(as2DTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", as2DTO.getCode());

			Validate.isTrue(comm.as2Service.findAs2(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Es3 as2 = BeanMapper.map(as2DTO, Es3.class);

			as2.setIdClass(TableNameUtil.getTableName(Es3.class));
			as2.setUser(DEFAULT_USER);
			as2.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			as2.setId(0);

			BeanValidators.validateWithException(validator, as2);

			comm.as2Service.saveOrUpdate(as2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") Es3DTO as2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(as2DTO, ERROR.INPUT_NULL);

			Es3 as2 = comm.as2Service.findAs2(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", as2DTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.as2Service.findAs2(paramsMap) == null || as2.getCode().equals(as2DTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(as2DTO, Es3.class), as2);

			as2.setIdClass(TableNameUtil.getTableName(Es3.class));
			as2.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			as2.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, as2);

			comm.as2Service.saveOrUpdate(as2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteAs2(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3 as2 = comm.as2Service.findAs2(id);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			as2.setIdClass(TableNameUtil.getTableName(Es3.class));
			as2.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.as2Service.saveOrUpdate(as2);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3DTO> getAs2Pagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<Es3DTO> result = new PaginationResult<Es3DTO>();

		try {

			return comm.as2Service.getAs2DTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3DTO> getAs2List(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<Es3DTO> result = new DTOListResult<Es3DTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.as2Service.getAs2List(searchParams.getParamsMap()), Es3DTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcs(@WebParam(name = "id") Integer id) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setEcsAgentText(findLookUp(dto.getEcsAgent()).getDto().getDescription());
			dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());
			dto.setImageText(findLookUp(dto.getImage()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcsByParams(@WebParam(name = "searchParams") SearchParams searchParams) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(searchParams.getParamsMap());

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setEcsAgentText(findLookUp(dto.getEcsAgent()).getDto().getDescription());
			dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());
			dto.setImageText(findLookUp(dto.getImage()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEcs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", ecsDTO.getCode());

			Validate.isTrue(comm.ecsService.findEcs(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Ecs ecs = BeanMapper.map(ecsDTO, Ecs.class);

			ecs.setIdClass(TableNameUtil.getTableName(Ecs.class));
			ecs.setUser(DEFAULT_USER);
			ecs.setStatus(CMDBuildConstants.STATUS_ACTIVE);
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
	public IdResult updateEcs(@WebParam(name = "id") Integer id, @WebParam(name = "ecsDTO") EcsDTO ecsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", ecsDTO.getCode());

			Validate.isTrue(comm.ecsService.findEcs(paramsMap) == null || ecs.getCode().equals(ecsDTO.getCode()),
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
	public IdResult deleteEcs(@WebParam(name = "id") Integer id) {

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
	public DTOListResult<EcsDTO> getEcsList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id) {

		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(findEipSpec(dto.getEipSpec()).getDto());

			// LookUp
			dto.setEipStatusText(findLookUp(dto.getEipStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipDTO> findEipByParams(@WebParam(name = "searchParams") SearchParams searchParams) {
		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(searchParams.getParamsMap());

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(findEipSpec(dto.getEipSpec()).getDto());

			// LookUp
			dto.setEipStatusText(findLookUp(dto.getEipStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", eipDTO.getCode());
			System.out.println(comm.eipService.findEip(paramsMap));

			Validate.isTrue(comm.eipService.findEip(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Eip eip = BeanMapper.map(eipDTO, Eip.class);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));
			eip.setUser(DEFAULT_USER);
			eip.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eip.setId(0);

			BeanValidators.validateWithException(validator, eip);

			comm.eipService.saveOrUpdate(eip);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipDTO, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", eipDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipService.findEip(paramsMap) == null || eip.getCode().equals(eipDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipDTO, Eip.class), eip);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));

			eip.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eip.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, eip);

			comm.eipService.saveOrUpdate(eip);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEip(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));

			eip.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.eipService.saveOrUpdate(eip);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EipDTO> result = new PaginationResult<EipDTO>();

		try {

			return comm.eipService.getEipDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipDTO> getEipList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<EipDTO> result = new DTOListResult<EipDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.eipService.getEipList(searchParams.getParamsMap()), EipDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicy(@WebParam(name = "id") Integer id) {

		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			EipPolicyDTO dto = BeanMapper.map(eipPolicy, EipPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(searchParams.getParamsMap());

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			EipPolicyDTO dto = BeanMapper.map(eipPolicy, EipPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipPolicy(@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", eipPolicyDTO.getCode());

			Validate.isTrue(comm.eipPolicyService.findEipPolicy(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EipPolicy eipPolicy = BeanMapper.map(eipPolicyDTO, EipPolicy.class);
			eipPolicy.setUser(DEFAULT_USER);
			eipPolicy.setIdClass(TableNameUtil.getTableName(EipPolicy.class));
			eipPolicy.setId(0);

			BeanValidators.validateWithException(validator, eipPolicy);

			comm.eipPolicyService.saveOrUpdate(eipPolicy);

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
	public IdResult updateEipPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", eipPolicyDTO.getCode());

			Validate.isTrue(
					comm.eipPolicyService.findEipPolicy(paramsMap) == null
							|| eipPolicy.getCode().equals(eipPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipPolicyDTO, EipPolicy.class), eipPolicy);

			eipPolicy.setUser(DEFAULT_USER);
			eipPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eipPolicy.setIdClass(TableNameUtil.getTableName(EipPolicy.class));

			BeanValidators.validateWithException(validator, eipPolicy);

			comm.eipPolicyService.saveOrUpdate(eipPolicy);

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
	public IdResult deleteEipPolicy(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			eipPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.eipPolicyService.saveOrUpdate(eipPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipPolicyDTO> getEipPolicyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<EipPolicyDTO> result = new DTOListResult<EipPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.eipPolicyService.getEipPolicyList(searchParams.getParamsMap()),
					EipPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipPolicyDTO> getEipPolicyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EipPolicyDTO> result = new PaginationResult<EipPolicyDTO>();

		try {

			return comm.eipPolicyService.getEipPolicyDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id) {

		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbDTO> findElbByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(searchParams.getParamsMap());

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", elbDTO.getCode());

			Validate.isTrue(comm.elbService.findElb(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Elb elb = BeanMapper.map(elbDTO, Elb.class);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));
			elb.setUser(DEFAULT_USER);
			elb.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elb.setId(0);

			BeanValidators.validateWithException(validator, elb);

			comm.elbService.saveOrUpdate(elb);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbDTO, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", elbDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbService.findElb(paramsMap) == null || elb.getCode().equals(elbDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(elbDTO, Elb.class), elb);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));

			elb.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elb.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, elb);

			comm.elbService.saveOrUpdate(elb);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteElb(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));
			elb.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.elbService.saveOrUpdate(elb);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<ElbDTO> result = new PaginationResult<ElbDTO>();

		try {

			return comm.elbService.getElbDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbDTO> getElbList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<ElbDTO> result = new DTOListResult<ElbDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.elbService.getElbList(searchParams.getParamsMap()), ElbDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicy(@WebParam(name = "id") Integer id) {

		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			ElbPolicyDTO dto = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(searchParams.getParamsMap());

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			ElbPolicyDTO dto = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult createElbPolicy(@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", elbPolicyDTO.getCode());

			Validate.isTrue(comm.elbPolicyService.findElbPolicy(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			ElbPolicy elbPolicy = BeanMapper.map(elbPolicyDTO, ElbPolicy.class);
			elbPolicy.setUser(DEFAULT_USER);
			elbPolicy.setIdClass(TableNameUtil.getTableName(DnsPolicy.class));
			elbPolicy.setId(0);

			BeanValidators.validateWithException(validator, elbPolicy);

			comm.elbPolicyService.saveOrUpdate(elbPolicy);

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
	public IdResult updateElbPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", elbPolicyDTO.getCode());

			Validate.isTrue(
					comm.elbPolicyService.findElbPolicy(paramsMap) == null
							|| elbPolicy.getCode().equals(elbPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(elbPolicyDTO, ElbPolicy.class), elbPolicy);

			elbPolicy.setUser(DEFAULT_USER);
			elbPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elbPolicy.setIdClass(TableNameUtil.getTableName(DnsPolicy.class));

			BeanValidators.validateWithException(validator, elbPolicy);

			comm.elbPolicyService.saveOrUpdate(elbPolicy);

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
	public IdResult deleteElbPolicy(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			elbPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.elbPolicyService.saveOrUpdate(elbPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbPolicyDTO> getElbPolicyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<ElbPolicyDTO> result = new DTOListResult<ElbPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.elbPolicyService.getElbPolicyList(searchParams.getParamsMap()),
					ElbPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public PaginationResult<ElbPolicyDTO> getElbPolicyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<ElbPolicyDTO> result = new PaginationResult<ElbPolicyDTO>();

		try {

			return comm.elbPolicyService.getElbPolicyDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id) {

		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setDomainTypeText(findLookUp(dto.getDomainType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsDTO> findDnsByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(searchParams.getParamsMap());

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setDomainTypeText(findLookUp(dto.getDomainType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDns(@WebParam(name = "dnsDTO") DnsDTO dnsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", dnsDTO.getCode());

			Validate.isTrue(comm.dnsService.findDns(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Dns dns = BeanMapper.map(dnsDTO, Dns.class);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));
			dns.setUser(DEFAULT_USER);
			dns.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			dns.setId(0);

			BeanValidators.validateWithException(validator, dns);

			comm.dnsService.saveOrUpdate(dns);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "dnsDTO") DnsDTO dnsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", dnsDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.dnsService.findDns(paramsMap) == null || dns.getCode().equals(dnsDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(dnsDTO, Dns.class), dns);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));
			dns.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			dns.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, dns);

			comm.dnsService.saveOrUpdate(dns);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDns(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));
			dns.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.dnsService.saveOrUpdate(dns);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<DnsDTO> result = new PaginationResult<DnsDTO>();

		try {

			return comm.dnsService.getDnsDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DnsDTO> getDnsList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<DnsDTO> result = new DTOListResult<DnsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.dnsService.getDnsList(searchParams.getParamsMap()), DnsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicy(@WebParam(name = "id") Integer id) {

		DTOResult<DnsPolicyDTO> result = new DTOResult<DnsPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(id);

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			DnsPolicyDTO dto = BeanMapper.map(dnsPolicy, DnsPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<DnsPolicyDTO> result = new DTOResult<DnsPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(searchParams.getParamsMap());

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			DnsPolicyDTO dto = BeanMapper.map(dnsPolicy, DnsPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDnsPolicy(@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsPolicyDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", dnsPolicyDTO.getCode());

			Validate.isTrue(comm.dnsPolicyService.findDnsPolicy(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			DnsPolicy dnsPolicy = BeanMapper.map(dnsPolicyDTO, DnsPolicy.class);
			dnsPolicy.setUser(DEFAULT_USER);
			dnsPolicy.setIdClass(TableNameUtil.getTableName(DnsPolicy.class));
			dnsPolicy.setId(0);

			BeanValidators.validateWithException(validator, dnsPolicy);

			comm.dnsPolicyService.saveOrUpdate(dnsPolicy);

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
	public IdResult updateDnsPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "dnsPolicyDTO") DnsPolicyDTO dnsPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsPolicyDTO, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(id);

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", dnsPolicyDTO.getCode());

			Validate.isTrue(
					comm.dnsPolicyService.findDnsPolicy(paramsMap) == null
							|| dnsPolicy.getCode().equals(dnsPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(dnsPolicyDTO, DnsPolicy.class), dnsPolicy);

			dnsPolicy.setUser(DEFAULT_USER);
			dnsPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			dnsPolicy.setIdClass(TableNameUtil.getTableName(DnsPolicy.class));

			BeanValidators.validateWithException(validator, dnsPolicy);

			comm.dnsPolicyService.saveOrUpdate(dnsPolicy);

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
	public IdResult deleteDnsPolicy(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(id);

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			dnsPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.dnsPolicyService.saveOrUpdate(dnsPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOListResult<DnsPolicyDTO> getDnsPolicyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<DnsPolicyDTO> result = new DTOListResult<DnsPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.dnsPolicyService.getDnsPolicyList(searchParams.getParamsMap()),
					DnsPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<DnsPolicyDTO> result = new PaginationResult<DnsPolicyDTO>();

		try {

			return comm.dnsPolicyService.getDnsPolicyDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id) {

		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(id);

			Validate.notNull(esg, ERROR.OBJECT_NULL);

			EsgDTO dto = BeanMapper.map(esg, EsgDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgDTO> findEsgByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(searchParams.getParamsMap());

			Validate.notNull(esg, ERROR.OBJECT_NULL);

			EsgDTO dto = BeanMapper.map(esg, EsgDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(esgDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", esgDTO.getCode());

			Validate.isTrue(comm.esgService.findEsg(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Esg esg = BeanMapper.map(esgDTO, Esg.class);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));
			esg.setUser(DEFAULT_USER);
			esg.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			esg.setId(0);

			BeanValidators.validateWithException(validator, esg);

			comm.esgService.saveOrUpdate(esg);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "esgDTO") EsgDTO esgDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(esgDTO, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", esgDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgService.findEsg(paramsMap) == null || esg.getCode().equals(esgDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(esgDTO, Esg.class), esg);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));
			esg.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			esg.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, esg);

			comm.esgService.saveOrUpdate(esg);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEsg(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(id);

			Validate.notNull(esg, ERROR.OBJECT_NULL);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));
			esg.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.esgService.saveOrUpdate(esg);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EsgDTO> result = new PaginationResult<EsgDTO>();

		try {

			return comm.esgService.getEsgDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgDTO> getEsgList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<EsgDTO> result = new DTOListResult<EsgDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.esgService.getEsgList(searchParams.getParamsMap()), EsgDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicy(@WebParam(name = "id") Integer id) {

		DTOResult<EsgPolicyDTO> result = new DTOResult<EsgPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);

			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);

			EsgPolicyDTO dto = BeanMapper.map(esgPolicy, EsgPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<EsgPolicyDTO> result = new DTOResult<EsgPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(searchParams.getParamsMap());

			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);

			EsgPolicyDTO dto = BeanMapper.map(esgPolicy, EsgPolicyDTO.class);

			// LookUp
			dto.setProtocolText(findLookUp(dto.getProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEsgPolicy(@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(esgPolicyDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", esgPolicyDTO.getCode());

			Validate.isTrue(comm.esgPolicyService.findEsgPolicy(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EsgPolicy esgPolicy = BeanMapper.map(esgPolicyDTO, EsgPolicy.class);
			esgPolicy.setUser(DEFAULT_USER);
			esgPolicy.setIdClass(TableNameUtil.getTableName(EsgPolicy.class));
			esgPolicy.setId(0);

			BeanValidators.validateWithException(validator, esgPolicy);

			comm.esgPolicyService.saveOrUpdate(esgPolicy);

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
	public IdResult updateEsgPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(esgPolicyDTO, ERROR.INPUT_NULL);

			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);

			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", esgPolicyDTO.getCode());

			Validate.isTrue(
					comm.esgPolicyService.findEsgPolicy(paramsMap) == null
							|| esgPolicy.getCode().equals(esgPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(esgPolicyDTO, EsgPolicy.class), esgPolicy);

			esgPolicy.setUser(DEFAULT_USER);
			esgPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			esgPolicy.setIdClass(TableNameUtil.getTableName(EsgPolicy.class));

			BeanValidators.validateWithException(validator, esgPolicy);

			comm.esgPolicyService.saveOrUpdate(esgPolicy);

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
	public IdResult deleteEsgPolicy(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);

			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);

			esgPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.esgPolicyService.saveOrUpdate(esgPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOListResult<EsgPolicyDTO> getEsgPolicyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<EsgPolicyDTO> result = new DTOListResult<EsgPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.esgPolicyService.getEsgPolicyList(searchParams.getParamsMap()),
					EsgPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EsgPolicyDTO> result = new PaginationResult<EsgPolicyDTO>();

		try {

			return comm.esgPolicyService.getEsgPolicyDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	public DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id) {

		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VpnDTO> findVpnByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(searchParams.getParamsMap());

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

			// Reference
			dto.setTagDTO(findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", vpnDTO.getCode());

			Validate.isTrue(comm.vpnService.findVpn(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Vpn vpn = BeanMapper.map(vpnDTO, Vpn.class);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));
			vpn.setUser(DEFAULT_USER);
			vpn.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vpn.setId(0);

			BeanValidators.validateWithException(validator, vpn);

			comm.vpnService.saveOrUpdate(vpn);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "vpnDTO") VpnDTO vpnDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", vpnDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vpnService.findVpn(paramsMap) == null || vpn.getCode().equals(vpnDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vpnDTO, Vpn.class), vpn);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));
			vpn.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vpn.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, vpn);

			comm.vpnService.saveOrUpdate(vpn);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVpn(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));

			vpn.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.vpnService.saveOrUpdate(vpn);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<VpnDTO> result = new PaginationResult<VpnDTO>();

		try {

			return comm.vpnService.getVpnDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VpnDTO> getVpnList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<VpnDTO> result = new DTOListResult<VpnDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.vpnService.getVpnList(searchParams.getParamsMap()), VpnDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicy(@WebParam(name = "id") Integer id) {

		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);

			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);

			GroupPolicyDTO dto = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicyByParams(@WebParam(name = "searchParams") SearchParams searchParams) {
		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(searchParams.getParamsMap());

			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);

			GroupPolicyDTO dto = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createGroupPolicy(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(groupPolicyDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", groupPolicyDTO.getCode());

			Validate.isTrue(comm.groupPolicyService.findGroupPolicy(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			GroupPolicy groupPolicy = BeanMapper.map(groupPolicyDTO, GroupPolicy.class);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));
			groupPolicy.setUser(DEFAULT_USER);
			groupPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			groupPolicy.setId(0);

			BeanValidators.validateWithException(validator, groupPolicy);

			comm.groupPolicyService.saveOrUpdate(groupPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateGroupPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(groupPolicyDTO, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", groupPolicyDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.groupPolicyService.findGroupPolicy(paramsMap) == null
							|| groupPolicy.getCode().equals(groupPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(groupPolicyDTO, GroupPolicy.class), groupPolicy);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));
			groupPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			groupPolicy.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, groupPolicy);

			comm.groupPolicyService.saveOrUpdate(groupPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteGroupPolicy(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);

			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));

			groupPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.groupPolicyService.saveOrUpdate(groupPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<GroupPolicyDTO> getGroupPolicyPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<GroupPolicyDTO> result = new PaginationResult<GroupPolicyDTO>();

		try {

			return comm.groupPolicyService.getGroupPolicyDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<GroupPolicyDTO> getGroupPolicyList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<GroupPolicyDTO> result = new DTOListResult<GroupPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.groupPolicyService.getGroupPolicyList(searchParams.getParamsMap()),
					GroupPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId) {

		IdResult result = new IdResult();

		try {

			MapEcsEsg map = new MapEcsEsg();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(esgId);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Esg.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEcsEsgService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsEsg(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "esgId") Integer esgId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(esgId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", esgId);

			MapEcsEsg map = comm.mapEcsEsgService.findMapEcsEsg(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsEsgService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsAs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "as2Id") Integer as2Id) {

		IdResult result = new IdResult();

		try {

			Log map = new Log();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(as2Id);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Es3.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEcsAs2Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsAs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "as2Id") Integer as2Id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(as2Id, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", as2Id);

			Log map = comm.mapEcsAs2Service.findMapEcsAs2(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsAs2Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsCs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "cs2Id") Integer cs2Id) {

		IdResult result = new IdResult();

		try {

			MapEcsEs3 map = new MapEcsEs3();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(cs2Id);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Cs2.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEcsCs2Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsCs2(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "cs2Id") Integer cs2Id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(cs2Id, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", cs2Id);

			MapEcsEs3 map = comm.mapEcsCs2Service.findMapEcsCs2(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsCs2Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId) {

		IdResult result = new IdResult();

		try {

			MapEcsEip map = new MapEcsEip();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(eipId);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Eip.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEcsEipService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsEip(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "eipId") Integer eipId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(eipId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", eipId);

			MapEcsEip map = comm.mapEcsEipService.findMapEcsEip(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsEipService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId) {

		IdResult result = new IdResult();

		try {

			MapEcsElb map = new MapEcsElb();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(elbId);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Elb.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEcsElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsElb(@WebParam(name = "ecsId") Integer ecsId, @WebParam(name = "elbId") Integer elbId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(elbId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", elbId);

			MapEcsElb map = comm.mapEcsElbService.findMapEcsElb(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId) {

		IdResult result = new IdResult();

		try {

			MapEipElb map = new MapEipElb();

			map.setId(0);
			map.setIdObj1(eipId);
			map.setIdObj2(elbId);
			map.setIdClass1(TableNameUtil.getTableName(Eip.class));
			map.setIdClass2(TableNameUtil.getTableName(Elb.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEipElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEipElb(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "elbId") Integer elbId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipId, ERROR.INPUT_NULL);
			Validate.notNull(elbId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", eipId);
			paramsMap.put("EQ_idObj2", elbId);

			MapEipElb map = comm.mapEipElbService.findMapEipElb(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEipElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId) {

		IdResult result = new IdResult();

		try {

			MapEipDns map = new MapEipDns();

			map.setId(0);
			map.setIdObj1(eipId);
			map.setIdObj2(dnsId);
			map.setIdClass1(TableNameUtil.getTableName(Eip.class));
			map.setIdClass2(TableNameUtil.getTableName(Dns.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapEipDnsService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEipDns(@WebParam(name = "eipId") Integer eipId, @WebParam(name = "dnsId") Integer dnsId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipId, ERROR.INPUT_NULL);
			Validate.notNull(dnsId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", eipId);
			paramsMap.put("EQ_idObj2", dnsId);

			MapEipDns map = comm.mapEipDnsService.findMapEipDns(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEipDnsService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapGroupPolicyVlan(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "vlanId") Integer vlanId) {

		IdResult result = new IdResult();

		try {

			MapGroupPolicyVlan map = new MapGroupPolicyVlan();

			map.setId(0);
			map.setIdObj1(groupPolicyId);
			map.setIdObj2(vlanId);
			map.setIdClass1(TableNameUtil.getTableName(GroupPolicy.class));
			map.setIdClass2(TableNameUtil.getTableName(Vlan.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapGroupPolicyVlanService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapGroupPolicyVlan(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "vlanId") Integer vlanId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(groupPolicyId, ERROR.INPUT_NULL);
			Validate.notNull(vlanId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", groupPolicyId);
			paramsMap.put("EQ_idObj2", vlanId);

			MapGroupPolicyVlan map = comm.mapGroupPolicyVlanService.findMapGroupPolicyVlan(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapGroupPolicyVlanService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapGroupPolicyIpaddress(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "ipaddressId") Integer ipaddressId) {

		IdResult result = new IdResult();

		try {

			MapGroupPolicyIpaddress map = new MapGroupPolicyIpaddress();

			map.setId(0);
			map.setIdObj1(groupPolicyId);
			map.setIdObj2(ipaddressId);
			map.setIdClass1(TableNameUtil.getTableName(GroupPolicy.class));
			map.setIdClass2(TableNameUtil.getTableName(Ipaddress.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapGroupPolicyIpaddressService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapGroupPolicyIpaddress(@WebParam(name = "groupPolicyId") Integer groupPolicyId,
			@WebParam(name = "ipaddressId") Integer ipaddressId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(groupPolicyId, ERROR.INPUT_NULL);
			Validate.notNull(ipaddressId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", groupPolicyId);
			paramsMap.put("EQ_idObj2", ipaddressId);

			MapGroupPolicyIpaddress map = comm.mapGroupPolicyIpaddressService.findMapGroupPolicyIpaddress(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapGroupPolicyIpaddressService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapVpnGroupPolicy(@WebParam(name = "vpnId") Integer vpnId,
			@WebParam(name = "groupPolicyId") Integer groupPolicyId) {

		IdResult result = new IdResult();

		try {

			MapVpnGroupPolicy map = new MapVpnGroupPolicy();

			map.setId(0);
			map.setIdObj1(vpnId);
			map.setIdObj2(groupPolicyId);
			map.setIdClass1(TableNameUtil.getTableName(Vpn.class));
			map.setIdClass2(TableNameUtil.getTableName(GroupPolicy.class));
			map.setUser(DEFAULT_USER);
			map.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.mapVpnGroupPolicyService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapVpnGroupPolicy(@WebParam(name = "vpnId") Integer vpnId,
			@WebParam(name = "groupPolicyId") Integer groupPolicyId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vpnId, ERROR.INPUT_NULL);
			Validate.notNull(groupPolicyId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", vpnId);
			paramsMap.put("EQ_idObj2", groupPolicyId);

			MapVpnGroupPolicy map = comm.mapVpnGroupPolicyService.findMapVpnGroupPolicy(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapVpnGroupPolicyService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id) {

		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			FimasDTO dto = BeanMapper.map(fimas, FimasDTO.class);

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
	public DTOResult<FimasDTO> findFimasByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(searchParams.getParamsMap());

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			FimasDTO dto = BeanMapper.map(fimas, FimasDTO.class);

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
	public IdResult createFimas(@WebParam(name = "fimasDTO") FimasDTO fimasDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_code", fimasDTO.getCode());

			Validate.isTrue(comm.fimasService.findFimas(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Fimas fimas = BeanMapper.map(fimasDTO, Fimas.class);

			fimas.setUser(DEFAULT_USER);
			fimas.setIdClass(TableNameUtil.getTableName(Fimas.class));
			fimas.setId(0);

			BeanValidators.validateWithException(validator, fimas);

			comm.fimasService.saveOrUpdate(fimas);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "fimasDTO") FimasDTO fimasDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", fimasDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasService.findFimas(paramsMap) == null
					|| fimas.getCode().equals(fimasDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasDTO, Fimas.class), fimas);

			fimas.setIdClass(TableNameUtil.getTableName(Fimas.class));
			fimas.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimas.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimas);

			comm.fimasService.saveOrUpdate(fimas);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimas(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			fimas.setIdClass(TableNameUtil.getTableName(Fimas.class));
			fimas.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasService.saveOrUpdate(fimas);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasDTO> getFimasPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<FimasDTO> result = new PaginationResult<FimasDTO>();

		try {

			return comm.fimasService.getFimasDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasDTO> getFimasList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<FimasDTO> result = new DTOListResult<FimasDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.fimasService.getFimasList(searchParams.getParamsMap()),
					FimasDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id) {

		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);

			FimasBoxDTO dto = BeanMapper.map(fimasBox, FimasBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxDTO> findFimasBoxByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(searchParams.getParamsMap());

			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);

			FimasBoxDTO dto = BeanMapper.map(fimasBox, FimasBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFimasBox(@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", fimasBoxDTO.getCode());

			Validate.isTrue(comm.fimasBoxService.findFimasBox(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			FimasBox fimasBox = BeanMapper.map(fimasBoxDTO, FimasBox.class);

			fimasBox.setUser(DEFAULT_USER);
			fimasBox.setIdClass(TableNameUtil.getTableName(FimasBox.class));
			fimasBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimasBox.setId(0);

			BeanValidators.validateWithException(validator, fimasBox);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", fimasBoxDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.fimasBoxService.findFimasBox(paramsMap) == null
							|| fimasBox.getCode().equals(fimasBoxDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasBoxDTO, FimasBox.class), fimasBox);

			fimasBox.setIdClass(TableNameUtil.getTableName(FimasBox.class));
			fimasBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimasBox.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimasBox);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasBox(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);

			fimasBox.setIdClass(TableNameUtil.getTableName(FimasBox.class));
			fimasBox.setUser(DEFAULT_USER);
			fimasBox.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<FimasBoxDTO> result = new PaginationResult<FimasBoxDTO>();

		try {

			return comm.fimasBoxService.getFimasBoxDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasBoxDTO> getFimasBoxList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<FimasBoxDTO> result = new DTOListResult<FimasBoxDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.fimasBoxService.getFimasBoxList(searchParams.getParamsMap()),
					FimasBoxDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id) {

		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);

			FimasPortDTO dto = BeanMapper.map(fimasPort, FimasPortDTO.class);

			// Reference
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortDTO> findFimasPortByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(searchParams.getParamsMap());

			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);

			FimasPortDTO dto = BeanMapper.map(fimasPort, FimasPortDTO.class);

			// Reference
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFimasPort(@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", fimasPortDTO.getCode());

			Validate.isTrue(comm.fimasPortService.findFimasPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			FimasPort fimasPort = BeanMapper.map(fimasPortDTO, FimasPort.class);

			fimasPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimasPort.setUser(DEFAULT_USER);
			fimasPort.setIdClass(TableNameUtil.getTableName(FimasPort.class));
			fimasPort.setId(0);

			BeanValidators.validateWithException(validator, fimasPort);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", fimasPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.fimasPortService.findFimasPort(paramsMap) == null
							|| fimasPort.getCode().equals(fimasPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasPortDTO, FimasPort.class), fimasPort);

			fimasPort.setIdClass(TableNameUtil.getTableName(FimasPort.class));
			fimasPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimasPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimasPort);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);

			fimasPort.setIdClass(TableNameUtil.getTableName(FimasPort.class));

			fimasPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<FimasPortDTO> result = new PaginationResult<FimasPortDTO>();

		try {

			return comm.fimasPortService.getFimasPortDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasPortDTO> getFimasPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<FimasPortDTO> result = new DTOListResult<FimasPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.fimasPortService.getFimasPortList(searchParams.getParamsMap()),
					FimasPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id) {

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
	public DTOResult<FirewallDTO> findFirewallByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", firewallDTO.getCode());

			Validate.isTrue(comm.firewallService.findFirewall(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Firewall firewall = BeanMapper.map(firewallDTO, Firewall.class);

			firewall.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewall.setUser(DEFAULT_USER);
			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));
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
	public IdResult updateFirewall(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", firewallDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.firewallService.findFirewall(paramsMap) == null
							|| firewall.getCode().equals(firewallDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallDTO, Firewall.class), firewall);

			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));
			firewall.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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
	public IdResult deleteFirewall(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));

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
	public PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<FirewallDTO> getFirewallList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id) {

		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO dto = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			// Reference
			dto.setFirewallDTO(findFirewall(dto.getFirewall()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByParams(
			@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(searchParams.getParamsMap());

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO dto = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			// Reference
			dto.setFirewallDTO(findFirewall(dto.getFirewall()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", firewallPortDTO.getCode());

			Validate.isTrue(comm.firewallPortService.findFirewallPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			FirewallPort firewallPort = BeanMapper.map(firewallPortDTO, FirewallPort.class);

			firewallPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewallPort.setUser(DEFAULT_USER);
			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));
			firewallPort.setId(0);

			BeanValidators.validateWithException(validator, firewallPort);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", firewallPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortService.findFirewallPort(paramsMap) == null
					|| firewallPort.getCode().equals(firewallPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallPortDTO, FirewallPort.class), firewallPort);

			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));
			firewallPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewallPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, firewallPort);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));

			firewallPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<FirewallPortDTO> result = new PaginationResult<FirewallPortDTO>();

		try {

			return comm.firewallPortService.getFirewallPortDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallPortDTO> getFirewallPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<FirewallPortDTO> result = new DTOListResult<FirewallPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.firewallPortService.getFirewallPortList(searchParams.getParamsMap()), FirewallPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id) {

		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO dto = BeanMapper.map(hardDisk, HardDiskDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setRotationalSpeedText(findLookUp(dto.getRotationalSpeed()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDiskByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(searchParams.getParamsMap());

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO dto = BeanMapper.map(hardDisk, HardDiskDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			dto.setRotationalSpeedText(findLookUp(dto.getRotationalSpeed()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", hardDiskDTO.getCode());

			Validate.isTrue(comm.hardDiskService.findHardDisk(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			HardDisk hardDisk = BeanMapper.map(hardDiskDTO, HardDisk.class);

			hardDisk.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			hardDisk.setUser(DEFAULT_USER);
			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));
			hardDisk.setId(0);

			BeanValidators.validateWithException(validator, hardDisk);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateHardDisk(@WebParam(name = "id") Integer id,
			@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", hardDiskDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.hardDiskService.findHardDisk(paramsMap) == null
							|| hardDisk.getCode().equals(hardDiskDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(hardDiskDTO, HardDisk.class), hardDisk);

			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));
			hardDisk.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			hardDisk.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, hardDisk);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteHardDisk(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));

			hardDisk.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<HardDiskDTO> result = new PaginationResult<HardDiskDTO>();

		try {

			return comm.hardDiskService.getHardDiskDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<HardDiskDTO> getHardDiskList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<HardDiskDTO> result = new DTOListResult<HardDiskDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.hardDiskService.getHardDiskList(searchParams.getParamsMap()),
					HardDiskDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id) {

		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			IpaddressDTO dto = BeanMapper.map(ipaddress, IpaddressDTO.class);

			// Reference
			dto.setVlanDTO(findVlan(dto.getVlan()).getDto());

			// LookUp
			dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
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
	public DTOResult<IpaddressDTO> findIpaddressByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(searchParams.getParamsMap());

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			IpaddressDTO dto = BeanMapper.map(ipaddress, IpaddressDTO.class);

			// Reference
			dto.setVlanDTO(findVlan(dto.getVlan()).getDto());

			// LookUp
			dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
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
	public IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", ipaddressDTO.getCode());

			Validate.isTrue(comm.ipaddressService.findIpaddress(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Ipaddress ipaddress = BeanMapper.map(ipaddressDTO, Ipaddress.class);

			BeanValidators.validateWithException(validator, ipaddress);

			ipaddress.setIpaddressStatus(LookUpConstants.IPAddressStatus.未使用.getValue());
			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));
			ipaddress.setUser(DEFAULT_USER);
			ipaddress.setId(0);

			comm.ipaddressService.saveOrUpdate(ipaddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", ipaddressDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.ipaddressService.findIpaddress(paramsMap) == null
							|| ipaddress.getCode().equals(ipaddressDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ipaddressDTO, Ipaddress.class), ipaddress);

			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));
			ipaddress.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ipaddress.setUser(DEFAULT_USER);

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
	public IdResult deleteIpaddress(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));
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
	public PaginationResult<IpaddressDTO> getIpaddressPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<IpaddressDTO> getIpaddressList(@WebParam(name = "searchParams") SearchParams searchParams) {
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
	public IdResult allocateIpaddress(@WebParam(name = "id") Integer id) {
		return changeIpaddressStatus(id, LookUpConstants.IPAddressStatus.使用中.getValue());
	}

	@Override
	public IdResult insertIpaddress(@WebParam(name = "ipaddressDTOList") List<IpaddressDTO> ipaddressDTOList) {

		IdResult result = new IdResult();

		try {

			// 先判断对象是否为空
			Validate.notNull(ipaddressDTOList, ERROR.INPUT_NULL);

			Integer tempId = 0;
			Integer insertCount = ipaddressDTOList.size(); // 插入Ipaddress的数量
			Integer insertSuccessCount = 0; // 成功插入Ipaddress的数量

			for (IpaddressDTO ipaddressDTO : ipaddressDTOList) {

				Map<String, Object> paramsMap = Maps.newHashMap();

				paramsMap.put("EQ_code", ipaddressDTO.getCode());

				// 如果code重复,跳过本次loop
				if (comm.ipaddressService.findIpaddress(paramsMap) != null) {
					continue;
				}

				Ipaddress ipaddress = BeanMapper.map(ipaddressDTO, Ipaddress.class);

				BeanValidators.validateWithException(validator, ipaddress);

				ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));
				ipaddress.setIpaddressStatus(LookUpConstants.IPAddressStatus.未使用.getValue());// 设置状态为未使用
				ipaddress.setUser(DEFAULT_USER);

				/* 使用spring-data-jap,在postgresql中,id不能重复,mysql则无此问题.不知道是否和主键策略有关系! */
				ipaddress.setId(tempId);

				comm.ipaddressService.saveOrUpdate(ipaddress);

				tempId--;
				insertSuccessCount++;
			}

			String message = "0".equals(insertSuccessCount.toString()) ? "Ipaddress已存在" : "插入Ipaddress " + insertCount
					+ " 条,成功创建Ipaddress " + insertSuccessCount + " 条";

			result.setMessage(message);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult initIpaddress(Integer id) {
		return changeIpaddressStatus(id, LookUpConstants.IPAddressStatus.未使用.getValue());
	}

	/**
	 * 修改Ipaddress对象的ipaddressStatus.
	 * 
	 * @param id
	 *            ipaddress Id
	 * @param ipaddressStatus
	 *            ipaddress状态 {@link LookUpConstants.IPAddressStatus}
	 * @return
	 */
	private IdResult changeIpaddressStatus(Integer id, Integer ipaddressStatus) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			ipaddress.setIpaddressStatus(ipaddressStatus);

			comm.ipaddressService.saveOrUpdate(ipaddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id) {

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
	public DTOResult<LoadBalancerDTO> findLoadBalancerByParams(
			@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", loadBalancerDTO.getCode());

			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			LoadBalancer loadBalancer = BeanMapper.map(loadBalancerDTO, LoadBalancer.class);

			loadBalancer.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancer.setUser(DEFAULT_USER);
			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));
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
	public IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", loadBalancerDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(paramsMap) == null
					|| loadBalancer.getCode().equals(loadBalancerDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerDTO, LoadBalancer.class), loadBalancer);

			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));
			loadBalancer.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancer.setUser(DEFAULT_USER);

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
	public IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<LoadBalancerDTO> getLoadBalancerList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id) {

		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO dto = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setLoadBalancerDTO(findLoadBalancer(dto.getLoadBalancer()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(
			@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(searchParams
					.getParamsMap());

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO dto = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setLoadBalancerDTO(findLoadBalancer(dto.getLoadBalancer()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createLoadBalancerPort(
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", loadBalancerPortDTO.getCode());

			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			LoadBalancerPort loadBalancerPort = BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class);

			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));
			loadBalancerPort.setUser(DEFAULT_USER);
			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancerPort.setId(0);

			BeanValidators.validateWithException(validator, loadBalancerPort);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", loadBalancerPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(paramsMap) == null
					|| loadBalancerPort.getCode().equals(loadBalancerPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class), loadBalancerPort);

			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));
			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancerPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, loadBalancerPort);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));

			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<LoadBalancerPortDTO> result = new PaginationResult<LoadBalancerPortDTO>();

		try {

			return comm.loadBalancerPortService.getLoadBalancerPortDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(
			@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<LoadBalancerPortDTO> result = new DTOListResult<LoadBalancerPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.loadBalancerPortService.getLoadBalancerPortList(searchParams.getParamsMap()),
					LoadBalancerPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id) {

		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO dto = BeanMapper.map(memory, MemoryDTO.class);

			// Reference
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setFrequencyText(Integer.parseInt(findLookUp(dto.getFrequency()).getDto().getDescription()));

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryDTO> findMemoryByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(searchParams.getParamsMap());

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO dto = BeanMapper.map(memory, MemoryDTO.class);

			// Reference
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// LookUp
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setFrequencyText(Integer.parseInt(findLookUp(dto.getFrequency()).getDto().getDescription()));

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", memoryDTO.getCode());

			Validate.isTrue(comm.memoryService.findMemory(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Memory memory = BeanMapper.map(memoryDTO, Memory.class);

			memory.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			memory.setUser(DEFAULT_USER);
			memory.setIdClass(TableNameUtil.getTableName(Memory.class));
			memory.setId(0);

			BeanValidators.validateWithException(validator, memory);

			comm.memoryService.saveOrUpdate(memory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", memoryDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.memoryService.findMemory(paramsMap) == null || memory.getCode().equals(memoryDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(memoryDTO, Memory.class), memory);

			memory.setIdClass(TableNameUtil.getTableName(Memory.class));
			memory.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			memory.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, memory);

			comm.memoryService.saveOrUpdate(memory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMemory(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			memory.setIdClass(TableNameUtil.getTableName(Memory.class));

			memory.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.memoryService.saveOrUpdate(memory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MemoryDTO> getMemoryPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<MemoryDTO> result = new PaginationResult<MemoryDTO>();

		try {

			return comm.memoryService.getMemoryDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MemoryDTO> getMemoryList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<MemoryDTO> result = new DTOListResult<MemoryDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.memoryService.getMemoryList(searchParams.getParamsMap()),
					MemoryDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StorageBoxDTO> findNetappBox(@WebParam(name = "id") Integer id) {

		DTOResult<StorageBoxDTO> result = new DTOResult<StorageBoxDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.netappBoxService.findNetappBox(id);

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			StorageBoxDTO dto = BeanMapper.map(netappBox, StorageBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setNetappControllerDTO(findNetappController(dto.getNetappController()).getDto());

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StorageBoxDTO> findNetappBoxByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<StorageBoxDTO> result = new DTOResult<StorageBoxDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.netappBoxService.findNetappBox(searchParams.getParamsMap());

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			StorageBoxDTO dto = BeanMapper.map(netappBox, StorageBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			dto.setDeviceSpecDTO(findDeviceSpec(dto.getDeviceSpec()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setNetappControllerDTO(findNetappController(dto.getNetappController()).getDto());

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNetappBox(@WebParam(name = "netappBoxDTO") StorageBoxDTO netappBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappBoxDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappBoxDTO.getCode());

			Validate.isTrue(comm.netappBoxService.findNetappBox(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			StorageBox netappBox = BeanMapper.map(netappBoxDTO, StorageBox.class);

			netappBox.setUser(DEFAULT_USER);
			netappBox.setIdClass(TableNameUtil.getTableName(StorageBox.class));
			netappBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappBox.setId(0);

			BeanValidators.validateWithException(validator, netappBox);

			comm.netappBoxService.saveOrUpdate(netappBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxDTO") StorageBoxDTO netappBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappBoxDTO, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.netappBoxService.findNetappBox(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappBoxDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.netappBoxService.findNetappBox(paramsMap) == null
							|| netappBox.getCode().equals(netappBoxDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappBoxDTO, StorageBox.class), netappBox);

			netappBox.setIdClass(TableNameUtil.getTableName(StorageBox.class));
			netappBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappBox.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, netappBox);

			comm.netappBoxService.saveOrUpdate(netappBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappBox(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.netappBoxService.findNetappBox(id);

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			netappBox.setIdClass(TableNameUtil.getTableName(StorageBox.class));
			netappBox.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappBoxService.saveOrUpdate(netappBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StorageBoxDTO> getNetappBoxPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<StorageBoxDTO> result = new PaginationResult<StorageBoxDTO>();

		try {

			return comm.netappBoxService.getNetappBoxDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StorageBoxDTO> getNetappBoxList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<StorageBoxDTO> result = new DTOListResult<StorageBoxDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.netappBoxService.getNetappBoxList(searchParams.getParamsMap()),
					StorageBoxDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StorageDTO> findNetappController(@WebParam(name = "id") Integer id) {

		DTOResult<StorageDTO> result = new DTOResult<StorageDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Storage netappController = comm.netappControllerService.findNetappController(id);

			Validate.notNull(netappController, ERROR.OBJECT_NULL);

			StorageDTO dto = BeanMapper.map(netappController, StorageDTO.class);

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
	public DTOResult<StorageDTO> findNetappControllerByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<StorageDTO> result = new DTOResult<StorageDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Storage netappController = comm.netappControllerService.findNetappController(searchParams.getParamsMap());

			Validate.notNull(netappController, ERROR.OBJECT_NULL);

			StorageDTO dto = BeanMapper.map(netappController, StorageDTO.class);

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
	public IdResult createNetappController(@WebParam(name = "netappControllerDTO") StorageDTO netappControllerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappControllerDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappControllerDTO.getCode());

			Validate.isTrue(comm.netappControllerService.findNetappController(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			Storage netappController = BeanMapper.map(netappControllerDTO, Storage.class);

			netappController.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappController.setUser(DEFAULT_USER);
			netappController.setIdClass(TableNameUtil.getTableName(Storage.class));
			netappController.setId(0);

			BeanValidators.validateWithException(validator, netappController);

			comm.netappControllerService.saveOrUpdate(netappController);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappController(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerDTO") StorageDTO netappControllerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappControllerDTO, ERROR.INPUT_NULL);

			Storage netappController = comm.netappControllerService.findNetappController(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappControllerDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappControllerService.findNetappController(paramsMap) == null
					|| netappController.getCode().equals(netappControllerDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappControllerDTO, Storage.class), netappController);

			netappController.setIdClass(TableNameUtil.getTableName(Storage.class));
			netappController.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappController.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, netappController);

			comm.netappControllerService.saveOrUpdate(netappController);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappController(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Storage netappController = comm.netappControllerService.findNetappController(id);

			Validate.notNull(netappController, ERROR.OBJECT_NULL);

			netappController.setIdClass(TableNameUtil.getTableName(Storage.class));

			netappController.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappControllerService.saveOrUpdate(netappController);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StorageDTO> getNetappControllerPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<StorageDTO> result = new PaginationResult<StorageDTO>();

		try {

			return comm.netappControllerService.getNetappControllerDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StorageDTO> getNetappControllerList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<StorageDTO> result = new DTOListResult<StorageDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.netappControllerService.getNetappControllerList(searchParams.getParamsMap()), StorageDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StoragePortDTO> findNetappPort(@WebParam(name = "id") Integer id) {

		DTOResult<StoragePortDTO> result = new DTOResult<StoragePortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.netappPortService.findNetappPort(id);

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			StoragePortDTO dto = BeanMapper.map(netappPort, StoragePortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setNetappBoxDTO(findNetappBox(dto.getNetappBox()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StoragePortDTO> findNetappPortByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<StoragePortDTO> result = new DTOResult<StoragePortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.netappPortService.findNetappPort(searchParams.getParamsMap());

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			StoragePortDTO dto = BeanMapper.map(netappPort, StoragePortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setNetappBoxDTO(findNetappBox(dto.getNetappBox()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNetappPort(@WebParam(name = "netappPortDTO") StoragePortDTO netappPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappPortDTO.getCode());

			Validate.isTrue(comm.netappPortService.findNetappPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			StoragePort netappPort = BeanMapper.map(netappPortDTO, StoragePort.class);

			netappPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappPort.setUser(DEFAULT_USER);
			netappPort.setIdClass(TableNameUtil.getTableName(StoragePort.class));
			netappPort.setId(0);

			BeanValidators.validateWithException(validator, netappPort);

			comm.netappPortService.saveOrUpdate(netappPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortDTO") StoragePortDTO netappPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(netappPortDTO, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.netappPortService.findNetappPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", netappPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.netappPortService.findNetappPort(paramsMap) == null
							|| netappPort.getCode().equals(netappPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappPortDTO, StoragePort.class), netappPort);

			netappPort.setIdClass(TableNameUtil.getTableName(StoragePort.class));
			netappPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, netappPort);

			comm.netappPortService.saveOrUpdate(netappPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.netappPortService.findNetappPort(id);

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			netappPort.setIdClass(TableNameUtil.getTableName(StoragePort.class));

			netappPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappPortService.saveOrUpdate(netappPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StoragePortDTO> getNetappPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<StoragePortDTO> result = new PaginationResult<StoragePortDTO>();

		try {

			return comm.netappPortService.getNetappPortDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StoragePortDTO> getNetappPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<StoragePortDTO> result = new DTOListResult<StoragePortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.netappPortService.getNetappPortList(searchParams.getParamsMap()),
					StoragePortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id) {

		DTOResult<NicDTO> result = new DTOResult<NicDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			NicDTO dto = BeanMapper.map(nic, NicDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());

			// LookUp
			dto.setNicRateText(findLookUp(dto.getNicRate()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicDTO> findNicByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<NicDTO> result = new DTOResult<NicDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(searchParams.getParamsMap());

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			NicDTO dto = BeanMapper.map(nic, NicDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());
			dto.setFimasDTO(findFimas(dto.getFimas()).getDto());

			// LookUp
			dto.setNicRateText(findLookUp(dto.getNicRate()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", nicDTO.getCode());

			Validate.isTrue(comm.nicService.findNic(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Nic nic = BeanMapper.map(nicDTO, Nic.class);

			nic.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nic.setUser(DEFAULT_USER);
			nic.setIdClass(TableNameUtil.getTableName(Nic.class));
			nic.setId(0);

			BeanValidators.validateWithException(validator, nic);

			comm.nicService.saveOrUpdate(nic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "nicDTO") NicDTO nicDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicDTO, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", nicDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicService.findNic(paramsMap) == null || nic.getCode().equals(nicDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicDTO, Nic.class), nic);

			nic.setIdClass(TableNameUtil.getTableName(Nic.class));
			nic.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nic.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, nic);

			comm.nicService.saveOrUpdate(nic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNic(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			nic.setIdClass(TableNameUtil.getTableName(Nic.class));

			nic.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.nicService.saveOrUpdate(nic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<NicDTO> result = new PaginationResult<NicDTO>();

		try {

			return comm.nicService.getNicDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicDTO> getNicList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<NicDTO> result = new DTOListResult<NicDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.nicService.getNicList(searchParams.getParamsMap()), NicDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id) {

		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			NicPortDTO dto = BeanMapper.map(nicPort, NicPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setNicDTO(findNic(dto.getNic()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicPortDTO> findNicPortByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(searchParams.getParamsMap());

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			NicPortDTO dto = BeanMapper.map(nicPort, NicPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setNicDTO(findNic(dto.getNic()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", nicPortDTO.getCode());

			Validate.isTrue(comm.nicPortService.findNicPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			NicPort nicPort = BeanMapper.map(nicPortDTO, NicPort.class);

			nicPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nicPort.setUser(DEFAULT_USER);
			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));
			nicPort.setId(0);

			BeanValidators.validateWithException(validator, nicPort);

			comm.nicPortService.saveOrUpdate(nicPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNicPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", nicPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.nicPortService.findNicPort(paramsMap) == null
							|| nicPort.getCode().equals(nicPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicPortDTO, NicPort.class), nicPort);

			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));
			nicPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nicPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, nicPort);

			comm.nicPortService.saveOrUpdate(nicPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNicPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));

			nicPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.nicPortService.saveOrUpdate(nicPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicPortDTO> getNicPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<NicPortDTO> result = new PaginationResult<NicPortDTO>();

		try {

			return comm.nicPortService.getNicPortDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicPortDTO> getNicPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<NicPortDTO> result = new DTOListResult<NicPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.nicPortService.getNicPortList(searchParams.getParamsMap()),
					NicPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id) {

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
	public DTOResult<ServerDTO> findServerByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", serverDTO.getCode());

			Validate.isTrue(comm.serverService.findServer(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Server server = BeanMapper.map(serverDTO, Server.class);

			server.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			server.setUser(DEFAULT_USER);
			server.setIdClass(TableNameUtil.getTableName(Server.class));
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
	public IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", serverDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.serverService.findServer(paramsMap) == null || server.getCode().equals(serverDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverDTO, Server.class), server);

			server.setIdClass(TableNameUtil.getTableName(Server.class));
			server.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			server.setUser(DEFAULT_USER);

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
	public IdResult deleteServer(@WebParam(name = "id") Integer id) {

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
	public PaginationResult<ServerDTO> getServerPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<ServerDTO> getServerList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id) {

		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO dto = BeanMapper.map(serverPort, ServerPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPortByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(searchParams.getParamsMap());

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO dto = BeanMapper.map(serverPort, ServerPortDTO.class);

			// Reference
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setServerDTO(findServer(dto.getServer()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", serverPortDTO.getCode());

			Validate.isTrue(comm.serverPortService.findServerPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			ServerPort serverPort = BeanMapper.map(serverPortDTO, ServerPort.class);

			serverPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			serverPort.setUser(DEFAULT_USER);
			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));
			serverPort.setId(0);

			BeanValidators.validateWithException(validator, serverPort);

			comm.serverPortService.saveOrUpdate(serverPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", serverPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.serverPortService.findServerPort(paramsMap) == null
							|| serverPort.getCode().equals(serverPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverPortDTO, ServerPort.class), serverPort);

			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));
			serverPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			serverPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, serverPort);

			comm.serverPortService.saveOrUpdate(serverPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServerPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));

			serverPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.serverPortService.saveOrUpdate(serverPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<ServerPortDTO> result = new PaginationResult<ServerPortDTO>();

		try {

			return comm.serverPortService.getServerPortDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerPortDTO> getServerPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<ServerPortDTO> result = new DTOListResult<ServerPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.serverPortService.getServerPortList(searchParams.getParamsMap()),
					ServerPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id) {

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
	public DTOResult<SwitchesDTO> findSwitchesByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", switchesDTO.getCode());

			Validate.isTrue(comm.switchesService.findSwitches(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Switches switches = BeanMapper.map(switchesDTO, Switches.class);

			switches.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switches.setUser(DEFAULT_USER);
			switches.setIdClass(TableNameUtil.getTableName(Switches.class));
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
	public IdResult updateSwitches(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", switchesDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.switchesService.findSwitches(paramsMap) == null
							|| switches.getCode().equals(switchesDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchesDTO, Switches.class), switches);

			switches.setIdClass(TableNameUtil.getTableName(Switches.class));

			switches.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switches.setUser(DEFAULT_USER);

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
	public IdResult deleteSwitches(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Validate.notNull(switches, ERROR.OBJECT_NULL);

			switches.setIdClass(TableNameUtil.getTableName(Switches.class));

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
	public PaginationResult<SwitchesDTO> getSwitchesPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<SwitchesDTO> getSwitchesList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id) {

		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO dto = BeanMapper.map(switchPort, SwitchPortDTO.class);

			// Reference
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchesDTO(findSwitches(dto.getSwitches()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPortByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(searchParams.getParamsMap());

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO dto = BeanMapper.map(switchPort, SwitchPortDTO.class);

			// Reference
			dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setSwitchesDTO(findSwitches(dto.getSwitches()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", switchPortDTO.getCode());

			Validate.isTrue(comm.switchPortService.findSwitchPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			SwitchPort switchPort = BeanMapper.map(switchPortDTO, SwitchPort.class);

			switchPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switchPort.setUser(DEFAULT_USER);
			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));
			switchPort.setId(0);

			BeanValidators.validateWithException(validator, switchPort);

			comm.switchPortService.saveOrUpdate(switchPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", switchPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.switchPortService.findSwitchPort(paramsMap) == null
							|| switchPort.getCode().equals(switchPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchPortDTO, SwitchPort.class), switchPort);

			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));
			switchPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switchPort.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, switchPort);

			comm.switchPortService.saveOrUpdate(switchPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitchPort(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));

			switchPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.switchPortService.saveOrUpdate(switchPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchPortDTO> getSwitchPortPagination(
			@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<SwitchPortDTO> result = new PaginationResult<SwitchPortDTO>();

		try {

			return comm.switchPortService.getSwitchPortDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchPortDTO> getSwitchPortList(@WebParam(name = "searchParams") SearchParams searchParams) {

		DTOListResult<SwitchPortDTO> result = new DTOListResult<SwitchPortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.switchPortService.getSwitchPortList(searchParams.getParamsMap()),
					SwitchPortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id) {

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
	public DTOResult<VlanDTO> findVlanByParams(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", vlanDTO.getCode());

			Validate.isTrue(comm.vlanService.findVlan(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Vlan vlan = BeanMapper.map(vlanDTO, Vlan.class);

			vlan.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));
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
	public IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_code", vlanDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vlanService.findVlan(paramsMap) == null || vlan.getCode().equals(vlanDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vlanDTO, Vlan.class), vlan);

			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));
			vlan.setStatus(CMDBuildConstants.STATUS_UPDATE);
			vlan.setUser(DEFAULT_USER);

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
	public IdResult deleteVlan(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));
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
	public PaginationResult<VlanDTO> getVlanPagination(@WebParam(name = "searchParams") SearchParams searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

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
	public DTOListResult<VlanDTO> getVlanList(@WebParam(name = "searchParams") SearchParams searchParams) {

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
	public IdResult insertVlan(@WebParam(name = "vlanDTOList") List<VlanDTO> vlanDTOList) {

		IdResult result = new IdResult();

		try {

			// 先判断对象是否为空
			Validate.notNull(vlanDTOList, ERROR.INPUT_NULL);

			Integer tempId = 0;
			Integer insertCount = vlanDTOList.size(); // 插入Vlan的数量
			Integer insertSuccessCount = 0; // 成功插入Vlan的数量

			for (VlanDTO vlanDTO : vlanDTOList) {

				Map<String, Object> paramsMap = Maps.newHashMap();

				paramsMap.put("EQ_code", vlanDTO.getCode());

				// 如果code重复,跳过本次loop
				if (comm.vlanService.findVlan(paramsMap) != null) {
					continue;
				}

				Vlan vlan = BeanMapper.map(vlanDTO, Vlan.class);

				vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));
				vlan.setUser(DEFAULT_USER);
				vlan.setId(tempId);

				BeanValidators.validateWithException(validator, vlan);

				comm.vlanService.saveOrUpdate(vlan);

				tempId--;
				insertSuccessCount++;
			}

			String message = "0".equals(insertSuccessCount.toString()) ? "Vlan已存在" : "插入Vlan " + insertCount
					+ " 条,成功创建Vlan " + insertSuccessCount + " 条";

			result.setMessage(message);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

}
