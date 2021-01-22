package me.meloni.SolarLogAPI.BasicGUI;

import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.DayCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.MonthCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.Graph.YearCustomizer;
import me.meloni.SolarLogAPI.Handling.Translation;
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
        f.setTitle(Translation.get("gui_visualization_title"));

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
        addTab(Translation.get("gui_visualization_daily_title"),new DayCustomizer(data, this));
        addTab(Translation.get("gui_visualization_monthly_title"),new MonthCustomizer(data, this));
        addTab(Translation.get("gui_visualization_yearly_title"), new YearCustomizer(data, this));
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
