package Interface.BasicUI;

import Handling.SolarMap;
import Interface.DatePicker;
import Interface.Graph.DayView;
import TransformUtilities.DataConversion.GetStartOf;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class DayCustomizer extends JPanel{
    static DayView cmp = null;

    public DayCustomizer(SolarMap data) {
        setLayout(new BorderLayout());

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data);
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            if(data.includesDay(event.getNewDate())){
                try {
                    cmp = new DayView(data.getDayGraphData(GetStartOf.day(event.getNewDate())));
                    paintcmp();
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
        b1.setText("Row 1");
        b2.setText("Row 2");
        b3.setText("Row 3");
        b4.setText("Row 4");
        b5.setText("Row 5");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
        b4.setSelected(true);
        b5.setSelected(true);
        b1.addActionListener(actionEvent -> {
            cmp.setRow1Visible(b1.isSelected());
            paintcmp();
        });
        b2.addActionListener(actionEvent -> {
            cmp.setRow2Visible(b2.isSelected());
            paintcmp();
        });
        b3.addActionListener(actionEvent -> {
            cmp.setRow3Visible(b3.isSelected());
            paintcmp();
        });
        b4.addActionListener(actionEvent -> {
            cmp.setRow4Visible(b4.isSelected());
            paintcmp();
        });
        b5.addActionListener(actionEvent -> {
            cmp.setRow5Visible(b5.isSelected());
            paintcmp();
        });
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);

        JCheckBox mousegui = new JCheckBox();
        mousegui.setText("MouseGUI");
        mousegui.setSelected(true);
        mousegui.addActionListener(actionEvent -> {
            cmp.setMouseGUIVisible(mousegui.isSelected());
            paintcmp();
        });
        p.add(mousegui);

        add(p,BorderLayout.WEST);
    }

    public void paintcmp() {
        GraphCustomizer.setCmp(cmp);
    }

}
