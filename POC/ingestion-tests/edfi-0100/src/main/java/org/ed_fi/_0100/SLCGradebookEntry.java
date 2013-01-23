//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 01:42:02 PM EST 
//


package org.ed_fi._0100;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * GradebookEntry record with key fields: GradebookEntryType, DateAssigned, SectionReference (StateOrganizationId, UniqueSectionCode).  Changed types of LearningStandardReference, LearningObjectiveReference, SectionReference and GradingPeriodReference to SLC reference types.
 * 
 * <p>Java class for SLC-GradebookEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SLC-GradebookEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ComplexObjectType">
 *       &lt;sequence>
 *         &lt;element name="GradebookEntryType" type="{http://ed-fi.org/0100}GradebookEntryType"/>
 *         &lt;element name="DateAssigned" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Description" type="{http://ed-fi.org/0100}Description" minOccurs="0"/>
 *         &lt;element name="LearningStandardReference" type="{http://ed-fi.org/0100}SLC-LearningStandardReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LearningObjectiveReference" type="{http://ed-fi.org/0100}SLC-LearningObjectiveReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SectionReference" type="{http://ed-fi.org/0100}SLC-SectionReferenceType"/>
 *         &lt;element name="GradingPeriodReference" type="{http://ed-fi.org/0100}SLC-GradingPeriodReferenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SLC-GradebookEntry", propOrder = {
    "gradebookEntryType",
    "dateAssigned",
    "description",
    "learningStandardReference",
    "learningObjectiveReference",
    "sectionReference",
    "gradingPeriodReference"
})
public class SLCGradebookEntry
    extends ComplexObjectType
{

    @XmlElement(name = "GradebookEntryType", required = true)
    protected String gradebookEntryType;
    @XmlElement(name = "DateAssigned", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateAssigned;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "LearningStandardReference")
    protected List<SLCLearningStandardReferenceType> learningStandardReference;
    @XmlElement(name = "LearningObjectiveReference")
    protected List<SLCLearningObjectiveReferenceType> learningObjectiveReference;
    @XmlElement(name = "SectionReference", required = true)
    protected SLCSectionReferenceType sectionReference;
    @XmlElement(name = "GradingPeriodReference")
    protected SLCGradingPeriodReferenceType gradingPeriodReference;

    /**
     * Gets the value of the gradebookEntryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradebookEntryType() {
        return gradebookEntryType;
    }

    /**
     * Sets the value of the gradebookEntryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradebookEntryType(String value) {
        this.gradebookEntryType = value;
    }

    /**
     * Gets the value of the dateAssigned property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateAssigned() {
        return dateAssigned;
    }

    /**
     * Sets the value of the dateAssigned property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateAssigned(XMLGregorianCalendar value) {
        this.dateAssigned = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the learningStandardReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the learningStandardReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLearningStandardReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SLCLearningStandardReferenceType }
     * 
     * 
     */
    public List<SLCLearningStandardReferenceType> getLearningStandardReference() {
        if (learningStandardReference == null) {
            learningStandardReference = new ArrayList<SLCLearningStandardReferenceType>();
        }
        return this.learningStandardReference;
    }

    /**
     * Gets the value of the learningObjectiveReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the learningObjectiveReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLearningObjectiveReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SLCLearningObjectiveReferenceType }
     * 
     * 
     */
    public List<SLCLearningObjectiveReferenceType> getLearningObjectiveReference() {
        if (learningObjectiveReference == null) {
            learningObjectiveReference = new ArrayList<SLCLearningObjectiveReferenceType>();
        }
        return this.learningObjectiveReference;
    }

    /**
     * Gets the value of the sectionReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCSectionReferenceType }
     *     
     */
    public SLCSectionReferenceType getSectionReference() {
        return sectionReference;
    }

    /**
     * Sets the value of the sectionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCSectionReferenceType }
     *     
     */
    public void setSectionReference(SLCSectionReferenceType value) {
        this.sectionReference = value;
    }

    /**
     * Gets the value of the gradingPeriodReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCGradingPeriodReferenceType }
     *     
     */
    public SLCGradingPeriodReferenceType getGradingPeriodReference() {
        return gradingPeriodReference;
    }

    /**
     * Sets the value of the gradingPeriodReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCGradingPeriodReferenceType }
     *     
     */
    public void setGradingPeriodReference(SLCGradingPeriodReferenceType value) {
        this.gradingPeriodReference = value;
    }

}
