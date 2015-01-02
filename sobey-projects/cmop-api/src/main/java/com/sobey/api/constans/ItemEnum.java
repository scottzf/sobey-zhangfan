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

	系统盘已用空间("vfs.fs.size[/,used]"),

	系统盘总大小("vfs.fs.size[/,total]"),

	系统盘可用空间百分比("vfs.fs.size[/,pfree]"),

	CPU占用率("system.cpu.util[,idle]"),

	内存可用大小("vm.memory.size[available]"),

	内存总大小("vm.memory.size[total]"),

	读性能("check_readk"),

	写性能("check_writek"),

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
