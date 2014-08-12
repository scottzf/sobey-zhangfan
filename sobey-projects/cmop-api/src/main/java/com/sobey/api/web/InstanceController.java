package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.AwsService;
import com.sobey.api.service.InstanceService;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.WSResult;

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

	@Autowired
	private AwsService awsService;

	/**
	 * 跳转到克隆页面
	 */
	@RequestMapping(value = "/clone/")
	public String clonePage() {
		return "instance/clone";
	}

	/**
	 * 克隆一个虚拟机
	 */
	@RequestMapping(value = "/clone/", method = RequestMethod.POST)
	public String clone(@RequestParam(value = "description") String description,
			@RequestParam(value = "gateway") String gateway, @RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "subNetMask") String subNetMask, @RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "vmTemplateOS") String vmTemplateOS, @RequestParam(value = "vlanId") Integer vlanId,
			@RequestParam(value = "agentTypeId") String agentTypeId,
			@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		System.out.println("****************");
		System.out.println("agentTypeId:" + agentTypeId);
		System.out.println("****************");

		if ("aws".equals(agentTypeId)) {// aws

			System.out.println("*****1***********");

			awsService.createEC2();

			redirectAttributes.addFlashAttribute("message", "创建成功");

		} else if ("vmware".equals(agentTypeId)) {

			System.out.println("******0**********");

			CloneVMParameter cloneVMParameter = new CloneVMParameter();
			cloneVMParameter.setDescription(description);
			cloneVMParameter.setGateway(gateway);
			cloneVMParameter.setIpaddress(ipaddress);
			cloneVMParameter.setVMName(vmName);
			cloneVMParameter.setSubNetMask(subNetMask);
			cloneVMParameter.setVMTemplateName(vmTemplateOS);
			cloneVMParameter.setDatacenter(datacenter);
			cloneVMParameter.setVlanId(vlanId);

			cloneVMParameter.setVMSUserName("Sobey");
			cloneVMParameter.setVMTemplateOS("Linux");

			String message = "";

			WSResult wsResult = service.cloneVM(cloneVMParameter);

			if ("0".equals(wsResult.getCode())) {
				message = "创建成功";
			} else {
				message = wsResult.getMessage();
			}

			redirectAttributes.addFlashAttribute("message", message);
		}

		return "redirect:/instance/clone/";
	}

	/**
	 * 跳转到销毁虚拟机页面
	 */
	@RequestMapping(value = "/destroy/")
	public String destroyPage() {
		return "instance/destroy";
	}

	/**
	 * 销毁虚拟机
	 */
	@RequestMapping(value = "/destroy/", method = RequestMethod.POST)
	public String destroy(@RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		DestroyVMParameter destroyVMParameter = new DestroyVMParameter();
		destroyVMParameter.setVMName(vmName);
		destroyVMParameter.setDatacenter(datacenter);

		String message = "";
		WSResult wsResult = service.desoroyVM(destroyVMParameter);

		if ("0".equals(wsResult.getCode())) {
			message = "销毁成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

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
	public String power(@RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "operation") String operation, @RequestParam(value = "datacenter") String datacenter,
			RedirectAttributes redirectAttributes) {

		PowerVMParameter powerVMParameter = new PowerVMParameter();
		powerVMParameter.setVMName(vmName);
		powerVMParameter.setPowerOperation(operation);
		powerVMParameter.setDatacenter(datacenter);

		String message = "";
		WSResult wsResult = service.powerVM(powerVMParameter);

		if ("0".equals(wsResult.getCode())) {
			message = "电源操作成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

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
	public String reconfig(@RequestParam(value = "vmName") String vmName,
			@RequestParam(value = "cpuNumber") Integer cpuNumber, @RequestParam(value = "memoryMB") Long memoryMB,
			@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		ReconfigVMParameter reconfigVMParameter = new ReconfigVMParameter();
		reconfigVMParameter.setVMName(vmName);
		reconfigVMParameter.setCPUNumber(cpuNumber);
		reconfigVMParameter.setMemoryMB(memoryMB);
		reconfigVMParameter.setDatacenter(datacenter);

		String message = "";
		WSResult wsResult = service.reconfigVM(reconfigVMParameter);

		if ("0".equals(wsResult.getCode())) {
			message = "配置修改成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/reconfig/";
	}

	/**
	 * 主机、虚拟机的关联关系
	 * 
	 * @return
	 */
	@RequestMapping(value = "/relation/")
	public String relationPage(Model model) {
		return "instance/relation";
	}

	/**
	 * 刷新主机、虚拟机的关联关系
	 */
	@RequestMapping(value = "/relation/", method = RequestMethod.POST)
	public String relation(@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("relations", service.relationVM(datacenter));

		return "redirect:/instance/relation/";
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
	public String sync(@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", service.syncVM(datacenter));

		return "redirect:/instance/sync/";
	}

	/**
	 * 跳转到创建PortGroup页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/portGroup/")
	public String portGroupPage(Model model) {
		return "instance/portGroup";
	}

	/**
	 * 创建PortGroup
	 */
	@RequestMapping(value = "/portGroup/", method = RequestMethod.POST)
	public String portGroup(@RequestParam(value = "vlanId") Integer vlanId,
			@RequestParam(value = "datacenter") String datacenter, RedirectAttributes redirectAttributes) {

		String message = "";
		WSResult wsResult = service.createPortGroup(vlanId, datacenter);

		if ("0".equals(wsResult.getCode())) {
			message = "分布式端口组创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/instance/portGroup/";
	}

}
