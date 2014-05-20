package com.sobey.switches.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.webservice.response.dto.ESGParameter;
import com.sobey.switches.webservice.response.dto.VlanParameter;
import com.sobey.switches.webservice.response.result.WSResult;

/**
 * 交换机对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "SwitchesSoapService", targetNamespace = WsConstants.NS)
public interface SwitchesSoapService {

	/**
	 * 在核心交换机上执行脚本，创建Vlan
	 * 
	 * @param vlanParameter
	 *            {@link VlanParameter}
	 * @return
	 */
	WSResult createVlanByCoreSwtich(@WebParam(name = "vlanParameter") VlanParameter vlanParameter);

	/**
	 * 在核心交换机上执行脚本，删除指定的Vlan
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @return
	 */
	WSResult deleteVlanByCoreSwtich(@WebParam(name = "vlanId") Integer vlanId);

	/**
	 * 在接入交换机上执行脚本，创建Vlan
	 * 
	 * @param vlanParameter
	 *            {@link VlanParameter}
	 * @return
	 */
	WSResult createVlanByAccessSwtich(@WebParam(name = "vlanParameter") VlanParameter vlanParameter);

	/**
	 * 在接入交换机上执行脚本，删除指定的Vlan
	 * 
	 * @param vlanId
	 *            Vlan编号
	 * @return
	 */
	WSResult deleteVlanByAccessSwtich(@WebParam(name = "vlanId") Integer vlanId);

	/**
	 * 在核心交换机上执行脚本，创建租户ESG安全组脚本
	 * 
	 * @param esgParameter
	 *            {@link ESGParameter}
	 * @return
	 */
	WSResult createESGBySwtich(@WebParam(name = "esgParameter") ESGParameter esgParameter);

	/**
	 * 在核心交换机上执行脚本，删除租户ESG安全组脚本
	 * 
	 * @param aclNumber
	 *            acl编号(3000-3999)
	 * @return
	 */
	WSResult deleteESGBySwtich(@WebParam(name = "aclNumber") Integer aclNumber);

}
