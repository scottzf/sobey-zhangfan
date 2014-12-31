
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getInstlogResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getInstlogResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="instLog" type="{http://r200806.ws.fmg.fortinet.com/}instLog" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInstlogResponse", propOrder = {
    "instLog"
})
public class GetInstlogResponse {

    protected InstLog instLog;

    /**
     * 获取instLog属性的值。
     * 
     * @return
     *     possible object is
     *     {@link InstLog }
     *     
     */
    public InstLog getInstLog() {
        return instLog;
    }

    /**
     * 设置instLog属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link InstLog }
     *     
     */
    public void setInstLog(InstLog value) {
        this.instLog = value;
    }

}
