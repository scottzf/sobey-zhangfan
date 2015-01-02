
package com.fortinet.fmg.ws.r200806;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>searchFazLogResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="searchFazLogResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMsg" type="{http://r200806.ws.fmg.fortinet.com/}errorMsg"/>
 *         &lt;element name="totalResultsFound" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="matchesReturned" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="logs">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="data" maxOccurs="100" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice>
 *                             &lt;element name="logEntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="eLog" type="{http://r200806.ws.fmg.fortinet.com/}eventLogsType"/>
 *                             &lt;element name="tLog" type="{http://r200806.ws.fmg.fortinet.com/}trafficLogsType"/>
 *                             &lt;element name="aLog" type="{http://r200806.ws.fmg.fortinet.com/}attackLogsType"/>
 *                             &lt;element name="vLog" type="{http://r200806.ws.fmg.fortinet.com/}antiVirusLogsType"/>
 *                             &lt;element name="wLog" type="{http://r200806.ws.fmg.fortinet.com/}webLogsType"/>
 *                             &lt;element name="cLog" type="{http://r200806.ws.fmg.fortinet.com/}contentLogsType"/>
 *                             &lt;element name="iLog" type="{http://r200806.ws.fmg.fortinet.com/}IMLogsType"/>
 *                             &lt;element name="sLog" type="{http://r200806.ws.fmg.fortinet.com/}emailLogsType"/>
 *                             &lt;element name="hLog" type="{http://r200806.ws.fmg.fortinet.com/}historyLogsType"/>
 *                             &lt;element name="gLog" type="{http://r200806.ws.fmg.fortinet.com/}genericLogsType"/>
 *                             &lt;element name="pLog" type="{http://r200806.ws.fmg.fortinet.com/}VoIPLogsType"/>
 *                             &lt;element name="dLog" type="{http://r200806.ws.fmg.fortinet.com/}DLPLogsType"/>
 *                             &lt;element name="rLog" type="{http://r200806.ws.fmg.fortinet.com/}appCtrlLogsType"/>
 *                             &lt;element name="nLog" type="{http://r200806.ws.fmg.fortinet.com/}netScanLogsType"/>
 *                           &lt;/choice>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CompressedData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
@XmlType(name = "searchFazLogResponse", propOrder = {
    "errorMsg",
    "totalResultsFound",
    "matchesReturned",
    "startIndex",
    "logs"
})
public class SearchFazLogResponse {

    @XmlElement(required = true)
    protected ErrorMsg errorMsg;
    protected int totalResultsFound;
    protected int matchesReturned;
    protected int startIndex;
    @XmlElement(required = true)
    protected SearchFazLogResponse.Logs logs;

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
     * 获取totalResultsFound属性的值。
     * 
     */
    public int getTotalResultsFound() {
        return totalResultsFound;
    }

    /**
     * 设置totalResultsFound属性的值。
     * 
     */
    public void setTotalResultsFound(int value) {
        this.totalResultsFound = value;
    }

    /**
     * 获取matchesReturned属性的值。
     * 
     */
    public int getMatchesReturned() {
        return matchesReturned;
    }

    /**
     * 设置matchesReturned属性的值。
     * 
     */
    public void setMatchesReturned(int value) {
        this.matchesReturned = value;
    }

    /**
     * 获取startIndex属性的值。
     * 
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 设置startIndex属性的值。
     * 
     */
    public void setStartIndex(int value) {
        this.startIndex = value;
    }

    /**
     * 获取logs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SearchFazLogResponse.Logs }
     *     
     */
    public SearchFazLogResponse.Logs getLogs() {
        return logs;
    }

    /**
     * 设置logs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SearchFazLogResponse.Logs }
     *     
     */
    public void setLogs(SearchFazLogResponse.Logs value) {
        this.logs = value;
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
     *         &lt;element name="data" maxOccurs="100" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice>
     *                   &lt;element name="logEntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="eLog" type="{http://r200806.ws.fmg.fortinet.com/}eventLogsType"/>
     *                   &lt;element name="tLog" type="{http://r200806.ws.fmg.fortinet.com/}trafficLogsType"/>
     *                   &lt;element name="aLog" type="{http://r200806.ws.fmg.fortinet.com/}attackLogsType"/>
     *                   &lt;element name="vLog" type="{http://r200806.ws.fmg.fortinet.com/}antiVirusLogsType"/>
     *                   &lt;element name="wLog" type="{http://r200806.ws.fmg.fortinet.com/}webLogsType"/>
     *                   &lt;element name="cLog" type="{http://r200806.ws.fmg.fortinet.com/}contentLogsType"/>
     *                   &lt;element name="iLog" type="{http://r200806.ws.fmg.fortinet.com/}IMLogsType"/>
     *                   &lt;element name="sLog" type="{http://r200806.ws.fmg.fortinet.com/}emailLogsType"/>
     *                   &lt;element name="hLog" type="{http://r200806.ws.fmg.fortinet.com/}historyLogsType"/>
     *                   &lt;element name="gLog" type="{http://r200806.ws.fmg.fortinet.com/}genericLogsType"/>
     *                   &lt;element name="pLog" type="{http://r200806.ws.fmg.fortinet.com/}VoIPLogsType"/>
     *                   &lt;element name="dLog" type="{http://r200806.ws.fmg.fortinet.com/}DLPLogsType"/>
     *                   &lt;element name="rLog" type="{http://r200806.ws.fmg.fortinet.com/}appCtrlLogsType"/>
     *                   &lt;element name="nLog" type="{http://r200806.ws.fmg.fortinet.com/}netScanLogsType"/>
     *                 &lt;/choice>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CompressedData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
        "data",
        "compressedData"
    })
    public static class Logs {

        protected List<SearchFazLogResponse.Logs.Data> data;
        @XmlElement(name = "CompressedData")
        protected byte[] compressedData;

        /**
         * Gets the value of the data property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the data property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getData().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SearchFazLogResponse.Logs.Data }
         * 
         * 
         */
        public List<SearchFazLogResponse.Logs.Data> getData() {
            if (data == null) {
                data = new ArrayList<SearchFazLogResponse.Logs.Data>();
            }
            return this.data;
        }

        /**
         * 获取compressedData属性的值。
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getCompressedData() {
            return compressedData;
        }

        /**
         * 设置compressedData属性的值。
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setCompressedData(byte[] value) {
            this.compressedData = value;
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
         *       &lt;choice>
         *         &lt;element name="logEntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="eLog" type="{http://r200806.ws.fmg.fortinet.com/}eventLogsType"/>
         *         &lt;element name="tLog" type="{http://r200806.ws.fmg.fortinet.com/}trafficLogsType"/>
         *         &lt;element name="aLog" type="{http://r200806.ws.fmg.fortinet.com/}attackLogsType"/>
         *         &lt;element name="vLog" type="{http://r200806.ws.fmg.fortinet.com/}antiVirusLogsType"/>
         *         &lt;element name="wLog" type="{http://r200806.ws.fmg.fortinet.com/}webLogsType"/>
         *         &lt;element name="cLog" type="{http://r200806.ws.fmg.fortinet.com/}contentLogsType"/>
         *         &lt;element name="iLog" type="{http://r200806.ws.fmg.fortinet.com/}IMLogsType"/>
         *         &lt;element name="sLog" type="{http://r200806.ws.fmg.fortinet.com/}emailLogsType"/>
         *         &lt;element name="hLog" type="{http://r200806.ws.fmg.fortinet.com/}historyLogsType"/>
         *         &lt;element name="gLog" type="{http://r200806.ws.fmg.fortinet.com/}genericLogsType"/>
         *         &lt;element name="pLog" type="{http://r200806.ws.fmg.fortinet.com/}VoIPLogsType"/>
         *         &lt;element name="dLog" type="{http://r200806.ws.fmg.fortinet.com/}DLPLogsType"/>
         *         &lt;element name="rLog" type="{http://r200806.ws.fmg.fortinet.com/}appCtrlLogsType"/>
         *         &lt;element name="nLog" type="{http://r200806.ws.fmg.fortinet.com/}netScanLogsType"/>
         *       &lt;/choice>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "logEntry",
            "eLog",
            "tLog",
            "aLog",
            "vLog",
            "wLog",
            "cLog",
            "iLog",
            "sLog",
            "hLog",
            "gLog",
            "pLog",
            "dLog",
            "rLog",
            "nLog"
        })
        public static class Data {

            protected String logEntry;
            protected EventLogsType eLog;
            protected TrafficLogsType tLog;
            protected AttackLogsType aLog;
            protected AntiVirusLogsType vLog;
            protected WebLogsType wLog;
            protected ContentLogsType cLog;
            protected IMLogsType iLog;
            protected EmailLogsType sLog;
            protected HistoryLogsType hLog;
            protected GenericLogsType gLog;
            protected VoIPLogsType pLog;
            protected DLPLogsType dLog;
            protected AppCtrlLogsType rLog;
            protected NetScanLogsType nLog;

            /**
             * 获取logEntry属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLogEntry() {
                return logEntry;
            }

            /**
             * 设置logEntry属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLogEntry(String value) {
                this.logEntry = value;
            }

            /**
             * 获取eLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link EventLogsType }
             *     
             */
            public EventLogsType getELog() {
                return eLog;
            }

            /**
             * 设置eLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link EventLogsType }
             *     
             */
            public void setELog(EventLogsType value) {
                this.eLog = value;
            }

            /**
             * 获取tLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link TrafficLogsType }
             *     
             */
            public TrafficLogsType getTLog() {
                return tLog;
            }

            /**
             * 设置tLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link TrafficLogsType }
             *     
             */
            public void setTLog(TrafficLogsType value) {
                this.tLog = value;
            }

            /**
             * 获取aLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link AttackLogsType }
             *     
             */
            public AttackLogsType getALog() {
                return aLog;
            }

            /**
             * 设置aLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link AttackLogsType }
             *     
             */
            public void setALog(AttackLogsType value) {
                this.aLog = value;
            }

            /**
             * 获取vLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link AntiVirusLogsType }
             *     
             */
            public AntiVirusLogsType getVLog() {
                return vLog;
            }

            /**
             * 设置vLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link AntiVirusLogsType }
             *     
             */
            public void setVLog(AntiVirusLogsType value) {
                this.vLog = value;
            }

            /**
             * 获取wLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link WebLogsType }
             *     
             */
            public WebLogsType getWLog() {
                return wLog;
            }

            /**
             * 设置wLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link WebLogsType }
             *     
             */
            public void setWLog(WebLogsType value) {
                this.wLog = value;
            }

            /**
             * 获取cLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link ContentLogsType }
             *     
             */
            public ContentLogsType getCLog() {
                return cLog;
            }

            /**
             * 设置cLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link ContentLogsType }
             *     
             */
            public void setCLog(ContentLogsType value) {
                this.cLog = value;
            }

            /**
             * 获取iLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link IMLogsType }
             *     
             */
            public IMLogsType getILog() {
                return iLog;
            }

            /**
             * 设置iLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link IMLogsType }
             *     
             */
            public void setILog(IMLogsType value) {
                this.iLog = value;
            }

            /**
             * 获取sLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link EmailLogsType }
             *     
             */
            public EmailLogsType getSLog() {
                return sLog;
            }

            /**
             * 设置sLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link EmailLogsType }
             *     
             */
            public void setSLog(EmailLogsType value) {
                this.sLog = value;
            }

            /**
             * 获取hLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link HistoryLogsType }
             *     
             */
            public HistoryLogsType getHLog() {
                return hLog;
            }

            /**
             * 设置hLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link HistoryLogsType }
             *     
             */
            public void setHLog(HistoryLogsType value) {
                this.hLog = value;
            }

            /**
             * 获取gLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link GenericLogsType }
             *     
             */
            public GenericLogsType getGLog() {
                return gLog;
            }

            /**
             * 设置gLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link GenericLogsType }
             *     
             */
            public void setGLog(GenericLogsType value) {
                this.gLog = value;
            }

            /**
             * 获取pLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link VoIPLogsType }
             *     
             */
            public VoIPLogsType getPLog() {
                return pLog;
            }

            /**
             * 设置pLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link VoIPLogsType }
             *     
             */
            public void setPLog(VoIPLogsType value) {
                this.pLog = value;
            }

            /**
             * 获取dLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link DLPLogsType }
             *     
             */
            public DLPLogsType getDLog() {
                return dLog;
            }

            /**
             * 设置dLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link DLPLogsType }
             *     
             */
            public void setDLog(DLPLogsType value) {
                this.dLog = value;
            }

            /**
             * 获取rLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link AppCtrlLogsType }
             *     
             */
            public AppCtrlLogsType getRLog() {
                return rLog;
            }

            /**
             * 设置rLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link AppCtrlLogsType }
             *     
             */
            public void setRLog(AppCtrlLogsType value) {
                this.rLog = value;
            }

            /**
             * 获取nLog属性的值。
             * 
             * @return
             *     possible object is
             *     {@link NetScanLogsType }
             *     
             */
            public NetScanLogsType getNLog() {
                return nLog;
            }

            /**
             * 设置nLog属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link NetScanLogsType }
             *     
             */
            public void setNLog(NetScanLogsType value) {
                this.nLog = value;
            }

        }

    }

}
