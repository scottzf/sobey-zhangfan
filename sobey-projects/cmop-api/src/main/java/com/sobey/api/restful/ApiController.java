package com.sobey.api.restful;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sobey.api.service.RestfulService;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.zabbix.ZHistoryItemDTO;
import com.sobey.generate.zabbix.ZItemDTO;

@RestController
public class ApiController {

	@Autowired
	private RestfulService servie;

	private String URLEscape(String value) {
		try {
			return new String(value.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	@RequestMapping(value = "/ECSResult/{ecsName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<EcsDTO> ECSResult(@PathVariable("ecsName") String ecsName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findECS(URLEscape(ecsName), accessKey);
	}

	@RequestMapping(value = "/createECS/", method = RequestMethod.POST)
	public WSResult createECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "remark") String remark, @RequestParam(value = "ecsSpec") String ecsSpec,
			@RequestParam(value = "idc") String idc, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createECS(ecsName, remark, ecsSpec, idc, accessKey);
	}

	@RequestMapping(value = "/destroyECS/", method = RequestMethod.POST)
	public WSResult destroyECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.destroyECS(ecsName, accessKey);
	}

	@RequestMapping(value = "/powerOpsECS/", method = RequestMethod.POST)
	public WSResult powerOpsECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "powerOperation") String powerOperation,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.powerOpsECS(ecsName, powerOperation, accessKey);
	}

	@RequestMapping(value = "/reconfigECS/", method = RequestMethod.POST)
	public WSResult reconfigECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "ecsSpec") String ecsSpec, @RequestParam(value = "accessKey") String accessKey) {
		return servie.reconfigECS(ecsName, ecsSpec, accessKey);
	}

	@RequestMapping(value = "/createES3/", method = RequestMethod.POST)
	public WSResult createES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "es3Size") Double es3Size, @RequestParam(value = "es3Type") String es3Type,
			@RequestParam(value = "idc") String idc, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.createES3(es3Name, es3Size, es3Type, idc, remark, accessKey);
	}

	@RequestMapping(value = "/attachES3/", method = RequestMethod.POST)
	public WSResult attachES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.attachES3(es3Name, ecsName, accessKey);
	}

	@RequestMapping(value = "/detachES3/", method = RequestMethod.POST)
	public WSResult detachES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.detachES3(es3Name, ecsName, accessKey);
	}

	@RequestMapping(value = "/deleteES3/", method = RequestMethod.POST)
	public WSResult deleteES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteES3(es3Name, accessKey);
	}

	@RequestMapping(value = "/allocateEIP/", method = RequestMethod.POST)
	public WSResult allocateEIP(@RequestParam(value = "isp") String isp,
			@RequestParam(value = "protocols") String protocols,
			@RequestParam(value = "sourcePorts") String sourcePorts,
			@RequestParam(value = "targetPorts") String targetPorts, @RequestParam(value = "accessKey") String accessKey) {
		return servie.allocateEIP(isp, protocols, sourcePorts, targetPorts, accessKey);
	}

	@RequestMapping(value = "/recoverEIP/", method = RequestMethod.POST)
	public WSResult recoverEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.recoverEIP(eipName, accessKey);
	}

	@RequestMapping(value = "/associateEIP/", method = RequestMethod.POST)
	public WSResult associateEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateEIP(eipName, serviceName, accessKey);
	}

	@RequestMapping(value = "/dissociateEIP/", method = RequestMethod.POST)
	public WSResult dissociateEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dissociateEIP(eipName, serviceName, accessKey);
	}

	@RequestMapping(value = "/createELB/", method = RequestMethod.POST)
	public WSResult createELB(@RequestParam(value = "ecsNames") String ecsNames,
			@RequestParam(value = "protocols") String protocols, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createELB(ecsNames, protocols, accessKey);
	}

	@RequestMapping(value = "/deleteELB/", method = RequestMethod.POST)
	public WSResult deleteELB(@RequestParam(value = "elbName") String elbName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteELB(elbName, accessKey);
	}

	@RequestMapping(value = "/createDNS/", method = RequestMethod.POST)
	public WSResult createDNS(@RequestParam(value = "domainName") String domainName,
			@RequestParam(value = "eipNames") String eipNames, @RequestParam(value = "protocols") String protocols,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.createDNS(domainName, eipNames, protocols, accessKey);
	}

	@RequestMapping(value = "/deleteDNS/", method = RequestMethod.POST)
	public WSResult deleteDNS(@RequestParam(value = "domainName") String domainName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteDNS(domainName, accessKey);
	}

	@RequestMapping(value = "/createESG/", method = RequestMethod.POST)
	public WSResult createESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "policyTypes") String policyTypes,
			@RequestParam(value = "targetIPs") String targetIPs, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createESG(esgName, policyTypes, targetIPs, accessKey);
	}

	@RequestMapping(value = "/deleteESG/", method = RequestMethod.POST)
	public WSResult deleteESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteESG(esgName, accessKey);
	}

	@RequestMapping(value = "/associateESG/", method = RequestMethod.POST)
	public WSResult associateESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateESG(ecsName, esgName, accessKey);
	}

	@RequestMapping(value = "/dissociateESG/", method = RequestMethod.POST)
	public WSResult dissociateESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dissociateESG(ecsName, esgName, accessKey);
	}

	@RequestMapping(value = "/createTag/", method = RequestMethod.POST)
	public WSResult createTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "parentTag") String parentTag, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createTag(tagName, parentTag, accessKey);
	}

	@RequestMapping(value = "/deleteTag/", method = RequestMethod.POST)
	public WSResult deleteTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteTag(tagName, accessKey);
	}

	@RequestMapping(value = "/associateTag/", method = RequestMethod.POST)
	public WSResult associateTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateTag(tagName, serviceName, accessKey);
	}

	@RequestMapping(value = "/dssociateTag/", method = RequestMethod.POST)
	public WSResult dssociateTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dssociateTag(tagName, serviceName, accessKey);
	}

	@RequestMapping(value = "/currentData/{ecsName}/{itemKey}/{accessKey}", method = RequestMethod.GET)
	public ZItemDTO getCurrentData(@PathVariable("ecsName") String ecsName, @PathVariable("itemKey") String itemKey,
			@PathVariable("accessKey") String accessKey) throws UnsupportedEncodingException {
		return servie.getCurrentData(new String(ecsName.getBytes("iso-8859-1"), "UTF-8"), itemKey, accessKey);
	}

	@RequestMapping(value = "/historyData/{ecsName}/{itemKey}/{accessKey}", method = RequestMethod.GET)
	public ZHistoryItemDTO getHistoryData(@PathVariable("ecsName") String ecsName,
			@PathVariable("itemKey") String itemKey, @PathVariable("accessKey") String accessKey)
			throws UnsupportedEncodingException {
		return servie.getHistoryData(new String(ecsName.getBytes("iso-8859-1"), "UTF-8"), itemKey, accessKey);
	}

}
