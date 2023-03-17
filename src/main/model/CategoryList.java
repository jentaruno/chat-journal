package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// List of chat categories the user has created
public class CategoryList implements Writable {
    private String userName;
    private List<Category> categoryList;

    // EFFECTS: create an empty category list, username is given username
    public CategoryList(String userName) {
        this.userName = userName;
        categoryList = new ArrayList<>();
    }

    //getters
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<JLabel> getCategoryTitles() {
        return categoryList.stream().map(e -> fromString(e.getTitle())).collect(Collectors.toList());
    }

    public String getUserName() {
        return userName;
    }

    // EFFECTS: return category if category exists in category list, otherwise return null
    public Category getChatCategory(String title) {
        for (Category category : categoryList) {
            if (category.getTitle().equals(title)) {
                return category;
            }
        }
        return null;
    }

    // MODIFIES: this, Category
    // EFFECTS: creates new chat category titled with user input
    public void newChatCategory(String title) {
        Category newCategory = new Category(title);
        categoryList.add(newCategory);
    }

    // MODIFIES: this, Category
    // EFFECTS: deletes given chat category
    public void deleteChatCategory(String title) {
        Category deletedCategory = getChatCategory(title);
        categoryList.remove(deletedCategory);
    }

    // MODIFIES: this
    // EFFECTS: adds given chat category to category list
    public void addChatCategory(Category category) {
        categoryList.add(category);
    }

    // EFFECTS: returns CategoryList as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("categoryList", categoriesToJson());
        return json;
    }

    // EFFECTS: returns categories in this CategoryList as a JSON array
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Category category : categoryList) {
            jsonArray.put(category.toJson());
        }
        return jsonArray;
    }

    private JLabel fromString(String str) {
        XMLDecoder d = new XMLDecoder(new ByteArrayInputStream(str.getBytes()));
        JLabel label = (JLabel) d.readObject();
        d.close();
        return label;
    }

}
