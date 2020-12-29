package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.DayCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.MonthCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.YearCustomizer;
import me.meloni.SolarLogAPI.SolarMap;
import me.meloni.SolarLogAPI.BasicGUI.Components.SimpleFrame;

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

    public static void visualize(SolarMap data) {
        new GraphCustomizer(data);
    }

    public GraphCustomizer(SolarMap data) {
        JFrame f = new SimpleFrame(this);
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visualization");

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
        addTab("Day View",new DayCustomizer(data, this));
        addTab("Month View",new MonthCustomizer(data, this));
        addTab("Year View", new YearCustomizer(data, this));
    }

    public void setCmp(JComponent c) {
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
