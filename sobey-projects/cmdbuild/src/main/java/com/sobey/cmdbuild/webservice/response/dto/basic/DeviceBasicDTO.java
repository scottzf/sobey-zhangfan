package com.sobey.cmdbuild.webservice.response.dto.basic;


public abstract class DeviceBasicDTO extends BasicDTO {

	protected Integer deviceSpec;
	protected String gdzcSn;
	protected Integer idc;
	protected Integer ipaddress;
	protected Integer rack;
	protected String site;
	protected String sn;
	public Integer getDeviceSpec() {
		return deviceSpec;
	}
	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}
	public String getGdzcSn() {
		return gdzcSn;
	}
	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}
	public Integer getIdc() {
		return idc;
	}
	public void setIdc(Integer idc) {
		this.idc = idc;
	}
	public Integer getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}
	public Integer getRack() {
		return rack;
	}
	public void setRack(Integer rack) {
		this.rack = rack;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

 

}
