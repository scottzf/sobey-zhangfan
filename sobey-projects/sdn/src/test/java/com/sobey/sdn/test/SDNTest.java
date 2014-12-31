package com.sobey.sdn.test;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.sobey.sdn.util.SDNPropertiesUtil;
import com.sobey.sdn.util.VcenterUtil;
import com.vmware.vim25.CustomizationAdapterMapping;
import com.vmware.vim25.CustomizationFixedIp;
import com.vmware.vim25.CustomizationFixedName;
import com.vmware.vim25.CustomizationGlobalIPSettings;
import com.vmware.vim25.CustomizationGuiUnattended;
import com.vmware.vim25.CustomizationIPSettings;
import com.vmware.vim25.CustomizationIdentification;
import com.vmware.vim25.CustomizationLicenseDataMode;
import com.vmware.vim25.CustomizationLicenseFilePrintData;
import com.vmware.vim25.CustomizationLinuxOptions;
import com.vmware.vim25.CustomizationLinuxPrep;
import com.vmware.vim25.CustomizationPassword;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.CustomizationSpecInfo;
import com.vmware.vim25.CustomizationSpecItem;
import com.vmware.vim25.CustomizationSysprep;
import com.vmware.vim25.CustomizationSysprepRebootOption;
import com.vmware.vim25.CustomizationUserData;
import com.vmware.vim25.CustomizationWinOptions;
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

		// 临时需要
		ServiceInstance si = VcenterUtil.getServiceInstance();

		// ECS ecs = new ECS();
		// ecs.setLocalIp("192.168.15.17"); // ip
		// ecs.setTemplateName("Win2008"); // 模板
		// ecs.setTemplateOS("Windows"); // 操作系统
		// ecs.setEcsId("zhangfanTest003"); // 云主机ID
		//
		// Subnet subnet = new Subnet();
		// subnet.setSubnetMask("255.255.255.0"); // 子网掩码
		// subnet.setGateway("192.168.15.1"); // 网关
		//
		// sdnService.createECS(ecs, subnet);

		// 获得虚拟机所在主机
		String hostIp = "172.16.2.31";
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				hostIp);
		// 按一定规则生成vlanId 很重要
		int vlanId = 150;

		String tenementId = "sobeyTest";
		String vlan = "";
		// 创建本地VLAN
		createLocalVlanByPortGroupName(host, tenementId, vlanId);
		/**
		 * 在主机对应置顶交换机上创建VLAN
		 */
		// 配置VLAN
		String[] vlan_Config_cmds = generateVlanConfigString(vlanId); // 配置面向服务器的接口的命令

		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), vlan_Config_cmds); // 交换机ip地址暂时空着

		// 在置顶交换机之间建NVGRE隧道ID
		String tunnelId = "" + vlanId;
		String[] nvgre_cmds = generateNvgreConfigString(vlanId); // 配置NVGRE的命令
		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), nvgre_cmds); // 交换机ip地址暂时空着

		// 生成交换机执行命令
		String swInterface = "eth-0-26";
		String[] interfaceConfig_cmds = generateInterfaceConfigString(swInterface, vlanId); // 配置面向服务器的接口的命令

		// Apache HTTP client以POST方式执行CLI命令
		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), interfaceConfig_cmds); // 交换机ip地址暂时空着

		/**
		 * 将租户虚拟机添加到VLAN中
		 */
		// 虚拟机连接本地VLAN
		connectVMToLocalVlan(si, "sobeyTest001", "sobeyTest_SDN " + vlanId);

	}

	@Test
	public void testCmds() throws Exception {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + 55; // 创建面向服务器的VLAN
		String str4 = "VLAN 4094"; // 创建上行VLAN
		String str5 = "exit"; // 退出VLAN模式
		String[] vlan_Config_cmds = { str1, str2, str3, str4, str5 };

		JsonRPCUtil.executeJsonRPCRequest("http://10.2.2.9:80/command-api", vlan_Config_cmds);

		String str11 = "configure terminal"; // 进入配置模式
		String str12 = "eth-0-26"; // 进入接口模式
		String str13 = "no shutdown"; // 打开接口
		String str14 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str15 = "switchport trunk allowed vlan add " + 55; // 允许vlanId标记的VLAN报文通过
		String[] interfaceConfig_cmds = { str11, str12, str13, str14, str15 };

		JsonRPCUtil.executeJsonRPCRequest("http://10.2.2.9:80/command-api", interfaceConfig_cmds);

		String str21 = "configure terminal"; // 进入配置模式
		String str22 = "nvgre"; // 进入NVGRE模式
		String str23 = "source 172.31.255.1"; // 设置NVGRE报文的外层IP源地址
		String str24 = "vlan " + 55 + " tunnel-id " + 55; // 将id为vlanId的VLAN映射到tunnel ID中
		String str25 = "vlan " + 55 + " peer 172.31.255.2"; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] nvgre_cmds = { str21, str22, str23, str24, str25 };

		JsonRPCUtil.executeJsonRPCRequest("http://10.2.2.9:80/command-api", nvgre_cmds);
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
		System.out.println(H3CUtil.getCommandResponse("172.16.3.13", "zhangfan", "gwgxhULJerjVkvCeJcxA"));
	}

	private String[] generateVlanConfigString(int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "VLAN database"; // 进入VLAN模式
		String str3 = "VLAN " + vlanId; // 创建面向服务器的VLAN
		String str4 = "VLAN 4094"; // 创建上行VLAN
		String str5 = "exit"; // 退出VLAN模式
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	private String[] generateNvgreConfigString(int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "nvgre"; // 进入NVGRE模式
		String str3 = "source 172.31.255.1"; // 设置NVGRE报文的外层IP源地址
		String str4 = "vlan " + vlanId + " tunnel-id " + vlanId; // 将id为vlanId的VLAN映射到tunnel ID中
		String str5 = "vlan " + vlanId + " peer 172.31.255.2"; // 在id为vlanId的vlanId中创建到TOR B的隧道
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	private String[] generateInterfaceConfigString(String swInterface, int vlanId) {
		String str1 = "configure terminal"; // 进入配置模式
		String str2 = "interface "+swInterface; // 进入接口模式
		String str3 = "no shutdown"; // 打开接口
		String str4 = "switchport mode trunk"; // 设置面向服务器的接口为trunk模式
		String str5 = "switchport trunk allowed vlan add " + vlanId; // 允许vlanId标记的VLAN报文通过
		String[] cmds = { str1, str2, str3, str4, str5 };
		return cmds;
	}

	private void connectVMToLocalVlan(ServiceInstance si, String ecsName, String vlan) throws Exception {

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", ecsName);
		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
		nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

		VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();
		VirtualDevice[] vds = vmConfigInfo.getHardware().getDevice();

		for (int i = 0; i < vds.length; i++) {

			if (vds[i] instanceof VirtualEthernetCard) {

				VirtualEthernetCard nic = (VirtualEthernetCard) vds[i];
				VirtualDeviceBackingInfo properties = nic.getBacking();
				VirtualEthernetCardNetworkBackingInfo nicBaking = (VirtualEthernetCardNetworkBackingInfo) properties;
				nicBaking.setDeviceName(vlan);// 指定要绑定的设配器(标准交换机端口)
				nic.setBacking(nicBaking);
				nicSpec.setDevice(nic);
			}
		}

		VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };
		vmConfigSpec.setDeviceChange(nicSpecArray);

		Task vmTask = vm.reconfigVM_Task(vmConfigSpec);

		vmTask.waitForTask();
	}

	/**
	 * 创建对应名称的本地VLAN
	 * 
	 * @param portGroupName
	 * @throws RemoteException
	 * @throws RuntimeFault
	 * @throws InvalidProperty
	 */
	private void createLocalVlanByPortGroupName(HostSystem host, String tenementId, int vlanId) throws InvalidProperty,
			RuntimeFault, RemoteException {

		// 获得虚拟机所在主机对应标准交换机
		HostNetworkSystem hns = host.getHostNetworkSystem();
		HostVirtualSwitch[] nets = hns.getNetworkInfo().getVswitch();
		HostVirtualSwitch vSwitch = nets[0];
		String switchName = vSwitch.getName();

		// 按规则生成租户对应的本地VLAN
		String vlanName = tenementId + "_SDN " + vlanId;

		// add a port group
		HostPortGroupSpec hpgs = new HostPortGroupSpec();
		hpgs.setName(vlanName);
		hpgs.setVlanId(vlanId); // not associated with a VLAN 建议用12--4093之间的VLAN id id在后期增加生成规律
		hpgs.setVswitchName(switchName);
		hpgs.setPolicy(new HostNetworkPolicy());
		hns.addPortGroup(hpgs);

	}

	@Test
	public void createRouter() throws Exception {
		// 临时需要
		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 获得vRouter模板
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "vRoute_MOD");

		// 虚拟机克隆方案创建
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		// CustomizationSpec数据对象类型包含需要自定义虚拟机部署时或将其迁移到新的主机的信息。
		CustomizationSpec cspec = new CustomizationSpec();
		CustomizationSpecInfo info = new CustomizationSpecInfo();
		CustomizationSpecItem specItem = new CustomizationSpecItem();

		CustomizationAdapterMapping adaptorMap = new CustomizationAdapterMapping();
		CustomizationIPSettings adapter = new CustomizationIPSettings();
		CustomizationFixedIp fixedIp = new CustomizationFixedIp();// 指定使用固定ip
		CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();

		info.setDescription("Linux");
		info.setName("Sobey");
		info.setType("Linux");// 设置克隆机器的操作系统类型

		specItem.setInfo(info);
		specItem.setSpec(cspec);

		// dns列表
		String dnsList[] = new String[] { "8.8.8.8" };
		String ipAddress = "172.16.35.2"; // 自定义的内网IP
		String subNetMask = "255.255.255.0";

		adapter.setDnsServerList(dnsList);
		adapter.setGateway(new String[] { "172.16.35.1" });
		adapter.setIp(fixedIp);
		adapter.setSubnetMask(subNetMask);

		fixedIp.setIpAddress(ipAddress);
		adaptorMap.setAdapter(adapter);

		// 不能使用MAC设置
		String dnsSuffixList[] = new String[] { "sobey.com", "sobey.cn" };
		gIP.setDnsSuffixList(dnsSuffixList);
		gIP.setDnsServerList(dnsList);

		CustomizationFixedName computerName = new CustomizationFixedName();
		computerName.setName("cmop");// 无法确认VM用户名是否能为中文,目前暂定为所有都是cmop

		CustomizationAdapterMapping[] nicSettingMap = new CustomizationAdapterMapping[] { adaptorMap };

		CustomizationSpecManager specManager = si.getCustomizationSpecManager();

		CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
		CustomizationLinuxPrep cLinuxPrep = new CustomizationLinuxPrep();
		cLinuxPrep.setDomain("sobey.com");
		cLinuxPrep.setHostName(computerName);
		cLinuxPrep.setHwClockUTC(true);
		cLinuxPrep.setTimeZone("Asia/Shanghai");

		cspec.setOptions(linuxOptions);
		cspec.setIdentity(cLinuxPrep);
		cspec.setGlobalIPSettings(gIP);
		cspec.setNicSettingMap(nicSettingMap);
		cspec.setEncryptionKey(specManager.getEncryptionKey());

		// 设置ResourcePool
		/**
		 * TODO 重要:宿主机暂时写死,宿主机的Value可以在VMTest中的PrintInventory方法查出来.
		 * 
		 * 后期应该做到CMDBuild查询宿主机的负载能力,找出负载最低的宿主机, 并根据名称查出ManagedObjectReference对象的value.
		 */
		ManagedObjectReference pool = new ManagedObjectReference();
		pool.set_value("resgroup-8");
		pool.setType("ResourcePool");
		pool.setVal("resgroup-8");

		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setPool(pool);
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(true);
		cloneSpec.setTemplate(false);
		cloneSpec.setCustomization(cspec);

		// vRouter.checkCustomizationSpec(specItem.getSpec());

		vRouter.cloneVM_Task((Folder) vRouter.getParent(), "vRouter_zhangfanTest1", cloneSpec);

	}
	@Test
	public void testFireString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("config firewall address").append(" ").append(188).append("\r");
		sb.append("edit ").append("\"").append("192.168.21.0").append("/24").append("\"")
				.append("\r");
		sb.append("set subnet ").append("192.168.21.26").append(" 255.255.255.255").append("\r");
		sb.append("next").append("\r");
		sb.append("end").append("\r");
		sb.append("\r");
		
		System.out.println(sb.toString());
	}
}
