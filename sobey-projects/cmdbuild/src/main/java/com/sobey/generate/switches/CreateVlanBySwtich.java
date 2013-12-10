package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * createVlanBySwtich complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="createVlanBySwtich">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vlanParameter" type="{http://switches.generate.sobey.com}VlanParameter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createVlanBySwtich", propOrder = { "vlanParameter" })
public class CreateVlanBySwtich {

	protected VlanParameter vlanParameter;

	/**
	 * 获取vlanParameter属性的值。
	 * 
	 * @return possible object is {@link VlanParameter }
	 * 
	 */
	public VlanParameter getVlanParameter() {
		return vlanParameter;
	}

	/**
	 * 设置vlanParameter属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link VlanParameter }
	 * 
	 */
	public void setVlanParameter(VlanParameter value) {
		this.vlanParameter = value;
	}

}
