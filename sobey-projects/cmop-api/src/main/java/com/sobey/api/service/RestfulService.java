package com.sobey.api.service;

import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.FirewallServiceEntity;
import com.sobey.api.entity.RouterEntity;
import com.sobey.api.entity.SubnetEntity;
import com.sobey.api.entity.TenantsEntity;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;

public interface RestfulService {

	/******** Tenants ********/

	public DTOResult<TenantsEntity> findTenants(String accessKey);

	public WSResult createTenants(String company, String name, String email, String password, String phone);

	/******** Subnet ********/

	public DTOResult<SubnetEntity> findSubnet(String code, String accessKey);

	public WSResult createSubnet(String subnetName, String segment, String gateway, String netmask, String idc,
			String accessKey);

	/******** ECS ********/
	public DTOResult<EcsEntity> findECS(String code, String accessKey);

	public WSResult createECS(String ecsName, String subnetCode, String remark, String ecsSpec, String idc,
			String accessKey);

	public WSResult destroyECS(String code, String accessKey);

	public WSResult powerOpsECS(String code, String powerOperation, String accessKey);

	public WSResult reconfigECS(String code, String ecsSpec, String accessKey);

	/******** ES3 ********/

	public DTOResult<Es3Entity> findES3(String code, String accessKey);

	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String ecsCode,
			String remark, String accessKey);

	public WSResult deleteES3(String code, String accessKey);

	/******** DNS ********/
	public DTOResult<DnsEntity> findDNS(String code, String accessKey);

	public WSResult createDNS(String domainName, String eipCodes, String protocols, String idc, String remark,
			String accessKey);

	public WSResult deleteDNS(String code, String accessKey);

	/******** Router ********/
	public DTOResult<RouterEntity> findRouter(String code, String accessKey);

	public WSResult createRouter(String routerName, String subnetCode, String remark, String ecsSpec, String idc,
			String accessKey);

	/******** FirewallService ********/
	public DTOResult<FirewallServiceEntity> findFirewallService(String code, String accessKey);

	public WSResult createFirewallService(String firewallServiceName, String directions, String rulesNames,
			String protocols, String actions, String startPorts, String endPorts, String ipaddresses, String idc,
			String accessKey);

}
