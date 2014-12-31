
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>logFormats的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="logFormats">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="rawFormat"/>
 *     &lt;enumeration value="CSV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "logFormats")
@XmlEnum
public enum LogFormats {

    @XmlEnumValue("rawFormat")
    RAW_FORMAT("rawFormat"),
    CSV("CSV");
    private final String value;

    LogFormats(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LogFormats fromValue(String v) {
        for (LogFormats c: LogFormats.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
