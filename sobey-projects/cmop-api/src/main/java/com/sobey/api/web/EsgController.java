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
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * esg 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/esg")
public class EsgController {

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

	@ModelAttribute("esgList")
	public List<EsgDTO> esgList() {
		return service.getEsgDTO();
	}

	/**
	 * 跳转到esg页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "esg/create";
	}

	/**
	 * 创建一个esg
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "description") String description,
			@RequestParam(value = "tenantsId") Integer tenantsId,
			@RequestParam(value = "policyTypes") Integer[] policyTypes,
			@RequestParam(value = "targetIPs") String[] targetIPs, RedirectAttributes redirectAttributes) {

		List<EsgPolicyDTO> esgPolicyDTOs = new ArrayList<EsgPolicyDTO>();

		for (int i = 0; i < policyTypes.length; i++) {
			EsgPolicyDTO policyDTO = new EsgPolicyDTO();
			policyDTO.setPolicyType(policyTypes[i]);
			policyDTO.setTargetIP(targetIPs[i]);
			esgPolicyDTOs.add(policyDTO);
		}

		EsgDTO esgDTO = new EsgDTO();
		esgDTO.setAgentType(LookUpConstants.AgentType.H3C.getValue());
		esgDTO.setTenants(tenantsId);
		esgDTO.setDescription(description);

		service.createESG(esgDTO, esgPolicyDTOs);

		redirectAttributes.addFlashAttribute("message", "esg创建成功");

		return "redirect:/esg/create/";
	}

	/**
	 * 跳转到删除esg页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "esg/delete";
	}

	/**
	 * 删除esg
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "esgId") Integer esgId, RedirectAttributes redirectAttributes) {

		service.deleteESG(esgId);

		redirectAttributes.addFlashAttribute("message", "Esg删除成功");

		return "redirect:/esg/delete/";
	}

	/**
	 * 跳转到绑定esg页面
	 */
	@RequestMapping(value = "/associate/")
	public String associatePage() {
		return "esg/associate";
	}

	/**
	 * 绑定esg
	 */
	@RequestMapping(value = "/associate/", method = RequestMethod.POST)
	public String associate(@RequestParam(value = "ecsId") Integer ecsId, @RequestParam(value = "esgId") Integer esgId,
			RedirectAttributes redirectAttributes) {

		service.associateESG(ecsId, esgId);

		redirectAttributes.addFlashAttribute("message", "Esg绑定成功");

		return "redirect:/esg/associate/";
	}

	/**
	 * 跳转到解绑esg页面
	 */
	@RequestMapping(value = "/dissociate/")
	public String dissociatePage() {
		return "esg/dissociate";
	}

	/**
	 * 解绑esg
	 */
	@RequestMapping(value = "/dissociate/", method = RequestMethod.POST)
	public String dissociate(@RequestParam(value = "ecsId") Integer ecsId,
			@RequestParam(value = "esgId") Integer esgId, RedirectAttributes redirectAttributes) {

		service.dissociateESG(ecsId, esgId);

		redirectAttributes.addFlashAttribute("message", "Esg解绑成功");

		return "redirect:/esg/dissociate/";
	}

}
