package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.Components.DatePicker;
import me.meloni.SolarLogAPI.BasicGUI.BasicGraphCustomizer;
import me.meloni.SolarLogAPI.SolarMap;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.Year;

/**
 * This Class includes a function to display yearly graph data.
 * @author ChaosMelone9
 * @since 3.4.5
 */
public class YearCustomizer extends JPanel{
    static YearView cmp = null;
    BasicGraphCustomizer instance;

    public YearCustomizer(SolarMap data, BasicGraphCustomizer instance) {
        this.instance = instance;
        setLayout(new BorderLayout());

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data);
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            try {
                cmp = new YearView(data, Year.of(event.getNewDate().getYear()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            paintComponent();
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
            cmp.setRow1Visible(b1.isSelected());
            paintComponent();
        });
        b2.addActionListener(actionEvent -> {
            cmp.setRow2Visible(b2.isSelected());
            paintComponent();
        });
        b3.addActionListener(actionEvent -> {
            cmp.setRow3Visible(b3.isSelected());
            paintComponent();
        });
        p.add(b1);
        p.add(b2);
        p.add(b3);

        JCheckBox mouseGUI = new JCheckBox();
        mouseGUI.setText("Mouse Feedback");
        mouseGUI.setSelected(true);
        mouseGUI.addActionListener(actionEvent -> {
            cmp.setMouseGUIVisible(mouseGUI.isSelected());
            paintComponent();
        });
        p.add(mouseGUI);

        JCheckBox shaded = new JCheckBox();
        shaded.setText("Shade graph");
        shaded.setSelected(false);
        shaded.addActionListener(actionEvent -> {
            boolean selected = shaded.isSelected();
            cmp.setRow1Shaded(selected);
            cmp.setRow2Shaded(selected);
            cmp.setRow3Shaded(selected);
            paintComponent();
        });
        p.add(shaded);

        add(p,BorderLayout.WEST);
    }

    private void paintComponent() {
        instance.setGraph(cmp, cmp.getTitle());
    }
}
