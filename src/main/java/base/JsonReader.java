package base;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {

    // Reads the JSON file and returns a JSONObject
    public static JSONObject readJson(String filePath) {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                System.err.println("Error: JSON file not found at " + filePath);
                return null;
            }
            
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new FileReader(filePath));
        } catch (Exception e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }

    // Gets the value of a key from the JSON file
    public static String getValue(String filePath, String key) {
        JSONObject jsonObject = readJson(filePath);
        if (jsonObject == null) {
            System.err.println("Error: JSON object is null. File might be missing or corrupted.");
            return null;
        }

        Object value = jsonObject.get(key);
        if (value == null) {
            System.err.println("Error: Key '" + key + "' not found in JSON.");
            return null;
        }

        System.out.println("Retrieved from JSON -> " + key + ": " + value);
        return value.toString();
    }
}
