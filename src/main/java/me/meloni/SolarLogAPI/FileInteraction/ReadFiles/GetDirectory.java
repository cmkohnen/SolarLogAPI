package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This Class includes functions to get the content of a directory.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class GetDirectory {
    public static List<File> getFiles(File directory) {
        assert directory.isDirectory();
        return Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }
}
