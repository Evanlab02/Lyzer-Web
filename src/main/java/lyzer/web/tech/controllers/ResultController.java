package lyzer.web.tech.controllers;

import java.io.IOException;
import org.json.JSONObject;
import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

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
     * Internal error code.
     */
    private static final int INTERNAL_ERROR = 500;

    /**
     * Gets all the results for the data type.
     *
     * @param ctx The context of the request.
     */
    public static void getAllResults(final Context ctx) {
        try {
            String dataType = ctx.pathParam("dataType");
            JsonReader reader = new JsonReader(dataType + ".json");
            String fileContent = reader.readFile();
            ctx.contentType("application/json");
            ctx.result(fileContent);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }

    /**
     * Gets the results for a single event and data type.
     *
     * @param ctx The context of the request.
     */
    public static void getSingleResult(final Context ctx) {
        try {
            String dataType = ctx.pathParam("dataType");
            JsonReader reader = new JsonReader(dataType + ".json");
            String fileContent = reader.readFile();
            JSONObject json = new JSONObject(fileContent);
            String year = ctx.pathParam("year");
            String location = ctx.pathParam("location");
            location = location.replace("_", " ");
            JSONObject result = json.getJSONObject(year);
            JSONObject finalResult = result.getJSONObject(location);
            ctx.contentType("application/json");
            ctx.result(finalResult.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }
}
