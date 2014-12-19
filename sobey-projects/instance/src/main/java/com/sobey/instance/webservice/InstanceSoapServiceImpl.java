package com.sobey.instance.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.CreateVMDiskParameter;
import com.sobey.instance.webservice.response.dto.DeleteVMDiskParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.HostsDTO;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.dto.VMInfoDTO;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;

@WebService(serviceName = "InstanceSoapService", endpointInterface = "com.sobey.instance.webservice.InstanceSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class InstanceSoapServiceImpl implements InstanceSoapService {

	@Autowired
	public VMService service;

	@Override
	public WSResult cloneVMByInstance(@WebParam(name = "cloneVMParameter") CloneVMParameter cloneVMParameter) {

		WSResult result = new WSResult();

		boolean flag = service.cloneVM(cloneVMParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "克隆失败");
		}

		if (!service.changeVlan(cloneVMParameter.getDatacenter(), cloneVMParameter.getvMName(),
				cloneVMParameter.getVlanId())) {
			result.setError(WSResult.SYSTEM_ERROR, "Vlan分配失败");
		}

		return result;
	}

	@Override
	public WSResult destroyVMByInstance(@WebParam(name = "destroyVMParameter") DestroyVMParameter destroyVMParameter) {

		WSResult result = new WSResult();

		boolean flag = service.destroyVM(destroyVMParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "销毁失败");
		}

		return result;
	}

	@Override
	public WSResult reconfigVMByInstance(@WebParam(name = "reconfigVMParameter") ReconfigVMParameter reconfigVMParameter) {

		WSResult result = new WSResult();

		boolean flag = service.reconfigVM(reconfigVMParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "配置更改失败");
		}

		return result;
	}

	@Override
	public WSResult powerVMByInstance(@WebParam(name = "powerVMParameter") PowerVMParameter powerVMParameter) {

		WSResult result = new WSResult();

		boolean flag = service.powerVM(powerVMParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "电源操作失败");
		}

		return result;
	}

	@Override
	public RelationVMParameter getVMAndHostRelationByInstance(@WebParam(name = "datacenter") String datacenter) {
		return service.getVMAndHostRelation(datacenter);
	}

	@Override
	public WSResult createPortGroupByInstance(Integer vlanId, String datacenter) {

		WSResult result = new WSResult();
		boolean flag = service.addDVSPortGroup(vlanId, datacenter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "分布式端口组创建失败");
		}

		return result;
	}

	@Override
	public DTOResult<VMInfoDTO> getVMInfoDTO(String vmName, String datacenter) {
		DTOResult<VMInfoDTO> result = new DTOResult<VMInfoDTO>();
		result.setDto(service.getVMInfoDTO(vmName, datacenter));
		return result;
	}

	@Override
	public WSResult createES3ByInstance(CreateVMDiskParameter createVMDiskParameter) {

		WSResult result = new WSResult();

		boolean flag = service.createVMDisk(createVMDiskParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "存储创建失败");
		}

		return result;
	}

	@Override
	public WSResult deleteES3ByInstance(DeleteVMDiskParameter deleteVMDiskParameter) {
		WSResult result = new WSResult();

		boolean flag = service.deleteVMDisk(deleteVMDiskParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "存储删除失败");
		}

		return result;
	}

	@Override
	public HostsDTO getHostsDTO(String datacenter) {
		HostsDTO dto = new HostsDTO();
		dto.setHostName(service.getHost(datacenter));
		return dto;
	}

}
