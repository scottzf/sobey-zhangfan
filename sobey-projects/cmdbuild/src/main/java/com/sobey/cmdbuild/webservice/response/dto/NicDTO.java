package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "NicDTO", namespace = WsConstants.NS)
public class NicDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private String description;
	private Integer fimas;

	private Integer id;
	private Integer idc;
	private Integer nicRate;
	private Integer portNumber;
	private String remark;
	private Integer server;

	private IdcDTO idcDTO;
	private RackDTO rackDTO;
	private ServerDTO serverDTO;
	private String brandText;
	private String nicRateText;

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public RackDTO getRackDTO() {
		return rackDTO;
	}

	public void setRackDTO(RackDTO rackDTO) {
		this.rackDTO = rackDTO;
	}

	public ServerDTO getServerDTO() {
		return serverDTO;
	}

	public void setServerDTO(ServerDTO serverDTO) {
		this.serverDTO = serverDTO;
	}

	public String getBrandText() {
		return brandText;
	}

	public void setBrandText(String brandText) {
		this.brandText = brandText;
	}

	public String getNicRateText() {
		return nicRateText;
	}

	public void setNicRateText(String nicRateText) {
		this.nicRateText = nicRateText;
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

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public Integer getNicRate() {
		return nicRate;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public String getRemark() {
		return remark;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setNicRate(Integer nicRate) {
		this.nicRate = nicRate;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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