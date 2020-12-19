package me.meloni.SolarLogAPI.Interface.BasicUI;

import me.meloni.SolarLogAPI.SolarMap;
import me.meloni.SolarLogAPI.Interface.SimpleFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes functions to customize a graph.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class GraphCustomizer extends JTabbedPane {
    static final JFrame graphFrame = new JFrame();
    static final List<JComponent> components = new ArrayList<>();

    public GraphCustomizer(SolarMap data) {
        JFrame f = new SimpleFrame(this);
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visualization");

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
        addTab("Day View",new DayCustomizer(data));
        addTab("Month View",new MonthCustomizer(data));
    }

    public static void run() {
        SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
        new GraphCustomizer(solarMap);
    }

    public static void setCmp(JComponent c) {
        for (JComponent cmp : components) {
            graphFrame.remove(cmp);
        }
        graphFrame.setTitle("Visualize - ");
        graphFrame.add(c);
        components.add(c);
        graphFrame.repaint();
        graphFrame.setVisible(true);
    }
}
