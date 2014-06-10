package com.sobey.zabbix.constans;


/**
 * zabbix 监控项目及键值
 * @author Administrator
 *
 */
public enum ItemEnum {

	Used_disk_space_on("vfs.fs.size[/,used]"),
	Used_disk_space_on_boot("vfs.fs.size[/boot,used]"),
	Total_swap_space("system.swap.size[,total]"),
	Total_memory("	vm.memory.size[total]"),
	Total_disk_space_on("	vfs.fs.size[/,total]"),
	Total_disk_space_on_boot("vfs.fs.size[/boot,total]"),
	System_uptime("system.uptime"),
	System_information("system.uname"),
	SDA的读性能("check_readk"),
	SDA的写性能("check_writek"),
	SDA的tps("check_tps"),
	traffic_out("net.if.out[eth1]"),
	traffic_in("net.if.in[eth1]"),
	Number_of_running_processes("proc.num[,,run]"),
	Number_of_processes("	proc.num[]"),
	Number_of_logged_in_users("system.users.num"),
	Maximum_number_of_processes("kernel.maxproc"),
	Maximum_number_of_opened_files("kernel.maxfiles"),
	Interrupts_per_second("system.cpu.intr"),
	Host_name("system.hostname"),
	Host_local_time("system.localtime"),
	Host_boot_time("system.boottime"),
	Free_swap_space_in_percentage("system.swap.size[,pfree]"),
	Free_swap_space("system.swap.size[,free]"),
	Free_inodes_on_percentage("vfs.fs.inode[/,pfree]"),
	Free_inodes_on_boot_percentage("vfs.fs.inode[/boot,pfree]"),
	Free_disk_space_on_percentage("vfs.fs.size[/,pfree]"),
	Free_disk_space_on_boot_percentage("vfs.fs.size[/boot,pfree]"),
	Free_disk_space_on("vfs.fs.size[/,free]"),
	Free_disk_space_on_boot("vfs.fs.size[/boot,free]"),
	CPU_load_avg15("system.cpu.load[percpu,avg15]"),
	CPU_load_avg5("system.cpu.load[percpu,avg5]"),
	CPU_load_avg1("system.cpu.load[percpu,avg1]"),
	CPU_iowait_time("system.cpu.util[,iowait]"),
	CPU_idle_time("system.cpu.util[,idle]"),
	CPU_interrupt_time("system.cpu.util[,interrupt]"),
	CPU_nice_time("system.cpu.util[,nice]"),
	CPU_system_time("system.cpu.util[,system]"),
	CPU_softirq_time("system.cpu.util[,softirq]"),
	CPU_user_time("system.cpu.util[,user]"),
	CPU_steal_time("system.cpu.util[,steal]"),
	Context_switches_per_second("system.cpu.switches"),
	Checksum_of_etc_passwd("vfs.file.cksum[/etc/passwd]"),
	Available_memory("vm.memory.size[available]"),
	Agent_ping("agent.ping"),
	var_tmp是否可写("check_dir_rw");

	private String name;

	private ItemEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
