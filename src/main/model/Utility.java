package model;

public class Utility {
    public static String getDateToday() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return String.valueOf(date);
    }
}