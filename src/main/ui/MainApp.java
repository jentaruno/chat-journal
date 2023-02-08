package ui;

import model.Category;
import model.CategoryList;
import model.Text;

import java.util.List;
import java.util.Scanner;

// Main menu UI with all chat categories
public class MainApp {
    private String userName;
    private CategoryList categoryList;
    private ChatApp chatApp;
    private Scanner input;

    private static final String CHAT_COMMAND = "!c";
    private static final String DELETE_COMMAND = "!d";
    private static final String QUIT_COMMAND = "!q";
    private static final String CHAT_HELP = " [chat name] -> create chat [chat name] or open existing chat [chat name]";
    private static final String DELETE_HELP = " [chat name] -> delete chat [chat name]";
    private static final String QUIT_HELP = "             -> quit app";

    // EFFECTS: initialises fields and runs the chat journal application
    public MainApp() {
        userName = "";
        categoryList = new CategoryList();
        chatApp = new ChatApp();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runMenu();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runMenu() {
        boolean keepGoing = true;
        String command;

        inputUserName();

        while (keepGoing) {
            System.out.println("\n############### " + userName + "'s Chat Journal ###############");
            displayCategories();

            displayCommands();
            command = input.next();

            if ((command.startsWith(QUIT_COMMAND))) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: allows user to input name and sets userName as their name
    private void inputUserName() {
        System.out.println("Please enter your name: ");
        System.out.print("> ");
        userName = input.next();
    }

    // MODIFIES: this
    // EFFECTS: display commands for chat categories
    private void displayCommands() {
        System.out.println("\nCommands:");
        System.out.println("\t* " + CHAT_COMMAND + CHAT_HELP);
        System.out.println("\t* " + DELETE_COMMAND + DELETE_HELP);
        System.out.println("\t* " + QUIT_COMMAND + QUIT_HELP);
        System.out.print("> ");
    }

    // EFFECTS: display current list of chat categories
    private void displayCategories() {
        if (categoryList.getCategoryList().size() == 0) {
            System.out.println("You currently have no chats.");
        } else {
            for (Category category : categoryList.getCategoryList()) {
                List<Text> texts = category.getTextList();
                int textCount = 0;
                for (Text text : texts) {
                    for (String msg : text.getTexts()) {
                        textCount++;
                    }
                }
                System.out.println("- " + category.getTitle() + ": " + textCount + " texts");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input for chat categories
    private void processCommand(String command) {
        if (command.startsWith(CHAT_COMMAND + " ")) {
            doOpenChat(command);
        } else if (command.startsWith(DELETE_COMMAND + " ")) {
            doDelete(command);
        } else {
            System.out.println("Invalid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates chat category with given name, or open existing one with given name
    private void doOpenChat(String command) {
        String selectedCategory = command.replace(CHAT_COMMAND + " ", "");
        if (categoryList.getChatCategory(selectedCategory) == null) {
            categoryList.newChatCategory(selectedCategory);
        }
        Category openedCategory = categoryList.getChatCategory(selectedCategory);
        chatApp.runCategory(openedCategory);
    }

    // MODIFIES: this
    // EFFECTS: deletes given chat category if it exists, otherwise print invalid
    private void doDelete(String command) {
        String selectedCategory = command.replace(DELETE_COMMAND + " ", "");
        if (categoryList.getChatCategory(selectedCategory) != null) {
            categoryList.deleteChatCategory(selectedCategory);
            System.out.println(selectedCategory + " has been deleted.");
        } else {
            System.out.println("Chat '" + selectedCategory + "' does not exist!");
        }
    }
}
