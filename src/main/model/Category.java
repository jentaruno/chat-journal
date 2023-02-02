package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    String title;
    List<Text> textList;

    // Constructor
    public Category(String title) {
        this.title = title;
        this.textList = new ArrayList<Text>();
    }

    //getters
    public String getTitle() {
        return title;
    }

    public List<Text> getTextList() {
        return textList;
    }

    // MODIFIES: this, Text
    // EFFECTS: add given text to list of texts from new/existing date
    public void addText(String text) {
        //stub
    }

    // MODIFIES: this, Text
    // EFFECTS: add given text by creating new Text instance
    private void addNewDateText(String text) {
        //stub
    }

    // MODIFIES: this, Text
    // EFFECTS: add given text to existing Text class with current date
    private void addTodayText(String text) {
        //stub
    }
}
