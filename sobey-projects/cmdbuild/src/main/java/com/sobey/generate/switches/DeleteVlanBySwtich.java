package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * deleteVlanBySwtich complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deleteVlanBySwtich">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "deleteVlanBySwtich", propOrder = { "vlanId" })
public class DeleteVlanBySwtich {

	protected Integer vlanId;

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
