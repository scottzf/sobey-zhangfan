package com.sobey.sdn.service;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.parameterObject.SubnetParameter;

/**
 * SDN Agent 业务层接口
 * @author Administrator
 *
 */
public interface SDNService {
	
	public String createECS(ECS ecs,int vlanId,String hostIp,String tenantId,String vmName,Subnet subnet);
	
    /**
     * 创建子网
     * @param subnetParameter
     * @return
     */
	public String createSubnet(Subnet subnet);
	
	/**
	 * 删除子网
	 * @param subnetParameter
	 * @return
	 */
	public String deleteSubnet(SubnetParameter subnetParameter);
	
	//在子网中添加ECS
	//public String associateSubnetWithECS(SubnetParameter subnetParameter);
	
	//从子网中移除ECS
	//public String dissociateSubnetWithECS(SubnetParameter subnetParameter);
	
	
	//public String dissociateSubnetWithECS(SubnetParameter subnetParameter);
	/**
	 * 创建路由器
	 * @param router
	 * @return
	 */
	public String createRouter(Router router)throws Exception;
	/**
	 * 删除路由器
	 * @param router
	 * @return
	 */
	public String deleteRouter(Router router)throws Exception;
	
}
