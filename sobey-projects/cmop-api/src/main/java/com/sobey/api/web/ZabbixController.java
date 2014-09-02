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

@Controller
@RequestMapping(value = "/zabbix")
public class ZabbixController {

	@Autowired
	private ApiService service;

	@ModelAttribute("ecsList")
	public List<EcsDTO> ecsList() {
		return service.getEcsDTO();
	}

	/**
	 * 跳转到Vlan页面
	 */
	@RequestMapping(value = "/")
	public String zabbixPage() {
		return "zabbix/summary";
	}

	/**
	 * 在核心交换机创建一个Vlan
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String zabbix(@RequestParam(value = "ecsId") Integer ecsId, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("CPU_load_avg1",
				"CPU Load 一分钟内平均负载:" + service.getCurrentData(ecsId, ItemEnum.CPU_load_avg1.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.CPU_load_avg1.getName()).getUnits());

		redirectAttributes.addFlashAttribute("CPU_load_avg5",
				"CPU Load 五分钟内平均负载:" + service.getCurrentData(ecsId, ItemEnum.CPU_load_avg5.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.CPU_load_avg5.getName()).getUnits());

		redirectAttributes.addFlashAttribute("CPU_load_avg15",
				"CPU Load 十五分钟内平均负载:" + service.getCurrentData(ecsId, ItemEnum.CPU_load_avg15.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.CPU_load_avg15.getName()).getUnits());

		redirectAttributes.addFlashAttribute("CPU_load_avg15",
				"CPU Load 十五分钟内平均负载:" + service.getCurrentData(ecsId, ItemEnum.CPU_load_avg15.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.CPU_load_avg15.getName()).getUnits());

		redirectAttributes.addFlashAttribute("traffic_in",
				"Traffic in:" + service.getCurrentData(ecsId, ItemEnum.traffic_in.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.traffic_in.getName()).getUnits());

		redirectAttributes.addFlashAttribute("traffic_out",
				"Traffic out:" + service.getCurrentData(ecsId, ItemEnum.traffic_out.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.traffic_out.getName()).getUnits());

		redirectAttributes.addFlashAttribute("Free_disk_space_on",
				"磁盘可用空间:" + service.getCurrentData(ecsId, ItemEnum.Free_disk_space_on_boot.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.Free_disk_space_on_boot.getName()).getUnits());

		redirectAttributes.addFlashAttribute("Total_disk_space_on",
				"磁盘总大小:" + service.getCurrentData(ecsId, ItemEnum.Total_disk_space_on_boot.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.Total_disk_space_on_boot.getName()).getUnits());

		redirectAttributes.addFlashAttribute("Available_memory",
				"内存大小:" + service.getCurrentData(ecsId, ItemEnum.Available_memory.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.Available_memory.getName()).getUnits());

		redirectAttributes.addFlashAttribute(
				"check_readk",
				"SDA的读性能:" + service.getCurrentData(ecsId, ItemEnum.SDA的读性能.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.SDA的读性能.getName()).getUnits());

		redirectAttributes.addFlashAttribute(
				"check_writek",
				"SDA的写性能:" + service.getCurrentData(ecsId, ItemEnum.SDA的写性能.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.SDA的写性能.getName()).getUnits());

		redirectAttributes.addFlashAttribute(
				"check_tps",
				"SDA的tps:" + service.getCurrentData(ecsId, ItemEnum.SDA的tps.getName()).getLastValue() + " "
						+ service.getCurrentData(ecsId, ItemEnum.SDA的tps.getName()).getUnits());

		return "redirect:/zabbix/";
	}

}
