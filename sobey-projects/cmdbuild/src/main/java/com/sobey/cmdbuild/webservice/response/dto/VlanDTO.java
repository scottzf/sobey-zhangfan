package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "VlanDTO")
@XmlType(name = "VlanDTO", namespace = WsConstants.NS)
public class VlanDTO extends BasicDTO {

	private String gateway;
	private Integer idc;
	private IdcDTO idcDTO;
	private String netMask;
	private Integer nic;
	private NicDTO nicDTO;
	private String remark;
	private String segment;
	private Integer subnet;
	private SubnetDTO subnetDTO;
	private Integer tenants;
	private TenantsDTO tenantsDTO;
	private Integer vlanId;

	public String getGateway() {
		return gateway;
	}

	public Integer getIdc() {
		return idc;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public String getNetMask() {
		return netMask;
	}

	public Integer getNic() {
		return nic;
	}

	public NicDTO getNicDTO() {
		return nicDTO;
	}

	public String getRemark() {
		return remark;
	}

	public String getSegment() {
		return segment;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public SubnetDTO getSubnetDTO() {
		return subnetDTO;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setNic(Integer nic) {
		this.nic = nic;
	}

	public void setNicDTO(NicDTO nicDTO) {
		this.nicDTO = nicDTO;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	public void setSubnetDTO(SubnetDTO subnetDTO) {
		this.subnetDTO = subnetDTO;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}