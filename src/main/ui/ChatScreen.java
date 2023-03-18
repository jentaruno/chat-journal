package ui;

import model.Category;
import model.Text;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChatScreen extends JFrame implements ActionListener {
    private MainMenu mainMenu;
    private Category category;
    private JLabel chatTitle;
    private String title;
    private List<Text> texts;
    private JTextField textField;
    private JPanel textsPanel;

    // EFFECTS: initialises fields and runs app
    public ChatScreen(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        displayInterface();
        setVisible(false);
    }

    public void runCategory(Category initCategory) {
        initFields(initCategory);
        displayWindow();
        updateNav();
        updateTexts();
    }

    private void initFields(Category initCategory) {
        this.category = initCategory;
        this.title = initCategory.getTitle();
        this.texts = initCategory.getTextList();
    }

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

    private void displayInterface() {
        chatTitle = new JLabel();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        panel.add(createNavBar());
        panel.add(createTextsList());
        panel.add(createTypeBar());

        add(panel);
    }

    private JPanel createNavBar() {
        chatTitle.setHorizontalAlignment(JLabel.LEFT);
        chatTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        JPanel navBar = new JPanel();
        navBar.add(chatTitle);
        navBar.add(backButton);
        return navBar;
    }

    private JPanel createTextsList() {
        textsPanel = new JPanel();
        textsPanel.setLayout(new BoxLayout(textsPanel, BoxLayout.Y_AXIS));
        return textsPanel;
    }


    private JPanel createTypeBar() {
        JPanel typePanel = new JPanel();
        textField = new JTextField(10);
        typePanel.add(textField);

        JButton sendButton = new JButton("Send");
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);
        typePanel.add(sendButton);

        return typePanel;
    }

    private void updateNav() {
        chatTitle.setText(title);
        chatTitle.revalidate();
        textField.setText("");
    }

    // EFFECTS: display existing texts, add spacing if too few texts
    private void updateTexts() {
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



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")) {
            doSend();
        }
        if (e.getActionCommand().equals("back")) {
            doBack();
        }
    }

    private void doSend() {
        String message = textField.getText();
        Text text = new Text(Category.getDateToday());
        text.add(message);
        category.addText(text);
        updateTexts();
    }

    private void doBack() {
        setVisible(false);
        mainMenu.reactivate();
    }
}
