package com.sobey.instance.test;

import java.net.URL;

import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * http://vijava.sf.net
 * 
 * @author Steve Jin
 */

public class RemoveVmDisk {
	public static void main(String[] args) throws Exception {

		ServiceInstance si = new ServiceInstance(new URL("https://10.10.2.20/sdk"), "root", "vmware", true);

		String vmname = "liukai02@sobey.com-10.10.101.1";

		Folder rootFolder = si.getRootFolder();
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine",
				vmname);

		String dsName = getFreeDatastoreName(vm, 1L);
		String diskName = "vijava";

		String fileName = "[" + dsName + "] " + vm.getName() + "/" + diskName + ".vmdk";

		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		VirtualDeviceConfigSpec vdiskSpec = createRemoveDiskConfigSpec(vm.getConfig(), fileName);
		vmConfigSpec.setDeviceChange(new VirtualDeviceConfigSpec[] { vdiskSpec });
		Task task = vm.reconfigVM_Task(vmConfigSpec);

		if (task.waitForTask() == Task.SUCCESS) {
			System.out.println("Disk removed.");
		} else {
			System.out.println("Error while removing disk");
		}

		si.getServerConnection().logout();
	}

	static VirtualDeviceConfigSpec createRemoveDiskConfigSpec(VirtualMachineConfigInfo vmConfig, String diskName)
			throws Exception {
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualDisk disk = (VirtualDisk) findVirtualDevice(vmConfig, diskName);

		if (disk != null) {
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
			// remove the following line can keep the disk file
			diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.destroy);
			diskSpec.setDevice(disk);
			return diskSpec;
		} else {
			throw new Exception("No device found: " + diskName);
		}
	}

	private static VirtualDevice findVirtualDevice(VirtualMachineConfigInfo cfg, String name) {
		VirtualDevice[] devices = cfg.getHardware().getDevice();

		for (int i = 0; devices != null && i < devices.length; i++) {

			if (devices[i] instanceof VirtualDisk) {

				VirtualDiskFlatVer2BackingInfo backingInfo = (VirtualDiskFlatVer2BackingInfo) devices[i].getBacking();

				if (backingInfo.getFileName().equals(name)) {
					return devices[i];
				}

			}

		}
		return null;
	}

	static String getFreeDatastoreName(VirtualMachine vm, long size) throws Exception {
		String dsName = null;
		Datastore[] datastores = vm.getDatastores();
		for (int i = 0; i < datastores.length; i++) {
			DatastoreSummary ds = datastores[i].getSummary();
			if (ds.getFreeSpace() > size) {
				dsName = ds.getName();
				break;
			}
		}
		return dsName;
	}
}