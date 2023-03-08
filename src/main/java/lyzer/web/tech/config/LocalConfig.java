package lyzer.web.tech.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
     * This method returns the IP address of the scraper application.
     *
     * @return IP address of the scraper application.
     * @since 0.1.0
     */
    public String getScraperIp() {
        return scraperIp;
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
     * This method converts a JSON object to a LocalConfig object.
     *
     * @param jsonString JSON string to be converted.
     * @return LocalConfig object with the values from the JSON object.
     * @since 0.1.0
     */
    public static LocalConfig converJSONToLocalConfig(
            final String jsonString
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, LocalConfig.class);
    }

    @Override
    public String toString() {
        return "{"
                + "scraperIp='" + scraperIp + '\''
                + '}';
    }
}
