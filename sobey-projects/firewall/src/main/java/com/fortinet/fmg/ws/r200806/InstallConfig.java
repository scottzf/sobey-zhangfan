
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>installConfig complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="installConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="to" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pkgoid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *         &lt;element name="devId" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newRevName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instValidate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "installConfig", propOrder = {
    "servicePass",
    "from",
    "to",
    "adom",
    "pkgoid",
    "devId",
    "serialNumber",
    "newRevName",
    "instValidate"
})
public class InstallConfig {

    protected ServicePass servicePass;
    protected String from;
    protected String to;
    protected String adom;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger pkgoid;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger devId;
    protected String serialNumber;
    protected String newRevName;
    protected Boolean instValidate;

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
     * 获取from属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * 设置from属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * 获取to属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTo() {
        return to;
    }

    /**
     * 设置to属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTo(String value) {
        this.to = value;
    }

    /**
     * 获取adom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdom() {
        return adom;
    }

    /**
     * 设置adom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdom(String value) {
        this.adom = value;
    }

    /**
     * 获取pkgoid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPkgoid() {
        return pkgoid;
    }

    /**
     * 设置pkgoid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPkgoid(BigInteger value) {
        this.pkgoid = value;
    }

    /**
     * 获取devId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDevId() {
        return devId;
    }

    /**
     * 设置devId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDevId(BigInteger value) {
        this.devId = value;
    }

    /**
     * 获取serialNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置serialNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * 获取newRevName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewRevName() {
        return newRevName;
    }

    /**
     * 设置newRevName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewRevName(String value) {
        this.newRevName = value;
    }

    /**
     * 获取instValidate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInstValidate() {
        return instValidate;
    }

    /**
     * 设置instValidate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInstValidate(Boolean value) {
        this.instValidate = value;
    }

}
