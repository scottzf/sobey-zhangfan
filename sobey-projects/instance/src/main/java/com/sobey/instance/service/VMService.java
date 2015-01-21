package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sobey.core.utils.MathsUtil;
import com.sobey.instance.constans.PowerOperationEnum;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.CustomizeVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.dto.RenameVMParameter;
import com.sobey.instance.webservice.response.dto.RunNetworkDeviceVMParameter;
import com.sobey.instance.webservice.response.dto.RunVMParameter;
import com.sobey.instance.webservice.response.dto.VMInfoDTO;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;
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
import com.vmware.vim25.CustomizationSysprep;
import com.vmware.vim25.CustomizationSysprepRebootOption;
import com.vmware.vim25.CustomizationUserData;
import com.vmware.vim25.CustomizationWinOptions;
import com.vmware.vim25.GuestNicInfo;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

/**
 * VM Service
 * 
 * @author Administrator
 *
 */
@Service
public class VMService extends VMWareService {

	private static Logger logger = LoggerFactory.getLogger(VMService.class);

	private static final String OrgNme = "Sobey";

	/**
	 * 克隆Firewall或netscarler等网络设备.
	 * 
	 * 根据vCenter中的网络设备模板进行克隆,不需要进行CustomizationSpec的配置
	 * 
	 * @param parameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	public WSResult cloneVM(CloneVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("cloneVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得模板对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmTemplateName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "模板不存在,请联系系统管理员.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("cloneVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		// 虚拟机克隆方案创建
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();

		ManagedObjectReference pool = new ManagedObjectReference();
		pool.set_value(parameter.getResourcePool());
		pool.setType("ResourcePool");

		ManagedObjectReference host = new ManagedObjectReference();
		host.set_value(parameter.getHostId());
		host.setType("HostSystem");

		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setPool(pool);
		relocateSpec.setHost(host);
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(false);
		cloneSpec.setTemplate(false);

		try {

			Task task = vm.cloneVM_Task((Folder) vm.getParent(), parameter.getVmName(), cloneSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("cloneVM:: VM cannot be cloned");
				result.setError(WSResult.SYSTEM_ERROR, "VM创建失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

	/**
	 * 销毁虚拟机
	 * 
	 * @param parameter
	 *            {@link DestroyVMParameter}
	 * @return
	 */
	public WSResult destroyVM(DestroyVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("destroyVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("destroyVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		try {

			Task task = vm.destroy_Task();

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("destroyVM:: VM cannot be destroy");
				result.setError(WSResult.SYSTEM_ERROR, "主机删除失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

	/**
	 * 根据虚拟机名称获得虚拟机的具体信息.
	 * 
	 * @param name
	 *            VM名称
	 * @param datacenter
	 *            数据中心
	 * @return
	 */
	public DTOResult<VMInfoDTO> findVMInfoDTO(String vmName, String datacenter) {

		DTOResult<VMInfoDTO> dtoResult = new DTOResult<VMInfoDTO>();

		VMInfoDTO vmInfoDTO = new VMInfoDTO();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("findVMInfoDTO::远程连接失败或错误的URL");
			dtoResult.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return dtoResult;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, vmName);

			if (vm == null) {
				dtoResult.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return dtoResult;
			}

			// 判断虚拟机是否安装有vmware tools.

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();

			VirtualHardware vmHardware = vmConfigInfo.getHardware();

			// 网络适配器
			if (vm.getGuest().getNet() != null && vm.getGuest().getNet().length != 0) {
				String portGroups = "";
				for (GuestNicInfo nicInfo : vm.getGuest().getNet()) {
					portGroups += nicInfo.getNetwork() + " ";
				}
				vmInfoDTO.setPortGroups(portGroups);
			}

			// 存储器
			if (vm.getDatastores() != null && vm.getDatastores().length != 0) {

				String datastores = "";
				for (Datastore datastore : vm.getDatastores()) {
					datastores += datastore.getName() + " ";
				}
				vmInfoDTO.setDatastore(datastores);
			}

			vmInfoDTO.setGuestFullName(vmConfigInfo.getGuestFullName());
			vmInfoDTO.setIpaddress(vm.getGuest().getIpAddress());
			vmInfoDTO.setCpuNumber(Integer.valueOf(vmHardware.getNumCPU()).toString());
			vmInfoDTO.setMemoryMB(Integer.valueOf(vmHardware.getMemoryMB()).toString());
			vmInfoDTO.setVmName(vmName);
			vmInfoDTO.setStatus(vm.getGuest().getGuestState());// running or notRunning

			if (vmHardware != null) {
				VirtualDevice[] vmDevices = vmHardware.getDevice();
				for (int i = 0; i < vmDevices.length; i++) {

					if (vmDevices[i] instanceof VirtualEthernetCard) {
						VirtualEthernetCard card = (VirtualEthernetCard) vmDevices[i];
						vmInfoDTO.setMacIPaddress(card.getMacAddress());
					}

					if (vmDevices[i] instanceof VirtualDisk) {
						VirtualDisk vmDisk = (VirtualDisk) vmDevices[i];
						// 硬盘空间大小单位为KB,此处将硬盘大小除以1024*1024得到GB的单位.
						vmInfoDTO.setDiskGB(String.valueOf(MathsUtil.div(vmDisk.getCapacityInKB(), 1048576)));
					}

				}
			}

			dtoResult.setDto(vmInfoDTO);

		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("findVMInfoDTO::远程连接失败或错误的URL");
				dtoResult.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return dtoResult;
			}
		}

		return dtoResult;
	}

	/**
	 * 获得Host和VM的关联关系.<br>
	 * 
	 * 利用HashMap key不能重复,value可以重复的特性,将VM名保存在key中,而Host名因为有重复的存在,所以放在value.<br>
	 * 这样保证了每个key(VM)对应一个Value(Host),key永远不会重复
	 * 
	 * @return
	 */
	public RelationVMParameter getRelationVM(String datacenter) {

		HashMap<String, String> map = Maps.newHashMap();

		ServiceInstance si;

		try {

			si = getServiceInstance(datacenter);

			ManagedEntity[] entities = new InventoryNavigator(si.getRootFolder())
					.searchManagedEntities("VirtualMachine");

			for (ManagedEntity managedEntity : entities) {

				VirtualMachine vm = (VirtualMachine) managedEntity;

				VirtualMachineRuntimeInfo vmri = (VirtualMachineRuntimeInfo) vm.getRuntime();
				ManagedObjectReference host = vmri.getHost();

				ManagedObjectReference managedObjectReference = new ManagedObjectReference();

				managedObjectReference.setType("HostSystem");
				managedObjectReference.setVal(host.getVal());
				HostSystem hostSystem = (HostSystem) MorUtil.createExactManagedEntity(si.getServerConnection(),
						managedObjectReference);

				map.put(vm.getName(), hostSystem.getName());
			}

		} catch (RemoteException | MalformedURLException e) {
			logger.info("getVMAndHostRelation::远程连接失败或错误的URL");
		}

		RelationVMParameter parameter = new RelationVMParameter();
		parameter.setDatacenter(datacenter);
		parameter.setRelationMaps(map);

		return parameter;
	}

	/**
	 * 对虚拟机的电源操作,操作内容可参考 {@link PowerOperationEnum}
	 * 
	 * @param parameter
	 *            {@link PowerVMParameter}
	 * @return
	 */
	public WSResult powerVM(PowerVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("powerVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("powerVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		try {

			Task task;

			switch (PowerOperationEnum.valueOf(parameter.getPowerOperation())) {

			case poweroff:

				task = vm.powerOffVM_Task();

				if (task.waitForTask() != Task.SUCCESS) {
					result.setError(WSResult.SYSTEM_ERROR, "主机关机失败,请联系系统管理员.");
				}

				break;

			case poweron:

				task = vm.powerOnVM_Task(null);
				if (task.waitForTask() != Task.SUCCESS) {
					result.setError(WSResult.SYSTEM_ERROR, "主机开机失败,请联系系统管理员.");
				}

				break;

			case reboot:
				vm.rebootGuest();
				break;

			case reset:

				task = vm.resetVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					result.setError(WSResult.SYSTEM_ERROR, "主机重置失败,请联系系统管理员.");
				}

				break;

			case shutdown:

				task = vm.suspendVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					result.setError(WSResult.SYSTEM_ERROR, "主机暂停失败,请联系系统管理员.");
				}

				break;

			case standby:

				vm.standbyGuest();
				break;

			case suspend:

				task = vm.suspendVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					result.setError(WSResult.SYSTEM_ERROR, "主机暂停失败,请联系系统管理员.");
				}

				break;

			default:

				result.setError(WSResult.SYSTEM_ERROR, "该电源操作不存在.");
				break;
			}

		} catch (RemoteException | InterruptedException e) {
			logger.info("powerVM::远程连接失败");
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;

	}

	/**
	 * 修改虚拟机配置
	 * 
	 * @param parameter
	 *            {@link ReconfigVMParameter}
	 * @return
	 */
	public WSResult reconfigVM(ReconfigVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("reconfigVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("reconfigVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		}

		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		vmConfigSpec.setMemoryMB(Long.valueOf(parameter.getMemoryMB()));
		vmConfigSpec.setNumCPUs(parameter.getCpuNumber());

		try {

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("reconfigVM:: VM cannot be reconfig");
				result.setError(WSResult.SYSTEM_ERROR, "主机修改失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

	/**
	 * 自定义VM IP配置
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult customizeVM(CustomizeVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("reconfigVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("reconfigVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "主机不存在.");
				return result;
			}
		}

		CustomizationGlobalIPSettings gIP = new CustomizationGlobalIPSettings();

		String[] dns = { "8.8.8.8" };
		String[] domain = { "sobey.com" };
		String[] gateway = { parameter.getGateway() };

		gIP.dnsServerList = dns;
		gIP.dnsSuffixList = domain;

		CustomizationFixedIp fixedIp = new CustomizationFixedIp();// 指定使用固定ip

		fixedIp.ipAddress = parameter.getIpaddress();

		CustomizationIPSettings adapter = new CustomizationIPSettings();
		adapter.dnsDomain = domain[0];
		adapter.dnsServerList = dns;
		adapter.ip = fixedIp;
		adapter.gateway = gateway;
		adapter.subnetMask = parameter.getSubNetMask();

		CustomizationAdapterMapping adaptorMap = new CustomizationAdapterMapping();
		adaptorMap.adapter = adapter;

		CustomizationAdapterMapping[] nicSettingMap = new CustomizationAdapterMapping[] { adaptorMap };

		CustomizationFixedName computerName = new CustomizationFixedName();
		computerName.name = OrgNme;

		CustomizationSpec customspec = new CustomizationSpec();

		if ("Linux".equals(parameter.getVmTemplateOS())) {

			CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
			CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
			linuxPrep.setDomain("sobey.com");
			linuxPrep.setHostName(computerName);
			linuxPrep.setHwClockUTC(true);
			linuxPrep.setTimeZone("Asia/Shanghai");

			customspec.identity = linuxPrep;
			customspec.options = linuxOptions;

		} else if ("Windows".equals(parameter.getVmTemplateOS())) {

			CustomizationWinOptions winOptions = new CustomizationWinOptions();
			CustomizationSysprep winSysprep = new CustomizationSysprep();

			CustomizationGuiUnattended guiUnattended = new CustomizationGuiUnattended();
			guiUnattended.setAutoLogon(false);
			guiUnattended.setAutoLogonCount(1);
			// 210为windows系统下的东八区时区标示,详情参考 http://msdn.microsoft.com/en-us/library/ms912391%28v=winembedded.11%29.aspx
			guiUnattended.setTimeZone(210);

			CustomizationPassword password = new CustomizationPassword();
			password.setValue(WINDOWS_DEFAULT_PASSWORD);
			password.setPlainText(true);
			guiUnattended.setPassword(password);
			winSysprep.setGuiUnattended(guiUnattended);

			CustomizationUserData userData = new CustomizationUserData();
			userData.setProductId("");
			userData.setFullName(OrgNme);
			userData.setOrgName(OrgNme);
			userData.setComputerName(computerName);
			winSysprep.setUserData(userData);

			// Windows Server 2000, 2003 必须
			CustomizationLicenseFilePrintData printData = new CustomizationLicenseFilePrintData();
			printData.setAutoMode(CustomizationLicenseDataMode.perSeat);
			winSysprep.setLicenseFilePrintData(printData);

			CustomizationIdentification identification = new CustomizationIdentification();
			identification.setJoinWorkgroup(OrgNme);
			winSysprep.setIdentification(identification);

			winOptions.setReboot(CustomizationSysprepRebootOption.shutdown);
			winOptions.setChangeSID(true);
			winOptions.setDeleteAccounts(false);

			customspec.setOptions(winOptions);
			customspec.setIdentity(winSysprep);
		}

		customspec.globalIPSettings = gIP;
		customspec.nicSettingMap = nicSettingMap;
		customspec.encryptionKey = si.getCustomizationSpecManager().getEncryptionKey();

		try {

			Task task = vm.customizeVM_Task(customspec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("reconfigVM:: VM cannot be reconfig");
				result.setError(WSResult.SYSTEM_ERROR, "主机IP定义失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();

			return result;
		}

		return result;
	}

	public WSResult runVM(RunVMParameter parameter) {

		WSResult result = new WSResult();

		/**
		 * Step.1 自定义IP.
		 * 
		 * Step.2 自定义CPU和Memory
		 * 
		 * Step.3 更改VM名称
		 * 
		 * Step.4 启动VM
		 * 
		 */

		// Step.1 自定义IP.

		// 注意开始的vmname应该是vcenter中 VM Pool中已经创建好的一个VM名称
		CustomizeVMParameter customizeVMParameter = new CustomizeVMParameter(parameter.getDatacenter(),
				parameter.getGateway(), parameter.getIpaddress(), parameter.getSubNetMask(), parameter.getTempVMName(),
				parameter.getVmTemplateOS());

		WSResult customizeVMWSResult = customizeVM(customizeVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(customizeVMWSResult.getCode())) {
			return customizeVMWSResult;
		}

		// Step.2 自定义CPU和Memory
		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter(parameter.getCpuNumber(),
				parameter.getDatacenter(), parameter.getMemoryMB(), parameter.getTempVMName());

		WSResult reconfigVMWSResult = reconfigVM(reconfigVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(reconfigVMWSResult.getCode())) {
			return reconfigVMWSResult;
		}

		// Step.3 更改VM名称
		RenameVMParameter renameVMParameter = new RenameVMParameter(parameter.getDatacenter(),
				parameter.getTempVMName(), parameter.getVmName());

		WSResult renameVMWSResult = renameVM(renameVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(renameVMWSResult.getCode())) {
			return renameVMWSResult;
		}

		// Step.4 启动VM
		PowerVMParameter powerVMParameter = new PowerVMParameter(parameter.getDatacenter(),
				PowerOperationEnum.poweron.toString(), parameter.getVmName());

		WSResult powerVMWSResult = powerVM(powerVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(powerVMWSResult.getCode())) {
			return powerVMWSResult;
		}

		return result;

	}

	public WSResult renameVM(RenameVMParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("cloneVM::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return result;
		}

		// 获得模板对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getTempVMName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "主机规格不存在,请联系系统管理员.");
				return result;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("cloneVM::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return result;
			}
		}

		try {

			Task task = vm.rename_Task(parameter.getVmName());

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("cloneNetworkDevice:: Network Device cannot be cloned");
				result.setError(WSResult.SYSTEM_ERROR, "网络设备创建失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {
			result.setError(WSResult.SYSTEM_ERROR, Remote_Error);
			return result;
		}

		return result;
	}

	public WSResult runNetworkDeviceVM(RunNetworkDeviceVMParameter parameter) {

		WSResult result = new WSResult();

		/**
		 * 
		 * Step.1 自定义CPU和Memory
		 * 
		 * Step.2 更改VM名称
		 * 
		 * Step.3 启动VM
		 * 
		 */

		// Step.1 自定义CPU和Memory
		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter(parameter.getCpuNumber(),
				parameter.getDatacenter(), parameter.getMemoryMB(), parameter.getTempVMName());

		WSResult reconfigVMWSResult = reconfigVM(reconfigVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(reconfigVMWSResult.getCode())) {
			return reconfigVMWSResult;
		}

		// Step.3 更改VM名称
		RenameVMParameter renameVMParameter = new RenameVMParameter(parameter.getDatacenter(),
				parameter.getTempVMName(), parameter.getVmName());

		WSResult renameVMWSResult = renameVM(renameVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(renameVMWSResult.getCode())) {
			return renameVMWSResult;
		}

		// Step.4 启动VM
		PowerVMParameter powerVMParameter = new PowerVMParameter(parameter.getDatacenter(),
				PowerOperationEnum.poweron.toString(), parameter.getVmName());

		WSResult powerVMWSResult = powerVM(powerVMParameter);

		if (WSResult.SYSTEM_ERROR.equals(powerVMWSResult.getCode())) {
			return powerVMWSResult;
		}

		return result;

	}

}
