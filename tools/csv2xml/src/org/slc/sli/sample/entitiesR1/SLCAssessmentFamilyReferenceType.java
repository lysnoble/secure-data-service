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
 * Changed to use a required SLC identity type.
 * 
 * <p>Java class for SLC-AssessmentFamilyReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SLC-AssessmentFamilyReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ReferenceType">
 *       &lt;sequence>
 *         &lt;element name="AssessmentFamilyIdentity" type="{http://ed-fi.org/0100}SLC-AssessmentFamilyIdentityType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SLC-AssessmentFamilyReferenceType", propOrder = {
    "assessmentFamilyIdentity"
})
public class SLCAssessmentFamilyReferenceType
    extends ReferenceType
{

    @XmlElement(name = "AssessmentFamilyIdentity", required = true)
    protected SLCAssessmentFamilyIdentityType assessmentFamilyIdentity;

    /**
     * Gets the value of the assessmentFamilyIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link SLCAssessmentFamilyIdentityType }
     *     
     */
    public SLCAssessmentFamilyIdentityType getAssessmentFamilyIdentity() {
        return assessmentFamilyIdentity;
    }

    /**
     * Sets the value of the assessmentFamilyIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCAssessmentFamilyIdentityType }
     *     
     */
    public void setAssessmentFamilyIdentity(SLCAssessmentFamilyIdentityType value) {
        this.assessmentFamilyIdentity = value;
    }

}
