package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileAttributes;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileVersion;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes functions to validate a file.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Validate {
    public static boolean isValidDataFile(File file) throws IOException {
        boolean valid = false;
        if(file.getName().contains(".dat") & file.getName().contains("backup_data") & file.canRead()) {
                if(FileVersion.isSupported(file)){
                        valid = true;
                }
        }
        return valid;
    }

    public static List<File> getValidFiles(List<File> files) throws IOException {
        List<File> ValidFiles = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        for (File file : files) {
            i++;
            Logger.logWithoutBreakup(Logger.INFO_LEVEL_3 + String.format(Translation.get("validate_validating"), file, i, files.size()));
            if(isValidDataFile(file)) {
                ValidFiles.add(file);
                i2++;
                Logger.logWithoutBreakup("    " + Translation.get("yes") + "\n");
            } else {
                Logger.logWithoutBreakup("    " + Translation.get("no") + "\n");
            }
        }
        Logger.log(Logger.INFO_LEVEL_3 + String.format(Translation.get("validate_done"), i, i2));
        return ValidFiles;
    }

    public static boolean isValidSolarLogFile(File file) throws IOException {
        if(file.getName().contains(".solarlog")) {
            UserDefinedFileAttributeView view = Files.getFileAttributeView(GetFile.getPathFromFile(file), UserDefinedFileAttributeView.class);
            ByteBuffer readBuffer = ByteBuffer.allocate(view.size(FileAttributes.fileType));
            view.read(FileAttributes.fileType, readBuffer);
            readBuffer.flip();
            String fileType = new String(readBuffer.array(), StandardCharsets.UTF_8);
            readBuffer = ByteBuffer.allocate(view.size(FileAttributes.fileVersion));
            view.read(FileAttributes.fileType, readBuffer);
            readBuffer.flip();
            String fileVersion = new String(readBuffer.array(), StandardCharsets.UTF_8);
            return fileType.equals(FileAttributes.fileTypeShouldBe) && fileVersion.equals(FileAttributes.fileVersion);
        } else {
            return false;
        }
    }
}
