package lyzer.web.tech.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lyzer.web.tech.config.LocalConfig;
import lyzer.web.tech.logs.ConsoleLogger;
import lyzer.web.tech.reader.JsonReader;
import lyzer.web.tech.responses.ScraperDataResponse;
import lyzer.web.tech.responses.ScraperQueueResponse;
import lyzer.web.tech.responses.ScraperVersionResponse;
import lyzer.web.tech.writer.JsonWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;


/**
 * Client for the scraper service.
 */
public final class ScraperClient extends Client {

    /**
     * The status code for a successful request.
     */
    private static final Integer STATUS_OK = 200;

    /**
     * Local configuration.
     */
    private final LocalConfig localConfig;

    /**
     * Constructor.
     *
     * @param config Local configuration.
     */
    public ScraperClient(final LocalConfig config) {
        this.localConfig = config;
    }

    /**
     * The update interval for the data.
     */
    private static final int UPDATE_INTERVAL = 30 * 60000;


    /**
     * This will update the data for the given params. This will only update
     * the data if the data has changed.
     *
     * @param params The params to send to the scraper service.
     * @return True if the data was updated otherwise false.
     */
    public boolean updateData(final String params) {
        ConsoleLogger logger = new ConsoleLogger();
        try {
            String fileName = params.replace("/", "") + ".json";
            JsonReader jsonReader = new JsonReader(fileName);
            ScraperDataResponse dataResponse = getData(params);
            HashMap<String, Object> data = dataResponse.getData();
            Integer statusCode = dataResponse.getStatus();
            String fileContent = jsonReader.readFile();
            Gson gson = new Gson();
            HashMap<String, Object> fileMap =
                    gson.fromJson(fileContent, HashMap.class);
            if (
                    Objects.equals(statusCode, STATUS_OK)
                            && !fileMap.equals(data)
            ) {
                JsonWriter jsonWriter = new JsonWriter(fileName);
                jsonWriter.writeFile(dataResponse.getData());
                return true;
            }
            return false;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            logger.log(
                    "Failed to update " + params.replace("/", ""),
                    "WARNING"
            );
            return false;
        }
    }

    /**
     * This will update the data for the given params. This will only update
     * the data if the data has changed.
     *
     * @return True if the data was updated otherwise false.
     */
    public boolean updateQueue() {
        ConsoleLogger logger = new ConsoleLogger();
        try {
            JsonReader jsonReader = new JsonReader("queue.json");
            String fileContent = jsonReader.readFile();
            ScraperQueueResponse dataResponse = getQueue();
            Integer statusCode = dataResponse.getStatus();
            String data = Arrays.toString(dataResponse.getData());
            if (
                    Objects.equals(statusCode, STATUS_OK)
                            && !fileContent.equals(data)
            ) {
                JsonWriter jsonWriter = new JsonWriter("queue.json");
                jsonWriter.writeFile(dataResponse.getData());
                return true;
            }
            return false;
        } catch (IOException | InterruptedException exception) {
            logger.log("Failed to update queue", "WARNING");
            return false;
        }
    }

    /**
     * This will update the version key for the Scraper service on this
     * service. This is so that this service can know when the other service
     * was updated.
     *
     * @return True if the version was updated otherwise false.
     */
    public boolean updateVersion() {
        ConsoleLogger logger = new ConsoleLogger();
        try {
            ScraperVersionResponse versionResponse = getVersion();
            Integer statusCode = versionResponse.getStatus();
            String scraperVersion = localConfig.getScraperVersion();
            if (
                    !scraperVersion.equals(versionResponse.getData())
                            && Objects.equals(statusCode, STATUS_OK)
            ) {
                localConfig.setScraperVersion(versionResponse.getData());
                JsonWriter jsonWriter = new JsonWriter("localConfig.json");
                jsonWriter.writeFile(localConfig);
                return true;
            }
            return false;
        } catch (IOException | InterruptedException exception) {
            logger.log("Failed to update scraper version", "WARNING");
            return false;
        }
    }

    /**
     * Get the version of the scraper service.
     *
     * @return Scraper version response.
     * @throws IOException If an error occurs while sending the request.
     * @throws InterruptedException If the thread is interrupted while waiting
     * for the response.
     */
    public ScraperVersionResponse getVersion(
    ) throws IOException, InterruptedException {
        URI uri = URI.create(localConfig.getScraperIp() + "/");
        HttpRequest request = createGetRequest(uri);
        HttpResponse<String> resp = sendRequest(request);
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.readValue(resp.body(), ScraperVersionResponse.class);
    }

    /**
     * Get the requested data from the scraper service.
     *
     * @return The response from the scraper service.
     * @throws IOException If an error occurs while sending the request.
     * @throws InterruptedException If the thread is interrupted while waiting
     */
    public ScraperQueueResponse getQueue(
    ) throws IOException, InterruptedException {
        URI uri = URI.create(localConfig.getScraperIp() + "/queue");
        HttpRequest request = createGetRequest(uri);
        HttpResponse<String> resp = sendRequest(request);
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.readValue(resp.body(), ScraperQueueResponse.class);
    }

    /**
     * Get the requested data from the scraper service.
     *
     * @param params The params to send to the scraper service.
     * @return The response from the scraper service.
     * @throws IOException If an error occurs while sending the request.
     * @throws InterruptedException If the thread is interrupted while waiting
     */
    public ScraperDataResponse getData(
            final String params
    ) throws IOException, InterruptedException {
        URI uri = URI.create(localConfig.getScraperIp() + "/data/" + params);
        HttpRequest request = createGetRequest(uri);
        HttpResponse<String> resp = sendRequest(request);
        ObjectMapper objMapper = new ObjectMapper();
        return objMapper.readValue(resp.body(), ScraperDataResponse.class);
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            ConsoleLogger logger = new ConsoleLogger();
            logger.log("Updating(This might take a while)...", "UPDATE");

            if (updateVersion()) {
                logger.log("Updated Scraper Version", "UPDATE");
            }

            if (updateQueue()) {
                logger.log("Updated Queue", "UPDATE");
            }

            String[] array = new String[]{
                    "seasons",
                    "races",
                    "fastest_laps",
                    "pitstops",
                    "starting_grids",
                    "qualifying",
                    "firstPractice",
                    "secondPractice",
                    "thirdPractice",
                    "drivers",
                    "constructors",
                    "sprints",
                    "sprint_grids"
            };

            for (String category : array) {
                if (updateData(category)) {
                    logger.log("Updated " + category, "UPDATE");
                }
            }

            logger.log("Update complete", "UPDATE");

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException exception) {
                logger.log("Unexpected Error");
                running = false;
            }
        }
    }
}
