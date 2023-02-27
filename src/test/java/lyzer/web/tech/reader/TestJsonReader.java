package lyzer.web.tech.reader;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class TestJsonReader {
    @Test
    public void testReadFile() {
        JsonReader jsonReader = new JsonReader("globalConfig.json");
        try {
            String json = jsonReader.readFile();
            JSONObject expected = new JSONObject("{\"placeholder\":\"placeholder\"}");
            JSONObject actual = new JSONObject(json);
            assertEquals(expected.toString(), actual.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testReadFileNotFound() {
        JsonReader jsonReader = new JsonReader("globalConfig2.json");
        try {
            jsonReader.readFile();
            fail();
        } catch (Exception e) {
            assertEquals("File not found.", e.getMessage());
        }
    }
}
