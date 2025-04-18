package utils;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
    private static JSONParser parser = new JSONParser();

    public static String getValue(String filePath, String key) {
        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            String value = (String) jsonObject.get(key);
            return value;
        } catch (Exception e) {
            return null;
        }
    }
} 