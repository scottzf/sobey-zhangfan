package com.sobey.sdn.service;

import java.util.List;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.parameterObject.SubnetParameter;

/**
 * SDN Agent 业务层接口
 * 
 * @author Administrator
 *
 */
public interface SDNService {
    /**
     * 创建云主机
     * 
     * @param ecs
     * @param vlanId
     * @param hostIp
     * @param tenantId
     * @param vmName
     * @param subnet
     * @return
     */
	public String createECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet);

	/**
	 * 创建子网
	 * 
	 * @param subnetParameter
	 * @return
	 */
	public String createSubnet(Subnet subnet);

	/**
	 * 删除子网
	 * 
	 * @param subnetParameter
	 * @return
	 */
	public String deleteSubnet(SubnetParameter subnetParameter);

	// 在子网中添加ECS
	// public String associateSubnetWithECS(SubnetParameter subnetParameter);

	// 从子网中移除ECS
	// public String dissociateSubnetWithECS(SubnetParameter subnetParameter);

	// public String dissociateSubnetWithECS(SubnetParameter subnetParameter);
	/**
	 * 创建路由器
	 * 
	 * @param router
	 * @return
	 */
	public String createRouter(Router router,String ip_update) throws Exception;

	/**
	 * 删除路由器
	 * 
	 * @param router
	 * @return
	 */
	public String deleteRouter(Router router) throws Exception;

	/**
	 * 绑定路由器和子网
	 * 
	 * @param router
	 * @param subnets
	 * @throws Exception
	 */
	public String bindingRouter(Router router, List<Subnet> subnets) throws Exception;

	/**
	 * 创建防火墙
	 * 
	 * @param firewall
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
	public void bindingFirewall(Router router, Firewall firewall,String ip_ISP) throws Exception;
	
	//public void createLoadBalancer(Firewall firewall) throws Exception;

}
