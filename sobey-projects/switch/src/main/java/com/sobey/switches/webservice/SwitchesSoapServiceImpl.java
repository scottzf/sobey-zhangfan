package com.sobey.switches.webservice;

import javax.jws.WebService;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.webservice.pojo.TelentLoginInfo;
import com.sobey.switches.webservice.response.dto.SgpDTO;
import com.sobey.switches.webservice.script.SwitchesScript;

@WebService(serviceName = "SwitchesSoapService", endpointInterface = "com.sobey.switches.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
public class SwitchesSoapServiceImpl implements SwitchesSoapService {

	private TelentLoginInfo telentLoginInfo;

	public void setTelentLoginInfo(TelentLoginInfo telentLoginInfo) {
		this.telentLoginInfo = telentLoginInfo;
	}

	@Override
	public void createTenantsVlan(Integer tenantsId, String ipAddress, String subnetMask) {

	}

	@Override
	public void createInterfaceTenants(Integer tenantsId) {

		System.out.println(telentLoginInfo.getTelentIp() + "///////////////////////");
		System.out.println(telentLoginInfo.getUsername() + "///////////////////////");
		System.out.println(telentLoginInfo.getPassword() + "///////////////////////");

		// 获取脚本，然后执行脚本
		String command = SwitchesScript.getInterfaceTenantsScript(tenantsId);

		// TelnetUtil.execCommand(telentLoginInfo.getTelentIp(), telentLoginInfo.getUsername(),
		// telentLoginInfo.getPassword(), command);

	}

	@Override
	public void createSgp(SgpDTO sgpDTO) {

	}

}
