package Handling;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.ReadFileObject;
import Interface.BasicUI.BasicConversion;
import Interface.BasicUI.GraphCustomizer;

import java.io.IOException;
import java.util.*;

/**
 * This Class is called when the jar package is executed.
 * @author ChaosMelone9
 * @since 0.0.0
 */
public class APIInitializer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> argsAsList = new ArrayList<>(Arrays.asList(args));

        Logger.log("API starts via Runtime Environment. Used Arguments: " + argsAsList);

        if(argsAsList.contains("simplegui")) {
            Map<Date, List<Integer>> data = (Map<Date, List<Integer>>) ReadFileObject.fileObject(GetFile.ChosenReadLocation());
            GraphCustomizer.run(data);
        }

        if(argsAsList.contains("convert")) {
            BasicConversion.run();
        }

    }

}
