package com.sobey.cmdbuild.webservice.response.dto.basic;

import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;

public abstract class DeviceBasicDTO extends BasicDTO {

	protected Integer deviceSpec;
	protected DeviceSpecDTO deviceSpecDTO;
	protected String gdzcSn;
	protected Integer idc;
	protected IdcDTO idcDTO;
	protected Integer ipaddress;
	protected IpaddressDTO ipaddressDTO;
	protected Integer rack;
	protected RackDTO rackDTO;
	protected String site;
	protected String sn;

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public DeviceSpecDTO getDeviceSpecDTO() {
		return deviceSpecDTO;
	}

	public void setDeviceSpecDTO(DeviceSpecDTO deviceSpecDTO) {
		this.deviceSpecDTO = deviceSpecDTO;
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

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public Integer getRack() {
		return rack;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public RackDTO getRackDTO() {
		return rackDTO;
	}

	public void setRackDTO(RackDTO rackDTO) {
		this.rackDTO = rackDTO;
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
