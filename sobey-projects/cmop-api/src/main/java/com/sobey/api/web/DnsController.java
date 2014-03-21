package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.DnsService;
import com.sobey.generate.dns.DNSParameter;

/**
 * Dns 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/dns")
public class DnsController {

	@Autowired
	private DnsService service;

	/**
	 * 跳转到Dns页面
	 */
	@RequestMapping(value = "/create")
	public String createPage() {
		return "dns/create";
	}

	/**
	 * 创建一个Dns
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam(value = "description") String description,
			@RequestParam(value = "gateway") String gateway, @RequestParam(value = "ipaddress") String ipaddress,
			@RequestParam(value = "subNetMask") String subNetMask, @RequestParam(value = "vmName") String vmName,
			RedirectAttributes redirectAttributes) {

		DNSParameter dnsParameter = new DNSParameter();

		String message = "";

		if (service.createDNS(dnsParameter).getCode().equals("0")) {
			message = "Dns创建成功";
		} else {
			message = "Dns创建失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/dns/create/";
	}

	/**
	 * 跳转到删除Dns页面
	 */
	@RequestMapping(value = "/delete")
	public String deletePage() {
		return "dns/delete";
	}

	/**
	 * 删除Dns
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "vmName") String vmName, RedirectAttributes redirectAttributes) {

		DNSParameter dnsParameter = new DNSParameter();

		String message = "";

		if (service.deleteDNS(dnsParameter).getCode().equals("0")) {
			message = "Dns删除成功";
		} else {
			message = "Dns删除失败";
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/dns/delete/";
	}

}
