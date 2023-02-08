package model;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import model.Utility;

// Group of texts for a certain date
public class Text {
    private String date;
    private List<String> texts;

    public Text() {
        this.date = Utility.getDateToday();
        this.texts = new ArrayList<>();
    }

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
