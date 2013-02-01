/*
 * Copyright 2012-2013 inBloom, Inc. and its affiliates.
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
// Generated on: 2012.04.20 at 03:09:04 PM EDT 
//


package org.slc.sli.sample.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Encapsulates the possible attributes that can be used to lookup the identity of sections at a school. 
 * 
 * <p>Java class for SectionIdentityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SectionIdentityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="StateOrganizationId" type="{http://ed-fi.org/0100}IdentificationCode"/>
 *           &lt;element name="EducationOrgIdentificationCode" type="{http://ed-fi.org/0100}EducationOrgIdentificationCode" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="UniqueSectionCode" type="{http://ed-fi.org/0100}UniqueSectionCode"/>
 *           &lt;sequence>
 *             &lt;choice>
 *               &lt;element name="CourseCode" type="{http://ed-fi.org/0100}CourseCode"/>
 *               &lt;element name="LocalCourseCode" type="{http://ed-fi.org/0100}LocalCourseCode"/>
 *             &lt;/choice>
 *             &lt;element name="SchoolYear" type="{http://ed-fi.org/0100}SchoolYearType"/>
 *             &lt;element name="Term" type="{http://ed-fi.org/0100}TermType"/>
 *             &lt;element name="ClassPeriodName" type="{http://ed-fi.org/0100}ClassPeriodNameType"/>
 *             &lt;element name="Location" type="{http://ed-fi.org/0100}ClassroomIdentificationCode"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SectionIdentityType", propOrder = {
    "stateOrganizationIdOrEducationOrgIdentificationCode",
    "uniqueSectionCode",
    "courseCode",
    "localCourseCode",
    "schoolYear",
    "term",
    "classPeriodName",
    "location"
})
public class SectionIdentityType {

    @XmlElements({
        @XmlElement(name = "StateOrganizationId", type = String.class),
        @XmlElement(name = "EducationOrgIdentificationCode", type = EducationOrgIdentificationCode.class)
    })
    protected List<Object> stateOrganizationIdOrEducationOrgIdentificationCode;
    @XmlElement(name = "UniqueSectionCode")
    protected String uniqueSectionCode;
    @XmlElement(name = "CourseCode")
    protected CourseCode courseCode;
    @XmlElement(name = "LocalCourseCode")
    protected String localCourseCode;
    @XmlElement(name = "SchoolYear")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String schoolYear;
    @XmlElement(name = "Term")
    protected TermType term;
    @XmlElement(name = "ClassPeriodName")
    protected String classPeriodName;
    @XmlElement(name = "Location")
    protected String location;

    /**
     * Gets the value of the stateOrganizationIdOrEducationOrgIdentificationCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateOrganizationIdOrEducationOrgIdentificationCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateOrganizationIdOrEducationOrgIdentificationCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link EducationOrgIdentificationCode }
     * 
     * 
     */
    public List<Object> getStateOrganizationIdOrEducationOrgIdentificationCode() {
        if (stateOrganizationIdOrEducationOrgIdentificationCode == null) {
            stateOrganizationIdOrEducationOrgIdentificationCode = new ArrayList<Object>();
        }
        return this.stateOrganizationIdOrEducationOrgIdentificationCode;
    }

    /**
     * Gets the value of the uniqueSectionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueSectionCode() {
        return uniqueSectionCode;
    }

    /**
     * Sets the value of the uniqueSectionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueSectionCode(String value) {
        this.uniqueSectionCode = value;
    }

    /**
     * Gets the value of the courseCode property.
     * 
     * @return
     *     possible object is
     *     {@link CourseCode }
     *     
     */
    public CourseCode getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the value of the courseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseCode }
     *     
     */
    public void setCourseCode(CourseCode value) {
        this.courseCode = value;
    }

    /**
     * Gets the value of the localCourseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalCourseCode() {
        return localCourseCode;
    }

    /**
     * Sets the value of the localCourseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalCourseCode(String value) {
        this.localCourseCode = value;
    }

    /**
     * Gets the value of the schoolYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * Sets the value of the schoolYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchoolYear(String value) {
        this.schoolYear = value;
    }

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link TermType }
     *     
     */
    public TermType getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermType }
     *     
     */
    public void setTerm(TermType value) {
        this.term = value;
    }

    /**
     * Gets the value of the classPeriodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassPeriodName() {
        return classPeriodName;
    }

    /**
     * Sets the value of the classPeriodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassPeriodName(String value) {
        this.classPeriodName = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

}
