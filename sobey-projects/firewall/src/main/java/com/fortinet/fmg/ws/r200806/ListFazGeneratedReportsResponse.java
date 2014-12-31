
package com.fortinet.fmg.ws.r200806;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>listFazGeneratedReportsResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="listFazGeneratedReportsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="totalNumberExists" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reportList" maxOccurs="30" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reportName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="reportProgressPercent" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="formats" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="activityReports" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listFazGeneratedReportsResponse", propOrder = {
    "errorMsg",
    "totalNumberExists",
    "reportList"
})
public class ListFazGeneratedReportsResponse {

    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    protected int totalNumberExists;
    protected List<ListFazGeneratedReportsResponse.ReportList> reportList;

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
     * 获取totalNumberExists属性的值。
     * 
     */
    public int getTotalNumberExists() {
        return totalNumberExists;
    }

    /**
     * 设置totalNumberExists属性的值。
     * 
     */
    public void setTotalNumberExists(int value) {
        this.totalNumberExists = value;
    }

    /**
     * Gets the value of the reportList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListFazGeneratedReportsResponse.ReportList }
     * 
     * 
     */
    public List<ListFazGeneratedReportsResponse.ReportList> getReportList() {
        if (reportList == null) {
            reportList = new ArrayList<ListFazGeneratedReportsResponse.ReportList>();
        }
        return this.reportList;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="reportName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="reportProgressPercent" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="formats" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="activityReports" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reportName",
        "startTime",
        "endTime",
        "reportProgressPercent",
        "size",
        "formats",
        "activityReports"
    })
    public static class ReportList {

        protected String reportName;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar startTime;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar endTime;
        protected int reportProgressPercent;
        protected int size;
        protected String formats;
        protected List<String> activityReports;

        /**
         * 获取reportName属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReportName() {
            return reportName;
        }

        /**
         * 设置reportName属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReportName(String value) {
            this.reportName = value;
        }

        /**
         * 获取startTime属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getStartTime() {
            return startTime;
        }

        /**
         * 设置startTime属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setStartTime(XMLGregorianCalendar value) {
            this.startTime = value;
        }

        /**
         * 获取endTime属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEndTime() {
            return endTime;
        }

        /**
         * 设置endTime属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEndTime(XMLGregorianCalendar value) {
            this.endTime = value;
        }

        /**
         * 获取reportProgressPercent属性的值。
         * 
         */
        public int getReportProgressPercent() {
            return reportProgressPercent;
        }

        /**
         * 设置reportProgressPercent属性的值。
         * 
         */
        public void setReportProgressPercent(int value) {
            this.reportProgressPercent = value;
        }

        /**
         * 获取size属性的值。
         * 
         */
        public int getSize() {
            return size;
        }

        /**
         * 设置size属性的值。
         * 
         */
        public void setSize(int value) {
            this.size = value;
        }

        /**
         * 获取formats属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFormats() {
            return formats;
        }

        /**
         * 设置formats属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFormats(String value) {
            this.formats = value;
        }

        /**
         * Gets the value of the activityReports property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the activityReports property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActivityReports().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getActivityReports() {
            if (activityReports == null) {
                activityReports = new ArrayList<String>();
            }
            return this.activityReports;
        }

    }

}
