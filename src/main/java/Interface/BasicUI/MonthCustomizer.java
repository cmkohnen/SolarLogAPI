package Interface.BasicUI;

import Interface.DatePicker;
import Interface.Graph.Graph;
import Interface.Graph.MonthView;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MonthCustomizer extends JPanel{
    static MonthView cmp = null;

    public MonthCustomizer(Map<Date, List<Integer>> data) {
        setLayout(new BorderLayout());

        DatePicker picker = new DatePicker();
        picker.addVetoPolicy(data);
        picker.setMaximumSize(new Dimension(200,40));
        picker.addDateChangeListener(event -> {
            Date daystamp = Date.from(event.getNewDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(data.containsKey(daystamp)){
                try {
                    YearMonth yearMonth = YearMonth.from(daystamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    cmp = Graph.monthView(yearMonth,data);
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
        b1.setText("Row 1");
        b2.setText("Row 2");
        b3.setText("Row 3");
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
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
        p.add(b1);
        p.add(b2);
        p.add(b3);

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