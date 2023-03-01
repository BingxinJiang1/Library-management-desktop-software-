package ui;

import model.Book;
import model.BookSystem;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// represent a Books Management System Application with user interface.
// A bookstore management system is developed to organize books on the computer
// for a book manager. The six main functions:
// add books, remove books, print books, modify books, save books, load books.
// Realize users stories.
public class BookManagementApp {

    BookSystem bookSystem;
    private static final Scanner keyboard = new Scanner(System.in);
    private static final String JSON_STORE = "./data/bookSystem.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Book Management application
    public BookManagementApp() throws FileNotFoundException {
        bookSystem = new BookSystem();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookManagement();
    }

    // MODIFIES: this
    // EFFECTS: let user enter command
    //              if user enter zero then system quits
    //              otherwise, processes user input
    private void runBookManagement() {
        String userEnter;
        boolean keepAsking = true;

        while (keepAsking) {
            displayGeneralMenu();
            userEnter = keyboard.nextLine();

            if (userEnter.equals("0")) {
                keepAsking = false;
            } else {
                processManagerCommand(userEnter);
            }
        }
        System.out.println("\nSystem end");
    }

    // EFFECTS: displays organizing menu of options to manger
    //          add books, remove book, sprint books, modify books, save books, load books
    private void displayGeneralMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tenter 1 -> add books");
        System.out.println("\tenter 2 -> remove books");
        System.out.println("\tenter 3 -> print books");
        System.out.println("\tenter 4 -> modify books");
        System.out.println("\tenter 5 -> save books to file");
        System.out.println("\tenter 6 -> load books from file");
        System.out.println("\tenter 0 -> quit");
    }

    // EFFECTS: pass user input into method
    //          if user enter 1
    //              process add book method
    //          if user enter 2
    //              process remove book method
    //          if user enter 3
    //              process print book method
    //          if user enter 4
    //              process modify book method
    //          if user enter 5
    //              process saveBookSystem method
    //          if user enter 6
    //              process loadBookSystem method
    //          otherwise, let user enter again.
    private void processManagerCommand(String userEnter) {
        if (userEnter.equals("1")) {
            addBook();
        } else if (userEnter.equals("2")) {
            removeBook();
        } else if (userEnter.equals("3")) {
            printBook();
        } else if (userEnter.equals("4")) {
            modifyBook();
        } else if (userEnter.equals("5")) {
            saveBookSystem();
        } else if (userEnter.equals("6")) {
            loadBookSystem();
        } else {
            System.out.println("Invalid input, please enter again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: let users add new books to the system
    //              book's price and book's stock cannot be zero or negative
    //              otherwise, let users enter again
    //          if this book existed in the system, just accumulates the stock
    //          if two books has same name, they are a same book.
    private void addBook() {
        System.out.println("Enter a new book name: ");
        String name = keyboard.nextLine();

        System.out.println("Enter a new book price: ");
        int price = keyboard.nextInt();
        while (price <= 0) {
            System.out.println("Invalid input. Price cannot be zero or negative. \nEnter a new book price: ");
            price = keyboard.nextInt();
        }

        System.out.println("Enter a new book stock: ");
        int stock = keyboard.nextInt();
        while (stock <= 0) {
            System.out.println("Invalid input. Stock cannot be zero or negative. \nEnter a new book price: ");
            stock = keyboard.nextInt();
        }
        // Consume the remaining newline.
        keyboard.nextLine();

        Book newBook = new Book(name, price, stock);
        if (bookSystem.isBookExistInSystem(newBook)) {
            System.out.println("This book exist in the system, the stock is accumulated.\n");
        }
        bookSystem.addNewBook(newBook);
    }

    // EFFECTS: print existed books name, price, current stock from this system.
    private void printBook() {
        for (int i = 0; i < bookSystem.getSystemList().size(); i++) {
            System.out.println(i + " -> " + bookSystem.getSystemList().get(i).getName()
                    + "  $" + bookSystem.getSystemList().get(i).getPrice()
                    + "  current stock: " + bookSystem.getSystemList().get(i).getStock());
        }
    }

    // MODIFIES: this
    // EFFECTS: let users choose which book they want to remove,
    //              while entered value out of arraylist bound, let user enter again
    //          users choose how many this books they want to remove
    //              while entered value out of intended bound, let user enter again
    //          remove books
    private void removeBook() {
        System.out.println("Select the book you want to remove: ");
        int selectBook = keyboard.nextInt();
        while (selectBook < 0 || selectBook >= bookSystem.getSystemList().size()) {
            System.out.println("Invalid input. Please enter again");
            selectBook = keyboard.nextInt();
        }

        System.out.println("Enter the number of book you want to remove: ");
        int numToDelete = keyboard.nextInt();
        while (numToDelete <= 0 || numToDelete > bookSystem.getSystemList().get(selectBook).getStock()) {
            System.out.println("Invalid input. Please enter again");
            numToDelete = keyboard.nextInt();
        }
        // Consume the remaining newline.
        keyboard.nextLine();

        bookSystem.removeFromSystem(selectBook, numToDelete);
    }

    /*
     * citation:
     * I wrote this function after looking at the provided program (JsonSerializationDemo)
     * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
     * */
    // EFFECTS: save book system to file.
    private void saveBookSystem() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookSystem);
            jsonWriter.close();
            System.out.println("Saved all books to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
     * citation:
     * I wrote this function after looking at the provided program (JsonSerializationDemo)
     * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
     * */
    // MODIFIES: this
    // EFFECTS: load book system from file
    private void loadBookSystem() {
        try {
            bookSystem = jsonReader.read();
            System.out.println("Loaded all books from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: users are allowed to modify the book name, book price and book stock
    //              while entered value out of arraylist bound, let user enter again
    //          display menu of book information
    //          users choose which part of book info to change
    private void modifyBook() {
        System.out.println("Select the book you want to modify: ");
        int selectBook = keyboard.nextInt();
        while (selectBook < 0 || selectBook >= bookSystem.getSystemList().size()) {
            System.out.println("Invalid input. Please enter again");
            selectBook = keyboard.nextInt();
        }
        // Consume the remaining newline.
        keyboard.nextLine();

        displayInfoMenu();
        modifyInfo(selectBook);
    }

    // MODIFIES: this
    // EFFECTS: display menu of book information, book name, book price and book stock
    private void displayInfoMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tenter 1 -> change book name");
        System.out.println("\tenter 2 -> change book price");
        System.out.println("\tenter 3 -> change book stock");
    }

    // MODIFIES: this
    // EFFECTS: if users enter 1
    //              user can change book name
    //          else if users enter 2
    //              user can change book price
    //          else if users enter 3
    //              user can change book stock
    //          otherwise, let users enter again
    private void modifyInfo(int i) {
        String selectInfo = keyboard.nextLine();
        boolean keepAsking = true;

        while (keepAsking) {
            if (selectInfo.equals("1")) {
                System.out.println("Enter a new book name: ");
                String newName = keyboard.nextLine();
                bookSystem.getSystemList().get(i).setName(newName);
                keepAsking = false;
            } else if (selectInfo.equals("2")) {
                changePrice(i);
                keepAsking = false;
            } else if (selectInfo.equals("3")) {
                changeStock(i);
                keepAsking = false;
            } else {
                System.out.println("Invalid input, please enter again.");
                selectInfo = keyboard.nextLine();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: while new price <= 0
    //              let users enter again
    //          set a new book price
    private void changePrice(int i) {
        System.out.println("Enter a new book price: ");
        double newPrice = keyboard.nextDouble();
        while (newPrice <= 0) {
            System.out.println("Invalid input. Please enter again");
            newPrice = keyboard.nextDouble();
        }
        bookSystem.getSystemList().get(i).setPrice(newPrice);
        // Consume the remaining newline.
        keyboard.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: while new stock <= 0
    //              let users enter again
    //          set a new book stock
    //          this changeStock method cannot use to remove books.
    private void changeStock(int i) {
        System.out.println("Enter a new book stock: ");
        int newStock = keyboard.nextInt();
        while (newStock <= 0) {
            System.out.println("Invalid input. Please enter again");
            newStock = keyboard.nextInt();
        }
        bookSystem.getSystemList().get(i).setStock(newStock);
        // Consume the remaining newline.
        keyboard.nextLine();
    }

}
