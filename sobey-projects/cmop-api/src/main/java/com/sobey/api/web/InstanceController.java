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

		service.createECS(ecsDTO);

		redirectAttributes.addFlashAttribute("message", "创建ECS成功");

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

	// /**
	// * 跳转到销毁虚拟机页面
	// */
	// @RequestMapping(value = "/destroy/")
	// public String destroyPage() {
	// return "instance/destroy";
	// }
	//
	// /**
	// * 销毁虚拟机
	// */
	// @RequestMapping(value = "/destroy/", method = RequestMethod.POST)
	// public String destroy(@RequestParam(value = "vmName") String vmName,
	// @RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {
	//
	// DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
	// destroyVMParameter.setVMName(vmName);
	// destroyVMParameter.setDatacenter(datacenter);
	//
	// String message = "";
	// WSResult wsResult = service.desoroyVM(destroyVMParameter);
	//
	// if ("0".equals(wsResult.getCode())) {
	// message = "销毁成功";
	// } else {
	// message = wsResult.getMessage();
	// }
	//
	// redirectAttributes.addFlashAttribute("message", message);
	//
	// return "redirect:/instance/destroy/";
	// }
	//
	// /**
	// * 跳转到虚拟机电源页面
	// */
	// @RequestMapping(value = "/power/")
	// public String powerPage() {
	// return "instance/power";
	// }
	//
	// /**
	// * 对虚拟机进行电源操作
	// */
	// @RequestMapping(value = "/power/", method = RequestMethod.POST)
	// public String power(@RequestParam(value = "vmName") String vmName,
	// @RequestParam(value = "operation") String operation, @RequestParam(value = "datacenter") String datacenter,
	// RedirectAttributes redirectAttributes) {
	//
	// PowerVMParameter powerVMParameter = new PowerVMParameter();
	// powerVMParameter.setVMName(vmName);
	// powerVMParameter.setPowerOperation(operation);
	// powerVMParameter.setDatacenter(datacenter);
	//
	// String message = "";
	// WSResult wsResult = service.powerVM(powerVMParameter);
	//
	// if ("0".equals(wsResult.getCode())) {
	// message = "电源操作成功";
	// } else {
	// message = wsResult.getMessage();
	// }
	//
	// redirectAttributes.addFlashAttribute("message", message);
	//
	// return "redirect:/instance/power/";
	// }
	//
	// /**
	// * 跳转到虚拟机配置页面
	// */
	// @RequestMapping(value = "/reconfig/")
	// public String reconfigPage() {
	// return "instance/reconfig";
	// }
	//
	// /**
	// * 修改虚拟机配置
	// */
	// @RequestMapping(value = "/reconfig/", method = RequestMethod.POST)
	// public String reconfig(@RequestParam(value = "vmName") String vmName,
	// @RequestParam(value = "cpuNumber") Integer cpuNumber, @RequestParam(value = "memoryMB") Long memoryMB,
	// @RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {
	//
	// ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
	// reconfigVMParameter.setVMName(vmName);
	// reconfigVMParameter.setCPUNumber(cpuNumber);
	// reconfigVMParameter.setMemoryMB(memoryMB);
	// reconfigVMParameter.setDatacenter(datacenter);
	//
	// String message = "";
	// WSResult wsResult = service.reconfigVM(reconfigVMParameter);
	//
	// if ("0".equals(wsResult.getCode())) {
	// message = "配置修改成功";
	// } else {
	// message = wsResult.getMessage();
	// }
	//
	// redirectAttributes.addFlashAttribute("message", message);
	//
	// return "redirect:/instance/reconfig/";
	// }
	//
	// /**
	// * 主机、虚拟机的关联关系
	// *
	// * @return
	// */
	// @RequestMapping(value = "/relation/")
	// public String relationPage(Model model) {
	// return "instance/relation";
	// }
	//
	// /**
	// * 刷新主机、虚拟机的关联关系
	// */
	// @RequestMapping(value = "/relation/", method = RequestMethod.POST)
	// public String relation(@RequestParam(value = "datacenter") String datacenter, RedirectAttributes
	// redirectAttributes) {
	//
	// redirectAttributes.addFlashAttribute("relations", service.relationVM(datacenter));
	//
	// return "redirect:/instance/relation/";
	// }
	//
	// /**
	// * 跳转到同步主机、虚拟机的关联关系页面
	// *
	// * @return
	// */
	// @RequestMapping(value = "/sync/")
	// public String syncPage(Model model) {
	// return "instance/sync";
	// }
	//
	// /**
	// * 同步主机、虚拟机的关联关系
	// */
	// @RequestMapping(value = "/sync/", method = RequestMethod.POST)
	// public String sync(@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes)
	// {
	//
	// redirectAttributes.addFlashAttribute("message", service.syncVM(datacenter));
	//
	// return "redirect:/instance/sync/";
	// }

}
