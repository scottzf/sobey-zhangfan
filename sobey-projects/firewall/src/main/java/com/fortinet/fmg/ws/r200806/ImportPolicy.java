
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>importPolicy complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="importPolicy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="adomName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adomOid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="devName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="devId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vdomName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vdomId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="policyPackageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importPolicy", propOrder = {
    "servicePass",
    "adomName",
    "adomOid",
    "devName",
    "devId",
    "vdomName",
    "vdomId",
    "policyPackageName"
})
public class ImportPolicy {

    protected ServicePass servicePass;
    protected String adomName;
    protected Integer adomOid;
    protected String devName;
    protected Integer devId;
    protected String vdomName;
    protected Integer vdomId;
    protected String policyPackageName;

    /**
     * 获取servicePass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ServicePass }
     *     
     */
    public ServicePass getServicePass() {
        return servicePass;
    }

    /**
     * 设置servicePass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePass }
     *     
     */
    public void setServicePass(ServicePass value) {
        this.servicePass = value;
    }

    /**
     * 获取adomName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdomName() {
        return adomName;
    }

    /**
     * 设置adomName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdomName(String value) {
        this.adomName = value;
    }

    /**
     * 获取adomOid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAdomOid() {
        return adomOid;
    }

    /**
     * 设置adomOid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAdomOid(Integer value) {
        this.adomOid = value;
    }

    /**
     * 获取devName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevName() {
        return devName;
    }

    /**
     * 设置devName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevName(String value) {
        this.devName = value;
    }

    /**
     * 获取devId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDevId() {
        return devId;
    }

    /**
     * 设置devId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDevId(Integer value) {
        this.devId = value;
    }

    /**
     * 获取vdomName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVdomName() {
        return vdomName;
    }

    /**
     * 设置vdomName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVdomName(String value) {
        this.vdomName = value;
    }

    /**
     * 获取vdomId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVdomId() {
        return vdomId;
    }

    /**
     * 设置vdomId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVdomId(Integer value) {
        this.vdomId = value;
    }

    /**
     * 获取policyPackageName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyPackageName() {
        return policyPackageName;
    }

    /**
     * 设置policyPackageName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyPackageName(String value) {
        this.policyPackageName = value;
    }

}
