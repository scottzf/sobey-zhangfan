package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.RelationVMParameter.RelationMaps;
import com.sobey.generate.instance.WSResult;

/**
 * Instance
 * 
 * @author Administrator
 * 
 */
@Service
public class InstanceService {

	@Autowired
	private InstanceSoapService instanceSoapService;

	public WSResult desoroyVM(DestroyVMParameter destroyVMParameter) {
		return instanceSoapService.destroyVMByInstance(destroyVMParameter);
	}

	public RelationMaps relationVM() {
		return instanceSoapService.getVMAndHostRelationByInstance().getRelationMaps();
	}

	public WSResult cloneVM(CloneVMParameter cloneVMParameter) {
		return instanceSoapService.cloneVMByInstance(cloneVMParameter);
	}

	public WSResult powerVM(PowerVMParameter powerVMParameter) {
		return instanceSoapService.powerVMByInstance(powerVMParameter);
	}

	public WSResult reconfigVM(ReconfigVMParameter reconfigVMParameter) {
		return instanceSoapService.reconfigVMByInstance(reconfigVMParameter);
	}

}
