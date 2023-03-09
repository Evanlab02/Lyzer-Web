package lyzer.web.tech.controllers;

import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperVersionResponse;

/**
 * VersionController.
 */
public final class VersionController {
    /**
     * Private constructor to prevent instantiation.
     */
    private VersionController() {
    }

    /**
     * Get the version of the API.
     *
     * @param ctx The context of the request.
     */
    public static void getVersion(final Context ctx) {
        ctx.contentType("application/json");
        ctx.result("{\"version\": \"1.0.1\"}");
    }

    /**
     * Get the version of the Scraper API.
     *
     * @param ctx The context of the request.
     */
    public static void getScraperVersion(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperVersionResponse response = client.getVersion();
        ctx.contentType("application/json");
        ctx.result("{\"version\": \"" + response.getData() + "\"}");
    }
}
