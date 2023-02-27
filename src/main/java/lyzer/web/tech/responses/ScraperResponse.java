package lyzer.web.tech.responses;

public abstract class ScraperResponse {
    /**
     * Message would be the error message if any,
     * otherwise it would say that data has been
     * retrieved successfully.
     */
    private String message;

    /**
     * Result would be either success or failure.
     */
    private String result;

    /**
     * Status would be the HTTP status code.
     */
    private Integer status;

    /**
     * Constructor for ScraperVersionResponse.
     */
    public ScraperResponse() {
    }

    /**
     * Getter for message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for result.
     *
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * Getter for status.
     *
     * @return status
     */
    public Integer getStatus() {
        return status;
    }
}
