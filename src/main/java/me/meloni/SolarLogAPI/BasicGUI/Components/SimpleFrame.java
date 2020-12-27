package me.meloni.SolarLogAPI.BasicGUI.Components;

import javax.swing.*;

public class SimpleFrame extends JFrame {

    public SimpleFrame(JComponent jComponent) {
        add(jComponent);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,800);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
