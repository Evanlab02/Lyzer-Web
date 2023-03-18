package lyzer.web.tech.clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import lyzer.web.tech.responses.ScraperArrayResponse;
import lyzer.web.tech.responses.ScraperDataResponse;
import lyzer.web.tech.responses.ScraperQueueResponse;
import lyzer.web.tech.responses.ScraperResponse;
import lyzer.web.tech.responses.ScraperVersionResponse;
import lyzer.web.tech.server.Manage;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * Client for the scraper service.
 */
public final class ScraperClient extends Client {

    /**
     * Local configuration.
     */
    private final String destinationUrl;

    /**
     * Constructor.
     *
     */
    public ScraperClient() {
        destinationUrl = Manage.getScraperIp();
    }

    /**
     * Get the version of the scraper service.
     *
     * @return Scraper version response.
     */
    public ScraperVersionResponse getVersion() {
        try {
            URI uri = URI.create(destinationUrl + "/");
            HttpRequest request = createGetRequest(uri);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(),
            ScraperVersionResponse.class);
        } catch (Exception e) {
            return new ScraperVersionResponse();
        }

    }

    /**
     * Get the requested data from the scraper service.
     *
     * @return The response from the scraper service.
     */
    public ScraperQueueResponse getQueue() {
        try {
            URI uri = URI.create(destinationUrl + "/queue");
            HttpRequest request = createGetRequest(uri);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(), ScraperQueueResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScraperQueueResponse();
        }
    }

    /**
     * Get the requested data from the scraper service.
     *
     * @param params The params to send to the scraper service.
     * @return The response from the scraper service.
     */
    public ScraperDataResponse getData(final String params) {
        try {
            URI uri = URI.create(destinationUrl + "/data/" + params);
            HttpRequest request = createGetRequest(uri);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(), ScraperDataResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScraperDataResponse();
        }
    }

    public ScraperArrayResponse getDataArray(final String params) {
        try {
            URI uri = URI.create(destinationUrl + "/data/" + params);
            HttpRequest request = createGetRequest(uri);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(), ScraperArrayResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScraperArrayResponse();
        }
    }

    public ScraperResponse sendIncident(String body) {
        try {
            URI uri = URI.create(destinationUrl + "/incident");
            HttpRequest request = createPostRequest(uri, body);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(), ScraperResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScraperArrayResponse();
        }
    }

    public ScraperResponse sendSuggestion(String body) {
        try {
            URI uri = URI.create(destinationUrl + "/request");
            HttpRequest request = createPostRequest(uri, body);
            HttpResponse<String> resp = sendRequest(request);
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(resp.body(), ScraperResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScraperArrayResponse();
        }
    }

    private HttpRequest createPostRequest(URI url, String body) {
        return HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .build();
    }
}
