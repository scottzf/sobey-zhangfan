package com.sobey.nagios.constans;

/**
 * nagios表中监控参数的Id,请在表"nagios_objects"查看.
 * 
 * @author Administrator
 * 
 */
public enum NagiosItemEnum {

	Current_Load("Current Load"), Current_Users("Current Users"), Ping("PING"), Root_Partition("Root Partition"), Swap_Usage(
			"Swap Usage"), Total_Processes("Total Processes"), CPU_Num("cpu_num"), DiskIO("diskIO"), Eth("eth"), Mem_Size(
			"mem_size"), System_OS("system_os"), Uptime("Uptime");

	private String itemName;

	private NagiosItemEnum(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return String.valueOf(this.itemName);
	}

}
