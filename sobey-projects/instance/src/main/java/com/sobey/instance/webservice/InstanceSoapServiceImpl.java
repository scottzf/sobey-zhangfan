package com.sobey.instance.webservice;

import java.util.HashMap;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.result.WSResult;

@WebService(serviceName = "InstanceSoapService", endpointInterface = "com.sobey.instance.webservice.InstanceSoapService", targetNamespace = WsConstants.NS)
public class InstanceSoapServiceImpl implements InstanceSoapService {

	@Autowired
	public VMService service;

	@Override
	public WSResult cloneVMByInstance(@WebParam(name = "CloneVMParameter") CloneVMParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.cloneVM(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult destroyVMByInstance(@WebParam(name = "DestroyVMParameter") DestroyVMParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.destroyVM(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult reconfigVMByInstance(@WebParam(name = "ReconfigVMParameter") ReconfigVMParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.reconfigVM(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult powerVMByInstance(@WebParam(name = "PowerVMParameter") PowerVMParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.powerVM(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public HashMap<String, String> getVMAndHostRelationByInstance() {
		return service.getVMAndHostRelation();
	}

}
