package com.sobey.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.api.constans.ItemEnum;
import com.sobey.api.service.ApiService;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.Es3DTO;

@Controller
@RequestMapping(value = "/zabbix")
public class ZabbixController {

	@Autowired
	private ApiService service;

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	@ModelAttribute("es3List")
	public List<Es3DTO> es3List() {
		return service.getEs3DTO();
	}

	@ModelAttribute("itemList")
	public ItemEnum[] itemList() {
		return ItemEnum.values();
	}

	/**
	 * 跳转到current页面
	 */
	@RequestMapping(value = "/current/")
	public String currentPage() {
		return "zabbix/current";
	}

	/**
	 * 返回最新监控数据
	 */
	@RequestMapping(value = "/current/", method = RequestMethod.POST)
	public String current(@RequestParam(value = "ecsId") Integer ecsId, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("traffic_in",
				"Traffic in:" + service.getCurrentData(ecsId, ItemEnum.网络流量in.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.网络流量in.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("traffic_out",
				"Traffic out:" + service.getCurrentData(ecsId, ItemEnum.网络流量out.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.网络流量out.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("Free_disk_space_on",
				"磁盘可用空间:" + service.getCurrentData(ecsId, ItemEnum.系统盘已用空间.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.系统盘已用空间.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("Total_disk_space_on",
				"磁盘总大小:" + service.getCurrentData(ecsId, ItemEnum.系统盘总大小.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.系统盘总大小.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("Available_memory",
				"内存大小:" + service.getCurrentData(ecsId, ItemEnum.内存可用大小.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.内存可用大小.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("check_readk",
				"SDA的读性能:" + service.getCurrentData(ecsId, ItemEnum.读性能.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.读性能.getValue()).getUnits());

		redirectAttributes.addFlashAttribute("check_writek",
				"SDA的写性能:" + service.getCurrentData(ecsId, ItemEnum.写性能.getValue()).getValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.写性能.getValue()).getUnits());

		return "redirect:/zabbix/current/";
	}

	/**
	 * 跳转到history页面
	 */
	@RequestMapping(value = "/history/")
	public String historyPage() {
		return "zabbix/history";
	}

	@RequestMapping(value = "/history/", method = RequestMethod.POST)
	public String history(@RequestParam(value = "ecsId") Integer ecsId,
			@RequestParam(value = "itemKey") String itemKey, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("historyData", service.getHistoryData(ecsId, itemKey).getZItemDTOs());
		redirectAttributes.addFlashAttribute("itemKey", itemKey);

		return "redirect:/zabbix/history/";
	}

	/**
	 * 跳转到storage用量页面
	 */
	@RequestMapping(value = "/volume/")
	public String volumePage() {
		return "zabbix/volume";
	}

	@RequestMapping(value = "/volume/", method = RequestMethod.POST)
	public String volume(@RequestParam(value = "es3Id") Integer es3Id, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("volumeData", service.getVolumeData(es3Id));
		redirectAttributes.addFlashAttribute("volumeDataPre", service.getVolumeDataPre(es3Id));
		return "redirect:/zabbix/volume/";
	}
}
