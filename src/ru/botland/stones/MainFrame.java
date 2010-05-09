package ru.botland.stones;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: botdes
 * Date: 09.05.2010
 * Time: 19:11:29
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame implements Allower {
    private JCheckBox jCheckBox;

    public MainFrame() {
        setSize(200, 300);
        setTitle("Stones"); 
        jCheckBox = new JCheckBox("listen clipboard", true);
        getContentPane().add(jCheckBox);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public boolean allow() {
        return jCheckBox.isSelected();
    }
}
