package com.sobey.sdn.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.constans.WsConstants;
import com.sobey.sdn.service.SDNService;

@WebService(serviceName = "SDNSoapService", endpointInterface = "com.sobey.sdn.webservice.SDNSoapService", targetNamespace = WsConstants.NS)
public class SDNSoapServiceImpl implements SDNSoapService {

	@Autowired
	private SDNService sdnService;

	@Override
	public void createECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet)
			throws Exception {

		// 根据子网为虚拟机生成内网IP
		String localIp = getLocalIpBySubnet(subnet);

		// 为云主机分配网络信息
		ecs.setSubnetMask(subnet.getSubnetMask()); // 指定子网掩码
		ecs.setGateway(subnet.getGateway()); // 指定网关
		ecs.setLocalIp(localIp); // 指定内网IP
		// 1.创建端口组

		sdnService.createECS(ecs, vlanId, hostIp, tenantId, vmName, subnet);
	}

	@Override
	public void createRouter(Router router,String ip_update) throws Exception {

		sdnService.createRouter(router,ip_update);
		// TODO Auto-generated method stub

	}

	@Override
	public void bindingRouter(Router router, List<Subnet> subnets) throws Exception {

		/**
		 * 暂时只实现两个子网之间的通信
		 */

		// 防火墙ip地址
		String ip = router.getLocalIp(); // 参数

	}

	private String getLocalIpBySubnet(Subnet subnet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createFirewall(Firewall firewall) throws Exception {
		sdnService.createFirewall(firewall);
	}

	@Override
	public void bindingFirewall(Router router, Firewall firewall) throws Exception {
		//sdnService.bindingFirewall(router,firewall);
	}
}
