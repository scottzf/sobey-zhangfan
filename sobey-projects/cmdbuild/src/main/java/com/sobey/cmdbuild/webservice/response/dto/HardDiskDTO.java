package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "HardDiskDTO", namespace = WsConstants.NS)
public class HardDiskDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private String description;
	private Integer fimas;
	private Integer hardDiskSize;
	private Integer id;
	private Integer idc;
	private String remark;
	private Integer rotationalSpeed;
	private Integer server;

	private IdcDTO idcDTO;
	private ServerDTO serverDTO;
	private FimasDTO fimasDTO;
	private String rotationalSpeedText;
	private String brandText;
	
	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public ServerDTO getServerDTO() {
		return serverDTO;
	}

	public void setServerDTO(ServerDTO serverDTO) {
		this.serverDTO = serverDTO;
	}

	public FimasDTO getFimasDTO() {
		return fimasDTO;
	}

	public void setFimasDTO(FimasDTO fimasDTO) {
		this.fimasDTO = fimasDTO;
	}

	public String getRotationalSpeedText() {
		return rotationalSpeedText;
	}

	public void setRotationalSpeedText(String rotationalSpeedText) {
		this.rotationalSpeedText = rotationalSpeedText;
	}

	public String getBrandText() {
		return brandText;
	}

	public void setBrandText(String brandText) {
		this.brandText = brandText;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Integer getBrand() {
		return brand;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getFimas() {
		return fimas;
	}

	public Integer getHardDiskSize() {
		return hardDiskSize;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getRotationalSpeed() {
		return rotationalSpeed;
	}

	public Integer getServer() {
		return server;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
	}

	public void setHardDiskSize(Integer hardDiskSize) {
		this.hardDiskSize = hardDiskSize;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRotationalSpeed(Integer rotationalSpeed) {
		this.rotationalSpeed = rotationalSpeed;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}