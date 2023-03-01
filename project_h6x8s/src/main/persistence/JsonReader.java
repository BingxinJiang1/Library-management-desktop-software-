package persistence;

import model.Book;
import model.BookSystem;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * citation:
 * I wrote this class after looking at the provided program (JsonSerializationDemo).
 * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * */
// Represents a reader that read book list from JSON data stored in the file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file.
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads book list from file and returns it;
    // throws IOException if an error occurs reading data from file.
    // logs an event indicating that this has occurred.
    public BookSystem read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Load from system."));
        return parseBookSystem(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses book list from JSON object and returns it.
    private BookSystem parseBookSystem(JSONObject jsonObject) {
        BookSystem bookSystem = new BookSystem();
        addBooks(bookSystem, jsonObject);
        return bookSystem;
    }

    // MODIFIES: bookSystem
    // EFFECTS: parses books from JSON object and adds them to bookSystem.
    private void addBooks(BookSystem bookSystem, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("book list");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            constructBook(bookSystem, nextBook);
        }
    }

    // MODIFIES: bookSystem
    // EFFECTS: parses each book information from JSON object and adds it to book system.
    private void constructBook(BookSystem bookSystem, JSONObject jsonObject) {
        String bookName = jsonObject.getString("book name");
        double bookPrice = jsonObject.getDouble("book price");
        int bookStock = jsonObject.getInt("book stock");
        Book book = new Book(bookName, bookPrice, bookStock);
        bookSystem.addNewBook(book);
    }
}
