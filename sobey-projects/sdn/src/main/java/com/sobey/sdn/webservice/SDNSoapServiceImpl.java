package com.sobey.sdn.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.TelnetUtil;
import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.constans.WsConstants;
import com.sobey.sdn.service.impl.SDNServiceImpl;

@WebService(serviceName = "SDNSoapService", endpointInterface = "com.sobey.sdn.webservice.SDNSoapService", targetNamespace = WsConstants.NS)
public class SDNSoapServiceImpl implements SDNSoapService {

	@Autowired
	private SDNServiceImpl service;

	@Override
	public void creatECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet)
			throws Exception {

		// 根据子网为虚拟机生成内网IP
		String localIp = getLocalIpBySubnet(subnet);

		// 为云主机分配网络信息
		ecs.setSubnetMask(subnet.getSubnetMask()); // 指定子网掩码
		ecs.setGateway(subnet.getGateway()); // 指定网关
		ecs.setLocalIp(localIp); // 指定内网IP
		// 1.创建端口组

		service.createECS(ecs, vlanId, hostIp, tenantId, vmName, subnet);
	}

	@Override
	public void createRouter(Router router) throws Exception {

		service.createRouter(router);
		// TODO Auto-generated method stub

	}

	@Override
	public void bindingRouter(Router router, List<Subnet> subnets) throws Exception {

		// 暂时只实现两个子网之间的通信

		// 防火墙ip地址
		String ip = router.getLocalIp(); // 参数

		// 创建地址段脚本
		StringBuilder addressField_sb = new StringBuilder();

		for (Subnet subnet : subnets) {

			// String subnetName = subnet.getSubnetName(); // 子网名称

			String segment = subnet.getSegment(); // 子网网段

			String subnetMask = subnet.getSubnetMask(); // 子网掩码

			String gateway = subnet.getGateway(); // 子网网关

			addressField_sb.append("config firewall address").append(SDNConstants.ENTER_SIGN);
			addressField_sb.append("edit ").append("\"").append(segment).append("\"").append(SDNConstants.ENTER_SIGN);
			addressField_sb.append("set subnet ").append(gateway).append(" ").append(subnetMask)
					.append(SDNConstants.ENTER_SIGN);
			addressField_sb.append("next");

			TelnetUtil.execCommand(ip, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
					addressField_sb.toString());
		}
		Subnet sourceSubnet = subnets.get(0);
		Subnet targetSubnet = subnets.get(1);

		StringBuilder sourceSubnet_strategy_sb = new StringBuilder();
		StringBuilder targetSubnet_strategy_sb = new StringBuilder();

		int no = 8; // 序列号 参数
		int sourceSubnet_portNo = sourceSubnet.getPortNo(); // 端口号 参数
		int targetSubnet_portNo = targetSubnet.getPortNo(); // 端口号 参数
		String sourceSubnet_segment = sourceSubnet.getSegment(); // 源子网网段 参数
		String targetSubnet_segment = targetSubnet.getSegment(); // 目标子网网段 参数

		sourceSubnet_strategy_sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("edit ").append(no).append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set srcintf ").append("\"").append("port").append(sourceSubnet_portNo)
				.append("\"").append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set dstintf ").append("\"").append("port").append(targetSubnet_portNo)
				.append("\"").append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set srcaddr ").append("\"").append(sourceSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set dstaddr ").append("\"").append(targetSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set schedule ").append("\"").append("always").append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("set service ").append("\"").append("ALL").append("\"")
				.append(SDNConstants.ENTER_SIGN);
		sourceSubnet_strategy_sb.append("next");

		TelnetUtil.execCommand(ip, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				sourceSubnet_strategy_sb.toString());

		targetSubnet_strategy_sb.append("config firewall policy").append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("edit ").append(no).append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set srcintf ").append("\"").append("port").append(targetSubnet_portNo)
				.append("\"").append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set dstintf ").append("\"").append("port").append(sourceSubnet_portNo)
				.append("\"").append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set srcaddr ").append("\"").append(targetSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set dstaddr ").append("\"").append(sourceSubnet_segment).append("\"")
				.append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set schedule ").append("\"").append("always").append("\"")
				.append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("set service ").append("\"").append("ALL").append("\"")
				.append(SDNConstants.ENTER_SIGN);
		targetSubnet_strategy_sb.append("next");

		TelnetUtil.execCommand(ip, SDNConstants.FIREWALL_USERNAME, SDNConstants.FIREWALL_PASSWORD,
				targetSubnet_strategy_sb.toString());
	}

	private String getLocalIpBySubnet(Subnet subnet) {
		// TODO Auto-generated method stub
		return null;
	}
}
