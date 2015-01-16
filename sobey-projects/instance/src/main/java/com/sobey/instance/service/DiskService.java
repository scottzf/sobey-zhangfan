package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.instance.webservice.response.dto.VMDiskParameter;
import com.sobey.instance.webservice.response.result.WSResult;
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
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Disk Service
 * 
 * @author Administrator
 *
 */
@Service
public class DiskService extends VMWareService {

	/**
	 * 磁盘模式VirtualDiskMode选项:<br>
	 * 
	 * <b>append</b> :Changes are appended to the redo log; you revoke changes by removing the undo log.<br>
	 * 
	 * <b>independent_nonpersistent</b> :Same as nonpersistent, but not affected by snapshots.<br>
	 * 
	 * <b>independent_persistent</b> :Same as persistent, but not affected by snapshots.<br>
	 * 
	 * <b>nonpersistent</b> :Changes to virtual disk are made to a redo log and discarded at power off.<br>
	 * 
	 * <b>persistent</b> :Changes are immediately and permanently written to the virtual disk.<br>
	 * 
	 * <b>undoable</b> :Changes are made to a redo log, but you are given the option to commit or undo.<br>
	 */
	private static final String diskMode = "persistent";

	private static Logger logger = LoggerFactory.getLogger(DiskService.class);

	/**
	 * SCSI Controller 名称 , 有时候可能是 全英文,如 "SCSI Controller 0" 需要提前检验.
	 */
	private static final String SCSI_Controller_Name = "SCSI 控制器 0";

	/**
	 * 默认存储分配存储器: vsanDatastore
	 * 
	 */
	private static final String Default_Datastore = "vsanDatastore";

	/**
	 * 对新增的存储进行设置
	 * 
	 * @param vm
	 * @param diskSize
	 *            存储大小
	 * @param diskMode
	 *            存储模式
	 * @param diskName
	 *            存储名称
	 * @return 返回null时,表示没有可创建的存储器.
	 */
	private static VirtualDeviceConfigSpec createAddDiskConfigSpec(VirtualMachine vm, long diskSize, String diskMode,
			String diskName) {

		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();

		VirtualMachineConfigInfo vmConfig = (VirtualMachineConfigInfo) vm.getConfig();

		VirtualDevice[] vds = vmConfig.getHardware().getDevice();

		VirtualDisk disk = new VirtualDisk();

		VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();

		int key = 0;

		for (int k = 0; k < vds.length; k++) {
			// System.out.println(vds[k].getDeviceInfo().getLabel());
			if (StringUtils.equalsIgnoreCase(SCSI_Controller_Name, vds[k].getDeviceInfo().getLabel())) {
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

		// String dsName = getFreeDatastoreName(vm, diskSize);
		String dsName = Default_Datastore;

		if (dsName == null) {
			return null;
		}

		// 自定义存储路径
		String fileName = "[" + dsName + "] " + vm.getName() + "/" + diskName + ".vmdk";

		diskfileBacking.setDiskMode(diskMode);
		diskfileBacking.setFileName(fileName);
		diskfileBacking.setThinProvisioned(true);
		diskfileBacking.setSplit(false);// split：标明磁盘文件是以多个不大于2GB的文件，还是单独文件存储
		diskfileBacking.setWriteThrough(false);// writeThrough：标明磁盘文件是直接写入洗盘还是缓冲

		disk.setControllerKey(key);
		disk.setUnitNumber(unitNumber);
		disk.setBacking(diskfileBacking);
		disk.setCapacityInKB(1024 * 1024 * diskSize);
		disk.setKey(1);

		diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);
		diskSpec.setDevice(disk);
		return diskSpec;
	}

	/**
	 * 对删除的存储进行设置
	 * 
	 * @param vmConfig
	 * @param diskName
	 * @return
	 */
	private static VirtualDeviceConfigSpec createRemoveDiskConfigSpec(VirtualMachineConfigInfo vmConfig, String diskName) {
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		VirtualDisk disk = (VirtualDisk) findVirtualDevice(vmConfig, diskName);

		if (disk != null) {
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
			// remove the following line can keep the disk file
			diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.destroy);
			diskSpec.setDevice(disk);
		} else {
			logger.info("getFreeDatastoreName::No device found" + diskName);
		}
		return diskSpec;
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

	/**
	 * 为存储空间指定一个存储器
	 * 
	 * @param vm
	 * @param size
	 * @return
	 */
	private static String getFreeDatastoreName(VirtualMachine vm, long size) {

		String dsName = null;

		Datastore[] datastores = null;

		try {
			datastores = vm.getDatastores();
		} catch (RemoteException e) {
			logger.info("getFreeDatastoreName::远程连接失败或错误的URL");
		}

		for (Datastore datastore : datastores) {
			DatastoreSummary ds = datastore.getSummary();
			// 只要存储器空间大于需要创建存储的大小,将其名称保存并返回.
			if (ds.getFreeSpace() > size) {
				dsName = ds.getName();
				break;
			}
		}

		return dsName;
	}

	/**
	 * 为VM分配存储空间
	 * 
	 * @param parameter
	 *            {@link VMDiskParameter}
	 * @return
	 */
	public WSResult createVMDisk(VMDiskParameter parameter) {

		WSResult result = new WSResult();

		long diskSize = 0;

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("createVMDisk::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		VirtualMachine vm = null;

		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("createVMDisk::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		try {
			diskSize = Long.valueOf(parameter.getDiskGB());// 设置存储大小
		} catch (NumberFormatException e) {
			result.setError(WSResult.SYSTEM_ERROR, "存储大小输入有误,必须是整数.");
		}

		String diskName = parameter.getDiskName();

		VirtualDeviceConfigSpec vdiskSpec = createAddDiskConfigSpec(vm, diskSize, diskMode, diskName);

		if (vdiskSpec == null) {
			result.setError(WSResult.SYSTEM_ERROR, "存储器空间不足,请联系系统管理员.");
		}

		VirtualDeviceConfigSpec[] vdiskSpecArray = { vdiskSpec };
		vmConfigSpec.setDeviceChange(vdiskSpecArray);

		try {

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("Failure -:   cannot be created stroage");
				result.setError(WSResult.SYSTEM_ERROR, "存储创建失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

	/**
	 * 删除为VM分配存储空间
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult deleteVMDisk(VMDiskParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("deleteVMDisk::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		VirtualMachine vm = null;

		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机规格不存在,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("deleteVMDisk::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		String dsName = getFreeDatastoreName(vm, Long.valueOf(parameter.getDiskGB()));
		String diskName = parameter.getDiskName();
		String fileName = "[" + dsName + "] " + vm.getName() + "/" + diskName + ".vmdk";

		VirtualDeviceConfigSpec vdiskSpec = createRemoveDiskConfigSpec(vm.getConfig(), fileName);
		vmConfigSpec.setDeviceChange(new VirtualDeviceConfigSpec[] { vdiskSpec });

		try {

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("Failure -:   cannot be remove stroage");
				result.setError(WSResult.SYSTEM_ERROR, "删除存储失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

}
