package persistence;

import org.json.JSONObject;

/*
 * citation:
 * I wrote this interface after looking at the provided program (JsonSerializationDemo).
 * JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * */
// This interface represents a general behaviour of writable class.
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}