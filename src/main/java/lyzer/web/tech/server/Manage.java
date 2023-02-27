package lyzer.web.tech.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import lyzer.web.tech.clients.ScraperClient;
import lyzer.web.tech.config.LocalConfig;
import lyzer.web.tech.logs.ConsoleLogger;
import lyzer.web.tech.reader.JsonReader;

import java.io.IOException;

/**
 * Main class.
 */
public final class Manage {

    /**
     * Hidden constructor.
     */
    private Manage() {
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        ConsoleLogger logger = new ConsoleLogger();

        LocalConfig localConfig = readLocalConfig();
        logger.log("Local config loaded.");

        ScraperClient scraperClient = new ScraperClient(localConfig);
        Thread updateScraperThread = new Thread(scraperClient);
        updateScraperThread.start();

        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Read local config file.
     *
     * @return LocalConfig object.
     */
    public static LocalConfig readLocalConfig() {
        ConsoleLogger logger = new ConsoleLogger();
        String config = null;
        try {
            JsonReader reader = new JsonReader("localConfig.json");
            config = reader.readFile();
        } catch (IOException exception) {
            logger.log("Failed to read local config file.", "WARNING");
            logger.log(exception.getMessage(), "ERROR");
            System.exit(1);
        }

        LocalConfig localConfig = new LocalConfig();
        try {
            localConfig = LocalConfig.converJSONToLocalConfig(config);
        } catch (JsonProcessingException exception) {
            logger.log(
                    "Failed to convert local config file to object.",
                    "WARNING"
            );
            logger.log(exception.getMessage(), "ERROR");
            System.exit(2);
        }

        return localConfig;
    }
}
