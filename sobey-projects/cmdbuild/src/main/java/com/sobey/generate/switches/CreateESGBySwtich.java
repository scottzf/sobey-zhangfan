package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * createESGBySwtich complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="createESGBySwtich">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esgParameter" type="{http://switches.generate.sobey.com}ESGParameter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createESGBySwtich", propOrder = { "esgParameter" })
public class CreateESGBySwtich {

	protected ESGParameter esgParameter;

	/**
	 * 获取esgParameter属性的值。
	 * 
	 * @return possible object is {@link ESGParameter }
	 * 
	 */
	public ESGParameter getEsgParameter() {
		return esgParameter;
	}

	/**
	 * 设置esgParameter属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link ESGParameter }
	 * 
	 */
	public void setEsgParameter(ESGParameter value) {
		this.esgParameter = value;
	}

}
