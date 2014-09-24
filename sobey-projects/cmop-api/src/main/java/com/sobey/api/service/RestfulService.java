package com.sobey.api.service;

import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.zabbix.ZHistoryItemDTO;
import com.sobey.generate.zabbix.ZItemDTO;

public interface RestfulService {

	public WSResult createECS(String ecsName, String remark, String ecsSpec, String idc, String accessKey);

	public WSResult destroyECS(String ecsName, String accessKey);

	public WSResult powerOpsECS(String ecsName, String powerOperation, String accessKey);

	public WSResult reconfigECS(String ecsName, String ecsSpec, String accessKey);

	public DTOResult<EcsDTO> findECS(String ecsName, String accessKey);

	public WSResult createES3(String es3Name, Double es3Size, String es3Type, String idc, String remark,
			String accessKey);

	public WSResult attachES3(String es3Name, String ecsName, String accessKey);

	public WSResult detachES3(String es3Name, String ecsName, String accessKey);

	public WSResult deleteES3(String es3Name, String accessKey);

	public WSResult allocateEIP(String isp, String protocols, String sourcePorts, String targetPorts, String accessKey);

	public WSResult recoverEIP(String eipName, String accessKey);

	public WSResult associateEIP(String eipName, String serviceName, String accessKey);

	public WSResult dissociateEIP(String eipName, String serviceName, String accessKey);

	public WSResult createELB(String ecsNames, String protocols, String accessKey);

	public WSResult deleteELB(String elbName, String accessKey);

	public WSResult createDNS(String domianName, String eipNames, String protocols, String accessKey);

	public WSResult deleteDNS(String domianName, String accessKey);

	public WSResult createESG(String esgName, String policyTypes, String targetIPs, String accessKey);

	public WSResult deleteESG(String esgName, String accessKey);

	public WSResult associateESG(String ecsName, String esgName, String accessKey);

	public WSResult dissociateESG(String ecsName, String esgName, String accessKey);

	public WSResult createTag(String tagName, String parentTag, String accessKey);

	public WSResult deleteTag(String tagName, String accessKey);

	public WSResult associateTag(String tagName, String serviceName, String accessKey);

	public WSResult dssociateTag(String tagName, String serviceName, String accessKey);

	public ZItemDTO getCurrentData(String ecsName, String itemKey, String accessKey);

	public ZHistoryItemDTO getHistoryData(String ecsName, String itemKey, String accessKey);

}
