package persistence;

import model.Book;
import model.BookSystem;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * citation:
 * I wrote this class after looking at the provided program (JsonSerializationDemo).
 * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * */
public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            BookSystem testSystem = new BookSystem();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException was expected.");
        } catch (FileNotFoundException e) {
            // pass
        }

    }

    @Test
    public void testWriterEmptyBookSystem() {
        try {
            BookSystem testSystem = new BookSystem();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookSystem.json");
            writer.open();
            writer.write(testSystem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookSystem.json");
            testSystem = reader.read();
            assertEquals(0, testSystem.getSystemList().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralBookSystem() {
        try {
            BookSystem testSystem = new BookSystem();
            testSystem.addNewBook(new Book("BC travel Guide", 10, 5));
            testSystem.addNewBook(new Book("MATH Homework", 15, 4));
            testSystem.addNewBook(new Book("CPSC Textbook", 20, 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookSystem.json");
            writer.open();
            writer.write(testSystem);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookSystem.json");
            testSystem = reader.read();
            List<Book> thingies = testSystem.getSystemList();
            assertEquals(3, thingies.size());
            assertEquals("BC travel Guide", testSystem.getSystemList().get(0).getName());
            assertEquals(10, testSystem.getSystemList().get(0).getPrice());
            assertEquals(5, testSystem.getSystemList().get(0).getStock());
            assertEquals("MATH Homework", testSystem.getSystemList().get(1).getName());
            assertEquals(15, testSystem.getSystemList().get(1).getPrice());
            assertEquals(4, testSystem.getSystemList().get(1).getStock());
            assertEquals("CPSC Textbook", testSystem.getSystemList().get(2).getName());
            assertEquals(20, testSystem.getSystemList().get(2).getPrice());
            assertEquals(3, testSystem.getSystemList().get(2).getStock());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
