package persistence;

import model.Category;
import model.CategoryList;
import model.Text;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCategoryList(String userName, int numCategories, CategoryList categoryList) {
        assertEquals(userName, categoryList.getUserName());
        assertEquals(numCategories, categoryList.getCategoryList().size());
    }

    protected void checkCategory(String title, int numTexts, Category category) {
        assertEquals(title, category.getTitle());
        assertEquals(numTexts, category.getTextList().size());
    }

    protected void checkText(String date, int numMessages, Text text) {
        assertEquals(date, text.getDate());
        assertEquals(numMessages, text.getTexts().size());
    }

    protected void checkMessage(String message, int index, Text text) {
        assertEquals(message, text.getText(index));
    }
}
