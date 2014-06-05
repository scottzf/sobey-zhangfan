package com.sobey.instance.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.instance.constans.DataCenterEnum;
import com.sobey.instance.service.VMService;
import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.DVSConfigSpec;
import com.vmware.vim25.DVSCreateSpec;
import com.vmware.vim25.DVSNameArrayUplinkPortPolicy;
import com.vmware.vim25.DistributedVirtualSwitchHostMemberConfigSpec;
import com.vmware.vim25.DistributedVirtualSwitchHostMemberPnicBacking;
import com.vmware.vim25.DistributedVirtualSwitchHostMemberPnicSpec;
import com.vmware.vim25.DistributedVirtualSwitchPortConnection;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.DvsFault;
import com.vmware.vim25.DvsNotAuthorized;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NotFound;
import com.vmware.vim25.PhysicalNic;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardDistributedVirtualPortBackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualVmxnet3;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

@ContextConfiguration({ "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VMNetworkTest extends TestCase {

	private static PropertiesLoader INSTANCE_LOADER = new PropertiesLoader("classpath:/instance.properties");
	private static final String INSTANCE_IP_CD = INSTANCE_LOADER.getProperty("INSTANCE_IP_CD");
	private static final String INSTANCE_USERNAME_CD = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME_CD");
	private static final String INSTANCE_PASSWORD_CD = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD_CD");
	private static final String INSTANCE_IP_XA = INSTANCE_LOADER.getProperty("INSTANCE_IP_XA");
	private static final String INSTANCE_USERNAME_XA = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME_XA");
	private static final String INSTANCE_PASSWORD_XA = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD_XA");

	@Autowired
	private VMService service;

	/**
	 * 创建分布式交换机端口组
	 */
	@Test
	public void createDVSPortGroupTest() throws RemoteException, MalformedURLException, InterruptedException {

		ServiceInstance si = getServiceInstance(DataCenterEnum.XA.toString());

		DistributedVirtualSwitch dvs = (DistributedVirtualSwitch) new InventoryNavigator(si.getRootFolder())
				.searchManagedEntity("DistributedVirtualSwitch", "DSwitch");

		if (dvs != null) {
			createDVSPortGroup(dvs, 2, "vlan2");
		}
	}

	/**
	 * 创建分布式交换机
	 */
	@Test
	public void createDVSTest() throws RemoteException, MalformedURLException, InterruptedException {

		ServiceInstance si = getServiceInstance(DataCenterEnum.XA.toString());
		Datacenter dc = (Datacenter) si.getSearchIndex().findByInventoryPath("成都测试数据中心");

		createDVS(dc);
	}

	@Test
	public void bindDVSPortGroupTest() throws InvalidProperty, RuntimeFault, RemoteException, MalformedURLException {

		changeVlan("Sobey123", "vlan100", "Network adapter 1");

	}

	public Boolean changeVlan(final String vmName, final String netName, final String nicName) {

		boolean retVal = false;

		try {

			ServiceInstance si = getServiceInstance(DataCenterEnum.XA.toString());

			Folder rootFolder = si.getRootFolder();

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity(
					"VirtualMachine", vmName);

			Network network = (Network) new InventoryNavigator(rootFolder).searchManagedEntity("Network", netName);

			if (network == null) {
				System.out.println("Could not find network " + netName);
			}

			DistributedVirtualSwitch dvs = null;

			ManagedEntity[] entity = new InventoryNavigator(rootFolder)
					.searchManagedEntities("DistributedVirtualSwitch");

			String key = "";
			DVPortgroupConfigInfo description = null;

			boolean found = false;

			for (ManagedEntity me : entity) {

				if (me instanceof DistributedVirtualSwitch) {

					DistributedVirtualSwitch tmpDvs = (DistributedVirtualSwitch) me;
					DistributedVirtualPortgroup[] vpgs = tmpDvs.getPortgroup();

					for (DistributedVirtualPortgroup vpg : vpgs) {

						if (netName.equals(vpg.getName())) {
							key = vpg.getConfig().getKey();
							description = vpg.getConfig();
							dvs = tmpDvs;
							found = true;
							break;
						}
					}

					if (found) {
						break;
					}
				}
			}

			VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();

			VirtualMachineConfigInfo vmConfigInfo = vm.getConfig();

			String uuid = dvs.getConfig().getUuid();

			ArrayList<VirtualDeviceConfigSpec> nicSpecList = new ArrayList<VirtualDeviceConfigSpec>();

			boolean nicFound = false;

			VirtualDevice[] vds = vmConfigInfo.getHardware().getDevice();
			for (VirtualDevice vd : vds) {
				if (vd instanceof VirtualEthernetCard) {

					VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
					nicSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);

					VirtualEthernetCard nic = (VirtualEthernetCard) vd;

					System.out.println("Nic: " + nic.getDeviceInfo().getLabel());

					if (nic.getDeviceInfo().getLabel().equalsIgnoreCase(nicName)) {
						System.out.println("Found nic " + nicName);

						VirtualEthernetCard newNic = new VirtualVmxnet3();
						newNic.setKey(nic.getKey());
						newNic.setDeviceInfo(nic.getDeviceInfo());

						newNic.getDeviceInfo().setLabel(nicName);

						VirtualEthernetCardDistributedVirtualPortBackingInfo backing9 = new VirtualEthernetCardDistributedVirtualPortBackingInfo();

						DistributedVirtualSwitchPortConnection port10 = new DistributedVirtualSwitchPortConnection();
						port10.setSwitchUuid(uuid);
						port10.setPortgroupKey(key);
						backing9.setPort(port10);

						newNic.setBacking(backing9);
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

						System.out.println("Setting UUID: " + uuid);
						System.out.println("Setting portgroupKey: " + key);
						System.out.println("Setting summary: " + netName);
						System.out.println("Port description: " + description.getNumPorts());

						nicSpec.setDevice(newNic);

						nicSpecList.add(nicSpec);

						nicFound = true;
					}

				}
			}

			if (!nicFound) {
				System.out.println("Could not find nic " + nicName);
			}

			VirtualDeviceConfigSpec[] configSpec = new VirtualDeviceConfigSpec[nicSpecList.size()];
			nicSpecList.toArray(configSpec);

			vmSpec.setDeviceChange(configSpec);

			Task vmTask = vm.reconfigVM_Task(vmSpec);

			String result = vmTask.waitForTask();

			retVal = result.equals(Task.SUCCESS);

		} catch (Exception exc) {
			System.out.println(exc);
		}

		return retVal;
	}

	@SuppressWarnings("unused")
	private List<String> getNetworkList(String datacenter) throws RemoteException, MalformedURLException {

		List<String> list = new ArrayList<String>();
		Datacenter dc = null;
		List<Network> networks = new ArrayList<Network>();

		Folder rootFolder = getServiceInstance(datacenter).getRootFolder();

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

			list.add(each.getName());
		}

		return list;
	}

	private static DistributedVirtualPortgroup createDVSPortGroup(DistributedVirtualSwitch dvs, Integer vlanId,
			String name) throws DvsFault, DuplicateName, InvalidName, RuntimeFault, RemoteException,
			InterruptedException {
		// create port group under this DVS
		DVPortgroupConfigSpec[] dvpgs = new DVPortgroupConfigSpec[1];
		dvpgs[0] = new DVPortgroupConfigSpec();
		dvpgs[0].setName(name);
		dvpgs[0].setNumPorts(128);
		dvpgs[0].setType("earlyBinding");
		VMwareDVSPortSetting vport = new VMwareDVSPortSetting();
		dvpgs[0].setDefaultPortConfig(vport);

		VmwareDistributedVirtualSwitchVlanIdSpec vlan = new VmwareDistributedVirtualSwitchVlanIdSpec();
		vport.setVlan(vlan);
		vlan.setInherited(false);
		vlan.setVlanId(vlanId);
		Task task_pg = dvs.addDVPortgroup_Task(dvpgs);

		TaskInfo ti = waitFor(task_pg);

		if (ti.getState() == TaskInfoState.error) {
			System.out.println("Failed to create a new DVS.");
			return null;
		}
		System.out.println("A new DVS port group has been created successfully.");
		ManagedObjectReference pgMor = (ManagedObjectReference) ti.getResult();
		DistributedVirtualPortgroup pg = (DistributedVirtualPortgroup) MorUtil.createExactManagedEntity(
				dvs.getServerConnection(), pgMor);
		return pg;
	}

	private static DistributedVirtualSwitch createDVS(Datacenter dc) throws InvalidProperty, RuntimeFault,
			RemoteException, DvsNotAuthorized, DvsFault, DuplicateName, InvalidName, NotFound, InterruptedException {
		Folder netFolder = dc.getNetworkFolder();

		DVSCreateSpec dcs = new DVSCreateSpec();
		DVSConfigSpec dcfg = new DVSConfigSpec();
		dcs.setConfigSpec(dcfg);
		dcfg.setName("DSwitch");

		DVSNameArrayUplinkPortPolicy dvsUplink = new DVSNameArrayUplinkPortPolicy();
		dvsUplink.setUplinkPortName(new String[] { "dvUplink1", "dvUplink2", "dvUplink3" });
		dcfg.setUplinkPortPolicy(dvsUplink);

		ManagedEntity[] hosts = new InventoryNavigator(dc).searchManagedEntities("HostSystem");

		DistributedVirtualSwitchHostMemberConfigSpec[] dvsHosts = new DistributedVirtualSwitchHostMemberConfigSpec[hosts.length];
		dcfg.setHost(dvsHosts);

		for (int i = 0; i < hosts.length; i++) {
			dvsHosts[i] = new DistributedVirtualSwitchHostMemberConfigSpec();
			dvsHosts[i].setOperation("add");
			dvsHosts[i].setHost(hosts[i].getMOR());

			DistributedVirtualSwitchHostMemberPnicBacking dvsPnic = new DistributedVirtualSwitchHostMemberPnicBacking();

			PhysicalNic[] pnics = ((HostSystem) hosts[i]).getConfig().getNetwork().getPnic();

			DistributedVirtualSwitchHostMemberPnicSpec[] pnicSpecs = new DistributedVirtualSwitchHostMemberPnicSpec[pnics.length];

			for (int j = 0; j < pnics.length; j++) {
				pnicSpecs[j] = new DistributedVirtualSwitchHostMemberPnicSpec();
				pnicSpecs[j].setPnicDevice(pnics[j].getDevice());
			}
			dvsPnic.setPnicSpec(pnicSpecs);
		}

		Task task = netFolder.createDVS_Task(dcs);
		TaskInfo ti = waitFor(task);

		if (ti.getState() == TaskInfoState.error) {
			System.out.println("Failed to create a new DVS.");
			return null;
		}

		System.out.println("A new DVS has been created successfully.");
		ManagedObjectReference dvsMor = (ManagedObjectReference) ti.getResult();
		DistributedVirtualSwitch dvs = (DistributedVirtualSwitch) MorUtil.createExactManagedEntity(
				dc.getServerConnection(), dvsMor);
		return dvs;
	}

	private static TaskInfo waitFor(Task task) throws RemoteException, InterruptedException {
		while (true) {
			TaskInfo ti = task.getTaskInfo();
			TaskInfoState state = ti.getState();
			if (state == TaskInfoState.success || state == TaskInfoState.error) {
				return ti;
			}
			Thread.sleep(1000);
		}
	}

	/**
	 * 初始化一个服务实例
	 * 
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	private ServiceInstance getServiceInstance(String datacenter) throws RemoteException, MalformedURLException {

		if (DataCenterEnum.CD.toString().equalsIgnoreCase(datacenter)) {
			return new ServiceInstance(new URL(INSTANCE_IP_CD), INSTANCE_USERNAME_CD, INSTANCE_PASSWORD_CD, true);
		} else if (DataCenterEnum.XA.toString().equalsIgnoreCase(datacenter)) {
			return new ServiceInstance(new URL(INSTANCE_IP_XA), INSTANCE_USERNAME_XA, INSTANCE_PASSWORD_XA, true);
		}

		return null;
	}

}
