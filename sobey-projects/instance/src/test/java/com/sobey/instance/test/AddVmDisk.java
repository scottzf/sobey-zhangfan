/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

 * Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

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

public class AddVmDisk {

	public static void main(String[] args) throws Exception {

		String vmname = "liukai02@sobey.com-10.10.101.1";
		// String op = "add";
		String op = "remove";

		ServiceInstance si = new ServiceInstance(new URL("https://10.10.2.20/sdk"), "root", "vmware", true);

		Folder rootFolder = si.getRootFolder();
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine",
				vmname);

		if (vm == null) {
			System.out.println("No VM " + vmname + " found");
			si.getServerConnection().logout();
			return;
		}

		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		if ("add".equalsIgnoreCase(op)) {

			// 磁盘模式VirtualDiskMode包含几种选项。 不同的FileBacking对不同的磁盘模式支持不同。
			// append :Changes are appended to the redo log; you revoke changes by removing the undo log.
			// independent_nonpersistent :Same as nonpersistent, but not affected by snapshots.
			// independent_persistent :Same as persistent, but not affected by snapshots.
			// nonpersistent :Changes to virtual disk are made to a redo log and discarded at power off.
			// persistent :Changes are immediately and permanently written to the virtual disk.
			// undoable :Changes are made to a redo log, but you are given the option to commit or undo.
			String diskMode = "persistent";

			long diskSize = 1L;
			String diskName = "vijava4";
			VirtualDeviceConfigSpec vdiskSpec = createAddDiskConfigSpec(vm, diskSize, diskMode, diskName);
			VirtualDeviceConfigSpec[] vdiskSpecArray = { vdiskSpec };
			vmConfigSpec.setDeviceChange(vdiskSpecArray);
		} else if ("remove".equalsIgnoreCase(op)) {
			// mode: persistent|independent_persistent,independent_nonpersistent
			String diskName = "vijava4";
			VirtualDeviceConfigSpec vdiskSpec = createRemoveDiskConfigSpec(vm, diskName);
			VirtualDeviceConfigSpec[] vdiskSpecArray = { vdiskSpec };
			vmConfigSpec.setDeviceChange(vdiskSpecArray);
		} else {
			System.out.println("Invlaid device type [disk|cd]");
			return;
		}

		Task task = vm.reconfigVM_Task(vmConfigSpec);
		System.out.println(task.waitForTask());
	}

	private static VirtualDevice findVirtualDevice(VirtualMachineConfigInfo vmConfig, String name) {
		VirtualDevice[] devices = vmConfig.getHardware().getDevice();
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getDeviceInfo().getLabel().equals(name)) {
				return devices[i];
			}
		}
		return null;
	}

	static VirtualDeviceConfigSpec createAddDiskConfigSpec(VirtualMachine vm, long diskSize, String diskMode,
			String diskName) throws Exception {
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualMachineConfigInfo vmConfig = (VirtualMachineConfigInfo) vm.getConfig();
		VirtualDevice[] vds = vmConfig.getHardware().getDevice();

		VirtualDisk disk = new VirtualDisk();
		VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();

		int key = 0;

		for (int k = 0; k < vds.length; k++) {
			if (vds[k].getDeviceInfo().getLabel().equalsIgnoreCase("SCSI Controller 0")) {
				key = vds[k].getKey();
			}
		}

		int j = 0;
		for (VirtualDevice virtualDevice : vds) {
			if (virtualDevice instanceof VirtualDisk) {
				j++;
			}
		}

		int unitNumber = j;

		String dsName = getFreeDatastoreName(vm, diskSize);
		if (dsName == null) {
			return null;
		}
		String fileName = "[" + dsName + "] " + vm.getName() + "/" + diskName + ".vmdk";

		diskfileBacking.setDiskMode(diskMode);
		diskfileBacking.setFileName(fileName);
		diskfileBacking.setThinProvisioned(true);
		diskfileBacking.setSplit(false);// split：标明磁盘文件是以多个不大于2GB的文件，还是单独文件存储
		diskfileBacking.setWriteThrough(false);// writeThrough：标明磁盘文件是直接写入洗盘还是缓冲

		disk.setControllerKey(key);
		disk.setUnitNumber(unitNumber);
		disk.setBacking(diskfileBacking);
		disk.setCapacityInKB(1024 * diskSize);
		disk.setKey(1);

		diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);
		diskSpec.setDevice(disk);
		return diskSpec;
	}

	static VirtualDeviceConfigSpec createRemoveDiskConfigSpec(VirtualMachine vm, String diskName) throws Exception {
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualDisk disk = (VirtualDisk) findVirtualDevice(vm.getConfig(), diskName);

		if (disk != null) {
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
			diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.destroy);
			diskSpec.setDevice(disk);
			return diskSpec;
		} else {
			System.out.println("No device found: " + diskName);
			return null;
		}
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
