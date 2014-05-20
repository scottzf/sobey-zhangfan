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

	public WSResult createVlanInCore(VlanParameter vlanParameter) {
		return service.createVlanByCoreSwtich(vlanParameter);
	}

	public WSResult deleteVlanInCore(Integer vlanId) {
		return service.deleteVlanByCoreSwtich(vlanId);
	}

	public WSResult createVlanInAccess(VlanParameter vlanParameter) {
		return service.createVlanByAccessSwtich(vlanParameter);
	}

	public WSResult deleteVlanInAccess(Integer vlanId) {
		return service.deleteVlanByAccessSwtich(vlanId);
	}

	public WSResult createEsg(ESGParameter esgParameter) {
		return service.createESGBySwtich(esgParameter);
	}

	public WSResult deleteEsg(Integer aclNumber) {
		return service.deleteESGBySwtich(aclNumber);
	}

}
