import me.meloni.SolarLogAPI.BasicGUI.GetChosenFile;
import me.meloni.SolarLogAPI.DataConversion.GetFromJS;
import me.meloni.SolarLogAPI.Inverter;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.InverterMap;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class JsTest {
    public static void main(String[] args) throws IOException, ParseException {
        Logger.setEnabled(true);
        File base_vars = GetChosenFile.chosenJSFile();

        File file = GetChosenFile.chosenJSFile();

        Map<Inverter, InverterMap> testData = GetFromJS.getAsInverterMapsFromJSFile(base_vars, file);

        testData.forEach((inverter, inverterMap) -> {
            Logger.log(inverter.identifier + ", " + inverterMap.size());
            Logger.log(inverterMap);
        });
    }
}
