package lyzer.web.tech.writer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class will be responsible for writing data to json files.
 */
public class JsonWriter {

    /**
     * The file path.
     */
    private final String fileDirectory;

    /**
     * The constructor for the JsonWriter.
     *
     * @param path The file path.
     */
    public JsonWriter(final String path) {
        this.fileDirectory = path;
    }

    /**
     * This will write an object to the file at the path.
     *
     * @param object The object to write to the file.
     * @throws IOException This will be thrown when the file does not exist.
     */
    public void writeFile(final Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(getFile(), object);
    }

    /**
     * This will get the file as a file object.
     *
     * @return the file object.
     */
    private File getFile() throws IOException {
        Path source = Paths.get(this.getClass()
                .getResource("/" + fileDirectory)
                .getPath());
        File file = source.toFile();

        if (!file.exists()) {
            file.createNewFile();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, new HashMap<>());
        }

        return source.toFile();
    }
}
