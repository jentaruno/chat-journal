package ui;

import model.Category;
import model.CategoryList;
import model.Text;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Main menu UI with all chat categories
public class MainApp {
    private static String JSON_STORE = "./data/";
    private CategoryList categoryList;
    private ChatApp chatApp;
    private final Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String CHAT_COMMAND = "!c";
    private static final String DELETE_COMMAND = "!d";
    private static final String SAVE_COMMAND = "!s";
    private static final String LOAD_COMMAND = "!l";
    private static final String QUIT_COMMAND = "!q";
    private static final String CHAT_HELP = " [chat name] -> create chat [chat name] or open existing chat [chat name]";
    private static final String DELETE_HELP = " [chat name] -> delete chat [chat name]";
    private static final String SAVE_HELP = "             -> save Chat Journal to file";
    private static final String LOAD_HELP = " [name]      -> load [name]'s Chat Journal from file";
    private static final String QUIT_HELP = "             -> quit app";

    // EFFECTS: initialises fields and runs the chat journal application
    public MainApp() throws FileNotFoundException {
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
            System.out.println("\n############### " + categoryList.getUserName() + "'s Chat Journal ###############");
            displayCategories();

            displayCommands();
            command = input.next();

            if ((command.startsWith(QUIT_COMMAND))) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        doSave();
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: allows user to input name. if new user, sets userName as their name. if existing user, load their file
    private void inputUserName() {
        System.out.println("Please enter your name: ");
        System.out.print("> ");
        String userName = input.next();
        JSON_STORE += userName + ".json";
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        try {
            CategoryList cl = jsonReader.read();
            categoryList = cl;
            System.out.println("Welcome back, " + categoryList.getUserName() + "!");
        } catch (IOException e) {
            categoryList = new CategoryList(userName);
        }
    }

    // MODIFIES: this
    // EFFECTS: display commands for chat categories
    private void displayCommands() {
        System.out.println("\nCommands:");
        System.out.println("\t* " + CHAT_COMMAND + CHAT_HELP);
        System.out.println("\t* " + DELETE_COMMAND + DELETE_HELP);
        System.out.println("\t* " + SAVE_COMMAND + SAVE_HELP);
        System.out.println("\t* " + LOAD_COMMAND + LOAD_HELP);
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
                    textCount += text.getTexts().size();
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
        } else if (command.startsWith(SAVE_COMMAND)) {
            doSave();
        } else if (command.startsWith(LOAD_COMMAND + " ")) {
            doLoad(command);
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

    // EFFECTS: saves current Chat Journal to file
    private void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(categoryList);
            jsonWriter.close();
            System.out.println("Saved " + categoryList.getUserName() + "'s Chat Journal to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Chat Journal from file
    private void doLoad(String command) {
        String userToLoad = command.replace(LOAD_COMMAND + " ", "");
        try {
            JSON_STORE = "./data/" + userToLoad + ".json";
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
            categoryList = jsonReader.read();
            System.out.println("Loaded " + categoryList.getUserName() + "'s Chat Journal from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            JSON_STORE = ".data/" + categoryList.getUserName() + ".json";
        }
    }
}
