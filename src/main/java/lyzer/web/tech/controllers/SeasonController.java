package lyzer.web.tech.controllers;

import java.io.IOException;
import org.json.JSONObject;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;


/**
 * SeasonController.
 */
public final class SeasonController {
    /**
     * Private constructor to prevent instantiation.
     */
    private SeasonController() {
    }

    /**
     * The internal error code.
     */
    private static final int INTERNAL_ERROR = 500;

    /**
     * Get all the seasons.
     *
     * @param ctx The context of the request.
     */
    public static void getSeasons(final Context ctx) {
        try {
            JsonReader jsonReader = new JsonReader("seasons.json");
            String fileContent = jsonReader.readFile();
            ctx.contentType("application/json");
            ctx.result(fileContent);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }

    /**
     * Get a specific season.
     *
     * @param ctx The context of the request.
     */
    public static void getSeason(final Context ctx) {
        try {
            JsonReader jsonReader = new JsonReader("seasons.json");
            String fileContent = jsonReader.readFile();
            JSONObject json = new JSONObject(fileContent);
            String season = ctx.pathParam("season");
            if (json.has(season)) {
                ctx.contentType("application/json");
                ctx.result(json.get(season).toString());
            } else {
                throw new IOException("Season not found");
            }
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }
}
