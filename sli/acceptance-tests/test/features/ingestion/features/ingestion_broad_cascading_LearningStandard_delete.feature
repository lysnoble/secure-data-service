@RALLY_US5180
Feature: Safe Deletion and Cascading Deletion

Background: I have a landing zone route configured
Given I am using local data store

@wip
Scenario: Delete LearningStandard with cascade = true
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningStandard"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|objectiveAssessment.body.learningObjectives       |1b0d13e233ef61ffafb613a8cc6930dfc0d29b92_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "BroadLearningStandardDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "BroadLearningStandardDelete.zip" is completed in database
	And I should see "Processed 1 records." in the resulting batch job file
    And I should not see an error log file created
	And I should not see a warning log file created
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database
@wip	
Scenario: Delete LearningStandard with cascade = false
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningStandard"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|assessmentItem.body.learningStandards             |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "SafeLearningStandardDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "SafeLearningStandardDelete.zip" is completed in database
    And a batch job log has been created
    And I should see "records deleted successfully: 0" in the resulting batch job file
    And I should see "records failed processing: 1" in the resulting batch job file
	And I should see "Not all records were processed completely due to errors." in the resulting batch job file
	And I should see "Processed 1 records." in the resulting batch job file
    And I should see "CORE_0066" in the resulting error log file for "InterchangeAssessmentMetadata.xml"
   	And I should not see a warning log file created
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database
@wip
Scenario: Delete Orphan LearningStandard with cascade = false
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningStandard"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|assessmentItem.body.learningStandards             |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "OrphanLearningStandardDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "OrphanLearningStandardDelete.zip" is completed in database
    And a batch job log has been created
    And I should see "Processed 1 records." in the resulting batch job file
    And I should see "records deleted successfully: 1" in the resulting batch job file
    And I should see "records failed processing: 0" in the resulting batch job file
    And I should not see an error log file created
   	And I should not see a warning log file created
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database
@wip
Scenario: Delete Orphan LearningStandard Ref with cascade = false
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningObjective"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|assessmentItem.body.learningStandards             |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "OrphanLearningStandardRefDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "OrphanLearningStandardRefDelete.zip" is completed in database
    And a batch job log has been created
    And I should see "Processed 1 records." in the resulting batch job file
    And I should see "records deleted successfully: 1" in the resulting batch job file
    And I should see "records failed processing: 0" in the resulting batch job file
    And I should not see an error log file created
   	And I should not see a warning log file created
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database
@wip
Scenario: Delete LearningStandard with default settings (Confirm that by default cascade = false, force = true and log violations = true)
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningStandard"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|assessmentItem.body.learningStandards             |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "ForceLearningStandardDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "ForceLearningStandardDelete.zip" is completed in database
    And a batch job log has been created
    And I should see "records deleted successfully: 1" in the resulting batch job file
    And I should see "child records deleted successfully: 0" in the resulting batch job file
    And I should see "records failed processing: 0" in the resulting batch job file
    And I should see "records not considered for processing: 0" in the resulting batch job file
    And I should see "All records processed successfully." in the resulting batch job file
    And I should see "Processed 1 records." in the resulting batch job file
    And I should not see an error log file created
    And I should see "CORE_0066" in the resulting warning log file for "InterchangeAssessmentMetadata.xml"
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database
@wip	
Scenario: Delete LearningStandard Reference with default settings (Confirm that by default cascade = false, force = true and log violations = true)
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "learningStandard" records like below in "Midgar" tenant. And I save this query as "learningStandard"
	|field                                             |value                                                |
	|_id                                               |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	Then there exist "1" "assessment" records like below in "Midgar" tenant. And I save this query as "assessment"
	|field                                             |value                                                |
	|assessmentItem.body.learningStandards             |b0e0b7466429d5d994f18da6982c3af25d1496c3_id          |
	And I save the collection counts in "Midgar" tenant
    And I post "ForceLearningStandardRefDelete.zip" file as the payload of the ingestion job
  	When zip file is scp to ingestion landing zone
    And a batch job for file "ForceLearningStandardRefDelete.zip" is completed in database
    And a batch job log has been created
    And I should see "records deleted successfully: 1" in the resulting batch job file
    And I should see "child records deleted successfully: 0" in the resulting batch job file
    And I should see "records failed processing: 0" in the resulting batch job file
    And I should see "records not considered for processing: 0" in the resulting batch job file
    And I should see "All records processed successfully." in the resulting batch job file
    And I should see "Processed 1 records." in the resulting batch job file
    And I should not see an error log file created
    And I should see "CORE_0066" in the resulting warning log file for "InterchangeAssessmentMetadata.xml"
	And I should not see "b0e0b7466429d5d994f18da6982c3af25d1496c3_id" in the "Midgar" database