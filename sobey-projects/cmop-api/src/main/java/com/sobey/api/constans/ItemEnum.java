package com.sobey.api.constans;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * zabbix 监控项目及键值(linux)
 * 
 * 
 * @author Administrator
 *
 */
public enum ItemEnum {

	// 系统盘已用空间("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,used]"),
	// 系统盘总大小("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,total]"),

	Disk("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,pfree]"),

	CPU("vmware.vm.cpu.usage[{$URL},{HOST.HOST}]"),

	Memory("vmware.vm.memory.size.usage.guest[{$URL},{HOST.HOST}]"),

	Disk_Read("vfs.dev.read[/dev/sda,sps,]"),

	Disk_Wirte("vfs.dev.write[/dev/sda,sps,] "),

	Network_Out("net.if.out[eth1]"),

	Network_In("net.if.in[eth1]");

	public static final Map<String, String> map = Maps.newLinkedHashMap();

	static {
		for (ItemEnum e : ItemEnum.values()) {

			map.put(e.name(), e.value);

		}
	}

	private String value;

	private ItemEnum(String name) {
		this.value = name;
	}

	public String getValue() {
		return value;
	}
}
