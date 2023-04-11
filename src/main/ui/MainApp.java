package ui;

import model.Category;
import model.CategoryList;
import model.EventLog;
import model.Event;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Main menu screen
public class MainApp extends JFrame implements ActionListener, WindowListener {
    private static String JSON_STORE = "./data/";
    private JLabel userNameLabel;
    private CategoryList categoryList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultListModel<String> listModel;
    private final ChatApp chatApp;
    JList<String> list;

    // EFFECTS: initialises fields and runs main menu
    public MainApp() {
        super("Chat Journal");
        chatApp = new ChatApp(this);
        getUserName();
        displayWindow();
        createInterface();
    }

    // EFFECTS: make main menu window visible
    public void reactivate() {
        setVisible(true);
    }

    // EFFECTS: set up window for main menu screen
    private void displayWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        addWindowListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this, CategoryList
    // EFFECTS: allows user to input name. if new user, sets userName as their name. if existing user, load their file
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

    // MODIFIES: this
    // EFFECTS: set up save file path, json writer, and reader according to given username
    private void setupJson(String userName) {
        JSON_STORE += userName + ".json";
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: create UI, with navbar, texts, and typing bar
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

    // MODIFIES: this
    // EFFECTS: create app heading according to current user's name
    private JLabel createHeading() {
        userNameLabel = new JLabel(categoryList.getUserName() + "'s Chat Journal");
        userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return userNameLabel;
    }

    // EFFECTS: create add, delete, save, and load buttons
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

    // MODIFIES: this
    // EFFECTS: create list layout for chat categories
    private JList<String> createChats() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(250, 20));
        return list;
    }

    // EFFECTS: create button to open chat
    private JButton createOpenButton() {
        JButton openButton = new JButton("Open Chat");
        openButton.setActionCommand("open");
        openButton.addActionListener(this);
        return openButton;
    }

    // MODIFIES: this
    // EFFECTS: update chat categories to reflect current category list
    private void updateChats() {
        listModel.clear();
        for (Category c : categoryList.getCategoryList()) {
            listModel.addElement(c.getTitle());
        }
        list.repaint();
    }

    // MODIFIES: this
    // EFFECTS: update username to reflect current username
    private void updateUserName() {
        userNameLabel.setText(categoryList.getUserName() + "'s Chat Journal");
    }

    // EFFECTS: link user's actions to methods they're supposed to trigger
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

    // MODIFIES: this, CategoryList
    // EFFECTS: creates chat category with given name
    private void doNewChat() {
        String chatTitle = JOptionPane.showInputDialog(null, "Please enter a chat title:");
        categoryList.newChatCategory(chatTitle);
        updateChats();
    }

    // MODIFIES: this, ChatApp
    // EFFECTS: open selected chat
    private void doOpenChat() {
        String selectedCategory = list.getSelectedValue();
        if (selectedCategory == null) {
            return;
        }
        setVisible(false);
        chatApp.runCategory(categoryList.getChatCategory(selectedCategory));
    }

    // MODIFIES: this, CategoryList
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

    // MODIFIES: this, CategoryList
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

    // EFFECTS: Log events when window is closed
    public void windowClosing(WindowEvent e) {
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

}