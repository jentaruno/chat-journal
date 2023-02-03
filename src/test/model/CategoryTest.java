package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category1;
    Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category("Diary");
        category2 = new Category("Ideas");
    }

    @Test
    void CategoryTest() {
        assertEquals("Diary", category1.getTitle());
        assertEquals("Ideas", category2.getTitle());
        assertEquals(new ArrayList<>(),category1.getTextList());
        assertEquals(new ArrayList<>(),category2.getTextList());
    }

    @Test
    void addTextTest() {
        category1.addText("Today was a great day");
        assertEquals(1, category1.getTextList().size());
        category1.addText("I saw dogs in the park");
        assertEquals(2, category1.getTextList().size());
    }
}