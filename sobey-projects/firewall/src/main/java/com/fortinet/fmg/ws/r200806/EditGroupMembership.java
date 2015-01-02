
package com.fortinet.fmg.ws.r200806;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>editGroupMembership complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="editGroupMembership">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePass" type="{http://r200806.ws.fmg.fortinet.com/}servicePass" minOccurs="0"/>
 *         &lt;element name="adom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grpId" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *         &lt;element name="addDeviceSNList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="addDeviceIDList" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delDeviceSNList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delDeviceIDList" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="addGroupNameList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="addGroupIDList" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delGroupNameList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delGroupIDList" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "editGroupMembership", propOrder = {
    "servicePass",
    "adom",
    "name",
    "grpId",
    "addDeviceSNList",
    "addDeviceIDList",
    "delDeviceSNList",
    "delDeviceIDList",
    "addGroupNameList",
    "addGroupIDList",
    "delGroupNameList",
    "delGroupIDList"
})
public class EditGroupMembership {

    protected ServicePass servicePass;
    protected String adom;
    protected String name;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger grpId;
    protected List<String> addDeviceSNList;
    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> addDeviceIDList;
    protected List<String> delDeviceSNList;
    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> delDeviceIDList;
    protected List<String> addGroupNameList;
    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> addGroupIDList;
    protected List<String> delGroupNameList;
    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> delGroupIDList;

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
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取grpId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGrpId() {
        return grpId;
    }

    /**
     * 设置grpId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGrpId(BigInteger value) {
        this.grpId = value;
    }

    /**
     * Gets the value of the addDeviceSNList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addDeviceSNList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddDeviceSNList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddDeviceSNList() {
        if (addDeviceSNList == null) {
            addDeviceSNList = new ArrayList<String>();
        }
        return this.addDeviceSNList;
    }

    /**
     * Gets the value of the addDeviceIDList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addDeviceIDList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddDeviceIDList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getAddDeviceIDList() {
        if (addDeviceIDList == null) {
            addDeviceIDList = new ArrayList<BigInteger>();
        }
        return this.addDeviceIDList;
    }

    /**
     * Gets the value of the delDeviceSNList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delDeviceSNList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelDeviceSNList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDelDeviceSNList() {
        if (delDeviceSNList == null) {
            delDeviceSNList = new ArrayList<String>();
        }
        return this.delDeviceSNList;
    }

    /**
     * Gets the value of the delDeviceIDList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delDeviceIDList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelDeviceIDList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getDelDeviceIDList() {
        if (delDeviceIDList == null) {
            delDeviceIDList = new ArrayList<BigInteger>();
        }
        return this.delDeviceIDList;
    }

    /**
     * Gets the value of the addGroupNameList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addGroupNameList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddGroupNameList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddGroupNameList() {
        if (addGroupNameList == null) {
            addGroupNameList = new ArrayList<String>();
        }
        return this.addGroupNameList;
    }

    /**
     * Gets the value of the addGroupIDList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addGroupIDList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddGroupIDList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getAddGroupIDList() {
        if (addGroupIDList == null) {
            addGroupIDList = new ArrayList<BigInteger>();
        }
        return this.addGroupIDList;
    }

    /**
     * Gets the value of the delGroupNameList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delGroupNameList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelGroupNameList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDelGroupNameList() {
        if (delGroupNameList == null) {
            delGroupNameList = new ArrayList<String>();
        }
        return this.delGroupNameList;
    }

    /**
     * Gets the value of the delGroupIDList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delGroupIDList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelGroupIDList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getDelGroupIDList() {
        if (delGroupIDList == null) {
            delGroupIDList = new ArrayList<BigInteger>();
        }
        return this.delGroupIDList;
    }

}
