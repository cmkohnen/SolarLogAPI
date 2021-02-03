package me.meloni.SolarLogAPI.BasicGUI.Components;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;

public class MonthPicker extends JPanel {
    private final JIntegerTextField year;
    private final JComboBox<Month> month;
    private final JButton accept;
    public MonthPicker() {
        Month[] months = {Month.JANUARY, Month.FEBRUARY,
                Month.MARCH, Month.APRIL, Month.MAY,
                Month.JUNE, Month.JULY, Month.AUGUST,
                Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER,
                Month.DECEMBER};
        this.year = new JIntegerTextField();
        this.month = new JComboBox<>(months);
        this.accept = new JButton("...");

        year.setEditable(true);

        setLayout(new BorderLayout());
        add(month, BorderLayout.NORTH);
        add(year, BorderLayout.CENTER);
        add(accept, BorderLayout.SOUTH);
    }

    public void addActionListener(ActionListener listener) {
        accept.addActionListener(listener);
    }

    public YearMonth getMonth() {
        try {
            int year = this.year.getValue();
            int month = ((Month) Objects.requireNonNull(this.month.getSelectedItem())).getValue();
            return YearMonth.of(year, month);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
