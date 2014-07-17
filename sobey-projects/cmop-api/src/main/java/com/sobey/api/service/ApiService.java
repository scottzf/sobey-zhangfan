package com.sobey.api.service;

import com.sobey.generate.cmdbuild.TenantsDTO;

public interface ApiService {

	/**
	 * 创建租户,同时创建默认的VPN和ESG,并为租户分配一个未使用的Vlan.
	 * 
	 * @param tenantsDTO
	 */
	public void createTenants(TenantsDTO tenantsDTO);

}
