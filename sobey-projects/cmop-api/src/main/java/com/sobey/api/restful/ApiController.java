package com.sobey.api.restful;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sobey.api.entity.DnsEntity;
import com.sobey.api.entity.EcsEntity;
import com.sobey.api.entity.EipEntity;
import com.sobey.api.entity.ElbEntity;
import com.sobey.api.entity.Es3Entity;
import com.sobey.api.entity.EsgEntity;
import com.sobey.api.entity.TagEntity;
import com.sobey.api.service.RestfulService;
import com.sobey.api.webservice.response.result.DTOResult;
import com.sobey.api.webservice.response.result.WSResult;
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

	/********** ECS ***********/

	@RequestMapping(value = "/ECSResult/{ecsName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<EcsEntity> ECSResult(@PathVariable("ecsName") String ecsName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findECS(URLEscape(ecsName), accessKey);
	}

	@RequestMapping(value = "/createECS/", method = RequestMethod.POST)
	public WSResult createECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "remark") String remark, @RequestParam(value = "ecsSpec") String ecsSpec,
			@RequestParam(value = "idc") String idc, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createECS(URLEscape(ecsName), remark, ecsSpec, idc, accessKey);
	}

	@RequestMapping(value = "/destroyECS/", method = RequestMethod.POST)
	public WSResult destroyECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.destroyECS(URLEscape(ecsName), accessKey);
	}

	@RequestMapping(value = "/powerOpsECS/", method = RequestMethod.POST)
	public WSResult powerOpsECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "powerOperation") String powerOperation,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.powerOpsECS(URLEscape(ecsName), powerOperation, accessKey);
	}

	@RequestMapping(value = "/reconfigECS/", method = RequestMethod.POST)
	public WSResult reconfigECS(@RequestParam(value = "ecsName") String ecsName,
			@RequestParam(value = "ecsSpec") String ecsSpec, @RequestParam(value = "accessKey") String accessKey) {
		return servie.reconfigECS(URLEscape(ecsName), ecsSpec, accessKey);
	}

	/********** ES3 ***********/

	@RequestMapping(value = "/ES3Result/{es3Name}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<Es3Entity> ES3Result(@PathVariable("es3Name") String es3Name,
			@PathVariable("accessKey") String accessKey) {
		return servie.findES3(URLEscape(es3Name), accessKey);
	}

	@RequestMapping(value = "/createES3/", method = RequestMethod.POST)
	public WSResult createES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "es3Size") Double es3Size, @RequestParam(value = "es3Type") String es3Type,
			@RequestParam(value = "idc") String idc, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.createES3(URLEscape(es3Name), es3Size, es3Type, idc, remark, accessKey);
	}

	@RequestMapping(value = "/attachES3/", method = RequestMethod.POST)
	public WSResult attachES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.attachES3(URLEscape(es3Name), ecsName, accessKey);
	}

	@RequestMapping(value = "/detachES3/", method = RequestMethod.POST)
	public WSResult detachES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.detachES3(URLEscape(es3Name), ecsName, accessKey);
	}

	@RequestMapping(value = "/deleteES3/", method = RequestMethod.POST)
	public WSResult deleteES3(@RequestParam(value = "es3Name") String es3Name,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteES3(URLEscape(es3Name), accessKey);
	}

	/********** ELB ***********/

	@RequestMapping(value = "/ELBResult/{elbName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<ElbEntity> ELBResult(@PathVariable("elbName") String elbName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findELB(URLEscape(elbName), accessKey);
	}

	@RequestMapping(value = "/createELB/", method = RequestMethod.POST)
	public WSResult createELB(@RequestParam(value = "ecsNames") String ecsNames,
			@RequestParam(value = "protocols") String protocols, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createELB(URLEscape(ecsNames), protocols, accessKey);
	}

	@RequestMapping(value = "/deleteELB/", method = RequestMethod.POST)
	public WSResult deleteELB(@RequestParam(value = "elbName") String elbName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteELB(URLEscape(elbName), accessKey);
	}

	/********** EIP ***********/

	@RequestMapping(value = "/EIPResult/{eipName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<EipEntity> EIPResult(@PathVariable("eipName") String eipName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findEIP(URLEscape(eipName), accessKey);
	}

	@RequestMapping(value = "/allocateEIP/", method = RequestMethod.POST)
	public WSResult allocateEIP(@RequestParam(value = "isp") String isp, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "bandwidth") String bandwidth, @RequestParam(value = "protocols") String protocols,
			@RequestParam(value = "sourcePorts") String sourcePorts,
			@RequestParam(value = "targetPorts") String targetPorts, @RequestParam(value = "accessKey") String accessKey) {
		return servie.allocateEIP(URLEscape(isp), protocols, sourcePorts, targetPorts, bandwidth, remark, accessKey);
	}

	@RequestMapping(value = "/recoverEIP/", method = RequestMethod.POST)
	public WSResult recoverEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.recoverEIP(URLEscape(eipName), accessKey);
	}

	@RequestMapping(value = "/associateEIP/", method = RequestMethod.POST)
	public WSResult associateEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateEIP(URLEscape(eipName), URLEscape(serviceName), accessKey);
	}

	@RequestMapping(value = "/dissociateEIP/", method = RequestMethod.POST)
	public WSResult dissociateEIP(@RequestParam(value = "eipName") String eipName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dissociateEIP(URLEscape(eipName), URLEscape(serviceName), accessKey);
	}

	/********** DNS ***********/

	@RequestMapping(value = "/DNSResult/{dnsName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<DnsEntity> DNSResult(@PathVariable("dnsName") String dnsName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findDNS(URLEscape(dnsName), accessKey);
	}

	@RequestMapping(value = "/createDNS/", method = RequestMethod.POST)
	public WSResult createDNS(@RequestParam(value = "domainName") String domainName,
			@RequestParam(value = "eipNames") String eipNames, @RequestParam(value = "protocols") String protocols,
			@RequestParam(value = "remark") String remark, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createDNS(URLEscape(domainName), URLEscape(eipNames), protocols, remark, accessKey);
	}

	@RequestMapping(value = "/deleteDNS/", method = RequestMethod.POST)
	public WSResult deleteDNS(@RequestParam(value = "domainName") String domainName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteDNS(domainName, accessKey);
	}

	/********** ESG ***********/

	@RequestMapping(value = "/ESGResult/{esgName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<EsgEntity> ESGResult(@PathVariable("esgName") String esgName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findESG(URLEscape(esgName), accessKey);
	}

	@RequestMapping(value = "/createESG/", method = RequestMethod.POST)
	public WSResult createESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "policyTypes") String policyTypes, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "targetIPs") String targetIPs, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createESG(URLEscape(esgName), URLEscape(policyTypes), targetIPs, remark, accessKey);
	}

	@RequestMapping(value = "/deleteESG/", method = RequestMethod.POST)
	public WSResult deleteESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteESG(URLEscape(esgName), accessKey);
	}

	@RequestMapping(value = "/associateESG/", method = RequestMethod.POST)
	public WSResult associateESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateESG(URLEscape(ecsName), URLEscape(esgName), accessKey);
	}

	@RequestMapping(value = "/dissociateESG/", method = RequestMethod.POST)
	public WSResult dissociateESG(@RequestParam(value = "esgName") String esgName,
			@RequestParam(value = "ecsName") String ecsName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dissociateESG(URLEscape(ecsName), URLEscape(esgName), accessKey);
	}

	/********** TAG ***********/

	@RequestMapping(value = "/TagResult/{tagName}/{accessKey}", method = RequestMethod.GET)
	public DTOResult<TagEntity> TagResult(@PathVariable("tagName") String tagName,
			@PathVariable("accessKey") String accessKey) {
		return servie.findTag(tagName, accessKey);
	}

	@RequestMapping(value = "/createTag/", method = RequestMethod.POST)
	public WSResult createTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "parentTag") String parentTag, @RequestParam(value = "accessKey") String accessKey) {
		return servie.createTag(URLEscape(tagName), URLEscape(parentTag), accessKey);
	}

	@RequestMapping(value = "/deleteTag/", method = RequestMethod.POST)
	public WSResult deleteTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "accessKey") String accessKey) {
		return servie.deleteTag(URLEscape(tagName), accessKey);
	}

	@RequestMapping(value = "/associateTag/", method = RequestMethod.POST)
	public WSResult associateTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.associateTag(URLEscape(tagName), URLEscape(serviceName), accessKey);
	}

	@RequestMapping(value = "/dssociateTag/", method = RequestMethod.POST)
	public WSResult dssociateTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "serviceName") String serviceName, @RequestParam(value = "accessKey") String accessKey) {
		return servie.dssociateTag(URLEscape(tagName), URLEscape(serviceName), accessKey);
	}

	/********** Zabbix ***********/

	@RequestMapping(value = "/currentData/{ecsName}/{itemKey}/{accessKey}", method = RequestMethod.GET)
	public ZItemDTO getCurrentData(@PathVariable("ecsName") String ecsName, @PathVariable("itemKey") String itemKey,
			@PathVariable("accessKey") String accessKey) throws UnsupportedEncodingException {
		return servie.getCurrentData(URLEscape(ecsName), itemKey, accessKey);
	}

	@RequestMapping(value = "/historyData/{ecsName}/{itemKey}/{accessKey}", method = RequestMethod.GET)
	public ZHistoryItemDTO getHistoryData(@PathVariable("ecsName") String ecsName,
			@PathVariable("itemKey") String itemKey, @PathVariable("accessKey") String accessKey)
			throws UnsupportedEncodingException {
		return servie.getHistoryData(URLEscape(ecsName), itemKey, accessKey);
	}

}
