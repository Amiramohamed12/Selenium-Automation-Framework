package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;



public class JsonReader {
    private final String TEST_DATA_PATH = "src/test/resources/test-data/";
    String jsonReader;
    String jsonFileName;

    /*
     * Constructor:
     * - Takes JSON file name
     * - Reads file from disk
     * - Converts it into JSON string for later querying
     */
    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;

        try {
            // Step 1: Read JSON file from path
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH + jsonFileName + ".json"));
            // Step 2: Convert JSON object to string (used by JsonPath later)
            jsonReader = data.toJSONString();

        } catch(Exception e) {
            // If file not found or invalid JSON format
            LogsManager.error("Error reading JSON file: ", jsonFileName, " - ", e.getMessage());
            jsonReader="{}"; // Set to empty JSON to prevent null pointer exceptions later
        }
    }

    /*
     * Extracts specific data from JSON using JsonPath expression
     *
     * Example:
     * jsonPath = "$.user.name"
     */
    public String getJsonData(String jsonPath){
        try {
            // Reads value from JSON string using JsonPath query
            return JsonPath.read(jsonReader, jsonPath);

        } catch(Exception e) {
            LogsManager.error("Error extracting data from JSON with path: ", jsonPath, " - ", e.getMessage());
            return "";
        }
    }
}

/*
===========================================================
IMPORTANT TEST DATA STRATEGY NOTES (FOR MEMORY)
===========================================================

1. STATIC TEST DATA (BEST APPROACH)
   - Predefined JSON snapshots
   - Example: saved database state or fixed JSON files
   - Pros: stable, repeatable tests

2. DYNAMIC TEST DATA (USED WHEN NECESSARY)
   2.1 Database Queries
       - Create / delete test data before & after tests
       - Requires cleanup (CRUD cycle)

   2.2 API Calls
       - Create test data through APIs
       - Issues: network dependency, slower execution, flaky tests

   2.3 UI Setup
       - Used when no backend access exists
       - Slow and less reliable

IMPORTANT RULE:
→ Always clean up after tests
→ Avoid data pollution between test runs
===========================================================
*/



