package com.sobey.instance.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.data.TestData;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMTest extends TestCase {

	@Autowired
	private VMService service;

	@Test
	public void PrintInventory() throws RemoteException, MalformedURLException {
		ServiceInstance si = new ServiceInstance(new URL("https://10.10.2.20/sdk"), "root", "vmware", true);
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
		for (int i = 0; i < vms.length; i++) {
			System.out.println("vm[" + i + "]=" + vms[i].getName());
		}

		System.out.println("\n============ Hosts ============");
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] { {
				"HostSystem", "name" }, }, true);
		for (int i = 0; i < hosts.length; i++) {
			System.out.println("host[" + i + "]=" + hosts[i].getName());
			System.out.println(hosts[i]);
		}

		System.out.println("\n============ ResourcePools ============");
		ManagedEntity[] resourcePools = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {
				{ "ResourcePool", "name" }, { "HostSystem", "name" } }, true);
		for (int i = 0; i < resourcePools.length; i++) {
			System.err.println("resourcePools[" + i + "]=" + resourcePools[i].getName());
			System.out.println(resourcePools[i].getMOR().getType());
			System.out.println(resourcePools[i].getMOR().get_value());
		}

		si.getServerConnection().logout();
	}

	@Test
	public void VMInfo() throws InvalidProperty, RuntimeFault, RemoteException, MalformedURLException {

		ServiceInstance si = new ServiceInstance(new URL("https://10.10.2.20/sdk"), "root", "vmware", true);
		Folder rootFolder = si.getRootFolder();

		VirtualMachine myVM = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine",
				"liukai02@sobey.com-10.10.101.2");

		System.out.println(">>>>>>>>>>>>>>>>>" + myVM.getGuest().getNet()[0].getNetwork());

		VirtualMachineConfigInfo myVMInfo = myVM.getConfig();
		
		System.out.println(myVMInfo.getGuestFullName());

		System.out.println(myVM.getGuest().getIpAddress());

		VirtualHardware vmHardware = myVMInfo.getHardware();

		System.out.println("\n============ CPU个数：" + vmHardware.getNumCPU());
		System.out.println("\n============ 内存大小：" + vmHardware.getMemoryMB());

		VirtualDevice[] vmDevices = vmHardware.getDevice();
		for (int i = 0; i < vmDevices.length; i++) {
			if (vmDevices[i] instanceof VirtualDisk) {
				VirtualDisk vmDisk = (VirtualDisk) vmDevices[i];
				System.out.println("\n============ 存储大小" + vmDisk.getCapacityInKB());
			}

			if (vmDevices[i] instanceof VirtualEthernetCard) {
				VirtualEthernetCard card = (VirtualEthernetCard) vmDevices[i];
				System.out.println("\n============ mac地址：" + card.getMacAddress());
			}
		}
		si.getServerConnection().logout();

	}

	@Test
	public void cloneVM() {
		CloneVMParameter parameter = TestData.randomCloneVMParameter();
		service.cloneVM(parameter);
		service.changeVlan(parameter.getDatacenter(), parameter.getvMName(), parameter.getVlanId());
	}

	@Test
	public void changeVlan() {
		service.changeVlan(DataCenterEnum.XA.toString(), "Sobey123", 100);
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
