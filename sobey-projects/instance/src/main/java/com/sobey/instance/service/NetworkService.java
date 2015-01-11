package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.instance.webservice.response.dto.BindingPortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreatePortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreateStandardSwitchParameter;
import com.sobey.instance.webservice.response.result.WSResult;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.HostVirtualSwitchSpec;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.mo.HostNetworkSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
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
