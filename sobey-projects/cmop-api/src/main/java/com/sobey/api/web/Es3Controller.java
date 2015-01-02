package com.sobey.api.web;

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
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * Storage 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/storage")
public class Es3Controller {

	@Autowired
	private ApiService service;

	@ModelAttribute("tenantsList")
	public List<TenantsDTO> tenantsDTOList() {
		return service.getTenantsDTO();
	}

	@ModelAttribute("idcList")
	public List<IdcDTO> idcList() {
		return service.getIdcDTO();
	}

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	@ModelAttribute("es3List")
	public List<Es3DTO> es3List() {
		return service.getEs3DTO();
	}

	/**
	 * 跳转到Storage页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "storage/create";
	}

	/**
	 * 创建一个Storage
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "volumeName") String volumeName,
			@RequestParam(value = "volumeSize") Double volumeSize,
			@RequestParam(value = "es3TypeId") Integer es3TypeId, @RequestParam(value = "idcId") Integer idcId,
			@RequestParam(value = "remark") String remark, @RequestParam(value = "tenantsId") Integer tenantsId,

			RedirectAttributes redirectAttributes) {

		Es3DTO es3dto = new Es3DTO();
		es3dto.setAgentType(LookUpConstants.AgentType.NetApp.getValue());
		es3dto.setDescription(volumeName);
		es3dto.setTotalSize(volumeSize.toString());
		es3dto.setEs3Type(es3TypeId);
		es3dto.setIdc(idcId);
		es3dto.setVolumeName(volumeName);
		es3dto.setTenants(tenantsId);
		es3dto.setRemark(remark);

		redirectAttributes.addFlashAttribute("message", service.createES3(es3dto).getMessage());

		return "redirect:/storage/create/";
	}

	/**
	 * 跳转到删除Storage页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "storage/delete";
	}

	/**
	 * Delete Storage
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "es3Id") Integer es3Id, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.deleteES3(es3Id).getMessage());

		return "redirect:/storage/delete/";
	}

	/**
	 * 跳转到Mount storage页面
	 */
	@RequestMapping(value = "/mount/")
	public String mountPage() {
		return "storage/mount";
	}

	/**
	 * Mount Storage
	 */
	@RequestMapping(value = "/mount/", method = RequestMethod.POST)
	public String mount(@RequestParam(value = "es3Id") Integer es3Id, @RequestParam(value = "ecsId") Integer ecsId,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.attachES3(es3Id, ecsId).getMessage());

		return "redirect:/storage/mount/";
	}

	/**
	 * 跳转到Umount Storage页面
	 */
	@RequestMapping(value = "/umount/")
	public String umountPage() {
		return "storage/umount";
	}

	/**
	 * Umount Storage
	 */
	@RequestMapping(value = "/umount/", method = RequestMethod.POST)
	public String umount(@RequestParam(value = "es3Id") Integer es3Id, @RequestParam(value = "ecsId") Integer ecsId,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.detachES3(es3Id, ecsId).getMessage());

		return "redirect:/storage/umount/";
	}

}
