package model;

import java.util.ArrayList;
import java.util.List;

import ui.ChatApp;

// Chat category with a title and a list of texts in it
public class Category {
    String title;
    List<Text> textList;

    // Constructor
    public Category(String title) {
        this.title = title;
        this.textList = new ArrayList<>();
    }

    //getters
    public String getTitle() {
        return title;
    }

    public List<Text> getTextList() {
        return textList;
    }

    // REQUIRES: text is not an empty string or null
    // MODIFIES: this, Text
    // EFFECTS: add given text to list of texts from new/existing date
    public void addText(String text) {
        String lastDate = "";
        Text lastText = null;

        if (textList.size() > 0) {
            int lastIndex = textList.size() - 1;
            lastText = textList.get(lastIndex);
            lastDate = lastText.getDate();
        }
        if (lastDate.equals(ChatApp.getDateToday())) {
            lastText.add(text);
        } else {
            Text newText = new Text();
            newText.add(text);
            textList.add(newText);
        }
    }
}
