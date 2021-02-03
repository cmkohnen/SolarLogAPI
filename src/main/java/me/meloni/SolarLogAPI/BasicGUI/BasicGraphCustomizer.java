package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.DayCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.MonthCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.YearCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.SimpleFrame;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class includes functions to customize a graph.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class BasicGraphCustomizer extends JTabbedPane {
    static final JFrame graphFrame = new JFrame();
    static final List<JComponent> components = new ArrayList<>();

    public static void visualize(SolarMap data) {
        new BasicGraphCustomizer(data);
    }

    public BasicGraphCustomizer(SolarMap data) {
        JFrame f = new SimpleFrame(this);
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visualization");

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
        addTab("Daily view",new DayCustomizer(data, this));
        addTab("Monthly view",new MonthCustomizer(data, this));
        addTab("Yearly view", new YearCustomizer(data, this));
    }

    public void setGraph(JComponent c, String title) {
        for (JComponent cmp : components) {
            graphFrame.remove(cmp);
        }
        graphFrame.setTitle(title);
        graphFrame.add(c);
        components.add(c);
        graphFrame.repaint();
        graphFrame.setVisible(true);
    }
}
