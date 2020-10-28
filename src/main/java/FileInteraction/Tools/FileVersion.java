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
        fileVersion.add("3.4.2");
        fileVersion.add("3.5.1");
        fileVersion.add("3.6.0");
        fileVersion.add("4.2.0");
        fileVersion.add("4.2.5");
        return fileVersion;
    }

    public static String getFileVersion(File file) throws IOException {
        return FileInformation.buildVersion(GetDataSection.InfoRow(GetFileContent.FileContentAsList(GetFile.Path(file))));
    }

    public static Map<String, List<Integer>> matrix() {
        Map<String, List<Integer>> matrix = new HashMap<>();
        List<Integer> version300 = new ArrayList<>();
        version300.add(6);
        version300.add(8);
        version300.add(15);
        version300.add(21);
        version300.add(36);
        matrix.put("3.0.0",version300);
        matrix.put("3.4.2",version300);
        List<Integer> version427 = new ArrayList<>();
        version427.add(6);
        version427.add(10);
        version427.add(17);
        version427.add(23);
        version427.add(44);
        matrix.put("4.2.7",version427);
        matrix.put("4.2.0",version427);
        matrix.put("3.6.0",version427);
        matrix.put("4.2.5",version427);
        List<Integer> version351 = new ArrayList<>();
        version351.add(6);
        version351.add(10);
        version351.add(17);
        version351.add(23);
        version351.add(38);
        matrix.put("3.5.1",version351);
        return matrix;
    }
}
