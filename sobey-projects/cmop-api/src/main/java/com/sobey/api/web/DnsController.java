package com.sobey.api.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.DnsService;
import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DNSPolicyParameter;
import com.sobey.generate.dns.DNSPublicIPParameter;
import com.sobey.generate.dns.WSResult;

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
	 * 根据协议获得默认的端口.
	 * 
	 * <pre>
	 * HTTP:80
	 * HTTPS:443
	 * </pre>
	 * 
	 * @param protocol
	 * @return
	 */
	private Integer getPortFromProtocol(String protocol) {

		if ("HTTPS".equals(protocol.toUpperCase())) {
			return 443;
		} else {
			return 80;
		}

	}

	/**
	 * 创建一个Dns
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam(value = "domianName") String domianName,
			@RequestParam(value = "publicIPs") String[] publicIPs,
			@RequestParam(value = "protocols") String[] protocols, RedirectAttributes redirectAttributes) {

		ArrayList<DNSPublicIPParameter> publicIPParameters = new ArrayList<DNSPublicIPParameter>();

		for (int i = 0; i < protocols.length; i++) {

			DNSPolicyParameter policyParameter = new DNSPolicyParameter();
			policyParameter.setProtocolText(protocols[i]);
			policyParameter.setSourcePort(getPortFromProtocol(protocols[i]));
			policyParameter.setTargetPort(getPortFromProtocol(protocols[i]));

			DNSPublicIPParameter publicIPParameter = new DNSPublicIPParameter();
			publicIPParameter.setIpaddress(publicIPs[i]);
			publicIPParameter.getPolicyParameters().add(policyParameter);

			publicIPParameters.add(publicIPParameter);

		}

		DNSParameter dnsParameter = new DNSParameter();
		dnsParameter.setDomianType("gslb");
		dnsParameter.setDomianName(domianName);
		dnsParameter.getPublicIPs().addAll(publicIPParameters);

		String message = "";
		WSResult wsResult = service.createDNS(dnsParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Dns创建成功";
		} else {
			message = wsResult.getMessage();
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
	public String delete(@RequestParam(value = "domianName") String domianName,
			@RequestParam(value = "publicIPs") String[] publicIPs,
			@RequestParam(value = "protocols") String[] protocols, RedirectAttributes redirectAttributes) {

		ArrayList<DNSPublicIPParameter> publicIPParameters = new ArrayList<DNSPublicIPParameter>();

		for (int i = 0; i < protocols.length; i++) {

			DNSPolicyParameter policyParameter = new DNSPolicyParameter();
			policyParameter.setProtocolText(protocols[i]);
			policyParameter.setSourcePort(getPortFromProtocol(protocols[i]));
			policyParameter.setTargetPort(getPortFromProtocol(protocols[i]));

			DNSPublicIPParameter publicIPParameter = new DNSPublicIPParameter();
			publicIPParameter.setIpaddress(publicIPs[i]);
			publicIPParameter.getPolicyParameters().add(policyParameter);

			publicIPParameters.add(publicIPParameter);

		}

		DNSParameter dnsParameter = new DNSParameter();
		dnsParameter.setDomianType("gslb");
		dnsParameter.setDomianName(domianName);
		dnsParameter.getPublicIPs().addAll(publicIPParameters);

		String message = "";
		WSResult wsResult = service.deleteDNS(dnsParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Dns删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/dns/delete/";
	}

}
