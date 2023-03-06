package lyzer.web.tech.controllers;

import java.io.IOException;

import org.json.JSONObject;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

public class DriverController {
    private DriverController() {
    }

    /**
     * Internal error code.
     */
    private static final int INTERNAL_ERROR = 500;

    /**
     * Gets all the results for the drivers.
     *
     * @param ctx The context of the request.
     */
    public static void getAllResults(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("drivers.json");
            String fileContent = reader.readFile();
            ctx.contentType("application/json");
            ctx.result(fileContent);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    };

    public static void getSingleResult(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("drivers.json");
            String fileContent = reader.readFile();
            JSONObject json = new JSONObject(fileContent);
            String year = ctx.pathParam("year");
            String driverSurname = ctx.pathParam("driverSurname");
            String driverName = ctx.pathParam("driverName");
            String driverKey = driverSurname + ", " + driverName;
            JSONObject result = json.getJSONObject(year);
            JSONObject finalResult = result.getJSONObject(driverKey);
            ctx.contentType("application/json");
            ctx.result(finalResult.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }


    public static void getDriverStandings(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("drivers.json");
            String fileContent = reader.readFile();
            JSONObject json = new JSONObject(fileContent);
            String year = ctx.pathParam("year");
            JSONObject result = json.getJSONObject(year);
            JSONObject finalResult = result.getJSONObject("All");
            ctx.contentType("application/json");
            ctx.result(finalResult.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }
}
