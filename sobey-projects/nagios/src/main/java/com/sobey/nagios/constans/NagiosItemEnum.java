package com.sobey.nagios.constans;

/**
 * nagios表中监控参数的Id,请在表"nagios_objects"查看.
 * 
 * @author Administrator
 * 
 */
public enum NagiosItemEnum {

	Current_Load("1384"), Current_Users("1385"), Ping("1386"), Root_Partition("1387"), Swap_Usage("1388"), Total_Processes(
			"1389"), CPU_Num("1390"), DiskIO("1393"), Eth("1394"), Mem_Size("1395"), System_OS("1396"), Uptime("1397");

	private String itemId;

	private NagiosItemEnum(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.itemId);
	}

}
