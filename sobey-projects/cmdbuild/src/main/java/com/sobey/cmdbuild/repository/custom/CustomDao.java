package com.sobey.cmdbuild.repository.custom;

public interface CustomDao {

	/**
	 * 获得物理网上最大VlanId(VlanId即vCenter中创建端口组所需的VlanId)
	 * 
	 * @param nicId
	 *            物理网卡Id
	 * @return
	 */
	public Integer selectMaxVlanId(Integer nicId);

	/**
	 * 获得租户下最大的PortIndex(PortIndex和vRouter里的网络->接口 名称对应以及vCenter中vRouter上的网络适配器编号相关、)
	 * 
	 * @param tenantsId
	 * @return
	 */
	public Integer selectMaxPortIndex(Integer tenantsId);

}
