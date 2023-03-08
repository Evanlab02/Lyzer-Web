package lyzer.web.tech.controllers;

import java.io.IOException;

import io.javalin.http.Context;
import lyzer.web.tech.reader.JsonReader;

public final class PatchNotesController {
    private PatchNotesController() {
    }

    /**
     * Internal error code.
     */
    private static final int INTERNAL_ERROR = 500;

    /**
     * Get the patch notes.
     *
     * @param ctx
     */
    public static void getPatchNotes(final Context ctx) {
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
