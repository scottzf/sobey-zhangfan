package com.sobey.firewall.webservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.sobey.core.utils.SSHUtil;
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
	private static final String username = null;

	private static Logger logger = LoggerFactory.getLogger(FirewallSoapServiceImpl.class);

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

		String command = service.configSystemInterfaceScrip(configSystemInterfaceParameters
				.getConfigSystemInterfaceParameters());

		try {
			SSHUtil.executeCommand(url, username, password, command);
		} catch (IOException e) {
			logger.info("ConfigSystemInterfaceListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
		}

		return result;
	}

	@Override
	public WSResult ModifyConfigSystemInterfaceByFirewall(ConfigSystemInterfaceParameter configSystemInterfaceParameter) {

		ArrayList<ConfigSystemInterfaceParameter> arrayList = new ArrayList<ConfigSystemInterfaceParameter>();
		arrayList.add(configSystemInterfaceParameter);

		ConfigSystemInterfaceParameters parameters = new ConfigSystemInterfaceParameters();
		parameters.setConfigSystemInterfaceParameters(arrayList);

		return ModifyConfigSystemInterfaceListByFirewall(parameters);

	}

	@Override
	public WSResult ModifyConfigSystemInterfaceListByFirewall(
			ConfigSystemInterfaceParameters configSystemInterfaceParameters) {

		WSResult result = new WSResult();

		String command = service.modifyConfigSystemInterfaceScrip(configSystemInterfaceParameters
				.getConfigSystemInterfaceParameters());

		try {
			SSHUtil.executeCommand(url, username, password, command);
		} catch (IOException e) {
			logger.info("ConfigFirewallPolicyParameterListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
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

		String command = service.configFirewallAddressScrip(configFirewallAddressParameters
				.getConfigFirewallAddressParameters());

		try {
			SSHUtil.executeCommand(url, username, password, command);
		} catch (IOException e) {
			logger.info("ConfigFirewallAddressParameterListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
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

		String command = service.configFirewallPolicyScrip(configFirewallPolicyParameters
				.getConfigFirewallPolicyParameters());

		try {
			SSHUtil.executeCommand(url, username, password, command);
		} catch (IOException e) {
			logger.info("ConfigFirewallPolicyParameterListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
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
				.configRouterStaticScrip(configRouterStaticParameters.getConfigRouterStaticParameters());

		try {
			SSHUtil.executeCommand(url, username, password, command);
		} catch (IOException e) {
			logger.info("ConfigRouterStaticParameterListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
		}

		return result;
	}

	@Override
	public WSResult RegisteredByFirewall() {

		WSResult result = new WSResult();

		try {
			SSHUtil.executeCommand(url, username, password, service.RegisteredScrip());
		} catch (IOException e) {
			logger.info("RegisteredByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
		}
		return result;
	}

}
