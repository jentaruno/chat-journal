package model;

import org.junit.jupiter.api.Test;

import static model.Utility.getDateToday;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void getDateTodayTest() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        assertEquals(String.valueOf(date), getDateToday());
    }
}