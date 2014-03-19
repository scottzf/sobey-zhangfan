package com.sobey.storage.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;
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
	 * @param createEs3Parameter
	 *            {@link CreateEs3Parameter}
	 * @return
	 */
	WSResult createEs3ByStorage(@WebParam(name = "createEs3Parameter") CreateEs3Parameter createEs3Parameter);

	/**
	 * 在netapp上执行脚本，删除Volume
	 * 
	 * @param deleteEs3Parameter
	 *            {@link DeleteEs3Parameter}
	 * @return
	 */
	WSResult deleteEs3ByStorage(@WebParam(name = "deleteEs3Parameter") DeleteEs3Parameter deleteEs3Parameter);

	/**
	 * 在netapp上执行脚本，挂载Volume
	 * 
	 * @param mountEs3Parameter
	 *            {@link MountEs3Parameter}
	 * @return
	 */
	WSResult mountEs3ByStorage(@WebParam(name = "mountEs3Parameter") MountEs3Parameter mountEs3Parameter);

	/**
	 * 在netapp上执行脚本，卸载Volume
	 * 
	 * @param umountEs3Parameter
	 *            {@link UmountEs3Parameter}
	 * @return
	 */
	WSResult umountEs3ByStorage(@WebParam(name = "umountEs3Parameter") UmountEs3Parameter umountEs3Parameter);

	/**
	 * 在netapp上执行脚本，修改Volume
	 * 
	 * @param remountEs3Parameter
	 *            {@link RemountEs3Parameter}
	 * @return
	 */
	WSResult remountEs3ByStorage(@WebParam(name = "remountEs3Parameter") RemountEs3Parameter remountEs3Parameter);

}
