package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.Components.DatePicker;
import me.meloni.SolarLogAPI.BasicGUI.BasicGraphCustomizer;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * This Class includes a function to display daily graph data.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class DayCustomizer extends JPanel{
    static DayView cmp = null;
    BasicGraphCustomizer instance;

    public DayCustomizer(SolarMap data, BasicGraphCustomizer instance) {
        this.instance = instance;
        setLayout(new BorderLayout());

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data);
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            if(data.includesDay(event.getNewDate())){
                try {
                    cmp = new DayView(data, GetStartOf.day(event.getNewDate()));
                    paintComponent();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if(!(event.getOldDate() == null)){
                    picker.setDate(event.getOldDate());
                }
            }
        });

        add(picker, BorderLayout.PAGE_START);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        JCheckBox b1 = new JCheckBox();
        JCheckBox b2 = new JCheckBox();
        JCheckBox b3 = new JCheckBox();
        JCheckBox b4 = new JCheckBox();
        JCheckBox b5 = new JCheckBox();
        b1.setText("verbrauchw");
        b2.setText("verbrauchkwh");
        b3.setText("leistungw");
        b4.setText("ertragkwh");
        b5.setText("energieverbrauchw");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
        b4.setSelected(true);
        b5.setSelected(true);
        b1.addActionListener(actionEvent -> {
            try {
                cmp.setRow1Visible(b1.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b1.setSelected(!b1.isSelected());
            }
        });
        b2.addActionListener(actionEvent -> {
            try {
                cmp.setRow2Visible(b2.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b2.setSelected(!b2.isSelected());
            }
        });
        b3.addActionListener(actionEvent -> {
            try {
                cmp.setRow3Visible(b3.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b3.setSelected(!b3.isSelected());
            }
        });
        b4.addActionListener(actionEvent -> {
            try {
                cmp.setRow4Visible(b4.isSelected());
                paintComponent();
            } catch (NullPointerException e) {
                b4.setSelected(!b4.isSelected());
            }
        });
        b5.addActionListener(actionEvent -> {
            try {
                cmp.setRow5Visible(b5.isSelected());
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
                cmp.setMouseGUIVisible(mouseGUI.isSelected());
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
                cmp.setRow1Shaded(selected);
                cmp.setRow3Shaded(selected);
                cmp.setRow5Shaded(selected);
                paintComponent();
            } catch (NullPointerException e) {
                shaded.setSelected(!selected);
            }
        });
        p.add(shaded);

        add(p,BorderLayout.WEST);
    }

    private void paintComponent() {
        instance.setGraph(cmp, cmp.getTitle());
    }
}
