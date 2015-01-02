package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.instance.constans.DataCenterEnum;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class VMWareService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	private static PropertiesLoader INSTANCE_LOADER = new PropertiesLoader("classpath:/instance.properties");

	/********** 成都 ***********/

	/**
	 * IP
	 */
	private static final String INSTANCE_IP_CD = INSTANCE_LOADER.getProperty("INSTANCE_IP_CD");

	/**
	 * Username
	 */
	private static final String INSTANCE_USERNAME_CD = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME_CD");

	/**
	 * password
	 */
	private static final String INSTANCE_PASSWORD_CD = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD_CD");

	/********** 西安 ***********/
	/**
	 * IP
	 */
	private static final String INSTANCE_IP_XA = INSTANCE_LOADER.getProperty("INSTANCE_IP_XA");

	/**
	 * password
	 */
	private static final String INSTANCE_PASSWORD_XA = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD_XA");

	/**
	 * Username
	 */
	private static final String INSTANCE_USERNAME_XA = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME_XA");

	/********** 西安2 ***********/
	/**
	 * IP
	 */
	private static final String INSTANCE_IP_XA2 = INSTANCE_LOADER.getProperty("INSTANCE_IP_XA2");

	/**
	 * Username
	 */
	private static final String INSTANCE_USERNAME_XA2 = INSTANCE_LOADER.getProperty("INSTANCE_USERNAME_XA2");

	/**
	 * password
	 */
	private static final String INSTANCE_PASSWORD_XA2 = INSTANCE_LOADER.getProperty("INSTANCE_PASSWORD_XA2");

	protected static final String Remote_Error = "远程连接失败,请联系系统管理员.";

	protected static final String ServiceInstance_Init_Error = "ServiceInstance初始化失败,请联系系统管理员.";

	/**
	 * windows模板默认密码.
	 */
	protected static final String WINDOWS_DEFAULT_PASSWORD = INSTANCE_LOADER.getProperty("WINDOWS_DEFAULT_PASSWORD");

	/**
	 * 根据数据中心,初始化一个服务实例
	 * 
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	protected ServiceInstance getServiceInstance(String datacenter) throws RemoteException, MalformedURLException {

		ServiceInstance serviceInstance = null;

		switch (DataCenterEnum.valueOf(datacenter)) {

		case 西安核心数据中心:

			serviceInstance = new ServiceInstance(new URL(INSTANCE_IP_XA), INSTANCE_USERNAME_XA, INSTANCE_PASSWORD_XA,
					true);
			break;

		case 西安核心数据中心2:

			serviceInstance = new ServiceInstance(new URL(INSTANCE_IP_XA2), INSTANCE_USERNAME_XA2,
					INSTANCE_PASSWORD_XA2, true);
			break;

		case 成都核心数据中心:

			serviceInstance = new ServiceInstance(new URL(INSTANCE_IP_CD), INSTANCE_USERNAME_CD, INSTANCE_PASSWORD_CD,
					true);
			break;

		default:
			break;
		}

		return serviceInstance;
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
	protected VirtualMachine getVirtualMachine(ServiceInstance si, String vmname) throws InvalidProperty, RuntimeFault,
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
	protected void logout(ServiceInstance si) throws RemoteException, MalformedURLException {
		si.getServerConnection().logout();
	}

}
