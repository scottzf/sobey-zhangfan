package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.InstanceService;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;

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
	private InstanceService service;

	/**
	 * 跳转到克隆页面
	 */
	@RequestMapping(value = "/clone")
	public String clonePage() {
		return "instance/clone";
	}

	/**
	 * 克隆一个虚拟机
	 */
	@RequestMapping(value = "/clone", method = RequestMethod.POST)
	public String clone(@RequestParam(value = "description") String description,
			@RequestParam(value = "gateway") String gateway, @RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "subNetMask") String subNetMask, @RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "vmTemplateOS") String vmTemplateOS, RedirectAttributes redirectAttributes) {

		CloneVMParameter cloneVMParameter = new CloneVMParameter();
		cloneVMParameter.setDescription(description);
		cloneVMParameter.setGateway(gateway);
		cloneVMParameter.setIpaddress(ipaddress);
		cloneVMParameter.setVMName(vmName);
		cloneVMParameter.setSubNetMask(subNetMask);
		cloneVMParameter.setVMTemplateName(vmTemplateOS);

		cloneVMParameter.setVMSUserName("Sobey");
		cloneVMParameter.setVMTemplateOS("Linux");

		String message = "";

		if (service.cloneVM(cloneVMParameter).getCode().equals("0")) {
			message = "克隆成功";
		} else {
			message = "克隆失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/clone/";
	}

	/**
	 * 跳转到销毁虚拟机页面
	 */
	@RequestMapping(value = "/destroy")
	public String destroyPage() {
		return "instance/destroy";
	}

	/**
	 * 销毁虚拟机
	 */
	@RequestMapping(value = "/destroy", method = RequestMethod.POST)
	public String destroy(@RequestParam(value = "vmName") String vmName, RedirectAttributes redirectAttributes) {

		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setVMName(vmName);

		String message = "";

		if (service.desoroyVM(destroyVMParameter).getCode().equals("0")) {
			message = "销毁成功";
		} else {
			message = "销毁失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/destroy/";
	}

	/**
	 * 跳转到虚拟机电源页面
	 */
	@RequestMapping(value = "/power")
	public String powerPage() {
		return "instance/power";
	}

	/**
	 * 对虚拟机进行电源操作
	 */
	@RequestMapping(value = "/power", method = RequestMethod.POST)
	public String power(@RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "operation") String operation, RedirectAttributes redirectAttributes) {

		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName(vmName);
		powerVMParameter.setPowerOperation(operation);

		String message = "";

		if (service.powerVM(powerVMParameter).getCode().equals("0")) {
			message = "电源操作成功";
		} else {
			message = "电源操作失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/power/";
	}

	/**
	 * 跳转到虚拟机配置页面
	 */
	@RequestMapping(value = "/reconfig")
	public String reconfigPage() {
		return "instance/reconfig";
	}

	/**
	 * 修改虚拟机配置
	 */
	@RequestMapping(value = "/reconfig", method = RequestMethod.POST)
	public String reconfig(@RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "cpuNumber") Integer cpuNumber, @RequestParam(value = "memoryMB") Long memoryMB,
			RedirectAttributes redirectAttributes) {

		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setVMName(vmName);
		reconfigVMParameter.setCPUNumber(cpuNumber);
		reconfigVMParameter.setMemoryMB(memoryMB);

		String message = "";

		if (service.reconfigVM(reconfigVMParameter).getCode().equals("0")) {
			message = "配置修改成功";
		} else {
			message = "配置修改失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/reconfig/";
	}

	/**
	 * 主机、虚拟机的关联关系
	 * 
	 * @return
	 */
	@RequestMapping(value = "/relation")
	public String relationPage(Model model) {
		model.addAttribute("relations", service.relationVM());
		return "instance/relation";
	}

	/**
	 * 刷新主机、虚拟机的关联关系
	 */
	@RequestMapping(value = "/relation", method = RequestMethod.POST)
	public String relation(RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("relations", service.relationVM());

		return "redirect:/instance/relation/";
	}

}
