//
// Copyright 2012 Shared Learning Collaborative, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

db.runCommand( { enablesharding : "sli" } );
db.runCommand( { shardcollection : "sli.assessment", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.attendance", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.calendarDate", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.cohort", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.competencyLevelDescriptor", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.course", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.courseOffering", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.courseSectionAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.disciplineAction", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.disciplineIncident", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.educationOrganization", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.educationOrganizationAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.educationOrganizationSchoolAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.grade", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.gradebookEntry", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.gradingPeriod", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.graduationPlan", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.learningObjective", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.learningStandard", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.parent", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.program", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.reportCard", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.section", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.sectionAssessmentAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.sectionSchoolAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.session", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.sessionCourseAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.staff", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.staffCohortAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.staffEducationOrganizationAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.staffProgramAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.student", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentAcademicRecord", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentAssessmentAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentCohortAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentCompetency", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentCompetencyObjective", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentDisciplineIncidentAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentParentAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentProgramAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentSectionAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentSectionGradebookEntry", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentSchoolAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.studentTranscriptAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.teacherSchoolAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
db.runCommand( { shardcollection : "sli.teacherSectionAssociation", key : {"metaData.tenantId" : 1, "_id" : 1} } );
