package lyzer.web.tech.controllers;

import io.javalin.http.Context;
import lyzer.web.tech.clients.NtfyClient;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.responses.ScraperResponse;

public class ReportController {
    private ReportController() {
    }

    public static void reportIncident(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperResponse response = client.sendIncident(ctx.body());
        ctx.result(response.getMessage());
        NtfyClient ntfyClient = new NtfyClient();
        ntfyClient.receivedFeedback();
    }

    public static void createSuggestion(final Context ctx) {
        ScraperClient client = new ScraperClient();
        ScraperResponse response = client.sendSuggestion(ctx.body());
        ctx.result(response.getMessage());
        NtfyClient ntfyClient = new NtfyClient();
        ntfyClient.receivedFeedback();
    }
}
