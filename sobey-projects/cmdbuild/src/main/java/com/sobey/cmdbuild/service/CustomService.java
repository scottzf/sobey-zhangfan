package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.repository.custom.CustomDao;
import com.sobey.core.utils.MathsUtil;

/**
 * 自定义查询的service类.
 */
@Service
@Transactional
public class CustomService {

	@Autowired
	private CustomDao customDao;

	/**
	 * 
	 * 获得物理网上最大VlanId(VlanId即vCenter中创建端口组所需的VlanId)
	 * 
	 * 根据nicId获得数据库中Vlan的最大值,并加上1.没有数据,则初始数据为 29+1 =30
	 * 
	 * 
	 * @param nicId
	 * @return
	 */
	public Integer selectMaxVlanId(Integer nicId) {
		Integer vlanId = customDao.selectMaxVlanId(nicId);
		return (int) MathsUtil.add(vlanId == null ? 29 : vlanId, 1);
	}

	/**
	 * 获得租户下最大的PortIndex(PortIndex和vRouter里的网络->接口 名称对应以及vCenter中vRouter上的网络适配器编号相关、)
	 * 
	 * @param tenantsId
	 * @return
	 */
	public Integer selectPortIndex(Integer tenantsId) {
		Integer portIndex = customDao.selectMaxPortIndex(tenantsId);
		return (int) MathsUtil.add(portIndex == null ? 0 : portIndex, 1);
	}

}