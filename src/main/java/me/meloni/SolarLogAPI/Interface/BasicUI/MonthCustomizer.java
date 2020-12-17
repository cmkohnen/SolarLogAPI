package me.meloni.SolarLogAPI.Interface.BasicUI;

import me.meloni.SolarLogAPI.SolarMap;
import me.meloni.SolarLogAPI.Interface.DatePicker;
import me.meloni.SolarLogAPI.Interface.Graph.MonthView;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * This Class includes a function to display monthly graph data.
 * @author ChaosMelone9
 * @since 1.0.0
 */
public class MonthCustomizer extends JPanel{
    static MonthView cmp = null;

    public MonthCustomizer(SolarMap data) {
        setLayout(new BorderLayout());

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data);
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            if(data.includesMonth(GetStartOf.yearMonth(event.getNewDate()))){
                try {
                    cmp = new MonthView(data.getMonthGraphData(GetStartOf.yearMonth(event.getNewDate())));
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
        b1.setText("Row 1");
        b2.setText("Row 2");
        b3.setText("Row 3");
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
        mouseGUI.setText("MouseGUI");
        mouseGUI.setSelected(true);
        mouseGUI.addActionListener(actionEvent -> {
            cmp.setMouseGUIVisible(mouseGUI.isSelected());
            paintComponent();
        });
        p.add(mouseGUI);

        add(p,BorderLayout.WEST);
    }

    public void paintComponent() {
        GraphCustomizer.setCmp(cmp);
    }

}
