package com.sobey.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.LoadbalancerService;
import com.sobey.api.utils.NetworkUtil;
import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.loadbalancer.ELBPolicyParameter;
import com.sobey.generate.loadbalancer.ELBPublicIPParameter;
import com.sobey.generate.loadbalancer.WSResult;

/**
 * Loadbalancer 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/loadbalancer")
public class LoadbalancerController {

	@Autowired
	private LoadbalancerService service;

	/**
	 * 跳转到Loadbalancer页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "loadbalancer/create";
	}

	/**
	 * 创建一个Loadbalancer
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "vip") String vip,
			@RequestParam(value = "publicIPs") String[] publicIPs,
			@RequestParam(value = "protocols") String[] protocols, RedirectAttributes redirectAttributes) {

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();

		for (int i = 0; i < protocols.length; i++) {

			ELBPolicyParameter policyParameter = new ELBPolicyParameter();
			policyParameter.setProtocolText(protocols[i]);
			policyParameter.setSourcePort(NetworkUtil.getPortFromProtocol(protocols[i]));
			policyParameter.setTargetPort(NetworkUtil.getPortFromProtocol(protocols[i]));

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();
			publicIPParameter.setIpaddress(publicIPs[i]);
			publicIPParameter.getPolicyParameters().add(policyParameter);

			publicIPParameters.add(publicIPParameter);
		}

		ELBParameter elbParameter = new ELBParameter();
		elbParameter.setVip(vip);
		elbParameter.getPublicIPs().addAll(publicIPParameters);

		String message = "";
		WSResult wsResult = service.createElb(elbParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Elb创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/loadbalancer/create/";
	}

	/**
	 * 跳转到删除Loadbalancer页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "loadbalancer/delete";
	}

	/**
	 * 删除Loadbalancer
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "vip") String vip,
			@RequestParam(value = "publicIPs") String[] publicIPs,
			@RequestParam(value = "protocols") String[] protocols, RedirectAttributes redirectAttributes) {

		List<ELBPublicIPParameter> publicIPParameters = new ArrayList<ELBPublicIPParameter>();

		for (int i = 0; i < protocols.length; i++) {

			ELBPolicyParameter policyParameter = new ELBPolicyParameter();
			policyParameter.setProtocolText(protocols[i]);
			policyParameter.setSourcePort(NetworkUtil.getPortFromProtocol(protocols[i]));
			policyParameter.setTargetPort(NetworkUtil.getPortFromProtocol(protocols[i]));

			ELBPublicIPParameter publicIPParameter = new ELBPublicIPParameter();
			publicIPParameter.setIpaddress(publicIPs[i]);
			publicIPParameter.getPolicyParameters().add(policyParameter);

			publicIPParameters.add(publicIPParameter);
		}

		ELBParameter elbParameter = new ELBParameter();
		elbParameter.setVip(vip);
		elbParameter.getPublicIPs().addAll(publicIPParameters);

		String message = "";
		WSResult wsResult = service.deleteElb(elbParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Elb删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/loadbalancer/delete/";
	}

}
