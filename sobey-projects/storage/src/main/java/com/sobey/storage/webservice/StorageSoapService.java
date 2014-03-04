package com.sobey.storage.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.webservice.response.dto.NetAppParameter;
import com.sobey.storage.webservice.response.result.WSResult;

/**
 * netapp对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "StorageSoapService", targetNamespace = WsConstants.NS)
public interface StorageSoapService {

	/**
	 * 在netapp上执行脚本，创建Volume
	 * 
	 * @param netAppParameter
	 *            {@link NetAppParameter}
	 * @return
	 */
	WSResult createEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter);

	/**
	 * 在netapp上执行脚本，删除Volume
	 * 
	 * @param netAppParameter
	 *            {@link NetAppParameter}
	 * @return
	 */
	WSResult deleteEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter);

	/**
	 * 在netapp上执行脚本，挂载Volume
	 * 
	 * @param netAppParameter
	 *            {@link NetAppParameter}
	 * @return
	 */
	WSResult mountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter);

	/**
	 * 在netapp上执行脚本，卸载Volume
	 * 
	 * @param netAppParameter
	 *            {@link NetAppParameter}
	 * @return
	 */
	WSResult umountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter);

	/**
	 * 在netapp上执行脚本，修改Volume
	 * 
	 * @param netAppParameter
	 *            {@link NetAppParameter}
	 * @return
	 */
	WSResult remountEs3ByStorage(@WebParam(name = "netAppParameter") NetAppParameter netAppParameter);

}
