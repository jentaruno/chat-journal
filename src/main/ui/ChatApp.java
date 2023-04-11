package ui;

import model.Category;
import model.Event;
import model.EventLog;
import model.Text;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Chat screen GUI
public class ChatApp extends JFrame implements ActionListener {
    private Category category;
    private String title;
    private List<Text> texts;
    private final MainApp mainApp;
    private final JLabel chatTitle;
    private final JTextField textField;
    private final JPanel textsPanel;

    // EFFECTS: creates chat screen linked to given main app, builds interface but not visible yet
    public ChatApp(MainApp mainApp) {
        this.mainApp = mainApp;
        chatTitle = new JLabel();
        textsPanel = new JPanel();
        textField = new JTextField(20);

        createInterface();
        setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: load information from given category and display it
    public void runCategory(Category initCategory) {
        EventLog.getInstance().logEvent(new Event("Chat opened: " + initCategory.getTitle()));
        initFields(initCategory);
        displayWindow();
        updateTitle();
        updateTexts();
    }

    // MODIFIES: this
    // EFFECTS: load information from given category to fields
    private void initFields(Category initCategory) {
        this.category = initCategory;
        this.title = initCategory.getTitle();
        this.texts = initCategory.getTextList();
    }

    // EFFECTS: set up window for chat screen and make visible
    private void displayWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: create UI, with navbar, texts, and typing bar
    private void createInterface() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        panel.add(createNavBar());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createTextsList());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createTypeBar());

        add(panel);
    }

    // MODIFIES: this
    // EFFECTS: create navbar, with category title and back button
    private JPanel createNavBar() {
        chatTitle.setHorizontalAlignment(JLabel.LEFT);
        chatTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));
        navBar.add(chatTitle);
        navBar.add(Box.createHorizontalGlue());
        navBar.add(backButton);
        navBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        return navBar;
    }

    // MODIFIES: this
    // EFFECTS: create scrollable panel for texts
    private JScrollPane createTextsList() {
        textsPanel.setLayout(new BoxLayout(textsPanel, BoxLayout.Y_AXIS));
        textsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scroll = new JScrollPane(textsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(150, 300));
        return scroll;
    }

    // MODIFIES: this
    // EFFECTS: create type bar, with text field and send button
    private JPanel createTypeBar() {
        JPanel typePanel = new JPanel();
        textField.addActionListener(e -> doSend());
        typePanel.add(textField);

        JButton sendButton = new JButton("Send");
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);
        typePanel.add(sendButton);
        typePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return typePanel;
    }

    // MODIFIES: this
    // EFFECTS: update title text to reflect current category
    private void updateTitle() {
        chatTitle.setText(title);
        chatTitle.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: update texts to reflect current category
    private void updateTexts() {
        textField.setText("");
        textsPanel.removeAll();
        for (Text text : texts) {
            JLabel dateBubble = new JLabel();
            dateBubble.setText(text.getDate());
            dateBubble.setForeground(Color.GRAY);
            textsPanel.add(dateBubble);
            for (String msg : text.getTexts()) {
                JLabel textBubble = new JLabel();
                textBubble.setText(msg);
                textsPanel.add(textBubble);
            }
        }
        textsPanel.revalidate();
    }

    // EFFECTS: link user's actions to methods they're supposed to trigger
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")) {
            doSend();
        }
        if (e.getActionCommand().equals("back")) {
            doBack();
        }
    }

    // MODIFIES: this, Category, Text
    // EFFECTS: add text user inputted in text area to chat
    private void doSend() {
        String message = textField.getText();
        if (message == null || message.equals("")) {
            return;
        }
        Text text = new Text(Category.getDateToday());
        text.add(message);
        category.addText(text);
        updateTexts();
    }

    // EFFECTS: return to main menu
    private void doBack() {
        EventLog.getInstance().logEvent(new Event("Chat closed: " + category.getTitle()));
        setVisible(false);
        mainApp.reactivate();
    }
}
