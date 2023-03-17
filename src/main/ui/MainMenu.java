package ui;

import model.CategoryList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;

    public MainMenu() {
        super("Chat Journal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        label = new JLabel("Chat Journal");
        add(label);
        displayButtons();
        displayChats();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void displayButtons() {
        JButton btn1 = new JButton("Add");
        btn1.setActionCommand("add");
        btn1.addActionListener(this);
        JButton btn2 = new JButton("Save");
        btn2.setActionCommand("save");
        btn2.addActionListener(this);
        JButton btn3 = new JButton("Load");
        btn3.setActionCommand("load");
        btn3.addActionListener(this);
        add(btn1);
        add(btn2);
        add(btn3);
    }

    public void displayChats() {

    }

    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            label.setText(field.getText());
        }
    }
}