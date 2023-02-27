package lyzer.web.tech.responses;

import java.util.HashMap;

public class ScraperDataResponse extends ScraperResponse {
    /**
     * Data that was requested.
     */
    private HashMap<String, Object> data;

    /**
     * Getter for data.
     *
     * @return data
     */
    public HashMap<String, Object> getData() {
        return data;
    }
}
