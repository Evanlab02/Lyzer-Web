package lyzer.web.tech.controllers;

import io.javalin.http.Context;
import lyzer.web.tech.config.LocalConfig;

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
        ctx.result("{\"version\": \"0.4.0\"}");
    }

    /**
     * Get the version of the Scraper API.
     *
     * @param ctx The context of the request.
     * @param config the config of the server.
     */
    public static void getScraperVersion(
        final Context ctx, final LocalConfig config
        ) {
        String version = config.getScraperVersion();
        ctx.contentType("application/json");
        ctx.result("{\"version\": \"" + version + "\"}");
    }
}
