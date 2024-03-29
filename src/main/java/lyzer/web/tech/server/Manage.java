package lyzer.web.tech.server;

import com.fasterxml.jackson.core.JsonProcessingException;

import lyzer.web.tech.clients.NtfyClient;
import lyzer.web.tech.config.LocalConfig;
import lyzer.web.tech.logs.ConsoleLogger;
import lyzer.web.tech.reader.JsonReader;

import java.io.IOException;

/**
 * Main class.
 */
public final class Manage {

    /**
     * Notification service ip.
     */
    private static String ntfyIp;

    /**
     * Scraper ip.
     */
    private static String scraperIp;

    /**
     * Hidden constructor.
     */
    private Manage() {
    }

    /**
     * Get the notification service ip.
     *
     * @return The notification service ip.
     */
    public static String getNtfyIp() {
        return ntfyIp;
    }

    /**
     * Get the scraper ip.
     *
     * @return The scraper ip.
     */
    public static String getScraperIp() {
        return scraperIp;
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
        scraperIp = localConfig.getScraperIp();
        ntfyIp = localConfig.getNtfyIp();

        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            Thread serverThread = new Thread(server);
            serverThread.start();
        } else {
            Server server = new Server();
            Thread serverThread = new Thread(server);
            serverThread.start();
        }

        NtfyClient ntfyClient = new NtfyClient();
        Thread ntfyClientThread = new Thread(ntfyClient);
        ntfyClientThread.start();

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
