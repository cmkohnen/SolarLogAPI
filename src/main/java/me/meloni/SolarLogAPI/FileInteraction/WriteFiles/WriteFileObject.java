package me.meloni.SolarLogAPI.FileInteraction.WriteFiles;

import me.meloni.SolarLogAPI.FileInteraction.GetFile;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileAttributes;
import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.Handling.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * This Class includes a function to write an object to a specified file.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class WriteFileObject {
    public static void write(File file, FileObject object) throws IOException {
        UserDefinedFileAttributeView view = Files.getFileAttributeView(GetFile.getPathFromFile(file), UserDefinedFileAttributeView.class);
        Logger.log(Logger.INFO_LEVEL_2 + String.format("Writing file %s. This may take a while.", file));
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(object);
        s.flush();
        s.close();
        byte[] bytes = FileAttributes.fileTypeShouldBe.getBytes(StandardCharsets.UTF_8);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        view.write(FileAttributes.fileType, writeBuffer);
        bytes = FileAttributes.fileVersionShouldBe.getBytes(StandardCharsets.UTF_8);
        writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        view.write(FileAttributes.fileVersion, writeBuffer);
        Logger.log(Logger.INFO_LEVEL_2 + "done.");
    }
}
