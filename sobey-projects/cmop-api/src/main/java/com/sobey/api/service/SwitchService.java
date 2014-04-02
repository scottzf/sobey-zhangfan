package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.switches.ESGParameter;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.switches.VlanParameter;
import com.sobey.generate.switches.WSResult;

/**
 * Switch
 * 
 * @author Administrator
 * 
 */
@Service
public class SwitchService {

	@Autowired
	private SwitchesSoapService service;

	public WSResult createVlan(VlanParameter vlanParameter) {
		return service.createVlanBySwtich(vlanParameter);
	}

	public WSResult deleteVlan(Integer vlanId) {
		return service.deleteVlanBySwtich(vlanId);
	}

	public WSResult createEsg(ESGParameter esgParameter) {
		return service.createESGBySwtich(esgParameter);
	}

	public WSResult deleteEsg(Integer aclNumber) {
		return service.deleteESGBySwtich(aclNumber);
	}

}
