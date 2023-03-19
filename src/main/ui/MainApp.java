package ui;

import model.Category;
import model.CategoryList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainApp extends JFrame implements ActionListener {
    private static String JSON_STORE = "./data/";
    private JLabel userNameLabel;
    private CategoryList categoryList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultListModel<String> listModel;
    private final ChatApp chatApp;
    JList<String> list;

    public MainApp() {
        super("Chat Journal");
        chatApp = new ChatApp(this);
        getUserName();
        displayWindow();
        createInterface();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void reactivate() {
        setVisible(true);
    }

    private void displayWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
    }

    private void getUserName() {
        String userName = JOptionPane.showInputDialog(null, "Please enter a user name:");
        if (userName == null || userName.equals("")) {
            userName = "Anon";
        }
        setupJson(userName);
        try {
            categoryList = jsonReader.read();
        } catch (IOException e) {
            categoryList = new CategoryList(userName);
        }
    }

    private void setupJson(String userName) {
        JSON_STORE += userName + ".json";
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void createInterface() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        panel.add(createHeading());
        panel.add(createNavButtons());
        panel.add(createChats());
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createOpenButton());
        updateChats();

        add(panel);
    }

    private JLabel createHeading() {
        userNameLabel = new JLabel(categoryList.getUserName() + "'s Chat Journal");
        userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return userNameLabel;
    }

    private JPanel createNavButtons() {
        JButton btn0 = new JButton("Add");
        btn0.setActionCommand("add");
        btn0.addActionListener(this);

        JButton btn1 = new JButton("Delete");
        btn1.setActionCommand("delete");
        btn1.addActionListener(this);

        JButton btn2 = new JButton("Save");
        btn2.setActionCommand("save");
        btn2.addActionListener(this);

        JButton btn3 = new JButton("Load");
        btn3.setActionCommand("load");
        btn3.addActionListener(this);

        JPanel menuItems = new JPanel();
        menuItems.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuItems.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuItems.add(btn0);
        menuItems.add(btn1);
        menuItems.add(btn2);
        menuItems.add(btn3);
        menuItems.setPreferredSize(new Dimension(400, 50));
        return menuItems;
    }

    private JList<String> createChats() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 20));
        return list;
    }

    private JButton createOpenButton() {
        JButton openButton = new JButton("Open Chat");
        openButton.setActionCommand("open");
        openButton.addActionListener(this);
        return openButton;
    }

    private void updateChats() {
        listModel.clear();
        for (Category c : categoryList.getCategoryList()) {
            listModel.addElement(c.getTitle());
        }
        list.repaint();
    }

    private void updateUserName() {
        userNameLabel.setText(categoryList.getUserName() + "'s Chat Journal");
    }

    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            doNewChat();
        }
        if (e.getActionCommand().equals("delete")) {
            doDelete();
        }
        if (e.getActionCommand().equals("save")) {
            doSave();
        }
        if (e.getActionCommand().equals("load")) {
            doLoad();
        }
        if (e.getActionCommand().equals("open")) {
            doOpenChat();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates chat category with given name
    private void doNewChat() {
        String chatTitle = JOptionPane.showInputDialog(null, "Please enter a chat title:");
        categoryList.newChatCategory(chatTitle);
        updateChats();
    }

    private void doOpenChat() {
        String selectedCategory = list.getSelectedValue();
        if (selectedCategory == null) {
            return;
        }
        setVisible(false);
        chatApp.runCategory(categoryList.getChatCategory(selectedCategory));
    }

    // MODIFIES: this
    // EFFECTS: deletes selected chat category
    private void doDelete() {
        String selectedCategory = list.getSelectedValue();
        categoryList.deleteChatCategory(selectedCategory);
        updateChats();
    }

    // EFFECTS: saves current Chat Journal to file
    private void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(categoryList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Chat Journal from file
    private void doLoad() {
        String userToLoad = JOptionPane.showInputDialog(null, "Please enter a user name:");
        try {
            JSON_STORE = "./data/" + userToLoad + ".json";
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
            categoryList = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
            JSON_STORE = ".data/" + categoryList.getUserName() + ".json";
        }
        updateChats();
        updateUserName();
    }
}