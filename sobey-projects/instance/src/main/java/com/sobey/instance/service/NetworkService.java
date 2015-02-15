package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.instance.webservice.response.dto.BindingDVSPortGroupParameter;
import com.sobey.instance.webservice.response.dto.BindingNetworkDevicePortGroupParameter;
import com.sobey.instance.webservice.response.dto.BindingPortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreatePortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreateStandardSwitchParameter;
import com.sobey.instance.webservice.response.result.WSResult;
import com.vmware.vim25.DistributedVirtualSwitchPortConnection;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualSwitchSpec;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardDistributedVirtualPortBackingInfo;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualVmxnet3;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Network Service
 * 
 * @author Administrator
 *
 */
@Service
public class NetworkService extends VMWareService {

	private static Logger logger = LoggerFactory.getLogger(NetworkService.class);

	/**
	 * 为虚拟机绑定一个基于标准交换机的虚拟端口组.
	 * 
	 * 注:默认VM只有一张网卡.
	 * 
	 * @param parameter
	 *            {@link BindingPortGroupParameter}
	 * @return
	 */
	public WSResult bindingPortGroup(BindingPortGroupParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("bindingPortGroup::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, "ServiceInstance初始化失败,请联系系统管理员.");
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

			VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
			nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();
			VirtualDevice[] vds = vmConfigInfo.getHardware().getDevice();

			for (VirtualDevice virtualDevice : vds) {

				// 此处默认一个虚拟机只有一张网卡.
				if (virtualDevice instanceof VirtualEthernetCard) {

					// 开启网卡连接
					VirtualDeviceConnectInfo connectable = new VirtualDeviceConnectInfo();
					connectable.startConnected = true;
					connectable.allowGuestControl = true;
					connectable.connected = true;
					connectable.status = "untried";

					VirtualEthernetCard nic = (VirtualEthernetCard) virtualDevice;
					nic.setConnectable(connectable);

					VirtualDeviceBackingInfo properties = nic.getBacking();
					VirtualEthernetCardNetworkBackingInfo nicBaking = (VirtualEthernetCardNetworkBackingInfo) properties;
					nicBaking.setDeviceName(parameter.getPortGroupName());

					nic.setBacking(nicBaking);

					nicSpec.setDevice(nic);

				}

			}

			VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			vmConfigSpec.setDeviceChange(nicSpecArray);

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("bindingPortGroup:: port group binding fail");
				result.setError(WSResult.SYSTEM_ERROR, "端口组绑定失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("bindingPortGroup::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "远程连接失败,请联系系统管理员.");
				return result;
			}
		}

		return result;
	}

	/**
	 * 为网络设备绑定一个基于标准交换机的虚拟端口组.<br>
	 * 
	 * 注:网络设备如vRouter默认会有10个网络适配器.其中8分配为电信,9为联通,10为默认配置给vRouter的管理端口组.
	 * 
	 * 即绑定子网只能选择1-7号的网络适配器,暂时只能绑定7个子网.
	 * 
	 * @param parameter
	 *            {@link BindingNetworkDevicePortGroupParameter}
	 * @return
	 */
	public WSResult bindingNetworkDevicePortGroup(BindingNetworkDevicePortGroupParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("bindingPortGroup::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, "ServiceInstance初始化失败,请联系系统管理员.");
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "网络设备不存在.");
				return result;
			}

			VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
			nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();
			VirtualDevice[] virtualDevices = vmConfigInfo.getHardware().getDevice();

			List<VirtualEthernetCard> virtualEthernetCards = new ArrayList<VirtualEthernetCard>();

			for (int i = 0; i < virtualDevices.length; i++) {
				if (virtualDevices[i] instanceof VirtualEthernetCard) {
					VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
					virtualEthernetCards.add(card);
				}
			}

			// 获得指定的端口.vRouter默认有10个网络适配器,其中8分配为电信,9为移动,10为默认配置给vRouter的管理端口组.
			// 即绑定子网只能选择1-7号的网络适配器,暂时只能绑定7个子网.
			VirtualEthernetCard nic = virtualEthernetCards.get(parameter.getPortIndex() - 1);

			// 将vRouter的网络适配器和需要绑定的Subnet Vlan进行关联.
			VirtualDeviceBackingInfo properties = nic.getBacking();
			VirtualEthernetCardNetworkBackingInfo nicBaking = (VirtualEthernetCardNetworkBackingInfo) properties;
			nicBaking.setDeviceName(parameter.getPortGroupName());// 指定要绑定的设配器(标准交换机端口)
			nic.setBacking(nicBaking);
			nicSpec.setDevice(nic);
			VirtualDeviceConnectInfo connectable = new VirtualDeviceConnectInfo();
			connectable.startConnected = true;
			connectable.allowGuestControl = true;
			connectable.connected = true;
			connectable.status = "untried";
			nic.setConnectable(connectable);

			VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			vmConfigSpec.setDeviceChange(nicSpecArray);

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("bindingPortGroup:: port group binding fail");
				result.setError(WSResult.SYSTEM_ERROR, "网络设备端口组绑定失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("bindingPortGroup::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "远程连接失败,请联系系统管理员.");
				return result;
			}
		}

		return result;
	}

	/**
	 * 为虚拟机绑定一个分布式端口组
	 * 
	 * @param parameter
	 *            {@link BindingNetworkDevicePortGroupParameter}
	 * @return
	 */
	public WSResult bindingDVSPortGroup(BindingDVSPortGroupParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("bindingPortGroup::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, "ServiceInstance初始化失败,请联系系统管理员.");
			return result;
		}

		// 获得VM对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, parameter.getVmName());

			if (vm == null) {
				result.setError(WSResult.SYSTEM_ERROR, "网络设备不存在.");
				return result;
			}

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();
			VirtualDevice[] virtualDevices = vmConfigInfo.getHardware().getDevice();

			List<VirtualEthernetCard> virtualEthernetCards = new ArrayList<VirtualEthernetCard>();

			for (int i = 0; i < virtualDevices.length; i++) {
				if (virtualDevices[i] instanceof VirtualEthernetCard) {
					VirtualEthernetCard card = (VirtualEthernetCard) virtualDevices[i];
					virtualEthernetCards.add(card);
				}
			}

			// 获得指定的端口.vRouter默认有10个网络适配器,其中8分配为电信,9为联通,10为默认配置给vRouter的管理端口组.
			// 即绑定子网只能选择1-7号的网络适配器,暂时只能绑定7个子网.
			VirtualEthernetCard nic = virtualEthernetCards.get(parameter.getPortIndex() - 1);

			// 获得所有分布式交换机
			ManagedEntity[] entity = new InventoryNavigator(si.getRootFolder())
					.searchManagedEntities("DistributedVirtualSwitch");

			String key = "";
			String uuid = "";
			boolean isFound = false;

			for (ManagedEntity managedEntity : entity) {

				if (managedEntity instanceof DistributedVirtualSwitch) {

					DistributedVirtualSwitch tmpDvs = (DistributedVirtualSwitch) managedEntity;
					DistributedVirtualPortgroup[] vpgs = tmpDvs.getPortgroup();

					for (DistributedVirtualPortgroup vpg : vpgs) {

						// 寻找名称相同的分布式端口组
						if (parameter.getPortGroupName().equals(vpg.getName())) {

							key = vpg.getConfig().getKey();
							uuid = tmpDvs.getConfig().getUuid();
							isFound = true;
							break;
						}
					}

					if (isFound) {
						break;
					}
				}
			}

			VirtualEthernetCard newNic = new VirtualVmxnet3();
			newNic.setKey(nic.getKey());
			newNic.setDeviceInfo(nic.getDeviceInfo());

			newNic.getDeviceInfo().setLabel(nic.getDeviceInfo().getLabel());

			VirtualEthernetCardDistributedVirtualPortBackingInfo backingInfo = new VirtualEthernetCardDistributedVirtualPortBackingInfo();

			DistributedVirtualSwitchPortConnection virtualSwitchPortConnection = new DistributedVirtualSwitchPortConnection();
			virtualSwitchPortConnection.setSwitchUuid(uuid);
			virtualSwitchPortConnection.setPortgroupKey(key);
			backingInfo.setPort(virtualSwitchPortConnection);

			newNic.setBacking(backingInfo);
			newNic.setAddressType("assigned");
			newNic.setMacAddress(nic.getMacAddress());
			newNic.setControllerKey(nic.getControllerKey());
			newNic.setUnitNumber(nic.getUnitNumber());

			VirtualDeviceConnectInfo connectable11 = new VirtualDeviceConnectInfo();
			connectable11.startConnected = true;
			connectable11.allowGuestControl = true;
			connectable11.connected = true;
			connectable11.status = "untried";

			newNic.setConnectable(connectable11);

			VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
			// 将vRouter的网络适配器添加到需要绑定的子网的vlan中
			nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

			nicSpec.setDevice(newNic);

			VirtualDeviceConfigSpec[] nicSpecArray = { nicSpec };

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			vmConfigSpec.setDeviceChange(nicSpecArray);

			Task task = vm.reconfigVM_Task(vmConfigSpec);

			if (task.waitForTask() != Task.SUCCESS) {
				logger.info("bindingPortGroup:: port group binding fail");
				result.setError(WSResult.SYSTEM_ERROR, "绑定分布式端口组失败,请联系系统管理员.");
				return result;
			}

		} catch (RemoteException | InterruptedException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("bindingPortGroup::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "远程连接失败,请联系系统管理员.");
				return result;
			}
		}

		return result;
	}

	/**
	 * 在标准交换机上创建虚拟端口组.
	 * 
	 * @param parameter
	 *            {@link CreatePortGroupParameter}
	 * @return
	 */
	public WSResult createPortGroup(CreatePortGroupParameter parameter) {

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("createPortGroup::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, "ServiceInstance初始化失败,请联系系统管理员.");
			return result;
		}

		try {

			HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
					parameter.getHostName());

			if (host == null) {
				result.setError(WSResult.SYSTEM_ERROR, "宿主机不存在.");
				return result;
			}

			HostNetworkSystem hns = host.getHostNetworkSystem();

			HostPortGroupSpec hpgs = new HostPortGroupSpec();
			hpgs.setName(parameter.getPortGroupName());
			hpgs.setVlanId(parameter.getVlanId());
			hpgs.setVswitchName(parameter.getVirtualSwitchName());
			hpgs.setPolicy(new HostNetworkPolicy());

			hns.addPortGroup(hpgs); // add port group

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("createPortGroup::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "远程连接失败,请联系系统管理员.");
				return result;
			}
		}

		return result;
	}

	/**
	 * 创建标准交换机 , 一张物理网卡对应一个标准交换机.所以一个Host可能会有多个标准交换机
	 * 
	 * @param parameter
	 *            {@link CreatePortGroupParameter}
	 * @return
	 */
	public WSResult createStandardSwitch(CreateStandardSwitchParameter parameter) {

		int numPorts = 8;// vcenter上标准交换机的端口数量

		WSResult result = new WSResult();

		ServiceInstance si;

		try {
			si = getServiceInstance(parameter.getDatacenter());
		} catch (RemoteException | MalformedURLException e) {
			logger.info("createStandardSwitch::远程连接失败或错误的URL");
			result.setError(WSResult.SYSTEM_ERROR, "ServiceInstance初始化失败,请联系系统管理员.");
			return result;
		}

		try {

			HostSystem host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem",
					parameter.getHostName());

			if (host == null) {
				result.setError(WSResult.SYSTEM_ERROR, "宿主机不存在.");
				return result;
			}

			HostVirtualSwitchSpec spec = new HostVirtualSwitchSpec();
			spec.setNumPorts(numPorts);
			HostNetworkSystem hostNetworkSystem = host.getHostNetworkSystem();
			hostNetworkSystem.addVirtualSwitch(parameter.getVirtualSwitchName(), spec);

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("createStandardSwitch::远程连接失败或错误的URL");
				result.setError(WSResult.SYSTEM_ERROR, "远程连接失败,请联系系统管理员.");
				return result;
			}
		}

		return result;
	}

}
