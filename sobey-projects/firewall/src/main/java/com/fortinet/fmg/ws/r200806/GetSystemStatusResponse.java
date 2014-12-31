
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getSystemStatusResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getSystemStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="platformType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="biosVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxNumAdminDomains" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxNumDeviceGroup" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="adminDomainConf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fipsMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diskSpaceFreeMB" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="diskSpaceUsedMB" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSystemStatusResponse", propOrder = {
    "errorMsg",
    "platformType",
    "version",
    "serialNumber",
    "biosVersion",
    "hostName",
    "maxNumAdminDomains",
    "maxNumDeviceGroup",
    "adminDomainConf",
    "fipsMode",
    "diskSpaceFreeMB",
    "diskSpaceUsedMB"
})
public class GetSystemStatusResponse {

    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    protected String platformType;
    protected String version;
    protected String serialNumber;
    protected String biosVersion;
    protected String hostName;
    protected Integer maxNumAdminDomains;
    protected Integer maxNumDeviceGroup;
    protected String adminDomainConf;
    protected String fipsMode;
    protected Integer diskSpaceFreeMB;
    protected Integer diskSpaceUsedMB;

    /**
     * 获取errorMsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ErrorMsg }
     *     
     */
    public ErrorMsg getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置errorMsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMsg }
     *     
     */
    public void setErrorMsg(ErrorMsg value) {
        this.errorMsg = value;
    }

    /**
     * 获取platformType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置platformType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatformType(String value) {
        this.platformType = value;
    }

    /**
     * 获取version属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置version属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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
     * 获取biosVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiosVersion() {
        return biosVersion;
    }

    /**
     * 设置biosVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiosVersion(String value) {
        this.biosVersion = value;
    }

    /**
     * 获取hostName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 设置hostName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * 获取maxNumAdminDomains属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxNumAdminDomains() {
        return maxNumAdminDomains;
    }

    /**
     * 设置maxNumAdminDomains属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxNumAdminDomains(Integer value) {
        this.maxNumAdminDomains = value;
    }

    /**
     * 获取maxNumDeviceGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxNumDeviceGroup() {
        return maxNumDeviceGroup;
    }

    /**
     * 设置maxNumDeviceGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxNumDeviceGroup(Integer value) {
        this.maxNumDeviceGroup = value;
    }

    /**
     * 获取adminDomainConf属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminDomainConf() {
        return adminDomainConf;
    }

    /**
     * 设置adminDomainConf属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminDomainConf(String value) {
        this.adminDomainConf = value;
    }

    /**
     * 获取fipsMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFipsMode() {
        return fipsMode;
    }

    /**
     * 设置fipsMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFipsMode(String value) {
        this.fipsMode = value;
    }

    /**
     * 获取diskSpaceFreeMB属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiskSpaceFreeMB() {
        return diskSpaceFreeMB;
    }

    /**
     * 设置diskSpaceFreeMB属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiskSpaceFreeMB(Integer value) {
        this.diskSpaceFreeMB = value;
    }

    /**
     * 获取diskSpaceUsedMB属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiskSpaceUsedMB() {
        return diskSpaceUsedMB;
    }

    /**
     * 设置diskSpaceUsedMB属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiskSpaceUsedMB(Integer value) {
        this.diskSpaceUsedMB = value;
    }

}
