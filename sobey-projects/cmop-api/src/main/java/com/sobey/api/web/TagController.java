package com.sobey.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.ApiService;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.TagDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * tag 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/tag")
public class TagController {

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

	@ModelAttribute("tagList")
	public List<TagDTO> tagList() {
		return service.getTagDTO();
	}

	/**
	 * 跳转到tag页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "tag/create";
	}

	/**
	 * 创建一个tag
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "name") String name,
			@RequestParam(value = "tenantsId") Integer tenantsId,
			@RequestParam(value = "parentTag", required = false) Integer parentTag,
			@RequestParam(value = "tagType") Integer tagType, RedirectAttributes redirectAttributes) {

		TagDTO tagDTO = new TagDTO();
		tagDTO.setTagType(tagType);
		tagDTO.setParentTag(parentTag);
		tagDTO.setTenants(tenantsId);
		tagDTO.setDescription(name);

		service.createTag(tagDTO);

		redirectAttributes.addFlashAttribute("message", "Tag创建成功");

		return "redirect:/tag/create/";
	}

	/**
	 * 跳转到删除tag页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "tag/delete";
	}

	/**
	 * 删除tag
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "tagId") Integer tagId, RedirectAttributes redirectAttributes) {

		service.deleteTag(tagId);
		redirectAttributes.addFlashAttribute("message", "Tag删除成功");

		return "redirect:/tag/delete/";
	}

	/**
	 * 跳转到绑定tag页面
	 */
	@RequestMapping(value = "/associate/")
	public String associatePage() {
		return "tag/associate";
	}

	/**
	 * 绑定tag
	 */
	@RequestMapping(value = "/associate/", method = RequestMethod.POST)
	public String associate(@RequestParam(value = "serviceId") Integer serviceId,
			@RequestParam(value = "tagId") Integer tagId, RedirectAttributes redirectAttributes) {

		service.associateTag(tagId, serviceId);

		redirectAttributes.addFlashAttribute("message", "tag绑定成功");

		return "redirect:/tag/associate/";
	}

	/**
	 * 跳转到解绑tag页面
	 */
	@RequestMapping(value = "/dissociate/")
	public String dissociatePage() {
		return "tag/dissociate";
	}

	/**
	 * 解绑tag
	 */
	@RequestMapping(value = "/dissociate/", method = RequestMethod.POST)
	public String dissociate(@RequestParam(value = "serviceId") Integer serviceId,
			@RequestParam(value = "tagId") Integer tagId, RedirectAttributes redirectAttributes) {

		service.dssociateTag(tagId, serviceId);

		redirectAttributes.addFlashAttribute("message", "tag解绑成功");

		return "redirect:/tag/dissociate/";
	}

}
