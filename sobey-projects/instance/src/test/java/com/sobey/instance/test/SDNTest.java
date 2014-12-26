package com.sobey.instance.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.instance.service.VMService;
import com.vmware.vim25.AlreadyExists;
import com.vmware.vim25.HostConfigFault;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualSwitch;
import com.vmware.vim25.HostVirtualSwitchSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.NotFound;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
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
public class SDNTest {

	@Autowired
	private VMService service;

	private ServiceInstance initServiceInstance() throws RemoteException, MalformedURLException {
		return new ServiceInstance(new URL("https://172.16.2.252/sdk"), "administrator@vsphere.local", "vmware", true);
	}

	@Test
	public void getAllResourse() throws RemoteException, MalformedURLException {

		Folder rootFolder = initServiceInstance().getRootFolder();

		ManagedEntity[] entities = new InventoryNavigator(rootFolder).searchManagedEntities(true);

		for (int i = 0; i < entities.length; i++) {
			System.out.println("entity[" + i + "]=" + entities[i].getName());
			System.out.println("Type[" + i + "]=" + entities[i].getMOR().getType());
			System.out.println();

		}

	}

	/**
	 * 创建标准交换机端口组
	 */
	@Test
	public void createHostPortGroup() throws HostConfigFault, AlreadyExists, NotFound, RuntimeFault, RemoteException,
			MalformedURLException {

		// 主机
		HostSystem host = (HostSystem) new InventoryNavigator(initServiceInstance().getRootFolder())
				.searchManagedEntity("HostSystem", "172.16.2.31");

		HostNetworkSystem hns = host.getHostNetworkSystem();

		// 标准虚拟交换机
		HostVirtualSwitch[] nets = hns.getNetworkInfo().getVswitch();

		// 标准虚拟交换机名称
		String switchName = nets[0].getName();

		// add a port group
		HostPortGroupSpec hpgs = new HostPortGroupSpec();

		hpgs.setName("Sobey_SDN");
		hpgs.setVlanId(20); // not associated with a VLAN 建议用12--4093之间的VLAN id
		hpgs.setVswitchName(switchName);
		hpgs.setPolicy(new HostNetworkPolicy());
		hns.addPortGroup(hpgs);

	}

	@Test
	public void deleteHostPortGroup() throws HostConfigFault, AlreadyExists, NotFound, RuntimeFault, RemoteException,
			MalformedURLException {

		HostSystem host = (HostSystem) new InventoryNavigator(initServiceInstance().getRootFolder())
				.searchManagedEntity("HostSystem", "172.16.2.31");

		HostNetworkSystem hns = host.getHostNetworkSystem();
		hns.removePortGroup("Sobey_SDN");
	}

	private VirtualMachine getVirtualMachine(ServiceInstance si, String vmname) throws InvalidProperty, RuntimeFault,
			RemoteException {
		return (VirtualMachine) new InventoryNavigator(si.getRootFolder())
				.searchManagedEntity("VirtualMachine", vmname);
	}

	/**
	 * 创建标准交换机
	 */
	@Test
	public void createHostVirtualSwitch() throws InvalidProperty, RuntimeFault, RemoteException, MalformedURLException {
		// 主机
		HostSystem host = (HostSystem) new InventoryNavigator(initServiceInstance().getRootFolder())
				.searchManagedEntity("HostSystem", "172.16.2.31");

		HostVirtualSwitchSpec spec = new HostVirtualSwitchSpec();
		spec.setNumPorts(8);
		HostNetworkSystem hostNetworkSystem = host.getHostNetworkSystem();
		hostNetworkSystem.addVirtualSwitch("Sobey Switch", spec);
	}

	@Test
	public void changePortGroup() {

		String portGroupName = "sdn_VLAN11";

		try {

			VirtualMachine vm = getVirtualMachine(initServiceInstance(), "zhangfan-001");

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
