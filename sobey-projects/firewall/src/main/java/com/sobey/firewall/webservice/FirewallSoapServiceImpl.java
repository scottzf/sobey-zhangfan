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
import com.sobey.firewall.webservice.response.dto.AuthenticateFirewallParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallAddressParameters;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallPolicyParameters;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallServiceCategoryParameter;
import com.sobey.firewall.webservice.response.dto.ConfigFirewallServiceCategoryParameters;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameter;
import com.sobey.firewall.webservice.response.dto.ConfigRouterStaticParameters;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameter;
import com.sobey.firewall.webservice.response.dto.ConfigSystemInterfaceParameters;
import com.sobey.firewall.webservice.response.dto.EIPParameter;
import com.sobey.firewall.webservice.response.dto.VPNUserParameter;
import com.sobey.firewall.webservice.response.result.WSResult;

@WebService(serviceName = "FirewallSoapService", endpointInterface = "com.sobey.firewall.webservice.FirewallSoapService", targetNamespace = WsConstants.NS)
public class FirewallSoapServiceImpl implements FirewallSoapService {

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

		String command = service.configFirewallVIPScrip(eipParameter);

		TelnetUtil.execCommand(eipParameter.getUrl(), eipParameter.getUserName(), eipParameter.getPassword(), command);

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

		String command = service.configSystemInterfaceScrip(configSystemInterfaceParameters);

		TelnetUtil.execCommand(configSystemInterfaceParameters.getUrl(), configSystemInterfaceParameters.getUserName(),
				configSystemInterfaceParameters.getPassword(), command);

		return result;
	}

	@Override
	public WSResult ModifyConfigSystemInterfaceByFirewall(ConfigSystemInterfaceParameter configSystemInterfaceParameter) {

		WSResult result = new WSResult();

		String command = service.modifyConfigSystemInterfaceScrip(configSystemInterfaceParameter);

		TelnetUtil.execCommand(configSystemInterfaceParameter.getUrl(), configSystemInterfaceParameter.getUserName(),
				configSystemInterfaceParameter.getPassword(), command);

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

		String command = service.configFirewallAddressScrip(configFirewallAddressParameters);

		TelnetUtil.execCommand(configFirewallAddressParameters.getUrl(), configFirewallAddressParameters.getUserName(),
				configFirewallAddressParameters.getPassword(), command);

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

		String command = service.configFirewallPolicyScrip(configFirewallPolicyParameters);

		TelnetUtil.execCommand(configFirewallPolicyParameters.getUrl(), configFirewallPolicyParameters.getUserName(),
				configFirewallPolicyParameters.getPassword(), command);

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

		String command = service.configRouterStaticScrip(configRouterStaticParameters);

		TelnetUtil.execCommand(configRouterStaticParameters.getUrl(), configRouterStaticParameters.getUserName(),
				configRouterStaticParameters.getPassword(), command);

		return result;
	}

	@Override
	public WSResult RegisteredByFirewall(AuthenticateFirewallParameter authenticateFirewallParameter) {

		WSResult result = new WSResult();

		TelnetUtil.execCommand(authenticateFirewallParameter.getUrl(), authenticateFirewallParameter.getUserName(),
				authenticateFirewallParameter.getPassword(), service.RegisteredScrip());

		return result;
	}

	@Override
	public WSResult ConfigFirewallServiceCategoryParameterByFirewall(
			ConfigFirewallServiceCategoryParameter configFirewallServiceCategoryParameter) {

		ArrayList<ConfigFirewallServiceCategoryParameter> arrayList = new ArrayList<ConfigFirewallServiceCategoryParameter>();
		arrayList.add(configFirewallServiceCategoryParameter);

		ConfigFirewallServiceCategoryParameters parameters = new ConfigFirewallServiceCategoryParameters();
		parameters.setConfigFirewallServiceCategoryParameters(arrayList);

		return ConfigFirewallServiceCategoryParameterListByFirewall(parameters);
	}

	@Override
	public WSResult ConfigFirewallServiceCategoryParameterListByFirewall(
			ConfigFirewallServiceCategoryParameters configFirewallServiceCategoryParameters) {
		WSResult result = new WSResult();

		String command = service.ConfigFirewallServiceCategoryScrip(configFirewallServiceCategoryParameters
				.getConfigFirewallServiceCategoryParameters());

		TelnetUtil.execCommand(configFirewallServiceCategoryParameters.getUrl(),
				configFirewallServiceCategoryParameters.getUserName(),
				configFirewallServiceCategoryParameters.getPassword(), command);

		return result;
	}

	@Override
	public WSResult PurgeConfigFirewallPolicyByFirewall(AuthenticateFirewallParameter authenticateFirewallParameter) {

		WSResult result = new WSResult();

		String command = service.PurgeConfigFirewallPolicyScrip(authenticateFirewallParameter);

		try {
			SSHUtil.executeCommand(authenticateFirewallParameter.getUrl(), authenticateFirewallParameter.getUserName(),
					authenticateFirewallParameter.getPassword(), command);
		} catch (IOException e) {
			logger.info("ConfigFirewallServiceCategoryParameterListByFirewall::" + e.getMessage());
			result.setError(WSResult.SYSTEM_ERROR, "IOException,请联系系统管理员");
		}

		return result;
	}

}
