package com.sobey.generate.firewall;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * deleteVPNUserByFirewallResponse complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deleteVPNUserByFirewallResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://firewall.generate.sobey.com}WSResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteVPNUserByFirewallResponse", propOrder = { "_return" })
public class DeleteVPNUserByFirewallResponse {

	@XmlElement(name = "return")
	protected WSResult _return;

	/**
	 * 获取return属性的值。
	 * 
	 * @return possible object is {@link WSResult }
	 * 
	 */
	public WSResult getReturn() {
		return _return;
	}

	/**
	 * 设置return属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link WSResult }
	 * 
	 */
	public void setReturn(WSResult value) {
		this._return = value;
	}

}
