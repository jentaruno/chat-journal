package persistence;

import model.Category;
import model.CategoryList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Text;
import org.json.*;

// Represents a reader that reads categoryList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads category list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CategoryList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCategoryList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses categoryList from JSON object and returns it
    private CategoryList parseCategoryList(JSONObject jsonObject) {
        String name = jsonObject.getString("userName");
        CategoryList categoryList = new CategoryList(name);
        addCategories(categoryList, jsonObject);
        return categoryList;
    }

    // MODIFIES: categoryList
    // EFFECTS: parses categories from JSON object and adds them to categoryList
    private void addCategories(CategoryList categoryList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categoryList");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(categoryList, nextCategory);
        }
    }

    // MODIFIES: categoryList
    // EFFECTS: parses category from JSON object and adds it to category list
    private void addCategory(CategoryList categoryList, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        Category category = new Category(title);
        JSONArray jsonArray = jsonObject.getJSONArray("textList");
        for (Object json : jsonArray) {
            JSONObject nextText = (JSONObject) json;
            addText(category, nextText);
        }
        categoryList.addChatCategory(category);
    }

    // MODIFIES: categoryList
    // EFFECTS: parses text from JSON object and adds it to category
    private void addText(Category category, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        Text text = new Text(date);
        JSONArray jsonArray = jsonObject.getJSONArray("texts");
        for (int i = 0; i < jsonArray.length(); i++) {
            text.add(jsonArray.getString(i));
        }
        category.addText(text);
    }
}
