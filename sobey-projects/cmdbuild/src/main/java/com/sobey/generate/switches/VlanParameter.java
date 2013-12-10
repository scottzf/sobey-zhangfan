package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * VlanParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="VlanParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gateway" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="netMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vlanId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VlanParameter", propOrder = { "gateway", "netMask", "vlanId" })
public class VlanParameter {

	protected String gateway;
	protected String netMask;
	protected Integer vlanId;

	/**
	 * 获取gateway属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * 设置gateway属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setGateway(String value) {
		this.gateway = value;
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

}
