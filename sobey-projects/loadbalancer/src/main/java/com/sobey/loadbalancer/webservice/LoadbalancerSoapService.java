package com.sobey.loadbalancer.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.loadbalancer.constans.WsConstants;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ElbSync;
import com.sobey.loadbalancer.webservice.response.result.DTOListResult;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

/**
 * Loadbalancer对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "LoadbalancerSoapService", targetNamespace = WsConstants.NS)
public interface LoadbalancerSoapService {

	/**
	 * 在Loadbalancer上创建ELB
	 * 
	 * @param parameter
	 *            {@link ELBParameter}
	 * @return
	 */
	WSResult createELBByLoadbalancer(@WebParam(name = "elbParameter") ELBParameter elbParameter);

	/**
	 * 在Loadbalancer上删除ELB
	 * 
	 * @param parameter
	 *            {@link ELBParameter}
	 * @return
	 */
	WSResult deleteELBByLoadbalancer(@WebParam(name = "elbParameter") ELBParameter elbParameter);

	/**
	 * 获得物理设备上elb相关的所有配置信息. 参数对象中,只需要填写登录相关的 Url,Username,protocol,Port.
	 * 
	 * @param parameter
	 *            {@link ELBParameter}
	 * @return
	 */
	DTOListResult<ElbSync> getELBConfig(@WebParam(name = "elbParameter") ELBParameter elbParameter);

}
