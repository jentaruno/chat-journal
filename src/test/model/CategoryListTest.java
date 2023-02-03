package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryListTest {

    CategoryList categoryList;

    @BeforeEach
    void setUp() {
        categoryList = new CategoryList();
        categoryList.newChatCategory("Diary");
        categoryList.newChatCategory("Ideas");
    }

    @Test
    void CategoryListTest() {
        assertEquals(2,categoryList.getCategoryList().size());
    }

    @Test
    void newChatCategoryTest() {
        assertEquals("Diary", categoryList.getCategory(0));
        assertEquals("Ideas", categoryList.getCategory(1));
    }

    @Test
    void deleteChatCategoryTest() {
        categoryList.deleteChatCategory("Diary");
        assertEquals(1,categoryList.getCategoryList().size());
        assertEquals("Ideas", categoryList.getCategory(0));
        categoryList.deleteChatCategory("Ideas");
        assertEquals(0,categoryList.getCategoryList().size());
    }

}