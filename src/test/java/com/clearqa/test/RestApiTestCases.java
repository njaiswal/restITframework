package com.clearqa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import com.clearqa.test.utils.Http2Json;
import com.clearqa.test.utils.SchemaManager;
import com.clearqa.test.utils.TestScenarios;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.report.ProcessingReport;
import org.testng.Reporter;
import org.testng.Assert;


public class RestApiTestCases {

    @BeforeClass
    public void oneTimeSetUp() throws Exception {
        SchemaManager.loadSchemas();
    }

    @DataProvider(name = "services", parallel = true)
    public Object[][] service_testcases() throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(TestScenarios.class));

        // TODO: Make this read services_testcases.yml from inside the target dir.
        TestScenarios testScenarios = (TestScenarios) yaml.load(new FileInputStream(new File("src/test/resources/testcases.yml")));
        return testScenarios.getDataProviderFormatedTestUrls();
    }

    @Test(dataProvider = "services", enabled = true)
    public void testServices(String q) throws Exception {

        JsonNode response = Http2Json.readJsonFromUrl(q);
        JsonSchema s = SchemaManager.getSchema(q);

        // Validate reponse againts json schema
        ProcessingReport report = s.validateUnchecked(response);
        Reporter.log("<pre>" + report.toString() + "</pre>");
        Assert.assertTrue(s.validInstance(response), "JSON response not valid against schema");
    }

    @AfterClass
    public void oneTimeTearDown() {

    }
}
