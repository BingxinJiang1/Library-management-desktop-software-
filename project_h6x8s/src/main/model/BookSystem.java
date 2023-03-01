package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// This class represents a System, it creates a list to arrange books.
// These methods are used to implement the basic functions.
public class BookSystem implements Writable {

    private final ArrayList<Book> systemList;

    // EFFECTS: create arrayList to get Book object
    public BookSystem() {
        this.systemList = new ArrayList<>();
    }

    // EFFECTS: return systemList
    public ArrayList<Book> getSystemList() {
        return systemList;
    }

    // EFFECTS: check this book exist in the system or not, exist return true, otherwise return false.
    //          two books are same if they have same book name
    public boolean isBookExistInSystem(Book book) {
        int i = 0;
        boolean bookExist = false;
        while (!bookExist && i < systemList.size()) {
            if (systemList.get(i).getName().equals(book.getName())) {
                bookExist = true;
            } else {
                i++;
            }
        }
        return bookExist;
    }

    // REQUIRES: this book exists in the system
    // EFFECTS: search book's position in system, return the index of book
    public int searchBook(Book book) {
        int position = 0;
        boolean find = false;
        while (!find && position < systemList.size()) {
            if (systemList.get(position).getName().equals(book.getName())) {
                find = true;
            } else {
                position++;
            }
        }
        return position;
    }

    // MODIFIES: this
    // EFFECTS: if pass in book exist in the system
    //              search book and
    //              accumulate new stock on the old
    //          otherwise, add a new book object into system list
    //          logs an event indicating that this has occurred.
    public void addNewBook(Book book) {
        if (isBookExistInSystem(book)) {
            addExistBook(searchBook(book), book.getStock());
            EventLog.getInstance().logEvent(new Event("Added stock to existed book."));
        } else {
            systemList.add(book);
            EventLog.getInstance().logEvent(new Event("Added new books to system."));
        }
    }

    // REQUIRES: this book exists in the system list, number > 0
    // MODIFIES: this
    // EFFECTS: stock more for the existing book
    public void addExistBook(int i, int num) {
        int oldStock = systemList.get(i).getStock();
        systemList.get(i).setStock(oldStock + num);
    }

    // REQUIRES: this book exists in the system, 0 < number =< old stock
    // MODIFIES: this
    // EFFECTS: if the current stock of book > the number that needs to remove
    //              current stock = current stock - num
    //          else
    //              set zero to the stock of this book
    //              remove the book object
    //          logs an event indicating that this has occurred.
    public void removeFromSystem(int i, int num) {
        int newStock;
        if (systemList.get(i).getStock() > num) {
            newStock = systemList.get(i).getStock() - num;
            systemList.get(i).setStock(newStock);
            EventLog.getInstance().logEvent(new Event("Removed some selected books from system."));
        } else {
            systemList.get(i).setStock(0);
            systemList.remove(i);
            EventLog.getInstance().logEvent(new Event("Removed all selected books from system."));
        }

    }

    /*
     * citation:
     * I wrote this function after looking at the provided program (JsonSerializationDemo)
     * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
     * */
    // return this book list as a JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("book list", booksToJson());
        return json;
    }

    /*
     * citation:
     * I wrote this function after looking at the provided program (JsonSerializationDemo)
     * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
     * */
    // EFFECTS: returns books in this book system as a JSON array.
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book next : systemList) {
            jsonArray.put(next.toJson());
        }

        return jsonArray;
    }
}
