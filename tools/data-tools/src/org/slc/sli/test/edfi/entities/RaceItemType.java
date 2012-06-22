/*
 * Copyright 2012 Shared Learning Collaborative, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.31 at 09:35:49 AM EDT 
//


package org.slc.sli.test.edfi.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RaceItemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RaceItemType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="American Indian - Alaskan Native"/>
 *     &lt;enumeration value="Asian"/>
 *     &lt;enumeration value="Black - African American"/>
 *     &lt;enumeration value="Native Hawaiian - Pacific Islander"/>
 *     &lt;enumeration value="White"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RaceItemType")
@XmlEnum
public enum RaceItemType {

    @XmlEnumValue("American Indian - Alaskan Native")
    AMERICAN_INDIAN_ALASKAN_NATIVE("American Indian - Alaskan Native"),
    @XmlEnumValue("Asian")
    ASIAN("Asian"),
    @XmlEnumValue("Black - African American")
    BLACK_AFRICAN_AMERICAN("Black - African American"),
    @XmlEnumValue("Native Hawaiian - Pacific Islander")
    NATIVE_HAWAIIAN_PACIFIC_ISLANDER("Native Hawaiian - Pacific Islander"),
    @XmlEnumValue("White")
    WHITE("White");
    private final String value;

    RaceItemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RaceItemType fromValue(String v) {
        for (RaceItemType c: RaceItemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
