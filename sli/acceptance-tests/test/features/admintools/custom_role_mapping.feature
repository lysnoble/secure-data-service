@RALLY_US176
@RALLY_US174
@RALLY_US3331
@RALLY_US2669
Feature: Custom Role Mapping Tool
As an SEA/LEA  Admin, I would like to have the Complex Role Mapping admin tool, so that I can map lists of SEA/LEA Directory roles to their associated SLI Access Rights.

Background:
Given I have an open web browser
When I navigate to the Custom Role Mapping Page
And I was redirected to the "Simple" IDP Login page

@production
Scenario: Reset to default role to right mapping
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I click on the Reset Mapping button
And I got a warning message saying "Are you sure you want to reset the mappings to factory defaults? This will remove any custom defined roles!"
When I click 'OK' on the warning message
Then the Leader, Educator, Aggregate Viewer and IT Administrator roles are now only mapped to themselves

@production
Scenario: Create new group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I click on the Add Group button
And I type the name "New Custom" in the Group name textbox
When I add the right "READ_GENERAL" to the group "New Custom"   
And I add the role "Dummy" to the group "New Custom"
And I hit the save button
Then the group "New Custom" contains the roles "Dummy"
And the group "New Custom" contains the rights "Read General"

@production
Scenario: Add role to existing group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I create a new role <Role> to the group <Group> that allows <User> to access the API
| Group              | Role        | User      |
| "Educator"         | "Teacher"   | "teacher" |
| "Leader"           | "Principal" | "prince"  |
| "IT Administrator" | "Admin"     | "root"    |
| "Aggregate Viewer" | "Observer"  | "bigbro"  |
| "New Custom"       | "Custom"    | "custom"  |
Then I see the mapping in the table
And That user can now access the API

@production
Scenario: Add rights to group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
And the user "custom" can access the API with rights "Read General"
And I edit the group "New Custom"
When I add the right "WRITE_GENERAL" to the group "New Custom"
And I hit the save button
Then the group "New Custom" contains the rights "Read and Write General"
And I wait for 5 seconds
And the user "custom" can access the API with rights "Read and Write General"

@production
Scenario: Remove rights from group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the group "New Custom"
When I remove the right "WRITE_GENERAL" from the group "New Custom"
And I hit the save button
Then the group "New Custom" contains the rights "Read General"
And I wait for 5 seconds
And the user "custom" can access the API with rights "Read General"
When I edit the group "New Custom"
When I remove the right "READ_GENERAL" from the group "New Custom"
#And I hit the save button
Then I am informed that I must have at least one role and right in the group
And the user "custom" can access the API with rights "Read General"

@production
Scenario: Remove role from group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I remove the role <Role> from the group <Group> that denies <User> access to the API
| Group              | Role        | User      |
| "Educator"         | "Teacher"   | "teacher" |
| "Leader"           | "Principal" | "prince"  |
| "IT Administrator" | "Admin"     | "root"    |
| "Aggregate Viewer" | "Observer"  | "bigbro"  |
| "New Custom"       | "Custom"    | "custom"  |
Then I no longer see that mapping in the table
And That user can no longer access the API

@production
Scenario: Cannot remove last role in group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the group "New Custom"
When I remove the role "Dummy" from the group "New Custom"
#And I hit the save button
Then I am informed that I must have at least one role and right in the group

@production
Scenario: Remove group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I remove the group "New Custom"
Then the group "New Custom" no longer appears on the page

@production
Scenario: Cannot add duplicate rights within a group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the rights for the group <Group> to include the duplicate right <Right>
| Right             | Group              |
| "Read General"    | "Educator"         |
| "Write General"   | "IT Administrator" |
| "Read Restricted" | "Leader"           |
Then I cannot find the right in the dropdown

@production
Scenario: Cannot add duplicate roles
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the roles for the group <Group> to include the duplicate role <Role>
| Role               | Group              |
| "Educator"         | "Educator"         |
| "Educator"         | "IT Administrator" |
| "Aggregate Viewer" | "Leader"           |
Then I am informed that "Role names must be unique across all groups"

@production
Scenario: Cancel button discards any unsaved changes for a group
When I submit the credentials "sunsetadmin" "sunsetadmin1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the group "Educator"
When I add the right "WRITE_GENERAL" to the group "Educator"
And I add the role "Teacher" to the group "Educator"
And I click the cancel button
Then the group "Educator" contains the rights "Read General Public and Aggregate"
And the group "Educator" contains the roles "Educator"

@sandboxy
Scenario: Developer modifies roles in their tenant without affecting other tenant
When I submit the credentials "developer-email@slidev.org" "test1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I remove the group "Educator"
Then the group "Educator" no longer appears on the page
And the user "linda.kim" in tenant "developer-email" can access the API with rights "none"
And the user "linda.kim" in tenant "sandboxadministrator" can access the API with rights "IT Administrator"
When I click on the Add Group button
And I type the name "New Custom" in the Group name textbox
When I add the right "READ_GENERAL" to the group "New Custom"   
And I add the role "Educator" to the group "New Custom"
And I hit the save button
Then the group "New Custom" contains the roles "Educator"
And the group "New Custom" contains the rights "Read General"
And the user "linda.kim" in tenant "developer-email" can access the API with rights "Read General"
And the user "linda.kim" in tenant "sandboxadministrator" can access the API with rights "IT Administrator"

@sandboxy
Scenario: Sandbox reset to defaults
When I submit the credentials "sandboxdeveloper" "sandboxdeveloper1234" for the "Simple" login page
Then I have navigated to my Custom Role Mapping Page
When I edit the group "IT Administrator"
When I add the right "AGGREGATE_WRITE" to the group "IT Administrator"   
And I hit the save button
And I wait for 5 seconds
And the user "linda.kim" in tenant "sandboxadministrator" can access the API with rights "all defaults"
And the user "linda.kim" in tenant "developer-email" can access the API with rights "Read General"
When I click on the Reset Mapping button
And I got a warning message saying "Are you sure you want to reset the mappings to factory defaults? This will remove any custom defined roles!"
When I click 'OK' on the warning message
Then the Leader, Educator, Aggregate Viewer and IT Administrator roles are now only mapped to themselves
And the user "linda.kim" in tenant "sandboxadministrator" can access the API with rights "IT Administrator"
And the user "linda.kim" in tenant "developer-email" can access the API with rights "Read General"

	
@wip
Scenario: Name validation for role and group names
#This requirement is questionable?  Redone to just make sure input is sanitized?
Given Something 
When something
Then something
