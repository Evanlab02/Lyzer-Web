package lyzer.web.tech.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperDataResponse;

public final class TeamController {

    /**
     * Private constructor to avoid instantiation.
     */
    private TeamController() {
    }


    /**
     * Gets all the results for the teams.
     *
     * @param ctx
     */
    public static void getAllResults(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperDataResponse response = client.getData("constructors");
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    };

    /**
     * Gets the results for a given team.
     *
     * @param ctx
     */
    public static void getTeamResults(final Context ctx) {
        ScraperClient client = new ScraperClient();
        String year = ctx.pathParam("year");
        String team = ctx.pathParam("team");
        String url = "constructors/" + year + "/" + team;
        ScraperDataResponse response = client.getData(url);
        Gson gson = new Gson(); 
        String result = gson.toJson(response.getData()); 
        ctx.contentType("application/json");
        ctx.result(result);
    };
}
