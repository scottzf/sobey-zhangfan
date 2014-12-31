
package com.fortinet.fmg.ws.r200806;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>importPolicyResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="importPolicyResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adomName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adomOid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="devName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="devId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vdoms" type="{http://r200806.ws.fmg.fortinet.com/}vdomInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="report" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="return" type="{http://r200806.ws.fmg.fortinet.com/}importPolicy" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importPolicyResponse", propOrder = {
    "adomName",
    "adomOid",
    "devName",
    "devId",
    "vdoms",
    "report",
    "errorMsg",
    "_return"
})
public class ImportPolicyResponse {

    protected String adomName;
    protected Integer adomOid;
    protected String devName;
    protected Integer devId;
    protected List<VdomInfo> vdoms;
    protected String report;
    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    @XmlElement(name = "return")
    protected ImportPolicy _return;

    /**
     * 获取adomName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdomName() {
        return adomName;
    }

    /**
     * 设置adomName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdomName(String value) {
        this.adomName = value;
    }

    /**
     * 获取adomOid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAdomOid() {
        return adomOid;
    }

    /**
     * 设置adomOid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAdomOid(Integer value) {
        this.adomOid = value;
    }

    /**
     * 获取devName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevName() {
        return devName;
    }

    /**
     * 设置devName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevName(String value) {
        this.devName = value;
    }

    /**
     * 获取devId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDevId() {
        return devId;
    }

    /**
     * 设置devId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDevId(Integer value) {
        this.devId = value;
    }

    /**
     * Gets the value of the vdoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vdoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVdoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VdomInfo }
     * 
     * 
     */
    public List<VdomInfo> getVdoms() {
        if (vdoms == null) {
            vdoms = new ArrayList<VdomInfo>();
        }
        return this.vdoms;
    }

    /**
     * 获取report属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReport() {
        return report;
    }

    /**
     * 设置report属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReport(String value) {
        this.report = value;
    }

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
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ImportPolicy }
     *     
     */
    public ImportPolicy getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ImportPolicy }
     *     
     */
    public void setReturn(ImportPolicy value) {
        this._return = value;
    }

}
