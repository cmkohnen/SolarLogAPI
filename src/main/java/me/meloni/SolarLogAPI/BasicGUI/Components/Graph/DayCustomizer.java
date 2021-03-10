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
import me.meloni.SolarLogAPI.BasicGUI.Components.DatePicker;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.Inverter;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;

/**
 * This class includes a function to display daily graph data.
 * @author Christoph Kohnen
 * @since 1.0.0
 */
public class DayCustomizer extends JPanel {
    /**
     * The currently used graph
     */
    static DayView graph = null;
    /**
     * The instance of the {@link BasicGraphCustomizer}
     */
    BasicGraphCustomizer instance;

    Inverter inverter;

    /**
     * Construct the JPanel and setup all components
     * @param data The data that should be used
     * @param instance The instance of the {@link BasicGraphCustomizer}
     */
    public DayCustomizer(SolarMap data, BasicGraphCustomizer instance) {
        this.instance = instance;
        setLayout(new BorderLayout());
        this.inverter = data.total;

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data.get(inverter));
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            if(data.get(inverter).includesDay(event.getNewDate())){
                graph = new DayView(data.get(inverter), GetStartOf.day(event.getNewDate()));
                paintComponent();
            } else {
                if(!(event.getOldDate() == null)){
                    picker.setDate(event.getOldDate());
                }
            }
        });

        add(picker, BorderLayout.PAGE_START);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        /*
        JCheckBox b1 = new JCheckBox();
        JCheckBox b2 = new JCheckBox();
        JCheckBox b3 = new JCheckBox();
        JCheckBox b4 = new JCheckBox();
        JCheckBox b5 = new JCheckBox();
        b1.setText("consumption");
        b2.setText("consumption sum");
        b3.setText("production");
        b4.setText("production sum");
        b5.setText("own consumption");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
        b4.setSelected(true);
        b5.setSelected(true);
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
        b4.addActionListener(actionEvent -> {
            try {
                graph.setRow4Visible(b4.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b4.setSelected(!b4.isSelected());
            }
        });
        b5.addActionListener(actionEvent -> {
            try {
                graph.setRow5Visible(b5.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b5.setSelected(!b5.isSelected());
            }
        });
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);

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
        shaded.setSelected(true);
        shaded.addActionListener(actionEvent -> {
            boolean selected = shaded.isSelected();
            try {
                graph.setRow1Shaded(selected);
                graph.setRow3Shaded(selected);
                graph.setRow5Shaded(selected);
                paintComponent();
            } catch (NullPointerException e) {
                shaded.setSelected(!selected);
            }
        });
        p.add(shaded);

         */

        add(p,BorderLayout.WEST);
    }

    /**
     * Update the graph on the {@link BasicGraphCustomizer}
     */
    private void paintComponent() {
        instance.setGraph(graph, graph.getTitle());
    }
}
