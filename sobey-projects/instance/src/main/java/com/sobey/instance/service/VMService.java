package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;

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
import com.vmware.vim25.CustomizationIPSettings;
import com.vmware.vim25.CustomizationLinuxOptions;
import com.vmware.vim25.CustomizationLinuxPrep;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.CustomizationSpecInfo;
import com.vmware.vim25.CustomizationSpecItem;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.CustomizationSpecManager;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

@Service
public class VMService {

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

	@SuppressWarnings("deprecation")
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
			String dnsList[] = new String[] { "10.193.16.105", "8.8.8.8" };
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
			CustomizationLinuxOptions linuxOptions = new CustomizationLinuxOptions();
			CustomizationLinuxPrep cLinuxPrep = new CustomizationLinuxPrep();
			cLinuxPrep.setDomain("sobeyc.com");
			cLinuxPrep.setHostName(computerName);
			cLinuxPrep.setHwClockUTC(true);
			cLinuxPrep.setTimeZone("Asia/Shanghai");

			CustomizationSpecManager specManager = si.getCustomizationSpecManager();

			cspec.setGlobalIPSettings(gIP);
			cspec.setOptions(linuxOptions);
			cspec.setIdentity(cLinuxPrep);
			cspec.setNicSettingMap(nicSettingMap);
			cspec.setEncryptionKey(specManager.getEncryptionKey());

			// 设置ResourcePool
			// pool为10.10.1.40的宿主机.暂时写死
			ManagedObjectReference pool = new ManagedObjectReference();
			pool.set_value("resgroup-18");
			pool.setType("ResourcePool");
			pool.setVal("resgroup-18");

			VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
			relocateSpec.setPool(pool);
			cloneSpec.setLocation(relocateSpec);
			cloneSpec.setPowerOn(true);
			cloneSpec.setTemplate(false);
			cloneSpec.setCustomization(cspec);

			vm.checkCustomizationSpec(specItem.getSpec());

			Task task = vm.cloneVM_Task((Folder) vm.getParent(), parameter.getvMName(), cloneSpec);

			String status = task.waitForMe();
			if (status != Task.SUCCESS) {
				System.out.println("Failure -: VM cannot be cloned");
				return false;
			}

			// 为VM添加注释
			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			vmConfigSpec.setAnnotation(parameter.getDescription());
			vm.reconfigVM_Task(vmConfigSpec);

		} catch (InvalidProperty e) {
			return false;
		} catch (RuntimeFault e) {
			return false;
		} catch (RemoteException e) {
			return false;
		} catch (MalformedURLException e) {
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
			return false;
		} catch (MalformedURLException e) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean reconfigVM(ReconfigVMParameter parameter) {

		try {

			ServiceInstance si = getServiceInstance();
			VirtualMachine vm = getVirtualMachine(si, parameter.getvMName());

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

			vmConfigSpec.setMemoryMB(parameter.getMemoryMB()); // in MB
			vmConfigSpec.setNumCPUs(parameter.getcPUNumber());

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			String status = task.waitForMe();
			if (status != Task.SUCCESS) {
				System.out.println("Failure -: VM cannot be cloned");
				return false;
			}

		} catch (RemoteException e) {
			return false;
		} catch (MalformedURLException e) {
			return false;
		}

		return true;

	}

	@SuppressWarnings("deprecation")
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
				if (task.waitForMe() != Task.SUCCESS) {
					return false;
				}

			} else if ("poweroff".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.powerOffVM_Task();
				if (task.waitForMe() != Task.SUCCESS) {
					return false;
				}

			} else if ("reset".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.resetVM_Task();
				if (task.waitForMe() != Task.SUCCESS) {
					return false;
				}

			} else if ("standby".equalsIgnoreCase(parameter.getPowerOperation())) {

				vm.standbyGuest();

			} else if ("suspend".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.suspendVM_Task();
				if (task.waitForMe() != Task.SUCCESS) {
					return false;
				}

			} else if ("shutdown".equalsIgnoreCase(parameter.getPowerOperation())) {

				Task task = vm.suspendVM_Task();
				if (task.waitForMe() != Task.SUCCESS) {
					return false;
				}

			} else {
				return false;
			}

			logout(si);

		} catch (RemoteException e) {
			return false;
		} catch (MalformedURLException e) {
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
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
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
