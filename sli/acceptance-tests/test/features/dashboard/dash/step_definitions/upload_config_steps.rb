When /^I enter the Configuration Area$/ do
  url = getBaseUrl() + "/service/config"
  @driver.get url
end

Then /^I am authorized to the Configuration Area$/ do
  assert(@driver.current_url.include?("/service/config"), "User is not on the service/config page")
end

When /^click Save$/ do
  clickButton("saveButton","id")
  begin
    #@driver.switch_to.alert.accept
    alert = @driver.switch_to.alert
    @alertMessage = alert.text
    puts @alertMessage
    alert.accept
  rescue
  end
end

When /^I paste Invalid json config into the text box$/ do
  invalid = "{config: 
          {
          \"listOfStudents\" :
          id : \"listOfStudents\",
          type : \"PANEL\",
          data :{
            lazy: true,
            entity: \"listOfStudents\",
            cacheKey: \"listOfStudents\"
          },
          root: 'students',
          items : [
            {name: \"Invalid View\", 
            items: 
            "
  putTextForConfigUpload(invalid)
end

Then /^I paste Valid json config into the text box$/ do
  valid = "{ config :
          {
          \"listOfStudents\" :
          {
            id : \"listOfStudents\",
            type : \"PANEL\",
            data :{
              lazy: true,
              entity: \"listOfStudents\",
              cacheKey: \"listOfStudents\"
            },
            root: 'students',
            items : [
              {name: \"Uploaded Custom View\", 
              items: [
                {name: \"My Student\", width: 150, field: \"name.fullName\", formatter:'restLink', params: {link:'service/layout/studentProfile/', target:\"_self\"}},
                {name: \"\", width: 60, field: \"programParticipation\", formatter: 'Lozenge'},
                {name: \"Absence Count For Testing\", field: \"attendances.absenceCount\", width:100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style:'color-widget-green'}, 6:{style: 'color-widget-yellow'}, 11:{style:'color-widget-red'}}}},
                {name: \"Tardy Count For Testing\", field: \"attendances.tardyCount\", width:100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style: 'color-widget-green'}, 6:{style:'color-widget-yellow'}, 11:{style:'color-widget-red'}}}}
              ]
              }
            ] 
          }
          ,
          \"studentProfile\" :
          {
            id : \"studentProfile\",
            type: \"LAYOUT\",
            data :{
              entity: \"student\",
              cacheKey: \"student\"
            }, 
            items: [
              {id : \"csi\", name: \"Student Info\", type: \"PANEL\"},
              {id: \"tab8\", name: \"Overview\",  type : \"TAB\", condition: {field: \"gradeLevel\", value: [\"Fourth Grade\", \"Fifth Grade\", \"Sixth Grade\", \"Seventh Grade\", \"Eighth grade\", \"Other\", \"Ungraded\", \"Not Available\"]}, items: [{id : \"contactInfo\", type: \"PANEL\"}, {id : \"enrollmentHist\", name: \"Student Enrollment Panel\", type: \"GRID\"}]},
              {id: \"tab1\", name: \"ELL\", type : \"TAB\", condition: {field: \"limitedEnglishProficiency\", value: [\"Limited\"]}, items: []}
            ]
          }
        }
        }"
        
  customConfig = "{ config :
  {
  \"listOfStudents\" :
  {
  id : \"listOfStudents\",
  type : \"PANEL\",
  data :{
    lazy: true,
    entity: \"listOfStudents\",
    cacheKey: \"listOfStudents\",
    params: {
      assessmentFilter: {\"ISAT Reading\": \"HIGHEST_EVER\", 
                         \"ISAT Writing\": \"MOST_RECENT_WINDOW\", 
                         \"DIBELS Nex\": \"MOST_RECENT_WINDOW\",
                         \"TRC\": \"MOST_RECENT_WINDOW\",
                         \"SAT Reading\": \"HIGHEST_EVER\",
                         \"SAT Writing\": \"HIGHEST_EVER\",
                         \"AP Language\": \"HIGHEST_EVER\",
                         \"AP Literature\": \"HIGHEST_EVER\" }
    }
  },
  root: 'students',
  items : [
    {name: \"Middle School ELA View\", 
     condition: {field: \"gradeLevel\", value: [\"Third grade\", \"Fourth grade\", \"Fifth grade\", \"Sixth grade\", \"Seventh grade\", \"Eighth grade\"]},
     items: [
      {name: \"Student\", width: 150, field: \"name.fullName\", formatter:'restLink', params: {link:'service/layout/studentProfile/', target:\"_self\"}},
      {name: \"\", width: 60, field: \"programParticipation\", formatter: 'Lozenge'},
      {name: \"ISAT Reading (highest ever)\", items:[
        {name: \"Perf. Lvl.\", field: \"assessments.ISAT Reading.perfLevel\", width:150, sorter: 'int', formatter: 'FuelGaugeWithScore', params:{name:'ISAT Reading', valueField:'Scale score', fieldName: \"ISATReading\", cutPoints:{ 5:{style:'color-widget-darkgreen'}, 4:{style:'color-widget-green'}, 3:{style:'color-widget-yellow'}, 2:{style:'color-widget-orange'}, 1:{style:'color-widget-red'}}}},
        {name: \"SS\", field: \"assessments.ISAT Reading.Scale score\", width:100, sorter: 'int'},
        {name: \"Lexile Score\", field: \"assessments.ISAT Reading.Other\", width:100}]},
      {name: \"ISAT Writing (most recent)\", items:[   
        {name: \"Perf. Lvl.\", field: \"assessments.ISAT Writing.perfLevel\", width:150, sorter: 'int', formatter: 'FuelGaugeWithScore', params:{name:'ISAT Writing', valueField:'Scale score', fieldName: \"ISATWriting\", cutPoints:{ 5:{style:'color-widget-darkgreen'}, 4:{style:'color-widget-green'}, 3:{style:'color-widget-yellow'}, 2:{style:'color-widget-orange'}, 1:{style:'color-widget-red'}}}},
        {name: \"SS\", field: \"assessments.ISAT Writing.Scale score\", width:100, sorter: 'int'}]},
      {name: \"Current Grades\", items:[
               {name: \"Unit Test 1\", field: \"FallSemester2011-2012-0\", width:100, sorter: 'LettersAndNumbers', formatter: 'Grade'}
            ]
      },
      {name: \"Final Grades\", items:[
               {name: \"Last Semester\", field: \"SpringSemester2010-2011\", width:100, sorter: 'LetterGrade', formatter: 'TearDrop'},
               {name: \"2 Semesters ago\", field: \"FallSemester2010-2011\", width:100, sorter: 'LetterGrade', formatter: 'TearDrop'}
            ]
      },
      {name: \"Attendance (current school year)\", items:[   
        {name: \"Absence Count\", field: \"attendances.absenceCount\", width:100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style:'color-widget-green'}, 6:{style: 'color-widget-yellow'}, 11:{style:'color-widget-red'}}}},
        {name: \"Attendance Rate %\", field: \"attendances.attendanceRate\", width:100, sorter: 'int', formatter: 'CutPoint', params:{cutPoints:{89:{style:'color-widget-red'}, 94:{style: 'color-widget-yellow'}, 98:{style:'color-widget-green'}, 100:{style:'color-widget-darkgreen'}}}},
        {name: \"Tardy Count\", field: \"attendances.tardyCount\", width:100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style: 'color-widget-green'}, 6:{style:'color-widget-yellow'}, 11:{style:'color-widget-red'}}}},
        {name: \"Tardy Rate %\", field: \"attendances.tardyRate\", width:100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style: 'color-widget-green'}, 6:{style:'color-widget-yellow'}, 11:{style:'color-widget-red'}}}}]}
    ]},
    {name: \"Early Literacy View\",
     condition: {field: \"gradeLevel\", value: [\"Kindergarten\", \"First grade\", \"Second grade\", \"Third grade\"]}, 
     items: [
      {name: \"Student\", field: \"name.fullName\", width:150, formatter:'restLink', params: {link:'service/layout/studentProfile/', target:\"_self\"}},
      {name: \"\", width: 60, field: \"programParticipation\", formatter: 'Lozenge'},
      {name: \"DIBELS Next\", items:[
        {name: \"Perf. Lvl.\", field: \"assessments.DIBELS Next.perfLevel\", width:100}]},
      {name: \"Reading\", items:[          
        {name: \"RL\", field: \"assessments.TRC.readingLevel\", width:100},
        {name: \"Prof. Level\", field: \"assessments.TRC.profLevel\", width:100}]},
      {name: \"Attendance\", items:[   
        {name: \"Absence Count\", field: \"attendances.absenceCount\", sorter: 'int', width:100, formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style: 'color-widget-green'}, 6:{style: 'color-widget-yellow'}, 11:{style: 'color-widget-red'}}}},
        {name: \"Tardy Count\", field: \"attendances.tardyCount\", sorter: 'int', width:100, formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style: 'color-widget-green'}, 6:{style: 'color-widget-yellow'}, 11:{style: 'color-widget-red'}}}}]}
    ]},
    {name: \"College Ready ELA View\", 
     condition: {field: \"gradeLevel\", value: [\"Ninth grade\", \"Tenth grade\", \"Eleventh grade\", \"Twelfth grade\"]},
     items: [
      {name: \"Student\", field: \"name.fullName\", width:150, formatter:'restLink', params: {link:'service/layout/studentProfile/', target:\"_self\"}},
      {name: \"\", width: 60, field: \"programParticipation\", formatter: 'Lozenge'},
      {name: \"Reading Test Scores (Highest)\", items:[
        {name: \"SAT\", field: \"assessments.SAT Reading.x\", width:100, sorter: 'int'},
        {name: \"%ile\", field: \"assessments.SAT Reading.percentile\", width:100, sorter: 'int'}]},   
      {name: \"Writing Test Scores (Highest)\", items:[          
        {name: \"SAT\", field: \"assessments.SAT Writing.x\", width:100, sorter: 'int'},
        {name: \"%ile\", field: \"assessments.SAT Writing.percentile\", width:100, sorter: 'int'}]},
      {name: \"AP Eng. Exam Scores (Highest)\", items:[          
        {name: \"Lang.\", field: \"assessments.AP Language.x\", width:100},
        {name: \"Lit.\", field: \"assessments.AP Literature.x\", width:100}]},                 
      {name: \"Final Grades\", items:[
        {name: \"Last Semester\", field: \"SpringSemester2010-2011\", width:100, sorter: 'LetterGrade', formatter: 'TearDrop'},
        {name: \"2 Semesters ago\", field: \"FallSemester2010-2011\", width:100, sorter: 'LetterGrade', formatter: 'TearDrop'}]},
      {name: \"Attendance\", items:[   
        {name: \"Absence Count\", field: \"attendances.absenceCount\", width: 100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style:'color-widget-green'}, 6:{style: 'color-widget-yellow'}, 11:{style: 'color-widget-red'}}}},
        {name: \"Attendance Rate %\", field: \"attendances.attendanceRate\", width: 100, sorter: 'int', formatter: 'CutPoint', params:{cutPoints:{89:{style: 'color-widget-red'}, 94:{style:'color-widget-yellow'}, 98:{style:'color-widget-green'}, 100:{style: 'color-widget-darkgreen'}}}},
        {name: \"Tardy Count\", field: \"attendances.tardyCount\", width: 100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style:'color-widget-green'}, 6:{style:'color-widget-yellow'}, 11:{style: 'color-widget-red'}}}},
        {name: \"Tardy Rate %\", field: \"attendances.tardyRate\", width: 100, sorter: 'int', formatter: 'CutPointReverse', params:{cutPoints:{0:{style:'color-widget-darkgreen'}, 1:{style:'color-widget-green'}, 6:{style:'color-widget-yellow'}, 11:{style: 'color-widget-red'}}}}]}
    ]}
  ] 
}
,
\"studentProfile\" :
{
  id : \"studentProfile\",
  type: \"LAYOUT\",
  data :{
    entity: \"student\",
    cacheKey: \"student\"
  }, 
  items: [
    {id : \"csi\", name: \"Student Info\", type:\"PANEL\"},
    {id: \"tab7\", name: \"Elementary School Overview\",  type : \"TAB\", condition: {field: \"gradeLevel\", value: [\"Infant/toddler\", \"Early Education\", \"Preschool/Prekindergarten\", \"Transitional Kindergarten\", \"Kindergarten\", \"First Grade\", \"Second Grade\", \"Third Grade\", \"Other\", \"Ungraded\", \"Not Available\"]}, items: [{id : \"contactInfo\", type: \"PANEL\"}, {id : \"enrollmentHist\", name: \"Student Enrollment Panel\", type: \"GRID\"}]},
    {id: \"tab8\", name: \"Middle School Overview\",  type : \"TAB\", condition: {field: \"gradeLevel\", value: [\"Fourth Grade\", \"Fifth Grade\", \"Sixth Grade\", \"Seventh Grade\", \"Eighth grade\", \"Other\", \"Ungraded\", \"Not Available\"]}, items: [{id : \"contactInfo\", type: \"PANEL\"}, {id : \"enrollmentHist\", name: \"Student Enrollment Panel\", type: \"GRID\"}]},
    {id: \"tab9\", name: \"High School Overview\",  type : \"TAB\", condition: {field: \"gradeLevel\", value: [\"Ninth grade\", \"Tenth grade\", \"Eleventh grade\", \"Twelfth grade\", \"Adult Education\", \"Grade 13\", \"Postsecondary\", \"Other\", \"Ungraded\", \"Not Available\"]}, items: [{id : \"contactInfo\", type: \"PANEL\"}, {id : \"enrollmentHist\", name: \"Student Enrollment Panel\", type: \"GRID\"}]},
    {id: \"tab2\", name: \"Attendance and Discipline\", type : \"TAB\", items: [{id : \"attendanceHist\", type: \"GRID\"}]},
    {id: \"tabE\", name: \"Assessments\",  type : \"TAB\", items: [{id : \"assessmentHistDIBELS\", type: \"GRID\"}], condition: {field: \"gradeLevel\", value: [\"Infant/toddler\", \"Early Education\", \"Preschool/Prekindergarten\", \"Transitional Kindergarten\", \"Kindergarten\", \"First Grade\", \"Second Grade\", \"Third Grade\", \"Other\", \"Ungraded\", \"Not Available\"]}},
    {id: \"tabM\", name: \"Assessments\",  type : \"TAB\", items: [{id : \"assessmentHistISATR\", type: \"GRID\"}, {id : \"assessmentHistISATW\", type: \"GRID\"}], condition: {field: \"gradeLevel\", value: [\"Fourth Grade\", \"Fifth Grade\", \"Sixth Grade\", \"Seventh Grade\", \"Eighth grade\", \"Other\", \"Ungraded\", \"Not Available\"]}},
    {id: \"tabH\", name: \"Assessments\",  type : \"TAB\", items: [{id : \"assessmentHistAPE\", type: \"GRID\"}, {id : \"assessmentHistSATR\", type: \"GRID\"}, {id : \"assessmentHistSATW\", type: \"GRID\"}], condition: {field: \"gradeLevel\", value: [\"Ninth grade\", \"Tenth grade\", \"Eleventh grade\", \"Twelfth grade\", \"Adult Education\", \"Grade 13\", \"Postsecondary\", \"Other\", \"Ungraded\", \"Not Available\"]}},
    {id: \"tab4\", name: \"Grades and Credits\",  type : \"TAB\", items: [ {id : \"transcriptHistory\", type : \"PANEL\"} ]},
    {id: \"tab5\", name: \"Advanced Academics\",  type : \"TAB\", items: []},
    {id: \"tab10\", name: \"Daybreak District\",  type : \"TAB\", items: []},
    {id: \"tab1\", name: \"ELL\", type : \"TAB\", condition: {field: \"limitedEnglishProficiency\", value: [\"Limited\"]}, items: []}
  ]
}
,
\"assessmentHistAPE\" :
{
    id : \"assessmentHistAPE\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : AP English\",
    data : {
        cacheKey: 'assessmentHistAPE',
        params: { assessmentFamily: \"AP.AP Eng\"}
    }
}
,
\"assessmentHistDIBELS\" :
{
    id : \"assessmentHistDIBELS\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : DIBELS Next\",
    data : {
        cacheKey: 'assessmentHistDIBELS',
        params: { assessmentFamily: \"DIBELS Next.DIBELS Next Grade 1\"}
    }
}
,
\"assessmentHistISATR\" :
{
    id : \"assessmentHistISATR\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : ISAT Reading\",
    data : {
        cacheKey: 'assessmentHistISATR',
        params: { assessmentFamily: \"ISAT Reading\"}
    }
}
,
\"assessmentHistISATW\" :
{
    id : \"assessmentHistISATW\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : ISAT Writing\",
    data : {
        cacheKey: 'assessmentHistISATW',
        params: { assessmentFamily: \"ISAT Writing\"}
    }
}
,
\"assessmentHistSATR\" :
{
    id : \"assessmentHistSATR\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : SAT Reading\",
    data : {
        cacheKey: 'assessmentHistSATR',
        params: { assessmentFamily: \"SAT Reading\"}
    }
}
,
\"assessmentHistSATW\" :
{
    id : \"assessmentHistSATW\",
    parentId: \"assessmentHist\",
    type : \"GRID\",
    name : \"Test History : SAT Writing\",
    data : {
        cacheKey: 'assessmentHistSATW',
        params: { assessmentFamily: \"SAT Writing\"}
    }
}
}
  }
  "
  
  putTextForConfigUpload(customConfig)
end

When /^I logout$/ do
  # current logout functionaly means delete all the cookies
  @driver.manage.delete_all_cookies
end

Then /^I should be shown a success message$/ do
  assert(@alertMessage.include?("Success"), "Actual message: " + @alertMessage)
end

Then /^I should be shown a failure message$/ do
  assert(@alertMessage.include?("input should be a valid JSON string"), "Actual message: " + @alertMessage)
end

Then /^I reset custom config$/ do
  putTextForConfigUpload("{}")
end

Then /^I see an error$/ do
  @driver.find_element(:class,"error-container")
end

def putTextForConfigUpload(uploadText)
  textBoxId = "jsonText"
  textArea = @driver.find_element(:id, textBoxId)
  #this clears the text area
  textArea.clear()
  putTextToField(uploadText, textBoxId ,"id")
end
