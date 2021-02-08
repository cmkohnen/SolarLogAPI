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
package me.meloni.SolarLogAPI.BasicGUI.Components;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;

/**
 * This is an interface to pick a year
 * @author Christoph Kohnen
 * @since 3.10.6
 */
public class MonthPicker extends JPanel {
    /**
     * The input field for the year
     */
    private final JIntegerTextField year;
    /**
     * The input field for the month
     */
    private final JComboBox<Month> month;
    /**
     * Update the selected
     */
    private final JButton accept;
    /**
     * Invoke the component
     */
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
        year.setText("2000");

        setLayout(new BorderLayout());
        add(month, BorderLayout.NORTH);
        add(year, BorderLayout.CENTER);
        add(accept, BorderLayout.SOUTH);
    }

    /**
     * Add an {@link ActionListener} to the button
     * @param listener The {@link ActionListener} that should be added
     */
    public void addActionListener(ActionListener listener) {
        accept.addActionListener(listener);
    }

    /**
     * Get the currently selected month
     * @return The currently selected month
     */
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
