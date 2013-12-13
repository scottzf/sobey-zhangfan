package com.sobey.generate.firewall;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * createEIPByFirewall complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="createEIPByFirewall">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://firewall.generate.sobey.com}EIPParameter" minOccurs="0"/>
 *         &lt;element name="allPolicies" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createEIPByFirewall", propOrder = { "eipParameter", "allPolicies" })
public class CreateEIPByFirewall {

	@XmlElement(name = "EIPParameter", namespace = "http://firewall.generate.sobey.com")
	protected EIPParameter eipParameter;
	protected List<String> allPolicies;

	/**
	 * 获取eipParameter属性的值。
	 * 
	 * @return possible object is {@link EIPParameter }
	 * 
	 */
	public EIPParameter getEIPParameter() {
		return eipParameter;
	}

	/**
	 * 设置eipParameter属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link EIPParameter }
	 * 
	 */
	public void setEIPParameter(EIPParameter value) {
		this.eipParameter = value;
	}

	/**
	 * Gets the value of the allPolicies property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the allPolicies property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAllPolicies().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getAllPolicies() {
		if (allPolicies == null) {
			allPolicies = new ArrayList<String>();
		}
		return this.allPolicies;
	}

}
