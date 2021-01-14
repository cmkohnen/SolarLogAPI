package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.FileInteraction.Tools.FileObject;
import me.meloni.SolarLogAPI.Handling.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This Class includes the fileObject function which results in a object read from a file.
 * @author ChaosMelone9
 * @since 0.0.2
 */
public class ReadFileObject {
    public static FileObject getObjectFromFile(File file) throws IOException, ClassNotFoundException {
        if(!(file == null) && file.exists()) {
            Logger.log(Logger.INFO_LEVEL_2 + "Reading file " + file + ". This may take a while.");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            FileObject o = (FileObject) s.readObject();
            s.close();
            Logger.log(Logger.INFO_LEVEL_2 + "done.");
            return o;
        } else {
         return null;
        }
    }
}
