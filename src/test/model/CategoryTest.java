package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Category.getDateToday;
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
    void getDateTodayTest() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        assertEquals(String.valueOf(date), Category.getDateToday());
    }

    @Test
    void CategoryTest() {
        assertEquals("Diary", category1.getTitle());
        assertEquals("Ideas", category2.getTitle());
        assertEquals(new ArrayList<>(), category1.getTextList());
        assertEquals(new ArrayList<>(), category2.getTextList());
    }

    @Test
    void addTextTest() {
        category1.addText(new Text(getDateToday(), "Today was a great day"));
        assertEquals(1, category1.getTextList().size());
        category1.addText(new Text(getDateToday(), "I saw dogs in the park"));
        assertEquals(1, category1.getTextList().size());
        category2.addText(new Text("2022-03-08", "Past date entry insert"));
        assertEquals(1, category2.getTextList().size());
        category2.addText(new Text(getDateToday(), "Current date entry insert"));
        assertEquals(2, category2.getTextList().size());
    }
}