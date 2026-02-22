package lukas.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs during a specific time frame.
 * Stores both a start (from) and an end (to) LocalDateTime.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(displayFormat) + " to: " + to.format(displayFormat) + ")";
    }
    @Override
    public String toFileFormat() {
        int statusAsNumber = 0;
        if(isDone){
            statusAsNumber = 1;
        }
        return String.format("E | %d | %s | %s | %s", statusAsNumber, description, from, to);    }
}