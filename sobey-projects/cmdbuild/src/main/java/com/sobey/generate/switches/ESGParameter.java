package com.sobey.generate.switches;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * ESGParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ESGParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aclNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="denys" type="{http://switches.generate.sobey.com}RuleParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permits" type="{http://switches.generate.sobey.com}RuleParameter" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ESGParameter", propOrder = { "aclNumber", "denys", "desc", "permits", "vlanId" })
public class ESGParameter {

	protected Integer aclNumber;
	@XmlElement(nillable = true)
	protected List<RuleParameter> denys;
	protected String desc;
	@XmlElement(nillable = true)
	protected List<RuleParameter> permits;
	protected Integer vlanId;

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

	/**
	 * Gets the value of the denys property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the denys property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDenys().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link RuleParameter }
	 * 
	 * 
	 */
	public List<RuleParameter> getDenys() {
		if (denys == null) {
			denys = new ArrayList<RuleParameter>();
		}
		return this.denys;
	}

	/**
	 * 获取desc属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置desc属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDesc(String value) {
		this.desc = value;
	}

	/**
	 * Gets the value of the permits property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the permits property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPermits().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link RuleParameter }
	 * 
	 * 
	 */
	public List<RuleParameter> getPermits() {
		if (permits == null) {
			permits = new ArrayList<RuleParameter>();
		}
		return this.permits;
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
