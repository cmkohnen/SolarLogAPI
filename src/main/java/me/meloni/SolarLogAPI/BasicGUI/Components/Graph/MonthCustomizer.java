package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.BasicGraphCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.Components.MonthPicker;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.YearMonth;

/**
 * This Class includes a function to display monthly graph data.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class MonthCustomizer extends JPanel{
    static MonthView cmp = null;
    BasicGraphCustomizer instance;

    public MonthCustomizer(SolarMap data, BasicGraphCustomizer instance) {
        this.instance = instance;
        setLayout(new BorderLayout());


        MonthPicker picker = new MonthPicker();
        picker.setSize(new Dimension(getWidth(), 80));
        picker.addActionListener(event -> {
            YearMonth month = picker.getMonth();
            if(month != null && data.includesMonth(month)) {
                try {
                    cmp = new MonthView(data, month);
                    paintComponent();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        add(picker, BorderLayout.PAGE_START);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        JCheckBox b1 = new JCheckBox();
        JCheckBox b2 = new JCheckBox();
        JCheckBox b3 = new JCheckBox();
        b1.setText("Verbrauch kWH");
        b2.setText("EigenVerbrauch kWH");
        b3.setText("Produktion kWH");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
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
        p.add(b1);
        p.add(b2);
        p.add(b3);

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
        shaded.setSelected(false);
        shaded.addActionListener(actionEvent -> {
            boolean selected = shaded.isSelected();
            try {
                cmp.setRow1Shaded(selected);
                cmp.setRow2Shaded(selected);
                cmp.setRow3Shaded(selected);
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
