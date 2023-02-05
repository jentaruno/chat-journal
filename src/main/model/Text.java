package model;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import ui.ChatApp;

// Group of texts for a certain date
public class Text {
    private String date;
    private List<String> texts;

    public Text() {
        this.date = ChatApp.getDateToday();
        this.texts = new ArrayList<>();
    }

    //getters
    public String getDate() {
        return date;
    }

    public List<String> getTexts() {
        return texts;
    }

    public String getText(int i) {
        return texts.get(i);
    }

    // REQUIRES: text is not an empty string
    // MODIFIES: this
    // EFFECTS: adds given string to text list
    public void add(String text) {
        texts.add(text);
    }
}
