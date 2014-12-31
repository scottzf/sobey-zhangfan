
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>quarantineTypes的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="quarantineTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="infected"/>
 *     &lt;enumeration value="heuristics"/>
 *     &lt;enumeration value="blocked"/>
 *     &lt;enumeration value="spam"/>
 *     &lt;enumeration value="intercepted"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "quarantineTypes")
@XmlEnum
public enum QuarantineTypes {

    @XmlEnumValue("infected")
    INFECTED("infected"),
    @XmlEnumValue("heuristics")
    HEURISTICS("heuristics"),
    @XmlEnumValue("blocked")
    BLOCKED("blocked"),
    @XmlEnumValue("spam")
    SPAM("spam"),
    @XmlEnumValue("intercepted")
    INTERCEPTED("intercepted");
    private final String value;

    QuarantineTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QuarantineTypes fromValue(String v) {
        for (QuarantineTypes c: QuarantineTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
