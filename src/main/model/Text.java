package model;

import org.json.JSONArray;
import org.json.JSONObject;

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

    // EFFECTS: creates a group of texts for a given date
    public Text(String date) {
        this.date = date;
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

    // EFFECTS: returns Text as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("texts", textsToJson());
        return json;
    }

    // EFFECTS: returns messages (strings) in this Text as a JSON array
    private JSONArray textsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String msg : texts) {
            jsonArray.put(msg);
        }

        return jsonArray;
    }
}
