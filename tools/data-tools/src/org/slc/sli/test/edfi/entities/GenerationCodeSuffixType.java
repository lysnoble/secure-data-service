//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.16 at 01:39:34 PM EST 
//


package org.slc.sli.test.edfi.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenerationCodeSuffixType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GenerationCodeSuffixType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Jr"/>
 *     &lt;enumeration value="Sr"/>
 *     &lt;enumeration value="II"/>
 *     &lt;enumeration value="III"/>
 *     &lt;enumeration value="IV"/>
 *     &lt;enumeration value="V"/>
 *     &lt;enumeration value="VI"/>
 *     &lt;enumeration value="VII"/>
 *     &lt;enumeration value="VIII"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GenerationCodeSuffixType")
@XmlEnum
public enum GenerationCodeSuffixType {

    @XmlEnumValue("Jr")
    JR("Jr"),
    @XmlEnumValue("Sr")
    SR("Sr"),
    II("II"),
    III("III"),
    IV("IV"),
    V("V"),
    VI("VI"),
    VII("VII"),
    VIII("VIII");
    private final String value;

    GenerationCodeSuffixType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GenerationCodeSuffixType fromValue(String v) {
        for (GenerationCodeSuffixType c: GenerationCodeSuffixType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
