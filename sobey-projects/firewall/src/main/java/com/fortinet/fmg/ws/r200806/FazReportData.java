
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>fazReportData complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="fazReportData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reportContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fazReportData", propOrder = {
    "reportContent"
})
public class FazReportData {

    @XmlElement(required = true)
    protected byte[] reportContent;

    /**
     * 获取reportContent属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getReportContent() {
        return reportContent;
    }

    /**
     * 设置reportContent属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setReportContent(byte[] value) {
        this.reportContent = value;
    }

}
