package lyzer.web.tech.controllers;

import com.google.gson.Gson;

import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperArrayResponse;
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

    /**
     * Gets all the years for which data is available.
     *
     * @param ctx The context of the request.
     */
    public static void getAllYears(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperArrayResponse response = client.getDataArray("years");
        Gson gson = new Gson();
        String result = gson.toJson(response.getData());
        ctx.contentType("application/json");
        ctx.result(result);
    }

    /**
     * Gets all the categories for a given year.
     *
     * @param ctx The context of the request.
     */
    public static void getCategories(final Context ctx) {
        String year = ctx.pathParam("year");
        ScraperClient client = new ScraperClient();
        ScraperArrayResponse response = client.getDataArray(
            "categories/" + year
        );
        Gson gson = new Gson();
        String result = gson.toJson(response.getData());
        ctx.contentType("application/json");
        ctx.result(result);
    }

    /**
     * Gets all the locations for a given year and category.
     *
     * @param ctx The context of the request.
     */
    public static void getLocations(final Context ctx) {
        String year = ctx.pathParam("year");
        String category = ctx.pathParam("category");
        ScraperClient client = new ScraperClient();
        ScraperArrayResponse response = client.getDataArray(
            "locations/" + year + "/" + category
        );
        Gson gson = new Gson();
        String result = gson.toJson(response.getData());
        ctx.contentType("application/json");
        ctx.result(result);
    }
}
