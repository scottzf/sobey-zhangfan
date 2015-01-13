package com.sobey.sdn.service.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sobey.sdn.bean.CreateEipParameter;
import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.SDNConstants;
import com.sobey.sdn.parameterObject.SubnetParameter;
import com.sobey.sdn.service.CentecSwitchService;
import com.sobey.sdn.service.FirewallScriptService;
import com.sobey.sdn.service.FirewallService;
import com.sobey.sdn.service.HostRelationMap;
import com.sobey.sdn.service.SDNService;
import com.sobey.sdn.test.testParameter.BindingFirewallParameter;
import com.sobey.sdn.test.testParameter.BindingRouterParameter;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;
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
import com.vmware.vim25.DistributedVirtualSwitchPortConnection;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupConfig;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardDistributedVirtualPortBackingInfo;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualVmxnet3;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.CustomizationSpecManager;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@Service(value = "sdnService")
public class SDNServiceImpl implements SDNService {

	@Override
	public String createECS(CreateECSParameter createECSParameter) {
		try {

			String tenantId = createECSParameter.getTenantId();
			int vlanId = createECSParameter.getVlanId();
			String hostIp = createECSParameter.getHostIp(); // 虚拟机所在宿主机IP
			String vmName = createECSParameter.getVmName(); // 虚拟机名称
			String gateway = createECSParameter.getGateway(); // 网关
			String subnetMask = createECSParameter.getSubNetMask(); // 掩码
			String localIp = createECSParameter.getLocalIp(); // 内网IP
			String templateName = createECSParameter.getTemplateName(); // 模板
			String templateOS = createECSParameter.getTemplateOS(); // 操作系统

			// 按规则生成租户对应的本地VLAN
			String portGroupName = tenantId + "_vlan" + vlanId;

			for (String host : HostRelationMap.hostList) {
				// 判断端口是否存在
				Boolean mark = checkNetworkIsExist(portGroupName, host);
				if (!mark) {
					createPortGroup(host, portGroupName, vlanId); // 创建端口组
				}
			}

			// 设置云主机相关属性
			ECS ecs = new ECS();
			ecs.setTemplateName(templateName); // 模板
			ecs.setTemplateOS(templateOS); // 操作系统
			ecs.setEcsName(vmName); // 设置虚拟机名称
			ecs.setHostIp(hostIp); // 设置虚拟机所在宿主机IP
			ecs.setGateway(gateway); // 网关
			ecs.setSubnetMask(subnetMask); // 掩码
			ecs.setLocalIp(localIp); // 内网IP

			// 根据虚拟机名称clone虚拟机，设置虚拟机的内网IP
			String result = cloneVM(ecs);
			if (result != null) {
				// return "clone虚拟机失败！";
			}

			// 标准网络交换机绑定VM
			bindingvSwitch(vmName, portGroupName);

			/**
			 * 暂时从固定资源列表中获得交换机接口
			 */
			String whichSWAndSwInterface = HostRelationMap.relationMap.get(hostIp);
			String whichSW = StringUtils.substringBefore(whichSWAndSwInterface, " "); // 取空格前字符串
			String swInterface = StringUtils.substringAfter(whichSWAndSwInterface, " "); // 取空格后字符串

			String swIp = getSwIpByMark(whichSW);

			// 在盛科交换机上创建策略
			createPolicyInSwitch(vlanId, swIp, swInterface);

			/**
			 * 后期用命令在核心交换机上获得交换机接口 *********代码保留，勿删**********
			 */
			// 根据主机IP,在核心交换机获得与该主机相连的交换机相关信息
			// String whichSW = H3CUtil.getCommandResponse(hostIp); // 获得核心交换机上对应的接口
			// String vm_mac = getMacByVM(vmName); // 获得虚拟机的Mac地址
			// String[] macs = vm_mac.split(":");
			// String mac = macs[0] + macs[1] + "." + macs[2] + macs[3] + "." + macs[4] + macs[5];
			// String swUrl = JsonRPCUtil.getSwitchIPByInterfaceStr(whichSW); // 根据对应接口获得对应置顶交换机URL
			// String swInterface = JsonRPCUtil.getSwitchPortByMac(whichSW, mac); // 获得主机与交换机哪个接口相连

		} catch (Exception e) {
			e.printStackTrace();
			return "操作异常，操作失败！";
		}
		return null;
	}

	private String getSwIpByMark(String whichSW) {
		if ("TOR-A".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-A_SWITCH_IP");
		}
		if ("TOR-B".equals(whichSW)) {
			return SDNPropertiesUtil.getProperty("TOR-B_SWITCH_IP");
		}
		return null;
	}

	/**
	 * 获得虚拟机mac地址 *********后期要用，勿删***********
	 * 
	 * @param vmName
	 * @return
	 * @throws Exception
	 */
	private String getMacByVM(String vmName) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", vmName);
		VirtualMachineConfigInfo myVMInfo = vm.getConfig();
		VirtualHardware vmHardware = myVMInfo.getHardware();
		VirtualDevice[] vmDevices = vmHardware.getDevice();
		String mac = null;
		for (int i = 0; i < vmDevices.length; i++) {
			if (vmDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) vmDevices[i];
				mac = card.getMacAddress();
			}
		}
		return mac;
	}

	private void createPolicyInSwitch(int vlanId, String swUrl, String swInterface) throws IOException {

		// 配置VLAN
		String[] vlan_Config_cmds = CentecSwitchService.generateVlanConfigString(vlanId); // 配置面向服务器的接口的命令

		JsonRPCUtil.executeJsonRPCRequest(swUrl, vlan_Config_cmds); // 执行

		// 生成交换机执行命令
		String[] interfaceConfig_cmds = CentecSwitchService.generateInterfaceConfigString(swInterface, vlanId); // 配置面向服务器的接口的命令

		// Apache HTTP client以POST方式执行CLI命令
		JsonRPCUtil.executeJsonRPCRequest(swUrl, interfaceConfig_cmds); // 执行

		// 在置顶交换机之间建NVGRE隧道ID
		String[] nvgre_cmds = CentecSwitchService.generateNvgreConfigString(swUrl, vlanId); // 配置NVGRE的命令

		JsonRPCUtil.executeJsonRPCRequest(swUrl, nvgre_cmds); // 执行

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

		// 网卡配置属性
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

				VirtualDeviceConnectInfo connectable = new VirtualDeviceConnectInfo();
				connectable.startConnected = true;
				connectable.allowGuestControl = true;
				connectable.connected = true;
				connectable.status = "untried";

				nic.setConnectable(connectable);
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

		String vmName = ecs.getEcsName(); // 虚拟机名
		String hostIp = ecs.getHostIp(); // 宿主机IP

		/**
		 * 根据云主机参数clone虚拟机
		 */
		ServiceInstance si = VcenterUtil.getServiceInstance();
		VirtualMachine vm = getVirtualMachine(si, vmTemplateName);
		if (vm == null) {
			return null;
		}

		/**
		 * 制做虚拟机克隆方案
		 */
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		// CustomizationSpec数据对象类型包含需要自定义虚拟机部署时或将其迁移到新的主机的信息。
		CustomizationSpec cspec = new CustomizationSpec();
		CustomizationSpecInfo info = new CustomizationSpecInfo();
		CustomizationSpecItem specItem = new CustomizationSpecItem();

		/**
		 * 虚拟机网络适配器相关配置设置
		 */
		CustomizationAdapterMapping adaptorMap = new CustomizationAdapterMapping();
		CustomizationIPSettings adapter = new CustomizationIPSettings();
		CustomizationFixedIp fixedIp = new CustomizationFixedIp(); // 指定使用固定ip
		CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();

		info.setDescription(vmTemplateName);
		info.setName("Sobey");
		info.setType(vmTemplateOS);// 设置克隆机器的操作系统类型

		specItem.setInfo(info);
		specItem.setSpec(cspec);

		String dnsList[] = new String[] { "8.8.8.8" }; // dns列表
		String ipAddress = ecs.getLocalIp(); // 自定义的内网IP
		String subNetMask = ecs.getSubnetMask(); // 子网掩码
		String gateway = ecs.getGateway(); // 网关

		fixedIp.setIpAddress(ipAddress);

		adapter.setDnsServerList(dnsList);
		adapter.setGateway(new String[] { gateway }); // 网关设置
		adapter.setIp(fixedIp);
		adapter.setSubnetMask(subNetMask);

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
			guiUnattended.setTimeZone(210);

			CustomizationPassword password = new CustomizationPassword();
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
		 * 重要:宿主机暂时写死,宿主机的Value可以在VMTest中的PrintInventory方法查出来.
		 * 
		 * 后期应该做到CMDBuild查询宿主机的负载能力,找出负载最低的宿主机, 并根据名称查出ManagedObjectReference对象的value.
		 */
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				hostIp);
		ManagedObjectReference pool = new ManagedObjectReference();
		String poolValue = getPoolValueByHostIp(hostIp);
		pool.set_value(poolValue);
		pool.setType("ResourcePool");
		pool.setVal(poolValue);

		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setPool(pool);
		relocateSpec.setHost(host.getMOR());
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(true);
		cloneSpec.setTemplate(false);
		cloneSpec.setCustomization(cspec);

		vm.checkCustomizationSpec(specItem.getSpec());

		Task task = vm.cloneVM_Task((Folder) vm.getParent(), vmName, cloneSpec);

		if (task.waitForTask() == Task.SUCCESS) {
			return null;
		} else {
			return "clone虚拟机失败！";
		}

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
	public String createRouter(CreateRouterParameter createRouterParameter) throws Exception {

		String routerName = createRouterParameter.getRouterName(); // vRouter名称
		String hostIp = createRouterParameter.getHostIp(); // 主机IP
		String ip_update = createRouterParameter.getControlIp();
		/**
		 * 根据路由器模板克隆路由器
		 */
		String clone_result = cloneVRouter(routerName, hostIp);
		if (clone_result != null) {
			return clone_result;
		}

		/**
		 * 修改vRouter管理IP
		 */
		String update_result = FirewallService.updateFirewallManageIp(ip_update);
		if (update_result != null) {
			return update_result;
		}

		return null;
	}

	/**
	 * 检查端口组是否存在
	 * 
	 * @param portGroupName
	 * @return
	 * @throws Exception
	 */
	private Boolean checkNetworkIsExist(String portGroupName, String hostIp) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();
		Folder rootFolder = si.getRootFolder();

		// 找到对应ip的主机
		HostSystem host = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem", hostIp);

		// 获得主机网络系统
		HostNetworkSystem hns = host.getHostNetworkSystem();

		List<String> list = new ArrayList<String>();

		// 获得端口组
		HostPortGroupConfig[] pgs = hns.getNetworkConfig().getPortgroup();
		for (int i = 0; i < pgs.length; i++) {
			list.add(pgs[i].getSpec().getName());
		}

		// 判断端口组是否存在于网络列表中
		if (list.contains(portGroupName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 克隆路由器
	 * 
	 * @param router
	 * @param ip_update
	 * @return
	 * @throws Exception
	 */
	private String cloneVRouter(String routerName, String hostIp) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 获得vRouter模板
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", SDNConstants.VROUTER_TEMPLATE);

		// 虚拟机克隆方案创建
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		// 设置ResourcePool
		/**
		 * 重要:宿主机暂时写死,宿主机的Value可以在VMTest中的PrintInventory方法查出来.
		 * 
		 * 后期应该做到CMDBuild查询宿主机的负载能力,找出负载最低的宿主机, 并根据名称查出ManagedObjectReference对象的value.
		 */
		String poolValue = getPoolValueByHostIp(hostIp);
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				hostIp);

		ManagedObjectReference pool = new ManagedObjectReference();
		pool.set_value(poolValue);
		pool.setType("ResourcePool");
		pool.setVal(poolValue);

		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setHost(host.getMOR());
		relocateSpec.setPool(pool);
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(true);
		cloneSpec.setTemplate(false);

		Task task = vRouter.cloneVM_Task((Folder) vRouter.getParent(), routerName, cloneSpec);

		if (task.waitForTask() != Task.SUCCESS) {
			return "创建路由器失败！";
		} else {
			return null;
		}
	}

	/**
	 * 获得主机的资源池标识
	 * 
	 * @param hostIp
	 * @return
	 * @throws Exception
	 */
	private String getPoolValueByHostIp(String hostIp) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 主机
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				hostIp);

		ComputeResource computeResource = (ComputeResource) host.getParent();
		String poolValue = computeResource.getResourcePool().getMOR().getVal();

		return poolValue;
	}

	@Override
	public String deleteRouter(Router router) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String bindingRouter(BindingRouterParameter bindingRouterParameter) throws Exception {

		/**
		 * 获得操作vRouter的属性
		 */
		String vRouterIp = bindingRouterParameter.getControlIp(); // vRouter管理IP
		String vRouterHostIp = bindingRouterParameter.getvRouterHostIp(); // vRouter所在宿主机IP
		String routerName = bindingRouterParameter.getRouterName(); // vRouter名称 参数
		int strategyNo = bindingRouterParameter.getStrategyNo(); // 策略号

		/**
		 * 将vRouter的网络适配器添加到需要绑定的子网的vlan中
		 */
		ServiceInstance si = VcenterUtil.getServiceInstance();
		// 获得租户对应的vRouter
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", routerName);
		// 获得vRouter的网络适配器
		VirtualDevice[] virtualDevices = vRouter.getConfig().getHardware().getDevice();
		List<VirtualEthernetCard> virtualEthernetCards = new ArrayList<VirtualEthernetCard>();
		for (int i = 0; i < virtualDevices.length; i++) {
			if (virtualDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
				virtualEthernetCards.add(card);
			}
		}

		List<Subnet> subnets = bindingRouterParameter.getSubnets();
		for (Subnet subnet : subnets) {

			String portGroupName = subnet.getPortGroupName(); // 子网所使用的端口组 参数
			int portNo = subnet.getPortNo(); // 子网所连的端口序号 参数
			String gateway = subnet.getGateway(); // 子网网关
			String subnetMask = subnet.getSubnetMask(); // 子网掩码
			int vlanId = subnet.getVlanId(); // 子网关联vlanId

			addVRouterNicToPortGroup(routerName, portGroupName, portNo);
			
			//向盛科交换机创建接口允许vlan通过的命令
			CentecSwitchService.makeVlanAccessInterface(vRouterHostIp, vlanId);

			// 创建地址段
			FirewallService.createAddressPool(vRouterIp, subnet.getSubnetName(), subnet.getSegment(),
					subnet.getSubnetMask());

			// 配置接口地址
			FirewallService.configurationPortIp(vRouterIp, portNo, gateway, subnetMask);
		}

		// 配置子网间的策略 重要：目前暂时实现两个子网的算法
		Subnet subnet1 = subnets.get(0);
		Subnet subnet2 = subnets.get(1);
		FirewallService.configurationNetworksStrategy(vRouterIp, strategyNo, "port1", "port2", subnet1.getSubnetName(),
				subnet2.getSubnetName());

		return null;
	}

	/**
	 * 将路由器对应网卡添加到所连子网的vlan中
	 * 
	 * @param routerName
	 * @param nic
	 * @param portGroupName
	 * @return
	 * @throws Exception
	 */
	private String addVRouterNicToPortGroup(String routerName, String portGroupName, int index) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();

		// 获得租户对应的vRouter
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", routerName);

		// 获得vRouter的网络适配器
		VirtualDevice[] virtualDevices = vRouter.getConfig().getHardware().getDevice();
		List<VirtualEthernetCard> virtualEthernetCards = new ArrayList<VirtualEthernetCard>();
		for (int i = 0; i < virtualDevices.length; i++) {
			if (virtualDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
				virtualEthernetCards.add(card);
			}
		}

		// 获得指定的端口
		VirtualEthernetCard nic = virtualEthernetCards.get(index - 1);

		// 将vRouter的网络适配器添加到需要绑定的子网的vlan中

		VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
		nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

		VirtualDeviceBackingInfo properties = nic.getBacking();
		VirtualEthernetCardNetworkBackingInfo nicBaking = (VirtualEthernetCardNetworkBackingInfo) properties;
		nicBaking.setDeviceName(portGroupName);// 指定要绑定的设配器(标准交换机端口)
		nic.setBacking(nicBaking);
		nicSpec.setDevice(nic);

		VirtualDeviceConnectInfo connectable = new VirtualDeviceConnectInfo();
		connectable.startConnected = true;
		connectable.allowGuestControl = true;
		connectable.connected = true;
		connectable.status = "untried";

		nic.setConnectable(connectable);

		VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };

		// 生成虚拟机配置规则
		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
		vmConfigSpec.setDeviceChange(nicSpecArray);

		Task vmTask = vRouter.reconfigVM_Task(vmConfigSpec);

		String result = vmTask.waitForTask();
		if (result == Task.SUCCESS) {
			return null;
		} else {
			return "绑定路由器失败！";
		}

	}

	private String addVRouterNicToDVSPortGroup(String routerName, String portGroupName, int index) throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();
		Folder rootFolder = si.getRootFolder();

		// 获得租户对应的vRouter
		VirtualMachine vRouter = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
				"VirtualMachine", routerName);

		// 获得vRouter的所有网络适配器
		VirtualDevice[] virtualDevices = vRouter.getConfig().getHardware().getDevice();
		List<VirtualEthernetCard> virtualEthernetCards = new ArrayList<VirtualEthernetCard>();
		for (int i = 0; i < virtualDevices.length; i++) {
			if (virtualDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
				virtualEthernetCards.add(card);
			}
		}

		// 获得指定的端口网络适配器
		VirtualEthernetCard nic = virtualEthernetCards.get(index - 1);

		// 获得所有分布式虚拟交换机
		ManagedEntity[] entity = new InventoryNavigator(rootFolder).searchManagedEntities("DistributedVirtualSwitch");

		String key = "";
		String uuid = "";
		boolean found = false;

		for (ManagedEntity me : entity) {

			if (me instanceof DistributedVirtualSwitch) {

				DistributedVirtualSwitch tmpDvs = (DistributedVirtualSwitch) me;
				DistributedVirtualPortgroup[] vpgs = tmpDvs.getPortgroup();

				for (DistributedVirtualPortgroup vpg : vpgs) {

					if (portGroupName.equals(vpg.getName())) {
						key = vpg.getConfig().getKey();
						uuid = tmpDvs.getConfig().getUuid();
						;
						found = true;
						break;
					}
				}

				if (found) {
					break;
				}
			}
		}

		// 设置分布式交换机端口组属性
		VirtualEthernetCard newNic = new VirtualVmxnet3();
		newNic.setKey(nic.getKey());
		newNic.setDeviceInfo(nic.getDeviceInfo());

		newNic.getDeviceInfo().setLabel(nic.getDeviceInfo().getLabel());

		VirtualEthernetCardDistributedVirtualPortBackingInfo backing9 = new VirtualEthernetCardDistributedVirtualPortBackingInfo();

		DistributedVirtualSwitchPortConnection port10 = new DistributedVirtualSwitchPortConnection();
		port10.setSwitchUuid(uuid);
		port10.setPortgroupKey(key);
		backing9.setPort(port10);

		newNic.setBacking(backing9);
		newNic.setAddressType("assigned");
		newNic.setMacAddress(nic.getMacAddress());
		newNic.setControllerKey(nic.getControllerKey());
		newNic.setUnitNumber(nic.getUnitNumber());

		VirtualDeviceConnectInfo connectable11 = new VirtualDeviceConnectInfo();
		connectable11.startConnected = true;
		connectable11.allowGuestControl = true;
		connectable11.connected = true;
		connectable11.status = "untried";

		newNic.setConnectable(connectable11);

		// 将vRouter的网络适配器添加到需要绑定的子网的vlan中
		VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
		nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

		nicSpec.setDevice(newNic);

		VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };

		// 生成虚拟机配置规则
		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
		vmConfigSpec.setDeviceChange(nicSpecArray);

		Task vmTask = vRouter.reconfigVM_Task(vmConfigSpec);

		String result = vmTask.waitForTask();
		if (result == Task.SUCCESS) {
			return null;
		} else {
			return "绑定路由器失败！";
		}
	}

	@Override
	public void createFirewall(Firewall firewall) throws Exception {

		/**
		 * 创建防火墙只是创建一些进出规则，将创建规则的脚本所需变量参数以及防火墙自身的属性存放起来
		 */

		// 防火墙自身属性参数
		String firewallName = ""; // 参数 防火墙名词
		String description = ""; // 参数 描述

		// 防火墙规则变量参数
		/**
		 * 规则1 允许电信的流量进入某子网所连端口（电信默认连端口8）
		 * 
		 * 所需参数 ： strategyNo（策略号） targetSubnet_port(子网所连端口) targetSubnet_addressPool（子网地址池名称）
		 */
		// 子网上公网 ( EIP相关 ) 5
		// config firewall policy
		// edit 6 （策略号）
		// set srcintf "port8"
		// set dstintf "port1" (子网所连端口)
		// set srcaddr "all"
		// set dstaddr "subnet1" （子网地址池名称）
		// set action accept
		// set schedule "always"
		// set service "ALL"
		// set nat enable
		// next
		// end
		int strategyNo = 100; // 参数 策略号
		String targetSubnet_port = ""; // 参数 子网所连端口
		String targetSubnet_addressPool = ""; // 参数 子网地址池名称
		String forward_internetStrategyConfigScript = FirewallScriptService.generateInternetStrategyConfigScript(
				strategyNo, SDNConstants.CTC_DEFAULT_PORT, targetSubnet_port, "all", targetSubnet_addressPool);
		/**
		 * 规则2 允许某子网所连端口流量进入电信所连端口（电信默认连端口8）
		 */
		String reverse_internetStrategyConfigScript = FirewallScriptService.generateInternetStrategyConfigScript(
				strategyNo + 1, targetSubnet_port, SDNConstants.CTC_DEFAULT_PORT, targetSubnet_addressPool, "all");

		// 配置接口IP地址脚本(网关)（EIP相关 ） 1
		// config system interface
		// edit "port3"
		// set ip 221.237.156.150 255.255.255.0
		// set allowaccess ping https ssh telnet
		// set type physical
		// set snmp-index 8
		int port1 = 777; // 参数
		String ip = "";
		String subnetMask = "";
		String portIpConfigScript = FirewallScriptService.generatePortIpConfigScript(port1, ip, subnetMask);

		// 配置默认路由脚本 （EIP相关 ） 2
		// config router static
		// edit 100
		// set device "port20"
		// set gateway 221.237.156.1
		String no = "";
		String port2 = "";
		String gateway = "";
		// String defaultRouteConfigScript = FirewallScriptService.generateDefaultRouteConfigScript(no, port2, gateway);

		// 创建地址段 3
		// config firewall address
		// edit "172.16.2.0/24"
		// set subnet 172.16.2.0 255.255.255.0
		// next
		String segment = ""; // 网段
		String gateway1 = "";
		String subnetMask1 = "";
		String addressField = FirewallScriptService.generateAddressFieldConfigScript(segment, gateway1, subnetMask1);

		// 配置子网间的策略 4
		// config firewall policy
		// edit 6
		// set srcintf "port2"
		// set dstintf "port3"
		// set srcaddr "172.16.2.0/24"
		// set dstaddr "172.16.3.0/24"
		// set action accept
		// set schedule "always"
		// set service "ALL"
		// next
		// String strategyNo = "";
		// String sourceSubnet_port = ""; // 端口号 参数
		// String targetSubnet_port = ""; // 端口号 参数
		// String sourceSubnet_segment = ""; // 源子网网段 参数
		// String targetSubnet_segment = ""; // 目标子网网段 参数

		// String subnetStrategyConfigScript = FirewallScriptService.generateSubnetStrategyConfigScript(strategyNo,
		// sourceSubnet_port, targetSubnet_port, sourceSubnet_segment, targetSubnet_segment);
	}

	@Override
	public void bindingFirewall(BindingFirewallParameter parameter) throws Exception {

		// 获取脚本所需变量参数
		String vRouter_ip = parameter.getControlIp(); // 参数vRouter的管理IP
		String ctc_subnetMask = parameter.getSubnetMask_CTC(); // 参数
		String gateway = parameter.getGateway_CTC(); // 参数
		String routerName = parameter.getRouterName();
		int routeNo = parameter.getRouteNo();
		String subnetAddressPoolName = parameter.getSubnetAddressPoolName(); // 地址池名称
		String ip_ISP = parameter.getIp_CTC();
		int strategyNo = parameter.getStrategyNo();
		String subnetPort = parameter.getSubnetPort();

		/**
		 * 将端口8连接到电信VLAN(ISP_CTC_VLAN1000）中
		 */
		addVRouterNicToDVSPortGroup(routerName, SDNConstants.CTC_DEFAULT_PORTGROUPNAME, SDNConstants.CTC_DEFAULT_PORTNO);

		/**
		 * 配置连接电信的端口（默认端口8）IP地址
		 */
		FirewallService.configurationPortIp(vRouter_ip, SDNConstants.CTC_DEFAULT_PORTNO, ip_ISP, ctc_subnetMask);

		/**
		 * 配置连接电信的端口（默认端口8）的网关
		 */
		FirewallService.configurationPortGateway(vRouter_ip, SDNConstants.CTC_DEFAULT_PORT, routeNo, gateway);

		/**
		 * 手动执行防火墙更新文件
		 */

		/**
		 * 配置需要访问互联网的子网与电信网络之间的策略
		 */
		FirewallService.configurationInternetStrategy(vRouter_ip, strategyNo, subnetPort,
				SDNConstants.CTC_DEFAULT_PORT, subnetAddressPoolName, "all");

	}

	@Override
	public void createEip(CreateEipParameter createEipParameter) throws Exception {
		FirewallService.createEIp(createEipParameter);

	}
}
