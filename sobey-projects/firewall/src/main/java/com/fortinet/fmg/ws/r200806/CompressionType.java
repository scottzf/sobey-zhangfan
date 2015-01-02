
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>compressionType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="compressionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="tar"/>
 *     &lt;enumeration value="gzip"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "compressionType")
@XmlEnum
public enum CompressionType {

    @XmlEnumValue("tar")
    TAR("tar"),
    @XmlEnumValue("gzip")
    GZIP("gzip");
    private final String value;

    CompressionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CompressionType fromValue(String v) {
        for (CompressionType c: CompressionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
