package com.sobey.firewall.webservice;

import com.sobey.firewall.constans.MethodEnum;
import com.sobey.firewall.webservice.response.result.WSResult;

/**
 * 对终端返回的信息进行处理.
 * 
 * 先将huawei防火墙返回的错误提示进行归纳,将其公共的信息抽象出来,然后将执行脚本返回的信息进行对比. <br>
 * 如果包含,说明报错,返回<b>false</b>.
 * 
 * @return
 */
public class TerminalResultHandle {

	/**
	 * vpn账号不存在
	 */
	public static final String DELETE_ERROR = "delete table entry";

	public static WSResult ResultHandle(String info, MethodEnum methodEnum) {

		WSResult result = new WSResult();

		switch (methodEnum) {
		case createEip:

			break;
		case deleteEip:

			if (info.contains(DELETE_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "VIP不存在");
			}

			break;
		case createVPN:

			break;
		case deleteVPN:

			if (info.contains(DELETE_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "VPN账号不存在");
			}

			break;
		case changeVPN:

			break;
		case bingdingRouter:

			break;
		default:
			break;
		}

		return result;

	}

}
