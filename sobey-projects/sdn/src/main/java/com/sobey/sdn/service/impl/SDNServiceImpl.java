package com.sobey.sdn.service.impl;

import java.io.IOException;
import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.parameterObject.SubnetParameter;
import com.sobey.sdn.service.SDNService;
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
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@Service(value = "sdnService")
public class SDNServiceImpl implements SDNService {

	@Override
	public String createECS(ECS ecs, int vlanId, String hostIp, String tenantId, String vmName, Subnet subnet) {
		try {

			// 按规则生成租户对应的本地VLAN
			String portGroupName = tenantId + "_SDN " + vlanId;

			// 创建端口组
			createPortGroup(hostIp, tenantId, vlanId);

			// 根据虚拟机名称clone虚拟机，设置虚拟机的内网IP
			cloneVM(ecs);

			// 标准网络交换机绑定VM
			bindingvSwitch(vmName, portGroupName);

			// 4.在盛科交换机上创建策略
			String swInterface = "eth-0-26";

			createPolicyInSwitch(vlanId, swInterface);

		} catch (Exception e) {
			e.printStackTrace();
			return "操作异常，操作失败！";
		}
		return null;
	}

	private void createPolicyInSwitch(int vlanId, String swInterface) throws IOException {

		// 配置VLAN
		String[] vlan_Config_cmds = generateVlanConfigString(vlanId); // 配置面向服务器的接口的命令

		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), vlan_Config_cmds); // 交换机ip地址暂时空着
		// 生成交换机执行命令
		String[] interfaceConfig_cmds = generateInterfaceConfigString(swInterface, vlanId); // 配置面向服务器的接口的命令

		// Apache HTTP client以POST方式执行CLI命令
		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), interfaceConfig_cmds); // 交换机ip地址暂时空着

		// 在置顶交换机之间建NVGRE隧道ID
		String[] nvgre_cmds = generateNvgreConfigString(vlanId); // 配置NVGRE的命令
		JsonRPCUtil.executeJsonRPCRequest(SDNPropertiesUtil.getProperty("TOR-A_SWITCH_URL"), nvgre_cmds); // 交换机ip地址暂时空着

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
		String str2 = "interface " + swInterface; // 进入接口模式
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

	/**
	 * 获取租户ID
	 * 
	 * @return
	 */
	private String getECSNameByTenementId() {

		return "sobeyTest";
	}

	/**
	 * 根据子网为虚拟机生成内网IP
	 * 
	 * @param subnet
	 * @return
	 */
	private String getLocalIpBySubnet(Subnet subnet) {
		return null;
	}

	/**
	 * 绑定虚拟机和端口组
	 * 
	 * @param vmName
	 * @param portGroupName
	 * @return
	 * @throws Exception
	 */
	private String bindingvSwitch(String vmName, String portGroupName) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", vmName);
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
				nicBaking.setDeviceName(portGroupName);// 指定要绑定的设配器(标准交换机端口)
				nic.setBacking(nicBaking);
				nicSpec.setDevice(nic);
			}
		}

		VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };
		vmConfigSpec.setDeviceChange(nicSpecArray);

		Task vmTask = vm.reconfigVM_Task(vmConfigSpec);

		String result = vmTask.waitForTask();

		if (Task.SUCCESS.equals(result)) {
			return null;
		} else {
			return "绑定虚拟机和端口组失败";
		}
	}

	private String cloneVM(ECS ecs) throws Exception {

		// 由云主机获得创建虚拟机所需参数
		String vmTemplateName = ecs.getTemplateName(); // 模板名称
		String vmTemplateOS = ecs.getTemplateOS(); // 模板操作系统

		// 根据租户ID按规则生成虚拟机名
		String vmName = ecs.getEcsId();

		/**
		 * 根据云主机参数clone虚拟机
		 */
		ServiceInstance si = VcenterUtil.getServiceInstance();
		VirtualMachine vm = getVirtualMachine(si, vmTemplateName);
		if (vm == null) {
			// logout(si);
			return null;
		}

		/**
		 * 制做虚拟机克隆方案
		 */
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		// CustomizationSpec数据对象类型包含需要自定义虚拟机部署时或将其迁移到新的主机的信息。
		CustomizationSpec cspec = new CustomizationSpec();
		// CustomizationSpecInfo
		CustomizationSpecInfo info = new CustomizationSpecInfo();
		// CustomizationSpecItem
		CustomizationSpecItem specItem = new CustomizationSpecItem();

		// CustomizationGlobalIPSettings
		CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();

		info.setDescription(vmTemplateName);
		info.setName("Sobey");
		info.setType(vmTemplateOS);// 设置克隆机器的操作系统类型

		specItem.setInfo(info);
		specItem.setSpec(cspec);

		/**
		 * 虚拟机网络适配器相关配置设置
		 */
		// CustomizationAdapterMapping
		CustomizationAdapterMapping adaptorMap = new CustomizationAdapterMapping();

		// CustomizationIPSettings
		CustomizationIPSettings adapter = new CustomizationIPSettings();

		// CustomizationFixedIp
		CustomizationFixedIp fixedIp = new CustomizationFixedIp();// 指定使用固定ip

		String dnsList[] = new String[] { "8.8.8.8" }; // dns列表
		String ipAddress = ecs.getLocalIp(); // 自定义的内网IP
		String subNetMask = ecs.getSubnetMask(); // 子网掩码

		adapter.setDnsServerList(dnsList);
		adapter.setGateway(new String[] { ecs.getGateway() }); // 网关设置
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

		if ("Linux".equals(vmTemplateOS)) {

			CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
			CustomizationLinuxPrep cLinuxPrep = new CustomizationLinuxPrep();
			cLinuxPrep.setDomain("sobey.com");
			cLinuxPrep.setHostName(computerName);
			cLinuxPrep.setHwClockUTC(true);
			cLinuxPrep.setTimeZone("Asia/Shanghai");

			cspec.setOptions(linuxOptions);
			cspec.setIdentity(cLinuxPrep);

		} else if ("Windows".equals(vmTemplateOS)) {

			CustomizationWinOptions winOptions = new CustomizationWinOptions();
			CustomizationSysprep cWinSysprep = new CustomizationSysprep();

			CustomizationGuiUnattended guiUnattended = new CustomizationGuiUnattended();
			guiUnattended.setAutoLogon(false);
			guiUnattended.setAutoLogonCount(1);
			guiUnattended.setTimeZone(210); // http://msdn.microsoft.com/en-us/library/ms912391%28v=winembedded.11%29.aspx

			CustomizationPassword password = new CustomizationPassword();
			// ????????????
			password.setValue("newmedia");
			password.setPlainText(true);
			guiUnattended.setPassword(password);
			cWinSysprep.setGuiUnattended(guiUnattended);

			CustomizationUserData userData = new CustomizationUserData();
			userData.setProductId("");
			userData.setFullName("Sobey");
			userData.setOrgName("Sobey");
			userData.setComputerName(computerName);
			cWinSysprep.setUserData(userData);

			// Windows Server 2000, 2003 必须
			CustomizationLicenseFilePrintData printData = new CustomizationLicenseFilePrintData();
			printData.setAutoMode(CustomizationLicenseDataMode.perSeat);
			cWinSysprep.setLicenseFilePrintData(printData);

			CustomizationIdentification identification = new CustomizationIdentification();
			identification.setJoinWorkgroup("Sobey");
			cWinSysprep.setIdentification(identification);

			winOptions.setReboot(CustomizationSysprepRebootOption.shutdown);
			winOptions.setChangeSID(true);
			winOptions.setDeleteAccounts(false);

			cspec.setOptions(winOptions);
			cspec.setIdentity(cWinSysprep);

		}

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
		pool.set_value("resgroup-42");
		pool.setType("ResourcePool");
		pool.setVal("resgroup-42");

		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setPool(pool);
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(true);
		cloneSpec.setTemplate(false);
		cloneSpec.setCustomization(cspec);

		vm.checkCustomizationSpec(specItem.getSpec());

		Task task = vm.cloneVM_Task((Folder) vm.getParent(), vmName, cloneSpec);

		if (task.waitForTask() != Task.SUCCESS) {
			return null;
		}

		// 为虚拟机设置网络适配器相关信息:设备状态设置为已连接、网络标签、虚拟机的备注.
		return null;
	}

	@Override
	public String createSubnet(Subnet subnet) {
		/*
		 * 1、关联租户ID； 2、关联接入层接口、接入层交换Local VLAN 3、关联NVGRE报文的外层IP源地址 4、关联NVGRE tunnel-id 5、关联NVGRE隧道
		 */
		/**
		 * 关联租户ID
		 */
		// String switchPort = getSwitchPort();
		return null;
	}

	@Override
	public String deleteSubnet(SubnetParameter subnetParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据虚拟机名获得虚拟机对象
	 * 
	 * @param si
	 *            服务实例
	 * @param vmname
	 *            虚拟机名
	 * @return
	 * @throws InvalidProperty
	 * @throws RuntimeFault
	 * @throws RemoteException
	 */
	private VirtualMachine getVirtualMachine(ServiceInstance si, String vmname) throws InvalidProperty, RuntimeFault,
			RemoteException {
		return (VirtualMachine) new InventoryNavigator(si.getRootFolder())
				.searchManagedEntity("VirtualMachine", vmname);

	}

	/**
	 * 为对应ip为hostIp的主机创建端口组
	 * 
	 * @param hostIp
	 * @param portGroupName
	 * @param vlanId
	 * @throws Exception
	 */
	private void createPortGroup(String hostIp, String portGroupName, int vlanId) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 找到对应ip的主机
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				hostIp);
		// 获得主机网络系统
		HostNetworkSystem hns = host.getHostNetworkSystem();

		// add a port group
		HostPortGroupSpec hpgs = new HostPortGroupSpec();
		hpgs.setName(portGroupName);
		hpgs.setVlanId(vlanId); // not associated with a VLAN 建议用12--4093之间的VLAN id
		hpgs.setVswitchName(SDNConstants.SWITCH_NAME);
		hpgs.setPolicy(new HostNetworkPolicy());

		// 添加端口组
		hns.addPortGroup(hpgs);

	}

	@Override
	public String createRouter(Router router) throws Exception {
		// 1、vRouter申请（网卡数为子网数+1）；
		// 2、关联租户ID；
		// 3、关联ESXi、ESXi物理网卡、接入层交换机端口；
		// 4、关联虚拟交换机接口、VLAN ID、虚拟子网ID、虚拟子网IP网关；
		// 5、关联NVGRE报文的外层IP源地址、NVGRE tunnel-id；
		// 6、关联NVGRE隧道；

		ServiceInstance si = VcenterUtil.getServiceInstance();

		String vRouterName = ""; // vRouter名称 参数

		cloneVRouter(router);
		// 虚拟机克隆方案创建
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		// 获得vRouter模板
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", "vRouter_FG");

		return null;
	}

	/**
	 * 克隆防火墙虚拟机
	 * 
	 * @param router
	 */
	private void cloneVRouter(Router router) throws Exception {
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

	@Override
	public String deleteRouter(Router router) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
