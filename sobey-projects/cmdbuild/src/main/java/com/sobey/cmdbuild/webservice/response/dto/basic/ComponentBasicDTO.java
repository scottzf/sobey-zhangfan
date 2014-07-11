package com.sobey.cmdbuild.webservice.response.dto.basic;

import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.StorageDTO;

public abstract class ComponentBasicDTO extends BasicDTO {

	protected Integer brand;
	protected String brandText;
	protected String gdzcSn;
	protected Integer idc;
	protected IdcDTO idcDTO;
	protected Integer rack;
	protected RackDTO rackDTO;
	protected Integer server;
	protected ServerDTO serverDTO;
	protected String site;
	protected String sn;
	protected Integer storage;
	protected StorageDTO storageDTO;

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public String getBrandText() {
		return brandText;
	}

	public void setBrandText(String brandText) {
		this.brandText = brandText;
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

	public Integer getServer() {
		return server;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public ServerDTO getServerDTO() {
		return serverDTO;
	}

	public void setServerDTO(ServerDTO serverDTO) {
		this.serverDTO = serverDTO;
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

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public StorageDTO getStorageDTO() {
		return storageDTO;
	}

	public void setStorageDTO(StorageDTO storageDTO) {
		this.storageDTO = storageDTO;
	}

}
