package com.sobey.sdn.service;

import com.sobey.sdn.bean.CreateEipParameter;
import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.bean.VMRCParameter;
import com.sobey.sdn.bean.VPNParameter;
import com.sobey.sdn.parameterObject.SubnetParameter;
import com.sobey.sdn.test.testParameter.BindingFirewallParameter;
import com.sobey.sdn.test.testParameter.BindingRouterParameter;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;

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
	public String createECS(CreateECSParameter createECSParameter);

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
	public String createRouter(CreateRouterParameter createRouterParameter) throws Exception;

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
	public String bindingRouter(BindingRouterParameter bindingRouterParameter) throws Exception;

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
	public void bindingFirewall(BindingFirewallParameter parameter) throws Exception;

	/**
	 * 创建EIP
	 * 
	 * @param createEipParameter
	 * @throws Exception
	 */
	public void createEip(CreateEipParameter createEipParameter) throws Exception;

	/**
	 * 创建VPN用户
	 * 
	 * @param vpnParameter
	 * @throws Exception
	 */
	public void createVPNUser(VPNParameter vpnParameter) throws Exception;

	/**
	 * 远程连接虚拟机控制台
	 * 
	 * @param vmName
	 * @throws Exception
	 */
	public VMRCParameter connectVMRC(String vmName) throws Exception;

	/**
	 * 在指定父目录下新建文件夹
	 * 
	 * @param folderName    文件夹名
	 * @param parentFolder  父目录名   若为空，则默认为vcenter的根目录
	 * @throws Exception
	 */
	public String createFolder(String folderName, String parentFolder) throws Exception;

}
