
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>getTCLRootFileResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getTCLRootFileResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fileLength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;choice>
 *           &lt;element name="fileContentsRaw" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="fileContentsBase64" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *           &lt;element name="fileContentsHex" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTCLRootFileResponse", propOrder = {
    "fileName",
    "fileOffset",
    "fileLength",
    "fileContentsRaw",
    "fileContentsBase64",
    "fileContentsHex"
})
public class GetTCLRootFileResponse {

    @XmlElement(required = true)
    protected String fileName;
    protected int fileOffset;
    protected int fileLength;
    protected String fileContentsRaw;
    protected byte[] fileContentsBase64;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] fileContentsHex;

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
     * 获取fileLength属性的值。
     * 
     */
    public int getFileLength() {
        return fileLength;
    }

    /**
     * 设置fileLength属性的值。
     * 
     */
    public void setFileLength(int value) {
        this.fileLength = value;
    }

    /**
     * 获取fileContentsRaw属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileContentsRaw() {
        return fileContentsRaw;
    }

    /**
     * 设置fileContentsRaw属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileContentsRaw(String value) {
        this.fileContentsRaw = value;
    }

    /**
     * 获取fileContentsBase64属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFileContentsBase64() {
        return fileContentsBase64;
    }

    /**
     * 设置fileContentsBase64属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFileContentsBase64(byte[] value) {
        this.fileContentsBase64 = value;
    }

    /**
     * 获取fileContentsHex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getFileContentsHex() {
        return fileContentsHex;
    }

    /**
     * 设置fileContentsHex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileContentsHex(byte[] value) {
        this.fileContentsHex = value;
    }

}
