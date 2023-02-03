package model;

import java.util.List;

// List of chat categories the user has created
public class CategoryList {
    private List<Category> categoryList;

    //getters
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public Category getCategory(int i) {
        return categoryList.get(i);
    }

    // MODIFIES: this, Category
    // EFFECTS: creates new chat category titled with user input
    public void newChatCategory(String title) {
        //stub
    }

    // MODIFIES: this, Category
    // EFFECTS: deletes given chat category
    public void deleteChatCategory(String title) {
        //stub
    }

}
