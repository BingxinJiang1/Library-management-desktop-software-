package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a book having book name, book price
// and the current number of books in stock.
// Implementation of getters and setters.
public class Book implements Writable {

    private String name;
    private double price;
    private int stock;

    // REQUIRES: stock,price can not initial as zero
    // MODIFIES: this
    // EFFECTS: construct a book
    public Book(String bookName, double price, int stock) {
        this.name = bookName;
        this.price = price;
        this.stock = stock;
    }

    // MODIFIES: this
    // EFFECTS: set a new book name
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: set a new book price
    public void setPrice(double price) {
        this.price = price;
    }

    // REQUIRES: stock >= 0, librarian addExistBook and removeExistBook methods to change stock number
    // MODIFIES: this
    // EFFECTS: set a new stock of book
    public void setStock(int stock) {
        this.stock = stock;
    }

    // EFFECTS: return book's name
    public String getName() {
        return name;
    }

    // EFFECTS: return price of book
    public double getPrice() {
        return price;
    }

    // EFFECTS: get number of stock
    public int getStock() {
        return stock;
    }

    /*
     * citation:
     * I wrote this function after looking at the provided program (JsonSerializationDemo)
     * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
     * */
    // return a JSON object including name, price, stock.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("book name", name);
        json.put("book price", price);
        json.put("book stock", stock);
        return json;
    }

}
