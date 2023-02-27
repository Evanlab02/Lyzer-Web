package lyzer.web.tech.controllers;

import io.javalin.http.Context;

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
        ctx.result("{\"version\": \"0.1.0\"}");
    }
}
