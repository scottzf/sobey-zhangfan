package com.sobey.sdn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.sdn.bean.Fengjing;
import com.sobey.sdn.service.impl.SDNServiceImpl;
import com.sobey.sdn.util.VcenterUtil;
import com.vmware.vim25.GuestNicInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

@Service(value = "fengjingTemp")
public class FengjingTemp {

	@Autowired
	private SDNServiceImpl sdnService;

	/**
	 * ps:方法有点绕 因为无法从虚拟机上直接获取主机IP
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Fengjing> getInfo() throws Exception {

		List<String> vms = sdnService.queryVmsInFolder("Tenement_5000");

		List<Fengjing> fengjings = new ArrayList<Fengjing>();

		ServiceInstance si = VcenterUtil.getServiceInstance();
		Folder rootFolder = si.getRootFolder();

		List<String> hosts = HostRelationMap.hostList;
		
		for (String hostIp : hosts) {

			HostSystem host = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem", hostIp);

			VirtualMachine[] vmsOnhost = host.getVms();

			if (vmsOnhost != null && vmsOnhost.length > 0) {

				for (int i = 0; i < vmsOnhost.length; i++) {

					if (vms.contains(vmsOnhost[i].getName())) {

						Fengjing fengjing = new Fengjing();
						VirtualMachine vm = (VirtualMachine) vmsOnhost[i];
						fengjing.setVmName(vm.getName());
						System.out.println(vm.getName());
						fengjing.setIp(vm.getGuest().getIpAddress());
						System.out.println(vm.getGuest().getIpAddress());
						fengjing.setHostIp(hostIp);
						System.out.println(hostIp);

						GuestNicInfo[] guestNicInfos = vm.getGuest().getNet();

						if (guestNicInfos != null && guestNicInfos.length > 0) {

							System.out.println(guestNicInfos.length);

							for (int j = 0; j < guestNicInfos.length; j++) {

								System.out.println(guestNicInfos[j].getNetwork());

								if (guestNicInfos[j].getNetwork().contains("TN_")) {
									fengjing.setVlan(guestNicInfos[j].getNetwork());
								}
							}
						}
						fengjings.add(fengjing);
						System.out.println("---------------------------------------");
					}
				}
			}
		}

		return fengjings;
	}
}
