package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Chat category with a title and a list of texts in it
public class Category {
    String title;
    List<Text> textList;

    // EFFECTS: creates a category with given title, and an empty list of texts
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

    // EFFECTS: returns date today
    public static String getDateToday() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return String.valueOf(date);
    }

    // REQUIRES: text is not an empty string or null
    // MODIFIES: this, Text
    // EFFECTS: add given text to list of texts from new/existing date
    public void addText(Text text) {
        String lastDate = "";
        Text lastText = null;

        if (textList.size() > 0) {
            int lastIndex = textList.size() - 1;
            lastText = textList.get(lastIndex);
            lastDate = lastText.getDate();
        }
        if (lastDate.equals(Category.getDateToday())) {
            assert lastText != null;
            lastText.add(text.getText(0));
        } else {
            textList.add(text);
        }
    }

    // EFFECTS: returns Category as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("textList", textListToJson());
        return json;
    }

    // EFFECTS: returns Texts in this Category as a JSON array
    public JSONArray textListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Text text : textList) {
            jsonArray.put(text.toJson());
        }
        return jsonArray;
    }
}
