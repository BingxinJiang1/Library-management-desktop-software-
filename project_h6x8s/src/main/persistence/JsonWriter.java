package persistence;

import model.BookSystem;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * citation:
 * I wrote this class after looking at the provided program (JsonSerializationDemo).
 * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * */
// Represents a writer that convert the bookSystem into JSON representation, then save them to a file.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: construct a writer, the writer writes to destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: convert JSON representation of the bookSystem to string type, then save them to a file.
    // logs an event indicating that this has occurred.
    public void write(BookSystem bookSystem) {
        JSONObject json = bookSystem.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Save to system."));
    }

    // MODIFIES: this
    // EFFECTS: writes converted string to destination file.
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }


}
