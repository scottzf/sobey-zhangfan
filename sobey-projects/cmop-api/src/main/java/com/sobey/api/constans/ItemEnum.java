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

	Disk("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,pfree]"),

	CPU("vmware.vm.cpu.usage[{$URL},{HOST.HOST}]"),

	Memory("vmware.vm.memory.size.usage.host[{$URL},{HOST.HOST}]"),

	Disk_Read("vmware.vm.vfs.dev.read[{$URL},{HOST.HOST},scsi0:0,bps]"),

	Disk_Wirte("vmware.vm.vfs.dev.write[{$URL},{HOST.HOST},scsi0:0,bps]"),

	Network_Out("vmware.vm.net.if.out[{$URL},{HOST.HOST},4000,bps]"),

	Network_In("vmware.vm.net.if.in[{$URL},{HOST.HOST},4000,bps]");

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
