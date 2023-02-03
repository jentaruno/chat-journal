package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextTest {

    Text text1;
    Text text2;

    @BeforeEach
    void setUp() {
        text1 = new Text();
        text2 = new Text();
    }

    @Test
    void TextTest() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        assertEquals(String.valueOf(date), text1.getDate());
        assertEquals(String.valueOf(date), text2.getDate());
        assertEquals(new ArrayList<>(), text1.getTexts());
        assertEquals(new ArrayList<>(), text2.getTexts());
    }

    @Test
    void add() {
        text1.add("Today was a great day");
        assertEquals("Today was a great day", text1.getText(0));
        text1.add("I saw dogs in the park");
        assertEquals("I saw dogs in the park",text1.getText(1));
    }
}