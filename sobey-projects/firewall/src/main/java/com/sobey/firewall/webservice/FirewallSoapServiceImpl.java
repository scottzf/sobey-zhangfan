package com.sobey.firewall.webservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.sobey.core.utils.Identities;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.firewall.constans.MethodEnum;
import com.sobey.firewall.constans.WsConstants;
import com.sobey.firewall.service.FirewallService;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameters;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameters;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameter;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameters;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameter;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameters;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;
import com.sobey.firewall.webservice.response.result.WSResult;

@WebService(serviceName = "FirewallSoapService", endpointInterface = "com.sobey.firewall.webservice.FirewallSoapService", targetNamespace = WsConstants.NS)
public class FirewallSoapServiceImpl implements FirewallSoapService {

	private static final String password = null;
	private static final String url = null;
	private static final Integer username = null;

	@Autowired
	private FirewallService service;

	/**
	 * 获得文件的相对路径,文件名自定义.
	 * 
	 * @param input
	 * @return
	 */
	private static String getFilePath(String input) {
		return "logs/" + input + ".txt";

	}

	@Override
	public WSResult createEIPByFirewall(EIPParameter eipParameter) {

		WSResult result = new WSResult();

		// TODO 用于测试环境临时加入的一条,需要看租户的防火墙默认IP是什么
		ArrayList<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.204");
		eipParameter.setAllPolicies(allPolicies);

		String command = service.createEip(eipParameter);

		String filePath = getFilePath(eipParameter.getPrivateIP());

		TelnetUtil.execCommand(eipParameter.getUrl(), eipParameter.getUserName(), eipParameter.getPassword(), command,
				filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.createEip);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteEIPByFirewall(EIPParameter eipParameter) {

		WSResult result = new WSResult();

		// TODO 用于测试环境临时加入的一条,需要看租户的防火墙默认IP是什么
		ArrayList<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.204");
		eipParameter.setAllPolicies(allPolicies);

		String command = service.deleteEip(eipParameter);

		String filePath = getFilePath(eipParameter.getPrivateIP());

		TelnetUtil.execCommand(eipParameter.getUrl(), eipParameter.getUserName(), eipParameter.getPassword(), command,
				filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.deleteEip);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult createVPNUserByFirewall(@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter) {

		WSResult result = new WSResult();

		String command = service.createVPNUser(vpnUserParameter);

		String filePath = getFilePath(vpnUserParameter.getVpnUser());

		TelnetUtil.execCommand(vpnUserParameter.getUrl(), vpnUserParameter.getUserName(),
				vpnUserParameter.getPassword(), command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.createVPN);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteVPNUserByFirewall(@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter) {

		WSResult result = new WSResult();

		String command = service.deleteVPNUser(vpnUserParameter);

		String filePath = getFilePath(vpnUserParameter.getVpnUser());

		TelnetUtil.execCommand(vpnUserParameter.getUrl(), vpnUserParameter.getUserName(),
				vpnUserParameter.getPassword(), command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.deleteVPN);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult changeVPNUserAccesssAddressByFirewall(
			@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter) {

		WSResult result = new WSResult();

		String command = service.changeAccesssAddressIntoVPNUser(vpnUserParameter);

		String filePath = getFilePath(vpnUserParameter.getVpnUser());

		TelnetUtil.execCommand(vpnUserParameter.getUrl(), vpnUserParameter.getUserName(),
				vpnUserParameter.getPassword(), command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.changeVPN);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult ConfigSystemInterfaceByFirewall(ConfigSystemInterfaceParameter configSystemInterfaceParameter) {

		ArrayList<ConfigSystemInterfaceParameter> arrayList = new ArrayList<ConfigSystemInterfaceParameter>();
		arrayList.add(configSystemInterfaceParameter);

		ConfigSystemInterfaceParameters parameters = new ConfigSystemInterfaceParameters();
		parameters.setConfigSystemInterfaceParameters(arrayList);

		return ConfigSystemInterfaceListByFirewall(parameters);
	}

	@Override
	public WSResult ConfigSystemInterfaceListByFirewall(ConfigSystemInterfaceParameters configSystemInterfaceParameters) {

		WSResult result = new WSResult();

		String command = service.ConfigSystemInterfaceScrip(configSystemInterfaceParameters
				.getConfigSystemInterfaceParameters());

		String filePath = getFilePath(Identities.uuid());

		TelnetUtil.execCommand(url, username, password, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.ConfigSystemInterface);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult ConfigFirewallAddressParameterByFirewall(
			ConfigFirewallAddressParameter configFirewallAddressParameter) {

		ArrayList<ConfigFirewallAddressParameter> arrayList = new ArrayList<ConfigFirewallAddressParameter>();
		arrayList.add(configFirewallAddressParameter);

		ConfigFirewallAddressParameters parameters = new ConfigFirewallAddressParameters();
		parameters.setConfigFirewallAddressParameters(arrayList);

		return ConfigFirewallAddressParameterListByFirewall(parameters);
	}

	@Override
	public WSResult ConfigFirewallAddressParameterListByFirewall(
			ConfigFirewallAddressParameters configFirewallAddressParameters) {

		WSResult result = new WSResult();

		String command = service.ConfigFirewallAddressScrip(configFirewallAddressParameters
				.getConfigFirewallAddressParameters());

		String filePath = getFilePath(Identities.uuid());

		TelnetUtil.execCommand(url, username, password, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.ConfigFirewallAddress);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult ConfigFirewallPolicyParameterByFirewall(ConfigFirewallPolicyParameter configFirewallPolicyParameter) {

		ArrayList<ConfigFirewallPolicyParameter> arrayList = new ArrayList<ConfigFirewallPolicyParameter>();
		arrayList.add(configFirewallPolicyParameter);

		ConfigFirewallPolicyParameters parameters = new ConfigFirewallPolicyParameters();
		parameters.setConfigFirewallPolicyParameters(arrayList);

		return ConfigFirewallPolicyParameterListByFirewall(parameters);
	}

	@Override
	public WSResult ConfigFirewallPolicyParameterListByFirewall(
			ConfigFirewallPolicyParameters configFirewallPolicyParameters) {

		WSResult result = new WSResult();

		String command = service.ConfigFirewallPolicyScrip(configFirewallPolicyParameters
				.getConfigFirewallPolicyParameters());

		String filePath = getFilePath(Identities.uuid());

		TelnetUtil.execCommand(url, username, password, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.ConfigFirewallPolicy);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult ConfigRouterStaticParameterByFirewall(ConfigRouterStaticParameter configRouterStaticParameter) {

		ArrayList<ConfigRouterStaticParameter> arrayList = new ArrayList<ConfigRouterStaticParameter>();
		arrayList.add(configRouterStaticParameter);

		ConfigRouterStaticParameters parameters = new ConfigRouterStaticParameters();
		parameters.setConfigRouterStaticParameters(arrayList);

		return ConfigRouterStaticParameterListByFirewall(parameters);
	}

	@Override
	public WSResult ConfigRouterStaticParameterListByFirewall(ConfigRouterStaticParameters configRouterStaticParameters) {

		WSResult result = new WSResult();

		String command = service
				.ConfigRouterStaticScrip(configRouterStaticParameters.getConfigRouterStaticParameters());

		String filePath = getFilePath(Identities.uuid());

		TelnetUtil.execCommand(url, username, password, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.ConfigRouterStatic);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

}
