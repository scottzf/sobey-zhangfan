
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addAdomResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="addAdomResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="adomOid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addAdomResponse", propOrder = {
    "errorMsg",
    "adomOid"
})
public class AddAdomResponse {

    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger adomOid;

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
     * 获取adomOid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAdomOid() {
        return adomOid;
    }

    /**
     * 设置adomOid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAdomOid(BigInteger value) {
        this.adomOid = value;
    }

}
