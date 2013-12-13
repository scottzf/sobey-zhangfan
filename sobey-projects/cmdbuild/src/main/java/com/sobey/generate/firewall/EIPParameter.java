package com.sobey.generate.firewall;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * EIPParameter complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EIPParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="internetIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isp" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="policies" type="{http://firewall.generate.sobey.com}EIPPolicyParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="privateIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EIPParameter", propOrder = { "internetIP", "isp", "policies", "privateIP" })
public class EIPParameter {

	protected String internetIP;
	protected Integer isp;
	@XmlElement(nillable = true)
	protected List<EIPPolicyParameter> policies;
	protected String privateIP;

	/**
	 * 获取internetIP属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInternetIP() {
		return internetIP;
	}

	/**
	 * 设置internetIP属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setInternetIP(String value) {
		this.internetIP = value;
	}

	/**
	 * 获取isp属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getIsp() {
		return isp;
	}

	/**
	 * 设置isp属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setIsp(Integer value) {
		this.isp = value;
	}

	/**
	 * Gets the value of the policies property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
	 * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
	 * the policies property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPolicies().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link EIPPolicyParameter }
	 * 
	 * 
	 */
	public List<EIPPolicyParameter> getPolicies() {
		if (policies == null) {
			policies = new ArrayList<EIPPolicyParameter>();
		}
		return this.policies;
	}

	/**
	 * 获取privateIP属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrivateIP() {
		return privateIP;
	}

	/**
	 * 设置privateIP属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrivateIP(String value) {
		this.privateIP = value;
	}

}
