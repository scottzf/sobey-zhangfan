package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.TableNameUtil;

@WebService(serviceName = "FinancialSoapService", endpointInterface = "com.sobey.cmdbuild.webservice.FinancialSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FinancialSoapServiceImpl extends BasicSoapSevcie implements FinancialSoapService {

	@Autowired
	private CmdbuildSoapServiceImpl cmdbuildSoapServiceImpl;

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(cmdbuildSoapServiceImpl.findLookUp(dto.getHight()).getDto().getDescription());
			dto.setMaintenanceText(cmdbuildSoapServiceImpl.findLookUp(dto.getMaintenance()).getDto().getDescription());
			dto.setPowerText(cmdbuildSoapServiceImpl.findLookUp(dto.getPower()).getDto().getDescription());
			dto.setDeviceTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getDeviceType()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(searchParams);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			DeviceSpecDTO dto = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

			// LookUp
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());
			dto.setHightText(cmdbuildSoapServiceImpl.findLookUp(dto.getHight()).getDto().getDescription());
			dto.setMaintenanceText(cmdbuildSoapServiceImpl.findLookUp(dto.getMaintenance()).getDto().getDescription());
			dto.setPowerText(cmdbuildSoapServiceImpl.findLookUp(dto.getPower()).getDto().getDescription());
			dto.setDeviceTypeText(cmdbuildSoapServiceImpl.findLookUp(dto.getDeviceType()).getDto().getDescription());

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

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			searchParams.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setIdClass(TableNameUtil.getTableName(DeviceSpec.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

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

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			searchParams.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(
					comm.deviceSpecService.findDeviceSpec(searchParams) == null
							|| deviceSpec.getCode().equals(deviceSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(deviceSpecDTO, DeviceSpec.class), deviceSpec);

			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			deviceSpec.setIdClass(TableNameUtil.getTableName(DeviceSpec.class));

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

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

			deviceSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DeviceSpecDTO> result = new PaginationResult<DeviceSpecDTO>();
		try {
			return comm.deviceSpecService.getDeviceSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<DeviceSpecDTO> result = new DTOListResult<DeviceSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.deviceSpecService.getDeviceSpecList(searchParams),
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
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(searchParams);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			EcsSpecDTO dto = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

			// LookUp
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(comm.ecsSpecService.findEcsSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setIdClass(TableNameUtil.getTableName(EcsSpec.class));

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return new IdResult(ecsSpec.getId());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(
					comm.ecsSpecService.findEcsSpec(searchParams) == null
							|| ecsSpec.getCode().equals(ecsSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ecsSpecDTO, EcsSpec.class), ecsSpec);

			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ecsSpec.setIdClass(TableNameUtil.getTableName(EcsSpec.class));

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return new IdResult(ecsSpec.getId());

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

			return new IdResult(ecsSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsSpecDTO> result = new PaginationResult<EcsSpecDTO>();
		try {
			return comm.ecsSpecService.getEcsSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EcsSpecDTO> result = new DTOListResult<EcsSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.ecsSpecService.getEcsSpecList(searchParams), EcsSpecDTO.class));
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
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(searchParams);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			EipSpecDTO dto = BeanMapper.map(eipSpec, EipSpecDTO.class);

			// LookUp
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(comm.eipSpecService.findEipSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EipSpec eipSpec = BeanMapper.map(eipSpecDTO, EipSpec.class);
			eipSpec.setUser(DEFAULT_USER);
			eipSpec.setIdClass(TableNameUtil.getTableName(EipSpec.class));

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return new IdResult(eipSpec.getId());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(
					comm.eipSpecService.findEipSpec(searchParams) == null
							|| eipSpec.getCode().equals(eipSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipSpecDTO, EipSpec.class), eipSpec);

			eipSpec.setUser(DEFAULT_USER);
			eipSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eipSpec.setIdClass(TableNameUtil.getTableName(EipSpec.class));

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return new IdResult(eipSpec.getId());

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

			return new IdResult(eipSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipSpecDTO> result = new PaginationResult<EipSpecDTO>();
		try {
			return comm.eipSpecService.getEipSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EipSpecDTO> result = new DTOListResult<EipSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.eipSpecService.getEipSpecList(searchParams), EipSpecDTO.class));
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
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

			result.setDto(dto);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(searchParams);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			Es3SpecDTO dto = BeanMapper.map(es3Spec, Es3SpecDTO.class);

			// LookUp
			dto.setBrandText(cmdbuildSoapServiceImpl.findLookUp(dto.getBrand()).getDto().getDescription());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(comm.es3SpecService.findEs3Spec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Es3Spec es3Spec = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			es3Spec.setUser(DEFAULT_USER);
			es3Spec.setIdClass(TableNameUtil.getTableName(Es3Spec.class));

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return new IdResult(es3Spec.getId());

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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(
					comm.es3SpecService.findEs3Spec(searchParams) == null
							|| es3Spec.getCode().equals(es3SpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(es3SpecDTO, Es3Spec.class), es3Spec);

			es3Spec.setUser(DEFAULT_USER);
			es3Spec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			es3Spec.setIdClass(TableNameUtil.getTableName(Es3Spec.class));

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return new IdResult(es3Spec.getId());

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

			return new IdResult(es3Spec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<Es3SpecDTO> result = new PaginationResult<Es3SpecDTO>();
		try {

			return comm.es3SpecService.getEs3SpecDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<Es3SpecDTO> result = new DTOListResult<Es3SpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.es3SpecService.getEs3SpecList(searchParams), Es3SpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
