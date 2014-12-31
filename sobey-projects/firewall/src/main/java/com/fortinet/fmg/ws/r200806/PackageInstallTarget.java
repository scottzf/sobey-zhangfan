
package com.fortinet.fmg.ws.r200806;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>packageInstallTarget complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="packageInstallTarget">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="grp" type="{http://r200806.ws.fmg.fortinet.com/}grpTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dev" type="{http://r200806.ws.fmg.fortinet.com/}devTarget" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "packageInstallTarget", propOrder = {
    "grp",
    "dev"
})
public class PackageInstallTarget {

    protected List<GrpTarget> grp;
    protected List<DevTarget> dev;

    /**
     * Gets the value of the grp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrpTarget }
     * 
     * 
     */
    public List<GrpTarget> getGrp() {
        if (grp == null) {
            grp = new ArrayList<GrpTarget>();
        }
        return this.grp;
    }

    /**
     * Gets the value of the dev property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dev property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDev().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DevTarget }
     * 
     * 
     */
    public List<DevTarget> getDev() {
        if (dev == null) {
            dev = new ArrayList<DevTarget>();
        }
        return this.dev;
    }

}
