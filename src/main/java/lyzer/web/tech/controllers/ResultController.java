package lyzer.web.tech.controllers;

import com.google.gson.Gson;

import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperDataResponse;

/**
 * ResultController.
 */
public final class ResultController {

    /**
     * Private constructor to prevent instantiation.
     */
    private ResultController() {
    }

    /**
     * Gets all the results for the data type.
     *
     * @param ctx The context of the request.
     */
    public static void getAllResults(final Context ctx) {
        String dataType = ctx.pathParam("dataType");
        ScraperClient client = new ScraperClient();
        ScraperDataResponse response = client.getData(dataType);
        Gson gson = new Gson();
        String result = gson.toJson(response.getData());
        ctx.contentType("application/json");
        ctx.result(result);
    }

    /**
     * Gets the results for a single event and data type.
     *
     * @param ctx The context of the request.
     */
    public static void getSingleResult(final Context ctx) {
        String dataType = ctx.pathParam("dataType");
        String year = ctx.pathParam("year");
        String location = ctx.pathParam("location");
        ScraperClient client = new ScraperClient();
        String url = dataType + "/" + year + "/" + location;
        ScraperDataResponse response = client.getData(url);
        Gson gson = new Gson();
        String result = gson.toJson(response.getData());
        ctx.contentType("application/json");
        ctx.result(result);
    }
}
