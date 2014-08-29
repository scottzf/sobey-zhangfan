package com.sobey.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.service.ApiService;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * Dns 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/dns")
public class DnsController {

	@Autowired
	private ApiService service;

	@ModelAttribute("tenantsList")
	public List<TenantsDTO> tenantsDTOList() {
		return service.getTenantsDTO();
	}

	@ModelAttribute("eipList")
	public List<EipDTO> eipList() {
		return service.getEipDTO();
	}

	@ModelAttribute("dnsList")
	public List<DnsDTO> dnsList() {
		return service.getDnsDTO();
	}

	private static String getPortFromProtocol(Integer protocol) {
		return "59".equals(protocol.toString()) ? "443" : "80";
	}

	/**
	 * 跳转到Dns页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "dns/create";
	}

	/**
	 * 创建一个Dns
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "domianName") String domianName,
			@RequestParam(value = "tenantsId") Integer tenantsId, @RequestParam(value = "eipIds") Integer[] eipIds,
			@RequestParam(value = "protocols") Integer[] protocols, RedirectAttributes redirectAttributes) {

		List<DnsPolicyDTO> dnsPolicyDTOs = new ArrayList<DnsPolicyDTO>();

		for (int i = 0; i < protocols.length; i++) {
			DnsPolicyDTO policyDTO = new DnsPolicyDTO();
			policyDTO.setDnsProtocol(protocols[i]);
			policyDTO.setPort(getPortFromProtocol(protocols[i]));
			policyDTO.setIpaddress(eipIds[i].toString());
			dnsPolicyDTOs.add(policyDTO);
		}

		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		dnsDTO.setTenants(tenantsId);
		dnsDTO.setDomainName(domianName);
		dnsDTO.setDescription(domianName);

		service.createDNS(dnsDTO, dnsPolicyDTOs, eipIds);

		redirectAttributes.addFlashAttribute("message", "Dns创建成功");

		return "redirect:/dns/create/";
	}

	/**
	 * 跳转到删除Dns页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "dns/delete";
	}

	/**
	 * 删除Dns
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "dnsId") Integer dnsId, RedirectAttributes redirectAttributes) {

		service.deleteDNS(dnsId);

		redirectAttributes.addFlashAttribute("message", "Dns删除成功");

		return "redirect:/dns/delete/";
	}

}
