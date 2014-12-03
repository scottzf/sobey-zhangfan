package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.ApiService;

/**
 * Sync 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/sync")
public class SyncController {

	@Autowired
	private ApiService service;

	/**
	 * 跳转到ECS同步页面
	 */
	@RequestMapping(value = "/ecs/")
	public String ecsPage() {
		return "sync/ecs";
	}

	/**
	 * ECS同步
	 */
	@RequestMapping(value = "/ecs/", method = RequestMethod.POST)
	public String syncECS(RedirectAttributes redirectAttributes) {

		service.syncVM("xa");

		redirectAttributes.addFlashAttribute("message", "同步完成.");

		return "redirect:/sync/ecs/";
	}

	/**
	 * 跳转到ES3同步页面
	 */
	@RequestMapping(value = "/es3/")
	public String es3Page() {
		return "sync/es3";
	}

	/**
	 * ES3同步
	 */
	@RequestMapping(value = "/es3/", method = RequestMethod.POST)
	public String syncES3(RedirectAttributes redirectAttributes) {

		service.syncVolume();

		redirectAttributes.addFlashAttribute("message", "同步完成.");

		return "redirect:/sync/es3/";
	}

	/**
	 * 跳转到Dns同步页面
	 */
	@RequestMapping(value = "/dns/")
	public String dnsPage() {
		return "sync/dns";
	}

	/**
	 * 同步Dns
	 */
	@RequestMapping(value = "/dns/", method = RequestMethod.POST)
	public String syncDNS(RedirectAttributes redirectAttributes) {

		service.syncDNS();

		redirectAttributes.addFlashAttribute("message", "同步完成.");

		return "redirect:/sync/dns/";
	}

	/**
	 * 跳转到ELB同步页面
	 */
	@RequestMapping(value = "/elb/")
	public String elbPage() {
		return "sync/elb";
	}

	/**
	 * 同步ELB
	 */
	@RequestMapping(value = "/elb/", method = RequestMethod.POST)
	public String syncELB(RedirectAttributes redirectAttributes) {

		service.syncELB();

		redirectAttributes.addFlashAttribute("message", "同步完成.");

		return "redirect:/sync/elb/";
	}

}
