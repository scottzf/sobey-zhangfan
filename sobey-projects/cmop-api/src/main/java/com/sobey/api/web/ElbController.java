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
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * elb 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/elb")
public class ElbController {

	@Autowired
	private ApiService service;

	@ModelAttribute("tenantsList")
	public List<TenantsDTO> tenantsDTOList() {
		return service.getTenantsDTO();
	}

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	@ModelAttribute("elbList")
	public List<ElbDTO> elbList() {
		return service.getElbDTO();
	}

	private static Integer getPortFromProtocol(Integer protocol) {
		return "97".equals(protocol) ? 443 : 80;
	}

	/**
	 * 跳转到elb页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "elb/create";
	}

	/**
	 * 创建elb
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "tenantsId") Integer tenantsId,
			@RequestParam(value = "ecsIds") Integer[] ecsIds, @RequestParam(value = "protocols") Integer[] protocols,
			RedirectAttributes redirectAttributes) {

		List<ElbPolicyDTO> elbPolicyDTOs = new ArrayList<ElbPolicyDTO>();

		for (int i = 0; i < protocols.length; i++) {
			ElbPolicyDTO policyDTO = new ElbPolicyDTO();
			policyDTO.setElbProtocol(protocols[i]);
			policyDTO.setSourcePort(getPortFromProtocol(protocols[i]));
			policyDTO.setTargetPort(getPortFromProtocol(protocols[i]));
			policyDTO.setIpaddress(ecsIds[i].toString());
			elbPolicyDTOs.add(policyDTO);
		}

		ElbDTO elbDTO = new ElbDTO();
		elbDTO.setAgentType(LookUpConstants.AgentType.Netscaler.getValue());
		elbDTO.setTenants(tenantsId);

		service.createELB(elbDTO, elbPolicyDTOs, ecsIds);

		redirectAttributes.addFlashAttribute("message", "elb创建成功");

		return "redirect:/elb/create/";
	}

	/**
	 * 跳转到删除elb页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "elb/delete";
	}

	/**
	 * 删除elb
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "elbId") Integer elbId, RedirectAttributes redirectAttributes) {

		service.deleteELB(elbId);

		redirectAttributes.addFlashAttribute("message", "elb删除成功");

		return "redirect:/elb/delete/";
	}

}
