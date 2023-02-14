package ui;

import model.Category;
import model.Text;

import java.util.List;
import java.util.Scanner;


// Chat UI with texts from a chat category
public class ChatApp {
    private Category category;
    private String title;
    private List<Text> texts;
    private final Scanner input;
    private boolean keepGoing = true;

    private static final String CLOSE_COMMAND = "!x";
    private static final int MIN_LINES = 4;

    // EFFECTS: initialises fields and runs app
    public ChatApp() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void init(Category initCategory) {
        this.category = initCategory;
        this.title = initCategory.getTitle();
        this.texts = initCategory.getTextList();
        this.keepGoing = true;
    }

    // MODIFIES: this
    // EFFECTS: processes user input for this category
    public void runCategory(Category initCategory) {
        init(initCategory);

        String command;

        while (keepGoing) {
            System.out.println("\n#### " + title + " ###############");
            System.out.println("(TYPE " + CLOSE_COMMAND + " TO CLOSE CHAT)");
            System.out.println("----------------------------------");
            displayTexts();
            System.out.println("----------------------------------");
            System.out.print("> ");
            command = input.next();

            processCommand(command);
        }
    }

    // EFFECTS: display existing texts, add spacing if too few texts
    private void displayTexts() {
        int lineCount = 0;
        for (Text text : texts) {
            System.out.println(text.getDate());
            lineCount++;
            for (String msg : text.getTexts()) {
                System.out.println("[" + msg + "]");
                lineCount++;
            }
        }
        while (lineCount < MIN_LINES) {
            System.out.println();
            lineCount++;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.startsWith(CLOSE_COMMAND)) {
            keepGoing = false;
        } else if (!command.replace(" ", "").isEmpty()) {
            category.addText(new Text(Category.getDateToday(), command));
        }
    }
}
