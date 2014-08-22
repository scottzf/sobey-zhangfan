package com.sobey.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.service.ApiService;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

/**
 * Instance 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/instance")
public class InstanceController {

	@Autowired
	private ApiService service;

	/**
	 * 跳转到克隆页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "instance/create";
	}

	/**
	 * 克隆一个虚拟机
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "vmname") String vmname, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "ecsSpecId") Integer ecsSpecId, @RequestParam(value = "idcId") Integer idcId,
			@RequestParam(value = "tenantsId") Integer tenantsId, RedirectAttributes redirectAttributes) {

		EcsDTO ecsDTO = new EcsDTO();
		ecsDTO.setAgentType(LookUpConstants.AgentType.VMware.getValue());
		ecsDTO.setDescription(vmname);
		ecsDTO.setEcsSpec(ecsSpecId);
		ecsDTO.setIdc(idcId);
		ecsDTO.setRemark(remark);
		ecsDTO.setTenants(tenantsId);

		redirectAttributes.addFlashAttribute("message", service.createECS(ecsDTO).getMessage());

		return "redirect:/instance/create/";
	}

	@ModelAttribute("tenantsList")
	public List<TenantsDTO> tenantsDTOList() {
		return service.getTenantsDTO();
	}

	@ModelAttribute("idcList")
	public List<IdcDTO> idcList() {
		return service.getIdcDTO();
	}

	@ModelAttribute("ecsSpecList")
	public List<EcsSpecDTO> ecsSpecList() {
		return service.getEcsSpecDTO();
	}

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	/**
	 * 跳转到销毁虚拟机页面
	 */
	@RequestMapping(value = "/destroy/")
	public String destroyPage() {
		return "instance/destroy";
	}

	//
	/**
	 * 销毁虚拟机
	 */
	@RequestMapping(value = "/destroy/", method = RequestMethod.POST)
	public String destroy(@RequestParam(value = "ecsId") Integer ecsId, RedirectAttributes redirectAttributes) {

		service.destroyECS(ecsId);

		redirectAttributes.addFlashAttribute("message", "销毁成功");

		return "redirect:/instance/destroy/";
	}

	/**
	 * 跳转到虚拟机电源页面
	 */
	@RequestMapping(value = "/power/")
	public String powerPage() {
		return "instance/power";
	}

	/**
	 * 对虚拟机进行电源操作
	 */
	@RequestMapping(value = "/power/", method = RequestMethod.POST)
	public String power(@RequestParam(value = "ecsId") Integer ecsId,
			@RequestParam(value = "operation") String operation, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.powerOpsECS(ecsId, operation).getMessage());

		return "redirect:/instance/power/";
	}

	/**
	 * 跳转到虚拟机配置页面
	 */
	@RequestMapping(value = "/reconfig/")
	public String reconfigPage() {
		return "instance/reconfig";
	}

	/**
	 * 修改虚拟机配置
	 */
	@RequestMapping(value = "/reconfig/", method = RequestMethod.POST)
	public String reconfig(@RequestParam(value = "ecsId") Integer ecsId,
			@RequestParam(value = "ecsSpecId") Integer ecsSpecId, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.reconfigECS(ecsId, ecsSpecId).getMessage());

		return "redirect:/instance/reconfig/";
	}

	/**
	 * 跳转到同步主机、虚拟机的关联关系页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/sync/")
	public String syncPage(Model model) {
		return "instance/sync";
	}

	/**
	 * 同步主机、虚拟机的关联关系
	 */
	@RequestMapping(value = "/sync/", method = RequestMethod.POST)
	public String sync(@RequestParam(value = "remark") String remark, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.syncVM(remark));

		return "redirect:/instance/sync/";
	}

}
