package com.sobey.api.service;

import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.CloneVMParameter;

public interface ApiService {

	/**
	 * 创建租户,同时创建默认的VPN和ESG,并为租户分配一个未使用的Vlan.
	 * 
	 * @param tenantsDTO
	 */
	public void createTenants(TenantsDTO tenantsDTO);

	public void createECS(Integer tenantsId, CloneVMParameter cloneVMParameter);

	public void destroyECS(Integer ecsId);

	public void powerOpsECS(Integer ecsId, String powerOperation);

	public void reconfigECS(Integer ecsId, Integer ecsSpecId);

	public String syncVM(String datacenter);

}
