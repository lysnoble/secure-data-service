/****** Script for SelectTopNRows command from SSMS  ******/
SELECT sasr.StudentUSI
      ,sasr.AssessmentTitle
      ,sasr.AcademicSubjectTypeId
      ,sasr.AssessedGradeLevelTypeId
      ,sasr.Version
      ,sasr.AdministrationDate
      ,armt.CodeValue as AssessmentReportingMethodType
      ,sasr.Result
  FROM EdFi.edfi.StudentAssessmentScoreResult sasr
  LEFT JOIN EdFi.edfi.AssessmentReportingMethodType armt ON sasr.AssessmentReportingMethodTypeId = armt.AssessmentReportingMethodTypeId
  ORDER BY sasr.StudentUSI, sasr.AssessmentTitle, sasr.AcademicSubjectTypeId, sasr.AssessedGradeLevelTypeId, sasr.Version, sasr.AdministrationDate
