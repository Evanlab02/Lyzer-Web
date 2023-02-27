package lyzer.web.tech.logs;

/**
 * This class is used to log messages to the console.
 */
public class ConsoleLogger {

    /**
     * This method is used to log a message to the console.
     * @param message The message to log.
     */
    public void log(final String message) {
        log(message, "INFO");
    }

    /**
     * This method is used to log a message to the console.
     * @param message The message to log.
     * @param level The level of the message.
     */
    public void log(final String message, final String level) {
        System.out.println("[" + level.toUpperCase() + "] " + message);
    }

}
