package persistence;

import model.Book;
import model.BookSystem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * citation:
 * I wrote this class after looking at the provided program (JsonSerializationDemo).
 * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * */
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookSystem testSystem = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBookSystem() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookSystem.json");
        try {
            BookSystem testSystem = reader.read();
            assertEquals(0, testSystem.getSystemList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBookSystem() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookSystem.json");
        try {
            BookSystem testSystem = reader.read();
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
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
