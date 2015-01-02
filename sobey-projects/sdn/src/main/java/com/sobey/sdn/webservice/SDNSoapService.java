package com.sobey.sdn.webservice;

import java.util.List;

import javax.jws.WebService;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.WsConstants;

/**
 * SDN对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "SDNSoapService", targetNamespace = WsConstants.NS)
public interface SDNSoapService {

	/**
	 * 1.创建子网 保存至CMDB
	 * 
	 * 2.创建云主机 内网IP vlanId
	 * 
	 * @return
	 */

	public void creatECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet) throws Exception;

	public void createRouter(Router router) throws Exception;

	public void bindingRouter(Router router, List<Subnet> subnets)throws Exception;

}
