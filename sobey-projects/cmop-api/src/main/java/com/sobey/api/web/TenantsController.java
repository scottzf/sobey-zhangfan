package com.sobey.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.ApiService;
import com.sobey.core.utils.Encodes;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.test.data.RandomData;

/**
 * Tenants 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/tenants")
public class TenantsController {

	@Autowired
	private ApiService service;

	/**
	 * 跳转到tenants页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "tenants/create";
	}

	/**
	 * 创建一个tenants
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "company") String company, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "phone") String phone,

			RedirectAttributes redirectAttributes) {

		TenantsDTO tenantsDTO = new TenantsDTO();
		tenantsDTO.setAccessKey(Encodes.encodeBase64(RandomData.randomName("accesskey").getBytes()));
		tenantsDTO.setCompany(company);
		tenantsDTO.setDescription(email);
		tenantsDTO.setEmail(email);
		tenantsDTO.setPassword(password);
		tenantsDTO.setPhone(phone);

		redirectAttributes.addFlashAttribute("message", service.createTenants(tenantsDTO).getMessage());

		return "redirect:/tenants/create/";
	}

}
