//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 01:12:00 PM EDT 
//


package org.slc.sli.test.edfi.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Definition of the performance levels and the associated cut scores.  Three styles are supported:
 *    1. Specification of performance level by min and max score
 *    2. Specification of performance level by cut score - min only
 *    3. Specification of performance level without any mapping to scores
 * 
 * <p>Java class for AssessmentPerformanceLevel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssessmentPerformanceLevel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PerformanceLevel" type="{http://ed-fi.org/0100}PerformanceLevelDescriptorType"/>
 *         &lt;element name="AssessmentReportingMethod" type="{http://ed-fi.org/0100}AssessmentReportingMethodType"/>
 *         &lt;element name="MinimumScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaximumScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssessmentPerformanceLevel", propOrder = {
    "performanceLevel",
    "assessmentReportingMethod",
    "minimumScore",
    "maximumScore"
})
public class AssessmentPerformanceLevel {

    @XmlElement(name = "PerformanceLevel", required = true)
    protected PerformanceLevelDescriptorType performanceLevel;
    @XmlElement(name = "AssessmentReportingMethod", required = true)
    protected AssessmentReportingMethodType assessmentReportingMethod;
    @XmlElement(name = "MinimumScore")
    protected Integer minimumScore;
    @XmlElement(name = "MaximumScore")
    protected Integer maximumScore;

    /**
     * Gets the value of the performanceLevel property.
     * 
     * @return
     *     possible object is
     *     {@link PerformanceLevelDescriptorType }
     *     
     */
    public PerformanceLevelDescriptorType getPerformanceLevel() {
        return performanceLevel;
    }

    /**
     * Sets the value of the performanceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformanceLevelDescriptorType }
     *     
     */
    public void setPerformanceLevel(PerformanceLevelDescriptorType value) {
        this.performanceLevel = value;
    }

    /**
     * Gets the value of the assessmentReportingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link AssessmentReportingMethodType }
     *     
     */
    public AssessmentReportingMethodType getAssessmentReportingMethod() {
        return assessmentReportingMethod;
    }

    /**
     * Sets the value of the assessmentReportingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssessmentReportingMethodType }
     *     
     */
    public void setAssessmentReportingMethod(AssessmentReportingMethodType value) {
        this.assessmentReportingMethod = value;
    }

    /**
     * Gets the value of the minimumScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinimumScore() {
        return minimumScore;
    }

    /**
     * Sets the value of the minimumScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinimumScore(Integer value) {
        this.minimumScore = value;
    }

    /**
     * Gets the value of the maximumScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaximumScore() {
        return maximumScore;
    }

    /**
     * Sets the value of the maximumScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaximumScore(Integer value) {
        this.maximumScore = value;
    }

}
