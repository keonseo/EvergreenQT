package org.evergreen.lambda.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class LambdaScratchSpace {

    private LambdaScratchSpace() { }

    public static File createTemporaryFile(final InputStream inputStream) {
        final File tempFile;

        try {
            final Path path = Files.createTempFile(Paths.get("/tmp"), "temp", ".mp3");
            tempFile = path.toFile();

            java.nio.file.Files.copy(
                    inputStream,
                    tempFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            tempFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tempFile;
    }
}
