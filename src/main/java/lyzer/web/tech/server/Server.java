package lyzer.web.tech.server;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import lyzer.web.tech.controllers.DriverController;
import lyzer.web.tech.controllers.PatchNotesController;
import lyzer.web.tech.controllers.ReportController;
import lyzer.web.tech.controllers.ResultController;
import lyzer.web.tech.controllers.SeasonController;
import lyzer.web.tech.controllers.TeamController;
import lyzer.web.tech.controllers.VersionController;

/**
 * Server.
 */
public final class Server implements Runnable {
    /**
     * The default port.
     */
    private static final int DEFAULT_PORT = 80;

    /**
     * Port to run the server on.
     */
    private int port = DEFAULT_PORT;

    /**
     * The host path.
     */
    private static final String HOST_PATH = "/";

    /**
     * The path to the html files.
     */
    private static final String HTML_PATH = "public/";

    /**
     * This method is the constructor for the server.
     */
    public Server() {
    }

    /**
     * This method is the constructor for the server.
     *
     * @param serverPort The port to run the server on.
     */
    public Server(final int serverPort) {
        this.port = serverPort;
    }

    /**
     * The run method.
     * This is the entry point for the server.
     */
    @Override
    public void run() {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = HOST_PATH;
                staticFiles.directory = HTML_PATH;
                staticFiles.location = Location.CLASSPATH;
            });
        });
        app.start(port);

        app.get("/version", VersionController::getVersion);
        app.get("/version/scraper", VersionController::getScraperVersion);

        app.get("/seasons", SeasonController::getSeasons);
        app.get("seasons/{season}", SeasonController::getSeason);

        app.get("/results/{dataType}", ResultController::getAllResults);
        app.get("/results/{dataType}/{year}/{location}",
        ResultController::getSingleResult);

        app.get("/drivers", DriverController::getAllResults);
        app.get("/drivers/{year}", DriverController::getDriverStandings);
        app.get("/drivers/{year}/{driverSurname}/{driverName}",
        DriverController::getSingleResult);

        app.get("/constructors", TeamController::getAllResults);
        app.get("/constructors/{year}/{team}", TeamController::getTeamResults);

        app.get("/patchnotes", PatchNotesController::getPatchNotes);

        app.get("/api/years", ResultController::getAllYears);
        app.get("/api/categories/{year}", ResultController::getCategories);
        app.get("/api/locations/{year}/{category}",
        ResultController::getLocations);

        app.post("/incident", ReportController::reportIncident);
        app.post("/request", ReportController::createSuggestion);
    }
}
