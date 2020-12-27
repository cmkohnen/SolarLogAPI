package me.meloni.SolarLogAPI.FileInteraction.WriteFiles;

import me.meloni.SolarLogAPI.Handling.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * This Class includes a function to write an object to a specified file.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class WriteFileObject {
    public static void write(File file, Object object) throws IOException {
        Logger.log(Logger.INFO_LEVEL_2 + "Writing to \"" + file + "\". This may take a while.");
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(object);
        s.flush();
        s.close();
        Logger.log(Logger.INFO_LEVEL_2 + "done.");
    }
}
