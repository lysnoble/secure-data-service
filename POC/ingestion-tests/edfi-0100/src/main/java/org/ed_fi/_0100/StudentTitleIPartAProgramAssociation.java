//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 01:42:02 PM EST 
//


package org.ed_fi._0100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This association represents the Title I Part A program(s) that a student participates in or receives services from.  The association is an extension of the StudentProgramAssociation particular for Title I part A programs.
 * 
 * <p>Java class for StudentTitleIPartAProgramAssociation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudentTitleIPartAProgramAssociation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}StudentProgramAssociation">
 *       &lt;sequence>
 *         &lt;element name="TitleIPartAParticipant" type="{http://ed-fi.org/0100}TitleIPartAParticipantType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentTitleIPartAProgramAssociation", propOrder = {
    "titleIPartAParticipant"
})
public class StudentTitleIPartAProgramAssociation
    extends StudentProgramAssociation
{

    @XmlElement(name = "TitleIPartAParticipant", required = true)
    protected TitleIPartAParticipantType titleIPartAParticipant;

    /**
     * Gets the value of the titleIPartAParticipant property.
     * 
     * @return
     *     possible object is
     *     {@link TitleIPartAParticipantType }
     *     
     */
    public TitleIPartAParticipantType getTitleIPartAParticipant() {
        return titleIPartAParticipant;
    }

    /**
     * Sets the value of the titleIPartAParticipant property.
     * 
     * @param value
     *     allowed object is
     *     {@link TitleIPartAParticipantType }
     *     
     */
    public void setTitleIPartAParticipant(TitleIPartAParticipantType value) {
        this.titleIPartAParticipant = value;
    }

}
