
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>runFazReportResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="runFazReportResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="reportTemplate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "runFazReportResponse", propOrder = {
    "errorMsg",
    "reportTemplate"
})
public class RunFazReportResponse {

    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    protected String reportTemplate;

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
     * 获取reportTemplate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportTemplate() {
        return reportTemplate;
    }

    /**
     * 设置reportTemplate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportTemplate(String value) {
        this.reportTemplate = value;
    }

}
