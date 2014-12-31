
package com.fortinet.fmg.ws.r200806;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>metafields complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="metafields">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="metafield" type="{http://r200806.ws.fmg.fortinet.com/}metafield" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metafields", propOrder = {
    "metafield"
})
public class Metafields {

    protected List<Metafield> metafield;

    /**
     * Gets the value of the metafield property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metafield property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetafield().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Metafield }
     * 
     * 
     */
    public List<Metafield> getMetafield() {
        if (metafield == null) {
            metafield = new ArrayList<Metafield>();
        }
        return this.metafield;
    }

}
