package lyzer.web.tech.clients;

import lyzer.web.tech.config.LocalConfig;
import lyzer.web.tech.reader.JsonReader;
import lyzer.web.tech.responses.ScraperDataResponse;
import lyzer.web.tech.responses.ScraperQueueResponse;
import lyzer.web.tech.responses.ScraperVersionResponse;
import lyzer.web.tech.writer.JsonWriter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestScraperClient {
    private LocalConfig localConfig;

    public TestScraperClient() throws IOException {
        JsonReader jsonReader = new JsonReader("localConfig.json");
        String jsonString = jsonReader.readFile();
        this.localConfig = LocalConfig.converJSONToLocalConfig(jsonString);
    }

    @Test
    public void testGetVersion() {
        ScraperClient client = new ScraperClient(this.localConfig);
        try {
            ScraperVersionResponse response = client.getVersion();
            assertEquals("0.9.0", response.getData());
            assertEquals("Data retrieved successfully.", response.getMessage());
            assertEquals("success", response.getResult());
            assertEquals(Integer.valueOf(200), response.getStatus());
        } catch (IOException | InterruptedException e) {
            fail("Failed to get version");
        }
    }

    @Test
    public void testGetQueue() {
        ScraperClient client = new ScraperClient(this.localConfig);
        try {
            ScraperQueueResponse response = client.getQueue();
            assertEquals("Data retrieved successfully.", response.getMessage());
            assertEquals("success", response.getResult());
            assertEquals(Integer.valueOf(200), response.getStatus());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("Failed to get queue");
        }
    }

    @Test
    public void testGetData() {
        ScraperClient client = new ScraperClient(this.localConfig);
        try {
            ScraperDataResponse response = client.getData("seasons");
            assertEquals("Data retrieved successfully.", response.getMessage());
            assertEquals("success", response.getResult());
            assertEquals(Integer.valueOf(200), response.getStatus());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("Failed to get data");
        }
    }

    @Test
    public void testUpdateVersion() {
        try {
            ScraperClient client = new ScraperClient(this.localConfig);
            assertTrue(client.updateVersion());
            assertFalse(client.updateVersion());
            assertEquals("0.9.0", localConfig.getScraperVersion());
            localConfig.setScraperVersion("");
            JsonWriter jsonWriter = new JsonWriter("localConfig.json");
            jsonWriter.writeFile(localConfig);
            JsonReader jsonReader = new JsonReader("localConfig.json");
            String jsonString = jsonReader.readFile();
            this.localConfig = LocalConfig.converJSONToLocalConfig(jsonString);
        } catch (IOException exception) {
            fail();
        }
    }

    @Test
    public void testUpdateAll() {
        ScraperClient scraperClient = new ScraperClient(localConfig);
        Thread thread = new Thread(scraperClient);
        thread.start();
        thread.interrupt();
        assertEquals("", localConfig.getScraperVersion());
    }
}
