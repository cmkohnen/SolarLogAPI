package me.meloni.SolarLogAPI.BasicGUI.Components;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Year;

public class YearPicker extends JPanel {
    private final JIntegerTextField year;
    private final JButton accept;
    public YearPicker() {
        this.year = new JIntegerTextField();
        this.accept = new JButton("...");

        year.setEditable(true);

        setLayout(new BorderLayout());
        add(year, BorderLayout.WEST);
        add(accept, BorderLayout.EAST);
    }

    public void addActionListener(ActionListener listener) {
        accept.addActionListener(listener);
    }

    public Year getYear() {
        return Year.of(year.getValue());
    }
}
