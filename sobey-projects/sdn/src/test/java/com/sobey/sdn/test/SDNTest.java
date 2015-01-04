package com.sobey.sdn.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.service.impl.SDNServiceImpl;
import com.sobey.sdn.util.H3CUtil;
import com.sobey.sdn.util.JsonRPCUtil;
import com.sobey.sdn.util.VcenterUtil;
import com.vmware.vim25.CustomizationAdapterMapping;
import com.vmware.vim25.CustomizationFixedIp;
import com.vmware.vim25.CustomizationFixedName;
import com.vmware.vim25.CustomizationGlobalIPSettings;
import com.vmware.vim25.CustomizationIPSettings;
import com.vmware.vim25.CustomizationLinuxOptions;
import com.vmware.vim25.CustomizationLinuxPrep;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.CustomizationSpecInfo;
import com.vmware.vim25.CustomizationSpecItem;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualSwitch;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.PhysicalNic;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.CustomizationSpecManager;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SDNTest extends TestCase {

	@Autowired
	private SDNServiceImpl sdnService;

	@Test
	public void createECS() throws Exception {

		//测试通过   目前需要手动修改两个地方（1.clone后手动修改虚拟机默认网络为标准网络，手动修改从核心交换机上获得的置顶交换机接口）
		
		ECS ecs = new ECS();
		ecs.setTemplateName("Win2008"); // 模板
		ecs.setTemplateOS("Windows"); // 操作系统
		ecs.setEcsId("zhangfanTest003"); // 云主机ID
		ecs.setLocalIp("192.168.20.18"); // 内网ip
		
		Subnet subnet = new Subnet();
		subnet.setSubnetMask("255.255.255.0"); // 子网掩码
		subnet.setGateway("192.168.20.1"); // 网关
		
		int vlanId = 250;
		
		String tenantId = "demo";
		
		String hostIp = "172.16.2.32";
		
		String vmName = "demo_Test2";

		sdnService.createECS(ecs, vlanId, hostIp, tenantId, vmName, subnet);

	}
	@Test
	public void createRouter() throws Exception {
		
		Router router = new Router();
		router.setHostIp("172.16.2.31");   //主机IP
		router.setRouterName("vRouter-zhangfanTest-001");;   //路由名称
		sdnService.createRouter(router);
	}
	
	@Test
	public void test() {

		JSONObject cmdsObject = new JSONObject();

		String[] arr = { "show run", "config t", "vlan database", "vlan 1-8", "interface eth-0-1",
				"switchport mode trunk", "switchport trunk allowed vlan add 2", "shutdown", "end",
				"show interface switchport" };

		cmdsObject.put("cmds", arr);

		/*
		 * cmdsObject.put("show run", ""); cmdsObject.put("config t", ""); cmdsObject.put("vlan database", "");
		 * cmdsObject.put("vlan 1-8", ""); cmdsObject.put("interface eth-0-1", "");
		 * cmdsObject.put("switchport mode trunk", ""); cmdsObject.put("switchport trunk allowed vlan add 2", "");
		 * cmdsObject.put("shutdown", ""); cmdsObject.put("end", ""); cmdsObject.put("show interface switchport", "");
		 */

		List<JSONObject> paramsObjects = new ArrayList<JSONObject>();

		JSONObject paramsObject = new JSONObject();
		paramsObject.put("format", "text");
		paramsObject.put("version", "1");
		paramsObject.put("cmds", arr);

		paramsObjects.add(paramsObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", paramsObjects);
		// 固定格式
		jsonObject.put("jsonrpc", "2.0"); // JSON RPC协议版本号
		jsonObject.put("id", "70853aff-af77-420e-8f3c-fa9430733a19"); // JSON RPC协议中的UID
		jsonObject.put("method", "executeCmds"); // 运行交换机CLI命令的方法

		System.out.println(jsonObject.toString());
	}

	@Test
	public void test2() {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("id", 0);
		jsonObj.put("auth", "");
		jsonObj.put("jsonrpc", "2.0");
		jsonObj.put("method", "item.get");
		jsonObj.put("params",
				(new JSONObject().put("search", (new JSONObject()).put("key_", "key_")).put("hostids", "hostids").put(
						"output", "extend")));

		List<JSONObject> arr = new ArrayList<JSONObject>();
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		List<JSONObject> arr2 = new ArrayList<JSONObject>();
		JSONObject obj2 = new JSONObject();
		arr2.add(obj2);
		obj.put("hostId1", "hostId1");
		obj.put("hostId2", "hostId2");
		obj.put("hostId3", "hostId3");
		obj2.put("hello", "hello");
		obj.put("obj2", arr2);
		obj1.put("vvvv1", "vvvv1");
		arr.add(obj);
		arr.add(obj1);
		// String[] arr = new String[1];
		// arr[0] = "hostId";

		jsonObj.put("method", "host.delete");
		jsonObj.put("params", arr);

		System.out.println(jsonObj.toString());
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
		// show mac address-table | begin e839.3513.4048
		// TelnetUtil.execCommand("10.2.2.9", "", "", "show run", FILE_PATH);
		// com.sobey.sdn.util.TelnetUtil.testGet("10.2.2.9", 23);

		// TelnetClient telnet = new TelnetClient();
		// telnet.connect("10.2.2.9", 23);
		// InputStream in = telnet.getInputStream();
		// PrintStream out = new PrintStream(telnet.getOutputStream());
		// out.print("++++++++++++++++++++++++++++++++++++++++++");

		String[] cmds = { "show run" };
		// System.out.println(JsonRPCUtil.executeJsonRPCRequest("", cmds));
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
		/*
		 * String response = JsonRPCUtil.executeJsonRPCRequest("", new String[] {
		 * "show mac address-table | begin e839.3513.410c" });
		 * 
		 * // System.out.println(response); int a = response.indexOf("e839.3513.410c"); String result =
		 * response.substring(a); int b = response.indexOf("\\n"); String str = result.substring(0, b).replaceAll(" ",
		 * ""); int c = str.indexOf("dynamic"); String str1 = str.substring(c + 7, c + 14); System.out.println(str1);
		 */

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
		System.out.println(H3CUtil.getCommandResponse("172.16.3.13"));
	}

	@Test
	public void testFireString() throws Exception{
		
		StringBuilder sb = new StringBuilder();

		sb.append("config firewall address").append(" ").append(188).append("\r");
		sb.append("edit ").append("\"").append("192.168.21.0").append("/24").append("\"").append("\r");
		sb.append("set subnet ").append("192.168.21.26").append(" 255.255.255.255").append("\r");
		sb.append("next").append("\r");
		sb.append("end").append("\r");
		sb.append("\r");

		System.out.println(sb.toString());
	}
	@Test
	public void getVmMac() throws Exception{
		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "demo_Test2");
		String mac = vm.getGuest().getNet()[0].getMacAddress();
		String[] macs = mac.split(":");
		String mac1 = macs[0]+macs[1]+"."+macs[2]+macs[3]+"."+macs[4]+macs[5];
		System.out.println(mac1);
	}
}
