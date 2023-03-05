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
    void CategoryTest() {
        assertEquals("Diary", category1.getTitle());
        assertEquals("Ideas", category2.getTitle());
        assertEquals(new ArrayList<>(), category1.getTextList());
        assertEquals(new ArrayList<>(), category2.getTextList());
    }

    @Test
    void getDateTodayTest() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        assertEquals(String.valueOf(date), Category.getDateToday());
    }

    @Test
    void addTextTest() {
        Text sampleText1 = new Text(getDateToday());
        sampleText1.add("Current date entry insert");
        Text sampleText2 = new Text("2022-03-08");
        sampleText2.add("Past date entry insert");
        category1.addText(sampleText1);
        assertEquals(1, category1.getTextList().size());
        category1.addText(sampleText1);
        assertEquals(1, category1.getTextList().size());
        category2.addText(sampleText2);
        assertEquals(1, category2.getTextList().size());
        category2.addText(sampleText1);
        assertEquals(2, category2.getTextList().size());
    }

    @Test
    void toJSON() {
        assertEquals("{\"textList\":[],\"title\":\"Diary\"}", category1.toJson().toString());
        Text sampleText1 = new Text("2022-03-05");
        category1.addText(new Text("2022-03-05"));
        assertEquals("{\"textList\":[{\"date\":\"2022-03-05\",\"texts\":[]}],\"title\":\"Diary\"}", category1.toJson().toString());
    }
}