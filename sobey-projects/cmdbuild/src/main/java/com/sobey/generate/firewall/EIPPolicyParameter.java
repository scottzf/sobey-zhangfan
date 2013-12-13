package com.sobey.generate.firewall;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * EIPPolicyParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EIPPolicyParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protocolText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourcePort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="targetPort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EIPPolicyParameter", propOrder = { "protocolText", "sourcePort", "targetPort" })
public class EIPPolicyParameter {

	protected String protocolText;
	protected Integer sourcePort;
	protected Integer targetPort;

	/**
	 * 获取protocolText属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getProtocolText() {
		return protocolText;
	}

	/**
	 * 设置protocolText属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setProtocolText(String value) {
		this.protocolText = value;
	}

	/**
	 * 获取sourcePort属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getSourcePort() {
		return sourcePort;
	}

	/**
	 * 设置sourcePort属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setSourcePort(Integer value) {
		this.sourcePort = value;
	}

	/**
	 * 获取targetPort属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getTargetPort() {
		return targetPort;
	}

	/**
	 * 设置targetPort属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setTargetPort(Integer value) {
		this.targetPort = value;
	}

}
