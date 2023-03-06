package lyzer.web.tech.controllers;

import java.io.IOException;

import org.json.JSONObject;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

public class TeamController {
    private TeamController() {
    }

    /**
     * Internal error code.
     */
    private static final int INTERNAL_ERROR = 500;
    
    public static void getAllResults(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("constructors.json");
            String fileContent = reader.readFile();
            ctx.contentType("application/json");
            ctx.result(fileContent);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    };

    public static void getTeamResults(final Context ctx) {
        try {
            JsonReader reader = new JsonReader("constructors.json");
            String fileContent = reader.readFile();
            JSONObject json = new JSONObject(fileContent);
            String year = ctx.pathParam("year");
            JSONObject result = json.getJSONObject(year);
            String team = ctx.pathParam("team");
            team = team.replaceAll("_", " ");
            JSONObject finalResult = result.getJSONObject(team);
            ctx.contentType("application/json");
            ctx.result(finalResult.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    };
}
