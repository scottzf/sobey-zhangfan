package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
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
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.CustomizationSpecInfo;
import com.vmware.vim25.CustomizationSpecItem;
import com.vmware.vim25.CustomizationSysprep;
import com.vmware.vim25.CustomizationSysprepRebootOption;
import com.vmware.vim25.CustomizationUserData;
import com.vmware.vim25.CustomizationWinOptions;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.CustomizationSpecManager;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@Service
public class VMService {

	private static Logger logger = LoggerFactory.getLogger(VMService.class);

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader INSTANCE_LOADER = new PropertiesLoader("classpath:/instance.properties");

	/* 脚本参数 */

	/**
	 * IP
	 */
	private static final String INSTANCE_IP = INSTANCE_LOADER.getProperty("INSTANCE_IP");

	/**
	 * Username
	 */
	private static final String INSTANCE_USERNAME = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME");

	/**
	 * password
	 */
	private static final String INSTANCE_PASSWORD = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD");

	/**
	 * 为虚拟机设置网络适配器相关信息:设备状态设置为已连接、网络标签、虚拟机的备注.
	 * 
	 * @param vm
	 * @param networkName
	 * @param annotation
	 */
	private void setVirtualMachineNetwork(VirtualMachine vm, String networkName, String annotation) {

		ArrayList<VirtualEthernetCard> nics = new ArrayList<VirtualEthernetCard>();

		try {

			VirtualHardware hardware = vm.getConfig().hardware;
			VirtualDevice[] devices = hardware.getDevice();

			for (VirtualDevice each : devices) {
				if (each instanceof VirtualEthernetCard) {
					nics.add((VirtualEthernetCard) each);
				}
			}

			for (VirtualEthernetCard each : nics) {

				// 设备状态设置为:已连接
				VirtualDeviceConnectInfo connectable = each.getConnectable();
				connectable.setConnected(true);
				each.setConnectable(connectable);

				VirtualEthernetCardNetworkBackingInfo backing = new VirtualEthernetCardNetworkBackingInfo();

				// 根据网络适配器名字找到该网络对象
				Network network = getNetworkFromString(networkName);

				if (network != null) {

					// 设置网络链接->网络标签
					backing.setNetwork(network.getMOR());
					backing.setDeviceName(network.getName());
					each.setBacking(backing);

					VirtualDeviceConfigSpec vdcs = new VirtualDeviceConfigSpec();

					vdcs.setOperation(VirtualDeviceConfigSpecOperation.edit);
					vdcs.setDevice(each);

					VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

					vmConfigSpec.setDeviceChange(new VirtualDeviceConfigSpec[] { vdcs });

					// 为VM添加注释
					vmConfigSpec.setAnnotation(annotation);

					Task changeNetwork = vm.reconfigVM_Task(vmConfigSpec);
					if (changeNetwork.waitForTask() != Task.SUCCESS) {
						logger.info(vm.getName() + " could not be changed to \"" + network.getName() + "\"");
					}

				} else {
					logger.info("Error in changing network label. network 不存在");
				}
			}

		}

		catch (Exception e) {
			logger.info("setVirtualMachineNetwork::Exception:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据名称获得数据中的网络设备对象.
	 * 
	 * @param name
	 *            网络设备器名称
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	private Network getNetworkFromString(String name) throws RemoteException, MalformedURLException {

		Network network = null;
		Datacenter dc = null;
		List<Network> networks = new ArrayList<Network>();

		Folder rootFolder = getServiceInstance().getRootFolder();

		ManagedEntity[] datacenters = rootFolder.getChildEntity();

		// 获得数据中心
		for (int i = 0; i < datacenters.length; i++) {
			if (datacenters[i] instanceof Datacenter) {
				dc = (Datacenter) datacenters[i];
				break;
			}
		}

		// 获得网络
		for (ManagedEntity entity : dc.getNetworkFolder().getChildEntity()) {
			if (entity instanceof Network) {
				networks.add(((Network) entity));
			}
		}

		for (Network each : networks) {
			if (each.getName().equals(name)) {
				network = each;
			}
		}

		return network;
	}

	public boolean cloneVM(CloneVMParameter parameter) {

		try {

			ServiceInstance si = getServiceInstance();

			VirtualMachine vm = getVirtualMachine(si, parameter.getvMTemplateName());

			if (vm == null) {
				logout(si);
				return false;
			}

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

			info.setDescription(parameter.getvMTemplateOS());
			info.setName("Sobey");
			info.setType(parameter.getvMTemplateOS());// 设置克隆机器的操作系统类型

			specItem.setInfo(info);
			specItem.setSpec(cspec);

			// dns列表
			String dnsList[] = new String[] { "8.8.8.8" };
			String ipAddress = parameter.getIpaddress(); // 自定义的内网IP
			String subNetMask = parameter.getSubNetMask();

			adapter.setDnsServerList(dnsList);
			adapter.setGateway(new String[] { parameter.getGateway() });
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

			if ("Linux".equals(parameter.getvMTemplateOS())) {

				CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
				CustomizationLinuxPrep cLinuxPrep = new CustomizationLinuxPrep();
				cLinuxPrep.setDomain("sobeyc.com");
				cLinuxPrep.setHostName(computerName);
				cLinuxPrep.setHwClockUTC(true);
				cLinuxPrep.setTimeZone("Asia/Shanghai");

				cspec.setOptions(linuxOptions);
				cspec.setIdentity(cLinuxPrep);

			} else if ("Windows".equals(parameter.getvMTemplateOS())) {

				CustomizationWinOptions winOptions = new CustomizationWinOptions();
				CustomizationSysprep cWinSysprep = new CustomizationSysprep();

				CustomizationGuiUnattended guiUnattended = new CustomizationGuiUnattended();
				guiUnattended.setAutoLogon(false);
				guiUnattended.setAutoLogonCount(1);
				guiUnattended.setTimeZone(235);
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

			} else {
				return false;
			}

			cspec.setGlobalIPSettings(gIP);
			cspec.setNicSettingMap(nicSettingMap);
			cspec.setEncryptionKey(specManager.getEncryptionKey());

			// 设置ResourcePool
			/**
			 * 重要:宿主机暂时写死.宿主机的Value可以在VMTest中的PrintInventory方法查出来.
			 */
			ManagedObjectReference pool = new ManagedObjectReference();
			pool.set_value("resgroup-8");
			pool.setType("ResourcePool");
			pool.setVal("resgroup-8");

			VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
			relocateSpec.setPool(pool);
			cloneSpec.setLocation(relocateSpec);
			cloneSpec.setPowerOn(true);
			cloneSpec.setTemplate(false);
			cloneSpec.setCustomization(cspec);

			vm.checkCustomizationSpec(specItem.getSpec());

			Task task = vm.cloneVM_Task((Folder) vm.getParent(), parameter.getvMName(), cloneSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("Failure -: VM cannot be cloned");
				return false;
			}

			// 为虚拟机设置网络适配器相关信息:设备状态设置为已连接、网络标签、虚拟机的备注.
			setVirtualMachineNetwork(getVirtualMachine(si, parameter.getvMName()), "VM Network",
					parameter.getDescription());

		} catch (InvalidProperty e) {
			logger.info("cloneVM::InvalidProperty:" + e);
			return false;
		} catch (RuntimeFault e) {
			logger.info("cloneVM::RuntimeFault:" + e);
			return false;
		} catch (RemoteException e) {
			logger.info("cloneVM::RemoteException:" + e);
			return false;
		} catch (MalformedURLException e) {
			logger.info("cloneVM::MalformedURLException:" + e);
			return false;
		} catch (InterruptedException e) {
			logger.info("cloneVM::InterruptedException:" + e);
			return false;
		}

		return true;
	}

	/**
	 * 销毁一个虚拟机
	 * 
	 * @param parameter
	 *            {@link DestroyVMParameter}
	 * @return
	 */
	public boolean destroyVM(DestroyVMParameter parameter) {

		try {

			ServiceInstance si = getServiceInstance();

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(
					"VirtualMachine", parameter.getvMName());

			if (vm == null) {
				logout(si);
				return false;
			}

			vm.destroy_Task();

		} catch (RemoteException e) {
			logger.info("destroyVM::RemoteException:" + e);
			return false;
		} catch (MalformedURLException e) {
			logger.info("destroyVM::MalformedURLException:" + e);
			return false;
		}

		return true;
	}

	public boolean reconfigVM(ReconfigVMParameter parameter) {

		try {

			ServiceInstance si = getServiceInstance();
			VirtualMachine vm = getVirtualMachine(si, parameter.getvMName());

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

			vmConfigSpec.setMemoryMB(parameter.getMemoryMB()); // in MB
			vmConfigSpec.setNumCPUs(parameter.getcPUNumber());

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("Failure -: VM cannot be cloned");
				return false;
			}

		} catch (RemoteException e) {
			logger.info("reconfigVM::RemoteException:" + e);
			return false;
		} catch (MalformedURLException e) {
			logger.info("reconfigVM::MalformedURLException:" + e);
			return false;
		} catch (InterruptedException e) {
			logger.info("reconfigVM::InterruptedException:" + e);
			return false;
		}

		return true;

	}

	public boolean powerVM(PowerVMParameter parameter) {

		try {

			ServiceInstance si = getServiceInstance();
			VirtualMachine vm = getVirtualMachine(si, parameter.getvMName());

			if (vm == null) {
				logout(si);
				return false;
			}

			if ("reboot".equalsIgnoreCase(parameter.getPowerOperation())) {
				vm.rebootGuest();
			} else if ("poweron".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.powerOnVM_Task(null);
				if (task.waitForTask() != Task.SUCCESS) {
					return false;
				}

			} else if ("poweroff".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.powerOffVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					return false;
				}

			} else if ("reset".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.resetVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					return false;
				}

			} else if ("standby".equalsIgnoreCase(parameter.getPowerOperation())) {

				vm.standbyGuest();

			} else if ("suspend".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.suspendVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					return false;
				}

			} else if ("shutdown".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.suspendVM_Task();
				if (task.waitForTask() != Task.SUCCESS) {
					return false;
				}

			} else {
				return false;
			}

			logout(si);

		} catch (RemoteException e) {
			logger.info("powerVM::RemoteException:" + e);
			return false;
		} catch (MalformedURLException e) {
			logger.info("powerVM::MalformedURLException:" + e);
			return false;
		} catch (InterruptedException e) {
			logger.info("powerVM::InterruptedException:" + e);
			return false;
		}

		return true;

	}

	/**
	 * 通过HashMap获得Host和VM的关联关系.<br>
	 * 
	 * 利用HashMap key不能重复,value可以重复的特性,将VM名保存在key中,而Host名因为有重复的存在,所以放在value.<br>
	 * 这样保证了每个key(VM)对应一个Value(Host),key永远不会重复
	 * 
	 * @return
	 */
	public RelationVMParameter getVMAndHostRelation() {

		HashMap<String, String> map = Maps.newHashMap();

		try {

			ServiceInstance si = getServiceInstance();

			ManagedEntity[] mes = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("VirtualMachine");
			if (mes == null || mes.length == 0) {
				return null;
			}

			for (int i = 0; i < mes.length; i++) {

				VirtualMachine vm = (VirtualMachine) mes[i];

				ResourcePool pool = vm.getResourcePool();

				if (pool != null) {
					map.put(vm.getName(), pool.getParent().getName());
				}

			}

		} catch (RemoteException e) {
			logger.info("RelationVMParameter::RemoteException:" + e);
		} catch (MalformedURLException e) {
			logger.info("RelationVMParameter::MalformedURLException:" + e);
		}

		RelationVMParameter parameter = new RelationVMParameter();
		parameter.setRelationMaps(map);
		return parameter;
	}

	/**
	 * 初始化一个服务实例
	 * 
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	private ServiceInstance getServiceInstance() throws RemoteException, MalformedURLException {
		return new ServiceInstance(new URL(INSTANCE_IP), INSTANCE_USERNAME, INSTANCE_PASSWORD, true);
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
	 * 退出服务实例
	 * 
	 * @param si
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	private void logout(ServiceInstance si) throws RemoteException, MalformedURLException {
		si.getServerConnection().logout();
	}

}
