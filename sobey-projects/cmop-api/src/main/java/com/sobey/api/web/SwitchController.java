package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.SwitchService;
import com.sobey.generate.switches.VlanParameter;
import com.sobey.generate.switches.WSResult;

/**
 * Switch 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/switch")
public class SwitchController {

	@Autowired
	private SwitchService service;

	/**
	 * 跳转到Vlan页面
	 */
	@RequestMapping(value = "/create/vlan/")
	public String createVlanPage() {
		return "switch/createVlan";
	}

	/**
	 * 创建一个Vlan
	 */
	@RequestMapping(value = "/create/vlan/", method = RequestMethod.POST)
	public String createVlan(@RequestParam(value = "vlanId") Integer vlanId,
			@RequestParam(value = "gateway") String gateway, @RequestParam(value = "netMask") String netMask,
			RedirectAttributes redirectAttributes) {

		VlanParameter vlanParameter = new VlanParameter();
		vlanParameter.setVlanId(vlanId);
		vlanParameter.setGateway(gateway);
		vlanParameter.setNetMask(netMask);

		String message = "";
		WSResult wsResult = service.createVlan(vlanParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Vlan创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/switch/create/vlan/";
	}

	/**
	 * 跳转到删除Vlan页面
	 */
	@RequestMapping(value = "/delete/vlan/")
	public String deleteVlanPage() {
		return "switch/deleteVlan";
	}

	/**
	 * 删除Vlan
	 */
	@RequestMapping(value = "/delete/vlan/", method = RequestMethod.POST)
	public String deleteVlan(@RequestParam(value = "vlanId") Integer vlanId, RedirectAttributes redirectAttributes) {

		String message = "";

		WSResult wsResult = service.deleteVlan(vlanId);

		if (wsResult.getCode().equals("0")) {
			message = "Vlan删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/switch/delete/vlan/";
	}

}
