
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>searchFazLog complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="searchFazLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="adom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://r200806.ws.fmg.fortinet.com/}searchContent" minOccurs="0"/>
 *         &lt;element name="format" type="{http://r200806.ws.fmg.fortinet.com/}logFormats" minOccurs="0"/>
 *         &lt;element name="deviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logType" type="{http://r200806.ws.fmg.fortinet.com/}logTypes"/>
 *         &lt;element name="searchCriteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxNumMatches" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="checkArchive" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DLPArchiveType" type="{http://r200806.ws.fmg.fortinet.com/}archiveTypes" minOccurs="0"/>
 *         &lt;element name="compression" type="{http://r200806.ws.fmg.fortinet.com/}compressionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchFazLog", propOrder = {
    "servicePass",
    "adom",
    "content",
    "format",
    "deviceName",
    "logType",
    "searchCriteria",
    "maxNumMatches",
    "startIndex",
    "checkArchive",
    "dlpArchiveType",
    "compression"
})
public class SearchFazLog {

    protected ServicePass servicePass;
    protected String adom;
    @XmlElement(defaultValue = "logs")
    protected SearchContent content;
    @XmlElement(defaultValue = "rawFormat")
    protected LogFormats format;
    protected String deviceName;
    @XmlElement(required = true, defaultValue = "traffic")
    protected LogTypes logType;
    protected String searchCriteria;
    @XmlElement(defaultValue = "30")
    protected int maxNumMatches;
    @XmlElement(defaultValue = "1")
    protected int startIndex;
    @XmlElement(defaultValue = "0")
    protected int checkArchive;
    @XmlElement(name = "DLPArchiveType")
    protected ArchiveTypes dlpArchiveType;
    @XmlElement(defaultValue = "tar")
    protected CompressionType compression;

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
     * 获取content属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SearchContent }
     *     
     */
    public SearchContent getContent() {
        return content;
    }

    /**
     * 设置content属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SearchContent }
     *     
     */
    public void setContent(SearchContent value) {
        this.content = value;
    }

    /**
     * 获取format属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LogFormats }
     *     
     */
    public LogFormats getFormat() {
        return format;
    }

    /**
     * 设置format属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LogFormats }
     *     
     */
    public void setFormat(LogFormats value) {
        this.format = value;
    }

    /**
     * 获取deviceName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置deviceName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceName(String value) {
        this.deviceName = value;
    }

    /**
     * 获取logType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LogTypes }
     *     
     */
    public LogTypes getLogType() {
        return logType;
    }

    /**
     * 设置logType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LogTypes }
     *     
     */
    public void setLogType(LogTypes value) {
        this.logType = value;
    }

    /**
     * 获取searchCriteria属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * 设置searchCriteria属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchCriteria(String value) {
        this.searchCriteria = value;
    }

    /**
     * 获取maxNumMatches属性的值。
     * 
     */
    public int getMaxNumMatches() {
        return maxNumMatches;
    }

    /**
     * 设置maxNumMatches属性的值。
     * 
     */
    public void setMaxNumMatches(int value) {
        this.maxNumMatches = value;
    }

    /**
     * 获取startIndex属性的值。
     * 
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 设置startIndex属性的值。
     * 
     */
    public void setStartIndex(int value) {
        this.startIndex = value;
    }

    /**
     * 获取checkArchive属性的值。
     * 
     */
    public int getCheckArchive() {
        return checkArchive;
    }

    /**
     * 设置checkArchive属性的值。
     * 
     */
    public void setCheckArchive(int value) {
        this.checkArchive = value;
    }

    /**
     * 获取dlpArchiveType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArchiveTypes }
     *     
     */
    public ArchiveTypes getDLPArchiveType() {
        return dlpArchiveType;
    }

    /**
     * 设置dlpArchiveType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArchiveTypes }
     *     
     */
    public void setDLPArchiveType(ArchiveTypes value) {
        this.dlpArchiveType = value;
    }

    /**
     * 获取compression属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CompressionType }
     *     
     */
    public CompressionType getCompression() {
        return compression;
    }

    /**
     * 设置compression属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CompressionType }
     *     
     */
    public void setCompression(CompressionType value) {
        this.compression = value;
    }

}
