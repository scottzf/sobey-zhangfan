
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>assignGlobalPolicy complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="assignGlobalPolicy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="adom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyPackageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyPackageOid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *         &lt;element name="adomList" type="{http://r200806.ws.fmg.fortinet.com/}adomTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allObjects" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="installToDevice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="checkAssigndDup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assignGlobalPolicy", propOrder = {
    "servicePass",
    "adom",
    "policyPackageName",
    "policyPackageOid",
    "adomList",
    "allObjects",
    "installToDevice",
    "checkAssigndDup"
})
public class AssignGlobalPolicy {

    protected ServicePass servicePass;
    protected String adom;
    protected String policyPackageName;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger policyPackageOid;
    protected List<AdomTarget> adomList;
    protected Boolean allObjects;
    protected Boolean installToDevice;
    protected Boolean checkAssigndDup;

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
     * 获取policyPackageName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyPackageName() {
        return policyPackageName;
    }

    /**
     * 设置policyPackageName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyPackageName(String value) {
        this.policyPackageName = value;
    }

    /**
     * 获取policyPackageOid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPolicyPackageOid() {
        return policyPackageOid;
    }

    /**
     * 设置policyPackageOid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPolicyPackageOid(BigInteger value) {
        this.policyPackageOid = value;
    }

    /**
     * Gets the value of the adomList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adomList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdomList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdomTarget }
     * 
     * 
     */
    public List<AdomTarget> getAdomList() {
        if (adomList == null) {
            adomList = new ArrayList<AdomTarget>();
        }
        return this.adomList;
    }

    /**
     * 获取allObjects属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllObjects() {
        return allObjects;
    }

    /**
     * 设置allObjects属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllObjects(Boolean value) {
        this.allObjects = value;
    }

    /**
     * 获取installToDevice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInstallToDevice() {
        return installToDevice;
    }

    /**
     * 设置installToDevice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInstallToDevice(Boolean value) {
        this.installToDevice = value;
    }

    /**
     * 获取checkAssigndDup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCheckAssigndDup() {
        return checkAssigndDup;
    }

    /**
     * 设置checkAssigndDup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCheckAssigndDup(Boolean value) {
        this.checkAssigndDup = value;
    }

}
