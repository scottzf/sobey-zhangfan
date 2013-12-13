package com.sobey.generate.firewall;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * deleteVPNUserByFirewall complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deleteVPNUserByFirewall">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://firewall.generate.sobey.com}VPNUserParameter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteVPNUserByFirewall", propOrder = { "vpnUserParameter" })
public class DeleteVPNUserByFirewall {

	@XmlElement(name = "VPNUserParameter", namespace = "http://firewall.generate.sobey.com")
	protected VPNUserParameter vpnUserParameter;

	/**
	 * 获取vpnUserParameter属性的值。
	 * 
	 * @return possible object is {@link VPNUserParameter }
	 * 
	 */
	public VPNUserParameter getVPNUserParameter() {
		return vpnUserParameter;
	}

	/**
	 * 设置vpnUserParameter属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link VPNUserParameter }
	 * 
	 */
	public void setVPNUserParameter(VPNUserParameter value) {
		this.vpnUserParameter = value;
	}

}
