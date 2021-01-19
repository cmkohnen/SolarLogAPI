import me.meloni.SolarLogAPI.BasicGUI.BasicSolarMapCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.GraphCustomizer;
import me.meloni.SolarLogAPI.Handling.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.setEnabled(true);
        GraphCustomizer.visualize(BasicSolarMapCustomizer.solarMap());

    }
}
