//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 01:12:00 PM EDT 
//


package org.slc.sli.test.edfi.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Grade Point Average computed for a gradng period or cumulatively.
 * 
 * <p>Java class for GradePointAverage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GradePointAverage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GPA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="GPAWeighted" type="{http://ed-fi.org/0100}GPAWeightedType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradePointAverage", propOrder = {
    "gpa"
})
public class GradePointAverage {

    @XmlElement(name = "GPA", required = true)
    protected BigDecimal gpa;
    @XmlAttribute(name = "GPAWeighted")
    protected GPAWeightedType gpaWeighted;

    /**
     * Gets the value of the gpa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGPA() {
        return gpa;
    }

    /**
     * Sets the value of the gpa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGPA(BigDecimal value) {
        this.gpa = value;
    }

    /**
     * Gets the value of the gpaWeighted property.
     * 
     * @return
     *     possible object is
     *     {@link GPAWeightedType }
     *     
     */
    public GPAWeightedType getGPAWeighted() {
        return gpaWeighted;
    }

    /**
     * Sets the value of the gpaWeighted property.
     * 
     * @param value
     *     allowed object is
     *     {@link GPAWeightedType }
     *     
     */
    public void setGPAWeighted(GPAWeightedType value) {
        this.gpaWeighted = value;
    }

}
