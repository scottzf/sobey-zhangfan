package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.LookUpConstants;
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
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
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

			Cs2 cs2 = BeanMapper.map(cs2DTO, Cs2.class);

			cs2.setUser(DEFAULT_USER);
			cs2.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			BeanValidators.validateWithException(validator, cs2);

			comm.cs2Service.saveOrUpdate(cs2);

			return new IdResult(cs2.getId());

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
			Validate.isTrue(comm.cs2Service.findCs2(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(cs2DTO, Cs2.class), cs2);

			cs2.setIdClass(TableNameUtil.getTableName(Cs2.class));

			cs2.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, cs2);

			comm.cs2Service.saveOrUpdate(cs2);

			return new IdResult(cs2.getId());

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

			Validate.isTrue(cs2 != null, ERROR.OBJECT_NULL);

			cs2.setIdClass(TableNameUtil.getTableName(Cs2.class));

			cs2.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.cs2Service.saveOrUpdate(cs2);

			return new IdResult(cs2.getId());

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

			List<Cs2> cs2 = comm.cs2Service.getCs2List(searchParams);

			List<Cs2DTO> list = BeanMapper.mapList(cs2, Cs2DTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult resizeCS2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			// 填写新增 CS2 的基本信息。创建CS2
			result = createCs2(cs2DTO);

			// 判断CS2是否创建成功，该判断有待验证
			if (result.getCode().equals("0")) {

				// TODO 调用 storage Agent 中的 createVolumes 接口。CS2 创建成功，将数据保存至 CMDBuild 中；创建失败，返回错误提示至页面。

				// 创建订单(订单开始时间和服务时间和CS2的创建时间相同)
				ConsumptionsDTO consumptionsDTO = new ConsumptionsDTO();

				consumptionsDTO.setId(0);
				consumptionsDTO.setTenants(cs2DTO.getTenants());
				consumptionsDTO.setBeginDate(cs2DTO.getBeginDate());
				consumptionsDTO.setServiceEnd(cs2DTO.getBeginDate());
				// consumptionsDTO.setIdentifier("??");
				// consumptionsDTO.setServiceType(LookUpConstants.ServiceType.);//没有找到cs3
				consumptionsDTO.setConsumptionsStatus(LookUpConstants.ConsumptionsStatus.Execution.getValue());

				result = financialSoapServiceImpl.createConsumptions(consumptionsDTO);

			}

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult operateCS2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			// TODO 修改 storage 的配置。
			// TODO 调用 Storage Agent 中的 resizeVolumes 接口。存储修改成功，将数据保存至CMDBuild 中；修改失败，返回错误提示至页面。

			// 结算订单。consumptionsId没有找到
			financialSoapServiceImpl.settleConsumptions(0, cs2DTO.getTenants());

			// 新建订单

			// TODO 调用 storage Agent 中的 createVolumes 接口。CS2 创建成功，将数据保存至 CMDBuild 中；创建失败，返回错误提示至页面。

			// 创建订单(订单开始时间和服务时间和CS2的创建时间相同)
			ConsumptionsDTO consumptionsDTO = new ConsumptionsDTO();

			consumptionsDTO.setId(0);
			consumptionsDTO.setTenants(cs2DTO.getTenants());
			consumptionsDTO.setBeginDate(cs2DTO.getBeginDate());
			consumptionsDTO.setServiceEnd(cs2DTO.getBeginDate());
			// consumptionsDTO.setIdentifier("??");
			// consumptionsDTO.setServiceType(LookUpConstants.ServiceType.);//没有找到cs3
			consumptionsDTO.setConsumptionsStatus(LookUpConstants.ConsumptionsStatus.Execution.getValue());

			result = financialSoapServiceImpl.createConsumptions(consumptionsDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult ModifyCS2Attributes(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {

		IdResult result = new IdResult();

		try {

			// 修改存储的基本信息。 如服务标签、存储说明等不需要关联 Storage Agent 的操作
			result = updateCs2(cs2DTO.getId(), cs2DTO);

			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOListResult<Cs2DTO> reportCS2(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		// 多条件获取对象集合，并导出列表的信息为 Excel 文件

		return null;
	}

	@Override
	public DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id) {
		DTOResult<As2DTO> result = new DTOResult<As2DTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			As2 as2 = comm.as2Service.findAs2(id);

			Validate.notNull(as2, ERROR.OBJECT_NULL);

			As2DTO dto = BeanMapper.map(as2, As2DTO.class);

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(infrastructureSoapServiceImpl.findNetappController(dto.getNetappController())
					.getDto());

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

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEs3SpecDTO(financialSoapServiceImpl.findEs3Spec(dto.getEs3Spec()).getDto());
			dto.setNetappControllerDTO(infrastructureSoapServiceImpl.findNetappController(dto.getNetappController())
					.getDto());

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

			// TODO 调用 storage Agent 中的 createVolumes 接口。AS2 创建成功，将数据保存至CMDBuild 中；创建失败，返回错误提示至页面。

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

			// TODO 创建订单(订单开始时间和服务时间和AS2的创建时间相同)
			ConsumptionsDTO consumptionsDTO = new ConsumptionsDTO();

			consumptionsDTO.setId(0);
			consumptionsDTO.setTenants(as2DTO.getTenants());
			consumptionsDTO.setBeginDate(as2DTO.getBeginDate());
			consumptionsDTO.setServiceEnd(as2DTO.getBeginDate());
			// consumptionsDTO.setIdentifier("??");
			// consumptionsDTO.setServiceType(LookUpConstants.ServiceType.);//没有找到cs3
			consumptionsDTO.setConsumptionsStatus(LookUpConstants.ConsumptionsStatus.Execution.getValue());

			result = financialSoapServiceImpl.createConsumptions(consumptionsDTO);

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
			Validate.isTrue(comm.as2Service.findAs2(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(as2DTO, As2.class), as2);

			as2.setIdClass(TableNameUtil.getTableName(As2.class));

			as2.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			as2.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, as2);

			comm.as2Service.saveOrUpdate(as2);

			return new IdResult(as2.getId());

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
			
			// TODO 选择存储,判断是否是是加载的。如果是加载状态，返回错误信息。
			// 调用 Storage Agent 中的 deleteVolumes 接口。存储删除成功，将数据保存至CMDBuild 中；销毁失败，返回错误提示至页面。
			
			Validate.notNull(id, ERROR.INPUT_NULL);

			As2 as2 = comm.as2Service.findAs2(id);

			Validate.isTrue(as2 != null, ERROR.OBJECT_NULL);
			
			// TODO 结算订单
			financialSoapServiceImpl.settleConsumptions(0, as2.getTenants());
			
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

			List<As2> as2 = comm.as2Service.getAs2List(searchParams);

			List<As2DTO> list = BeanMapper.mapList(as2, As2DTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult resizeAS2(@WebParam(name = "as2DTO") As2DTO as2DTO) {

		IdResult result = new IdResult();

		try {

			// TODO 修改 storage 的配置。

			// 结算订单。consumptionsId没有找到
			result = financialSoapServiceImpl.settleConsumptions(0, as2DTO.getTenants());

			// 新建订单

			// TODO 调用 Storage Agent 中的 resizeStorage 接口。存储修改成功，将数据保存至CMDBuild 中；修改失败，返回错误提示至页面

			// 创建订单(订单开始时间和服务时间和CS2的创建时间相同)
			ConsumptionsDTO consumptionsDTO = new ConsumptionsDTO();

			consumptionsDTO.setId(0);
			consumptionsDTO.setTenants(as2DTO.getTenants());
			consumptionsDTO.setBeginDate(as2DTO.getBeginDate());
			consumptionsDTO.setServiceEnd(as2DTO.getBeginDate());
			// consumptionsDTO.setIdentifier("??");
			// consumptionsDTO.setServiceType(LookUpConstants.ServiceType.);//没有找到as3
			consumptionsDTO.setConsumptionsStatus(LookUpConstants.ConsumptionsStatus.Execution.getValue());

			result = financialSoapServiceImpl.createConsumptions(consumptionsDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult operateAS2(@WebParam(name = "as2DTO") As2DTO as2DTO) {
		return null;
	}

	@Override
	public IdResult ModifyAS2Attributes(@WebParam(name = "as2DTO") As2DTO as2DTO) {
		return null;
	}

	@Override
	public DTOListResult<As2DTO> reportAS2(Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<EcsDTO> findECS(@WebParam(name = "id") Integer id) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(id);

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(financialSoapServiceImpl.findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(infrastructureSoapServiceImpl.findServer(dto.getServer()).getDto());

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
	public DTOResult<EcsDTO> findECSByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ecs ecs = comm.ecsService.findEcs(searchParams);

			Validate.notNull(ecs, ERROR.OBJECT_NULL);

			EcsDTO dto = BeanMapper.map(ecs, EcsDTO.class);

			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setEcsSpecDTO(financialSoapServiceImpl.findEcsSpec(dto.getEcsSpec()).getDto());
			dto.setServerDTO(infrastructureSoapServiceImpl.findServer(dto.getServer()).getDto());

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
	public IdResult createECS(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
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

			// 调用 ECS Agent 中的 cloneVM 接口。虚拟机创建成功，将数据保存至 CMDBuild 中；
			// 创建失败，返回错误提示至页面。

			// 分配 IP。
			// 创建订单。
			// 为虚拟机创建监控。

			return new IdResult(ecs.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsDTO> getECSList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EcsDTO> result = new DTOListResult<EcsDTO>();

		try {

			List<Ecs> ecs = comm.ecsService.getEcsList(searchParams);

			List<EcsDTO> list = BeanMapper.mapList(ecs, EcsDTO.class);

			result.setDtos(list);

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
	public IdResult resizeECSs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		return null;
	}

	@Override
	public IdResult DestroyECSs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		return null;
	}

	@Override
	public IdResult powerOpsECS(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		return null;
	}

	@Override
	public IdResult ModifyECSAttributes(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		return null;
	}

	@Override
	public DTOListResult<EcsDTO> reportECS(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id) {
		DTOResult<EipDTO> result = new DTOResult<EipDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Eip eip = comm.eipService.findEip(id);

			Validate.notNull(eip, ERROR.OBJECT_NULL);

			EipDTO dto = BeanMapper.map(eip, EipDTO.class);

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(financialSoapServiceImpl.findEipSpec(dto.getEipSpec()).getDto());

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

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());
			dto.setIpaddressDTO(infrastructureSoapServiceImpl.findIpaddress(dto.getIpaddress()).getDto());
			dto.setEipSpecDTO(financialSoapServiceImpl.findEipSpec(dto.getEipSpec()).getDto());

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

			return new IdResult(eip.getId());

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
			Validate.isTrue(comm.eipService.findEip(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipDTO, Eip.class), eip);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));

			eip.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eip.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, eip);

			comm.eipService.saveOrUpdate(eip);

			return new IdResult(eip.getId());

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

			Validate.isTrue(eip != null, ERROR.OBJECT_NULL);

			eip.setIdClass(TableNameUtil.getTableName(Eip.class));

			eip.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.eipService.saveOrUpdate(eip);

			return new IdResult(eip.getId());

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

			List<Eip> eip = comm.eipService.getEipList(searchParams);

			List<EipDTO> list = BeanMapper.mapList(eip, EipDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult resizeEIP(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult allocateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult associateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult dissociateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult changeEIPBandwidth(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult operateEIP(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public IdResult ModifyEIPAttributes(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		return null;
	}

	@Override
	public DTOListResult<EipDTO> reportEIP(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id) {
		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Elb elb = comm.elbService.findElb(id);

			Validate.notNull(elb, ERROR.OBJECT_NULL);

			ElbDTO dto = BeanMapper.map(elb, ElbDTO.class);

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

			return new IdResult(elb.getId());

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
			Validate.isTrue(comm.elbService.findElb(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(elbDTO, Elb.class), elb);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));

			elb.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			elb.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, elb);

			comm.elbService.saveOrUpdate(elb);

			return new IdResult(elb.getId());

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

			Validate.isTrue(elb != null, ERROR.OBJECT_NULL);

			elb.setIdClass(TableNameUtil.getTableName(Elb.class));

			elb.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.elbService.saveOrUpdate(elb);

			return new IdResult(elb.getId());

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

			List<Elb> elb = comm.elbService.getElbList(searchParams);

			List<ElbDTO> list = BeanMapper.mapList(elb, ElbDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult allocateELB(@WebParam(name = "elbDTO") ElbDTO elbDTO) {
		return null;
	}

	@Override
	public IdResult modifyELBAttributes(@WebParam(name = "elbDTO") ElbDTO elbDTO) {
		return null;
	}

	@Override
	public DTOListResult<ElbDTO> reportELB(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id) {
		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Dns dns = comm.dnsService.findDns(id);

			Validate.notNull(dns, ERROR.OBJECT_NULL);

			DnsDTO dto = BeanMapper.map(dns, DnsDTO.class);

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

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

			dto.setTagDTO(cmdbuildSoapServiceImpl.findTag(dto.getTag()).getDto());
			dto.setTenantsDTO(cmdbuildSoapServiceImpl.findTenants(dto.getTenants()).getDto());

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

			return new IdResult(dns.getId());

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
			Validate.isTrue(comm.dnsService.findDns(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(dnsDTO, Dns.class), dns);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));

			dns.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			dns.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, dns);

			comm.dnsService.saveOrUpdate(dns);

			return new IdResult(dns.getId());

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

			Validate.isTrue(dns != null, ERROR.OBJECT_NULL);

			dns.setIdClass(TableNameUtil.getTableName(Dns.class));

			dns.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.dnsService.saveOrUpdate(dns);

			return new IdResult(dns.getId());

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

			List<Dns> dns = comm.dnsService.getDnsList(searchParams);

			List<DnsDTO> list = BeanMapper.mapList(dns, DnsDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult modifyDNSAttributes(@WebParam(name = "DnsDTO") DnsDTO DnsDTO) {
		return null;
	}

	@Override
	public DTOListResult<DnsDTO> reportDNS(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id) {
		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Esg esg = comm.esgService.findEsg(id);

			Validate.notNull(esg, ERROR.OBJECT_NULL);

			EsgDTO dto = BeanMapper.map(esg, EsgDTO.class);

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

			return new IdResult(esg.getId());

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
			Validate.isTrue(comm.esgService.findEsg(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(esgDTO, Esg.class), esg);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));

			esg.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			esg.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, esg);

			comm.esgService.saveOrUpdate(esg);

			return new IdResult(esg.getId());

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

			Validate.isTrue(esg != null, ERROR.OBJECT_NULL);

			esg.setIdClass(TableNameUtil.getTableName(Esg.class));

			esg.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.esgService.saveOrUpdate(esg);

			return new IdResult(esg.getId());

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

			List<Esg> esg = comm.esgService.getEsgList(searchParams);

			List<EsgDTO> list = BeanMapper.mapList(esg, EsgDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult ModifyESGAttributes(@WebParam(name = "esgDTO") EsgDTO esgDTO) {
		return null;
	}

	@Override
	public DTOListResult<EsgDTO> reportESG(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	public DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id) {
		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vpn vpn = comm.vpnService.findVpn(id);

			Validate.notNull(vpn, ERROR.OBJECT_NULL);

			VpnDTO dto = BeanMapper.map(vpn, VpnDTO.class);

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

			return new IdResult(vpn.getId());

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
			Validate.isTrue(comm.vpnService.findVpn(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vpnDTO, Vpn.class), vpn);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));

			vpn.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			vpn.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, vpn);

			comm.vpnService.saveOrUpdate(vpn);

			return new IdResult(vpn.getId());

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

			Validate.isTrue(vpn != null, ERROR.OBJECT_NULL);

			vpn.setIdClass(TableNameUtil.getTableName(Vpn.class));

			vpn.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.vpnService.saveOrUpdate(vpn);

			return new IdResult(vpn.getId());

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

			List<Vpn> vpn = comm.vpnService.getVpnList(searchParams);

			List<VpnDTO> list = BeanMapper.mapList(vpn, VpnDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult ModifyESGAttributes(@WebParam(name = "vpnDTO") VpnDTO vpnDTO) {
		return null;
	}

	@Override
	public IdResult changeVPNPassword(@WebParam(name = "id") Integer id) {
		return null;
	}

	@Override
	public DTOListResult<VpnDTO> reportVPN(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicy(@WebParam(name = "id") Integer id) {
		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);

			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);

			GroupPolicyDTO dto = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);

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

			return new IdResult(groupPolicy.getId());

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
			Validate.isTrue(comm.groupPolicyService.findGroupPolicy(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(groupPolicyDTO, GroupPolicy.class), groupPolicy);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));

			groupPolicy.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			groupPolicy.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, groupPolicy);

			comm.groupPolicyService.saveOrUpdate(groupPolicy);

			return new IdResult(groupPolicy.getId());

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

			Validate.isTrue(groupPolicy != null, ERROR.OBJECT_NULL);

			groupPolicy.setIdClass(TableNameUtil.getTableName(GroupPolicy.class));

			groupPolicy.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.groupPolicyService.saveOrUpdate(groupPolicy);

			return new IdResult(groupPolicy.getId());

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

			List<GroupPolicy> groupPolicy = comm.groupPolicyService.getGroupPolicyList(searchParams);

			List<GroupPolicyDTO> list = BeanMapper.mapList(groupPolicy, GroupPolicyDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult ModifyGroupPolicyAttributes(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO) {
		return null;
	}

}
