package com.sobey.api.service;

import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.storage.CreateEs3Parameter;

public interface ApiService {

	/**
	 * 创建租户,同时创建默认的VPN和ESG,并为租户分配一个未使用的Vlan.
	 * 
	 * @param tenantsDTO
	 */
	public void createTenants(TenantsDTO tenantsDTO);

	/**
	 * 创建ECS
	 * 
	 * @param tenantsId
	 * @param cloneVMParameter
	 */
	public void createECS(Integer tenantsId, CloneVMParameter cloneVMParameter);

	/**
	 * 销毁ECS
	 * 
	 * @param ecsId
	 */
	public void destroyECS(Integer ecsId);

	/**
	 * 针对ECS的电源操作
	 * 
	 * @param ecsId
	 * @param powerOperation
	 */
	public void powerOpsECS(Integer ecsId, String powerOperation);

	/**
	 * 修改ECS的配置
	 * 
	 * @param ecsId
	 * @param ecsSpecId
	 */
	public void reconfigECS(Integer ecsId, Integer ecsSpecId);

	/**
	 * 同步ECS
	 * 
	 * @param datacenter
	 * @return
	 */
	public String syncVM(String datacenter);

	public void createES3(Integer tenantsId, CreateEs3Parameter createEs3Parameter);

	public void attachES3(Integer es3Id, Integer ecsId);

	public void detachES3(Integer es3Id, Integer ecsId);

	public void deleteES3(Integer es3Id);

}
