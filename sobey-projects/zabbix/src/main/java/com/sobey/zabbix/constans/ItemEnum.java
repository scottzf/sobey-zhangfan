package com.sobey.zabbix.constans;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * zabbix 监控项目及键值
 * 
 * @author Administrator
 *
 */
public enum ItemEnum {

	系统盘已用空间("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,used]"),

	系统盘总大小("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,total]"),

	系统盘占用率("vmware.vm.vfs.fs.size[{$URL},{HOST.HOST},/,pfree]"),

	CPU使用情况("vmware.vm.cpu.usage[{$URL},{HOST.HOST}]"),

	内存使用情况("vmware.vm.memory.size.usage.guest[{$URL},{HOST.HOST}]"),

	读速率("vfs.dev.read[/dev/sda,sps,]"),

	写速率("vfs.dev.write[/dev/sda,sps,] "),

	网络流量out("net.if.out[eth1]"),

	网络流量in("net.if.in[eth1]");

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
