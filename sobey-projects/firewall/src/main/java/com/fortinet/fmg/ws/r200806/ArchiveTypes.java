
package com.fortinet.fmg.ws.r200806;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>archiveTypes的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="archiveTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="web"/>
 *     &lt;enumeration value="email"/>
 *     &lt;enumeration value="ftp"/>
 *     &lt;enumeration value="IM"/>
 *     &lt;enumeration value="MMS"/>
 *     &lt;enumeration value="quarantine"/>
 *     &lt;enumeration value="IPS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "archiveTypes")
@XmlEnum
public enum ArchiveTypes {

    @XmlEnumValue("web")
    WEB("web"),
    @XmlEnumValue("email")
    EMAIL("email"),
    @XmlEnumValue("ftp")
    FTP("ftp"),
    IM("IM"),
    MMS("MMS"),
    @XmlEnumValue("quarantine")
    QUARANTINE("quarantine"),
    IPS("IPS");
    private final String value;

    ArchiveTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ArchiveTypes fromValue(String v) {
        for (ArchiveTypes c: ArchiveTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
