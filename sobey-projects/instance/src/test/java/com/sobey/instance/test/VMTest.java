package com.sobey.instance.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.MathsUtil;
import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.data.TestData;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.dto.VMInfoDTO;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMTest extends TestCase {

	@Autowired
	private VMService service;

	@Test
	public void getVMHostRelationship() throws RemoteException, MalformedURLException {

		ServiceInstance si = new ServiceInstance(new URL("https://172.20.0.19/sdk"), "administrator@vsphere.local",
				"Newmed!@s0bey", true);
		Folder rootFolder = si.getRootFolder();

		System.out.println("\n============ Virtual Machines ============");
		ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { {
				"VirtualMachine", "name" }, }, true);

		System.out.println(vms.length);
		for (int i = 0; i < vms.length; i++) {
			System.out.println("vm[" + i + "]=" + vms[i].getName());
			VirtualMachineRuntimeInfo vmri = (VirtualMachineRuntimeInfo) ((VirtualMachine) vms[i]).getRuntime();
			ManagedObjectReference host = vmri.getHost();
			ManagedObjectReference managedObjectReference = new ManagedObjectReference();
			managedObjectReference.setType("HostSystem");
			managedObjectReference.setVal(host.getVal());
			HostSystem hostSystem = (HostSystem) MorUtil.createExactManagedEntity(si.getServerConnection(),
					managedObjectReference);
			System.err.println(hostSystem.getName());
		}

	}

	@Test
	public void getHostVMRelationship() throws RemoteException, MalformedURLException {

		ServiceInstance si = new ServiceInstance(new URL("https://172.20.0.19/sdk"), "administrator@vsphere.local",
				"Newmed!@s0bey", true);
		Folder rootFolder = si.getRootFolder();

		System.out.println("\n============ Virtual Machines ============");
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { {
				"HostSystem", "name" }, }, true);

		for (int i = 0; i < hosts.length; i++) {
			ManagedObjectReference managedObjectReference = new ManagedObjectReference();
			managedObjectReference.setType("HostSystem");
			managedObjectReference.setVal(hosts[i].getMOR().getVal());
			HostSystem hostSystem = (HostSystem) MorUtil.createExactManagedEntity(si.getServerConnection(),
					managedObjectReference);
			for (ManagedEntity managedEntity : hostSystem.getVms()) {
				System.out.println(managedEntity.getName());
			}
		}

	}

	@Test
	public void PrintInventory() throws RemoteException, MalformedURLException {
		ServiceInstance si = new ServiceInstance(new URL("https://172.16.2.252/sdk"), "administrator@vsphere.local",
				"vmware", true);
		Folder rootFolder = si.getRootFolder();

		System.out.println("============ Data Centers ============");
		ManagedEntity[] dcs = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { { "Datacenter",
				"name" }, }, true);
		for (int i = 0; i < dcs.length; i++) {
			System.out.println("Datacenter[" + i + "]=" + dcs[i].getName());
		}

		System.out.println("\n============ Virtual Machines ============");
		ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { {
				"VirtualMachine", "name" }, }, true);
		System.out.println(vms.length);
		for (int i = 0; i < vms.length; i++) {
			System.out.println("vm[" + i + "]=" + vms[i].getName());
		}

		System.out.println("\n============ Hosts ============");
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { {
				"HostSystem", "name" }, }, true);
		for (int i = 0; i < hosts.length; i++) {
			System.out.println("host[" + i + "]=" + hosts[i].getName());
			ComputeResource cr = (ComputeResource) hosts[i].getParent();
			System.err.println(cr.getResourcePool().getMOR().getVal());
		}

		System.out.println("\n============ ResourcePools ============");
 
		ManagedEntity[] resourcePools = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {
				{ "ResourcePool", "name" } }, true);

		for (int i = 0; i < resourcePools.length; i++) {
			System.err.println("resourcePools[" + i + "]=" + resourcePools[i].getName());
			System.out.println(resourcePools[i]);
			System.out.println(resourcePools[i].getMOR().getType());
			System.out.println(resourcePools[i].getMOR().get_value());
		}

		si.getServerConnection().logout();
	}

	@Test
	public void getResourcesInfo() throws RemoteException, MalformedURLException {
		ServiceInstance si = new ServiceInstance(new URL("https://172.20.0.19/sdk"), "administrator@vsphere.local",
				"Newmed!@s0bey", true);
		Folder rootFolder = si.getRootFolder();

		HashMap<String, String> map = new HashMap<String, String>();

		ManagedEntity[] resourcePools = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {
				{ "ResourcePool", "name" }, { "HostSystem", "name" } }, true);
		for (int i = 0; i < resourcePools.length; i++) {
			// System.err.println(resourcePools[i].getMOR().get_value());

			if (i + 1 != resourcePools.length) {

				map.put(resourcePools[i].getMOR().get_value(), resourcePools[i + 1].getMOR().get_value());
			}

		}

		for (Entry<String, String> entity : map.entrySet()) {
			System.out.println(entity.getKey());
			System.err.println(entity.getValue());
		}

		si.getServerConnection().logout();
	}

	@Test
	public void VMInfo() throws InvalidProperty, RuntimeFault, RemoteException, MalformedURLException {

		ServiceInstance si = new ServiceInstance(new URL("https://172.20.0.19/sdk"), "administrator@vsphere.local",
				"Newmed!@s0bey", true);
		Folder rootFolder = si.getRootFolder();

		VirtualMachine myVM = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine",
				"172.20.1.64");

		/**
		 * VM是否关机只和status有关系,
		 * 
		 * 如果没有安装vmare tools ,myVM为null,不记录入CMDB的服务资源
		 * 
		 * netapp controller 适配器vcenter中能查出4个,但是通过SDK无法获得:myVM.getGuest().getNet()为null 分析是vmware tools
		 * 属于第三方/独立运行,故无法通过Guest获得.
		 */

		VirtualMachineConfigInfo myVMInfo = myVM.getConfig();

		VirtualHardware vmHardware = myVMInfo.getHardware();

		System.err.println("============ VM状态：" + myVM.getGuest().getGuestState());
		System.out.println("============ 操作系统：" + myVMInfo.getGuestFullName());
		System.out.println("============ IP地址：" + myVM.getGuest().getIpAddress());
		System.out.println("============ CPU个数：" + vmHardware.getNumCPU());
		System.out.println("============ 内存大小：" + vmHardware.getMemoryMB());
		System.out.println("============ 网络适配器：" + myVM.getGuest().getNet());

		for (Datastore datastore : myVM.getDatastores()) {
			System.out.println("============ 存储器：" + datastore.getName());
		}

		VirtualDevice[] vmDevices = vmHardware.getDevice();
		for (int i = 0; i < vmDevices.length; i++) {
			if (vmDevices[i] instanceof VirtualDisk) {
				VirtualDisk vmDisk = (VirtualDisk) vmDevices[i];
				System.out.println("============ 存储大小" + MathsUtil.div(vmDisk.getCapacityInKB(), 1048576));
			}

			if (vmDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) vmDevices[i];
				System.out.println("============ mac地址：" + card.getMacAddress());
			}
		}
		si.getServerConnection().logout();

	}

	@Test
	public void HostInfo() throws InvalidProperty, RuntimeFault, RemoteException, MalformedURLException {

		ServiceInstance si = new ServiceInstance(new URL("https://172.16.2.252/sdk"), "administrator@vsphere.local",
				"vmware", true);

		// 主机
		HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
				"172.16.2.31");

		ComputeResource computeResource = (ComputeResource) host.getParent();

		System.out.println(host.getName());
		System.out.println("============ ResourcePool：" + computeResource.getResourcePool().getMOR().getVal());
		System.out.println("============ 制造商：" + host.getHardware().getSystemInfo().getVendor());
		System.out.println("============ 型号：" + host.getHardware().getSystemInfo().getModel());
		System.out.println("============ 内存大小：" + host.getHardware().getMemorySize());
		System.out.println("============ CPU数量：" + host.getHardware().getCpuInfo().getNumCpuCores());
		// 频率由HZ -> GHZ = HZ/1024*1024*1024
		System.out.println("============ CPU频率："
				+ MathsUtil.div(Double.valueOf(host.getHardware().getCpuInfo().getHz()), 1073741824));

		for (Datastore datastore : host.getDatastores()) {
			System.out.println("============ 存储器：" + datastore.getName());
		}

		for (Network network : host.getNetworks()) {
			System.out.println("============ 网络：" + network.getName());
		}

		si.getServerConnection().logout();
	}

	@Test
	public void getVMInfoDTO() {
		VMInfoDTO dto = service.getVMInfoDTO("172.20.1.64", DataCenterEnum.西安核心数据中心.toString());
		System.out.println(dto);
	}

	@Test
	public void cloneVM() {
		CloneVMParameter parameter = TestData.randomCloneVMParameter();
		service.cloneVM(parameter);
		service.changeVlan(parameter.getDatacenter(), parameter.getvMName(), parameter.getVlanId());
	}

	@Test
	public void changeVlan() {
		service.changeVlan(DataCenterEnum.西安核心数据中心.toString(), "Sobey123", 100);
	}

	@Test
	public void destroyVM() {
		DestroyVMParameter parameter = TestData.randomDestroyVMParameter();
		assertTrue(service.destroyVM(parameter));
	}

	@Test
	public void reconfigVM() {
		ReconfigVMParameter parameter = TestData.randomReconfigVMParameter();
		assertTrue(service.reconfigVM(parameter));
	}

	@Test
	public void powerVM() {
		PowerVMParameter parameter = TestData.randomPowerVMParameter();
		assertTrue(service.powerVM(parameter));
	}

	@Test
	public void getVM() {
		RelationVMParameter parameter = service.getVMAndHostRelation("XA");
		for (Entry<String, String> element : parameter.getRelationMaps().entrySet()) {
			System.out.println("VM:" + element.getKey());
			System.out.println("Host:" + element.getValue());
			System.out.println("************************");
		}

	}

}
