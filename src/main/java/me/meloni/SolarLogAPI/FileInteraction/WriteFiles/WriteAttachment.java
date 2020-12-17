package me.meloni.SolarLogAPI.FileInteraction.WriteFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;

public class WriteAttachment {
    public static void write(InputStream inputStream, File path) throws IOException {
        if(!path.exists()) {
            FileOutputStream output = new FileOutputStream(path);
            byte[] buffer = new byte[4096];
            int byteRead;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, byteRead);
            }
            output.close();
        } else {
            throw new FileAlreadyExistsException("File " + path + "already exists!");
        }
    }
}
