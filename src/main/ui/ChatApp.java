package ui;

import model.Category;
import model.Text;

import java.util.List;


// Chat UI with texts from a chat category
public class ChatApp {
    private List<Text> texts;

    // EFFECTS: initialises fields and runs app
    public ChatApp() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: processes user input for this category
    public void runCategory(Category category) {
        //stub
    }

    // EFFECTS: display existing texts
    private void displayTexts() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        //stub
    }

    // MODIFIES: this, Category, Text
    // EFFECTS: allows user to input text, add it to list of texts
    private void inputNewText() {
        //stub
    }

    // EFFECTS: closes category and return to main menu
    private void closeCategory() {
        //stub
    }
}
