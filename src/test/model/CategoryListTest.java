package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryListTest {

    CategoryList categoryList;

    @BeforeEach
    void setUp() {
        categoryList = new CategoryList("User");
    }

    @Test
    void CategoryListTest() {
        assertEquals("User", categoryList.getUserName());
        assertEquals(new ArrayList<>(), categoryList.getCategoryList());
    }

    void generateTestCategories() {
        categoryList.newChatCategory("Diary");
        categoryList.newChatCategory("Ideas");
    }

    @Test
    void getChatCategoryTest() {
        generateTestCategories();
        assertEquals("Diary", categoryList.getChatCategory("Diary").getTitle());
        assertEquals("Ideas", categoryList.getChatCategory("Ideas").getTitle());
        assertNull(categoryList.getChatCategory("Travel"));
    }

    @Test
    void addChatCategoryTest() {
        categoryList.addChatCategory(new Category("Notes"));
        assertEquals("Notes", categoryList.getChatCategory("Notes").getTitle());
    }

    @Test
    void newChatCategoryTest() {
        generateTestCategories();
        assertEquals("Diary", categoryList.getChatCategory("Diary").getTitle());
        assertEquals("Ideas", categoryList.getChatCategory("Ideas").getTitle());
    }

    @Test
    void deleteChatCategoryTest() {
        generateTestCategories();
        categoryList.deleteChatCategory("Diary");
        assertEquals(1, categoryList.getCategoryList().size());
        assertEquals("Ideas", categoryList.getChatCategory("Ideas").getTitle());
        categoryList.deleteChatCategory("Ideas");
        assertEquals(0, categoryList.getCategoryList().size());
    }

    @Test
    void toJsonTest() {
        assertEquals("{\"categoryList\":[],\"userName\":\"User\"}", categoryList.toJson().toString());
        categoryList.newChatCategory("Food");
        assertEquals("{\"categoryList\":[{\"textList\":[],\"title\":\"Food\"}],\"userName\":\"User\"}",
                categoryList.toJson().toString());
        categoryList.newChatCategory("Travel");
        assertEquals("{\"categoryList\":[{\"textList\":[],\"title\":\"Food\"},{\"textList\":[],\"title\":\"Travel\"}],\"userName\":\"User\"}",
                categoryList.toJson().toString());
    }

}