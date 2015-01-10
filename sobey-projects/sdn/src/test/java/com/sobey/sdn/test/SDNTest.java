package com.sobey.sdn.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.service.FirewallService;
import com.sobey.sdn.service.HostRelationMap;
import com.sobey.sdn.service.impl.SDNServiceImpl;
import com.sobey.sdn.util.H3CUtil;
import com.sobey.sdn.util.JsonRPCUtil;
import com.sobey.sdn.util.SDNPropertiesUtil;
import com.sobey.sdn.util.VcenterUtil;
import com.vmware.vim25.HostPortGroupConfig;
import com.vmware.vim25.PhysicalNic;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SDNTest extends TestCase {

	@Autowired
	private SDNServiceImpl sdnService;

	@Test
	public void createECS() throws Exception {
		
		String vmName = "192.168.230.9";

		ECS ecs = new ECS();
		ecs.setTemplateName("Windows 2008 R2 Mod"); // 模板
		ecs.setTemplateOS("Windows"); // 操作系统
		ecs.setLocalIp(vmName); // 内网ip

		Subnet subnet = new Subnet();
		subnet.setSubnetMask("255.255.255.0"); // 子网掩码
		subnet.setGateway("192.168.230.1"); // 网关

		int vlanId = 300;

		String tenantId = "zhangfanTest";

		String hostIp = "10.2.5.23";


		sdnService.createECS(ecs, vlanId, hostIp, tenantId, vmName, subnet);
	}
	
	@Test
	public void createRouter() throws Exception {

		 Router router = new Router();
		
		 router.setHostIp("10.2.5.25"); // 主机IP
		 router.setRouterName("zhangfanTest-vRouter"); // 路由名称
		 String ip_update = "10.2.253.53"; // 端口10更新ip
		
		 sdnService.createRouter(router, ip_update);

	}
	
	@Test
	public void bindingRouter() throws Exception {

		Router router = new Router();
		router.setRouterName("zhangfanTest-vRouter");
		router.setControlIp("10.2.253.16");

		Subnet subnet1 = new Subnet();
		subnet1.setSubnetName("subnet1");
		subnet1.setSegment("192.168.200.0");
		subnet1.setGateway("192.168.200.1");
		subnet1.setSubnetMask("255.255.255.0");
		subnet1.setPortGroupName("zhangfanTest_TN 200");
		
		Subnet subnet2 = new Subnet();
		subnet2.setSubnetName("subnet2");
		subnet2.setSegment("192.168.230.0");
		subnet2.setGateway("192.168.230.1");
		subnet2.setSubnetMask("255.255.255.0");
		subnet2.setPortGroupName("zhangfanTest_TN 300");
		
		List<Subnet> subnets = new ArrayList<Subnet>();
		subnets.add(subnet1);
		subnets.add(subnet2);

		sdnService.bindingRouter(router, subnets);

	}
	@Test
	public void bindingFirewall() throws Exception {

		Router router = new Router();
		router.setRouterName("zhangfanTest-vRouter");
		router.setControlIp("10.2.253.16");

		String ip_ISP = "221.237.156.153"; // 连接电信端口的ip地址

		Firewall firewall = new Firewall();
		firewall.seteSubnetMask("255.255.255.0");
		firewall.setGateway("221.237.156.1");
		
		sdnService.bindingFirewall(router, firewall, ip_ISP);
	}

	@Test
	public void getRouterInfo() throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "vRouter_5001"); // 5001-01

		VirtualMachineConfigInfo virtualMachineConfigInfo = vm.getConfig();
		VirtualHardware virtualHardware = virtualMachineConfigInfo.getHardware();
		VirtualDevice[] virtualDevices = virtualHardware.getDevice();

		for (int i = 0; i < virtualDevices.length; i++) {
			System.out.println(virtualDevices[i].getDeviceInfo().getLabel());
			if (virtualDevices[i] instanceof VirtualEthernetCard) {

				VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
				System.out.println("\n============ mac地址：" + card.getMacAddress());
				System.out.println("-------------------------------");
			}
		}

	}

	@Test
	public void getHostNetwork() throws Exception {
		
		ServiceInstance si = VcenterUtil.getServiceInstance();
		// 找到对应ip的主机
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				"10.2.5.25");

		// 获得主机网络系统
		HostNetworkSystem hns = host.getHostNetworkSystem();
		HostPortGroupConfig[] pgs = hns.getNetworkConfig().getPortgroup();
		for (int i = 0; i < pgs.length; i++) {
			System.out.println(pgs[i].getSpec().getName());
		}
		//hns.getNetworkInfo()
	}

	@Test
	public void getHostInfo() throws Exception {
		// vcenter sdk
		// 服务实例
		ServiceInstance serviceInstance = VcenterUtil.getServiceInstance();
		Folder rootFolder = serviceInstance.getRootFolder();
		// 主机
		ManagedEntity[] entities = new InventoryNavigator(rootFolder).searchManagedEntities("HostSystem");
		for (int i = 0; i < entities.length; i++) {
			if (entities[i] instanceof HostSystem) {
				HostSystem host = (HostSystem) entities[i];
				System.out.println("宿主机管理IP: " + host.getName()); // 宿主机管理IP
				PhysicalNic[] physicalNics = host.getConfig().getNetwork().getPnic();
				for (int j = 0; j < physicalNics.length; j++) {
					System.out.println("网络接口卡: " + host.getConfig().getNetwork().getPnic()[j].getDevice());// 网络接口卡
					System.out.println("MAC地址: " + host.getConfig().getNetwork().getPnic()[j].getMac());// MAC地址
				}
				System.out.println("------------------------------------------");
			}
		}
		System.out.println(entities.length);
	}

	/**
	 * 获得盛科交换机相关信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void getCentecSwitchInfo() throws Exception {
		String swInterface = JsonRPCUtil.getSwitchPortByMac("BAGG8", "0050.5683.03c4"); // 获得主机与交换机哪个接口相连
		System.out.println(swInterface);
	}

	@Test
	public void getH3CSwitchInfo() throws Exception {
		/*
		 * Connection con = new Connection("192.168.0.114"); ConnectionInfo info = con.connect(); boolean result =
		 * con.authenticateWithPassword("lldu", "123456"); Session session = con.openSession();
		 * session.execCommand("java test.Main");
		 */
		Connection connection = new Connection("10.2.3.254", 22);
		connection.connect();
		Boolean mark = connection.authenticateWithPassword("zhangfan", "gwgxhULJerjVkvCeJcxA");
		System.out.println(mark);
		Session session = connection.openSession();
		session.execCommand("dis arp | begin 172.16.3.13");

		System.out.println("ExitCode: " + session.getExitStatus());

		InputStream stdout = new StreamGobbler(session.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line.contains("172.16.3.13")) {
				System.out.println("原始字符串：" + line);
				String str = line.trim().replaceAll(" ", "").replaceAll("172.16.3.13", ""); // 替换空格
				String mac = str.substring(0, 14);
				System.out.println("mac地址:" + mac);
				int x = str.indexOf("BAGG");
				String port = str.substring(x, x + 5);
				System.out.println("那台交换机" + port);
				System.out.println("yyyyyyyy" + str);
				break;
			}
			System.out.println(line);
			if (line == null)
				break;
		}
	}

	@Test
	public void test6() throws Exception {

	}

	@Test
	public void getExcel() throws Exception {
		/*
		 * List<RelationObject> relationObjects = new ArrayList<RelationObject>(); ServiceInstance serviceInstance =
		 * VcenterUtil.getServiceInstance(); Folder rootFolder = serviceInstance.getRootFolder(); // 主机 ManagedEntity[]
		 * entities = new InventoryNavigator(rootFolder).searchManagedEntities("HostSystem");
		 * 
		 * for (int i = 0; i < entities.length; i++) {
		 * 
		 * if (entities[i] instanceof HostSystem) {
		 * 
		 * HostSystem host = (HostSystem) entities[i];
		 * 
		 * if (RPCConstants.hostSet.contains(host.getName())) {
		 * 
		 * RelationObject relationObject = new RelationObject(); List<NICRelation> nicRelations = new
		 * ArrayList<NICRelation>(); relationObject.setIp(host.getName()); // 宿主机管理IP String[] heixin =
		 * SshUtil.getCommandResponse(host.getName(), "zhangfan", "gwgxhULJerjVkvCeJcxA"); PhysicalNic[] physicalNics =
		 * host.getConfig().getNetwork().getPnic();
		 * 
		 * for (int j = 0; j < physicalNics.length; j++) {
		 * 
		 * 
		 * String mac = host.getConfig().getNetwork().getPnic()[j].getMac();// MAC地址
		 * 
		 * if (mac.replaceAll(":", "").equals(heixin[0].replaceAll("-", ""))) {
		 * 
		 * NICRelation nicRelation = new NICRelation();
		 * nicRelation.setNic(host.getConfig().getNetwork().getPnic()[j].getDevice());// 网络接口卡 nicRelation.setMac(mac);
		 * String swPort = JsonRPCUtil .executeJsonRPCRequest(heixin[1], heixin[0].replaceAll("-", "."));
		 * nicRelation.setPort(swPort); nicRelations.add(nicRelation); } }
		 * 
		 * relationObject.setNicRelations(nicRelations); relationObjects.add(relationObject); } } } for (RelationObject
		 * r : relationObjects) { System.out.println(r.getIp()); List<NICRelation> nicRelations = r.getNicRelations();
		 * for (NICRelation nicRelation : nicRelations) { System.out.println("mac: " + nicRelation.getMac());
		 * System.out.println("NIC :" + nicRelation.getNic()); System.out.println("交换机端口： " + nicRelation.getPort()); }
		 * System.out.println("----------------------------------------"); }
		 */
	}

	@Test
	public void getSWPort() throws Exception {
		String result = JsonRPCUtil.getSwitchPortByMac("BAGG7", "e839.3513.410c");
		System.out.println(result);
	}

	@Test
	public void getCoreSW() throws Exception {
		System.out.println(H3CUtil.getCommandResponse("10.2.5.25"));
	}

	@Test
	public void testFireString() throws Exception {

		// FirewallService.createAddressPool("10.2.252.6", "addressPoolName1", "172.16.6.0", "255.255.255.0");
		// FirewallService.createAddressPool("10.2.252.6", "addressPoolName2", "172.16.7.0", "255.255.255.0");

		FirewallService.configurationNetworksStrategy("10.2.252.6", 180, "port6", "port7", "addressPoolName1",
				"addressPoolName2");
	}

	@Test
	public void getVmMac() throws Exception {
		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "demo_Test2");
		String mac = vm.getGuest().getNet()[0].getMacAddress();
		String[] macs = mac.split(":");
		String mac1 = macs[0] + macs[1] + "." + macs[2] + macs[3] + "." + macs[4] + macs[5];
		System.out.println(mac1);
	}

	@Test
	public void getMacByVM() throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "zhangfanTest_vm_201");
		VirtualMachineConfigInfo myVMInfo = vm.getConfig();
		VirtualHardware vmHardware = myVMInfo.getHardware();
		VirtualDevice[] vmDevices = vmHardware.getDevice();
		for (int i = 0; i < vmDevices.length; i++) {
			if (vmDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) vmDevices[i];
				System.out.println("\n============ mac地址：" + card.getMacAddress());
			}
		}
		System.out.println(vm.getGuest().getNet().length);
		String mac = vm.getGuest().getNet()[0].getMacAddress();
		System.out.println(mac);
	}

	private void createPolicyInSwitch(int vlanId, String swUrl, String swInterface) throws IOException {

		// 配置VLAN
		String[] vlan_Config_cmds = generateVlanConfigString(vlanId); // 配置面向服务器的接口的命令

		JsonRPCUtil.executeJsonRPCRequest(swUrl, vlan_Config_cmds); // 执行
		// 生成交换机执行命令
		String[] interfaceConfig_cmds = generateInterfaceConfigString(swInterface, vlanId); // 配置面向服务器的接口的命令

		// Apache HTTP client以POST方式执行CLI命令
		JsonRPCUtil.executeJsonRPCRequest(swUrl, interfaceConfig_cmds); // 执行

		// 在置顶交换机之间建NVGRE隧道ID
		String[] nvgre_cmds = generateNvgreConfigString(swUrl, vlanId); // 配置NVGRE的命令
		JsonRPCUtil.executeJsonRPCRequest(swUrl, nvgre_cmds); // 执行

	}

	private String[] generateVlanConfigString(int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + vlanId; // 创建面向服务器的VLAN
		String str4 = "VLAN 4094"; // 创建上行VLAN
		String str5 = "exit"; // 退出VLAN模式
		String str6 = "copy running-config startup-config"; // 保存配置信息
		String[] cmds = { str1, str2, str3, str4, str5, str6 };
		return cmds;
	}

	private String[] generateNvgreConfigString(String swUrl, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式
		String sourceIp = "10.2.255.2"; // 置顶交换机源IP
		String peerIp = "10.2.255.1"; // 置顶交换机peer IP
		if ("10.2.2.8".equals(swUrl)) {
			sourceIp = "10.2.255.1";
			peerIp = "10.2.255.2";
		}
		String str3 = "source " + sourceIp; // 设置NVGRE报文的外层IP源地址
		String str4 = "vlan " + vlanId + " tunnel-id " + vlanId; // 将id为vlanId的VLAN映射到tunnel ID中
		String str5 = "vlan " + vlanId + " peer " + peerIp; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	private String[] generateInterfaceConfigString(String swInterface, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface " + swInterface; // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + vlanId; // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	private String generateupdatePort10IpConfigScript(String ip_update) {
		// 修改端口10的ip
		// config system interface
		// edit port10
		// set ip <ip netmask>
		// end
		// exe backup config flash
		StringBuilder sb = new StringBuilder();

		sb.append("config system interface").append(SDNConstants.ENTER_SIGN);
		sb.append("edit port10").append(SDNConstants.ENTER_SIGN);
		sb.append("set ip ").append(ip_update).append(" ").append(SDNConstants.PORT10_SUBNETMASK)
				.append(SDNConstants.ENTER_SIGN);
		sb.append("end").append(SDNConstants.ENTER_SIGN);
		sb.append("exe backup config flash").append(SDNConstants.ENTER_SIGN);

		return sb.toString();
	}

	@Test
	public void getLocationByVm() throws Exception {

		ECS ecs = new ECS();
		ecs.setTemplateName("Windows 2008 R2 Mod"); // 模板
		ecs.setTemplateOS("Windows"); // 操作系统
		ecs.setLocalIp("192.168.15.21"); // 内网ip

		ecs.setSubnetMask("255.255.255.0"); // 子网掩码
		ecs.setGateway("192.168.15.0"); // 网关
		ecs.setEcsName("zhangfanTest_demo_vm_101");

		ecs.setHostIp("10.2.5.27");

		//sdnService.cloneVM(ecs);

		// ServiceInstance si = VcenterUtil.getServiceInstance();
		//
		// VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
		// "VirtualMachine", "VM_TTTTTTTTT");
		//
		// System.out.println(vm.getConfig().getLocationId());
		// System.out.println(vm.getResourcePool().getConfig().getDynamicType());
		//
		//
		// VirtualMachineRelocateSpec relocSpec = new VirtualMachineRelocateSpec();
		//
		// HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
		// "10.2.5.25");
		//
		// System.out.println(host.getMOR().get_value());
		//
		// relocSpec.setHost(host.getMOR());

	}
	@Test
	public void getPoolValueByHostIp() throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 主机
		ManagedEntity[] hosts = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("HostSystem");
		for (int i = 0; i < hosts.length; i++) {
			HostSystem host = (HostSystem) hosts[i];
			ComputeResource computeResource = (ComputeResource) host.getParent();
			String poolValue = computeResource.getResourcePool().getMOR().getVal();
			System.out.println(host.getName());
			System.out.println(poolValue);
			System.out.println("--------------------------------------");
		}
	}

	@Test
	public void testSsh() throws Exception {
		// 连接
		Connection connection = new Connection(SDNPropertiesUtil.getProperty("G4_SW1_CORE_IP"), 22);
		connection.connect();

		// 登陆
		Boolean mark = connection.authenticateWithPassword(SDNConstants.FIREWALL_USERNAME,
				SDNConstants.FIREWALL_PASSWORD);
		if (mark) {
			// 执行
			Session session = connection.openSession();
			session.execCommand(SDNConstants.VROUTER_REGISTER_CMD);
		}
	}
	@Test
	public void testgetMapString() throws Exception {
		String whichSWAndSwInterface = HostRelationMap.relationMap.get("10.2.5.25");
		System.out.println(whichSWAndSwInterface);
		String whichSW = StringUtils.substringBefore(whichSWAndSwInterface, " ");
		String swInterface = StringUtils.substringAfter(whichSWAndSwInterface, " ");
		
		System.err.println(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_IP"));
		
		System.out.println("========"+whichSW);
		System.out.println(swInterface);
		
	}
}
