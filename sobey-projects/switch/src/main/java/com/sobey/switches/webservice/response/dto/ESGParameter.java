package com.sobey.switches.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.switches.constans.WsConstants;

/**
 * 交换机上,执行脚本所需参数的ESG对象:
 * 
 * <pre>
 * aclNumber	acl编号(3000-3999)
 * vlanId		Vlan编号
 * desc		描述
 * permits		许可ip列表(数量不固定)
 * denys		拒绝ip列表(数量不固定)
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "ESGParameter")
@XmlType(name = "ESGParameter", namespace = WsConstants.NS)
public class ESGParameter {

	/**
	 * acl编号(3000-3999)
	 */
	private Integer aclNumber;

	/**
	 * vlan编号
	 */
	private Integer vlanId;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 许可ip列表(必须有,数量不固定)
	 */
	private ArrayList<RuleParameter> permits;

	/**
	 * 拒绝ip列表(必须有,数量不固定)
	 */
	private ArrayList<RuleParameter> denys;

	public Integer getAclNumber() {
		return aclNumber;
	}

	public void setAclNumber(Integer aclNumber) {
		this.aclNumber = aclNumber;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ArrayList<RuleParameter> getPermits() {
		return permits;
	}

	public void setPermits(ArrayList<RuleParameter> permits) {
		this.permits = permits;
	}

	public ArrayList<RuleParameter> getDenys() {
		return denys;
	}

	public void setDenys(ArrayList<RuleParameter> denys) {
		this.denys = denys;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
