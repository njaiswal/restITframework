restITframework: Rest Integration Test Framework
================================================

This test framework will give a simple way to verify that rest webservice is responding with right JSON responses.
All you need to provide is a couple of yml config files and JSON schema files for the expected JSON responses.
Adding new simple tests should not take more than 10 mins of your time.
Framework is flexible enough for you to add more advanced features such as Authentication headers and complex JSON response
validation which cannot be done via simple JSON schema files.

Steps to make use of this framework.
-----------------------------------

1. Git clone the project.
git clone git@github.com:njaiswal/restITframework.git restIT

2. Import the project in eclipse or your fav ID as existing maven project.

3. Replace src/test/resources/testcases.yml file with your own.
    - Make sure you change baseurl to map to base url of your rest service.
    - Add services which you want to test with/out prameters, this depends on your rest service. See examples in the file.

4. For each of the test case you have added in the above step, you need to add a schema map. 
   Replace file src/main/resources/schemaMap.yml with your own.
   Each schema map in this yml file has 3 fields:
      - id: This is just a name. Nothing more than a string that you understand and will be included in logs/reports
      - file: This is the JSON schema file path
      - regex: If the URL matches this regex, response of this URL will be validated againts above JSON schema file
      
5. In src/test/resoruces/schemas directory add all JSON schema files defined in schemaMap.yml file

Steps to run the test framework and view reports.
-------------------------------------------------

To run:
  
    mvn test
    
To view reports: 
    
    target/surefire-reports/index.html

Make sure to check the 'Reporter Output' tab for any JSON schema validation error reports.

Enjoy.
Let me know if you have any questions/suggestions.
