package lyzer.web.tech.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Abstract class for creating HTTP clients.
 */
public abstract class Client implements Runnable {

    /**
     * The time it takes to time out any request
     * sent through a client.
     */
    private static final Integer TIMEOUT_TIME = 10;

    /**
     * Creates a GET request.
     *
     * @param url the URL to send the request to
     * @return the GET request
     */
    public HttpRequest createGetRequest(final URI url) {
        return HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
    }

    /**
     * Sends a request and returns the response.
     *
     * @param request the request to send
     * @return the response
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public HttpResponse<String> sendRequest(
            final HttpRequest request
    ) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_TIME))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
