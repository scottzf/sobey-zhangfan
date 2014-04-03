package com.sobey.api.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.SwitchService;
import com.sobey.generate.switches.ESGParameter;
import com.sobey.generate.switches.RuleParameter;
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

	/**
	 * 跳转到Esg页面
	 */
	@RequestMapping(value = "/create/esg/")
	public String createEsgPage() {
		return "switch/createEsg";
	}

	/**
	 * 创建一个Esg
	 */
	@RequestMapping(value = "/create/esg/", method = RequestMethod.POST)
	public String createEsg(@RequestParam(value = "aclNumber") Integer aclNumber,
			@RequestParam(value = "vlanId") Integer vlanId, @RequestParam(value = "desc") String desc,
			@RequestParam(value = "permitsDestination") String[] permitsDestination,
			@RequestParam(value = "permitsDestinationNetMask") String[] permitsDestinationNetMask,
			@RequestParam(value = "permitsSource") String[] permitsSource,
			@RequestParam(value = "permitsSourceNetMask") String[] permitsSourceNetMask,
			@RequestParam(value = "denysDestination") String[] denysDestination,
			@RequestParam(value = "denysDestinationNetMask") String[] denysDestinationNetMask,
			@RequestParam(value = "denysSource") String[] denysSource,
			@RequestParam(value = "denysSourceNetMask") String[] denysSourceNetMask,
			RedirectAttributes redirectAttributes) {

		ESGParameter esgParameter = new ESGParameter();
		ArrayList<RuleParameter> permits = new ArrayList<>();

		for (int i = 0; i < permitsDestination.length; i++) {
			RuleParameter ruleParameter = new RuleParameter();
			ruleParameter.setDestination(permitsDestination[i]);
			ruleParameter.setDestinationNetMask(permitsDestinationNetMask[i]);
			ruleParameter.setSource(permitsSource[i]);
			ruleParameter.setSourceNetMask(permitsSourceNetMask[i]);
			permits.add(ruleParameter);
		}

		ArrayList<RuleParameter> denys = new ArrayList<>();

		for (int i = 0; i < denysDestination.length; i++) {
			RuleParameter ruleParameter = new RuleParameter();
			ruleParameter.setDestination(denysDestination[i]);
			ruleParameter.setDestinationNetMask(denysDestinationNetMask[i]);
			ruleParameter.setSource(denysSource[i]);
			ruleParameter.setSourceNetMask(denysSourceNetMask[i]);
			denys.add(ruleParameter);
		}

		esgParameter.setAclNumber(aclNumber);
		esgParameter.setVlanId(vlanId);
		esgParameter.setDesc(desc);
		esgParameter.getPermits().addAll(permits);
		esgParameter.getDenys().addAll(denys);

		String message = "";

		WSResult wsResult = service.createEsg(esgParameter);

		if (wsResult.getCode().equals("0")) {
			message = "Esg创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/switch/create/esg/";
	}

	/**
	 * 跳转到删除Esg页面
	 */
	@RequestMapping(value = "/delete/esg/")
	public String deleteEsgPage() {
		return "switch/deleteEsg";
	}

	/**
	 * 删除Esg
	 */
	@RequestMapping(value = "/delete/esg/", method = RequestMethod.POST)
	public String deleteEsg(@RequestParam(value = "aclNumber") Integer aclNumber, RedirectAttributes redirectAttributes) {

		String message = "";

		WSResult wsResult = service.deleteEsg(aclNumber);

		if (wsResult.getCode().equals("0")) {
			message = "Esg删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/switch/delete/esg/";
	}

}
