package com.sobey.instance.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sobey.core.utils.MathsUtil;
import com.sobey.instance.webservice.response.dto.HostInfoDTO;
import com.sobey.instance.webservice.response.result.DTOListResult;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

/**
 * Host Service
 * 
 * @author Administrator
 *
 */
@Service
public class HostService extends VMWareService {

	private static Logger logger = LoggerFactory.getLogger(HostService.class);

	/**
	 * 根据宿主机名称获得宿主机的具体信息
	 * 
	 * @param hostName
	 *            宿主机名称
	 * @param datacenter
	 *            数据中心
	 * @return
	 */
	public DTOResult<HostInfoDTO> findHostInfoDTO(String hostName, String datacenter) {

		DTOResult<HostInfoDTO> dtoResult = new DTOResult<HostInfoDTO>();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("findHostInfoDTO::远程连接失败或错误的URL");
			dtoResult.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return dtoResult;
		}

		HostSystem host;

		try {

			host = (HostSystem) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("HostSystem", hostName);

			dtoResult.setDto(wrapHostInfoDTO(host));

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("findHostInfoDTO::远程连接失败或错误的URL");
				dtoResult.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return dtoResult;
			}
		}

		return dtoResult;
	}

	/**
	 * 获得数据中心所有的宿主机列表
	 * 
	 * @param datacenter
	 *            数据中心
	 * @return
	 */
	public DTOListResult<HostInfoDTO> getHostInfoDTOs(String datacenter) {

		DTOListResult<HostInfoDTO> dtoListResult = new DTOListResult<HostInfoDTO>();

		ArrayList<HostInfoDTO> list = new ArrayList<HostInfoDTO>();

		ServiceInstance si;

		try {
			si = getServiceInstance(datacenter);
		} catch (RemoteException | MalformedURLException e) {
			logger.info("getHostInfoDTOs::远程连接失败或错误的URL");
			dtoListResult.setError(WSResult.SYSTEM_ERROR, ServiceInstance_Init_Error);
			return dtoListResult;
		}

		ManagedEntity[] entities;

		try {

			entities = new InventoryNavigator(si.getRootFolder()).searchManagedEntities("HostSystem");

			for (ManagedEntity managedEntity : entities) {
				HostSystem host = (HostSystem) managedEntity;
				list.add(wrapHostInfoDTO(host));
			}

		} catch (RemoteException e) {
			try {
				logout(si);
			} catch (RemoteException | MalformedURLException ex) {
				logger.info("getHostInfoDTOs::远程连接失败或错误的URL");
				dtoListResult.setError(WSResult.SYSTEM_ERROR, Remote_Error);
				return dtoListResult;
			}
		}

		dtoListResult.setDtos(list);

		return dtoListResult;
	}

	/**
	 * HostSystem -> HostInfoDTO
	 * 
	 * @param host
	 * @return
	 */
	private HostInfoDTO wrapHostInfoDTO(HostSystem host) {

		ComputeResource computeResource = (ComputeResource) host.getParent();

		HostInfoDTO hostInfoDTO = new HostInfoDTO();

		// 频率由HZ -> GHZ = HZ/1024*1024*1024
		hostInfoDTO.setCpuHz(String.valueOf(MathsUtil.div(Double.valueOf(host.getHardware().getCpuInfo().getHz()),
				1073741824)));

		// 转换成MB 1024*1024 = 1048576
		hostInfoDTO.setMemoryMB(String.valueOf(MathsUtil.div(host.getHardware().getMemorySize(), 1048576)));

		hostInfoDTO.setCpuNumber(String.valueOf(host.getHardware().getCpuInfo().getNumCpuCores()));
		hostInfoDTO.setModel(host.getHardware().getSystemInfo().getModel());
		hostInfoDTO.setHostName(host.getName());
		hostInfoDTO.setResourcePool(computeResource.getResourcePool().getMOR().getVal());
		hostInfoDTO.setVendor(host.getHardware().getSystemInfo().getVendor());

		return hostInfoDTO;
	}

}
