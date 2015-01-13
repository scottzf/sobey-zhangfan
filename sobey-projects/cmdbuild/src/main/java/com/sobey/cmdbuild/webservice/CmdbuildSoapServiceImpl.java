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
import com.sobey.cmdbuild.entity.ConfigFirewallAddress;
import com.sobey.cmdbuild.entity.ConfigFirewallPolicy;
import com.sobey.cmdbuild.entity.ConfigFirewallServiceCategory;
import com.sobey.cmdbuild.entity.ConfigRouterStatic;
import com.sobey.cmdbuild.entity.ConfigSystemInterface;
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
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.FirewallService;
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
import com.sobey.cmdbuild.entity.MapEipDns;
import com.sobey.cmdbuild.entity.MapEipElb;
import com.sobey.cmdbuild.entity.MapRouterFirewallService;
import com.sobey.cmdbuild.entity.MapTagService;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Router;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.Service;
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.entity.StorageBox;
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.entity.Subnet;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.ConfigFirewallAddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.ConfigFirewallPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.ConfigFirewallServiceCategoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.ConfigRouterStaticDTO;
import com.sobey.cmdbuild.webservice.response.dto.ConfigSystemInterfaceDTO;
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
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallServiceDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.LogDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEipDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEcsEs3DTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEipDnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapEipElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapRouterFirewallServiceDTO;
import com.sobey.cmdbuild.webservice.response.dto.MapTagServiceDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.RouterDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServiceDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageDTO;
import com.sobey.cmdbuild.webservice.response.dto.StoragePortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SubnetDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagRelation;
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
	public Integer getMaxAclNumber() {
		return comm.customService.selectMaxAclNumber();
	}

	@Override
	public Integer getMaxPolicyId(Integer tenantsId) {
		return comm.customService.selectMaxPolicyId(tenantsId);
	}

	@Override
	public Integer getMaxRouterId(Integer tenantsId) {
		return comm.customService.selectMaxRouterId(tenantsId);
	}

	@Override
	public Integer getMaxVlanId(Integer nicId, Integer subnetId) {
		return comm.customService.selectMaxVlanId(nicId, subnetId);
	}

	@Override
	public Integer getMaxPortIndex(Integer tenantsId) {
		return comm.customService.selectPortIndex(tenantsId);
	}

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

			// 删除Tag,同时也需要将Tag和其他服务资源的关联关系也一并删除掉.

			Map<String, Object> tagParamsMap = Maps.newHashMap();
			tagParamsMap.put("EQ_idObj1", id);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(tagParamsMap);

			for (MapTagService map : tags) {
				deleteMapTagService(id, map.getIdObj2());
			}

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
	public List<TagRelation> getTagRelation(Integer serviceId) {
		return comm.customService.getTagRelation(serviceId);
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
			paramsMap.put("EQ_imageName", ecsSpecDTO.getDescription());

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
			paramsMap.put("EQ_imageName", ecsSpecDTO.getDescription());

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

			// // Reference
			// dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			// dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			// dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			// dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			// dto.setServerDTO(findServer(dto.getServer()).getDto());
			//
			// // LookUp
			// dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			// dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());
			// dto.setEcsTypeText(findLookUp(dto.getEcsType()).getDto().getDescription());

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
			// dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			// dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			// dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			// dto.setEcsSpecDTO(findEcsSpec(dto.getEcsSpec()).getDto());
			// dto.setServerDTO(findServer(dto.getServer()).getDto());

			// LookUp
			// dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			// dto.setEcsStatusText(findLookUp(dto.getEcsStatus()).getDto().getDescription());
			// dto.setEcsTypeText(findLookUp(dto.getEcsType()).getDto().getDescription());

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

			// 验证ipaddress是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_ipaddress", ecsDTO.getIpaddress());
			paramsMap.put("EQ_tenants", ecsDTO.getTenants());

			Validate.isTrue(comm.ecsService.findEcs(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象s
			Ecs ecs = BeanMapper.map(ecsDTO, Ecs.class);

			String code = "ECS-" + Identities.randomBase62(8);
			ecs.setCode(code);
			ecs.setUser(DEFAULT_USER);
			ecs.setId(0);

			BeanValidators.validateWithException(validator, ecs);

			comm.ecsService.saveOrUpdate(ecs);

			result.setMessage(code);
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

			// 验证ipaddress是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_ipaddress", ecsDTO.getIpaddress());
			paramsMap.put("EQ_tenants", ecsDTO.getTenants());

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

			// 删除Ecs,同时也需要将Ecs和其他服务资源的关联关系也一并删除掉.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", id);

			List<MapEcsEip> eips = comm.mapEcsEipService.getMapEcsEipList(paramsMap);
			List<MapEcsElb> elbs = comm.mapEcsElbService.getMapEcsElbList(paramsMap);
			List<MapEcsEs3> es3s = comm.mapEcsEs3Service.getMapEcsEs3List(paramsMap);

			Map<String, Object> tagParamsMap = Maps.newHashMap();
			tagParamsMap.put("EQ_idObj2", id);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(tagParamsMap);

			for (MapEcsEip map : eips) {
				deleteMapEcsEip(id, map.getIdObj2());
			}

			for (MapEcsElb map : elbs) {
				deleteMapEcsElb(id, map.getIdObj2());
			}

			for (MapEcsEs3 map : es3s) {
				deleteMapEcsEs3(id, map.getIdObj2());
			}

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

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

		DTOResult<Es3DTO> result = new DTOResult<Es3DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3 es3 = comm.es3Service.findEs3(id);

			Validate.notNull(es3, ERROR.OBJECT_NULL);

			Es3DTO dto = BeanMapper.map(es3, Es3DTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setStorageDTO(findStorage(dto.getStorage()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEs3TypeText(findLookUp(dto.getEs3Type()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3DTO> findEs3ByParams(SearchParams searchParams) {

		DTOResult<Es3DTO> result = new DTOResult<Es3DTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Es3 es3 = comm.es3Service.findEs3(searchParams.getParamsMap());

			Validate.notNull(es3, ERROR.OBJECT_NULL);

			Es3DTO dto = BeanMapper.map(es3, Es3DTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setStorageDTO(findStorage(dto.getStorage()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEs3TypeText(findLookUp(dto.getEs3Type()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEs3(Es3DTO es3DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3DTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", es3DTO.getDescription());
			paramsMap.put("EQ_tenants", es3DTO.getTenants());

			Validate.isTrue(comm.es3Service.findEs3(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			String code = "ES3-" + Identities.randomBase62(8);
			Es3 es3 = BeanMapper.map(es3DTO, Es3.class);
			es3.setCode(code);
			es3.setUser(DEFAULT_USER);
			es3.setId(0);

			BeanValidators.validateWithException(validator, es3);

			comm.es3Service.saveOrUpdate(es3);

			result.setMessage(code);
			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEs3(Integer id, Es3DTO es3DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3DTO, ERROR.INPUT_NULL);

			Es3 es3 = comm.es3Service.findEs3(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", es3DTO.getDescription());
			paramsMap.put("EQ_tenants", es3DTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.es3Service.findEs3(paramsMap) == null || es3.getDescription().equals(es3DTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(es3DTO, Es3.class), es3);

			es3.setUser(DEFAULT_USER);
			es3.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			es3.setIdClass(TableNameUtil.getTableName(Es3.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, es3);

			comm.es3Service.saveOrUpdate(es3);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEs3(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			// 删除Es3,同时也需要将Es3和其他服务资源的关联关系也一并删除掉.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj2", id);
			List<MapEcsEs3> es3List = comm.mapEcsEs3Service.getMapEcsEs3List(paramsMap);

			Map<String, Object> tagParamsMap = Maps.newHashMap();
			tagParamsMap.put("EQ_idObj2", id);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(tagParamsMap);

			for (MapEcsEs3 map : es3List) {
				deleteMapEcsEs3(map.getIdObj1(), id);
			}

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

			Es3 es3 = comm.es3Service.findEs3(id);

			Validate.notNull(es3, ERROR.OBJECT_NULL);

			es3.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.es3Service.saveOrUpdate(es3);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3DTO> getEs3List(SearchParams searchParams) {

		DTOListResult<Es3DTO> result = new DTOListResult<Es3DTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.es3Service.getEs3List(searchParams.getParamsMap()), Es3DTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3DTO> getEs3Pagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<Es3DTO> result = new PaginationResult<Es3DTO>();

		try {

			return comm.es3Service.getEs3DTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipDTO> findEip(Integer id) {

		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setRouterDTO(findRouter(dto.getRouter()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEipStatusText(findLookUp(dto.getEipStatus()).getDto().getDescription());
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
	public DTOResult<EipDTO> findEipByParams(SearchParams searchParams) {

		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(searchParams.getParamsMap());

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setRouterDTO(findRouter(dto.getRouter()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
			dto.setEipStatusText(findLookUp(dto.getEipStatus()).getDto().getDescription());
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
	public IdResult createEip(EipDTO eipDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", eipDTO.getDescription());
			paramsMap.put("EQ_tenants", eipDTO.getTenants());

			Validate.isTrue(comm.eipService.findEip(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Eip eip = BeanMapper.map(eipDTO, Eip.class);
			eip.setCode("EIP-" + Identities.randomBase62(8));
			eip.setUser(DEFAULT_USER);
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
	public IdResult updateEip(Integer id, EipDTO eipDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipDTO, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", eipDTO.getDescription());
			paramsMap.put("EQ_tenants", eipDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.eipService.findEip(paramsMap) == null || eip.getDescription().equals(eipDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipDTO, Eip.class), eip);

			eip.setUser(DEFAULT_USER);
			eip.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eip.setIdClass(TableNameUtil.getTableName(Eip.class));

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
	public IdResult deleteEip(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			// 删除Eip,同时也需要将Eip和其他服务资源的关联关系也一并删除掉.

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", id);
			List<MapEipDns> dnsList = comm.mapEipDnsService.getMapEipDnsList(paramsMap);
			List<MapEipElb> elbs = comm.mapEipElbService.getMapEipElbList(paramsMap);

			Map<String, Object> paramsMap2 = Maps.newHashMap();
			paramsMap2.put("EQ_idObj2", id);
			List<MapEcsEip> eips = comm.mapEcsEipService.getMapEcsEipList(paramsMap2);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(paramsMap2);

			for (MapEcsEip map : eips) {
				deleteMapEcsEip(map.getIdObj1(), id);
			}

			for (MapEipElb map : elbs) {
				deleteMapEipElb(id, map.getIdObj2());
			}

			for (MapEipDns map : dnsList) {
				deleteMapEipDns(id, map.getIdObj2());
			}

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

			Eip eip = comm.eipService.findEip(id);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

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
	public DTOListResult<EipDTO> getEipList(SearchParams searchParams) {

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
	public PaginationResult<EipDTO> getEipPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

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
	public DTOResult<ElbDTO> findElb(Integer id) {

		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbDTO> findElbByParams(SearchParams searchParams) {

		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(searchParams.getParamsMap());

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createElb(ElbDTO elbDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", elbDTO.getDescription());
			paramsMap.put("EQ_tenants", elbDTO.getTenants());

			Validate.isTrue(comm.elbService.findElb(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Elb elb = BeanMapper.map(elbDTO, Elb.class);
			elb.setCode("ELB-" + Identities.randomBase62(8));
			elb.setUser(DEFAULT_USER);
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
	public IdResult updateElb(Integer id, ElbDTO elbDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbDTO, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", elbDTO.getDescription());
			paramsMap.put("EQ_tenants", elbDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.elbService.findElb(paramsMap) == null || elb.getDescription().equals(elbDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(elbDTO, Elb.class), elb);

			elb.setUser(DEFAULT_USER);
			elb.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elb.setIdClass(TableNameUtil.getTableName(Elb.class));

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
	public IdResult deleteElb(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			// 删除Elb,同时也需要将Elb和其他服务资源的关联关系也一并删除掉.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj2", id);

			List<MapEipElb> eips = comm.mapEipElbService.getMapEipElbList(paramsMap);
			List<MapEcsElb> ecsList = comm.mapEcsElbService.getMapEcsElbList(paramsMap);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(paramsMap);

			for (MapEipElb map : eips) {
				deleteMapEipElb(map.getIdObj1(), id);
			}

			for (MapEcsElb map : ecsList) {
				deleteMapEcsElb(map.getIdObj1(), id);
			}

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

			Elb elb = comm.elbService.findElb(id);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

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
	public DTOListResult<ElbDTO> getElbList(SearchParams searchParams) {

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
	public PaginationResult<ElbDTO> getElbPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

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
	public DTOResult<DnsDTO> findDns(Integer id) {

		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
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
	public DTOResult<DnsDTO> findDnsByParams(SearchParams searchParams) {

		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(searchParams.getParamsMap());

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setAgentTypeText(findLookUp(dto.getAgentType()).getDto().getDescription());
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
	public IdResult createDns(DnsDTO dnsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", dnsDTO.getDescription());
			paramsMap.put("EQ_tenants", dnsDTO.getTenants());

			Validate.isTrue(comm.dnsService.findDns(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Dns dns = BeanMapper.map(dnsDTO, Dns.class);
			dns.setCode("DNS-" + Identities.randomBase62(8));
			dns.setUser(DEFAULT_USER);
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
	public IdResult updateDns(Integer id, DnsDTO dnsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", dnsDTO.getDescription());
			paramsMap.put("EQ_tenants", dnsDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.dnsService.findDns(paramsMap) == null || dns.getDescription().equals(dnsDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(dnsDTO, Dns.class), dns);

			dns.setUser(DEFAULT_USER);
			dns.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			dns.setIdClass(TableNameUtil.getTableName(Dns.class));

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
	public IdResult deleteDns(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			// 删除Dns,同时也需要将Dns和其他服务资源的关联关系也一并删除掉.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj2", id);

			List<MapEipDns> dnsList = comm.mapEipDnsService.getMapEipDnsList(paramsMap);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(paramsMap);

			for (MapEipDns map : dnsList) {
				deleteMapEipDns(map.getIdObj1(), id);
			}

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

			Dns dns = comm.dnsService.findDns(id);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

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
	public DTOListResult<DnsDTO> getDnsList(SearchParams searchParams) {

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
	public PaginationResult<DnsDTO> getDnsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

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
	public DTOResult<VpnDTO> findVpn(Integer id) {

		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

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
	public DTOResult<VpnDTO> findVpnByParams(SearchParams searchParams) {
		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(searchParams.getParamsMap());

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

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
	public IdResult createVpn(VpnDTO vpnDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);

			// 验证同一租户下description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", vpnDTO.getDescription());
			paramsMap.put("EQ_tenants", vpnDTO.getTenants());

			Validate.isTrue(comm.vpnService.findVpn(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Vpn vpn = BeanMapper.map(vpnDTO, Vpn.class);
			vpn.setCode("VPN-" + Identities.randomBase62(8));
			vpn.setUser(DEFAULT_USER);
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
	public IdResult updateVpn(Integer id, VpnDTO vpnDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			// 验证同一租户下description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", vpnDTO.getDescription());
			paramsMap.put("EQ_tenants", vpnDTO.getTenants());

			Validate.isTrue(
					comm.vpnService.findVpn(paramsMap) == null || vpn.getDescription().equals(vpnDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vpnDTO, Vpn.class), vpn);

			vpn.setUser(DEFAULT_USER);
			vpn.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));

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
	public IdResult deleteVpn(Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			// 删除VPN,同时也需要将VPN和其他服务资源的关联关系也一并删除掉.

			Map<String, Object> tagParamsMap = Maps.newHashMap();
			tagParamsMap.put("EQ_idObj2", id);
			List<MapTagService> tags = comm.mapTagServiceService.getMapTagServiceList(tagParamsMap);

			for (MapTagService map : tags) {
				deleteMapTagService(map.getIdObj1(), id);
			}

			Vpn vpn = comm.vpnService.findVpn(id);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

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
	public DTOListResult<VpnDTO> getVpnList(SearchParams searchParams) {

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
	public PaginationResult<VpnDTO> getVpnPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

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
	public DTOResult<EipPolicyDTO> findEipPolicy(Integer id) {

		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			EipPolicyDTO dto = BeanMapper.map(eipPolicy, EipPolicyDTO.class);

			// LookUp
			dto.setEipProtocolText(findLookUp(dto.getEipProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicyByParams(SearchParams searchParams) {

		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(searchParams.getParamsMap());

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

			EipPolicyDTO dto = BeanMapper.map(eipPolicy, EipPolicyDTO.class);

			// LookUp
			dto.setEipProtocolText(findLookUp(dto.getEipProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipPolicy(EipPolicyDTO eipPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);

			// 将DTO对象转换至Entity对象
			EipPolicy eipPolicy = BeanMapper.map(eipPolicyDTO, EipPolicy.class);
			eipPolicy.setCode("EIPPolicy-" + Identities.randomBase62(8));
			eipPolicy.setUser(DEFAULT_USER);
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
	public IdResult updateEipPolicy(Integer id, EipPolicyDTO eipPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);

			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);

			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);

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
	public IdResult deleteEipPolicy(Integer id) {

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
	public DTOListResult<EipPolicyDTO> getEipPolicyList(SearchParams searchParams) {

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
	public PaginationResult<EipPolicyDTO> getEipPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<ElbPolicyDTO> findElbPolicy(Integer id) {

		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			ElbPolicyDTO dto = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);

			// LookUp
			dto.setElbProtocolText(findLookUp(dto.getElbProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicyByParams(SearchParams searchParams) {

		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(searchParams.getParamsMap());

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			ElbPolicyDTO dto = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);

			// LookUp
			dto.setElbProtocolText(findLookUp(dto.getElbProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createElbPolicy(ElbPolicyDTO elbPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);

			// 将DTO对象转换至Entity对象
			ElbPolicy elbPolicy = BeanMapper.map(elbPolicyDTO, ElbPolicy.class);
			elbPolicy.setCode("ELBPolicy-" + Identities.randomBase62(8));
			elbPolicy.setUser(DEFAULT_USER);
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
	public IdResult updateElbPolicy(Integer id, ElbPolicyDTO elbPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);

			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);

			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(elbPolicyDTO, ElbPolicy.class), elbPolicy);

			elbPolicy.setUser(DEFAULT_USER);
			elbPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elbPolicy.setIdClass(TableNameUtil.getTableName(ElbPolicy.class));

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
	public IdResult deleteElbPolicy(Integer id) {

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
	public DTOListResult<ElbPolicyDTO> getElbPolicyList(SearchParams searchParams) {

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
	public PaginationResult<ElbPolicyDTO> getElbPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<DnsPolicyDTO> findDnsPolicy(Integer id) {

		DTOResult<DnsPolicyDTO> result = new DTOResult<DnsPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(id);

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			DnsPolicyDTO dto = BeanMapper.map(dnsPolicy, DnsPolicyDTO.class);

			// LookUp
			dto.setDnsProtocolText(findLookUp(dto.getDnsProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsPolicyDTO> findDnsPolicyByParams(SearchParams searchParams) {

		DTOResult<DnsPolicyDTO> result = new DTOResult<DnsPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(searchParams.getParamsMap());

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

			DnsPolicyDTO dto = BeanMapper.map(dnsPolicy, DnsPolicyDTO.class);

			// LookUp
			dto.setDnsProtocolText(findLookUp(dto.getDnsProtocol()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDnsPolicy(DnsPolicyDTO dnsPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsPolicyDTO, ERROR.INPUT_NULL);

			// 将DTO对象转换至Entity对象
			DnsPolicy dnsPolicy = BeanMapper.map(dnsPolicyDTO, DnsPolicy.class);
			dnsPolicy.setCode("DNSPolicy-" + Identities.randomBase62(8));
			dnsPolicy.setUser(DEFAULT_USER);
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
	public IdResult updateDnsPolicy(Integer id, DnsPolicyDTO dnsPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(dnsPolicyDTO, ERROR.INPUT_NULL);

			DnsPolicy dnsPolicy = comm.dnsPolicyService.findDnsPolicy(id);

			Validate.notNull(dnsPolicy, ERROR.OBJECT_NULL);

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
	public IdResult deleteDnsPolicy(Integer id) {

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
	public DTOListResult<DnsPolicyDTO> getDnsPolicyList(SearchParams searchParams) {

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
	public PaginationResult<DnsPolicyDTO> getDnsPolicyPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<MapEcsEs3DTO> findMapEcsEs3(Integer id) {

		DTOResult<MapEcsEs3DTO> result = new DTOResult<MapEcsEs3DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapEcsEs3 map = comm.mapEcsEs3Service.findMapEcsEs3(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsEs3DTO dto = BeanMapper.map(map, MapEcsEs3DTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOResult<MapEcsEs3DTO> findMapEcsEs3ByParams(SearchParams searchParams) {

		DTOResult<MapEcsEs3DTO> result = new DTOResult<MapEcsEs3DTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapEcsEs3 map = comm.mapEcsEs3Service.findMapEcsEs3(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsEs3DTO dto = BeanMapper.map(map, MapEcsEs3DTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsEs3(Integer ecsId, Integer es3Id) {

		IdResult result = new IdResult();

		try {

			MapEcsEs3 map = new MapEcsEs3();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(es3Id);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Es3.class));
			map.setUser(DEFAULT_USER);

			comm.mapEcsEs3Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsEs3(Integer ecsId, Integer es3Id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsId, ERROR.INPUT_NULL);
			Validate.notNull(es3Id, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", ecsId);
			paramsMap.put("EQ_idObj2", es3Id);

			MapEcsEs3 map = comm.mapEcsEs3Service.findMapEcsEs3(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapEcsEs3Service.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MapEcsEs3DTO> getMapEcsEs3List(SearchParams searchParams) {

		DTOListResult<MapEcsEs3DTO> result = new DTOListResult<MapEcsEs3DTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.mapEcsEs3Service.getMapEcsEs3List(searchParams.getParamsMap()),
					MapEcsEs3DTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapEcsEs3DTO> getMapEcsEs3Pagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapEcsEs3DTO> result = new PaginationResult<MapEcsEs3DTO>();

		try {

			return comm.mapEcsEs3Service.getMapEcsEs3DTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEcsEipDTO> findMapEcsEip(Integer id) {

		DTOResult<MapEcsEipDTO> result = new DTOResult<MapEcsEipDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapEcsEip map = comm.mapEcsEipService.findMapEcsEip(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsEipDTO dto = BeanMapper.map(map, MapEcsEipDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEcsEipDTO> findMapEcsEipByParams(SearchParams searchParams) {

		DTOResult<MapEcsEipDTO> result = new DTOResult<MapEcsEipDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapEcsEip map = comm.mapEcsEipService.findMapEcsEip(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsEipDTO dto = BeanMapper.map(map, MapEcsEipDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsEip(Integer ecsId, Integer eipId) {

		IdResult result = new IdResult();

		try {

			MapEcsEip map = new MapEcsEip();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(eipId);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Eip.class));
			map.setUser(DEFAULT_USER);

			comm.mapEcsEipService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsEip(Integer ecsId, Integer eipId) {

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
	public DTOListResult<MapEcsEipDTO> getMapEcsEipList(SearchParams searchParams) {

		DTOListResult<MapEcsEipDTO> result = new DTOListResult<MapEcsEipDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.mapEcsEipService.getMapEcsEipList(searchParams.getParamsMap()),
					MapEcsEipDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapEcsEipDTO> getMapEcsEipPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapEcsEipDTO> result = new PaginationResult<MapEcsEipDTO>();

		try {

			return comm.mapEcsEipService.getMapEcsEipDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEcsElbDTO> findMapEcsElb(Integer id) {

		DTOResult<MapEcsElbDTO> result = new DTOResult<MapEcsElbDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapEcsElb map = comm.mapEcsElbService.findMapEcsElb(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsElbDTO dto = BeanMapper.map(map, MapEcsElbDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEcsElbDTO> findMapEcsElbByParams(SearchParams searchParams) {

		DTOResult<MapEcsElbDTO> result = new DTOResult<MapEcsElbDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapEcsElb map = comm.mapEcsElbService.findMapEcsElb(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEcsElbDTO dto = BeanMapper.map(map, MapEcsElbDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEcsElb(Integer ecsId, Integer elbId) {

		IdResult result = new IdResult();

		try {

			MapEcsElb map = new MapEcsElb();

			map.setId(0);
			map.setIdObj1(ecsId);
			map.setIdObj2(elbId);
			map.setIdClass1(TableNameUtil.getTableName(Ecs.class));
			map.setIdClass2(TableNameUtil.getTableName(Elb.class));
			map.setUser(DEFAULT_USER);

			comm.mapEcsElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEcsElb(Integer ecsId, Integer elbId) {

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
	public DTOListResult<MapEcsElbDTO> getMapEcsElbList(SearchParams searchParams) {

		DTOListResult<MapEcsElbDTO> result = new DTOListResult<MapEcsElbDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.mapEcsElbService.getMapEcsElbList(searchParams.getParamsMap()),
					MapEcsElbDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapEcsElbDTO> getMapEcsElbPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapEcsElbDTO> result = new PaginationResult<MapEcsElbDTO>();

		try {

			return comm.mapEcsElbService.getMapEcsElbDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEipElbDTO> findMapEipElb(Integer id) {

		DTOResult<MapEipElbDTO> result = new DTOResult<MapEipElbDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapEipElb map = comm.mapEipElbService.findMapEipElbDao(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEipElbDTO dto = BeanMapper.map(map, MapEipElbDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEipElbDTO> findMapEipElbByParams(SearchParams searchParams) {

		DTOResult<MapEipElbDTO> result = new DTOResult<MapEipElbDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapEipElb map = comm.mapEipElbService.findMapEipElb(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEipElbDTO dto = BeanMapper.map(map, MapEipElbDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEipElb(Integer eipId, Integer elbId) {

		IdResult result = new IdResult();

		try {

			MapEipElb map = new MapEipElb();

			map.setId(0);
			map.setIdObj1(eipId);
			map.setIdObj2(elbId);
			map.setIdClass1(TableNameUtil.getTableName(Eip.class));
			map.setIdClass2(TableNameUtil.getTableName(Elb.class));
			map.setUser(DEFAULT_USER);

			comm.mapEipElbService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEipElb(Integer eipId, Integer elbId) {

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
	public DTOListResult<MapEipElbDTO> getMapEipElbList(SearchParams searchParams) {

		DTOListResult<MapEipElbDTO> result = new DTOListResult<MapEipElbDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.mapEipElbService.getMapEipElbList(searchParams.getParamsMap()),
					MapEipElbDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapEipElbDTO> getMapEipElbPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapEipElbDTO> result = new PaginationResult<MapEipElbDTO>();

		try {

			return comm.mapEipElbService.getMapEipElbDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEipDnsDTO> findMapEipDns(Integer id) {

		DTOResult<MapEipDnsDTO> result = new DTOResult<MapEipDnsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapEipDns map = comm.mapEipDnsService.findMapEipDns(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEipDnsDTO dto = BeanMapper.map(map, MapEipDnsDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapEipDnsDTO> findMapEipDnsByParams(SearchParams searchParams) {

		DTOResult<MapEipDnsDTO> result = new DTOResult<MapEipDnsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapEipDns map = comm.mapEipDnsService.findMapEipDns(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapEipDnsDTO dto = BeanMapper.map(map, MapEipDnsDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapEipDns(Integer eipId, Integer dnsId) {

		IdResult result = new IdResult();

		try {

			MapEipDns map = new MapEipDns();

			map.setId(0);
			map.setIdObj1(eipId);
			map.setIdObj2(dnsId);
			map.setIdClass1(TableNameUtil.getTableName(Eip.class));
			map.setIdClass2(TableNameUtil.getTableName(Dns.class));
			map.setUser(DEFAULT_USER);

			comm.mapEipDnsService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapEipDns(Integer eipId, Integer dnsId) {

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
	public DTOListResult<MapEipDnsDTO> getMapEipDnsList(SearchParams searchParams) {

		DTOListResult<MapEipDnsDTO> result = new DTOListResult<MapEipDnsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.mapEipDnsService.getMapEipDnsList(searchParams.getParamsMap()),
					MapEipDnsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapEipDnsDTO> getMapEipDnsPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapEipDnsDTO> result = new PaginationResult<MapEipDnsDTO>();

		try {

			return comm.mapEipDnsService.getMapEipDnsDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapTagServiceDTO> findMapTagService(Integer id) {

		DTOResult<MapTagServiceDTO> result = new DTOResult<MapTagServiceDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapTagService map = comm.mapTagServiceService.findMapTagService(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapTagServiceDTO dto = BeanMapper.map(map, MapTagServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapTagServiceDTO> findMapTagServiceByParams(SearchParams searchParams) {

		DTOResult<MapTagServiceDTO> result = new DTOResult<MapTagServiceDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapTagService map = comm.mapTagServiceService.findMapTagService(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapTagServiceDTO dto = BeanMapper.map(map, MapTagServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapTagService(Integer tagId, Integer serviceId) {

		IdResult result = new IdResult();

		try {

			MapTagService map = new MapTagService();

			EcsDTO ecsDTO = findEcs(serviceId).getDto();
			Es3DTO es3dto = findEs3(serviceId).getDto();
			ElbDTO elbDTO = findElb(serviceId).getDto();
			EipDTO eipDTO = findEip(serviceId).getDto();
			DnsDTO dnsDTO = findDns(serviceId).getDto();

			if (ecsDTO != null) {
				map.setIdClass2(Ecs.class.getSimpleName());
			} else if (es3dto != null) {
				map.setIdClass2(Es3.class.getSimpleName());
			} else if (elbDTO != null) {
				map.setIdClass2(Elb.class.getSimpleName());
			} else if (eipDTO != null) {
				map.setIdClass2(Eip.class.getSimpleName());
			} else if (dnsDTO != null) {
				map.setIdClass2(Dns.class.getSimpleName());
			} else {
				System.out.println("没有找到资源!");
			}

			map.setId(0);
			map.setIdObj1(tagId);
			map.setIdObj2(serviceId);
			map.setIdClass1(TableNameUtil.getTableName(Tag.class));
			map.setUser(DEFAULT_USER);

			comm.mapTagServiceService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMapTagService(Integer tagId, Integer serviceId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(tagId, ERROR.INPUT_NULL);
			Validate.notNull(serviceId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", tagId);
			paramsMap.put("EQ_idObj2", serviceId);

			MapTagService map = comm.mapTagServiceService.findMapTagService(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapTagServiceService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MapTagServiceDTO> getMapTagServiceList(SearchParams searchParams) {

		DTOListResult<MapTagServiceDTO> result = new DTOListResult<MapTagServiceDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.mapTagServiceService.getMapTagServiceList(searchParams.getParamsMap()), MapTagServiceDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapTagServiceDTO> getMapTagServicePagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<MapTagServiceDTO> result = new PaginationResult<MapTagServiceDTO>();

		try {

			return comm.mapTagServiceService.getMapTagServiceDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapRouterFirewallServiceDTO> findMapRouterFirewallService(Integer id) {

		DTOResult<MapRouterFirewallServiceDTO> result = new DTOResult<MapRouterFirewallServiceDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			MapRouterFirewallService map = comm.mapRouterFirewallServiceService.findMapRouterFirewallService(id);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapRouterFirewallServiceDTO dto = BeanMapper.map(map, MapRouterFirewallServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MapRouterFirewallServiceDTO> findMapRouterFirewallServiceByParams(SearchParams searchParams) {

		DTOResult<MapRouterFirewallServiceDTO> result = new DTOResult<MapRouterFirewallServiceDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			MapRouterFirewallService map = comm.mapRouterFirewallServiceService
					.findMapRouterFirewallService(searchParams.getParamsMap());

			Validate.notNull(map, ERROR.OBJECT_NULL);

			MapRouterFirewallServiceDTO dto = BeanMapper.map(map, MapRouterFirewallServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMapRouterFirewallService(Integer routerId, Integer firewallServiceId) {

		IdResult result = new IdResult();

		try {

			MapRouterFirewallService map = new MapRouterFirewallService();

			map.setId(0);
			map.setIdObj1(routerId);
			map.setIdObj2(firewallServiceId);
			map.setIdClass1(TableNameUtil.getTableName(Router.class));
			map.setIdClass2(TableNameUtil.getTableName(FirewallService.class));
			map.setUser(DEFAULT_USER);

			comm.mapRouterFirewallServiceService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult deleteMapRouterFirewallService(Integer routerId, Integer firewallServiceId) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(routerId, ERROR.INPUT_NULL);
			Validate.notNull(firewallServiceId, ERROR.INPUT_NULL);

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_idObj1", routerId);
			paramsMap.put("EQ_idObj2", firewallServiceId);

			MapRouterFirewallService map = comm.mapRouterFirewallServiceService.findMapRouterFirewallService(paramsMap);

			Validate.notNull(map, ERROR.OBJECT_NULL);

			map.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.mapRouterFirewallServiceService.saveOrUpdate(map);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MapRouterFirewallServiceDTO> getMapRouterFirewallServiceList(SearchParams searchParams) {

		DTOListResult<MapRouterFirewallServiceDTO> result = new DTOListResult<MapRouterFirewallServiceDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.mapRouterFirewallServiceService.getMapRouterFirewallServiceList(searchParams.getParamsMap()),
					MapRouterFirewallServiceDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MapRouterFirewallServiceDTO> getMapRouterFirewallServicePagination(
			SearchParams searchParams, Integer pageNumber, Integer pageSize) {

		PaginationResult<MapRouterFirewallServiceDTO> result = new PaginationResult<MapRouterFirewallServiceDTO>();

		try {

			return comm.mapRouterFirewallServiceService.getMapRouterFirewallServiceDTOPagination(
					searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDisk(Integer id) {

		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO dto = BeanMapper.map(hardDisk, HardDiskDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

			// LookUp
			dto.setRotationalSpeedText(findLookUp(dto.getRotationalSpeed()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
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
	public DTOResult<HardDiskDTO> findHardDiskByParams(SearchParams searchParams) {

		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(searchParams.getParamsMap());

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO dto = BeanMapper.map(hardDisk, HardDiskDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

			// LookUp
			dto.setRotationalSpeedText(findLookUp(dto.getRotationalSpeed()).getDto().getDescription());
			dto.setBrandText(findLookUp(dto.getBrand()).getDto().getDescription());
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
	public IdResult createHardDisk(HardDiskDTO hardDiskDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", hardDiskDTO.getDescription());

			Validate.isTrue(comm.hardDiskService.findHardDisk(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			HardDisk hardDisk = BeanMapper.map(hardDiskDTO, HardDisk.class);
			hardDisk.setCode("HardDisk-" + Identities.randomBase62(8));
			hardDisk.setUser(DEFAULT_USER);
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
	public IdResult updateHardDisk(Integer id, HardDiskDTO hardDiskDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", hardDiskDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.hardDiskService.findHardDisk(paramsMap) == null
							|| hardDisk.getDescription().equals(hardDiskDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(hardDiskDTO, HardDisk.class), hardDisk);
			hardDisk.setUser(DEFAULT_USER);
			hardDisk.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));

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
	public IdResult deleteHardDisk(Integer id) {

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
	public DTOListResult<HardDiskDTO> getHardDiskList(SearchParams searchParams) {

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
	public PaginationResult<HardDiskDTO> getHardDiskPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<MemoryDTO> findMemory(Integer id) {

		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO dto = BeanMapper.map(memory, MemoryDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}
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
	public DTOResult<MemoryDTO> findMemoryByParams(SearchParams searchParams) {

		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(searchParams.getParamsMap());

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO dto = BeanMapper.map(memory, MemoryDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

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
	public IdResult createMemory(MemoryDTO memoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", memoryDTO.getDescription());

			Validate.isTrue(comm.memoryService.findMemory(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Memory memory = BeanMapper.map(memoryDTO, Memory.class);
			memory.setCode("Memory-" + Identities.randomBase62(8));
			memory.setUser(DEFAULT_USER);
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
	public IdResult updateMemory(Integer id, MemoryDTO memoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", memoryDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.memoryService.findMemory(paramsMap) == null
							|| memory.getDescription().equals(memoryDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(memoryDTO, Memory.class), memory);

			memory.setUser(DEFAULT_USER);
			memory.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			memory.setIdClass(TableNameUtil.getTableName(Memory.class));

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
	public IdResult deleteMemory(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

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
	public DTOListResult<MemoryDTO> getMemoryList(SearchParams searchParams) {

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
	public PaginationResult<MemoryDTO> getMemoryPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<NicDTO> findNic(Integer id) {

		DTOResult<NicDTO> result = new DTOResult<NicDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			NicDTO dto = BeanMapper.map(nic, NicDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

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
	public DTOResult<NicDTO> findNicByParams(SearchParams searchParams) {

		DTOResult<NicDTO> result = new DTOResult<NicDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(searchParams.getParamsMap());

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			NicDTO dto = BeanMapper.map(nic, NicDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

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
	public IdResult createNic(NicDTO nicDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", nicDTO.getDescription());

			Validate.isTrue(comm.nicService.findNic(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			Nic nic = BeanMapper.map(nicDTO, Nic.class);
			nic.setCode("Nic-" + Identities.randomBase62(8));
			nic.setUser(DEFAULT_USER);
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
	public IdResult updateNic(Integer id, NicDTO nicDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicDTO, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", nicDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.nicService.findNic(paramsMap) == null || nic.getDescription().equals(nicDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicDTO, Nic.class), nic);

			nic.setUser(DEFAULT_USER);
			nic.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nic.setIdClass(TableNameUtil.getTableName(Nic.class));

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
	public IdResult deleteNic(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Validate.notNull(nic, ERROR.OBJECT_NULL);

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
	public DTOListResult<NicDTO> getNicList(SearchParams searchParams) {

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
	public PaginationResult<NicDTO> getNicPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

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
	public DTOResult<StorageBoxDTO> findStorageBox(Integer id) {

		DTOResult<StorageBoxDTO> result = new DTOResult<StorageBoxDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StorageBox storageBox = comm.storageBoxService.findStorageBox(id);

			Validate.notNull(storageBox, ERROR.OBJECT_NULL);

			StorageBoxDTO dto = BeanMapper.map(storageBox, StorageBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());
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
	public DTOResult<StorageBoxDTO> findStorageBoxByParams(SearchParams searchParams) {

		DTOResult<StorageBoxDTO> result = new DTOResult<StorageBoxDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.storageBoxService.findStorageBox(searchParams.getParamsMap());

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			StorageBoxDTO dto = BeanMapper.map(netappBox, StorageBoxDTO.class);

			// Reference
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setRackDTO(findRack(dto.getRack()).getDto());
			if (dto.getServer() != null) {
				dto.setServerDTO(findServer(dto.getServer()).getDto());
			} else if (dto.getStorage() != null) {
				dto.setStorageDTO(findStorage(dto.getStorage()).getDto());
			}

			// LookUp
			dto.setDiskTypeText(findLookUp(dto.getDiskType()).getDto().getDescription());
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
	public IdResult createStorageBox(StorageBoxDTO storageBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storageBoxDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.
			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storageBoxDTO.getDescription());

			Validate.isTrue(comm.storageBoxService.findStorageBox(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			StorageBox storageBox = BeanMapper.map(storageBoxDTO, StorageBox.class);
			storageBox.setCode("StorageBox-" + Identities.randomBase62(8));
			storageBox.setUser(DEFAULT_USER);
			storageBox.setId(0);

			BeanValidators.validateWithException(validator, storageBox);

			comm.storageBoxService.saveOrUpdate(storageBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateStorageBox(Integer id, StorageBoxDTO storageBoxDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storageBoxDTO, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.storageBoxService.findStorageBox(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storageBoxDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.storageBoxService.findStorageBox(paramsMap) == null
					|| netappBox.getDescription().equals(storageBoxDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(storageBoxDTO, StorageBox.class), netappBox);

			netappBox.setUser(DEFAULT_USER);
			netappBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			netappBox.setIdClass(TableNameUtil.getTableName(StorageBox.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, netappBox);

			comm.storageBoxService.saveOrUpdate(netappBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteStorageBox(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StorageBox netappBox = comm.storageBoxService.findStorageBox(id);

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			netappBox.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.storageBoxService.saveOrUpdate(netappBox);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StorageBoxDTO> getStorageBoxList(SearchParams searchParams) {

		DTOListResult<StorageBoxDTO> result = new DTOListResult<StorageBoxDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.storageBoxService.getStorageBoxList(searchParams.getParamsMap()),
					StorageBoxDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StorageBoxDTO> getStorageBoxPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {
		PaginationResult<StorageBoxDTO> result = new PaginationResult<StorageBoxDTO>();

		try {

			return comm.storageBoxService.getStorageBoxDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
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
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());

			// // LookUp
			// if (dto.getIsp() != null) {
			// dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
			// }
			// dto.setIpAddressPoolText(findLookUp(dto.getIpAddressPool()).getDto().getDescription());
			// dto.setIpAddressStatusText(findLookUp(dto.getIpAddressStatus()).getDto().getDescription());

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

			// // Reference
			// dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());
			//
			// // LookUp
			// if (dto.getIsp() != null) {
			// dto.setIspText(findLookUp(dto.getIsp()).getDto().getDescription());
			// }
			// dto.setIpAddressPoolText(findLookUp(dto.getIpAddressPool()).getDto().getDescription());
			// dto.setIpAddressStatusText(findLookUp(dto.getIpAddressStatus()).getDto().getDescription());

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
			paramsMap.put("EQ_subnet", ipaddressDTO.getSubnet());

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
			paramsMap.put("EQ_subnet", ipaddressDTO.getSubnet());

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
	public IdResult initIpaddress(Integer id) {
		return changeIpaddressStatus(id, LookUpConstants.IPAddressStatus.未使用.getValue());
	}

	@Override
	public IdResult allocateIpaddress(Integer id) {
		return changeIpaddressStatus(id, LookUpConstants.IPAddressStatus.已使用.getValue());
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

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

		return result;
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
	public DTOResult<FirewallPortDTO> findFirewallPort(Integer id) {

		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO dto = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setFirewallDTO(findFirewall(dto.getFirewall()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByParams(SearchParams searchParams) {

		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(searchParams.getParamsMap());

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO dto = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setFirewallDTO(findFirewall(dto.getFirewall()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFirewallPort(FirewallPortDTO firewallPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", firewallPortDTO.getDescription());

			Validate.isTrue(comm.firewallPortService.findFirewallPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			FirewallPort firewallPort = BeanMapper.map(firewallPortDTO, FirewallPort.class);
			firewallPort.setCode("FirewallPort-" + Identities.randomBase62(8));
			firewallPort.setUser(DEFAULT_USER);
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
	public IdResult updateFirewallPort(Integer id, FirewallPortDTO firewallPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", firewallPortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortService.findFirewallPort(paramsMap) == null
					|| firewallPort.getDescription().equals(firewallPortDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallPortDTO, FirewallPort.class), firewallPort);

			firewallPort.setUser(DEFAULT_USER);
			firewallPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));

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
	public IdResult deleteFirewallPort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

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
	public DTOListResult<FirewallPortDTO> getFirewallPortList(SearchParams searchParams) {

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
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(Integer id) {

		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO dto = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(SearchParams searchParams) {

		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(searchParams
					.getParamsMap());

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO dto = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public IdResult createLoadBalancerPort(LoadBalancerPortDTO loadBalancerPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", loadBalancerPortDTO.getDescription());

			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			LoadBalancerPort loadBalancerPort = BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class);
			loadBalancerPort.setCode("LoadBalancerPort-" + Identities.randomBase62(8));
			loadBalancerPort.setUser(DEFAULT_USER);
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
	public IdResult updateLoadBalancerPort(Integer id, LoadBalancerPortDTO loadBalancerPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", loadBalancerPortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(paramsMap) == null
					|| loadBalancerPort.getDescription().equals(loadBalancerPortDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class), loadBalancerPort);

			loadBalancerPort.setUser(DEFAULT_USER);
			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));

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
	public IdResult deleteLoadBalancerPort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

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
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(SearchParams searchParams) {

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
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

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
	public DTOResult<StoragePortDTO> findStoragePort(Integer id) {

		DTOResult<StoragePortDTO> result = new DTOResult<StoragePortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.storagePortService.findStoragePort(id);

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			StoragePortDTO dto = BeanMapper.map(netappPort, StoragePortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setStorageDTO(findStorage(dto.getStorage()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<StoragePortDTO> findStoragePortByParams(SearchParams searchParams) {

		DTOResult<StoragePortDTO> result = new DTOResult<StoragePortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.storagePortService.findStoragePort(searchParams.getParamsMap());

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			StoragePortDTO dto = BeanMapper.map(netappPort, StoragePortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
			dto.setStorageDTO(findStorage(dto.getStorage()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createStoragePort(StoragePortDTO storagePortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storagePortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storagePortDTO.getDescription());

			Validate.isTrue(comm.storagePortService.findStoragePort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			StoragePort storagePort = BeanMapper.map(storagePortDTO, StoragePort.class);
			storagePort.setCode("StoragePort-" + Identities.randomBase62(8));
			storagePort.setUser(DEFAULT_USER);
			storagePort.setId(0);

			BeanValidators.validateWithException(validator, storagePort);

			comm.storagePortService.saveOrUpdate(storagePort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateStoragePort(Integer id, StoragePortDTO storagePortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(storagePortDTO, ERROR.INPUT_NULL);

			StoragePort storagePort = comm.storagePortService.findStoragePort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", storagePortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.storagePortService.findStoragePort(paramsMap) == null
					|| storagePort.getDescription().equals(storagePortDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(storagePortDTO, StoragePort.class), storagePort);

			storagePort.setUser(DEFAULT_USER);
			storagePort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			storagePort.setIdClass(TableNameUtil.getTableName(StoragePort.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, storagePort);

			comm.storagePortService.saveOrUpdate(storagePort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteStoragePort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			StoragePort netappPort = comm.storagePortService.findStoragePort(id);

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			netappPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.storagePortService.saveOrUpdate(netappPort);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<StoragePortDTO> getStoragePortList(SearchParams searchParams) {

		DTOListResult<StoragePortDTO> result = new DTOListResult<StoragePortDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.storagePortService.getStoragePortList(searchParams.getParamsMap()),
					StoragePortDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<StoragePortDTO> getStoragePortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<StoragePortDTO> result = new PaginationResult<StoragePortDTO>();

		try {

			return comm.storagePortService.getStoragePortDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPort(Integer id) {

		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO dto = BeanMapper.map(serverPort, ServerPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public DTOResult<ServerPortDTO> findServerPortByParams(SearchParams searchParams) {

		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(searchParams.getParamsMap());

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO dto = BeanMapper.map(serverPort, ServerPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public IdResult createServerPort(ServerPortDTO serverPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", serverPortDTO.getDescription());

			Validate.isTrue(comm.serverPortService.findServerPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			ServerPort serverPort = BeanMapper.map(serverPortDTO, ServerPort.class);
			serverPort.setCode("ServerPort-" + Identities.randomBase62(8));
			serverPort.setUser(DEFAULT_USER);
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
	public IdResult updateServerPort(Integer id, ServerPortDTO serverPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", serverPortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortService.findServerPort(paramsMap) == null
					|| serverPort.getDescription().equals(serverPortDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverPortDTO, ServerPort.class), serverPort);

			serverPort.setUser(DEFAULT_USER);
			serverPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));

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
	public IdResult deleteServerPort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

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
	public DTOListResult<ServerPortDTO> getServerPortList(SearchParams searchParams) {

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
	public PaginationResult<ServerPortDTO> getServerPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<SwitchPortDTO> findSwitchPort(Integer id) {

		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO dto = BeanMapper.map(switchPort, SwitchPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public DTOResult<SwitchPortDTO> findSwitchPortByParams(SearchParams searchParams) {

		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(searchParams.getParamsMap());

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO dto = BeanMapper.map(switchPort, SwitchPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public IdResult createSwitchPort(SwitchPortDTO switchPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", switchPortDTO.getDescription());

			Validate.isTrue(comm.switchPortService.findSwitchPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			SwitchPort switchPort = BeanMapper.map(switchPortDTO, SwitchPort.class);
			switchPort.setCode("SwitchPort-" + Identities.randomBase62(8));
			switchPort.setUser(DEFAULT_USER);
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
	public IdResult updateSwitchPort(Integer id, SwitchPortDTO switchPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", switchPortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortService.findSwitchPort(paramsMap) == null
					|| switchPort.getDescription().equals(switchPortDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchPortDTO, SwitchPort.class), switchPort);

			switchPort.setUser(DEFAULT_USER);
			switchPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));

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
	public IdResult deleteSwitchPort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

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
	public DTOListResult<SwitchPortDTO> getSwitchPortList(SearchParams searchParams) {

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
	public PaginationResult<SwitchPortDTO> getSwitchPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<NicPortDTO> findNicPort(Integer id) {

		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			NicPortDTO dto = BeanMapper.map(nicPort, NicPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public DTOResult<NicPortDTO> findNicPortByParams(SearchParams searchParams) {

		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(searchParams.getParamsMap());

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			NicPortDTO dto = BeanMapper.map(nicPort, NicPortDTO.class);

			// Reference
			if (dto.getConnectedTo() != null) {
				dto.setSwitchPortDTO(findSwitchPort(dto.getConnectedTo()).getDto());
			}
			dto.setIpaddressDTO(findIpaddress(dto.getIpaddress()).getDto());
			dto.setIdcDTO(findIdc(dto.getIdc()).getDto());
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
	public IdResult createNicPort(NicPortDTO nicPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", nicPortDTO.getDescription());

			Validate.isTrue(comm.nicPortService.findNicPort(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			NicPort nicPort = BeanMapper.map(nicPortDTO, NicPort.class);
			nicPort.setCode("NicPort-" + Identities.randomBase62(8));
			nicPort.setUser(DEFAULT_USER);
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
	public IdResult updateNicPort(Integer id, NicPortDTO nicPortDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", nicPortDTO.getDescription());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.nicPortService.findNicPort(paramsMap) == null
							|| nicPort.getDescription().equals(nicPortDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicPortDTO, NicPort.class), nicPort);

			nicPort.setUser(DEFAULT_USER);
			nicPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));

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
	public IdResult deleteNicPort(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

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
	public DTOListResult<NicPortDTO> getNicPortList(SearchParams searchParams) {

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
	public PaginationResult<NicPortDTO> getNicPortPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

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
	public DTOResult<ServiceDTO> findService(Integer id) {

		DTOResult<ServiceDTO> result = new DTOResult<ServiceDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Service service = comm.serviceService.findService(id);

			Validate.notNull(service, ERROR.OBJECT_NULL);

			ServiceDTO dto = BeanMapper.map(service, ServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServiceDTO> findServiceByParams(SearchParams searchParams) {

		DTOResult<ServiceDTO> result = new DTOResult<ServiceDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Service service = comm.serviceService.findService(searchParams.getParamsMap());

			Validate.notNull(service, ERROR.OBJECT_NULL);

			ServiceDTO dto = BeanMapper.map(service, ServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public DTOListResult<ServiceDTO> getServiceList(SearchParams searchParams) {

		DTOListResult<ServiceDTO> result = new DTOListResult<ServiceDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.serviceService.getServiceList(searchParams.getParamsMap()),
					ServiceDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServiceDTO> getServicePagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<ServiceDTO> result = new PaginationResult<ServiceDTO>();

		try {

			return comm.serviceService.getServiceDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RouterDTO> findRouter(Integer id) {

		DTOResult<RouterDTO> result = new DTOResult<RouterDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Router router = comm.routerService.findRouter(id);

			Validate.notNull(router, ERROR.OBJECT_NULL);

			RouterDTO dto = BeanMapper.map(router, RouterDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());
			dto.setEcsDTO(findEcs(dto.getEcs()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RouterDTO> findRouterByParams(SearchParams searchParams) {

		DTOResult<RouterDTO> result = new DTOResult<RouterDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Router router = comm.routerService.findRouter(searchParams.getParamsMap());

			Validate.notNull(router, ERROR.OBJECT_NULL);

			RouterDTO dto = BeanMapper.map(router, RouterDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createRouter(RouterDTO routerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(routerDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", routerDTO.getDescription());
			paramsMap.put("EQ_tenants", routerDTO.getTenants());

			Validate.isTrue(comm.routerService.findRouter(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			String code = "Router-" + Identities.randomBase62(8);

			Router router = BeanMapper.map(routerDTO, Router.class);
			router.setCode(code);
			router.setUser(DEFAULT_USER);
			router.setId(0);

			BeanValidators.validateWithException(validator, router);

			comm.routerService.saveOrUpdate(router);
			result.setMessage(code);
			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateRouter(Integer id, RouterDTO routerDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(routerDTO, ERROR.INPUT_NULL);

			Router router = comm.routerService.findRouter(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", routerDTO.getDescription());
			paramsMap.put("EQ_tenants", routerDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.routerService.findRouter(paramsMap) == null
							|| router.getDescription().equals(routerDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(routerDTO, Router.class), routerDTO);

			router.setUser(DEFAULT_USER);
			router.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			router.setIdClass(TableNameUtil.getTableName(Router.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, router);

			comm.routerService.saveOrUpdate(router);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteRouter(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Router router = comm.routerService.findRouter(id);

			Validate.notNull(router, ERROR.OBJECT_NULL);

			router.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.routerService.saveOrUpdate(router);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<RouterDTO> getRouterList(SearchParams searchParams) {

		DTOListResult<RouterDTO> result = new DTOListResult<RouterDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.routerService.getRouterList(searchParams.getParamsMap()),
					RouterDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<RouterDTO> getRouterPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<RouterDTO> result = new PaginationResult<RouterDTO>();

		try {

			return comm.routerService.getRouterDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallServiceDTO> findFirewallService(Integer id) {

		DTOResult<FirewallServiceDTO> result = new DTOResult<FirewallServiceDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallService firewallService = comm.firewallServiceService.findFirewallService(id);

			Validate.notNull(firewallService, ERROR.OBJECT_NULL);

			FirewallServiceDTO dto = BeanMapper.map(firewallService, FirewallServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallServiceDTO> findFirewallServiceByParams(SearchParams searchParams) {

		DTOResult<FirewallServiceDTO> result = new DTOResult<FirewallServiceDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FirewallService firewallService = comm.firewallServiceService.findFirewallService(searchParams
					.getParamsMap());

			Validate.notNull(firewallService, ERROR.OBJECT_NULL);

			FirewallServiceDTO dto = BeanMapper.map(firewallService, FirewallServiceDTO.class);

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFirewallService(FirewallServiceDTO firewallServiceDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallServiceDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", firewallServiceDTO.getDescription());
			paramsMap.put("EQ_tenants", firewallServiceDTO.getTenants());

			Validate.isTrue(comm.firewallServiceService.findFirewallService(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			String code = "FirewallService-" + Identities.randomBase62(8);

			FirewallService firewallService = BeanMapper.map(firewallServiceDTO, FirewallService.class);
			firewallService.setCode(code);
			firewallService.setUser(DEFAULT_USER);
			firewallService.setId(0);

			BeanValidators.validateWithException(validator, firewallService);

			comm.firewallServiceService.saveOrUpdate(firewallService);

			result.setMessage(code);
			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult updateFirewallService(Integer id, FirewallServiceDTO firewallServiceDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallServiceDTO, ERROR.INPUT_NULL);

			FirewallService firewallService = comm.firewallServiceService.findFirewallService(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", firewallServiceDTO.getDescription());
			paramsMap.put("EQ_tenants", firewallServiceDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallServiceService.findFirewallService(paramsMap) == null
					|| firewallService.getDescription().equals(firewallServiceDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallServiceDTO, FirewallService.class), firewallServiceDTO);

			firewallService.setUser(DEFAULT_USER);
			firewallService.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			firewallService.setIdClass(TableNameUtil.getTableName(FirewallService.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, firewallService);

			comm.firewallServiceService.saveOrUpdate(firewallService);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallService(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallService firewallService = comm.firewallServiceService.findFirewallService(id);

			Validate.notNull(firewallService, ERROR.OBJECT_NULL);

			firewallService.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.firewallServiceService.saveOrUpdate(firewallService);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallServiceDTO> getFirewallServiceList(SearchParams searchParams) {

		DTOListResult<FirewallServiceDTO> result = new DTOListResult<FirewallServiceDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.firewallServiceService.getFirewallServiceList(searchParams.getParamsMap()),
					FirewallServiceDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallServiceDTO> getFirewallServicePagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

		PaginationResult<FirewallServiceDTO> result = new PaginationResult<FirewallServiceDTO>();

		try {

			return comm.firewallServiceService.getFirewallServiceDTOPagination(searchParams.getParamsMap(), pageNumber,
					pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SubnetDTO> findSubnet(Integer id) {

		DTOResult<SubnetDTO> result = new DTOResult<SubnetDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Subnet subnet = comm.subnetService.findSubnet(id);

			Validate.notNull(subnet, ERROR.OBJECT_NULL);

			SubnetDTO dto = BeanMapper.map(subnet, SubnetDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setRouterDTO(findRouter(dto.getRouter()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SubnetDTO> findSubnetByParams(SearchParams searchParams) {

		DTOResult<SubnetDTO> result = new DTOResult<SubnetDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Subnet subnet = comm.subnetService.findSubnet(searchParams.getParamsMap());

			Validate.notNull(subnet, ERROR.OBJECT_NULL);

			SubnetDTO dto = BeanMapper.map(subnet, SubnetDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setRouterDTO(findRouter(dto.getRouter()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createSubnet(SubnetDTO subnetDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(subnetDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();
			paramsMap.put("EQ_description", subnetDTO.getDescription());
			paramsMap.put("EQ_tenants", subnetDTO.getTenants());

			Validate.isTrue(comm.subnetService.findSubnet(paramsMap) == null, ERROR.OBJECT_DUPLICATE);

			String code = "Subnet-" + Identities.randomBase62(8);

			Subnet subnet = BeanMapper.map(subnetDTO, Subnet.class);
			subnet.setCode(code);
			subnet.setUser(DEFAULT_USER);
			subnet.setId(0);

			BeanValidators.validateWithException(validator, subnet);

			comm.subnetService.saveOrUpdate(subnet);

			result.setMessage(code);
			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSubnet(Integer id, SubnetDTO subnetDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(subnetDTO, ERROR.INPUT_NULL);

			Subnet subnet = comm.subnetService.findSubnet(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", subnetDTO.getDescription());
			paramsMap.put("EQ_tenants", subnetDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.subnetService.findSubnet(paramsMap) == null
							|| subnet.getDescription().equals(subnetDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(subnetDTO, Router.class), subnetDTO);

			subnet.setUser(DEFAULT_USER);
			subnet.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			subnet.setIdClass(TableNameUtil.getTableName(SwitchPort.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, subnet);

			comm.subnetService.saveOrUpdate(subnet);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSubnet(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Subnet subnet = comm.subnetService.findSubnet(id);

			Validate.notNull(subnet, ERROR.OBJECT_NULL);

			subnet.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.subnetService.saveOrUpdate(subnet);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOListResult<SubnetDTO> getSubnetList(SearchParams searchParams) {

		DTOListResult<SubnetDTO> result = new DTOListResult<SubnetDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.subnetService.getSubnetList(searchParams.getParamsMap()),
					SubnetDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SubnetDTO> getSubnetPagination(SearchParams searchParams, Integer pageNumber,
			Integer pageSize) {

		PaginationResult<SubnetDTO> result = new PaginationResult<SubnetDTO>();

		try {

			return comm.subnetService.getSubnetDTOPagination(searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallAddressDTO> findConfigFirewallAddress(Integer id) {

		DTOResult<ConfigFirewallAddressDTO> result = new DTOResult<ConfigFirewallAddressDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallAddress configFirewallAddress = comm.configFirewallAddressService
					.findConfigFirewallAddress(id);

			Validate.notNull(configFirewallAddress, ERROR.OBJECT_NULL);

			ConfigFirewallAddressDTO dto = BeanMapper.map(configFirewallAddress, ConfigFirewallAddressDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallAddressDTO> findConfigFirewallAddressByParams(SearchParams searchParams) {

		DTOResult<ConfigFirewallAddressDTO> result = new DTOResult<ConfigFirewallAddressDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ConfigFirewallAddress configFirewallAddress = comm.configFirewallAddressService
					.findConfigFirewallAddress(searchParams.getParamsMap());

			Validate.notNull(configFirewallAddress, ERROR.OBJECT_NULL);

			ConfigFirewallAddressDTO dto = BeanMapper.map(configFirewallAddress, ConfigFirewallAddressDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult createConfigFirewallAddress(ConfigFirewallAddressDTO configFirewallAddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallAddressDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallAddressDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallAddressDTO.getTenants());

			Validate.isTrue(comm.configFirewallAddressService.findConfigFirewallAddress(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			ConfigFirewallAddress configFirewallAddress = BeanMapper.map(configFirewallAddressDTO,
					ConfigFirewallAddress.class);
			configFirewallAddress.setCode("CFA" + Identities.randomBase62(8));
			configFirewallAddress.setUser(DEFAULT_USER);
			configFirewallAddress.setId(0);

			BeanValidators.validateWithException(validator, configFirewallAddress);

			comm.configFirewallAddressService.saveOrUpdate(configFirewallAddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConfigFirewallAddress(Integer id, ConfigFirewallAddressDTO configFirewallAddressDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallAddressDTO, ERROR.INPUT_NULL);

			ConfigFirewallAddress configFirewallAddress = comm.configFirewallAddressService
					.findConfigFirewallAddress(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallAddressDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallAddressDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.configFirewallAddressService.findConfigFirewallAddress(paramsMap) == null
					|| configFirewallAddress.getDescription().equals(configFirewallAddressDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(configFirewallAddressDTO, ConfigFirewallAddress.class),
					configFirewallAddressDTO);

			configFirewallAddress.setUser(DEFAULT_USER);
			configFirewallAddress.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			configFirewallAddress.setIdClass(TableNameUtil.getTableName(ConfigFirewallAddress.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, configFirewallAddress);

			comm.configFirewallAddressService.saveOrUpdate(configFirewallAddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConfigFirewallAddress(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallAddress configFirewallAddress = comm.configFirewallAddressService
					.findConfigFirewallAddress(id);

			Validate.notNull(configFirewallAddress, ERROR.OBJECT_NULL);

			configFirewallAddress.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.configFirewallAddressService.saveOrUpdate(configFirewallAddress);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConfigFirewallAddressDTO> getConfigFirewallAddressList(SearchParams searchParams) {

		DTOListResult<ConfigFirewallAddressDTO> result = new DTOListResult<ConfigFirewallAddressDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.configFirewallAddressService.getConfigFirewallAddressList(searchParams.getParamsMap()),
					ConfigFirewallAddressDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConfigFirewallAddressDTO> getConfigFirewallAddressPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

		PaginationResult<ConfigFirewallAddressDTO> result = new PaginationResult<ConfigFirewallAddressDTO>();

		try {

			return comm.configFirewallAddressService.getConfigFirewallAddressDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallPolicyDTO> findConfigFirewallPolicy(Integer id) {

		DTOResult<ConfigFirewallPolicyDTO> result = new DTOResult<ConfigFirewallPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallPolicy configFirewallPolicy = comm.configFirewallPolicyService.findConfigFirewallPolicy(id);

			Validate.notNull(configFirewallPolicy, ERROR.OBJECT_NULL);

			ConfigFirewallPolicyDTO dto = BeanMapper.map(configFirewallPolicy, ConfigFirewallPolicyDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			// LookUp
			dto.setPolicyTypeText(findLookUp(dto.getPolicyType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallPolicyDTO> findConfigFirewallPolicyByParams(SearchParams searchParams) {

		DTOResult<ConfigFirewallPolicyDTO> result = new DTOResult<ConfigFirewallPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ConfigFirewallPolicy configFirewallPolicy = comm.configFirewallPolicyService
					.findConfigFirewallPolicy(searchParams.getParamsMap());

			Validate.notNull(configFirewallPolicy, ERROR.OBJECT_NULL);

			ConfigFirewallPolicyDTO dto = BeanMapper.map(configFirewallPolicy, ConfigFirewallPolicyDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			// LookUp
			dto.setPolicyTypeText(findLookUp(dto.getPolicyType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult createConfigFirewallPolicy(ConfigFirewallPolicyDTO configFirewallPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallPolicyDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallPolicyDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallPolicyDTO.getTenants());

			Validate.isTrue(comm.configFirewallPolicyService.findConfigFirewallPolicy(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			ConfigFirewallPolicy configFirewallPolicy = BeanMapper.map(configFirewallPolicyDTO,
					ConfigFirewallPolicy.class);
			configFirewallPolicy.setCode("CFP" + Identities.randomBase62(8));
			configFirewallPolicy.setUser(DEFAULT_USER);
			configFirewallPolicy.setId(0);

			BeanValidators.validateWithException(validator, configFirewallPolicy);

			comm.configFirewallPolicyService.saveOrUpdate(configFirewallPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConfigFirewallPolicy(Integer id, ConfigFirewallPolicyDTO configFirewallPolicyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallPolicyDTO, ERROR.INPUT_NULL);

			ConfigFirewallPolicy configFirewallPolicy = comm.configFirewallPolicyService.findConfigFirewallPolicy(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallPolicyDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallPolicyDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.configFirewallPolicyService.findConfigFirewallPolicy(paramsMap) == null
					|| configFirewallPolicy.getDescription().equals(configFirewallPolicyDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(configFirewallPolicyDTO, ConfigFirewallPolicy.class),
					configFirewallPolicyDTO);

			configFirewallPolicy.setUser(DEFAULT_USER);
			configFirewallPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			configFirewallPolicy.setIdClass(TableNameUtil.getTableName(ConfigFirewallPolicy.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, configFirewallPolicy);

			comm.configFirewallPolicyService.saveOrUpdate(configFirewallPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConfigFirewallPolicy(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallPolicy configFirewallPolicy = comm.configFirewallPolicyService.findConfigFirewallPolicy(id);

			Validate.notNull(configFirewallPolicy, ERROR.OBJECT_NULL);

			configFirewallPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.configFirewallPolicyService.saveOrUpdate(configFirewallPolicy);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConfigFirewallPolicyDTO> getConfigFirewallPolicyList(SearchParams searchParams) {

		DTOListResult<ConfigFirewallPolicyDTO> result = new DTOListResult<ConfigFirewallPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.configFirewallPolicyService.getConfigFirewallPolicyList(searchParams.getParamsMap()),
					ConfigFirewallPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConfigFirewallPolicyDTO> getConfigFirewallPolicyPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

		PaginationResult<ConfigFirewallPolicyDTO> result = new PaginationResult<ConfigFirewallPolicyDTO>();

		try {

			return comm.configFirewallPolicyService.getConfigFirewallPolicyDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigRouterStaticDTO> findConfigRouterStatic(Integer id) {

		DTOResult<ConfigRouterStaticDTO> result = new DTOResult<ConfigRouterStaticDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigRouterStatic configRouterStatic = comm.configRouterStaticService.findConfigRouterStatic(id);

			Validate.notNull(configRouterStatic, ERROR.OBJECT_NULL);

			ConfigRouterStaticDTO dto = BeanMapper.map(configRouterStatic, ConfigRouterStaticDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			// LookUp
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
	public DTOResult<ConfigRouterStaticDTO> findConfigRouterStaticByParams(SearchParams searchParams) {

		DTOResult<ConfigRouterStaticDTO> result = new DTOResult<ConfigRouterStaticDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ConfigRouterStatic configRouterStatic = comm.configRouterStaticService.findConfigRouterStatic(searchParams
					.getParamsMap());

			Validate.notNull(configRouterStatic, ERROR.OBJECT_NULL);

			ConfigRouterStaticDTO dto = BeanMapper.map(configRouterStatic, ConfigRouterStaticDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			// LookUp
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
	public IdResult createConfigRouterStatic(ConfigRouterStaticDTO configRouterStaticDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configRouterStaticDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configRouterStaticDTO.getDescription());
			paramsMap.put("EQ_tenants", configRouterStaticDTO.getTenants());

			Validate.isTrue(comm.configRouterStaticService.findConfigRouterStatic(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			ConfigRouterStatic configRouterStatic = BeanMapper.map(configRouterStaticDTO, ConfigRouterStatic.class);
			configRouterStatic.setCode("CRS" + Identities.randomBase62(8));
			configRouterStatic.setUser(DEFAULT_USER);
			configRouterStatic.setId(0);

			BeanValidators.validateWithException(validator, configRouterStatic);

			comm.configRouterStaticService.saveOrUpdate(configRouterStatic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConfigRouterStatic(Integer id, ConfigRouterStaticDTO configRouterStaticDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configRouterStaticDTO, ERROR.INPUT_NULL);

			ConfigRouterStatic configRouterStatic = comm.configRouterStaticService.findConfigRouterStatic(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configRouterStaticDTO.getDescription());
			paramsMap.put("EQ_tenants", configRouterStaticDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.configRouterStaticService.findConfigRouterStatic(paramsMap) == null
					|| configRouterStatic.getDescription().equals(configRouterStaticDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(configRouterStaticDTO, ConfigRouterStatic.class), configRouterStaticDTO);

			configRouterStatic.setUser(DEFAULT_USER);
			configRouterStatic.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			configRouterStatic.setIdClass(TableNameUtil.getTableName(ConfigRouterStatic.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, configRouterStatic);

			comm.configRouterStaticService.saveOrUpdate(configRouterStatic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConfigRouterStatic(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigRouterStatic configRouterStatic = comm.configRouterStaticService.findConfigRouterStatic(id);

			Validate.notNull(configRouterStatic, ERROR.OBJECT_NULL);

			configRouterStatic.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.configRouterStaticService.saveOrUpdate(configRouterStatic);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConfigRouterStaticDTO> getConfigRouterStaticList(SearchParams searchParams) {

		DTOListResult<ConfigRouterStaticDTO> result = new DTOListResult<ConfigRouterStaticDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.configRouterStaticService.getConfigRouterStaticList(searchParams.getParamsMap()),
					ConfigRouterStaticDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConfigRouterStaticDTO> getConfigRouterStaticPagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

		PaginationResult<ConfigRouterStaticDTO> result = new PaginationResult<ConfigRouterStaticDTO>();

		try {

			return comm.configRouterStaticService.getConfigRouterStaticDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigSystemInterfaceDTO> findConfigSystemInterface(Integer id) {

		DTOResult<ConfigSystemInterfaceDTO> result = new DTOResult<ConfigSystemInterfaceDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigSystemInterface configSystemInterface = comm.configSystemInterfaceService
					.findConfigSystemInterface(id);

			Validate.notNull(configSystemInterface, ERROR.OBJECT_NULL);

			ConfigSystemInterfaceDTO dto = BeanMapper.map(configSystemInterface, ConfigSystemInterfaceDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigSystemInterfaceDTO> findConfigSystemInterfaceByParams(SearchParams searchParams) {

		DTOResult<ConfigSystemInterfaceDTO> result = new DTOResult<ConfigSystemInterfaceDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ConfigSystemInterface configSystemInterface = comm.configSystemInterfaceService
					.findConfigSystemInterface(searchParams.getParamsMap());

			Validate.notNull(configSystemInterface, ERROR.OBJECT_NULL);

			ConfigSystemInterfaceDTO dto = BeanMapper.map(configSystemInterface, ConfigSystemInterfaceDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());
			dto.setSubnetDTO(findSubnet(dto.getSubnet()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createConfigSystemInterface(ConfigSystemInterfaceDTO configSystemInterfaceDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configSystemInterfaceDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configSystemInterfaceDTO.getDescription());
			paramsMap.put("EQ_tenants", configSystemInterfaceDTO.getTenants());

			Validate.isTrue(comm.configSystemInterfaceService.findConfigSystemInterface(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			ConfigSystemInterface configSystemInterface = BeanMapper.map(configSystemInterfaceDTO,
					ConfigSystemInterface.class);
			configSystemInterface.setCode("CSI" + Identities.randomBase62(8));
			configSystemInterface.setUser(DEFAULT_USER);
			configSystemInterface.setId(0);

			BeanValidators.validateWithException(validator, configSystemInterface);

			comm.configSystemInterfaceService.saveOrUpdate(configSystemInterface);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConfigSystemInterface(Integer id, ConfigSystemInterfaceDTO configSystemInterfaceDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configSystemInterfaceDTO, ERROR.INPUT_NULL);

			ConfigSystemInterface configSystemInterface = comm.configSystemInterfaceService
					.findConfigSystemInterface(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configSystemInterfaceDTO.getDescription());
			paramsMap.put("EQ_tenants", configSystemInterfaceDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.configSystemInterfaceService.findConfigSystemInterface(paramsMap) == null
					|| configSystemInterface.getDescription().equals(configSystemInterfaceDTO.getDescription()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(configSystemInterfaceDTO, ConfigSystemInterface.class),
					configSystemInterfaceDTO);

			configSystemInterface.setUser(DEFAULT_USER);
			configSystemInterface.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			configSystemInterface.setIdClass(TableNameUtil.getTableName(ConfigSystemInterface.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, configSystemInterface);

			comm.configSystemInterfaceService.saveOrUpdate(configSystemInterface);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConfigSystemInterface(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigSystemInterface configSystemInterface = comm.configSystemInterfaceService
					.findConfigSystemInterface(id);

			Validate.notNull(configSystemInterface, ERROR.OBJECT_NULL);

			configSystemInterface.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.configSystemInterfaceService.saveOrUpdate(configSystemInterface);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConfigSystemInterfaceDTO> getConfigSystemInterfaceList(SearchParams searchParams) {

		DTOListResult<ConfigSystemInterfaceDTO> result = new DTOListResult<ConfigSystemInterfaceDTO>();

		try {

			result.setDtos(BeanMapper.mapList(
					comm.configSystemInterfaceService.getConfigSystemInterfaceList(searchParams.getParamsMap()),
					ConfigSystemInterfaceDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConfigSystemInterfaceDTO> getConfigSystemInterfacePagination(SearchParams searchParams,
			Integer pageNumber, Integer pageSize) {

		PaginationResult<ConfigSystemInterfaceDTO> result = new PaginationResult<ConfigSystemInterfaceDTO>();

		try {

			return comm.configSystemInterfaceService.getConfigSystemInterfaceDTOPagination(searchParams.getParamsMap(),
					pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallServiceCategoryDTO> findconfigFirewallServiceCategory(Integer id) {

		DTOResult<ConfigFirewallServiceCategoryDTO> result = new DTOResult<ConfigFirewallServiceCategoryDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallServiceCategory configFirewallServiceCategory = comm.configFirewallServiceCategoryService
					.findConfigFirewallServiceCategory(id);

			Validate.notNull(configFirewallServiceCategory, ERROR.OBJECT_NULL);

			ConfigFirewallServiceCategoryDTO dto = BeanMapper.map(configFirewallServiceCategory,
					ConfigFirewallServiceCategoryDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConfigFirewallServiceCategoryDTO> findconfigFirewallServiceCategoryByParams(
			SearchParams searchParams) {

		DTOResult<ConfigFirewallServiceCategoryDTO> result = new DTOResult<ConfigFirewallServiceCategoryDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ConfigFirewallServiceCategory configFirewallServiceCategory = comm.configFirewallServiceCategoryService
					.findConfigFirewallServiceCategory(searchParams.getParamsMap());

			Validate.notNull(configFirewallServiceCategory, ERROR.OBJECT_NULL);

			ConfigFirewallServiceCategoryDTO dto = BeanMapper.map(configFirewallServiceCategory,
					ConfigFirewallServiceCategoryDTO.class);

			// Reference
			dto.setTenantsDTO(findTenants(dto.getTenants()).getDto());
			dto.setFirewallServiceDTO(findFirewallService(dto.getFirewallService()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createconfigFirewallServiceCategory(
			ConfigFirewallServiceCategoryDTO configFirewallServiceCategoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallServiceCategoryDTO, ERROR.INPUT_NULL);

			// 验证description是否唯一.如果不为null,则弹出错误.

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallServiceCategoryDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallServiceCategoryDTO.getTenants());

			Validate.isTrue(
					comm.configFirewallServiceCategoryService.findConfigFirewallServiceCategory(paramsMap) == null,
					ERROR.OBJECT_DUPLICATE);

			ConfigFirewallServiceCategory configFirewallServiceCategory = BeanMapper.map(
					configFirewallServiceCategoryDTO, ConfigFirewallServiceCategory.class);
			configFirewallServiceCategory.setCode("CFSC" + Identities.randomBase62(8));
			configFirewallServiceCategory.setUser(DEFAULT_USER);
			configFirewallServiceCategory.setId(0);

			BeanValidators.validateWithException(validator, configFirewallServiceCategory);

			comm.configFirewallServiceCategoryService.saveOrUpdate(configFirewallServiceCategory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateconfigFirewallServiceCategory(Integer id,
			ConfigFirewallServiceCategoryDTO configFirewallServiceCategoryDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(configFirewallServiceCategoryDTO, ERROR.INPUT_NULL);

			ConfigFirewallServiceCategory configFirewallServiceCategory = comm.configFirewallServiceCategoryService
					.findConfigFirewallServiceCategory(id);

			Map<String, Object> paramsMap = Maps.newHashMap();

			paramsMap.put("EQ_description", configFirewallServiceCategoryDTO.getDescription());
			paramsMap.put("EQ_tenants", configFirewallServiceCategoryDTO.getTenants());

			// 验证description是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.configFirewallServiceCategoryService.findConfigFirewallServiceCategory(paramsMap) == null
							|| configFirewallServiceCategory.getDescription().equals(
									configFirewallServiceCategoryDTO.getDescription()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(configFirewallServiceCategoryDTO, ConfigFirewallServiceCategory.class),
					configFirewallServiceCategoryDTO);

			configFirewallServiceCategory.setUser(DEFAULT_USER);
			configFirewallServiceCategory.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			configFirewallServiceCategory.setIdClass(TableNameUtil.getTableName(ConfigFirewallServiceCategory.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, configFirewallServiceCategory);

			comm.configFirewallServiceCategoryService.saveOrUpdate(configFirewallServiceCategory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteconfigFirewallServiceCategory(Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ConfigFirewallServiceCategory configFirewallServiceCategory = comm.configFirewallServiceCategoryService
					.findConfigFirewallServiceCategory(id);

			Validate.notNull(configFirewallServiceCategory, ERROR.OBJECT_NULL);

			configFirewallServiceCategory.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.configFirewallServiceCategoryService.saveOrUpdate(configFirewallServiceCategory);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConfigFirewallServiceCategoryDTO> getconfigFirewallServiceCategoryList(
			SearchParams searchParams) {

		DTOListResult<ConfigFirewallServiceCategoryDTO> result = new DTOListResult<ConfigFirewallServiceCategoryDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.configFirewallServiceCategoryService
					.getConfigFirewallServiceCategoryList(searchParams.getParamsMap()),
					ConfigFirewallServiceCategoryDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConfigFirewallServiceCategoryDTO> getconfigFirewallServiceCategoryPagination(
			SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		PaginationResult<ConfigFirewallServiceCategoryDTO> result = new PaginationResult<ConfigFirewallServiceCategoryDTO>();

		try {

			return comm.configFirewallServiceCategoryService.getConfigFirewallServiceCategoryDTOPagination(
					searchParams.getParamsMap(), pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
