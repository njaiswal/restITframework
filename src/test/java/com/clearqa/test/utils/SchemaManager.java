package com.clearqa.test.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class SchemaManager {
    private static HashMap<String, JsonSchema> map = new HashMap<String, JsonSchema>();
    private static Map<String, String> schema_layout = new HashMap<String,String>(){{
        put("liveEquities", "/schemas/liveEquities_schema.json");
        put("equities", "/schemas/equities_schema.json");
    }};

    public static void loadSchema(String service, String schema_path)
            throws JsonProcessingException, IOException, ProcessingException {

        JsonNode schema_file = JsonLoader.fromResource(schema_path);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schema_file);

        map.put(service, schema);
    }

    public static void loadSchemas() throws JsonProcessingException, IOException, ProcessingException {
        for(String key: schema_layout.keySet()){
            String path = schema_layout.get(key);
            loadSchema(key, path);
        }
    }

    public static JsonSchema getSchema(String url) {

        String schema_id = "none";

        // Get appropriate schema file for url
        if(url.matches("^.*/equities$")){
            schema_id = "equities";
        }

        if(url.matches("^.*/live$")){
            schema_id = "liveEquities";
        }

        if(schema_id.equals("none")) {
            Assert.fail("Could not determine schema file for url: " + url);
        }

        if(map.containsKey(schema_id)) {
            Reporter.log("Checking schema against: " +  schema_id + "<br>");
            return map.get(schema_id);
        } else {
            Assert.fail("Could not find schema for " + schema_id);
        }
        return null;
    }
}
