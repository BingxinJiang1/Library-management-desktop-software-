package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of book management system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 * I wrote this class after looking at the program(AlarmSystem.java).
 * <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git">...</a>
 */
public class EventLog implements Iterable<Event> {
    /**
     * the only EventLog in the system (Singleton Design Pattern)
     */
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * MODIFIES: this
     * EFFECTS: create an event list. Prevent external construction.
     * (Singleton Design Pattern).
     */
    private EventLog() {
        events = new ArrayList<>();
    }

    /**
     * MODIFIES: this
     * EFFECTS: Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     *
     * @return instance of EventLog
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * MODIFIES: this
     * EFFECTS: Adds an event to the event log.
     *
     * @param e the event to be added
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    // EFFECTS: allow client code to use for-each loop to iterate over the events in the EvenLog object.
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
