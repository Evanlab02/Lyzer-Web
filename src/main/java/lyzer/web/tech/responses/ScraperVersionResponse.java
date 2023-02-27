package lyzer.web.tech.responses;

/**
 * ScraperVersionResponse is the response object for the
 * version response from the scraper service.
 */
public final class ScraperVersionResponse extends ScraperResponse {
    /**
     * Data would be the version of the scraper.
     */
    private String data;

    /**
     * Returns the data.
     *
     * @return data
     */
    public String getData() {
        return data;
    }
}
