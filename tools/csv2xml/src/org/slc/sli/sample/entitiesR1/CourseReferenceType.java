//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.05 at 01:12:38 PM EST 
//


package org.slc.sli.sample.entitiesR1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides alternative references for courses during interchange. Use XML IDREF to reference a course record that is included in the interchange
 * 
 * <p>Java class for CourseReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CourseReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ReferenceType">
 *       &lt;sequence>
 *         &lt;element name="CourseIdentity" type="{http://ed-fi.org/0100}CourseIdentityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseReferenceType", propOrder = {
    "courseIdentity"
})
public class CourseReferenceType
    extends ReferenceType
{

    @XmlElement(name = "CourseIdentity")
    protected CourseIdentityType courseIdentity;

    /**
     * Gets the value of the courseIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link CourseIdentityType }
     *     
     */
    public CourseIdentityType getCourseIdentity() {
        return courseIdentity;
    }

    /**
     * Sets the value of the courseIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseIdentityType }
     *     
     */
    public void setCourseIdentity(CourseIdentityType value) {
        this.courseIdentity = value;
    }

}
