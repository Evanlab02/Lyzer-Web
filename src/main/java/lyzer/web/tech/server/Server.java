package lyzer.web.tech.server;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import lyzer.web.tech.config.LocalConfig;
import lyzer.web.tech.controllers.SeasonController;
import lyzer.web.tech.controllers.VersionController;

/**
 * Server.
 */
public final class Server implements Runnable {
    /**
     * The default port.
     */
    private static final int DEFAULT_PORT = 7000;

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
     * The local config.
     */
    private final LocalConfig localConfig;

    /**
     * This method is the constructor for the server.
     *
     * @param config The local config.
     */
    public Server(final LocalConfig config) {
        this.localConfig = config;
    }

    /**
     * This method is the constructor for the server.
     *
     * @param config The local config.
     * @param serverPort The port to run the server on.
     */
    public Server(final LocalConfig config, final int serverPort) {
        this.localConfig = config;
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
        app.get("/version/scraper", ctx ->
        VersionController.getScraperVersion(ctx, localConfig));

        app.get("/seasons", SeasonController::getSeasons);
    }
}
