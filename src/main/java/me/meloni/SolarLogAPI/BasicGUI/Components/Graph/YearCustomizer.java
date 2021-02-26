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
package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.BasicGraphCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.YearPicker;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.time.Year;

/**
 * This class includes a function to display yearly graph data.
 * @author Christoph Kohnen
 * @since 3.4.5
 */
public class YearCustomizer extends JPanel {
    /**
     * The currently used graph
     */
    static YearView graph = null;
    /**
     * The instance of the {@link BasicGraphCustomizer}
     */
    BasicGraphCustomizer instance;

    /**
     * Construct the JPanel and setup all components
     * @param data The data that should be used
     * @param instance The instance of the {@link BasicGraphCustomizer}
     */
    public YearCustomizer(SolarMap data, BasicGraphCustomizer instance) {
        this.instance = instance;
        setLayout(new BorderLayout());

        YearPicker picker = new YearPicker();
        picker.setMaximumSize(new Dimension(200,40));
        picker.addActionListener(event -> {
            Year year = picker.getYear();
            if(year != null && data.includesYear(year)) {
                graph = new YearView(data, year);
                paintComponent();
            }
        });

        add(picker, BorderLayout.PAGE_START);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        JCheckBox b1 = new JCheckBox();
        JCheckBox b2 = new JCheckBox();
        JCheckBox b3 = new JCheckBox();
        b1.setText("consumption");
        b2.setText("own consumption");
        b3.setText("production");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
        b1.addActionListener(actionEvent -> {
            try {
                graph.setRow1Visible(b1.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b1.setSelected(!b1.isSelected());
            }
        });
        b2.addActionListener(actionEvent -> {
            try {
                graph.setRow2Visible(b2.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b2.setSelected(!b2.isSelected());
            }
        });
        b3.addActionListener(actionEvent -> {
            try {
                graph.setRow3Visible(b3.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b3.setSelected(!b3.isSelected());
            }
        });
        p.add(b1);
        p.add(b2);
        p.add(b3);

        JCheckBox mouseGUI = new JCheckBox();
        mouseGUI.setText("Mouse Feedback");
        mouseGUI.setSelected(true);
        mouseGUI.addActionListener(actionEvent -> {
            try {
                graph.setMouseGUIVisible(mouseGUI.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                mouseGUI.setSelected(!mouseGUI.isSelected());
            }
        });
        p.add(mouseGUI);

        JCheckBox shaded = new JCheckBox();
        shaded.setText("Shade graph");
        shaded.setSelected(false);
        shaded.addActionListener(actionEvent -> {
            boolean selected = shaded.isSelected();
            try {
                graph.setRow1Shaded(selected);
                graph.setRow2Shaded(selected);
                graph.setRow3Shaded(selected);
                paintComponent();
            } catch (NullPointerException e) {
                shaded.setSelected(!selected);
            }
        });
        p.add(shaded);

        add(p,BorderLayout.WEST);
    }

    /**
     * Update the graph on the {@link BasicGraphCustomizer}
     */
    private void paintComponent() {
        instance.setGraph(graph, graph.getTitle());
    }
}
