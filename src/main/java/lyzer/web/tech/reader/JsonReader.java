package lyzer.web.tech.reader;

import lyzer.web.tech.logs.ConsoleLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The JsonReader class is used to read a json file from the resources' folder.
 * The file is read as a string and returned.
 */
public class JsonReader {

    /**
     * The path to the json file.
     */
    private final String fileDirectory;

    /**
     * The constructor for the JsonReader class.
     *
     * @param path The path to the json file.
     *             The path should be relative to the resources' folder.
     */
    public JsonReader(final String path) {
        fileDirectory = path;
    }

    /**
     * Reads the json file and returns it as a string.
     *
     * @return The json file as a string.
     * @throws IOException If the file is not found or if there is an error
     * reading the file.
     */
    public String readFile() throws IOException {
        BufferedReader bufferedReader = setupBufferedReader();
        StringBuilder stringBuilder = readFileContents(bufferedReader);
        return stringBuilder.toString();
    }

    /**
     * Sets up the BufferedReader to read the file.
     *
     * @return The BufferedReader to read the file.
     * @throws FileNotFoundException If the file is not found.
     */
    private BufferedReader setupBufferedReader() throws FileNotFoundException {
        Thread thread = Thread.currentThread();
        ClassLoader classloader = thread.getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(
                fileDirectory
        );

        if (inputStream == null) {
            ConsoleLogger logger = new ConsoleLogger();
            logger.log("File not found: " + fileDirectory, "WARNING");
            throw new FileNotFoundException("File not found.");
        }

        InputStreamReader streamReader = new InputStreamReader(
                inputStream, StandardCharsets.UTF_8
        );
        return new BufferedReader(streamReader);
    }

    /**
     * Reads the file and returns it as a StringBuilder.
     *
     * @param bufferedReader The BufferedReader to read the file.
     * @return The file as a StringBuilder.
     * @throws IOException If there is an error reading the file.
     */
    private StringBuilder readFileContents(
            final BufferedReader bufferedReader
    ) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line; (line = bufferedReader.readLine()) != null;) {
            stringBuilder.append(line);
        }
        return stringBuilder;
    }
}
