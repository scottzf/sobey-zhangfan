
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>adomDetail complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="adomDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isBackupMode" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VPNManagement" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="metafields" type="{http://r200806.ws.fmg.fortinet.com/}metafields" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adomDetail", propOrder = {
    "oid",
    "name",
    "description",
    "version",
    "mr",
    "state",
    "isBackupMode",
    "vpnManagement",
    "metafields"
})
public class AdomDetail {

    @XmlElement(required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger oid;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    protected int version;
    protected int mr;
    protected boolean state;
    protected boolean isBackupMode;
    @XmlElement(name = "VPNManagement")
    protected boolean vpnManagement;
    protected Metafields metafields;

    /**
     * 获取oid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOid() {
        return oid;
    }

    /**
     * 设置oid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOid(BigInteger value) {
        this.oid = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取version属性的值。
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * 设置version属性的值。
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * 获取mr属性的值。
     * 
     */
    public int getMr() {
        return mr;
    }

    /**
     * 设置mr属性的值。
     * 
     */
    public void setMr(int value) {
        this.mr = value;
    }

    /**
     * 获取state属性的值。
     * 
     */
    public boolean isState() {
        return state;
    }

    /**
     * 设置state属性的值。
     * 
     */
    public void setState(boolean value) {
        this.state = value;
    }

    /**
     * 获取isBackupMode属性的值。
     * 
     */
    public boolean isIsBackupMode() {
        return isBackupMode;
    }

    /**
     * 设置isBackupMode属性的值。
     * 
     */
    public void setIsBackupMode(boolean value) {
        this.isBackupMode = value;
    }

    /**
     * 获取vpnManagement属性的值。
     * 
     */
    public boolean isVPNManagement() {
        return vpnManagement;
    }

    /**
     * 设置vpnManagement属性的值。
     * 
     */
    public void setVPNManagement(boolean value) {
        this.vpnManagement = value;
    }

    /**
     * 获取metafields属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Metafields }
     *     
     */
    public Metafields getMetafields() {
        return metafields;
    }

    /**
     * 设置metafields属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Metafields }
     *     
     */
    public void setMetafields(Metafields value) {
        this.metafields = value;
    }

}
