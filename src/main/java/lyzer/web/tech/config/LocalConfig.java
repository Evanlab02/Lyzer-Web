package lyzer.web.tech.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lyzer.web.tech.logs.ConsoleLogger;


/**
 * This class is responsible for storing the local configuration of the
 * application.
 *
 * @since 0.1.0
 */
public final class LocalConfig {

    /**
     * The IP address of the scraper application.
     *
     * @since 0.1.0
     */
    private String scraperIp;

    /**
     * The version of the scraper application.
     *
     * @since 0.1.0
     */
    private String scraperVersion;

    /**
     * This method returns the IP address of the scraper application.
     *
     * @return IP address of the scraper application.
     * @since 0.1.0
     */
    public String getScraperIp() {
        return scraperIp;
    }

    /**
     * This method returns the version of the scraper application.
     *
     * @return version of the scraper application.
     * @since 0.1.0
     */
    public String getScraperVersion() {
        return scraperVersion;
    }

    /**
     * This method sets the IP address of the scraper application.
     *
     * @param scraperHost IP address of the scraper application.
     * @since 0.1.0
     */
    public void setScraperIp(final String scraperHost) {
        this.scraperIp = scraperHost;
    }

    /**
     * This method sets the version of the scraper application.
     *
     * @param version version of the scraper application.
     * @since 0.1.0
     */
    public void setScraperVersion(final String version) {
        this.scraperVersion = version;
    }

    /**
     * This method converts a JSON object to a LocalConfig object.
     *
     * @param jsonString JSON string to be converted.
     * @return LocalConfig object with the values from the JSON object.
     * @since 0.1.0
     */
    public static LocalConfig converJSONToLocalConfig(
            final String jsonString
    ) throws JsonProcessingException {
        ConsoleLogger logger = new ConsoleLogger();
        logger.log("Converting File to Object...");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, LocalConfig.class);
    }

    @Override
    public String toString() {
        return "{"
                + "scraperIp='" + scraperIp + '\''
                + ", scraperVersion='" + scraperVersion + '\''
                + '}';
    }
}
