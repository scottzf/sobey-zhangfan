
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>deviceDetail complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="deviceDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="devId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmware" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmwareVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="buildNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hostname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="platform" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IPSContract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="antiVirusContract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appsignature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mgmtMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceDetail", propOrder = {
    "devId",
    "firmware",
    "firmwareVersion",
    "buildNum",
    "description",
    "hostname",
    "platform",
    "sn",
    "ip",
    "ipsContract",
    "antiVirusContract",
    "appsignature",
    "mgmtMode"
})
public class DeviceDetail {

    @XmlElement(required = true)
    protected String devId;
    @XmlElement(required = true)
    protected String firmware;
    @XmlElement(required = true)
    protected String firmwareVersion;
    @XmlElement(required = true)
    protected String buildNum;
    protected String description;
    @XmlElement(required = true)
    protected String hostname;
    @XmlElement(required = true)
    protected String platform;
    @XmlElement(required = true)
    protected String sn;
    @XmlElement(required = true)
    protected String ip;
    @XmlElement(name = "IPSContract")
    protected String ipsContract;
    protected String antiVirusContract;
    protected String appsignature;
    protected String mgmtMode;

    /**
     * 获取devId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevId() {
        return devId;
    }

    /**
     * 设置devId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevId(String value) {
        this.devId = value;
    }

    /**
     * 获取firmware属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmware() {
        return firmware;
    }

    /**
     * 设置firmware属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmware(String value) {
        this.firmware = value;
    }

    /**
     * 获取firmwareVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * 设置firmwareVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmwareVersion(String value) {
        this.firmwareVersion = value;
    }

    /**
     * 获取buildNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildNum() {
        return buildNum;
    }

    /**
     * 设置buildNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildNum(String value) {
        this.buildNum = value;
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
     * 获取hostname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * 设置hostname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }

    /**
     * 获取platform属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置platform属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatform(String value) {
        this.platform = value;
    }

    /**
     * 获取sn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置sn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSn(String value) {
        this.sn = value;
    }

    /**
     * 获取ip属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp(String value) {
        this.ip = value;
    }

    /**
     * 获取ipsContract属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPSContract() {
        return ipsContract;
    }

    /**
     * 设置ipsContract属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPSContract(String value) {
        this.ipsContract = value;
    }

    /**
     * 获取antiVirusContract属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntiVirusContract() {
        return antiVirusContract;
    }

    /**
     * 设置antiVirusContract属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntiVirusContract(String value) {
        this.antiVirusContract = value;
    }

    /**
     * 获取appsignature属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppsignature() {
        return appsignature;
    }

    /**
     * 设置appsignature属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppsignature(String value) {
        this.appsignature = value;
    }

    /**
     * 获取mgmtMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMgmtMode() {
        return mgmtMode;
    }

    /**
     * 设置mgmtMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMgmtMode(String value) {
        this.mgmtMode = value;
    }

}
