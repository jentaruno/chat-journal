package persistence;

import model.Category;
import model.CategoryList;
import model.Text;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CategoryList cl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCategoryList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCategoryList.json");
        try {
            CategoryList cl = reader.read();
            assertEquals("Jen", cl.getUserName());
            assertEquals(0, cl.getCategoryList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCategoryListEmptyCategory() {
        JsonReader reader = new JsonReader("./data/testReaderCategoryListEmptyCategory.json");
        try {
            CategoryList cl = reader.read();
            assertEquals("User", cl.getUserName());
            assertEquals(1, cl.getCategoryList().size());
            checkCategory("Empty", 0, cl.getCategoryList().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCategoryListCategoriesWithTexts() {
        JsonReader reader = new JsonReader("./data/testReaderCategoryListCategoriesWithTexts.json");
        try {
            CategoryList cl = reader.read();
            assertEquals("User", cl.getUserName());
            assertEquals(2, cl.getCategoryList().size());

            Category c0 = cl.getCategoryList().get(0);
            Text mar5 = c0.getTextList().get(0);
            checkCategory("Dairy", 1, c0);
            checkText("2023-03-05", 3, mar5);
            checkMessage("milk", 0, mar5);
            checkMessage("cows", 1, mar5);
            checkMessage("yogurt", 2, mar5);

            Category c1 = cl.getCategoryList().get(1);
            Text mar2 = c1.getTextList().get(0);
            Text mar3 = c1.getTextList().get(1);
            checkCategory("Hi", 2, c1);
            checkText("2023-03-02", 1, mar2);
            checkMessage("hello", 0, mar2);
            checkText("2023-03-03", 1, mar3);
            checkMessage("howdy", 0, mar3);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}