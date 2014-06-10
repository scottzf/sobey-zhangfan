package com.sobey.api.web;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sobey.api.service.FirewallService;
import com.sobey.generate.firewall.EIPParameter;
import com.sobey.generate.firewall.EIPPolicyParameter;
import com.sobey.generate.firewall.VPNUserParameter;
import com.sobey.generate.firewall.WSResult;

/**
 * Firewall 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/firewall")
public class FirewallController {

	@Autowired
	private FirewallService service;

	/**
	 * 跳转到Eip页面
	 */
	@RequestMapping(value = "/create/eip/")
	public String createEipPage() {
		return "firewall/createEip";
	}

	/**
	 * 创建Eip
	 */
	@RequestMapping(value = "/create/eip/", method = RequestMethod.POST)
	public String createEip(@RequestParam(value = "internetIP") String internetIP,
			@RequestParam(value = "isp") Integer isp, @RequestParam(value = "privateIP") String privateIP,
			@RequestParam(value = "protocolTexts") String[] protocolTexts,
			@RequestParam(value = "sourcePorts") String[] sourcePorts,
			@RequestParam(value = "targetPorts") String[] targetPorts, RedirectAttributes redirectAttributes) {

		ArrayList<EIPPolicyParameter> policies = new ArrayList<>();

		for (int i = 0; i < targetPorts.length; i++) {
			EIPPolicyParameter policyParameter = new EIPPolicyParameter();
			policyParameter.setProtocolText(protocolTexts[i]);
			policyParameter.setSourcePort(Integer.valueOf(sourcePorts[i]));
			policyParameter.setTargetPort(Integer.valueOf(targetPorts[i]));
			policies.add(policyParameter);
		}

		EIPParameter eipParameter = new EIPParameter();
		eipParameter.setInternetIP(internetIP);
		eipParameter.setIsp(isp);
		eipParameter.setPrivateIP(privateIP);
		eipParameter.getPolicies().addAll(policies);

		// 获得所有的策略.必须有个默认的策略,不能为null,否则webservice接口会报错"Fault occurred while processing."
		ArrayList<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.219-udp-8080");
		eipParameter.getAllPolicies().addAll(allPolicies);

		String message = "";
		WSResult wsResult = service.createEip(eipParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Eip创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/firewall/create/eip/";
	}

	/**
	 * 跳转到删除Eip页面
	 */
	@RequestMapping(value = "/delete/eip/")
	public String deleteEipPage() {
		return "firewall/deleteEip";
	}

	/**
	 * 删除Eip
	 */
	@RequestMapping(value = "/delete/eip/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "internetIP") String internetIP,
			@RequestParam(value = "isp") Integer isp, @RequestParam(value = "privateIP") String privateIP,
			@RequestParam(value = "protocolTexts") String[] protocolTexts,
			@RequestParam(value = "sourcePorts") String[] sourcePorts,
			@RequestParam(value = "targetPorts") String[] targetPorts, RedirectAttributes redirectAttributes) {

		ArrayList<EIPPolicyParameter> policies = new ArrayList<>();

		for (int i = 0; i < targetPorts.length; i++) {
			EIPPolicyParameter policyParameter = new EIPPolicyParameter();
			policyParameter.setProtocolText(protocolTexts[i]);
			policyParameter.setSourcePort(Integer.valueOf(sourcePorts[i]));
			policyParameter.setTargetPort(Integer.valueOf(targetPorts[i]));
			policies.add(policyParameter);
		}

		EIPParameter eipParameter = new EIPParameter();
		eipParameter.setInternetIP(internetIP);
		eipParameter.setIsp(isp);
		eipParameter.setPrivateIP(privateIP);
		eipParameter.getPolicies().addAll(policies);

		// 获得所有的策略.必须有个默认的策略,不能为null,否则webservice接口会报错"Fault occurred while processing."
		ArrayList<String> allPolicies = Lists.newArrayList();
		allPolicies.add("119.6.200.219-udp-8080");
		eipParameter.getAllPolicies().addAll(allPolicies);

		String message = "";
		WSResult wsResult = service.deleteEip(eipParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Eip删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/firewall/delete/eip/";
	}

	/**
	 * 跳转到VPN页面
	 */
	@RequestMapping(value = "/create/vpn/")
	public String createVPNPage() {
		return "firewall/createVPN";
	}

	/**
	 * 创建VPN
	 */
	@RequestMapping(value = "/create/vpn/", method = RequestMethod.POST)
	public String createVPN(@RequestParam(value = "firewallPolicyId") Integer firewallPolicyId,
			@RequestParam(value = "vlanId") Integer vlanId, @RequestParam(value = "netMask") String netMask,
			@RequestParam(value = "vpnUser") String vpnUser, @RequestParam(value = "vpnPassword") String vpnPassword,
			@RequestParam(value = "ipaddress") String[] ipaddress, @RequestParam(value = "segments") String[] segments,
			RedirectAttributes redirectAttributes) {

		VPNUserParameter vpnUserParameter = new VPNUserParameter();
		vpnUserParameter.setFirewallPolicyId(firewallPolicyId);
		vpnUserParameter.setVlanId(vlanId);
		vpnUserParameter.setNetMask(netMask);
		vpnUserParameter.setVpnUser(vpnUser);
		vpnUserParameter.setVpnPassword(vpnPassword);
		vpnUserParameter.getSegments().addAll(Arrays.asList(segments));
		vpnUserParameter.getIpaddress().addAll(Arrays.asList(ipaddress));

		String message = "";
		WSResult wsResult = service.createVPNUser(vpnUserParameter);

		if (wsResult.getCode().equals("0")) {
			message = "VPNUser创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/firewall/create/vpn/";
	}

	/**
	 * 跳转到删除VPN页面
	 */
	@RequestMapping(value = "/delete/vpn/")
	public String deleteVPNPage() {
		return "firewall/deleteVPN";
	}

	/**
	 * 删除Eip
	 */
	@RequestMapping(value = "/delete/vpn/", method = RequestMethod.POST)
	public String deleteVPN(@RequestParam(value = "firewallPolicyId") Integer firewallPolicyId,
			@RequestParam(value = "vlanId") Integer vlanId, @RequestParam(value = "netMask") String netMask,
			@RequestParam(value = "vpnUser") String vpnUser, @RequestParam(value = "vpnPassword") String vpnPassword,
			@RequestParam(value = "ipaddress") String[] ipaddress, @RequestParam(value = "segments") String[] segments,
			RedirectAttributes redirectAttributes) {

		VPNUserParameter vpnUserParameter = new VPNUserParameter();
		vpnUserParameter.setFirewallPolicyId(firewallPolicyId);
		vpnUserParameter.setVlanId(vlanId);
		vpnUserParameter.setNetMask(netMask);
		vpnUserParameter.setVpnUser(vpnUser);
		vpnUserParameter.setVpnPassword(vpnPassword);
		vpnUserParameter.getSegments().addAll(Arrays.asList(segments));
		vpnUserParameter.getIpaddress().addAll(Arrays.asList(ipaddress));

		String message = "";
		WSResult wsResult = service.deleteVPNUser(vpnUserParameter);

		if (wsResult.getCode().equals("0")) {
			message = "VPNUser删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/firewall/delete/vpn/";
	}

	/**
	 * 跳转到修改VPN页面
	 */
	@RequestMapping(value = "/change/vpn/")
	public String changeVPNPage() {
		return "firewall/changeVPN";
	}

	/**
	 * 修改Eip
	 */
	@RequestMapping(value = "/change/vpn/", method = RequestMethod.POST)
	public String changeVPN(@RequestParam(value = "firewallPolicyId") Integer firewallPolicyId,
			@RequestParam(value = "vlanId") Integer vlanId, @RequestParam(value = "netMask") String netMask,
			@RequestParam(value = "vpnUser") String vpnUser, @RequestParam(value = "vpnPassword") String vpnPassword,
			@RequestParam(value = "ipaddress") String[] ipaddress, @RequestParam(value = "segments") String[] segments,
			RedirectAttributes redirectAttributes) {

		VPNUserParameter vpnUserParameter = new VPNUserParameter();
		vpnUserParameter.setFirewallPolicyId(firewallPolicyId);
		vpnUserParameter.setVlanId(vlanId);
		vpnUserParameter.setNetMask(netMask);
		vpnUserParameter.setVpnUser(vpnUser);
		vpnUserParameter.setVpnPassword(vpnPassword);
		vpnUserParameter.getSegments().addAll(Arrays.asList(segments));
		vpnUserParameter.getIpaddress().addAll(Arrays.asList(ipaddress));

		String message = "";
		WSResult wsResult = service.changeVPNUserAccesssAddress(vpnUserParameter);

		if (wsResult.getCode().equals("0")) {
			message = "VPNUser修改成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/firewall/change/vpn/";
	}

}
