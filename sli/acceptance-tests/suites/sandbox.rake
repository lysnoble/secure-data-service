############################################################
# API Sandbox Tests start
############################################################
desc "Run Sandbox mode Tests"
task :adminSandboxTests do
  @tags = ["~@wip", "@sandbox"]
  Rake::Task["adminToolsTests"].invoke
  Rake::Task["accountApprovalInterfaceTests"].invoke
  Rake::Task["accountApprovalTests"].invoke
end

desc "Run Sandbox API tests"
task :apiSandboxTests do
  @tags = ["~@wip", "@sandbox"]
  Rake::Task["securityTests"].invoke
end

desc "Run all sandbox tests"
task :sandboxTests => [:apiSandboxTests, :adminSandboxTests] do
  displayFailureReport()
  if $SUCCESS
    puts "Completed All Tests"
  else
    raise "Tests have failed"
  end
end
############################################################
# API Sandbox Tests end
############################################################


############################################################
# Account Approval tests start
############################################################
desc "Run Account Approval acceptance tests"
task :accountApprovalInterfaceTests => [:realmInitNoPeople] do
    runTests("test/features/apiV1/end_user_stories/sandbox/AccountApproval/prod_sandbox_AccountApproval_Inteface.feature")
end

desc "Run Account Approval Sandbox acceptance tests"
task :accountApprovalSandboxTests do
  @tags = ["~@wip", "@sandbox"]
  Rake::Task["accountApprovalInterfaceTests"].invoke
  Rake::Task["accountApprovalTests"].invoke
end
############################################################
# Account Approval tests end
############################################################

