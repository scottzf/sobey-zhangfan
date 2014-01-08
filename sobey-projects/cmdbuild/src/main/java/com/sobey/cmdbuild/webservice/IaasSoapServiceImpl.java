package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.entity.As2;
import com.sobey.cmdbuild.entity.Cs2;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.entity.GroupPolicy;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.TableNameUtil;

//@WebService(serviceName = "IaasSoapService", endpointInterface = "com.sobey.cmdbuild.webservice.IaasSoapServiceImpl", targetNamespace = WsConstants.NS)
//查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IaasSoapServiceImpl extends BasicSoapSevcie implements IaasSoapService {

	@Autowired
	private CmdbuildSoapServiceImpl cmdbuildSoapServiceImpl;

	@Autowired
	private FinancialSoapServiceImpl financialSoapServiceImpl;

	@Autowired
	private InfrastructureSoapServiceImpl infrastructureSoapServiceImpl;

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<Cs2DTO> findCs2(@WebParam(name = "id") Integer id) {

		DTOResult<Cs2DTO> result = new DTOResult<Cs2DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(id);

			Validate.notNull(cs2, ERROR.OBJECT_NULL);

			Cs2DTO dto = BeanMapper.map(cs2, Cs2DTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setFimasDTO(infrastructureSoapServiceImpl.findFimas(dto.getFimas()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Cs2DTO> findCs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<Cs2DTO> result = new DTOResult<Cs2DTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(searchParams);

			Validate.notNull(cs2, ERROR.OBJECT_NULL);

			Cs2DTO dto = BeanMapper.map(cs2, Cs2DTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setFimasDTO(infrastructureSoapServiceImpl.findFimas(dto.getFimas()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createCs2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", cs2DTO.getCode());

			Validate.isTrue(comm.cs2Service.findCs2(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Cs2 cs2 = BeanMapper.map(cs2DTO, Cs2.class);

			cs2.setUser(DEFAULT_USER);
			cs2.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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
	public IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);

			Cs2 cs2 = comm.cs2Service.findCs2(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", cs2DTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.cs2Service.findCs2(searchParams) == null || cs2.getCode().equals(cs2DTO.getCode()),
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
	public PaginationResult<Cs2DTO> getCs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<Cs2DTO> result = new PaginationResult<Cs2DTO>();

		try {

			return comm.cs2Service.getCs2DTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Cs2DTO> getCs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<Cs2DTO> result = new DTOListResult<Cs2DTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.cs2Service.getCs2List(searchParams), Cs2DTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id) {

		DTOResult<As2DTO> result = new DTOResult<As2DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			As2 as2 = comm.as2Service.findAs2(id);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			As2DTO dto = BeanMapper.map(as2, As2DTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(infrastructureSoapServiceImpl.findNetappController(dto.getNetappController())
					.getDto());

			// LookUp
			dto.setVolumeTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getVolumeType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<As2DTO> findAs2ByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<As2DTO> result = new DTOResult<As2DTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			As2 as2 = comm.as2Service.findAs2(searchParams);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			As2DTO dto = BeanMapper.map(as2, As2DTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(infrastructureSoapServiceImpl.findNetappController(dto.getNetappController())
					.getDto());

			// LookUp
			dto.setVolumeTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getVolumeType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createAs2(@WebParam(name = "as2DTO") As2DTO as2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(as2DTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", as2DTO.getCode());

			Validate.isTrue(comm.as2Service.findAs2(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			As2 as2 = BeanMapper.map(as2DTO, As2.class);

			as2.setIdClass(TableNameUtil.getTableName(As2.class));
			as2.setUser(DEFAULT_USER);
			as2.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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
	public IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") As2DTO as2DTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(as2DTO, ERROR.INPUT_NULL);

			As2 as2 = comm.as2Service.findAs2(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", as2DTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.as2Service.findAs2(searchParams) == null || as2.getCode().equals(as2DTO.getCode()),
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(as2DTO, As2.class), as2);

			as2.setIdClass(TableNameUtil.getTableName(As2.class));
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

			As2 as2 = comm.as2Service.findAs2(id);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			as2.setIdClass(TableNameUtil.getTableName(As2.class));
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
	public PaginationResult<As2DTO> getAs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<As2DTO> result = new PaginationResult<As2DTO>();

		try {

			return comm.as2Service.getAs2DTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<As2DTO> getAs2List(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<As2DTO> result = new DTOListResult<As2DTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.as2Service.getAs2List(searchParams), As2DTO.class));

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
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(financialSoapServiceImpl.findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(infrastructureSoapServiceImpl.findServer(dto.getServer()).getDto());

			// LookUp
			dto.setEcsAgentText(cmdbuildSoapServiceImpl.findLookUp(dto.getEcsAgent()).getDto().getDescription());
			dto.setEcsStatusText(cmdbuildSoapServiceImpl.findLookUp(dto.getEcsStatus()).getDto().getDescription());
			dto.setImageText(cmdbuildSoapServiceImpl.findLookUp(dto.getImage()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(searchParams);

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			// Reference
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(financialSoapServiceImpl.findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(infrastructureSoapServiceImpl.findServer(dto.getServer()).getDto());

			// LookUp
			dto.setEcsAgentText(cmdbuildSoapServiceImpl.findLookUp(dto.getEcsAgent()).getDto().getDescription());
			dto.setEcsStatusText(cmdbuildSoapServiceImpl.findLookUp(dto.getEcsStatus()).getDto().getDescription());
			dto.setImageText(cmdbuildSoapServiceImpl.findLookUp(dto.getImage()).getDto().getDescription());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", ecsDTO.getCode());

			Validate.isTrue(comm.ecsService.findEcs(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Ecs ecs = BeanMapper.map(ecsDTO, Ecs.class);

			ecs.setIdClass(TableNameUtil.getTableName(Ecs.class));
			ecs.setUser(DEFAULT_USER);
			ecs.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsDTO.getCode());

			Validate.isTrue(comm.ecsService.findEcs(searchParams) == null || ecs.getCode().equals(ecsDTO.getCode()),
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
	public DTOListResult<EcsDTO> getEcsList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<EcsDTO> result = new DTOListResult<EcsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.ecsService.getEcsList(searchParams), EcsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EcsDTO> result = new PaginationResult<EcsDTO>();

		try {

			return comm.ecsService.getEcsDTOPagination(searchParams, pageNumber, pageSize);

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
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(financialSoapServiceImpl.findEipSpec(dto.getEipSpec()).getDto());

			// LookUp
			dto.setEipStatusText(cmdbuildSoapServiceImpl.findLookUp(dto.getEipStatus()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipDTO> findEipByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(searchParams);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(financialSoapServiceImpl.findEipSpec(dto.getEipSpec()).getDto());

			// LookUp
			dto.setEipStatusText(cmdbuildSoapServiceImpl.findLookUp(dto.getEipStatus()).getDto().getDescription());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", eipDTO.getCode());

			Validate.isTrue(comm.eipService.findEip(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Eip eip = BeanMapper.map(eipDTO, Eip.class);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));
			eip.setUser(DEFAULT_USER);
			eip.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", eipDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipService.findEip(searchParams) == null || eip.getCode().equals(eipDTO.getCode()),
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
	public PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EipDTO> result = new PaginationResult<EipDTO>();

		try {

			return comm.eipService.getEipDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipDTO> getEipList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<EipDTO> result = new DTOListResult<EipDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.eipService.getEipList(searchParams), EipDTO.class));

			return result;

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
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbDTO> findElbByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(searchParams);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", elbDTO.getCode());

			Validate.isTrue(comm.elbService.findElb(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Elb elb = BeanMapper.map(elbDTO, Elb.class);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));
			elb.setUser(DEFAULT_USER);
			elb.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", elbDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbService.findElb(searchParams) == null || elb.getCode().equals(elbDTO.getCode()),
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
	public PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<ElbDTO> result = new PaginationResult<ElbDTO>();

		try {

			return comm.elbService.getElbDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbDTO> getElbList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<ElbDTO> result = new DTOListResult<ElbDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.elbService.getElbList(searchParams), ElbDTO.class));

			return result;

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
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setDomainTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getDomainType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsDTO> findDnsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(searchParams);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

			// LookUp
			dto.setDomainTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getDomainType()).getDto().getDescription());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", dnsDTO.getCode());

			Validate.isTrue(comm.dnsService.findDns(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Dns dns = BeanMapper.map(dnsDTO, Dns.class);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));
			dns.setUser(DEFAULT_USER);
			dns.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", dnsDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.dnsService.findDns(searchParams) == null || dns.getCode().equals(dnsDTO.getCode()),
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
	public PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<DnsDTO> result = new PaginationResult<DnsDTO>();

		try {

			return comm.dnsService.getDnsDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DnsDTO> getDnsList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<DnsDTO> result = new DTOListResult<DnsDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.dnsService.getDnsList(searchParams), DnsDTO.class));

			return result;

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
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgDTO> findEsgByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(searchParams);

			Validate.notNull(esg, ERROR.OBJECT_NULL);

			EsgDTO dto = BeanMapper.map(esg, EsgDTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", esgDTO.getCode());

			Validate.isTrue(comm.esgService.findEsg(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Esg esg = BeanMapper.map(esgDTO, Esg.class);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));
			esg.setUser(DEFAULT_USER);
			esg.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", esgDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgService.findEsg(searchParams) == null || esg.getCode().equals(esgDTO.getCode()),
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
	public PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<EsgDTO> result = new PaginationResult<EsgDTO>();

		try {

			return comm.esgService.getEsgDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgDTO> getEsgList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<EsgDTO> result = new DTOListResult<EsgDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.esgService.getEsgList(searchParams), EsgDTO.class));

			return result;

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
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VpnDTO> findVpnByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(searchParams);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

			// Reference
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", vpnDTO.getCode());

			Validate.isTrue(comm.vpnService.findVpn(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Vpn vpn = BeanMapper.map(vpnDTO, Vpn.class);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));
			vpn.setUser(DEFAULT_USER);
			vpn.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", vpnDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vpnService.findVpn(searchParams) == null || vpn.getCode().equals(vpnDTO.getCode()),
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
	public PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<VpnDTO> result = new PaginationResult<VpnDTO>();

		try {

			return comm.vpnService.getVpnDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VpnDTO> getVpnList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<VpnDTO> result = new DTOListResult<VpnDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.vpnService.getVpnList(searchParams), VpnDTO.class));

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
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicyByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(searchParams);

			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);

			GroupPolicyDTO dto = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);

			// Reference
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", groupPolicyDTO.getCode());

			Validate.isTrue(comm.groupPolicyService.findGroupPolicy(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			GroupPolicy groupPolicy = BeanMapper.map(groupPolicyDTO, GroupPolicy.class);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));
			groupPolicy.setUser(DEFAULT_USER);
			groupPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", groupPolicyDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.groupPolicyService.findGroupPolicy(searchParams) == null
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
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<GroupPolicyDTO> result = new PaginationResult<GroupPolicyDTO>();

		try {

			return comm.groupPolicyService.getGroupPolicyDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<GroupPolicyDTO> getGroupPolicyList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<GroupPolicyDTO> result = new DTOListResult<GroupPolicyDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.groupPolicyService.getGroupPolicyList(searchParams),
					GroupPolicyDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
