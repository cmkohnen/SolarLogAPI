package Interface.BasicUI;

import Handling.SolarMap;
import Interface.SimpleFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GraphCustomizer extends JTabbedPane {
    static JFrame graphframe = new JFrame();
    static List<JComponent> cmps = new ArrayList<>();

    public GraphCustomizer(SolarMap data) {
        addTab("Day View",new DayCustomizer(data));
        addTab("Month View",new MonthCustomizer(data));
    }

    public static void run() {
        SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
        JFrame f = new SimpleFrame(new GraphCustomizer(solarMap));
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visuilization");

        graphframe.setSize(1000, 600);
        graphframe.setLocationRelativeTo(f);
    }

    public static void setCmp(JComponent c) {
        for (JComponent cmp : cmps) {
            graphframe.remove(cmp);
        }
        graphframe.setTitle("Visualize - ");
        graphframe.add(c);
        cmps.add(c);
        graphframe.repaint();
        graphframe.setVisible(true);
    }

}
