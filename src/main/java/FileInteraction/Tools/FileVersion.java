package FileInteraction.Tools;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFileContent;
import TransformUtilities.DataConversion.FileInformation;
import TransformUtilities.DataConversion.GetDataSection;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileVersion {

    public static boolean isSupported(File file) throws IOException {
        return fileVersion().contains(getFileVersion(file));
    }

    public static List<String> fileVersion(){
        List<String> fileVersion = new ArrayList<>();
        fileVersion.add("4.2.7");
        fileVersion.add("3.0.0");
        return fileVersion;
    }

    public static String getFileVersion(File file) throws IOException {
        return FileInformation.buildVersion(GetDataSection.InfoRow(GetFileContent.FileContentAsList(GetFile.Path(file))));
    }
}
