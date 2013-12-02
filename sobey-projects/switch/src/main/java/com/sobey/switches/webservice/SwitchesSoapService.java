package com.sobey.switches.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.webservice.response.dto.SgpDTO;

/**
 * 交换机和防火墙接口
 */
@WebService(name = "SwitchesSoapService", targetNamespace = WsConstants.NS)
public interface SwitchesSoapService {

	/**
	 * 核心交换机创建租户VLAN及VLAN网关地址<br/>
	 * 
	 * @param tenantsId
	 *            租户id
	 * @param ipAddress
	 *            ip地址
	 * @param subnetMask
	 *            子网掩码
	 */
	void createTenantsVlan(@WebParam(name = "tenantsId") Integer tenantsId,
			@WebParam(name = "ipAddress") String ipAddress, @WebParam(name = "subnetMask") String subnetMask);

	/**
	 * 接入层交换机创建租户VLAN<br/>
	 * 
	 * @param tenantsId
	 *            租户id
	 */
	void createInterfaceTenants(@WebParam(name = "tenantsId") Integer tenantsId);

	/**
	 * 多租户之间SGP安全组IP控制
	 * 
	 * @param sgpDTO
	 */
	void createSgp(@WebParam(name = "sgpDTO") SgpDTO sgpDTO);

}
