package lyzer.web.tech.controllers;

import java.io.IOException;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

public class RaceController {
    private RaceController() {
    }

    private static final int INTERNAL_ERROR = 500;

    public static void getAllRaces(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("races.json");
            String fileContent = reader.readFile();
            ctx.contentType("application/json");
            ctx.result(fileContent);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }

    public static void getSingleRace(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("races.json");
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
        } catch (Exception exception) {
            exception.printStackTrace();
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }
}
