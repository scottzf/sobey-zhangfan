
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getAdoms complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getAdoms">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="names" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="adomIds" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdoms", propOrder = {
    "servicePass",
    "names",
    "adomIds"
})
public class GetAdoms {

    protected ServicePass servicePass;
    protected List<String> names;
    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> adomIds;

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
     * Gets the value of the names property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the names property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNames() {
        if (names == null) {
            names = new ArrayList<String>();
        }
        return this.names;
    }

    /**
     * Gets the value of the adomIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adomIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdomIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getAdomIds() {
        if (adomIds == null) {
            adomIds = new ArrayList<BigInteger>();
        }
        return this.adomIds;
    }

}
