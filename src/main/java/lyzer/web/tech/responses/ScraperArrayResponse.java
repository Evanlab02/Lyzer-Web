package lyzer.web.tech.responses;

public class ScraperArrayResponse extends ScraperResponse {
    /**
     * Data that was requested.
     */
    private String[][] data;

    /**
     * Getter for data.
     *
     * @return data
     */
    public String[][] getData() {
        return data;
    }
}
