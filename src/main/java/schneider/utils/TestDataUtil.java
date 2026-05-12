package schneider.utils;

import java.io.IOException;
import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataUtil {
	private static JsonNode testData;
	 static {
	        try {
	            ObjectMapper mapper = new ObjectMapper();

	            String path = System.getProperty("user.dir")
	                    + "/src/main/resources/testdata.json";

	            testData = mapper.readTree(new File(path));

	        } catch (IOException e) {
	            throw new RuntimeException("Failed to load test data file", e);
	        }
	    }
	 public static String getData(String jsonPath) {

	        String[] keys = jsonPath.split("\\.");

	        JsonNode node = testData;

	        for (String key : keys) {
	            node = node.get(key);

	            if (node == null) {
	                throw new RuntimeException(
	                        "Invalid test data path: " + jsonPath);
	            }
	        }

	        return node.asText();
	    }
	 public static int getIntData(String jsonPath) {
	        return Integer.parseInt(getData(jsonPath));
	    }
	 public static boolean getBooleanData(String jsonPath) {
	        return Boolean.parseBoolean(getData(jsonPath));
	    }
}
