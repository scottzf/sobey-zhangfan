package com.sobey.api.service;

import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.EipEntity;
import com.sobey.api.entity.ElbEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.FirewallServiceEntity;
import com.sobey.api.entity.RouterEntity;
import com.sobey.api.entity.SubnetEntity;
import com.sobey.api.entity.TenantsEntity;
import com.sobey.api.entity.VMRCEntity;
import com.sobey.api.webservice.response.result.DTOListResult;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.zabbix.ZItemDTO;

public interface RestfulService {

	/******** Tenants ********/

	public DTOResult<TenantsEntity> findTenants(String accessKey);

	public WSResult createTenants(String company, String name, String email, String phone);

	/******** Subnet ********/

	public DTOResult<SubnetEntity> findSubnet(String code, String accessKey);

	public WSResult createSubnet(String subnetName, String segment, String gateway, String netmask, String idc,
			String accessKey);

	/******** ECS ********/
	public DTOResult<EcsEntity> findECS(String code, String accessKey);

	public WSResult createECS(String ecsName, String subnetCode, String remark, String imageName, String cpuNumber,
			String memoryMB, String idc, String accessKey);

	public WSResult destroyECS(String code, String accessKey);

	public WSResult powerOpsECS(String code, String powerOperation, String accessKey);

	/******** ES3 ********/

	public DTOResult<Es3Entity> findES3(String code, String accessKey);

	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String remark,
			String accessKey);

	public WSResult bindingES3(String es3Code, String ecsCode, String accessKey);

	public WSResult deleteES3(String code, String accessKey);

	/******** DNS ********/
	public DTOResult<DnsEntity> findDNS(String code, String accessKey);

	public WSResult createDNS(String domainName, String eipCodes, String protocols, String idc, String remark,
			String accessKey);

	public WSResult deleteDNS(String code, String accessKey);

	/******** Router ********/
	public DTOResult<RouterEntity> findRouter(String code, String accessKey);

	public WSResult createRouter(String routerName, String remark, String imageName, String cpuNumber, String memoryMB,
			String idc, String firewallServiceCode, String accessKey);

	public WSResult bindingRouter(String routerCode, String subnetCodes, String accessKey);

	/******** FirewallService ********/
	public DTOResult<FirewallServiceEntity> findFirewallService(String code, String accessKey);

	public WSResult createFirewallService(String firewallServiceName, String directions, String rulesNames,
			String protocols, String actions, String startPorts, String endPorts, String ipaddresses, String idc,
			String accessKey);

	WSResult bindingFirewallService(String routerCode, String firewallServiceCode, String accessKey);

	/******** EIP ********/
	public DTOResult<EipEntity> findEIP(String eipCode, String accessKey);

	public WSResult allocateEIP(String isp, String protocols, String sourcePorts, String targetPorts, String bandwidth,
			String remark, String accessKey);

	public WSResult recoverEIP(String eipCode, String accessKey);

	public WSResult associateEIP(String eipCode, String serviceCode, String accessKey);

	public WSResult dissociateEIP(String eipCode, String serviceCode, String accessKey);

	/******** VMRC ********/
	public DTOResult<VMRCEntity> findVMRC(String ecsCode, String accessKey);

	/******** Zabbix ********/
	public DTOResult<ZItemDTO> getCurrentData(String ecsCode, String itemKey, String accessKey);

	public DTOListResult<ZItemDTO> getHistoryData(String ecsCode, String itemKey, String accessKey);

	/******** ELB ********/
	public DTOResult<ElbEntity> findELB(String elbCode, String accessKey);

	public WSResult createELB(String ecsCodes, String protocols, String accessKey);

	public WSResult deleteELB(String elbCode, String accessKey);

}
