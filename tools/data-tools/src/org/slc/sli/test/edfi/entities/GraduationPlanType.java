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
 * <p>Java class for GraduationPlanType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GraduationPlanType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Career and Technical Education"/>
 *     &lt;enumeration value="Distinguished"/>
 *     &lt;enumeration value="Minimum"/>
 *     &lt;enumeration value="Recommended"/>
 *     &lt;enumeration value="Standard"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GraduationPlanType")
@XmlEnum
public enum GraduationPlanType {

    @XmlEnumValue("Career and Technical Education")
    CAREER_AND_TECHNICAL_EDUCATION("Career and Technical Education"),
    @XmlEnumValue("Distinguished")
    DISTINGUISHED("Distinguished"),
    @XmlEnumValue("Minimum")
    MINIMUM("Minimum"),
    @XmlEnumValue("Recommended")
    RECOMMENDED("Recommended"),
    @XmlEnumValue("Standard")
    STANDARD("Standard");

    /** total possible enum values */
    public static final int NUM_TYPES = 5;

    private final String value;

    GraduationPlanType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GraduationPlanType fromValue(String v) {
        for (GraduationPlanType c: GraduationPlanType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    //in order to be unique, a school can't have two graduationPlans of the same type, so iterate through
    public static GraduationPlanType fromIndex(int i) {
    	switch (i % NUM_TYPES) {
    	case 0:
    		return CAREER_AND_TECHNICAL_EDUCATION;
    	case 1:
    		return DISTINGUISHED;
    	case 2:
    		return MINIMUM;
    	case 3:
    		return RECOMMENDED;
    	default:
    		return STANDARD;
    	}
    }
}