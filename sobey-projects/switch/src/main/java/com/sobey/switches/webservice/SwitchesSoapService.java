package com.sobey.switches.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.webservice.response.dto.SwitchPolicyParameter;
import com.sobey.switches.webservice.response.result.WSResult;

/**
 * 交换机对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "SwitchesSoapService", targetNamespace = WsConstants.NS)
public interface SwitchesSoapService {

	WSResult createSingleSubnetPolicyBySwitch(
			@WebParam(name = "switchPolicyParameter") SwitchPolicyParameter switchPolicyParameter);

	WSResult createMultipleSubnetPolicyBySwitch(
			@WebParam(name = "switchPolicyParameter") SwitchPolicyParameter switchPolicyParameter);

}
