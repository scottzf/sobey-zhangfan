package com.sobey.sdn.bean;

/**
 * 冯敬临时迁移所需数据对象 将来删
 * 
 * @author Administrator
 *
 */
public class Fengjing {

	/**
	 * 虚拟机名称
	 */
	private String vmName;

	/**
	 * 主机IP
	 */
	private String hostIp;

	/**
	 * 虚拟机所连标准端口组
	 */
	private String vlan;

	/**
	 * 虚拟机ip地址
	 */
	private String ip;

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
