package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * deleteESGBySwtich complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deleteESGBySwtich">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aclNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteESGBySwtich", propOrder = { "aclNumber" })
public class DeleteESGBySwtich {

	protected Integer aclNumber;

	/**
	 * 获取aclNumber属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getAclNumber() {
		return aclNumber;
	}

	/**
	 * 设置aclNumber属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setAclNumber(Integer value) {
		this.aclNumber = value;
	}

}
