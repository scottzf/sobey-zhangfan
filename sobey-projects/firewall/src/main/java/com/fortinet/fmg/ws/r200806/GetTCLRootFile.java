
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getTCLRootFile complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getTCLRootFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fileMaxLen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fileEncode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="base64"/>
 *               &lt;enumeration value="hex"/>
 *               &lt;enumeration value="raw"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTCLRootFile", propOrder = {
    "servicePass",
    "fileName",
    "fileOffset",
    "fileMaxLen",
    "fileEncode"
})
public class GetTCLRootFile {

    protected ServicePass servicePass;
    @XmlElement(required = true)
    protected String fileName;
    protected int fileOffset;
    protected int fileMaxLen;
    @XmlElement(required = true)
    protected String fileEncode;

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
     * 获取fileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置fileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * 获取fileOffset属性的值。
     * 
     */
    public int getFileOffset() {
        return fileOffset;
    }

    /**
     * 设置fileOffset属性的值。
     * 
     */
    public void setFileOffset(int value) {
        this.fileOffset = value;
    }

    /**
     * 获取fileMaxLen属性的值。
     * 
     */
    public int getFileMaxLen() {
        return fileMaxLen;
    }

    /**
     * 设置fileMaxLen属性的值。
     * 
     */
    public void setFileMaxLen(int value) {
        this.fileMaxLen = value;
    }

    /**
     * 获取fileEncode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileEncode() {
        return fileEncode;
    }

    /**
     * 设置fileEncode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileEncode(String value) {
        this.fileEncode = value;
    }

}
