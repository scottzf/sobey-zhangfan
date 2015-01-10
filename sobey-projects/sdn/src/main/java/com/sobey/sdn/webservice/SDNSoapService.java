package com.sobey.sdn.webservice;

import java.util.List;

import javax.jws.WebService;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Firewall;
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
	 * 2. vlanId
	 * 
	 * @return
	 */

	/**
	 * 创建云主机 并分配内网IP
	 * 
	 * @param ecs
	 * @param vlanId
	 * @param hostIp
	 * @param tenantId
	 * @param vmName
	 * @param subnet
	 * @throws Exception
	 */
	public void createECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet)
			throws Exception;

	/**
	 * 创建租户路由器
	 * 
	 * @param router
	 * @throws Exception
	 */
	public void createRouter(Router router, String ip_update) throws Exception;

	/**
	 * 绑定子网和路由器
	 * 
	 * @param router
	 * @param subnets
	 * @throws Exception
	 */
	public void bindingRouter(Router router, List<Subnet> subnets) throws Exception;

	/**
	 * 创建防火墙
	 * 
	 * @param router
	 * @param subnets
	 * @throws Exception
	 */
	public void createFirewall(Firewall firewall) throws Exception;

	/**
	 * 绑定路由器到防火墙
	 * 
	 * @param router
	 * @param firewall
	 * @throws Exception
	 */
	public void bindingFirewall(Router router, Firewall firewall) throws Exception;

}
