package lyzer.web.tech.clients;

import java.net.URI;
import java.net.http.HttpRequest;

import lyzer.web.tech.logs.ConsoleLogger;
import lyzer.web.tech.responses.ScraperVersionResponse;
import lyzer.web.tech.server.Manage;

/**
 * Client for the notification service.
 */
public final class NtfyClient extends Client implements Runnable {

    /**
     * The amount of milliseconds in a minute.
     */
    private final int minuteInMilliSeconds = 60000;

    /**
     * The interval in minutes to check if the scraper is online.
     */
    private final int checkingIntervalMinutes = 30;

    /**
     * The interval in milliseconds to check if the scraper is online.
     */
    private final int checkingInterval =
        checkingIntervalMinutes * minuteInMilliSeconds;


    /**
     * The success status code.
     */
    private final int sucessStatusCode = 200;

    /**
     * Local configuration.
     */
    private final String destinationUrl;

    /**
     * Constructor.
     *
     */
    public NtfyClient() {
        destinationUrl = Manage.getNtfyIp();
    }

    /**
     * Create a post request for the notification service.
     *
     * @param url  The url to send the request to.
     * @param body The body of the request.
     * @return The request.
     */
    public HttpRequest createNtfyPostRequest(final URI url, final String body) {
        return HttpRequest.newBuilder()
                .uri(url)
                .headers(
                    "Title", "Lyzer Scraper is offline!",
                    "Priority", "urgent",
                    "Tags", "warning"
                )
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    /**
     * Create a low priority post request for the notification service.
     *
     * @param url  The url to send the request to.
     * @param body The body of the request.
     * @return The request.
     */
    public HttpRequest createLowNtfyPostRequest(
        final URI url, final String body
        ) {
        return HttpRequest.newBuilder()
                .uri(url)
                .headers(
                    "Title", "Lyzer Scraper is online!",
                    "Priority", "1",
                    "Tags", "info"
                )
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    /**
     * Send a emergency notification to the notification service.
     */
    public void sendEmergency() {
        try {
            URI url = URI.create(destinationUrl);
            HttpRequest request = createNtfyPostRequest(
                url,
                "Lyzer Scraper is offline!"
            );
            sendRequest(request);
        } catch (Exception e) {
            ConsoleLogger logger = new ConsoleLogger();
            logger.log(
                "FATAL ERROR: Could not send emergency notification!",
                "ERROR"
            );
        }
    }

    /**
     * Send a notice notification to the notification service.
     */
    public void sendNotice() {
        try {
            URI url = URI.create(destinationUrl);
            HttpRequest request = createLowNtfyPostRequest(
                url,
                "Lyzer Scraper is online!"
            );
            sendRequest(request);
        } catch (Exception e) {
            ConsoleLogger logger = new ConsoleLogger();
            logger.log(
                "FATAL ERROR: Could not send notice notification!",
                "ERROR"
            );
        }
    }

    /**
     * Send a notice that someone has given feedback on the site.
     */
    public void receivedFeedback() {
        try {
            URI url = URI.create(destinationUrl);
            HttpRequest request = createLowNtfyPostRequest(
                url,
                "Received feedback!"
            );
            sendRequest(request);
        } catch (Exception e) {
            ConsoleLogger logger = new ConsoleLogger();
            logger.log(
                "FATAL ERROR: Could not send notice notification!",
                "ERROR"
            );
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ScraperClient scraperClient = new ScraperClient();
                ScraperVersionResponse response = scraperClient.getVersion();
                if (response.getStatus() == null) {
                    sendEmergency();
                } else if (response.getStatus() == sucessStatusCode) {
                    sendNotice();
                }
            } catch (Exception exception) {
                sendEmergency();
            }

            try {
                Thread.sleep(checkingInterval);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
