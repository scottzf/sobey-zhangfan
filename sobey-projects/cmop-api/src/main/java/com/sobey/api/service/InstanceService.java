package com.sobey.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.RelationVMParameter.RelationMaps.Entry;
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
	private InstanceSoapService service;

	public WSResult desoroyVM(DestroyVMParameter destroyVMParameter) {
		return service.destroyVMByInstance(destroyVMParameter);
	}

	public HashMap<String, String> relationVM() {

		HashMap<String, String> map = new HashMap<String, String>();

		// 将RelationVMParameter转换成HashMap

		for (Entry entry : service.getVMAndHostRelationByInstance().getRelationMaps().getEntry()) {
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	public WSResult cloneVM(CloneVMParameter cloneVMParameter) {
		return service.cloneVMByInstance(cloneVMParameter);
	}

	public WSResult powerVM(PowerVMParameter powerVMParameter) {
		return service.powerVMByInstance(powerVMParameter);
	}

	public WSResult reconfigVM(ReconfigVMParameter reconfigVMParameter) {
		return service.reconfigVMByInstance(reconfigVMParameter);
	}

}
