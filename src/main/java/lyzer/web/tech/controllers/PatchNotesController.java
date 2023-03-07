package lyzer.web.tech.controllers;

import java.io.IOException;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

public class PatchNotesController {
    private PatchNotesController() {
    }

    /**
     * Internal error code.
     */
    private static final int INTERNAL_ERROR = 500;

    public static void getPatchNotes(Context ctx) {
        try {
            JsonReader jsonReader = new JsonReader("patch_notes.json");
            String content = jsonReader.readFile();
            ctx.contentType("application/json");
            ctx.result(content);
        } catch (IOException exception) {
            ctx.status(INTERNAL_ERROR);
            ctx.result("{}");
        }
    }
}
