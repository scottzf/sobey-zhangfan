
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addPolicyPackage complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="addPolicyPackage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="adom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isGlobal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="policyPackageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cloneFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packageInstallTarget" type="{http://r200806.ws.fmg.fortinet.com/}packageInstallTarget" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPolicyPackage", propOrder = {
    "servicePass",
    "adom",
    "isGlobal",
    "policyPackageName",
    "cloneFrom",
    "rename",
    "packageInstallTarget"
})
public class AddPolicyPackage {

    protected ServicePass servicePass;
    protected String adom;
    protected Boolean isGlobal;
    protected String policyPackageName;
    protected String cloneFrom;
    protected String rename;
    protected PackageInstallTarget packageInstallTarget;

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
     * 获取isGlobal属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsGlobal() {
        return isGlobal;
    }

    /**
     * 设置isGlobal属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsGlobal(Boolean value) {
        this.isGlobal = value;
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
     * 获取cloneFrom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloneFrom() {
        return cloneFrom;
    }

    /**
     * 设置cloneFrom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloneFrom(String value) {
        this.cloneFrom = value;
    }

    /**
     * 获取rename属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRename() {
        return rename;
    }

    /**
     * 设置rename属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRename(String value) {
        this.rename = value;
    }

    /**
     * 获取packageInstallTarget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PackageInstallTarget }
     *     
     */
    public PackageInstallTarget getPackageInstallTarget() {
        return packageInstallTarget;
    }

    /**
     * 设置packageInstallTarget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PackageInstallTarget }
     *     
     */
    public void setPackageInstallTarget(PackageInstallTarget value) {
        this.packageInstallTarget = value;
    }

}
