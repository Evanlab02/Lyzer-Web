package lyzer.web.tech.server;

import io.javalin.Javalin;
import lyzer.web.tech.controllers.VersionController;

/**
 * Server.
 */
public final class Server implements Runnable {

    /**
     * Port to run the server on.
     */
    private static final int PORT = 7000;

    /**
     * The run method.
     * This is the entry point for the server.
     */
    @Override
    public void run() {
        Javalin app = Javalin.create().start(PORT);
        app.get("/", VersionController::getVersion);
    }
}
