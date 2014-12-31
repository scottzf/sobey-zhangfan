
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>searchContent的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="searchContent">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="logs"/>
 *     &lt;enumeration value="contentLogs"/>
 *     &lt;enumeration value="localLogs"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "searchContent")
@XmlEnum
public enum SearchContent {

    @XmlEnumValue("logs")
    LOGS("logs"),
    @XmlEnumValue("contentLogs")
    CONTENT_LOGS("contentLogs"),
    @XmlEnumValue("localLogs")
    LOCAL_LOGS("localLogs");
    private final String value;

    SearchContent(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchContent fromValue(String v) {
        for (SearchContent c: SearchContent.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
