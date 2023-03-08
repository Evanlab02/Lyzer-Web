package lyzer.web.tech.controllers;

import com.google.gson.Gson;

import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperDataResponse;

public final class DriverController {

    /**
     * Private constructor to avoid instantiation.
     */
    private DriverController() {
    }

    /**
     * Gets all the results for the drivers.
     *
     * @param ctx The context of the request.
     */
    public static void getAllResults(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperDataResponse response = client.getData("drivers");
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    };

    /**
     * Gets the results for a given driver.
     *
     * @param ctx The context of the request.
     */
    public static void getSingleResult(final Context ctx) {
        ScraperClient client = new ScraperClient();
        String driverSurname = ctx.pathParam("driverSurname");
        String driverName = ctx.pathParam("driverName");
        String year = ctx.pathParam("year");
        String driverKey = driverSurname + ",_" + driverName;
        String url = "drivers/" + year + "/" + driverKey;
        ScraperDataResponse response = client.getData(url);
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    }


    /**
     * This method gets the driver standings for a given year.
     *
     * @param ctx
     */
    public static void getDriverStandings(final Context ctx) {
        ScraperClient client = new ScraperClient();
        String year = ctx.pathParam("year");
        String url = "drivers/" + year + "/All";
        ScraperDataResponse response = client.getData(url);
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    }
}
