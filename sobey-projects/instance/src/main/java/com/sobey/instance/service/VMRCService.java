package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.instance.webservice.response.dto.VMRCDTO;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * VMRC Service
 * 
 * @author Administrator
 *
 */
@Service
public class VMRCService extends VMWareService {

	private static Logger logger = LoggerFactory.getLogger(VMRCService.class);

	public static final String VCENTER_IP = "10.2.7.250"; // 登陆VCENTER的IP地址

	public static final String VCENTER_USERNAME = "administrator@vsphere.local"; // 登陆VCENTER的用户名

	public static final String VCENTER_PASSWORD = "Newmed!@s0bey"; // 登陆VCENTER的密码

	public DTOResult<VMRCDTO> connectVMRC(String vmName, String datacenter) {

		DTOResult<VMRCDTO> dtoResult = new DTOResult<VMRCDTO>();

		VMRCDTO vmrcDTO = new VMRCDTO();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("connectVMRC::远程连接失败或错误的URL");
			dtoResult.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return dtoResult;
		}

		// 目前固定参数
		vmrcDTO.setHostName(VCENTER_IP); // 登陆VCENTER的IP地址
		vmrcDTO.setUserName(VCENTER_USERNAME); // 登陆VCENTER的用户名
		vmrcDTO.setPassword(VCENTER_PASSWORD); // 登陆VCENTER的密码
		vmrcDTO.setAllowSSLValidationErrors(true); // 是否允许SSL验证错误（默认为是）

		String sslThumbprintStr = null;
		try {
			sslThumbprintStr = si.getSessionManager().acquireCloneTicket();
		} catch (RemoteException e) {
			dtoResult.setError(WSResult.SYSTEM_ERROR, "sslThumbprintStr获取失败,请联系系统管理员.");
			return dtoResult;
		}

		String hostSSLThumbprint = StringUtils.substringAfter(sslThumbprintStr, "tp-"); // 主机认证证书指纹
		vmrcDTO.setHostSSLThumbprint(hostSSLThumbprint);

		if (sslThumbprintStr == null) {
			dtoResult.setError(WSResult.SYSTEM_ERROR, "sslThumbprintStr不存在,请联系系统管理员.");
			return dtoResult;
		}

		// 获得模板对象
		VirtualMachine vm = null;

		try {

			vm = getVirtualMachine(si, vmName);

			if (vm == null) {
				dtoResult.setError(WSResult.SYSTEM_ERROR, "主机不存在,请联系系统管理员.");
				return dtoResult;
			}
		} catch (RemoteException e) {

			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("connectVMRC::远程连接失败或错误的URL");
				dtoResult.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return dtoResult;
			}
		}

		String vmId = vm.getMOR().getVal();
		vmrcDTO.setVmId(vmId);

		dtoResult.setDto(vmrcDTO);

		return dtoResult;
	}

}
