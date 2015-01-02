
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>logTypes的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="logTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="event"/>
 *     &lt;enumeration value="traffic"/>
 *     &lt;enumeration value="attack"/>
 *     &lt;enumeration value="antiVirus"/>
 *     &lt;enumeration value="webLogs"/>
 *     &lt;enumeration value="IM"/>
 *     &lt;enumeration value="email"/>
 *     &lt;enumeration value="content"/>
 *     &lt;enumeration value="history"/>
 *     &lt;enumeration value="generic"/>
 *     &lt;enumeration value="voIP"/>
 *     &lt;enumeration value="DLP"/>
 *     &lt;enumeration value="appCtrl"/>
 *     &lt;enumeration value="netScan"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "logTypes")
@XmlEnum
public enum LogTypes {

    @XmlEnumValue("event")
    EVENT("event"),
    @XmlEnumValue("traffic")
    TRAFFIC("traffic"),
    @XmlEnumValue("attack")
    ATTACK("attack"),
    @XmlEnumValue("antiVirus")
    ANTI_VIRUS("antiVirus"),
    @XmlEnumValue("webLogs")
    WEB_LOGS("webLogs"),
    IM("IM"),
    @XmlEnumValue("email")
    EMAIL("email"),
    @XmlEnumValue("content")
    CONTENT("content"),
    @XmlEnumValue("history")
    HISTORY("history"),
    @XmlEnumValue("generic")
    GENERIC("generic"),
    @XmlEnumValue("voIP")
    VO_IP("voIP"),
    DLP("DLP"),
    @XmlEnumValue("appCtrl")
    APP_CTRL("appCtrl"),
    @XmlEnumValue("netScan")
    NET_SCAN("netScan");
    private final String value;

    LogTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LogTypes fromValue(String v) {
        for (LogTypes c: LogTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
