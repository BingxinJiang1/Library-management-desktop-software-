package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// unit test for Book class constructor, setters and getters.
class BookTest {

    Book testBook1;
    Book testBook2;
    Book testBook3;
    Book testBook4;
    Book testBook5;

    @BeforeEach
    public void runBefore() {
        testBook1 = new Book("BC travel Guide", 10, 5);
        testBook2 = new Book("MATH Homework", 15, 4);
        testBook3 = new Book("CPSC Textbook", 20, 3);
        testBook4 = new Book("Brainstorming", 30.99, 2);
        testBook5 = new Book("Asian History", 1, 1);
    }

    @Test
    public void testBookGetter() {
        assertEquals("BC travel Guide", testBook1.getName());
        assertEquals("MATH Homework", testBook2.getName());
        assertEquals("CPSC Textbook", testBook3.getName());
        assertEquals("Brainstorming", testBook4.getName());
        assertEquals("Asian History", testBook5.getName());

        assertEquals(10, testBook1.getPrice());
        assertEquals(15, testBook2.getPrice());
        assertEquals(20, testBook3.getPrice());
        assertEquals(30.99, testBook4.getPrice());
        assertEquals(1, testBook5.getPrice());

        assertEquals(5, testBook1.getStock());
        assertEquals(4, testBook2.getStock());
        assertEquals(3, testBook3.getStock());
        assertEquals(2, testBook4.getStock());
        assertEquals(1, testBook5.getStock());
    }

    @Test
    public void testBookSetter() {
        testBook1.setName("111");
        assertEquals("111", testBook1.getName());
        testBook2.setName("222");
        assertEquals("222", testBook2.getName());
        testBook3.setName("333");
        assertEquals("333", testBook3.getName());

        testBook1.setPrice(10.5);
        assertEquals(10.5, testBook1.getPrice());
        testBook2.setPrice(1);
        assertEquals(1, testBook2.getPrice());
        testBook3.setPrice(502);
        assertEquals(502, testBook3.getPrice());
    }
}