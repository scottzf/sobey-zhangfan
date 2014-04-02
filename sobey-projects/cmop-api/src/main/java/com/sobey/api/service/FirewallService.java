package com.sobey.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.firewall.EIPParameter;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.firewall.VPNUserParameter;
import com.sobey.generate.firewall.WSResult;

/**
 * Firewall
 * 
 * @author Administrator
 * 
 */
@Service
public class FirewallService {

	@Autowired
	private FirewallSoapService service;

	public WSResult createVPNUser(VPNUserParameter vpnUserParameter) {
		return service.createVPNUserByFirewall(vpnUserParameter);
	}

	public WSResult deleteVPNUser(VPNUserParameter vpnUserParameter) {
		return service.deleteVPNUserByFirewall(vpnUserParameter);
	}

	public WSResult changeVPNUserAccesssAddress(VPNUserParameter vpnUserParameter) {
		return service.changeVPNUserAccesssAddressByFirewall(vpnUserParameter);
	}

	public WSResult createEip(EIPParameter eipParameter, List<String> allPolicies) {
		return service.createEIPByFirewall(eipParameter, allPolicies);
	}

	public WSResult deleteEip(EIPParameter eipParameter, List<String> allPolicies) {
		return service.deleteEIPByFirewall(eipParameter, allPolicies);
	}

}
