package model;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

// Group of texts for a certain date
public class Text {
    private String date;
    private List<String> texts;

    // EFFECTS: creates a group of texts for today's date, with an empty list of texts
    public Text() {
        this.date = Category.getDateToday();
        this.texts = new ArrayList<>();
    }

    // EFFECTS: creates a group of texts for a given date, with given text in list of texts
    public Text(String date, String msg) {
        this.date = date;
        this.texts = new ArrayList<>();
        this.texts.add(msg);
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
