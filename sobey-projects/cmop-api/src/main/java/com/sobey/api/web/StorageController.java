package com.sobey.api.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.service.StorageService;
import com.sobey.generate.storage.CreateEs3Parameter;
import com.sobey.generate.storage.DeleteEs3Parameter;
import com.sobey.generate.storage.MountEs3Parameter;
import com.sobey.generate.storage.RemountEs3Parameter;
import com.sobey.generate.storage.UmountEs3Parameter;
import com.sobey.generate.storage.WSResult;

/**
 * Storage 模块
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/storage")
public class StorageController {

	@Autowired
	private StorageService service;

	/**
	 * 跳转到Storage页面
	 */
	@RequestMapping(value = "/create/")
	public String createPage() {
		return "storage/create";
	}

	/**
	 * 创建一个Storage
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	public String create(@RequestParam(value = "volumeName") String volumeName,
			@RequestParam(value = "volumeSize") String volumeSize,
			@RequestParam(value = "clientIPaddress") String clientIPaddress, RedirectAttributes redirectAttributes) {

		CreateEs3Parameter createEs3Parameter = new CreateEs3Parameter();

		createEs3Parameter.setVolumeName(volumeName);
		createEs3Parameter.setVolumeSize(volumeSize);
		createEs3Parameter.setClientIPaddress(clientIPaddress);

		String message = "";

		WSResult wsResult = service.createEs3(createEs3Parameter);

		if (wsResult.getCode().equals("0")) {
			message = "Es3创建成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/storage/create/";
	}

	/**
	 * 跳转到删除Storage页面
	 */
	@RequestMapping(value = "/delete/")
	public String deletePage() {
		return "storage/delete";
	}

	/**
	 * Delete Storage
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "volumeName") String volumeName, RedirectAttributes redirectAttributes) {

		DeleteEs3Parameter deleteEs3Parameter = new DeleteEs3Parameter();
		deleteEs3Parameter.setVolumeName(volumeName);

		String message = "";

		WSResult wsResult = service.deleteEs3(deleteEs3Parameter);

		if (wsResult.getCode().equals("0")) {
			message = "Es3删除成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/storage/delete/";
	}

	/**
	 * 跳转到Mount storage页面
	 */
	@RequestMapping(value = "/mount/")
	public String mountPage() {
		return "storage/mount";
	}

	/**
	 * Mount Storage
	 */
	@RequestMapping(value = "/mount/", method = RequestMethod.POST)
	public String mount(@RequestParam(value = "volumeName") String volumeName,
			@RequestParam(value = "clientIPaddress") String clientIPaddress,
			@RequestParam(value = "netAppIPaddress") String netAppIPaddress, RedirectAttributes redirectAttributes) {

		MountEs3Parameter mountEs3Parameter = new MountEs3Parameter();
		mountEs3Parameter.setVolumeName(volumeName);
		mountEs3Parameter.setClientIPaddress(clientIPaddress);
		mountEs3Parameter.setNetAppIPaddress(netAppIPaddress);

		String message = "";
		WSResult wsResult = service.mountEs3(mountEs3Parameter);

		if (wsResult.getCode().equals("0")) {
			message = "Es3挂载成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/storage/mount/";
	}

	/**
	 * 跳转到Umount Storage页面
	 */
	@RequestMapping(value = "/umount/")
	public String umountPage() {
		return "storage/umount";
	}

	/**
	 * Umount Storage
	 */
	@RequestMapping(value = "/umount/", method = RequestMethod.POST)
	public String umount(@RequestParam(value = "clientIPaddress") String clientIPaddress,
			RedirectAttributes redirectAttributes) {

		UmountEs3Parameter umountEs3Parameter = new UmountEs3Parameter();
		umountEs3Parameter.setClientIPaddress(clientIPaddress);

		String message = "";
		WSResult wsResult = service.umountEs3(umountEs3Parameter);

		if (wsResult.getCode().equals("0")) {
			message = "Es3卸载成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/storage/umount/";
	}

	/**
	 * 跳转到Remount Storage页面
	 */
	@RequestMapping(value = "/remount/")
	public String remountPage() {
		return "storage/remount";
	}

	/**
	 * Remount Storage
	 */
	@RequestMapping(value = "/remount/", method = RequestMethod.POST)
	public String remount(@RequestParam(value = "volumeName") String volumeName,
			@RequestParam(value = "beforeClientIPaddress") String[] beforeClientIPaddress,
			@RequestParam(value = "afterClientIPaddress") String[] afterClientIPaddress,
			RedirectAttributes redirectAttributes) {

		RemountEs3Parameter remountEs3Parameter = new RemountEs3Parameter();
		remountEs3Parameter.setVolumeName(volumeName);
		remountEs3Parameter.getBeforeClientIPaddress().addAll(Arrays.asList(beforeClientIPaddress));
		remountEs3Parameter.getAfterClientIPaddress().addAll(Arrays.asList(afterClientIPaddress));

		String message = "";
		WSResult wsResult = service.remountEs3(remountEs3Parameter);

		if (wsResult.getCode().equals("0")) {
			message = "Es3修改成功";
		} else {
			message = wsResult.getMessage();
		}

		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/storage/remount/";
	}

}
