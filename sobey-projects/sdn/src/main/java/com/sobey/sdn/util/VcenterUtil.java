package com.sobey.sdn.util;

import java.net.URL;

import com.vmware.vim25.mo.ServiceInstance;

public class VcenterUtil {
    /**
     * 获得登陆vcenter的实例
     * @return
     * @throws Exception
     */
	public static ServiceInstance getServiceInstance() throws Exception {
		URL url = new URL(SDNPropertiesUtil.getProperty("VCENTER_IP_CD"));
		String userName = SDNPropertiesUtil.getProperty("VCENTER_USERNAME_CD");
		String password = SDNPropertiesUtil.getProperty("VCENTER_PASSWORD_CD");
		ServiceInstance si = new ServiceInstance(url, userName, password, true);
		/*URL url = new URL("https://172.16.3.241/sdk");
		String userName = "VSPHERE.LOCAL\\zhangfan";
		String password = "gwgxhULJerjVkvCeJcxA";
		ServiceInstance si = new ServiceInstance(url, userName, password, true);*/
		return si;
	}
}
