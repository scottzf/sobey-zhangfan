package com.sobey.generate.switches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * RuleParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RuleParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinationNetMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceNetMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuleParameter", propOrder = { "destination", "destinationNetMask", "source", "sourceNetMask" })
public class RuleParameter {

	protected String destination;
	protected String destinationNetMask;
	protected String source;
	protected String sourceNetMask;

	/**
	 * 获取destination属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 设置destination属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDestination(String value) {
		this.destination = value;
	}

	/**
	 * 获取destinationNetMask属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDestinationNetMask() {
		return destinationNetMask;
	}

	/**
	 * 设置destinationNetMask属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDestinationNetMask(String value) {
		this.destinationNetMask = value;
	}

	/**
	 * 获取source属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 设置source属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSource(String value) {
		this.source = value;
	}

	/**
	 * 获取sourceNetMask属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSourceNetMask() {
		return sourceNetMask;
	}

	/**
	 * 设置sourceNetMask属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSourceNetMask(String value) {
		this.sourceNetMask = value;
	}

}
