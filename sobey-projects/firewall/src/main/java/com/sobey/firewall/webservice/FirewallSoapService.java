package com.sobey.firewall.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.firewall.constans.WsConstants;
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
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @return
	 */
	WSResult createEIPByFirewall(@WebParam(name = "EIPParameter") EIPParameter parameter,
			@WebParam(name = "allPolicies") List<String> allPolicies);

	/**
	 * 在防火墙上执行脚本，删除EIP
	 * 
	 * @param parameter
	 *            {@link EIPParameter}
	 * @param allPolicies
	 *            所有EIP的映射策略.
	 * @return
	 */
	WSResult deleteEIPByFirewall(@WebParam(name = "EIPParameter") EIPParameter parameter,
			@WebParam(name = "allPolicies") List<String> allPolicies);

	/**
	 * 在防火墙上执行脚本，创建VPN User
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult createVPNUserByFirewall(@WebParam(name = "VPNUserParameter") VPNUserParameter parameter);

	/**
	 * 在防火墙上执行脚本，删除VPN User
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult deleteVPNUserByFirewall(@WebParam(name = "VPNUserParameter") VPNUserParameter parameter);

	/**
	 * 在防火墙上执行脚本，为VPUN用户组新增或删除可访问段.
	 * 
	 * @param parameter
	 *            {@link VPNUserParameter }
	 * @return
	 */
	WSResult changeVPNUserAccesssAddressByFirewall(@WebParam(name = "VPNUserParameter") VPNUserParameter parameter);

}
