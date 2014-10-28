package com.sobey.api.service;

import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.EipEntity;
import com.sobey.api.entity.ElbEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.EsgEntity;
import com.sobey.api.entity.TagEntity;
import com.sobey.api.entity.TenantsEntity;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.zabbix.ZHistoryItemDTO;
import com.sobey.generate.zabbix.ZItemDTO;

public interface RestfulService {

	/******** Tenants ********/

	public DTOResult<TenantsEntity> findTenants(String accessKey);

	public WSResult createTenants(String company, String name, String email, String password, String phone);

	/******** ECS ********/

	public DTOResult<EcsEntity> findECS(String ecsName, String accessKey);

	public WSResult createECS(String ecsName, String remark, String ecsSpec, String idc, String accessKey);

	public WSResult destroyECS(String ecsName, String accessKey);

	public WSResult powerOpsECS(String ecsName, String powerOperation, String accessKey);

	public WSResult reconfigECS(String ecsName, String ecsSpec, String accessKey);

	/******** ES3 ********/

	public DTOResult<Es3Entity> findES3(String es3Name, String accessKey);

	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String remark,
			String accessKey);

	public WSResult attachES3(String es3Name, String ecsName, String accessKey);

	public WSResult detachES3(String es3Name, String ecsName, String accessKey);

	public WSResult deleteES3(String es3Name, String accessKey);

	/******** EIP ********/

	public DTOResult<EipEntity> findEIP(String eipName, String accessKey);

	public WSResult allocateEIP(String isp, String protocols, String sourcePorts, String targetPorts, String bandwidth,
			String remark, String accessKey);

	public WSResult recoverEIP(String eipName, String accessKey);

	public WSResult associateEIP(String eipName, String serviceId, String accessKey);

	public WSResult dissociateEIP(String eipName, String serviceId, String accessKey);

	/******** ELB ********/

	public DTOResult<ElbEntity> findELB(String elbName, String accessKey);

	public WSResult createELB(String ecsNames, String protocols, String accessKey);

	public WSResult deleteELB(String elbName, String accessKey);

	/******** DNS ********/

	public DTOResult<DnsEntity> findDNS(String dnsName, String accessKey);

	public WSResult createDNS(String domianName, String eipNames, String protocols, String remark, String accessKey);

	public WSResult deleteDNS(String domianName, String accessKey);

	/******** ESG ********/

	public DTOResult<EsgEntity> findESG(String esgName, String accessKey);

	public WSResult createESG(String esgName, String policyTypes, String targetIPs, String remark, String accessKey);

	public WSResult deleteESG(String esgName, String accessKey);

	public WSResult associateESG(String ecsName, String esgName, String accessKey);

	public WSResult dissociateESG(String ecsName, String esgName, String accessKey);

	/******** TAG ********/

	public DTOResult<TagEntity> findTag(String tagName, String accessKey);

	public WSResult createTag(String tagName, String parentTag, String accessKey);

	public WSResult deleteTag(String tagName, String accessKey);

	public WSResult associateTag(String tagName, String serviceId, String accessKey);

	public WSResult dssociateTag(String tagName, String serviceId, String accessKey);

	/******** Zabbix ********/

	public ZItemDTO getCurrentData(String ecsName, String itemKey, String accessKey);

	public ZHistoryItemDTO getHistoryData(String ecsName, String itemKey, String accessKey);

}
