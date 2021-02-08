/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
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
 * This class includes functions to customize a graph.
 * @author Christoph Kohnen
 * @since 1.0.0
 */
public class BasicGraphCustomizer extends JTabbedPane {
    /**
     * A frame to which graphs can be mapped
     */
    static final JFrame graphFrame = new JFrame();
    /**
     * Temporal storage for a component. Thi is to be able to remove it later
     */
    static final List<JComponent> components = new ArrayList<>();

    /**
     * Function to visualize a set of data.
     * @param data A {@link SolarMap} containing all data
     */
    public static void visualize(SolarMap data) {
        new BasicGraphCustomizer(data);
    }

    /**
     * Instantiate and visualize a set of data
     * @param data A {@link SolarMap} containing all data
     */
    public BasicGraphCustomizer(SolarMap data) {
        JFrame f = new SimpleFrame(this, true);
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visualization");

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
        addTab("Daily view",new DayCustomizer(data, this));
        addTab("Monthly view",new MonthCustomizer(data, this));
        addTab("Yearly view", new YearCustomizer(data, this));
    }

    /**
     * Modify the currently shown graph
     * @param graph The graph which should be shown
     * @param title The title the frame should use
     */
    public void setGraph(JComponent graph, String title) {
        for (JComponent cmp : components) {
            graphFrame.remove(cmp);
        }
        graphFrame.setTitle(title);
        graphFrame.add(graph);
        components.add(graph);
        graphFrame.repaint();
        graphFrame.setVisible(true);
    }
}
