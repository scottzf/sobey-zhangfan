package com.sobey.sdn.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.sdn.bean.ECS;
import com.sobey.sdn.service.impl.SDNServiceImpl;
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
import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.DistributedVirtualSwitchPortConnection;
import com.vmware.vim25.HostIpConfig;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualNicSpec;
import com.vmware.vim25.HostVirtualSwitch;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardDistributedVirtualPortBackingInfo;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualVmxnet3;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;
import com.vmware.vim25.mo.CustomizationSpecManager;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMTest {

	@Autowired
	private SDNServiceImpl sdnService;

	@Test
	public void getResourcePoolInfo() throws Exception {

		ServiceInstance si = VcenterUtil.getServiceInstance();
		Folder rootFolder = si.getRootFolder();
//		VirtualMachine vm1 = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("VirtualMachine", "5001-02");
//		System.out.println("........................"+vm1.getParent().toString());
//		VirtualMachine vm2 = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("VirtualMachine", "Win2008");
//		System.out.println("........................"+vm2.getParent().toString());
//		
//		System.out.println("\n============ 存储 ============");
		/*ManagedEntity[] datas = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("Datastore");
		for (int i = 0; i < datas.length; i++) {
			Datastore data = (Datastore) datas[i];
			System.out.println(data.getName());
		}*/
		
		System.out.println("\n============ 存储 ============");
		System.out.println("\n============ Hosts ============");
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities("Network");
		
		for (ManagedEntity managedEntity : hosts) {
			System.out.println(managedEntity.getName());
			System.out.println(managedEntity.getMOR().getType());
			
		}
		
//		for (int i = 0; i < hosts.length; i++) {
//			HostSystem host = (HostSystem) hosts[i];
//			System.err.println("存储  "+host.getDatastores().length);
//			System.err.println("存储  "+host.getDatastores()[0].getName());
//			System.err.println("存储  "+host.getDatastores()[1].getName());
//			System.out.println("host[" + i + "]=" + hosts[i].getName());
//			System.out.println(hosts[i]);
//			System.err.println(hosts[i].getParent());
//			System.out.println("--------------------------------------");
//		}
//		System.out.println("\n============ Hosts ============");
//		// VirtualMachine vm = (VirtualMachine) new
//		// InventoryNavigator(si.getRootFolder()).searchManagedEntity("VirtualMachine", "Windows 2008 R2 Mod");
//		// System.err.println("fuck   "+vm.getDatastores()[0].getName());
//		// System.err.println("fuck   "+vm.getDatastores().length);
//		ManagedEntity[] vms = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("VirtualMachine");
//		for (int i = 0; i < vms.length; i++) {
//			VirtualMachine vm = (VirtualMachine) vms[i];
//			System.err.println(vm.getName() + "-----------" + vm.getResourcePool());
//		}
//		// System.out.println("模板"+vm.getParent().toString());
//		System.out.println("\n============ ResourcePools ============");
//		ManagedEntity[] resourcePools = new InventoryNavigator(rootFolder).searchManagedEntities("ResourcePool");
//		for (int i = 0; i < resourcePools.length; i++) {
//			System.err.println("resourcePools[" + i + "]=" + resourcePools[i].getName());
//			System.out.println(resourcePools[i].getMOR().getType());
//			System.out.println(resourcePools[i].getMOR().get_value());
//			System.out.println("--------------------------------------");
//
//		}
	}

	@Test
	public void cloneVM() throws Exception {
		/*
		 * ECS ecs = new ECS(); ecs.setLocalIp("172.16.5.31"); ecs.setTemplateName("Win2008");
		 * ecs.setTemplateOS("Windows"); sdnService.createECS(ecs);
		 */
		try {

			ServiceInstance si = VcenterUtil.getServiceInstance();
			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
					"VirtualMachine", "Win2008");
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

			info.setDescription("Windows");
			info.setName("Sobey");
			info.setType("Windows");// 设置克隆机器的操作系统类型

			specItem.setInfo(info);
			specItem.setSpec(cspec);

			// dns列表
			String dnsList[] = new String[] { "8.8.8.8" };
			String ipAddress = "172.16.10.15"; // 自定义的内网IP
			String subNetMask = "255.255.255.0";

			adapter.setDnsServerList(dnsList);
			adapter.setGateway(new String[] { "172.16.10.0" });
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

			CustomizationWinOptions winOptions = new CustomizationWinOptions();
			CustomizationSysprep cWinSysprep = new CustomizationSysprep();

			CustomizationGuiUnattended guiUnattended = new CustomizationGuiUnattended();
			guiUnattended.setAutoLogon(false);
			guiUnattended.setAutoLogonCount(1);
			guiUnattended.setTimeZone(210); // http://msdn.microsoft.com/en-us/library/ms912391%28v=winembedded.11%29.aspx

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
			//VirtualMachineRelocateSpec vmrs = new VirtualMachineRelocateSpec(); 
			
		//	HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
		//			"HostSystem", "172.16.2.32");
		//	vmrs.setHost(host.getMOR());
		//	vmrs.setDatastore(host.getDatastores()[0].getMOR());
		//	vmrs.setPool(pool);
		//	vmrs.setDatastore(); // mor ref to datastore
			//vmrs.setHost(findHostByName(serverName)); // findInventoryPath is used
			//vmrs.setPool(getResourcePool(serverName));
			
			
			relocateSpec.setPool(pool);
			cloneSpec.setLocation(relocateSpec);
			cloneSpec.setPowerOn(true);
			cloneSpec.setTemplate(false);
			cloneSpec.setCustomization(cspec);

			vm.checkCustomizationSpec(specItem.getSpec());

			Task task = vm.cloneVM_Task((Folder) vm.getParent(), "zhangfan-010", cloneSpec);
		//	vm.getServerConnection().getVimService().cloneVM_Task(vm.getMOR(),(Folder) vm.getParent(), "zhangfan-007",cloneSpec);
			
			// 为虚拟机设置网络适配器相关信息:设备状态设置为已连接、网络标签、虚拟机的备注.

		} catch (InvalidProperty e) {
			e.printStackTrace();
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		/*
		 * VirtualMachineRelocateSpec relocSpec = new VirtualMachineRelocateSpec(); cloneSpec.setLocation(relocSpec);
		 * cloneSpec.setPowerOn(false); cloneSpec.setTemplate(false);
		 * 
		 * String clonedName = cloneName; ManagedObjectReference
		 */

		/*
		 * VirtualMachineCloneSpec vmcs = new VirtualMachineCloneSpec(); 
		 * VirtualMachineRelocateSpec vmrs = new VirtualMachineRelocateSpec(); 
		 * vmrs.setDatastore(dataStore); // mor ref to datastore
		 * vmrs.setHost(findHostByName(serverName)); // findInventoryPath is used
		 * vmrs.setPool(getResourcePool(serverName));
		 * 
		 * 
		 * vmcs.setLocation(vmrs); 
		 * vmcs.setPowerOn(false); 
		 * vmcs.setTemplate(false);
		 * 
		 * ManagedObjectReference ref = connexion.cloneVM_Task(baseRef, vmFolder, clone, vmcs);
		 */
	}

	@Test
	public void createVlan() {
		try {

			ServiceInstance si = VcenterUtil.getServiceInstance();
			//主机
			HostSystem host =  (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem","172.16.2.31");
			HostNetworkSystem hns = host.getHostNetworkSystem();
			//标准虚拟交换机
			HostVirtualSwitch[] nets= hns.getNetworkInfo().getVswitch();
			//标准虚拟交换机名称
			String switchName = nets[0].getName();
			// add a port group
		    HostPortGroupSpec hpgs = new HostPortGroupSpec();
		    hpgs.setName("zhangfan_SDN_VNetWork16");
		    hpgs.setVlanId(36); // not associated with a VLAN   建议用12--4093之间的VLAN id
		    hpgs.setVswitchName(switchName);
		    hpgs.setPolicy(new HostNetworkPolicy());
		    hns.addPortGroup(hpgs);
			//
		    // add a virtual NIC to VMKernel
		    HostVirtualNicSpec hvns = new HostVirtualNicSpec();
		    hvns.setMac("00:50:56:89:a2:b4");
		    HostIpConfig hic = new HostIpConfig();
		    hic.setDhcp(false);
		    hic.setIpAddress("172.16.5.31");
		    hic.setSubnetMask("255.255.255.0");
		    hvns.setIp(hic);
		    //String result = hns.addVirtualNic("zhangfan_SDN_VNetWork15", hvns);
		   // String result = hns.addServiceConsoleVirtualNic("zhangfan_SDN_VNetWork16", hvns);
		   // System.out.println("=============="+result);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	@Test
	public void addVlanToVM() {

		try {

			ServiceInstance si = VcenterUtil.getServiceInstance();

			Folder rootFolder = si.getRootFolder();

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity(
					"VirtualMachine", "zhangfan-001");

			//主机
			HostSystem host =  (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem","172.16.2.31");
			HostNetworkSystem hns = host.getHostNetworkSystem();
			//标准虚拟交换机
			HostVirtualSwitch[] nets = hns.getNetworkInfo().getVswitch();
			HostVirtualSwitch vswitch = nets[0];
			
			Network network = (Network) new InventoryNavigator(rootFolder).searchManagedEntity("Network", "zhangfanTest");

			if (network == null) {
				System.out.println("Could not find network " + "zhangfanTest");
			}
			DistributedVirtualSwitch dvs = null;

			ManagedEntity[] entity = new InventoryNavigator(rootFolder).searchManagedEntities("Network");

			String key = "";
			DVPortgroupConfigInfo description = null;

			boolean found = false;

			for (ManagedEntity me : entity) {
				
				Network network2 = (Network) me;
				
				System.out.println(network2.getName());

				if (me instanceof DistributedVirtualSwitch) {

					DistributedVirtualSwitch tmpDvs = (DistributedVirtualSwitch) me;
					DistributedVirtualPortgroup[] vpgs = tmpDvs.getPortgroup();

					for (DistributedVirtualPortgroup vpg : vpgs) {

						if ("zhangfanTest".equals(vpg.getName())) {
							key = vpg.getConfig().getKey();
							description = vpg.getConfig();
							dvs = tmpDvs;
							found = true;
							break;
						}
					}

					if (found) {
						break;
					}
				}
			}

			VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();

			String uuid = dvs.getConfig().getUuid();

			ArrayList<VirtualDeviceConfigSpec> nicSpecList = new ArrayList<VirtualDeviceConfigSpec>();

			boolean nicFound = false;

			VirtualDevice[] vds = vmConfigInfo.getHardware().getDevice();
			for (VirtualDevice vd : vds) {
				if (vd instanceof VirtualEthernetCard) {

					VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
					nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

					VirtualEthernetCard nic = (VirtualEthernetCard) vd;

					System.out.println("Nic: " + nic.getDeviceInfo().getLabel());

//					if (nic.getDeviceInfo().getLabel().equalsIgnoreCase(nicName)) {
//						System.out.println("Found nic " + nicName);
//
//						VirtualEthernetCard newNic = new VirtualVmxnet3();
//						newNic.setKey(nic.getKey());
//						newNic.setDeviceInfo(nic.getDeviceInfo());
//
//						newNic.getDeviceInfo().setLabel(nicName);
//
//						VirtualEthernetCardDistributedVirtualPortBackingInfo backing9 = new VirtualEthernetCardDistributedVirtualPortBackingInfo();
//
//						DistributedVirtualSwitchPortConnection port10 = new DistributedVirtualSwitchPortConnection();
//						port10.setSwitchUuid(uuid);
//						port10.setPortgroupKey(key);
//						backing9.setPort(port10);
//
//						newNic.setBacking(backing9);
//						newNic.setAddressType("assigned");
//						newNic.setMacAddress(nic.getMacAddress());
//						newNic.setControllerKey(nic.getControllerKey());
//						newNic.setUnitNumber(nic.getUnitNumber());
//
//						VirtualDeviceConnectInfo connectable11 = new VirtualDeviceConnectInfo();
//						connectable11.startConnected = true;
//						connectable11.allowGuestControl = true;
//						connectable11.connected = true;
//						connectable11.status = "untried";
//
//						newNic.setConnectable(connectable11);
//
//						System.out.println("Setting UUID: " + uuid);
//						System.out.println("Setting portgroupKey: " + key);
//						System.out.println("Setting summary: " + netName);
//						System.out.println("Port description: " + description.getNumPorts());
//
//						nicSpec.setDevice(newNic);
//
//						nicSpecList.add(nicSpec);
//
//						nicFound = true;
//					}

				}
			}

//			if (!nicFound) {
//				System.out.println("Could not find nic " + nicName);
//			}

			VirtualDeviceConfigSpec[] configSpec = new VirtualDeviceConfigSpec[nicSpecList.size()];
			nicSpecList.toArray(configSpec);

			vmSpec.setDeviceChange(configSpec);

			Task vmTask = vm.reconfigVM_Task(vmSpec);

			String result = vmTask.waitForTask();

			result.equals(Task.SUCCESS);

		} catch (Exception exc) {
			System.out.println(exc);
		}
	}
	
	@Test
	public void changePortGroup() {

		String portGroupName = "sdn_VLAN13";

		try {

			ServiceInstance si = VcenterUtil.getServiceInstance();

			Folder rootFolder = si.getRootFolder();

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity(
					"VirtualMachine", "zhangfan-001");

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
					System.out.println(nicBaking.getDeviceName());
				}
			}

			VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };
			vmConfigSpec.setDeviceChange(nicSpecArray);

			Task vmTask = vm.reconfigVM_Task(vmConfigSpec);

			String result = vmTask.waitForTask();

			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
