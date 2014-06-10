package com.sobey.switches.webservice;

import com.sobey.switches.constans.MethodEnum;
import com.sobey.switches.webservice.response.result.WSResult;

/**
 * 对终端返回的信息进行处理.
 * 
 * 先将H3C交换机返回的错误提示进行归纳,将其公共的信息抽象出来,然后将执行脚本返回的信息进行对比. <br>
 * 如果包含,说明报错,返回<b>false</b>.
 * 
 * @return
 */
public class TerminalResultHandle {

	/**
	 * 创建Acl之前需要Vlan
	 */
	public static final String CREATEACL_ERROR = "This VLAN must exist before creating the Interface VLAN";
	public static final String VLAN_ERROR = "VLAN(s) do(es) not exist.";
	public static final String IP_OVERLAPS_ERROR = "Error: The IP address you entered overlaps with another interface!";

	public static WSResult ResultHandle(String info, MethodEnum methodEnum) {

		WSResult result = new WSResult();

		switch (methodEnum) {
		case createVlan:

			if (info.contains(IP_OVERLAPS_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "IP在交换机中已存在");
			}

			break;
		case deleteVlan:
			if (info.contains(VLAN_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "Vlan不存在");
			}

			break;
		case createAcl:

			if (info.contains(CREATEACL_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "创建Acl之前需要Vlan");
			}

			break;
		case deleteAcl:

			// 删除acl不会报错,即使交换机中没有.

			break;
		default:
			break;
		}

		return result;

	}

}
