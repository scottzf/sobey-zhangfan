package com.sobey.firewall.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.firewall.constans.WsConstants;
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

/**
 * 防火墙firewall对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "FirewallSoapService", targetNamespace = WsConstants.NS)
public interface FirewallSoapService {

	/**
	 * 在防火墙上执行脚本，创建EIP
	 * 
	 * @param eipParameter
	 *            {@link EIPParameter}
	 * @return
	 */
	WSResult createEIPByFirewall(@WebParam(name = "eipParameter") EIPParameter eipParameter);

	/**
	 * 在防火墙上执行脚本，删除EIP
	 * 
	 * @param eipParameter
	 *            {@link EIPParameter}
	 * @return
	 */
	WSResult deleteEIPByFirewall(@WebParam(name = "eipParameter") EIPParameter eipParameter);

	/**
	 * 在防火墙上执行脚本，创建VPN User
	 * 
	 * @param vpnUserParameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult createVPNUserByFirewall(@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter);

	/**
	 * 在防火墙上执行脚本，删除VPN User
	 * 
	 * @param vpnUserParameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult deleteVPNUserByFirewall(@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter);

	/**
	 * 在防火墙上执行脚本，为VPNUser组新增或删除可访问段.
	 * 
	 * @param vpnUserParameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult changeVPNUserAccesssAddressByFirewall(
			@WebParam(name = "vpnUserParameter") VPNUserParameter vpnUserParameter);

	WSResult ConfigSystemInterfaceByFirewall(
			@WebParam(name = "configSystemInterfaceParameter") ConfigSystemInterfaceParameter configSystemInterfaceParameter);

	WSResult ConfigSystemInterfaceListByFirewall(
			@WebParam(name = "configSystemInterfaceParameters") ConfigSystemInterfaceParameters configSystemInterfaceParameters);

	WSResult ModifyConfigSystemInterfaceByFirewall(
			@WebParam(name = "configSystemInterfaceParameter") ConfigSystemInterfaceParameter configSystemInterfaceParameter);

	WSResult ConfigFirewallAddressParameterByFirewall(
			@WebParam(name = "configFirewallAddressParameter") ConfigFirewallAddressParameter configFirewallAddressParameter);

	WSResult ConfigFirewallAddressParameterListByFirewall(
			@WebParam(name = "configFirewallAddressParameters") ConfigFirewallAddressParameters configFirewallAddressParameters);

	WSResult ConfigFirewallPolicyParameterByFirewall(
			@WebParam(name = "configFirewallPolicyParameter") ConfigFirewallPolicyParameter configFirewallPolicyParameter);

	WSResult ConfigFirewallPolicyParameterListByFirewall(
			@WebParam(name = "configFirewallPolicyParameters") ConfigFirewallPolicyParameters configFirewallPolicyParameters);

	WSResult ConfigRouterStaticParameterByFirewall(
			@WebParam(name = "configRouterStaticParameter") ConfigRouterStaticParameter configRouterStaticParameter);

	WSResult ConfigRouterStaticParameterListByFirewall(
			@WebParam(name = "configRouterStaticParameters") ConfigRouterStaticParameters configRouterStaticParameters);

	WSResult ConfigFirewallServiceCategoryParameterByFirewall(
			@WebParam(name = "configFirewallServiceCategoryParameter") ConfigFirewallServiceCategoryParameter configFirewallServiceCategoryParameter);

	WSResult ConfigFirewallServiceCategoryParameterListByFirewall(
			@WebParam(name = "configFirewallServiceCategoryParameters") ConfigFirewallServiceCategoryParameters configFirewallServiceCategoryParameters);

	WSResult RegisteredByFirewall(
			@WebParam(name = "authenticateFirewallParameter") AuthenticateFirewallParameter authenticateFirewallParameter);

	WSResult PurgeConfigFirewallPolicyByFirewall(
			@WebParam(name = "authenticateFirewallParameter") AuthenticateFirewallParameter authenticateFirewallParameter);

}
