package model;

import java.util.ArrayList;
import java.util.List;

// List of chat categories the user has created
public class CategoryList {
    private List<Category> categoryList;

    // EFFECTS: create an empty category list
    public CategoryList() {
        categoryList = new ArrayList<>();
    }

    //getters
    public List<Category> getCategoryList() {
        return categoryList;
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

}
