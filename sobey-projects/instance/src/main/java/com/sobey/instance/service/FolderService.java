package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.instance.webservice.response.result.DTOListResult;
import com.sobey.instance.webservice.response.result.WSResult;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * VM Service
 * 
 * @author Administrator
 *
 */
@Service
public class FolderService extends VMWareService {

	private static Logger logger = LoggerFactory.getLogger(FolderService.class);

	public WSResult createFolder(String datacenter, String folderName, String parentFolderName) {

		WSResult result = new WSResult();

		ServiceInstance si;

		boolean isExist = true;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("moveVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		Folder parentFolder = null;

		if (StringUtils.isEmpty(parentFolderName)) {

			// parentFolder为空，则默认为vcenter的根目录
			parentFolder = si.getRootFolder();

			try {
				isExist = checkFolderIsExist(datacenter, folderName, parentFolder.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			// parentFolder不为空，查找对应父目录
			try {

				parentFolder = (Folder) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("Folder",
						parentFolderName);

				isExist = checkFolderIsExist(datacenter, folderName, parentFolderName);

			} catch (Exception e) {

				try {
					logout(si);
				} catch (RemoteException | MalformedURLException ex) {
					logger.info("createFolder::远程连接失败或错误的URL");
					result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
					return result;
				}

			}
		}

		if (parentFolder == null) {

			result.setError(WSResult.PARAMETER_ERROR, "指定父目录不存在");
			return result;

		} else {

			if (!isExist) {
				try {
					parentFolder.createFolder(folderName);
				} catch (RemoteException e) {
					try {
						logout(si);
					} catch (RemoteException | MalformedURLException ex) {
						logger.info("createFolder::远程连接失败或错误的URL");
						result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
						return result;
					}
				}
			}

		}

		return result;
	}

	/**
	 * 判断文件夹是否存在.<br>
	 * 
	 * True表示文件夹存在,False表示文件夹不存在
	 * 
	 * @param datacenter
	 * @param folderName
	 * @param parentFolder
	 * @return
	 * @throws Exception
	 */
	private boolean checkFolderIsExist(String datacenter, String folderName, String parentFolderName) throws Exception {

		ServiceInstance si = getServiceInstance(datacenter);

		List<String> folderNames = new ArrayList<String>();

		Folder parentFolder = (Folder) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("Folder",
				parentFolderName);

		ManagedEntity[] folders = parentFolder.getChildEntity();

		for (ManagedEntity entity : folders) {

			if (entity instanceof Folder) {
				Folder folder = (Folder) entity;
				folderNames.add(folder.getName());
			}

		}

		if (folderNames.contains(folderName)) {
			// list中包含了文件名,返回true
			return true;
		}

		return false;
	}

	public WSResult moveVM(String datacenter, String vmName, String folderName) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("moveVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, vmName);

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在,请联系系统管理员.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("moveVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		Folder rootFolder = si.getRootFolder();

		Folder targetFolder = null;

		try {

			targetFolder = (Folder) new InventoryNavigator(rootFolder).searchManagedEntity("Folder", folderName);

			if (targetFolder == null) {
				result.setError(WSResult.SYSTEM_ERROR, "文件夹不存在,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("moveVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		try {
			Task task = targetFolder.moveIntoFolder_Task(new ManagedEntity[] { vm });

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("moveVM:: VM cannot be moved");
				result.setError(WSResult.SYSTEM_ERROR, "VM移动失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;

	}

	public DTOListResult<String> queryVMInFolder(String datacenter, String folderName) {

		DTOListResult<String> result = new DTOListResult<String>();

		List<String> vms = new ArrayList<String>();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("moveVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		Folder rootFolder = si.getRootFolder();

		Folder folder = null;

		try {

			folder = (Folder) new InventoryNavigator(rootFolder).searchManagedEntity("Folder", folderName);

			if (folder == null) {
				result.setError(WSResult.SYSTEM_ERROR, "文件夹不存在,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("queryVMInFolder::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		ManagedEntity[] entities;

		try {
			entities = folder.getChildEntity();

			for (ManagedEntity entity : entities) {

				if (entity instanceof VirtualMachine) {

					VirtualMachine vm = (VirtualMachine) entity;
					vms.add(vm.getName());
				}

			}

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("queryVMInFolder::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		result.setDtos(vms);

		return result;
	}

}
