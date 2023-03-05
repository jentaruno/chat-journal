package persistence;

import model.CategoryList;
import model.Category;
import model.Text;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CategoryList cl = new CategoryList("User");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCategoryList() {
        try {
            CategoryList cl = new CategoryList("Dumbo");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCategoryList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCategoryList.json");
            cl = reader.read();
            assertEquals("Dumbo", cl.getUserName());
            assertEquals(0, cl.getCategoryList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCategoryList() {
        try {
            CategoryList cl = new CategoryList("Shrek");
            Category emptyCategory = new Category("Empty");
            Category categoryWithText = new Category("Two texts");
            Text text1 = new Text("2022-02-01");
            Text text2 = new Text("2022-02-02");
            text2.add("One");
            text2.add("Two");
            categoryWithText.addText(text1);
            categoryWithText.addText(text2);

            cl.addChatCategory(emptyCategory);
            cl.addChatCategory(categoryWithText);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCategoryList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCategoryList.json");
            cl = reader.read();
            assertEquals("Shrek", cl.getUserName());
            assertEquals(2, cl.getCategoryList().size());

            Category category0 = cl.getCategoryList().get(0);
            Category category1 = cl.getCategoryList().get(1);
            checkCategory("Empty", 0, category0);
            checkCategory("Two texts", 2, category1);
            checkText("2022-02-01", 0, category1.getTextList().get(0));
            checkText("2022-02-02", 2, category1.getTextList().get(1));
            checkMessage("One", 0, category1.getTextList().get(1));
            checkMessage("Two", 1, category1.getTextList().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}