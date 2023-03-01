package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// unit test for BookSystem class.
public class BookSystemTest {

    Book testBook1;
    Book testBook2;
    Book testBook3;
    BookSystem testSystem;

    @BeforeEach
    public void runBefore() {
        testBook1 = new Book("BC travel Guide", 10, 5);
        testBook2 = new Book("Brainstorming", 30.99, 2);
        testBook3 = new Book("Asian History", 1, 1);
        testSystem = new BookSystem();
    }

    @Test
    public void testBookSystem() {
        assertEquals(0, testSystem.getSystemList().size());
        assertTrue(testSystem.getSystemList().isEmpty());
    }

    @Test
    public void testIsBookExistInSystem() {
        // case1: a book exists in the system
        testSystem.addNewBook(testBook1);
        testSystem.addNewBook(testBook2);
        testSystem.addNewBook(testBook3);
        assertTrue(testSystem.isBookExistInSystem(testBook3));
        assertTrue(testSystem.isBookExistInSystem(testBook2));
        assertTrue(testSystem.isBookExistInSystem(testBook1));
        // case2: a book does not exist in the system
        Book testBook4 = new Book("MATH Homework", 15, 4);
        assertFalse(testSystem.isBookExistInSystem(testBook4));
        assertEquals(3, testSystem.getSystemList().size());
    }

    @Test
    public void testAddNewBookWithOneBook() {
        testSystem.addNewBook(testBook1);
        assertEquals("BC travel Guide", testSystem.getSystemList().get(0).getName());
        assertEquals(1, testSystem.getSystemList().size());
    }

    @Test
    public void testAddNewBookWithDistinctBooks() {
        testSystem.addNewBook(testBook1);
        assertEquals("BC travel Guide", testSystem.getSystemList().get(0).getName());
        testSystem.addNewBook(testBook2);
        assertEquals(30.99, testSystem.getSystemList().get(1).getPrice());
        testSystem.addNewBook(testBook3);
        assertEquals(1, testSystem.getSystemList().get(2).getStock());
        assertEquals(3, testSystem.getSystemList().size());
    }

    // use addNewBook method
    @Test
    public void testAddExistBookWithOneSameBook1() {
        testSystem.addNewBook(testBook1);
        Book testBook4 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook4);
        assertEquals(15, testSystem.getSystemList().get(0).getStock());
        assertEquals(1, testSystem.getSystemList().size());
    }

    // use addNewBook method
    @Test
    public void testAddExistBookWithOneSameBookThreeTimes1() {
        testSystem.addNewBook(testBook1);
        Book testBook4 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook4);
        assertEquals(15, testSystem.getSystemList().get(0).getStock());
        assertEquals(1, testSystem.getSystemList().size());
        Book testBook5 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook5);
        assertEquals(25, testSystem.getSystemList().get(0).getStock());
        assertEquals(1, testSystem.getSystemList().size());
        Book testBook6 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook6);
        assertEquals(35, testSystem.getSystemList().get(0).getStock());
        assertEquals(1, testSystem.getSystemList().size());
    }

    // combine and test all scenarios of adding books.
    @Test
    public void testAddNewBookRepeatedly() {
        // case1: add one book object to the system
        testSystem.addNewBook(testBook1);
        assertEquals("BC travel Guide", testSystem.getSystemList().get(0).getName());
        // case2: add multiple books objects to the system
        testSystem.addNewBook(testBook2);
        assertEquals(30.99, testSystem.getSystemList().get(1).getPrice());
        testSystem.addNewBook(testBook3);
        assertEquals(1, testSystem.getSystemList().get(2).getStock());
        assertEquals(3, testSystem.getSystemList().size());
        // case3: add one existed book object
        Book testBook4 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook4);
        assertEquals(15, testSystem.getSystemList().get(0).getStock());
        assertEquals(3, testSystem.getSystemList().size());
        // case4: add same one existed book object multiple times
        Book testBook5 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook5);
        assertEquals(25, testSystem.getSystemList().get(0).getStock());
        assertEquals(3, testSystem.getSystemList().size());
        Book testBook6 = new Book("BC travel Guide", 15, 10);
        testSystem.addNewBook(testBook6);
        assertEquals(35, testSystem.getSystemList().get(0).getStock());
        assertEquals(3, testSystem.getSystemList().size());
    }

    // use addExistBook method
    @Test
    public void testAddExistBookWithOneSameBook2() {
        // case1: add one existed book
        testSystem.addNewBook(testBook1);
        testSystem.addExistBook(0, 5);
        assertEquals(10, testSystem.getSystemList().get(0).getStock());
    }

    // use addExistBook method
    @Test
    public void testAddExistBookWithOneSameBookThreeTimes2() {
        // case2: add same one existed books multiple times
        testSystem.addNewBook(testBook1);
        testSystem.addExistBook(0, 5);
        assertEquals(10, testSystem.getSystemList().get(0).getStock());
        testSystem.addExistBook(0, 11);
        assertEquals(21, testSystem.getSystemList().get(0).getStock());
        testSystem.addExistBook(0, 10);
        assertEquals(31, testSystem.getSystemList().get(0).getStock());
        assertEquals(1, testSystem.getSystemList().size());
    }

    // use addExistBook method
    @Test
    public void testAddExistBookWithDistinctBooksMultipleTimes() {
        // case3: add distinct existed books
        testSystem.addNewBook(testBook1);
        testSystem.addExistBook(0, 5);
        assertEquals(10, testSystem.getSystemList().get(0).getStock());
        testSystem.addNewBook(testBook2);
        testSystem.addExistBook(1, 1);
        assertEquals(3, testSystem.getSystemList().get(1).getStock());
        testSystem.addNewBook(testBook3);
        testSystem.addExistBook(2, 10);
        assertEquals(11, testSystem.getSystemList().get(2).getStock());
        assertEquals(3, testSystem.getSystemList().size());
    }

    @Test
    public void testSearchBookOneExistBook() {
        //case1: search one existed book
        testSystem.addNewBook(testBook2);
        assertEquals(0, testSystem.searchBook(testBook2));
    }

    @Test
    public void testSearchBookDistinctExistBooks() {
        //case2: search different existed books
        testSystem.addNewBook(testBook2);
        testSystem.addNewBook(testBook1);
        testSystem.addNewBook(testBook3);
        assertEquals(0, testSystem.searchBook(testBook2));
        assertEquals(1, testSystem.searchBook(testBook1));
        assertEquals(2, testSystem.searchBook(testBook3));
    }

    @Test
    public void testSearchBookWithOneNotExistBook() {
        // case3: search one book it does not exist int system
        testSystem.addNewBook(testBook2);
        testSystem.addNewBook(testBook1);
        testSystem.addNewBook(testBook3);
        Book testBook4 = new Book("MATH Homework", 15, 4);
        assertEquals(3, testSystem.searchBook(testBook4));
        assertEquals(3, testSystem.getSystemList().size());
    }

    @Test
    public void testRemoveFromSystemRemoveOneStockOfOneBook() {
        // case1: remove 1 stock of one book
        testSystem.addNewBook(testBook1);
        testSystem.removeFromSystem(0, 1);
        assertEquals(4, testSystem.getSystemList().get(0).getStock());
    }

    @Test
    public void testRemoveFromSystemRemoveSomeStocksOfOneBook() {
        // case2: remove 3 stocks of one book
        testSystem.addNewBook(testBook1);
        testSystem.removeFromSystem(0, 3);
        assertEquals(2, testSystem.getSystemList().get(0).getStock());
    }

    @Test
    public void testRemoveFromSystemRemoveAllStocksOfOneBook() {
        // case3: remove 5 stocks of one book
        testSystem.addNewBook(testBook1);
        testSystem.removeFromSystem(0, 5);
        assertEquals(0, testBook1.getStock());
        assertTrue(testSystem.getSystemList().isEmpty());
    }

    @Test
    public void testRemoveFromSystemRepeatedly() {
        // case4: remove distinct books with distinct amount
        testSystem.addNewBook(testBook3);
        testSystem.addNewBook(testBook1);
        testSystem.addNewBook(testBook2);
        testSystem.removeFromSystem(1, 3);
        assertEquals(2, testSystem.getSystemList().get(1).getStock());
        testSystem.removeFromSystem(2, 2);
        assertFalse(testSystem.getSystemList().contains(testBook2));
        assertEquals(2, testSystem.getSystemList().size());
        testSystem.removeFromSystem(0, 1);
        assertFalse(testSystem.getSystemList().contains(testBook3));
        assertEquals(1, testSystem.getSystemList().size());
        testSystem.removeFromSystem(0, 2);
        assertFalse(testSystem.getSystemList().contains(testBook1));
        assertTrue(testSystem.getSystemList().isEmpty());
    }
}
