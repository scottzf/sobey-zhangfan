package com.sobey.generate.firewall;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * VPNUserParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="VPNUserParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firewallPolicyId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ipaddress" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="netMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="segments" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vlanId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vpnPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vpnUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VPNUserParameter", propOrder = { "firewallPolicyId", "ipaddress", "netMask", "segments", "vlanId",
		"vpnPassword", "vpnUser" })
public class VPNUserParameter {

	protected Integer firewallPolicyId;
	@XmlElement(nillable = true)
	protected List<String> ipaddress;
	protected String netMask;
	@XmlElement(nillable = true)
	protected List<String> segments;
	protected Integer vlanId;
	protected String vpnPassword;
	protected String vpnUser;

	/**
	 * 获取firewallPolicyId属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getFirewallPolicyId() {
		return firewallPolicyId;
	}

	/**
	 * 设置firewallPolicyId属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setFirewallPolicyId(Integer value) {
		this.firewallPolicyId = value;
	}

	/**
	 * Gets the value of the ipaddress property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the ipaddress property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getIpaddress().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getIpaddress() {
		if (ipaddress == null) {
			ipaddress = new ArrayList<String>();
		}
		return this.ipaddress;
	}

	/**
	 * 获取netMask属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNetMask() {
		return netMask;
	}

	/**
	 * 设置netMask属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNetMask(String value) {
		this.netMask = value;
	}

	/**
	 * Gets the value of the segments property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the segments property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSegments().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getSegments() {
		if (segments == null) {
			segments = new ArrayList<String>();
		}
		return this.segments;
	}

	/**
	 * 获取vlanId属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getVlanId() {
		return vlanId;
	}

	/**
	 * 设置vlanId属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setVlanId(Integer value) {
		this.vlanId = value;
	}

	/**
	 * 获取vpnPassword属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVpnPassword() {
		return vpnPassword;
	}

	/**
	 * 设置vpnPassword属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVpnPassword(String value) {
		this.vpnPassword = value;
	}

	/**
	 * 获取vpnUser属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVpnUser() {
		return vpnUser;
	}

	/**
	 * 设置vpnUser属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVpnUser(String value) {
		this.vpnUser = value;
	}

}
