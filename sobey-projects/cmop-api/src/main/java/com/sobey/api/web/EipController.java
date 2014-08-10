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
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * eip 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/eip")
public class EipController {

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

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	@ModelAttribute("elbList")
	public List<ElbDTO> elbList() {
		return service.getElbDTO();
	}

	/**
	 * 跳转到Eip页面
	 */
	@RequestMapping(value = "/allocate/")
	public String allocatePage() {
		return "eip/allocate";
	}

	/**
	 * 创建Eip
	 */
	@RequestMapping(value = "/allocate/", method = RequestMethod.POST)
	public String allocate(@RequestParam(value = "isp") Integer isp,
			@RequestParam(value = "tenantsId") Integer tenantsId,
			@RequestParam(value = "protocols") Integer[] protocols,
			@RequestParam(value = "sourcePorts") Integer[] sourcePorts,
			@RequestParam(value = "targetPorts") Integer[] targetPorts, RedirectAttributes redirectAttributes) {

		List<EipPolicyDTO> eipPolicyDTOs = new ArrayList<EipPolicyDTO>();

		for (int i = 0; i < targetPorts.length; i++) {
			EipPolicyDTO policyDTO = new EipPolicyDTO();
			policyDTO.setEipProtocol(protocols[i]);
			policyDTO.setSourcePort(sourcePorts[i]);
			policyDTO.setTargetPort(targetPorts[i]);
			eipPolicyDTOs.add(policyDTO);
		}

		EipDTO eipDTO = new EipDTO();
		eipDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		eipDTO.setBandwidth(1);
		eipDTO.setTenants(tenantsId);
		eipDTO.setIsp(isp);

		service.allocateEIP(eipDTO, eipPolicyDTOs);

		redirectAttributes.addFlashAttribute("message", "Eip创建成功");

		return "redirect:/eip/allocate/";
	}

	/**
	 * 跳转到删除Eip页面
	 */
	@RequestMapping(value = "/recover/")
	public String recoverPage() {
		return "eip/recover";
	}

	/**
	 * 删除Eip
	 */
	@RequestMapping(value = "/recover/", method = RequestMethod.POST)
	public String recover(@RequestParam(value = "eipId") Integer eipId, RedirectAttributes redirectAttributes) {

		service.recoverEIP(eipId);

		redirectAttributes.addFlashAttribute("message", "Eip删除成功");

		return "redirect:/eip/recover/";
	}

	/**
	 * 跳转到绑定Eip页面
	 */
	@RequestMapping(value = "/associate/")
	public String associatePage() {
		return "eip/associate";
	}

	/**
	 * 删除Eip
	 */
	@RequestMapping(value = "/associate/", method = RequestMethod.POST)
	public String associate(@RequestParam(value = "eipId") Integer eipId,
			@RequestParam(value = "serviceId") Integer serviceId, RedirectAttributes redirectAttributes) {

		service.associateEIP(eipId, serviceId);

		redirectAttributes.addFlashAttribute("message", "Eip绑定成功");

		return "redirect:/eip/associate/";
	}

	/**
	 * 跳转到解绑Eip页面
	 */
	@RequestMapping(value = "/dissociate/")
	public String dissociatePage() {
		return "eip/dissociate";
	}

	/**
	 * 解绑Eip
	 */
	@RequestMapping(value = "/dissociate/", method = RequestMethod.POST)
	public String dissociate(@RequestParam(value = "eipId") Integer eipId,
			@RequestParam(value = "serviceId") Integer serviceId, RedirectAttributes redirectAttributes) {

		service.dissociateEIP(eipId, serviceId);

		redirectAttributes.addFlashAttribute("message", "Eip解绑成功");

		return "redirect:/eip/dissociate/";
	}

}
