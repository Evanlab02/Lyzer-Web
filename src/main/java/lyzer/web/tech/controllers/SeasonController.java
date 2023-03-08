package lyzer.web.tech.controllers;

import com.google.gson.Gson;

import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperDataResponse;


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
     * Get all the seasons.
     *
     * @param ctx The context of the request.
     */
    public static void getSeasons(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperDataResponse response = client.getData("seasons");
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    }

    /**
     * Get a specific season.
     *
     * @param ctx The context of the request.
     */
    public static void getSeason(final Context ctx) {
        String season = ctx.pathParam("season");
        ScraperClient client = new ScraperClient();
        ScraperDataResponse response = client.getData("seasons/" + season);
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    }
}
